package com.zhitu.xxf;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SendMsgActivity extends ActionBarActivity {
	
	private android.app.ActionBar actionBar;
	private Button sendmsgBtn;
	private EditText sendText;
	private String usernameString;
	private String textinfoString;
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_msg);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("新动态");
		actionBar.show();
		sendmsgBtn = (Button)findViewById(R.id.msgbutton1);
		sendText = (EditText)findViewById(R.id.msgeditText1);
		sendmsgBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (sendText.getText().toString() == null
						|| sendText.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "不能空",
							Toast.LENGTH_LONG).show();
					return;
				
				} else {
					usernameString = AppValues.username;
					textinfoString = sendText.getText().toString();
					progressBar = new ProgressBar(getApplicationContext());
					progressBar.setVisibility(View.VISIBLE);
					new UserNewMsg().execute((Void)null);
				}
				
			}
		});
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_msg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == android.R.id.home){
			finish();
		}
	
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_send_msg,
					container, false);
			return rootView;
		}
	}
	
	public class UserNewMsg extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

		/*	ZhituNetWork netWork = new ZhituNetWork();
			if (netWork.sendAMsg(usernameString, textinfoString)) {
				return true;
			} else {
				return false;
			}
		*/
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {

				Toast toast = Toast.makeText(SendMsgActivity.this, "发布成功",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 200);
				toast.show();
				//finish();
				
			} else {

				// mPasswordView.setError(getString(R.string.error_incorrect_password));
				Toast toast = Toast.makeText(SendMsgActivity.this, "发布失败",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 200);
				toast.show();

			}
		}

		@Override
		protected void onCancelled() {
			progressBar.setVisibility(View.GONE);
		}
	}


}
