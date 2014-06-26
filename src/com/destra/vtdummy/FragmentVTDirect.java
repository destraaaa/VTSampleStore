package com.destra.vtdummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.ShoppingCartItem;

public class FragmentVTDirect extends Fragment {
	static ListView l;
	public static Button credit_card;
	public static TextView gross;
	EditText bil_First, bil_Last, bil_Address, bil_City, bil_Postal, bil_Phone;
	String bilFirst, bilLast, bilAddress, bilCity, bilPostal, bilPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_choose_method, container, false);
		l = (ListView) v.findViewById(R.id.list_bought);
		credit_card = (Button) v.findViewById(R.id.credit_card);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		bil_First = (EditText) v.findViewById(R.id.bil_First);
		bil_Last = (EditText) v.findViewById(R.id.bil_Last);
		bil_Address = (EditText) v.findViewById(R.id.bil_Address);
		bil_City = (EditText) v.findViewById(R.id.bil_City);
		bil_Postal = (EditText) v.findViewById(R.id.bil_Post);
		bil_Phone = (EditText) v.findViewById(R.id.bil_Phone);
		gross.setText(""+FragmentHome.gross.getText());
		getActivity().getActionBar().setTitle("Billing Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome)getActivity()).getCart());
		l.setAdapter(adapter);
		credit_card.setOnClickListener(new CreditCardListener());
		return v;
	}
	
	private class CreditCardListener implements OnClickListener {

    	public CreditCardListener(){
    		
    	}

        public void onClick(View v) {
        	//Toast.makeText(context, nearbyItems.get(position).getNama(), Toast.LENGTH_SHORT).show();
        	Fragment fr = new Fragment();
    		fr = new FragmentCreditCard();
    		FragmentManager fm = getFragmentManager();
    		FragmentTransaction fragTrans = fm.beginTransaction();
    		fragTrans.addToBackStack("to Checkout");
    		fragTrans.replace(R.id.frame_container, fr, "CreditCard");
    		fragTrans.commit();
        }

     }
	
    private class PayButtonListener implements OnClickListener {

    	public PayButtonListener(){
    		
    	}

        public void onClick(View v) {
        	//Toast.makeText(context, nearbyItems.get(position).getNama(), Toast.LENGTH_SHORT).show();
        	RetrieveRedirectURL p = new RetrieveRedirectURL(getActivity());
        	p.execute(new RequestURL("http://vt-dummy-store.ap01.aws.af.cm/checkout_process2.php", ((ActivityHome)getActivity()).getCart()));
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
    
    class RetrieveRedirectURL extends AsyncTask<RequestURL, Void, Void> {
		private String Content;
        String parameters = "";
		Context context;
		public RetrieveRedirectURL(Context context) {
	        this.context = context;
	    } 
	    protected Void doInBackground(RequestURL... urls) {
	    	HttpURLConnection connection;
	        OutputStreamWriter request = null;

	             URL url = null;   
	             String response = null;
	             bilFirst = bil_First.getText().toString();
	             bilLast = bil_Last.getText().toString();
	             bilAddress = bil_Address.getText().toString();
	             bilCity = bil_City.getText().toString();
	             bilPostal = bil_Postal.getText().toString();
	             bilPhone = bil_Phone.getText().toString();
	             parameters = parameters + "bilFirst=" + bilFirst;
	             parameters = parameters + "&bilLast=" + bilLast;
	             parameters = parameters + "&bilAddress=" + bilAddress;
	             parameters = parameters + "&bilCity=" + bilCity;
	             parameters = parameters + "&bilPostal=" + bilPostal;
	             parameters = parameters + "&bilPhone=" + bilPhone;
	             for (int i = 1; i<=urls[0].cart.size(); i++){
	            	 if(urls[0].cart.get(i-1).getQuantity()!=0){
		            		 parameters = parameters + "&id"+i+"="+urls[0].cart.get(i-1).getItem().getId()+
	 				 				"&name"+i+"="+urls[0].cart.get(i-1).getItem().getNama()+
	 				 			   "&price"+i+"="+urls[0].cart.get(i-1).getItem().getHarga()+
	 				 			"&quantity"+i+"="+urls[0].cart.get(i-1).getQuantity();
	            	 }
	             }
	             if (Integer.parseInt((String)gross.getText()) > 1000000){
	            	 parameters = parameters + "&secure=true";
	             }else{
	            	 parameters = parameters + "&secure=false";
	             }

	             try
	             {
	                 url = new URL(urls[0].url);
	                 connection = (HttpURLConnection) url.openConnection();
	                 connection.setDoOutput(true);
	                 connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	                 connection.setRequestMethod("POST");    

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
	    	//Toast.makeText(context ,parameters, Toast.LENGTH_LONG).show();
	    	Intent i = new Intent(getActivity(), FragmentVTWeb.class);
	    	i.putExtra("url", Content);
	    	startActivity(i);
//	    	Fragment fr = new Fragment();
//    		fr = new FragmentVTWeb();
//    		final Bundle bundle = new Bundle();
//    		bundle.putString("url", Content);
//    		fr.setArguments(bundle); 
//    		FragmentManager fm = getFragmentManager();
//    		FragmentTransaction fragTrans = fm.beginTransaction();
//    		fragTrans.addToBackStack("to Checkout");
//    		fragTrans.replace(R.id.frame_container, fr);
//    		fragTrans.commit();
//	    	Intent i = new Intent(Intent.ACTION_VIEW, Uri
//					.parse(Content));
//	    	i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//	    	this.context.startActivity(i);
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }
	}
}
