package com.destra.vtdummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.ShoppingCartItem;

public class FragmentCreditCard extends Fragment {
	static ListView l;
	public static Button pay;
	public static TextView gross;
	public static final String client_key =  "bb558bb6-371a-415b-b7ee-bf285f33868f";
	EditText cc_Number, name_Card, exp_Month, exp_Year, cvv_Number;
	String ccNumber, nameCard, expMonth, expYear, cvvNumber;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_credit_card, container, false);
		l = (ListView) v.findViewById(R.id.list_bought);
		pay = (Button) v.findViewById(R.id.pay);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		cc_Number = (EditText) v.findViewById(R.id.cc_Number);
		name_Card = (EditText) v.findViewById(R.id.name_Card);
		exp_Month = (EditText) v.findViewById(R.id.exp_Month);
		exp_Year = (EditText) v.findViewById(R.id.exp_Year);
		cvv_Number = (EditText) v.findViewById(R.id.cvv_Number);
		gross.setText(""+FragmentHome.gross.getText());
		getActivity().getActionBar().setTitle("CC Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome)getActivity()).getCart());
		l.setAdapter(adapter);
		pay.setOnClickListener(new PayButtonListener());
		return v;
	}
	
    private class PayButtonListener implements OnClickListener {

    	public PayButtonListener(){
    		
    	}

        public void onClick(View v) {
        	//Toast.makeText(context, nearbyItems.get(position).getNama(), Toast.LENGTH_SHORT).show();
        	RetrieveToken p = new RetrieveToken(getActivity());
        	p.execute("http://vt-dummy-store.ap01.aws.af.cm/checkout_process2.php");
        }

     }
    
    class RequestURL{
    	String url;
    	ArrayList<ShoppingCartItem> cart;
    	
    	public RequestURL(String u, ArrayList<ShoppingCartItem> c){
    		url = u;
    		cart = c;
    	}
    }
    
    class RetrieveToken extends AsyncTask<String, Void, Void> {
		private String Content;
        String parameters = "";
		Context context;
		public RetrieveToken(Context context) {
	        this.context = context;
	    } 
	    protected Void doInBackground(String... urls) {
	    	HttpURLConnection connection;
	        OutputStreamWriter request = null;

	             URL url = null;   
	             String response = null;
	             ccNumber = cc_Number.getText().toString();
	             nameCard = name_Card.getText().toString();
	             expMonth = exp_Month.getText().toString();
	             expYear = exp_Year.getText().toString();
	             cvvNumber = cvv_Number.getText().toString();
	             parameters = parameters + "card_number=" + ccNumber;
	             parameters = parameters + "&card_exp_month=" + expMonth;
	             parameters = parameters + "&card_exp_year=" + expYear;
	             parameters = parameters + "&card_cvv=" + cvvNumber;
	             parameters = parameters + "&client_key=" + cvvNumber;
	             try
	             {
	                 url = new URL(urls[0]);
	                 connection = (HttpURLConnection) url.openConnection();
	                 connection.setDoOutput(true);
	                 connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	                 connection.setRequestMethod("GET");    

	                 request = new OutputStreamWriter(connection.getOutputStream());
	                 request.write(parameters);
	                 request.flush();
	                 request.close();            
	                 String line = "";               
	                 InputStreamReader isr = new InputStreamReader(connection.getInputStream());
	                 BufferedReader reader = new BufferedReader(isr);
	                 StringBuilder sb = new StringBuilder();
	                 while ((line = reader.readLine()) != null)
	                 {
	                     sb.append(line + "");
	                 }
	                 // Response from server after login process will be stored in response variable.                
	                 Content = sb.toString();
	                 // You can perform UI operations here
	                 isr.close();
	                 reader.close();

	             }
	             catch(IOException e)
	             {
	                 // Error
	             }
	        return null;
	    }

	    protected void onPostExecute(Void feed) {
	    	JSONObject jsonResponse;
			try {
				jsonResponse = new JSONObject(Content);
				String token = jsonResponse.getString("token_id");
				Toast.makeText(context ,token, Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}
