package com.zhitu.xxf;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private TextView welLoginTextView;
	private TextView welRegisTextView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		actionBar = getActionBar();
		actionBar.setIcon(R.drawable.zhitu_logo_no_bg);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.hide();
		welLoginTextView = (TextView) findViewById(R.id.wel_login_textView);
		welRegisTextView = (TextView) findViewById(R.id.wel_register_textView);
		welLoginTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
			}
		});

		welRegisTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(intent);
			}
		});
		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
		 */
	}
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * 
	 * // Inflate the menu; this adds items to the action bar if it is present.
	 * //getMenuInflater().inflate(R.menu.welcome, menu);
	 * 
	 * return true; }
	 */
	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

}
