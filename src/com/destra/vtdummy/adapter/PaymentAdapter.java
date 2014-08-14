package com.destra.vtdummy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.R;
import com.destra.vtdummy.model.PaymentItem;

public class PaymentAdapter extends BaseAdapter {
	private ViewHolder holder;
    private LayoutInflater inflater;
    private ArrayList<PaymentItem> payItems;
    Context context;

    private class ViewHolder {
		ImageView gambarView;
		TextView namaView;
    }

    public PaymentAdapter(Context context, ArrayList<PaymentItem> payItems) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.payItems = payItems;
    }
    
    public PaymentAdapter(Context context, ArrayList<PaymentItem> payItems, int gross) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.payItems = payItems;
    }

    @Override
    public int getCount() {
		return payItems.size();
	}

    @Override
    public PaymentItem getItem(int position) {
		return payItems.get(position);
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
		    convertView = inflater.inflate(R.layout.item_list_payment, null);
		    holder.gambarView = (ImageView) convertView.findViewById(R.id.gambar);
		    holder.namaView = (TextView) convertView.findViewById(R.id.nama);
		    convertView.setTag(holder);
		}
		if(holder!=null){
			//Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
			holder.gambarView.setImageResource(payItems.get(position).getGambar());
			holder.namaView.setText(payItems.get(position).getNama());
		}
		return convertView;
    }

}
