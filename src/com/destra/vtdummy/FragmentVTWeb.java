package com.destra.vtdummy;

import java.lang.reflect.InvocationTargetException;

import com.destra.vtdummy.model.VeritransObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;

public class FragmentVTWeb extends Activity {
	public static WebView vtweb;
	public static String url, dari;
	public static Activity a;
	public static boolean visited = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		a = this;
		visited = true;
		setContentView(R.layout.vt_web_view);
		Intent myIntent = getIntent();
		url = myIntent.getStringExtra("url");
//		dari = myIntent.getStringExtra("dari");
//		if (dari.equals(VeritransObject.CIMB_CLICKS)) {
//			getActionBar().setTitle("CIMB Clicks");
//			// calling onPrepareOptionsMenu() to hide action bar icons
//			invalidateOptionsMenu();
//		} else {
//			getActionBar().setTitle("VT-Web");
//			// calling onPrepareOptionsMenu() to hide action bar icons
//			invalidateOptionsMenu();
//		}
		// Bundle extras = getIntent().getExtras();
		// if (extras != null) {
		// url = extras.getString("url");
		// }
		// Bundle args = getArguments();
		// if (args != null && args.containsKey("url")){
		// url = args.getString("url");
		// }
		ActivityHome.fragmentTag = "vt";
		vtweb = (WebView) findViewById(R.id.vtwebView);
		vtweb.resumeTimers();
		vtweb.getSettings().setJavaScriptEnabled(true);
		vtweb.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				Toast.makeText(FragmentVTWeb.this, Uri.parse(url).getHost(),
//						Toast.LENGTH_LONG).show();
				if (Uri.parse(url).getHost()
						.equals("api.sandbox.veritrans.co.id")) {
					view.loadUrl(url);
					return false;
				} else {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					startActivity(intent);
					FragmentVTWeb.this.finish();
					return true;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (Uri.parse(url).getHost() != null) {
					if (Uri.parse(url).getHost().equals("vtdummyandroid")) { // NON-NLS
						view.stopLoading();
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(url));
						startActivity(intent);
						finish();
					}
				}
			}

		});
		vtweb.loadUrl(url);

	}

	@Override
	public void finish() {

		visited = false;
		vtweb.clearHistory();
		vtweb.clearCache(true);
		vtweb.loadUrl("about:blank");
		vtweb.freeMemory(); // new code
		vtweb.pauseTimers(); // new code
		vtweb = null;
		super.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (vtweb.canGoBack()) {
					vtweb.goBack();
				} else {
					finish();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
