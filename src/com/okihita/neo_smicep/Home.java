package com.okihita.neo_smicep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends Activity {
	/*static ListView l;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		l = (ListView) findViewById(R.id.list);
		l.setOnTouchListener(new OnTouchListener() {
		    // Setting on Touch Listener for handling the touch inside ScrollView
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    // Disallow the touch request for parent scroll on touch of child view
		    v.getParent().requestDisallowInterceptTouchEvent(true);
		    return false;
		    }
		});
		final Button GetServerData = (Button) findViewById(R.id.GetServerData);
		ListView listView = (ListView) findViewById(android.R.id.list);
		GetServerData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// WebServer Request URL
				String serverURL = "http://virtuo-id.com/smiceptest/hardcoded.php";

				// Use AsyncTask execute Method To Prevent ANR Problem
				new LongOperation().execute(serverURL);
			}
		});
	}

	// Class with extends AsyncTask class
	private class LongOperation extends AsyncTask<String, Void, Void> {

		// Required initialization
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(Home.this);
		String data = "";
		TextView uiUpdate = (TextView) findViewById(R.id.output);
		//TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;
		EditText serverText = (EditText) findViewById(R.id.serverText);
		private Context thisContext;
		public LongOperation(Context context){
			thisContext = context;
		}
		public LongOperation(){
			
		}
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

			try {
				// Set Request parameter
				data += "&" + URLEncoder.encode("data", "UTF-8") + "="
						+ serverText.getText();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********
			BufferedReader reader = null;

			// Send data
			try {

				// Defined URL where to send data
				URL url = new URL(urls[0]);

				// Send POST data request

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();

				// Get the server response

				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}

				// Append Server Response To Content String
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {

					reader.close();
				}

				catch (Exception ex) {
				}
			}

			/*****************************************************
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();

			if (Error != null) {

				//uiUpdate.setText("Output : " + Error);

			} else {

				// Show Response Json On Screen (activity)
				//uiUpdate.setText(Content);

				/****************** Start Parse Response JSON Data *************

				String OutputData = "";
				JSONObject jsonResponse;

				try {
					/******
					 * Creates a new JSONObject with name/value mappings from
					 * the JSON string.
					 ********
					jsonResponse = new JSONObject(Content);

					/*****
					 * Returns the value mapped by name if it exists and is a
					 * JSONArray.
					 ***

					/******* Returns null otherwise. *******
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("Andromeda");

					/*********** Process each JSON Node ************

					int lengthJsonArr = jsonMainNode.length();
					//TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
					ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();
					for (int i = 0; i < lengthJsonArr; i++) {
						/****** Get Object for each JSON node. ***********
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);
						/******* Fetch node values **********
						String start = jsonChildNode.optString("name")
								.toString();
						String nama = jsonChildNode.optString("number")
								.toString();
						String until = jsonChildNode.optString(
								"date_added").toString();
						ScheduleItem s = new ScheduleItem(start, nama, until);
						items.add(s);

						/*OutputData += "Name           : "
								+ names[i]
								+ "\n"
								+ "Number      : "
								+ numbers[i]
								+ "\n"
								+ "Time                : "
								+ dates[i]
								+ "\n"
								+ "--------------------------------------------------";
*
					}
					/****************** End Parse Response JSON Data *************

					// Show Parsed Output on screen (activity)
					ScheduleAdapter adapter = new ScheduleAdapter(Home.this, items);
				    Home.l.setAdapter(adapter);
					//jsonParsed.setText(OutputData);

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}
	}*/
}