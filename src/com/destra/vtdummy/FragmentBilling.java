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

public class FragmentBilling extends Fragment {
	static ListView l;
	EditText bil_First, bil_Last, bil_Address, bil_City, bil_Postal, bil_Phone;
	Button saveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_billing, container, false);
		bil_First = (EditText) v.findViewById(R.id.bil_First);
		bil_Last = (EditText) v.findViewById(R.id.bil_Last);
		bil_Address = (EditText) v.findViewById(R.id.bil_Address);
		bil_City = (EditText) v.findViewById(R.id.bil_City);
		bil_Postal = (EditText) v.findViewById(R.id.bil_Post);
		bil_Phone = (EditText) v.findViewById(R.id.bil_Phone);
		saveButton = (Button) v.findViewById(R.id.save_bil);
		saveButton.setOnClickListener(new SaveButtonListener());
		if(((ActivityHome)getActivity()).issetBillingInfo()){
			String bF = ((ActivityHome)getActivity()).billingFirst;
			String bL = ((ActivityHome)getActivity()).billingLast;
			String bA = ((ActivityHome)getActivity()).billingAddress;
			String bC = ((ActivityHome)getActivity()).billingCity;
			String bZ = ((ActivityHome)getActivity()).billingPostal;
			String bP = ((ActivityHome)getActivity()).billingPhone;
			setBilInfo(bF, bL, bA, bC, bZ, bP);
		}
		return v;
	}
	
	public void saveButtonHandler(View v){
		((ActivityHome) getActivity()).setBillingInfo(bil_First.getText().toString(), bil_Last.getText().toString(), bil_Address.getText().toString(), bil_City.getText().toString(), bil_Postal.getText().toString(), bil_Phone.getText().toString());
	}
	
	public void setBilInfo(String bF, String bL, String bA, String bC, String bZ, String bP){
		bil_First.setText(bF);
		bil_Last.setText(bL);
		bil_Address.setText(bA);
		bil_City.setText(bC);
		bil_Postal.setText(bZ);
		bil_Phone.setText(bP);
	}
	
    private class SaveButtonListener implements OnClickListener {

        public SaveButtonListener() {
        }

        public void onClick(View v) {
        	((ActivityHome) getActivity()).setBillingInfo(bil_First.getText().toString(), bil_Last.getText().toString(), bil_Address.getText().toString(), bil_City.getText().toString(), bil_Postal.getText().toString(), bil_Phone.getText().toString());
        	Toast.makeText(getActivity(), "Billing Information saved", Toast.LENGTH_SHORT).show();
        }

     }

}
