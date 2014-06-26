package com.destra.vtdummy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentShipping extends Fragment {
	static ListView l;
	EditText ship_First, ship_Last, ship_Address, ship_City, ship_Postal, ship_Phone;
	Button saveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_shipping, container, false);
		ship_First = (EditText) v.findViewById(R.id.ship_First);
		ship_Last = (EditText) v.findViewById(R.id.ship_Last);
		ship_Address = (EditText) v.findViewById(R.id.ship_Address);
		ship_City = (EditText) v.findViewById(R.id.ship_City);
		ship_Postal = (EditText) v.findViewById(R.id.ship_Post);
		ship_Phone = (EditText) v.findViewById(R.id.ship_Phone);
		saveButton = (Button) v.findViewById(R.id.save_ship);
		saveButton.setOnClickListener(new SaveButtonListener());
		if(((ActivityHome)getActivity()).issetShippingInfo()){
			String bF = ((ActivityHome)getActivity()).shippingFirst;
			String bL = ((ActivityHome)getActivity()).shippingLast;
			String bA = ((ActivityHome)getActivity()).shippingAddress;
			String bC = ((ActivityHome)getActivity()).shippingCity;
			String bZ = ((ActivityHome)getActivity()).shippingPostal;
			String bP = ((ActivityHome)getActivity()).shippingPhone;
			setBilInfo(bF, bL, bA, bC, bZ, bP);
		}
		return v;
	}
	
	public void saveButtonHandler(View v){
		((ActivityHome) getActivity()).setShippingInfo(ship_First.getText().toString(), ship_Last.getText().toString(), ship_Address.getText().toString(), ship_City.getText().toString(), ship_Postal.getText().toString(), ship_Phone.getText().toString());
	}
	
	public void setBilInfo(String bF, String bL, String bA, String bC, String bZ, String bP){
		ship_First.setText(bF);
		ship_Last.setText(bL);
		ship_Address.setText(bA);
		ship_City.setText(bC);
		ship_Postal.setText(bZ);
		ship_Phone.setText(bP);
	}
	
    private class SaveButtonListener implements OnClickListener {

        public SaveButtonListener() {
        }

        public void onClick(View v) {
        	((ActivityHome) getActivity()).setShippingInfo(ship_First.getText().toString(), ship_Last.getText().toString(), ship_Address.getText().toString(), ship_City.getText().toString(), ship_Postal.getText().toString(), ship_Phone.getText().toString());
        	Toast.makeText(getActivity(), "Shipping Information saved", Toast.LENGTH_SHORT).show();
        }

     }

}
