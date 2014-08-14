package com.destra.vtdummy;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.destra.vtdummy.adapter.ShoppingCartAdapter;
import com.destra.vtdummy.model.VeritransObject;

public class FragmentMandiriClickpay extends Fragment {
	static ListView l;
	public static Button pay;
	public static TextView gross;
	public static EditText card_number, token_;
	public static TextView last_ten, amount_, token_req;
	public static String cardNumber, lastTen, token;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_mandiri_clickpay, container,
				false);
		l = (ListView) v.findViewById(R.id.list_bought);
		pay = (Button) v.findViewById(R.id.pay);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		card_number = (EditText) v.findViewById(R.id.card_Number);
		last_ten = (TextView) v.findViewById(R.id.last_Ten);
		amount_ = (TextView) v.findViewById(R.id.amount_);
		token_req = (TextView) v.findViewById(R.id.token_req);
		token_ = (EditText) v.findViewById(R.id.token_);
		gross.setText("" + FragmentHome.gross.getText());
		getActivity().getActionBar().setTitle("Mandiri Clickpay");
		getActivity().invalidateOptionsMenu();
		ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(),
				((ActivityHome) getActivity()).getCart());
		l.setAdapter(adapter);
		pay.setOnClickListener(new PayButtonListener());
		TextWatcher textWatcher = new TextWatcher() {
		    @Override
			public void afterTextChanged(Editable s) {
		    	if(s.length() <= 10){
		    		last_ten.setText(s);
		    	}else{
		    		last_ten.setText(s.subSequence(s.length()-10, s.length()));
		    	}
		    }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		};
		card_number.addTextChangedListener(textWatcher);
		amount_.setText(gross.getText());
		return v;
	}

	private class PayButtonListener implements OnClickListener {

		public PayButtonListener() {

		}

		public void onClick(View v) {
			// Toast.makeText(context, nearbyItems.get(position).getNama(),
			// Toast.LENGTH_SHORT).show();
			cardNumber = card_number.getText().toString();
			lastTen = last_ten.getText().toString();
			token = token_.getText().toString();
			Time now = new Time();
			now.setToNow();
			String[] splitted = now.toString().split("GMT");
			VeritransObject ver = new VeritransObject(getActivity(),
					splitted[0], ActivityHome.client_key,
					ActivityHome.server_key);
			ver.setMandiriClickpay(cardNumber, lastTen, amount_.getText().toString(), token_req.getText().toString(), token);
			ver.setCustomerDetails("Destra", "Bintang Perkasa", "destra.bintang@veritrans.co.id", "085715516893");
			ver.pay(((ActivityHome) getActivity()).getCart(), gross.getText().toString());
		}

	}
}
