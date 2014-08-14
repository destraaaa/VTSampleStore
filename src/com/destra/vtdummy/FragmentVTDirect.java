package com.destra.vtdummy;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.destra.vtdummy.FragmentProduct.CheckEmail;
import com.destra.vtdummy.adapter.PaymentAdapter;
import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.PaymentItem;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentVTDirect extends Fragment {
	static ListView l, listPayment;
	public static ImageView credit_card, cimb_clicks, mandiri_clickpay, t_cash,
			xl_tunai, permata, bii;
	public static TextView gross, grandTotal;
	static String token_id, masked;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_choose_method, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		listPayment = (ListView) v.findViewById(R.id.list_payment);
		credit_card = (ImageView) v.findViewById(R.id.credit_card);
		credit_card.setOnClickListener(new CreditCardListener());
		cimb_clicks = (ImageView) v.findViewById(R.id.cimb_clicks);
		cimb_clicks.setOnClickListener(new CimbClicksListener());
		mandiri_clickpay = (ImageView) v.findViewById(R.id.mandiri_clickpay);
		mandiri_clickpay.setOnClickListener(new MandiriClickpayListener());
		t_cash = (ImageView) v.findViewById(R.id.t_cash);
		t_cash.setOnClickListener(new TcashListener());
		xl_tunai = (ImageView) v.findViewById(R.id.xl_tunai);
		xl_tunai.setOnClickListener(new XlTunaiListener());
		permata = (ImageView) v.findViewById(R.id.permata);
		permata.setOnClickListener(new PermataListener());
		bii = (ImageView) v.findViewById(R.id.bii);
		bii.setOnClickListener(new BiiListener());
		ArrayList<PaymentItem> pays = new ArrayList<PaymentItem>();
		pays.add(new PaymentItem(R.drawable.credit_card, "Credit Card"));
		pays.add(new PaymentItem(R.drawable.cimb_clicks, "CIMB Clicks"));
		pays.add(new PaymentItem(R.drawable.mandiri_clickpay,
				"Mandiri Clickpay"));
		pays.add(new PaymentItem(R.drawable.t_cash, "Telkomsel Cash"));
		pays.add(new PaymentItem(R.drawable.xltunai_logo, "XL Tunai"));
		pays.add(new PaymentItem(R.drawable.logo_permata, "Permata"));
		pays.add(new PaymentItem(R.drawable.bii_logo, "BII"));
		gross = (TextView) v.findViewById(R.id.gross_amount);
		grandTotal = (TextView) v.findViewById(R.id.grandTotal);
		gross.setText("" + FragmentHome.gross.getText());
		grandTotal.setText("" + FragmentHome.gross.getText());
//		getActivity().getActionBar().setTitle("Choose Payment Method");
//		// calling onPrepareOptionsMenu() to hide action bar icons
//		getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(), ((ActivityHome) getActivity()).getCart());
		PaymentAdapter ad = new PaymentAdapter(getActivity(), pays);
		l.setAdapter(adapter);
		listPayment.setAdapter(ad);
		listPayment.setOnItemClickListener(new PaymentClickListener());
		return v;
	}


	private class PaymentClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 0) {
				Fragment fr = new Fragment();
				fr = new FragmentBilling();
				Bundle args = getArguments();
				if (args  != null && args.containsKey("content")){
					String content = args.getString("content");
					String split[] = content.split(" ");
					token_id = split[0];
					if (!token_id.equals("false")){
						masked = split[1];
						final Bundle bundle = new Bundle();
			    		bundle.putString("masked", masked);
			    		bundle.putString("token_id", token_id);
			    		fr.setArguments(bundle);
					}
				}
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.addToBackStack("to Checkout");
				fragTrans.replace(R.id.frame_container, fr, "Billing");
				fragTrans.commit();
			} else if (position == 1) {  
				Time now = new Time();
				now.setToNow();
				String[] splitted = now.toString().split("GMT");
				VeritransObject ver = new VeritransObject(getActivity(),
						splitted[0], ActivityHome.client_key,
						ActivityHome.server_key);
				ver.setCimbClicks("Contoh Deskripsi");
				ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
				ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
			} else if (position == 2) {  
				Fragment fr = new Fragment();
				fr = new FragmentMandiriClickpay();
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.addToBackStack("to Checkout");
				fragTrans.replace(R.id.frame_container, fr, "MandiriClickpay");
				fragTrans.commit();
			} else if (position == 3){
				Fragment fr = new Fragment();
				fr = new FragmentTCash();
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.addToBackStack("to Checkout");
				fragTrans.replace(R.id.frame_container, fr, "TelkomselCash");
				fragTrans.commit();
			} else if (position == 4){
				Time now = new Time();
				now.setToNow();
				String[] splitted = now.toString().split("GMT");
				VeritransObject ver = new VeritransObject(getActivity(),
						splitted[0], ActivityHome.client_key,
						ActivityHome.server_key);
				ver.setXlTunai();
				ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
				ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
			} else if (position == 5){
				Time now = new Time();
				now.setToNow();
				String[] splitted = now.toString().split("GMT");
				VeritransObject ver = new VeritransObject(getActivity(),
						splitted[0], ActivityHome.client_key,
						ActivityHome.server_key);
				ver.setPermata();
				ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
				ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
			} else if (position == 6){
				Time now = new Time();
				now.setToNow();
				String[] splitted = now.toString().split("GMT");
				VeritransObject ver = new VeritransObject(getActivity(),
						splitted[0], ActivityHome.client_key,
						ActivityHome.server_key);
				ver.setBii();
				ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
				ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
//			} else if (position == 7){
//				Time now = new Time();
//				now.setToNow();
//				String[] splitted = now.toString().split("GMT");
//				VeritransObject ver = new VeritransObject(getActivity(),
//						splitted[0], ActivityHome.client_key,
//						ActivityHome.server_key);
//				ver.setOneClick(token_id);
//				ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
//				ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
//				
//			} else if (position == 8){
//				Fragment fr = new Fragment();
//				fr = new FragmentTwoClicks();
//				final Bundle bundle = new Bundle();
//	    		bundle.putString("masked", masked);
//	    		bundle.putString("token_id", token_id);
//	    		fr.setArguments(bundle);
//				FragmentManager fm = getFragmentManager();
//				FragmentTransaction fragTrans = fm.beginTransaction();
//				fragTrans.addToBackStack("to Checkout");
//				fragTrans.replace(R.id.frame_container, fr, "CreditCard");
//				fragTrans.commit();
				
			} 
		}
	}
	
    private class CreditCardListener implements OnClickListener {
    	public CreditCardListener(){
    		
    	}

        public void onClick(View v) {
        	Fragment fr = new Fragment();
			fr = new FragmentBilling();
			Bundle args = getArguments();
			if (args  != null && args.containsKey("content")){
				String content = args.getString("content");
				String split[] = content.split(" ");
				token_id = split[0];
				if (!token_id.equals("false")){
					masked = split[1];
					final Bundle bundle = new Bundle();
		    		bundle.putString("masked", masked);
		    		bundle.putString("token_id", token_id);
		    		fr.setArguments(bundle);
				}
			}
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragTrans = fm.beginTransaction();
			fragTrans.addToBackStack("to Checkout");
			fragTrans.replace(R.id.frame_container, fr, "Billing");
			fragTrans.commit();
        }

     }
    
    private class CimbClicksListener implements OnClickListener {
    	public CimbClicksListener(){
    		
    	}

        public void onClick(View v) {
        	Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setCimbClicks("Contoh Deskripsi");
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
        }

     }
    
    private class MandiriClickpayListener implements OnClickListener {
    	public MandiriClickpayListener(){
    		
    	}

        public void onClick(View v) {
        	Fragment fr = new Fragment();
			fr = new FragmentMandiriClickpay();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragTrans = fm.beginTransaction();
			fragTrans.addToBackStack("to Checkout");
			fragTrans.replace(R.id.frame_container, fr, "MandiriClickpay");
			fragTrans.commit();
        }
     }
    
    private class TcashListener implements OnClickListener {
    	public TcashListener(){
    		
    	}

        public void onClick(View v) {
        	Fragment fr = new Fragment();
			fr = new FragmentTCash();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragTrans = fm.beginTransaction();
			fragTrans.addToBackStack("to Checkout");
			fragTrans.replace(R.id.frame_container, fr, "TelkomselCash");
        }
     }
    
    private class XlTunaiListener implements OnClickListener {
    	public XlTunaiListener(){
    		
    	}

        public void onClick(View v) {
        	Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setXlTunai();
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
        }
     }
    
    private class PermataListener implements OnClickListener {
    	public PermataListener(){
    		
    	}

        public void onClick(View v) {
        	Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setPermata();
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
        }
     }
    
    private class BiiListener implements OnClickListener {
    	public BiiListener(){
    		
    	}

        public void onClick(View v) {
        	Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setBii();
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
        }
     }

}
