package com.destra.vtdummy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentTesting extends Activity {
	String text;
	EditText test_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_testing);
		test_text = (EditText) findViewById(R.id.test_text);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    text = extras.getString("text");
		}
		test_text.setText(text);
	}

}
