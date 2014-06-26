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
		    holder.gambarView = (ImageView) convertView.findViewById(R.id.gambar);
		    holder.namaView = (TextView) convertView.findViewById(R.id.nama);
		    holder.hargaView = (TextView) convertView.findViewById(R.id.harga);
		    holder.btnList = (Button) convertView.findViewById(R.id.buy);
		    convertView.setTag(holder);
		}
		if(holder!=null){
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
        		FragmentHome.checkout.setVisibility(View.VISIBLE);;
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
    
    class RetrieveRedirectURL extends AsyncTask<String, Void, Void> {
		private String Content;
		Context context;
		public RetrieveRedirectURL(Context context) {
	        this.context = context;
	    } 
	    protected Void doInBackground(String... urls) {
	    	HttpURLConnection connection;
	        OutputStreamWriter request = null;

	             URL url = null;   
	             String response = null;         
	             String parameters = "id1=1&name1="+urls[1]+"&price1="+urls[2]+"&quantity1="+urls[3];

	             try
	             {
	                 url = new URL(urls[0]);
	                 connection = (HttpURLConnection) url.openConnection();
	                 connection.setDoOutput(true);
	                 connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	                 connection.setRequestMethod("POST");    

	                 request = new OutputStreamWriter(connection.getOutputStream());
	                 request.write(parameters);
	                 request.flush();
	                 request.close();            
	                 String line = "";               
	                 InputStreamReader isr = new InputStreamReader(connection.getInputStream());
	                 BufferedReader reader = new BufferedReader(isr);
	                 StringBuilder sb = new StringBuilder();
	                 while ((line = reader.readLine()) != null)
	                 {
	                     sb.append(line + "");
	                 }
	                 // Response from server after login process will be stored in response variable.                
	                 Content = sb.toString();
	                 // You can perform UI operations here
	                 isr.close();
	                 reader.close();

	             }
	             catch(IOException e)
	             {
	                 // Error
	             }
	        return null;
	    }

	    protected void onPostExecute(Void feed) {
	    	//Toast.makeText(context ,"Message from Server: \n"+ Content, 0).show();
	    	Intent i = new Intent(Intent.ACTION_VIEW, Uri
					.parse(Content));
	    	i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    	this.context.startActivity(i);
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }
	}


}
