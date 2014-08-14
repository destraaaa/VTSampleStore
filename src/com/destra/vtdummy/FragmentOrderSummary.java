package com.destra.vtdummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.ShoppingCartItem;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentOrderSummary extends Fragment {
	static ListView l;
	public static Button shop;
	public static final String client_key = "bb558bb6-371a-415b-b7ee-bf285f33868f";
	public static String status;
	TextView trans_status;
	TextView bilName, bilAddress, bilPhone, shipName, shipAddress, shipPhone, vaNumber_, va_desc;
	String ccNumber, nameCard, expMonth, expYear, cvvNumber;
	AlertDialog alert;
	JSONObject jsonResponse;
	public static LinearLayout header, content, va_info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_order_summary, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		Toast.makeText(getActivity(), "Your transaction is successful", Toast.LENGTH_SHORT).show();
		header = (LinearLayout) v.findViewById(R.id.bil_info_content);
		content = (LinearLayout) v.findViewById(R.id.ship_info_content);
		va_info = (LinearLayout) v.findViewById(R.id.va_info_content);
		vaNumber_ = (TextView) v.findViewById(R.id.vaNumber_);
		va_desc = (TextView) v.findViewById(R.id.desc);
		trans_status = (TextView) v.findViewById(R.id.status);
		shop = (Button) v.findViewById(R.id.shop);
		bilName = (TextView) v.findViewById(R.id.bilName_);
		bilAddress = (TextView) v.findViewById(R.id.bilAddress_);
		bilPhone = (TextView) v.findViewById(R.id.bilPhone_);
		shipName = (TextView) v.findViewById(R.id.shipName_);
		shipAddress = (TextView) v.findViewById(R.id.shipAddress_);
		shipPhone = (TextView) v.findViewById(R.id.shipPhone_);
		shipName.setText(FragmentProduct.shipFirst + " " + FragmentProduct.shipLast);
		shipAddress.setText(FragmentProduct.shipAddress + ", " + FragmentProduct.shipCity + ", " + FragmentProduct.shipPostal);
		shipPhone.setText(FragmentProduct.shipPhone);
		bilName.setText(FragmentBilling.bilFirst + " " + FragmentBilling.bilLast);
		bilAddress.setText(FragmentBilling.bilAddress + ", " + FragmentBilling.bilCity + ", " + FragmentBilling.bilPostal);
		bilPhone.setText(FragmentBilling.bilPhone);
		//getActivity().getActionBar().setTitle("Order Summary");
		// calling onPrepareOptionsMenu() to hide action bar icons
		//getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		Bundle args = getArguments();
		if (args  != null && args.containsKey("status")){
			status = args.getString("status");
			try {
				jsonResponse = new JSONObject(status);
				trans_status.setText(trans_status.getText()+jsonResponse.getString("transaction_status"));
				if (jsonResponse.getString("payment_type").equals(VeritransObject.CREDIT_CARD)){
					header.setVisibility(View.VISIBLE);
					va_info.setVisibility(View.GONE);
				}
				else if((jsonResponse.getString("payment_type").equals(VeritransObject.PERMATA))||(jsonResponse.getString("payment_type").equals(VeritransObject.BII))){
					header.setVisibility(View.GONE);
					if(jsonResponse.has("permata_va_number")){
						vaNumber_.setText(jsonResponse.getString("permata_va_number"));
						va_desc.setText("Visit http://api.sandbox.veritrans.co.id:7676/permata/va/index and submit the number above to complete your payment");
					}
					else if (jsonResponse.has("bii_va_number")){
						vaNumber_.setText(jsonResponse.getString("bii_va_number"));
						va_desc.setText("Visit http://api.sandbox.veritrans.co.id:7676/bii/va/index and submit the number above to complete your payment");
					}
					va_info.setVisibility(View.VISIBLE);
				}
				else{
					header.setVisibility(View.GONE);
					va_info.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		shop.setOnClickListener(new ShopButtonListener());
		return v;
	}
	
	private class ShopButtonListener implements OnClickListener {

    	public ShopButtonListener(){
    		
    	}

        public void onClick(View v) {
        	Fragment fr = new Fragment();
    		fr = new FragmentHome();
    		FragmentManager fm = getFragmentManager();
    		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    		FragmentTransaction fragTrans = fm.beginTransaction();
    		ActivityHome.fragmentTag = "None";
    		fragTrans.replace(R.id.frame_container, fr);
    		fragTrans.commit();
        }

     }

}
