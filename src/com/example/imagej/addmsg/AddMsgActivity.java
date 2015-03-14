package com.example.imagej.addmsg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zhitu.xxf.AppValues;
import com.zhitu.xxf.LoginActivity;
import com.zhitu.xxf.R;
import com.zhitu.xxf.RegisterActivity;
import com.zhitu.xxf.SendMsgActivity;
import com.zhitu.xxf.ZhituNetWork;
import com.zhitu.xxf.SendMsgActivity.UserNewMsg;
import com.example.imagej.ScanJ.ChildAdapter;



import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AddMsgActivity extends ActionBarActivity {

	ChildAdapter adapter;
	GridView mGridView;
	private ActionBar actionBar;
	ArrayList<String> list;
	private String usernameString;
	TextView locationTextView;
	private String textinfoString;
	private ProgressBar progressBar;
	private EditText sendEditText;
	private LocationClient mLocClient;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_new_msg);
		progressBar = new ProgressBar(getApplicationContext());
		list=new ArrayList<String>();
		actionBar = getActionBar();
		actionBar.setTitle("新动态");
		actionBar.show();
		mGridView =(GridView) findViewById(R.id.child_grid);
		sendEditText = (EditText)findViewById(R.id.send_msg_textinfo);
		locationTextView = (TextView)findViewById(R.id.location_textview);
		//////////////////////////////////////////////////////////////
		mLocClient = ((Location)getApplication()).mLocationClient;
		((Location)getApplication()).mTv = locationTextView;
		if (mLocClient != null && !mLocClient.isStarted()) {
			setLocationOption();
			mLocClient.start();
		} else if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
		}
		if (mLocClient != null && mLocClient.isStarted())
			mLocClient.requestLocation();
		else 
			Log.d("boot", "locClient is null or not started");
	}
	
    private void setLocationOption(){
    	if (mLocClient == null) return ;
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("detail");
		option.setCoorType("bd09ll");
		option.setScanSpan(10000);		
		mLocClient.setLocOption(option);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add("ok");
		menuItem.setIcon(R.drawable.amsc_ic_menu_done_holo_light);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				if (sendEditText.getText().toString() == null
						|| sendEditText.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "不能空",
							Toast.LENGTH_LONG).show();
				} else {
					usernameString = AppValues.username;
					textinfoString = sendEditText.getText().toString();
					progressBar = new ProgressBar(getApplicationContext());
					progressBar.setVisibility(View.VISIBLE);
					new UserNewMsg().execute((Void)null);
				}
				return true;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override  
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}
	
	public void makeCustomToast(String text,int duration) {	
		View layout = getLayoutInflater().inflate(R.layout.custom_toast, 
		        (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        // set a message
        TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
        toastText.setText(text);

        // Toast...
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
			String path = data.getStringExtra("mFile");
			if (list.size()<3) {
				list.add(path);
				adapter = new ChildAdapter(AddMsgActivity.this, list, mGridView);
				mGridView.setAdapter(adapter);
			}
			else Toast.makeText(AddMsgActivity.this, "图片已经达到上限！",Toast.LENGTH_SHORT).show();	
	}
	
	public void addPhoto(View view)
	{
		Intent intent = new Intent(AddMsgActivity.this,AddPhoto.class);
		startActivityForResult(intent,1);
	}

	public class UserNewMsg extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			ZhituNetWork netWork = new ZhituNetWork();
			String picString = "";
			for (int i = 0; i < list.size(); i++) {
				picString += list.get(i)+"&";
			}
			if (netWork.sendAMsg(usernameString, textinfoString,picString)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {
				makeCustomToast("发布成功", Toast.LENGTH_LONG);
				finish();				
			} else {
				// mPasswordView.setError(getString(R.string.error_incorrect_password));
				makeCustomToast("发布失败", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			progressBar.setVisibility(View.GONE);
		}
	}

}
