package com.destra.vtdummy;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.format.Time;
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
import com.destra.vtdummy.model.VeritransObject;

public class FragmentBilling extends Fragment{
	static ListView l;
	public static Button fill, one_click, two_clicks;
	public String Content;
	public static TextView gross, grandTotal;
	public static String token_id, masked;
	public static EditText bil_First, bil_Last, bil_Address, bil_City,
			bil_Postal, bil_Phone;
	public static String bilFirst, bilLast, bilAddress, bilCity, bilPostal,
			bilPhone;
	AlertDialog alert;
	JSONObject jsonResponse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_billing, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		fill = (Button) v.findViewById(R.id.fill);
		one_click = (Button) v.findViewById(R.id.one);
		two_clicks = (Button) v.findViewById(R.id.two);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		grandTotal = (TextView) v.findViewById(R.id.grandTotal);
		bil_First = (EditText) v.findViewById(R.id.bil_First);
		bil_Last = (EditText) v.findViewById(R.id.bil_Last);
		bil_Address = (EditText) v.findViewById(R.id.bil_Address);
		bil_City = (EditText) v.findViewById(R.id.bil_City);
		bil_Postal = (EditText) v.findViewById(R.id.bil_Post);
		bil_Phone = (EditText) v.findViewById(R.id.bil_Phone);
		Bundle args = getArguments();
		if (args  != null && args.containsKey("masked")){
			masked = args.getString("masked");
			token_id = args.getString("token_id");
			one_click.setVisibility(View.VISIBLE);
			two_clicks.setVisibility(View.VISIBLE);
		}
		else{
			one_click.setVisibility(View.GONE);
			two_clicks.setVisibility(View.GONE);
		}
		gross.setText("" + FragmentHome.gross.getText());
		grandTotal.setText("" + FragmentHome.gross.getText());
		//getActivity().getActionBar().setTitle("CC Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		//getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		fill.setOnClickListener(new FillButtonListener());
		one_click.setOnClickListener(new OneClickListener());
		two_clicks.setOnClickListener(new TwoClicksListener());
		return v;
	}
	

	private class FillButtonListener implements OnClickListener {

		public FillButtonListener() {

		}

		public void onClick(View v) {
			// Toast.makeText(context, nearbyItems.get(position).getNama(),
			// Toast.LENGTH_SHORT).show();
			bilFirst = bil_First.getText().toString();
			bilLast = bil_Last.getText().toString();
			bilAddress = bil_Address.getText().toString();
			bilCity = bil_City.getText().toString();
			bilPostal = bil_Postal.getText().toString();
			bilPhone = bil_Phone.getText().toString();
			Fragment fr = new Fragment();
			fr = new FragmentCreditCard();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragTrans = fm.beginTransaction();
			fragTrans.addToBackStack("to Checkout");
			fragTrans.replace(R.id.frame_container, fr, "Billing");
			fragTrans.commit();
		}

	}
	
	private class OneClickListener implements OnClickListener {

		public OneClickListener() {

		}

		public void onClick(View v) {
			bilFirst = bil_First.getText().toString();
			bilLast = bil_Last.getText().toString();
			bilAddress = bil_Address.getText().toString();
			bilCity = bil_City.getText().toString();
			bilPostal = bil_Postal.getText().toString();
			bilPhone = bil_Phone.getText().toString();
			Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setOneClick(token_id);
			ver.setBillingInfo(bilFirst, bilLast, bilAddress, bilCity,
					bilPostal, bilPhone, "IDN");
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
	
	private class TwoClicksListener implements OnClickListener {

		public TwoClicksListener() {

		}

		public void onClick(View v) {
			bilFirst = bil_First.getText().toString();
			bilLast = bil_Last.getText().toString();
			bilAddress = bil_Address.getText().toString();
			bilCity = bil_City.getText().toString();
			bilPostal = bil_Postal.getText().toString();
			bilPhone = bil_Phone.getText().toString();
			Fragment fr = new Fragment();
			fr = new FragmentTwoClicks();
			final Bundle bundle = new Bundle();
    		bundle.putString("masked", masked);
    		bundle.putString("token_id", token_id);
    		fr.setArguments(bundle);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragTrans = fm.beginTransaction();
			fragTrans.addToBackStack("to Checkout");
			fragTrans.replace(R.id.frame_container, fr, "CreditCard");
			fragTrans.commit();
		}

	}

}
