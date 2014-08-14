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

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentProduct extends Fragment {
	static ListView l;
	public static ImageView vtdirect, vtweb;
	public static TextView gross, grandTotal;
	public static EditText ship_First, ship_Last, ship_Address, ship_City, ship_Postal, ship_Phone;
	public static String shipFirst, shipLast, shipAddress, shipCity, shipPostal, shipPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_product, container, false);
		l = (ListView) v.findViewById(R.id.list_bought);
		vtweb = (ImageView) v.findViewById(R.id.vtweb);
		vtdirect = (ImageView) v.findViewById(R.id.vtdirect);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		ship_First = (EditText) v.findViewById(R.id.ship_First);
		ship_Last = (EditText) v.findViewById(R.id.ship_Last);
		ship_Address = (EditText) v.findViewById(R.id.ship_Address);
		ship_City = (EditText) v.findViewById(R.id.ship_City);
		ship_Postal = (EditText) v.findViewById(R.id.ship_Post);
		ship_Phone = (EditText) v.findViewById(R.id.ship_Phone);
		grandTotal = (TextView) v.findViewById(R.id.grandTotal);
		gross.setText(""+FragmentHome.gross.getText());
		grandTotal.setText(""+FragmentHome.gross.getText());
		//getActivity().getActionBar().setTitle("Shipping Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		//getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome)getActivity()).getCart());
		FragmentProduct.l.setAdapter(adapter);
		vtweb.setOnClickListener(new VTWebListener());
		vtdirect.setOnClickListener(new VTDirectListener());
		return v;
	}

    private class VTDirectListener implements OnClickListener {

    	public VTDirectListener(){
    		
    	}

        public void onClick(View v) {
        	//Toast.makeText(context, nearbyItems.get(position).getNama(), Toast.LENGTH_SHORT).show();
        	CheckEmail test = new CheckEmail(getActivity());
        	test.execute("http://vt-dummy-store.ap01.aws.af.cm/android_check.php");
        }

     }
	
    private class VTWebListener implements OnClickListener {

    	public VTWebListener(){
    		
    	}

        public void onClick(View v) {
        	Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			if(Integer.parseInt(gross.getText().toString())>1000000){
				ver.setVTWeb(VeritransObject.SECURE_TRUE, "credit_card", "mandiri_clickpay", "cimb_clicks");
			}
			else{
				ver.setVTWeb(VeritransObject.SECURE_FALSE, "credit_card", "mandiri_clickpay", "cimb_clicks");
			}
			ver.setBillingInfo(FragmentProduct.ship_First.getText().toString(),
					FragmentProduct.ship_Last.getText().toString(),
					FragmentProduct.ship_Address.getText().toString(),
					FragmentProduct.ship_City.getText().toString(),
					FragmentProduct.ship_Postal.getText().toString(),
					FragmentProduct.ship_Phone.getText().toString(), "IDN");
			ver.setShippingInfo(FragmentProduct.ship_First.getText().toString(),
					FragmentProduct.ship_Last.getText().toString(),
					FragmentProduct.ship_Address.getText().toString(),
					FragmentProduct.ship_City.getText().toString(),
					FragmentProduct.ship_Postal.getText().toString(),
					FragmentProduct.ship_Phone.getText().toString(), "IDN");
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
        }

     }
    
	class CheckEmail extends AsyncTask<String, Void, Void> {
		private String Content;
		Context context;

		public CheckEmail(Context context) {
			this.context = context;
		}

		protected Void doInBackground(String... urls) {

			try {
				String url = urls[0];
				HttpPost httpRequest = new HttpPost(URI.create(url));
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
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
			//Toast.makeText(context, Content, Toast.LENGTH_SHORT).show();
        	shipFirst = ship_First.getText().toString();
            shipLast = ship_Last.getText().toString();
            shipAddress = ship_Address.getText().toString();
            shipCity = ship_City.getText().toString();
            shipPostal = ship_Postal.getText().toString();
            shipPhone = ship_Phone.getText().toString();
	      	Fragment fr = new Fragment();
	  		fr = new FragmentVTDirect();
	  		final Bundle bundle = new Bundle();
    		bundle.putString("content", Content);
    		fr.setArguments(bundle);
	  		FragmentManager fm = getFragmentManager();
	  		FragmentTransaction fragTrans = fm.beginTransaction();
	  		fragTrans.addToBackStack("to Checkout");
	  		fragTrans.replace(R.id.frame_container, fr, "Billing");
	  		fragTrans.commit();
		}

	}
}
