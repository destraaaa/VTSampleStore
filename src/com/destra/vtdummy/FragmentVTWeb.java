package com.destra.vtdummy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentVTWeb extends Activity {
	public static WebView vtweb;
	public static String url;
	public static Activity a;
	public static boolean visited = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		a = this;
		visited = true;
		setContentView(R.layout.vt_web_view);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    url = extras.getString("url");
		}
//		Bundle args = getArguments();
//		if (args  != null && args.containsKey("url")){
//			url = args.getString("url");
//		}
		getActionBar().setTitle("VT-Web");
		// calling onPrepareOptionsMenu() to hide action bar icons
		invalidateOptionsMenu();
		ActivityHome.fragmentTag = "vt";
		vtweb = (WebView) findViewById(R.id.vtwebView);
		vtweb.getSettings().setJavaScriptEnabled(true);
		vtweb.loadUrl(url);
		
	}
	
	@Override
	public void finish() {

	    //mWebContainer.removeAllViews();
		visited = false;
	    vtweb.clearHistory();
	    vtweb.clearCache(true);
	    vtweb.loadUrl("about:blank");
	    vtweb.freeMemory();  //new code   
	    vtweb.pauseTimers(); //new code
	    vtweb= null;

	    super.finish();   
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(vtweb.canGoBack()){
                    vtweb.goBack();
                }else{
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
