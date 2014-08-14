package com.destra.vtdummy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.destra.vtdummy.R;
import com.destra.vtdummy.model.ShoppingCartItem;

public class ShoppingCartAdapter extends BaseAdapter {
	private ViewHolder holder;
    private LayoutInflater inflater;
    private ArrayList<ShoppingCartItem> cart;
    Context context;
    
    private class ViewHolder {
    	LinearLayout lin;
		TextView namaView;
		TextView hargaView;
		TextView quantityView;
		TextView totalView;
    }

    public ShoppingCartAdapter(Context context, ArrayList<ShoppingCartItem> cart) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.cart = new ArrayList<ShoppingCartItem>();
		for (int i = 0; i < cart.size(); i++){
			if(cart.get(i).getQuantity() != 0){
				this.cart.add(cart.get(i));
			}
		}
    }

    @Override
    public int getCount() {
		return cart.size();
	}

    @Override
    public ShoppingCartItem getItem(int position) {
		return cart.get(position);
	}

    @Override
    public long getItemId(int position) {
		return position;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
		    holder = new ViewHolder();
		    convertView = inflater.inflate(R.layout.item_list_cart, null);
		    holder.lin = (LinearLayout) convertView.findViewById(R.id.item_list_cart);
		    holder.namaView = (TextView) convertView.findViewById(R.id.nama);
		    holder.hargaView = (TextView) convertView.findViewById(R.id.harga);
		    holder.quantityView = (TextView) convertView.findViewById(R.id.quantity);
		    holder.totalView = (TextView) convertView.findViewById(R.id.total);
		    convertView.setTag(holder);
		}
		if(holder!=null){
			if(position % 2 == 0){
				holder.lin.setBackgroundColor(Color.parseColor("#edfbff"));
			}
			else{
				holder.lin.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			holder.namaView.setText(cart.get(position).getItem().getNama());
			holder.hargaView.setText(cart.get(position).getItem().getHarga().toString());
			holder.quantityView.setText(""+cart.get(position).getQuantity());
			holder.totalView.setText(""+cart.get(position).getItem().getHarga()*cart.get(position).getQuantity());
		}
		return convertView;
    }

}
