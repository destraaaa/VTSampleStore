package com.destra.vtdummy.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.destra.vtdummy.ActivityHome;
import com.destra.vtdummy.FragmentHome;
import com.destra.vtdummy.R;
import com.destra.vtdummy.model.Item;
import com.destra.vtdummy.model.ShoppingCartItem;

public class ItemAdapter extends BaseAdapter {
	private ViewHolder holder;
    private LayoutInflater inflater;
    private ArrayList<Item> shoppingItems;
    Context context;
    private static int grossAmount;

    private class ViewHolder {
		ImageView gambarView;
		LinearLayout list_item;
		TextView namaView;
		TextView hargaView;
		Button btnList;
    }

    public ItemAdapter(Context context, ArrayList<Item> shopItems) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.shoppingItems = shopItems;
		grossAmount = 0;
    }
    
    public ItemAdapter(Context context, ArrayList<Item> shopItems, int gross) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.shoppingItems = shopItems;
		grossAmount = gross;
    	FragmentHome.checkout.setVisibility(View.VISIBLE);;
    }

    @Override
    public int getCount() {
		return shoppingItems.size();
	}

    @Override
    public Item getItem(int position) {
		return shoppingItems.get(position);
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
		    convertView = inflater.inflate(R.layout.item_list_shop, null);
		    holder.list_item = (LinearLayout) convertView.findViewById(R.id.list_item);
		    holder.gambarView = (ImageView) convertView.findViewById(R.id.gambar);
		    holder.namaView = (TextView) convertView.findViewById(R.id.nama);
		    holder.hargaView = (TextView) convertView.findViewById(R.id.harga);
		    holder.btnList = (Button) convertView.findViewById(R.id.buy);
		    convertView.setTag(holder);
		}
		if(holder!=null){
			if(position % 2 == 0){
				holder.list_item.setBackgroundColor(Color.parseColor("#edfbff"));
			}
			else{
				holder.list_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			holder.gambarView.setImageResource(shoppingItems.get(position).getGambar());
			holder.namaView.setText(shoppingItems.get(position).getNama());
			holder.hargaView.setText(shoppingItems.get(position).getHarga().toString());
			holder.btnList.setOnClickListener(new ItemClickListener(position));
		}
		return convertView;
    }
    
    public static int getGrossAmount(){
    	return grossAmount;
    }
    
    private class ItemClickListener implements OnClickListener {

        private int position;

        public ItemClickListener(int position) {
           this.position = position;
        }

        public void onClick(View v) {
        	//Toast.makeText(context, nearbyItems.get(position).getNama(), Toast.LENGTH_SHORT).show();
        	//RetrieveRedirectURL p = new RetrieveRedirectURL(context);
        	if(grossAmount == 0){
        		FragmentHome.checkout.setVisibility(View.VISIBLE);
        	}
        	ActivityHome.getCart().get(position).setQuantity(ActivityHome.getCart().get(position).getQuantity()+1);
        	grossAmount += shoppingItems.get(position).getHarga();
        	FragmentHome.gross.setText(""+ItemAdapter.getGrossAmount());
        	//p.execute("http://vt-dummy-store.ap01.aws.af.cm/checkout_process2.php", shoppingItems.get(position).getNama(), shoppingItems.get(position).getHarga().toString(), "1");
        }

        public int getPosition() {
          return position;
        }

     }
}
