package com.zhitu.xxf;

import com.google.gson.Gson;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


public class ItemUserInfoActivity extends ActionBarActivity {

	private String usernameString;
	private UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_details_indexitem);
		Intent intent = getIntent();
		if(intent!=null){
			if(getIntent().getExtras()!=null){
				usernameString = getIntent().getExtras().getString("itemusername");
		}
	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.item_user_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}
	
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		
		public class GsonUserInfo{
			public String status;
			public UserInfo user;
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public UserInfo getUser() {
				return user;
			}
			public void setUser(UserInfo user) {
				this.user = user;
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {

			ZhituNetWork netWork = new ZhituNetWork();
			String s = netWork.getUserinfo(usernameString);
			if (s!=null) {
				Gson gson = new Gson();
				GsonUserInfo gsonUserInfo = gson.fromJson(s, GsonUserInfo.class);
				if(gsonUserInfo.getStatus()!=null && gsonUserInfo.getStatus().equals("success")){
					userInfo = gsonUserInfo.getUser();
					if(userInfo!=null){
	 				return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			} else {
				return false;
			}
		
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {
				
			} else {
				
			}
		}

	}



}
