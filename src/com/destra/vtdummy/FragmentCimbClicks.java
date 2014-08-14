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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.ShoppingCartItem;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentCimbClicks extends Fragment {
	static ListView l;
	public static Button pay;
	public static TextView gross;
	public static EditText description_;
	public static String description;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_cimb_clicks, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		pay = (Button) v.findViewById(R.id.pay);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		description_ = (EditText) v.findViewById(R.id.description_);
		gross.setText("" + FragmentHome.gross.getText());
		getActivity().getActionBar().setTitle("CC Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		pay.setOnClickListener(new PayButtonListener());
		return v;
	}

	private class PayButtonListener implements OnClickListener {

		public PayButtonListener() {

		}

		public void onClick(View v) {
			// Toast.makeText(context, nearbyItems.get(position).getNama(),
			// Toast.LENGTH_SHORT).show();
			description = description_.getText().toString();
			Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setCimbClicks(description);
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
		}

	}
}
