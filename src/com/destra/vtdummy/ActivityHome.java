package com.destra.vtdummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.destra.vtdummy.adapter.NavDrawerListAdapter;
import com.destra.vtdummy.model.NavDrawerItem;
import com.destra.vtdummy.model.ShoppingCartItem;
import com.destra.vtdummy.model.VeritransObject;

public class ActivityHome extends Activity implements
		VeritransObject.ResponseListener {
	public static Object fragmentTag;
	public static final String server_key = "NmQ3Y2NkNzEtZWE1Mi00M2NjLWFjNDItNTQwMjA3N2JkNmM2Og==";
	public static final String client_key = "bb558bb6-371a-415b-b7ee-bf285f33868f";
	/* Billing Information */
	public String billingFirst;
	public String billingLast;
	public String billingAddress;
	public String billingCity;
	public String billingPostal;
	public String billingPhone;

	/* Shipping Information */
	public String shippingFirst;
	public String shippingLast;
	public String shippingAddress;
	public String shippingCity;
	public String shippingPostal;
	public String shippingPhone;
	public static Activity home;
	private static ArrayList<ShoppingCartItem> cart = new ArrayList<ShoppingCartItem>();

	public DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	public ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    // hide statusbar of Android
	    // could also be done later
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home);
		home = this;
		mTitle = mDrawerTitle = getTitle();
		// if(FragmentVTWeb.visited) {
		// Toast.makeText(this, "masuk", Toast.LENGTH_SHORT).show();
		// FragmentVTWeb.a.finish();
		// }
		billingFirst = "";
		billingLast = "";
		billingAddress = "";
		billingCity = "";
		billingPostal = "";
		billingPhone = "";
		shippingFirst = "";
		shippingLast = "";
		shippingAddress = "";
		shippingCity = "";
		shippingPostal = "";
		shippingPhone = "";
		// load slide menu items
		fragmentTag = "None";
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array

		// Home
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
		// .getResourceId(0, -1)));
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
		// .getResourceId(1, -1)));
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
		// .getResourceId(2, -1)));
		// Find People
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav
				// menu
				// toggle
				// icon
				R.string.app_name, // nav drawer open - description for
				// accessibility
				R.string.app_name // nav drawer close - description for
		// accessibility
		) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		Fragment fr = new Fragment();
		fr = new FragmentHome();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragTrans = fm.beginTransaction();
		fragTrans.replace(R.id.frame_container, fr);
		fragTrans.commit();
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			// displayView(0);
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			// displayView(position);

			// Siapin Activity atau Fragment buat tombol waktu diklik
			Fragment fr = new Fragment();

			if (position == 0) { // Beranda
				fr = new FragmentHome();
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.replace(R.id.frame_container, fr);
				fragTrans.commit();
			} else if (position == 1) { // Beranda
				fr = new FragmentBilling();
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.replace(R.id.frame_container, fr);
				fragTrans.commit();
			} else if (position == 2) { // Beranda
				fr = new FragmentShipping();
				FragmentManager fm = getFragmentManager();
				FragmentTransaction fragTrans = fm.beginTransaction();
				fragTrans.replace(R.id.frame_container, fr);
				fragTrans.commit();
			}

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		// case R.id.action_settings:
		// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void setBillingInfo(String bF, String bL, String bA, String bC,
			String bZ, String bP) {
		billingFirst = bF;
		billingLast = bL;
		billingAddress = bA;
		billingCity = bC;
		billingPostal = bZ;
		billingPhone = bP;
	}

	public void setShippingInfo(String bF, String bL, String bA, String bC,
			String bZ, String bP) {
		shippingFirst = bF;
		shippingLast = bL;
		shippingAddress = bA;
		shippingCity = bC;
		shippingPostal = bZ;
		shippingPhone = bP;
	}

	public boolean issetBillingInfo() {
		if (billingFirst.equals("") && billingLast.equals("")
				&& billingAddress.equals("") && billingCity.equals("")
				&& billingPostal.equals("") && billingPhone.equals(""))
			return false;
		else
			return true;
	}

	public boolean issetShippingInfo() {
		if (shippingFirst.equals("") && shippingLast.equals("")
				&& shippingAddress.equals("") && shippingCity.equals("")
				&& shippingPostal.equals("") && shippingPhone.equals(""))
			return false;
		else
			return true;
	}

	public static ArrayList<ShoppingCartItem> getCart() {
		return cart;
	}

	public void resetShoppingCart() {
		cart = new ArrayList<ShoppingCartItem>();
	}

	@Override
	public void onBackPressed() {
		// FragmentManager fm = getFragmentManager();
		// Fragment f = fm.findFragmentById(R.id.frame_container);
		// fragmentTag = f.getTag();
		// Log.d("Tag",""+fragmentTag);
		// if(fragmentTag.equals("OrderSummary")){
		// this.finish();
		// }
		// do handling with help of tag here
		// call super method
		super.onBackPressed();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		fragmentTag = "None";
		Fragment fr = new Fragment();
		fr = new FragmentHome();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragTrans = fm.beginTransaction();
		fragTrans.replace(R.id.frame_container, fr);
		fragTrans.commit();
		// getIntent() should always return the most recent
		setIntent(intent);
	}

	@Override
	public void onResponseRecieved(String response) {
		// update ui using result
		// Toast.makeText(this, response, Toast.LENGTH_LONG).show();
		try {
			JSONObject jsonResponse = new JSONObject(response);
			String status_code = jsonResponse.getString("status_code");
			if ((status_code.equals("200")) || (status_code.equals("201"))) {
				if (jsonResponse.has("redirect_url")) {
					Intent i = new Intent(this, FragmentVTWeb.class);
					i.putExtra("url", jsonResponse.getString("redirect_url"));
					if (jsonResponse.has("payment_type")) {
						i.putExtra("dari",
								jsonResponse.getString("payment_type"));
					} else {
						i.putExtra("dari", "vtweb");
					}
					startActivity(i);
				} else {
					if ((jsonResponse.getString("payment_type")
							.equals(VeritransObject.CREDIT_CARD))
							&& (jsonResponse.has("saved_token_id"))) {
						SaveToken save = new SaveToken(this);
						String masked = jsonResponse.getString("masked_card");
						String split[] = masked.split("-");
						String firstChunk = split[0].substring(0, 4);
						String secondChunk = split[0].substring(4, 6);
						masked = firstChunk + "-" + secondChunk + "**-****-"
								+ split[1];
						save.execute(
								"http://vt-dummy-store.ap01.aws.af.cm/android_connection.php",
								jsonResponse.getString("saved_token_id"),
								masked);
					}
					Fragment fr = new Fragment();
					fr = new FragmentOrderSummary();
					final Bundle bundle = new Bundle();
					bundle.putString("status", response);
					fr.setArguments(bundle);
					FragmentManager fm = getFragmentManager();
					FragmentTransaction fragTrans = fm.beginTransaction();
					fragTrans.replace(R.id.frame_container, fr, "OrderSummary");
					fragTrans.commit();
				}
			} else {
				Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class SaveToken extends AsyncTask<String, Void, Void> {
		private String Content;
		Context context;

		public SaveToken(Context context) {
			this.context = context;
		}

		protected Void doInBackground(String... urls) {

			try {
				String url = urls[0];
				String token = urls[1];
				String masked_card = urls[2];
				HttpPost httpRequest = new HttpPost(URI.create(url));
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						3);
				nameValuePairs.add(new BasicNameValuePair("email",
						"destra.bintang@veritrans.co.id"));
				nameValuePairs.add(new BasicNameValuePair("saved_token_id",
						token));
				nameValuePairs.add(new BasicNameValuePair("masked_card",
						masked_card));
				httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(
						entity);
				// Get the server response

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(bufHttpEntity.getContent()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}

				// Append Server Response To Content String
				Content = sb.toString();
				// You can perform UI operations here

			} catch (IOException e) {
				// Error
			}
			return null;
		}

		protected void onPostExecute(Void feed) {
			Toast.makeText(context, Content, Toast.LENGTH_SHORT).show();
		}

	}
}
