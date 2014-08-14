package com.destra.vtdummy;

import android.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentCreditCard extends Fragment{
	static ListView l;
	public static Button pay;
	public static TextView gross;
	public static boolean saveToken;
	CheckBox chkRemember;
	EditText cc_Number, name_Card, exp_Month, exp_Year, cvv_Number;
	String ccNumber, nameCard, expMonth, expYear, cvvNumber;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_credit_card, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		pay = (Button) v.findViewById(R.id.pay);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		cc_Number = (EditText) v.findViewById(R.id.cc_Number);
		name_Card = (EditText) v.findViewById(R.id.name_Card);
		exp_Month = (EditText) v.findViewById(R.id.exp_Month);
		exp_Year = (EditText) v.findViewById(R.id.exp_Year);
		cvv_Number = (EditText) v.findViewById(R.id.cvv_Number);
		gross.setText("" + FragmentHome.gross.getText());
		//getActivity().getActionBar().setTitle("CC Information");
		// calling onPrepareOptionsMenu() to hide action bar icons
		//getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		pay.setOnClickListener(new PayButtonListener());
		chkRemember = (CheckBox) v.findViewById(R.id.chkRemember);
		 
		chkRemember.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
	                //is chkIos checked?
			if (((CheckBox) v).isChecked()) {
				saveToken = true;
			}
			else{
				saveToken = false;
			}
	 
		  }
		});
		saveToken = false;
		return v;
	}
	

	private class PayButtonListener implements OnClickListener {

		public PayButtonListener() {

		}
		@Override
		public void onClick(View v) {
			// Toast.makeText(context, nearbyItems.get(position).getNama(),
			// Toast.LENGTH_SHORT).show();
			ccNumber = cc_Number.getText().toString();
			expMonth = exp_Month.getText().toString();
			expYear = exp_Year.getText().toString();
			cvvNumber = cvv_Number.getText().toString();
			Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			if(Integer.parseInt(gross.getText().toString())>1000000){
				ver.setCreditCard(ccNumber, expMonth, expYear, cvvNumber, "bni", VeritransObject.SECURE_TRUE, saveToken);
			}
			else{
				if(saveToken == true){
					ver.setCreditCard(ccNumber, expMonth, expYear, cvvNumber, "bni", VeritransObject.SECURE_TRUE, saveToken);
				}
				else{
					ver.setCreditCard(ccNumber, expMonth, expYear, cvvNumber, "bni", VeritransObject.SECURE_FALSE, saveToken);
				}
			}
			ver.setBillingInfo(FragmentBilling.bilFirst, FragmentBilling.bilLast, FragmentBilling.bilAddress, FragmentBilling.bilCity,
					FragmentBilling.bilPostal, FragmentBilling.bilPhone, "IDN");
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

}
