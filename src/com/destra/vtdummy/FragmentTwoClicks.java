package com.destra.vtdummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentTwoClicks extends Fragment{
	static ListView l;
	public static Button pay;
	public static TextView gross;
	public static String token_id;
	EditText cvv_Number;
	TextView cc_Number;
	String ccNumber, cvvNumber;
	Spinner spinner;
	List<String> cards, tokens;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_two_clicks, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		spinner = (Spinner) v.findViewById(R.id.spinner);
		pay = (Button) v.findViewById(R.id.pay);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		cc_Number = (TextView) v.findViewById(R.id.cc_Number);
		cvv_Number = (EditText) v.findViewById(R.id.cvv_Number);
		Bundle args = getArguments();
		if (args  != null && args.containsKey("masked")){
			cards = new ArrayList<String>();
			tokens = new ArrayList<String>();
			DisplayCards d = new DisplayCards(getActivity());
			d.execute("http://vt-dummy-store.ap01.aws.af.cm/android_display_cards.php");
		}
		gross.setText("" + FragmentHome.gross.getText());
		//getActivity().getActionBar().setTitle("CC Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		//getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		pay.setOnClickListener(new PayButtonListener(getActivity()));
		return v;
	}
	

	private class PayButtonListener implements OnClickListener {
		Context context;
		public PayButtonListener(Context context) {
			this.context = context;
		}

		public void onClick(View v) {
			// Toast.makeText(context, nearbyItems.get(position).getNama(),
			// Toast.LENGTH_SHORT).show();
			cvvNumber = cvv_Number.getText().toString();
			Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			if(Integer.parseInt(gross.getText().toString())>1000000){
				ver.setTwoClicks(token_id, cvvNumber, VeritransObject.SECURE_TRUE);
			}
			else{
				ver.setTwoClicks(token_id, cvvNumber, VeritransObject.SECURE_FALSE);
			}
			ver.setBillingInfo(FragmentBilling.bilFirst, FragmentBilling.bilLast, FragmentBilling.bilAddress, FragmentBilling.bilCity, FragmentBilling.bilPostal, FragmentBilling.bilPhone, "IDN");
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
		}

	}
	
	class DisplayCards extends AsyncTask<String, Void, Void> {
		private String Content;
		Context context;

		public DisplayCards(Context context) {
			this.context = context;
		}

		protected Void doInBackground(String... urls) {

			try {
				String url = urls[0];
				HttpPost httpRequest = new HttpPost(URI.create(url));
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		        nameValuePairs.add(new BasicNameValuePair("email", "destra.bintang@veritrans.co.id"));
		        httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(
						entity);
				// Get the server response

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(bufHttpEntity.getContent()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}

				// Append Server Response To Content String
				Content = sb.toString();
				// You can perform UI operations here

			} catch (IOException e) {
				// Error
			}
			return null;
		}

		protected void onPostExecute(Void feed) {
			try {
				JSONArray result = new JSONArray(Content);
				for (int i=0; i<result.length(); i++){
					JSONObject j = new JSONObject(result.getString(i));
					String masked = j.getString("card");
					String token = j.getString("token");
					cards.add(masked);
					tokens.add(token);
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, cards);
				dataAdapter.setDropDownViewResource(R.layout.spinner_item);
				spinner.setAdapter(dataAdapter);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
				    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				        // On selecting a spinner item
				        String item = parent.getItemAtPosition(position).toString();
				 
				        // Showing selected spinner item
				        token_id = tokens.get(position);
				 
				    }

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
