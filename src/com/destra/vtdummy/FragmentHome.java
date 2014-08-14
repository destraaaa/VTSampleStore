package com.destra.vtdummy;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.ItemAdapter;
import com.destra.vtdummy.model.Item;
import com.destra.vtdummy.model.ShoppingCartItem;

public class FragmentHome extends Fragment {
	static ListView l;
	public static TextView gross;
	public static Button checkout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_item_shop, container, false);
		((ActivityHome)getActivity()).fragmentTag = "None";
		l = (ListView) v.findViewById(R.id.list_nearby);
		gross = (TextView) v.findViewById(R.id.gross_amount);
		checkout = (Button) v.findViewById(R.id.checkout);
		l.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		        Toast.makeText(getActivity(), "Stop Clicking me", Toast.LENGTH_SHORT).show();
		    }
		});

		// Use AsyncTask execute Method To Prevent ANR Problem
		// Android always uses AsyncTask to thread internet connection
		// alongside other operation.
		ArrayList<Item> items = new ArrayList<Item>();
		ItemAdapter adapter;
		items.add(new Item(1, R.drawable.motor1, "Ducati Corse", 500000));
		items.add(new Item(2, R.drawable.motor2, "Nicky Hayden", 300000));
		items.add(new Item(3, R.drawable.motor3, "Mach I", 200000));
		if (((ActivityHome)getActivity()).getCart().size() != 0){
			if((((ActivityHome)getActivity()).fragmentTag.equals("Checkout"))||(((ActivityHome)getActivity()).fragmentTag.equals("backToMerchant"))){
				gross.setText(FragmentProduct.gross.getText());
				adapter = new ItemAdapter(getActivity(),
						items, Integer.parseInt((String)gross.getText()));
			}else{
				((ActivityHome)getActivity()).resetShoppingCart();
				for(int i = 0; i<items.size(); i++){
					((ActivityHome)getActivity()).getCart().add(new ShoppingCartItem(items.get(i), 0));
				}
				adapter = new ItemAdapter(getActivity(),
						items);
			}
		}
		else{
			for(int i = 0; i<items.size(); i++){
				((ActivityHome)getActivity()).getCart().add(new ShoppingCartItem(items.get(i), 0));
			}
			adapter = new ItemAdapter(getActivity(),
					items);
		}
		FragmentHome.l.setAdapter(adapter);
		checkout.setOnClickListener(new CheckoutButtonListener());
		return v;
	}
	
    private class CheckoutButtonListener implements OnClickListener {

        public CheckoutButtonListener() {
        }

        public void onClick(View v) {
        	Fragment fr = new Fragment();
    		fr = new FragmentProduct();
    		FragmentManager fm = getFragmentManager();
    		FragmentTransaction fragTrans = fm.beginTransaction();
    		fragTrans.addToBackStack("to Home");
    		fragTrans.replace(R.id.frame_container, fr, "Checkout");
    		fragTrans.commit();
        }

     }
}
