package com.destra.vtdummy.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.Time;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.destra.vtdummy.ActivityHome;
import com.destra.vtdummy.FragmentProduct;
import com.destra.vtdummy.FragmentTesting;
import com.destra.vtdummy.FragmentVTWeb;

public class VeritransObject {
	private JSONObject jsonResponse;
	private String response;
	public static final String CREDIT_CARD = "credit_card";
	public static final String CIMB_CLICKS = "cimb_clicks";
	public static final String MANDIRI_CLICKPAY = "mandiri_clickpay";
	public static final String T_CASH = "telkomsel_cash";
	public static final String XL_TUNAI = "xl_tunai";
	public static final String PERMATA = "permata";
	public static final String BII = "bii";
	public static final String ONE_CLICK = "one_click";
	public static final String TWO_CLICKS = "two_clicks";
	public static final String VTWEB = "vtweb";
	public static final boolean SECURE_TRUE = true;
	public static final boolean SECURE_FALSE = false;
	public static final boolean SAVE_TOKEN_TRUE = true;
	public static final boolean SAVE_TOKEN_FALSE = false;
	private String client_key, server_key;
	private AlertDialog alert;
	private Context context;
	private String token_id, token_cvv_id, card_number, card_cvv, card_exp_month,
			card_exp_year, bank;
	private boolean secure, save_token_id;
	private String description;
	private String input1, input2, input3, token;
	private String customer, promo;
	private ArrayList<String> enabled;
	private String order_id, gross_amount;
	private String payment_type;
	private String shipFirst, shipLast, shipAddress, shipCity, shipPostal,
			shipPhone, shipCountry, bilFirst, bilLast, bilAddress, bilCity,
			bilPostal, bilPhone, bilCountry, custFirst, custLast, custEmail,
			custPhone;

	public void setCreditCard(String cc_number, String cc_exp_month,
			String cc_exp_year, String cc_cvv, String bank, boolean secure, boolean save) {
		card_number = cc_number;
		card_exp_month = cc_exp_month;
		card_exp_year = cc_exp_year;
		card_cvv = cc_cvv;
		this.bank = bank;
		this.secure = secure;
		this.save_token_id = save;
		this.payment_type = CREDIT_CARD;
	}
	
	public void setMandiriClickpay(String card, String input1, String input2, String input3, String token){
		card_number = card;
		this.input1 = input1;
		this.input2 = input2;
		this.input3 = input3;
		this.token = token;
		this.payment_type = MANDIRI_CLICKPAY;
	}

	public void setCimbClicks(String description) {
		this.description = description;
		this.payment_type = CIMB_CLICKS;
	}
	
	public void setTcash(String customer, String promo){
		this.customer = customer;
		this.promo = promo;
		this.payment_type = T_CASH;
	}
	
	public void setXlTunai(){
		this.payment_type = XL_TUNAI;
	}
	
	public void setPermata(){
		this.payment_type = PERMATA;
	}
	
	public void setBii(){
		this.payment_type = BII;
	}
	
	public void setOneClick(String token){
		this.payment_type = ONE_CLICK;
		this.token_id = token;
	}
	
	public void setTwoClicks(String token, String cvv, boolean secure){
		this.payment_type = TWO_CLICKS;
		this.token_id = token;
		this.card_cvv = cvv;
		this.secure = secure;
	}
	
	public void setVTWeb(boolean secure, String... payments){
		enabled = new ArrayList<String>();
		this.payment_type = VTWEB;
		this.secure = secure;
		for (int i = 0; i<payments.length; i++){
			enabled.add(payments[i]);
		}
	}

	public void setBillingInfo(String first, String last, String address,
			String city, String postal, String phone, String country) {
		bilFirst = first;
		bilLast = last;
		bilAddress = address;
		bilCity = city;
		bilPostal = postal;
		bilPhone = phone;
		bilCountry = country;
	}

	public void setShippingInfo(String first, String last, String address,
			String city, String postal, String phone, String country) {
		shipFirst = first;
		shipLast = last;
		shipAddress = address;
		shipCity = city;
		shipPostal = postal;
		shipPhone = phone;
		shipCountry = country;
	}

	public void setCustomerDetails(String first, String last, String email,
			String phone) {
		custFirst = first;
		custLast = last;
		custEmail = email;
		custPhone = phone;
	}

	public String getCardNumber() {
		return card_number;
	}

	public void setCardNumber(String card_number) {
		this.card_number = card_number;
	}

	public String getCardCvv() {
		return card_cvv;
	}

	public void setCardCvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}

	public String getCardExpMonth() {
		return card_exp_month;
	}

	public void setCardExpMonth(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public String getCardExpYear() {
		return card_exp_year;
	}

	public void setCardExpYear(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public VeritransObject(Context context, String order_id, String client_key, String server_key) {
		this.order_id = order_id;
		this.context = context;
		this.client_key = client_key;
		this.server_key = server_key;
	}

	public void pay(ArrayList<ShoppingCartItem> itemBought, String gross_amount) {
		this.gross_amount = gross_amount;
		if ((payment_type.equals(CREDIT_CARD))||(payment_type.equals(TWO_CLICKS))) {
			RetrieveToken p = new RetrieveToken(context);
			String parameters = "";
			parameters = parameters + "client_key=" + client_key;
			if(payment_type.equals(CREDIT_CARD)){
				parameters = parameters + "&card_number=" + card_number;
				parameters = parameters + "&card_exp_month=" + card_exp_month;
				parameters = parameters + "&card_exp_year=" + card_exp_year;
			}
			if(payment_type.equals(TWO_CLICKS)){
				parameters = parameters + "&two_click=true";
				parameters = parameters + "&token_id=" + token_id;
			}
			parameters = parameters + "&card_cvv=" + card_cvv;
			if (secure == SECURE_TRUE) {
				//Toast.makeText(context, "true", Toast.LENGTH_LONG).show();
				parameters = parameters + "&secure=true";
				parameters = parameters + "&gross_amount=" + gross_amount;
				parameters = parameters + "&bank=" + bank;
			} else {
				//Toast.makeText(context, "false", Toast.LENGTH_LONG).show();
				parameters = parameters + "&secure=false";
			}

			p.execute(new RequestURL(
					"https://api.sandbox.veritrans.co.id/v2/token?"
							+ parameters, itemBought));// request token
		}
		else{
			ProcessTransaction p = new ProcessTransaction(context);
			p.execute(new RequestURL(
					"https://api.sandbox.veritrans.co.id/v2/charge",
					itemBought));
		}
	}

	public String getOrderId() {
		return order_id;
	}

	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}

	public String getGrossAmount() {
		return gross_amount;
	}
	
	public void setSecure(boolean secure){
		this.secure = secure;
	}

	public void setGrossAmount(String gross_amount) {
		this.gross_amount = gross_amount;
	}

	public JSONObject getJsonResponse() {
		return jsonResponse;
	}

	public String getTokenId() {
		return token_id;
	}

	public void setTokenId(String token_id) {
		this.token_id = token_id;
	}

	public String getShipFirst() {
		return shipFirst;
	}

	public void setShipFirst(String shipFirst) {
		this.shipFirst = shipFirst;
	}

	public String getShipLast() {
		return shipLast;
	}

	public void setShipLast(String shipLast) {
		this.shipLast = shipLast;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipPostal() {
		return shipPostal;
	}

	public void setShipPostal(String shipPostal) {
		this.shipPostal = shipPostal;
	}

	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	public String getBilFirst() {
		return bilFirst;
	}

	public void setBilFirst(String bilFirst) {
		this.bilFirst = bilFirst;
	}

	public String getBilLast() {
		return bilLast;
	}

	public void setBilLast(String bilLast) {
		this.bilLast = bilLast;
	}

	public String getBilAddress() {
		return bilAddress;
	}

	public void setBilAddress(String bilAddress) {
		this.bilAddress = bilAddress;
	}

	public String getBilCity() {
		return bilCity;
	}

	public void setBilCity(String bilCity) {
		this.bilCity = bilCity;
	}

	public String getBilPostal() {
		return bilPostal;
	}

	public void setBilPostal(String bilPostal) {
		this.bilPostal = bilPostal;
	}

	public String getBilPhone() {
		return bilPhone;
	}

	public void setBilPhone(String bilPhone) {
		this.bilPhone = bilPhone;
	}
	
	public String getResponse(){
		return response;
	}

	class RequestURL {
		String url;
		ArrayList<ShoppingCartItem> cart;

		public RequestURL(String u, ArrayList<ShoppingCartItem> c) {
			url = u;
			cart = c;
		}
	}

	class RetrieveToken extends
			AsyncTask<RequestURL, Void, ArrayList<ShoppingCartItem>> {
		private String Content;
		Context context;

		public RetrieveToken(Context context) {
			this.context = context;
		}

		protected ArrayList<ShoppingCartItem> doInBackground(RequestURL... urls) {
			HttpURLConnection connection;
			OutputStreamWriter request = null;
			BufferedReader reader = null;

			// URL url = null;
			// String response = null;
			try {
				String url = urls[0].url;
				HttpGet httpRequest = new HttpGet(URI.create(url));
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(
						entity);
				// Get the server response

				reader = new BufferedReader(new InputStreamReader(
						bufHttpEntity.getContent()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}

				// Append Server Response To Content String
				Content = sb.toString();

			} catch (IOException e) {
				// Error
			}
			return urls[0].cart;
		}

		protected void onPostExecute(ArrayList<ShoppingCartItem> cart) {
			try {
				//Toast.makeText(context, Content, Toast.LENGTH_LONG).show();
				jsonResponse = new JSONObject(Content);
				alert = new AlertDialog.Builder(context).create();
				WebView wv = new WebView(context);
				if (jsonResponse.length() == 5) {
					String redirect = jsonResponse.getString("redirect_url");
					alert.setTitle("3DSecure Veritrans");
					wv.getSettings().setJavaScriptEnabled(true);
					wv.loadUrl(redirect);
					wv.setWebViewClient(new PopUpClient(cart));
					alert.setView(wv);
					alert.show();
				} else {
					if (jsonResponse.getString("token_id") != null) {
						alert.dismiss();
						token_cvv_id = jsonResponse.getString("token_id");
						ProcessTransaction q = new ProcessTransaction(context);
						q.execute(new RequestURL(
								"https://api.sandbox.veritrans.co.id/v2/charge",
								cart));
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class PopUpClient extends WebViewClient {
		private ArrayList<ShoppingCartItem> cart;

		public PopUpClient(ArrayList<ShoppingCartItem> cart) {
			this.cart = cart;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			if (url.startsWith("https://api.sandbox.veritrans.co.id/v2/token/callback/")) {
				alert.dismiss();
				try {
					token_cvv_id = jsonResponse.getString("token_id");
					ProcessTransaction q = new ProcessTransaction(context);
					q.execute(new RequestURL(
							"https://api.sandbox.veritrans.co.id/v2/charge",
							cart));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class ProcessTransaction extends AsyncTask<RequestURL, Void, Void> {
		private String Content;
		String parameters = "";
		Context context;
		ResponseListener listener;
		JSONObject transactionData = new JSONObject();

		public ProcessTransaction(Context context) {
			this.context = context;
			listener = (ResponseListener) context;
		}

		protected Void doInBackground(RequestURL... urls) {
			HttpURLConnection connection;
			OutputStreamWriter request = null;
			JSONArray arrayItems = new JSONArray();
			for (int i = 1; i <= urls[0].cart.size(); i++) {
				if (urls[0].cart.get(i - 1).getQuantity() != 0) {
					// parameters = parameters + "&id" + i + "="
					// + urls[0].cart.get(i - 1).getItem().getId()
					// + "&name" + i + "="
					// + urls[0].cart.get(i - 1).getItem().getNama()
					// + "&price" + i + "="
					// + urls[0].cart.get(i - 1).getItem().getHarga()
					// + "&quantity" + i + "="
					// + urls[0].cart.get(i - 1).getQuantity();
					JSONObject itemDetails = new JSONObject();
					try {
						itemDetails.put("id", ""
								+ urls[0].cart.get(i - 1).getItem().getId());
						itemDetails.put("name", urls[0].cart.get(i - 1)
								.getItem().getNama());
						itemDetails.put("price", urls[0].cart.get(i - 1)
								.getItem().getHarga());
						itemDetails.put("quantity", urls[0].cart.get(i - 1)
								.getQuantity());
						arrayItems.put(itemDetails);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			JSONObject transactionDetails = new JSONObject();
			try {
				transactionDetails.put("order_id", order_id);
				transactionDetails.put("gross_amount", gross_amount);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONObject billingAddress = new JSONObject();
			try {
				billingAddress.put("first_name", bilFirst);
				billingAddress.put("last_name", bilLast);
				billingAddress.put("address", bilAddress);
				billingAddress.put("city", bilCity);
				billingAddress.put("postal_code", bilPostal);
				billingAddress.put("phone", bilPhone);
				billingAddress.put("country_code", shipCountry);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONObject shippingAddress = new JSONObject();
			try {
				shippingAddress.put("first_name", shipFirst);
				shippingAddress.put("last_name", shipLast);
				shippingAddress.put("address", shipAddress);
				shippingAddress.put("city", shipCity);
				shippingAddress.put("postal_code", shipPostal);
				shippingAddress.put("phone", shipPhone);
				shippingAddress.put("country_code", bilCountry);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONObject customerDetails = new JSONObject();
			try {
				customerDetails.put("first_name", custFirst);
				customerDetails.put("last_name", custLast);
				customerDetails.put("email", custEmail);
				customerDetails.put("phone", custPhone);
				if ((payment_type.equals(CREDIT_CARD))|| (payment_type.equals(VTWEB))){
					customerDetails.put("billing_address", billingAddress);
					customerDetails.put("shipping_address", shippingAddress);
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				transactionData.put("transaction_details", transactionDetails);
				transactionData.put("item_details", arrayItems);
				transactionData.put("customer_details", customerDetails);
				if (payment_type.equals(CREDIT_CARD)) {
					JSONObject creditCard = new JSONObject();
					try {
						creditCard.put("token_id", token_cvv_id);
						creditCard.put("bank", "bni");
						creditCard.put("save_token_id", save_token_id);
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("credit_card", creditCard);
				}
				
				else if (payment_type.equals(CIMB_CLICKS)) {
					JSONObject cimbClicks = new JSONObject();
					try {
						cimbClicks.put("description", description);
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("cimb_clicks", cimbClicks);
				}
				else if (payment_type.equals(MANDIRI_CLICKPAY)) {
					JSONObject mandiriClickpay = new JSONObject();
					try {
						mandiriClickpay.put("card_number", card_number);
						mandiriClickpay.put("input1", input1);
						mandiriClickpay.put("input2", input2);
						mandiriClickpay.put("input3", input3);
						mandiriClickpay.put("token", token);
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("mandiri_clickpay", mandiriClickpay);
				}
				else if (payment_type.equals(T_CASH)) {
					JSONObject tcash = new JSONObject();
					try {
						tcash.put("customer", customer);
						tcash.put("promo", promo);
						
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("telkomsel_cash", tcash);
				}
				else if (payment_type.equals(ONE_CLICK)) {
					payment_type = CREDIT_CARD;
					JSONObject creditCard = new JSONObject();
					try {
						creditCard.put("token_id", token_id);
						creditCard.put("bank", "bni");
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("credit_card", creditCard);
				}
				else if (payment_type.equals(TWO_CLICKS)) {
					payment_type = CREDIT_CARD;
					JSONObject creditCard = new JSONObject();
					try {
						creditCard.put("token_id", token_id);
						creditCard.put("token_cvv_id", token_cvv_id);
						creditCard.put("bank", "bni");
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("credit_card", creditCard);
				}
				else if (payment_type.equals(VTWEB)) {
					JSONObject vtweb = new JSONObject();
					try {
						JSONArray enabledpayments = new JSONArray();
						for (int i = 0; i < enabled.size(); i++){
							enabledpayments.put(enabled.get(i));
						}
						vtweb.put("enabled_payments", enabledpayments);
						vtweb.put("credit_card_3d_secure", secure);
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					transactionData.put("vtweb", vtweb);
				}
				transactionData.put("payment_type", payment_type);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// if (Integer.parseInt((String)gross.getText()) > 1000000){
			// parameters = parameters + "&secure=true";
			// }else{
			// parameters = parameters + "&secure=false";
			// }

			try {
				String url = urls[0].url;
				HttpPost httpRequest = new HttpPost(URI.create(url));
				StringEntity se = new StringEntity(transactionData.toString());
				httpRequest.setEntity(se);
				httpRequest.setHeader("Accept", "application/json");
				httpRequest.setHeader("Content-type", "application/json");
				httpRequest.setHeader("Authorization", "Basic " + server_key);
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
			//Toast.makeText(context, Content, Toast.LENGTH_LONG).show();
			response = Content;
			if (listener != null) {
				listener.onResponseRecieved(response);
			}
//			Intent i = new Intent(context, FragmentTesting.class);
//	    	i.putExtra("text", transactionData.toString()+"\n\n" + response);
//	    	context.startActivity(i);
			// Fragment fr = new Fragment();
			// fr = new FragmentOrderSummary();
			// Bundle bundle = new Bundle();
			// bundle.putString("status", Content);
			// fr.setArguments(bundle);
			// FragmentManager fm = getFragmentManager();
			// FragmentTransaction fragTrans = fm.beginTransaction();
			// fragTrans.replace(R.id.frame_container, fr, "OrderSummary");
			// fragTrans.commit();
		}

	}

	public interface ResponseListener {

		public void onResponseRecieved(String response);

	}
}
