package com.zhitu.dzc;

import java.util.ArrayList;  

import com.google.gson.Gson;
import com.zhitu.xxf.AppValues;
import com.zhitu.xxf.GsonUser;
import com.zhitu.xxf.R;
import com.zhitu.xxf.UserMsg;
import com.zhitu.xxf.ZhituNetWork;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;



public class MainActivity extends Activity {  

    private ListView listView;    
    private ListViewAdapter listViewAdapter;  
   
    private ActionBar actionBar;
    @Override 
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.lv_home);  
        actionBar = getActionBar();
        actionBar.show();
        listView = (ListView)findViewById(R.id.lv_home);   
        new UpdateMainPage().execute((Void)null);
    }  

    private ArrayList<UserMsg> userMsgsList;
    public class UpdateMainPage extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			ZhituNetWork netWork = new ZhituNetWork();
			String jsonString = netWork.pullMainPageData(AppValues.username,"0");
			if(jsonString!=null){
				Gson gson = new Gson();
				GsonUser gsonUser = gson.fromJson(jsonString, GsonUser.class);
				if(gsonUser.getStatus()!=null && gsonUser.getStatus().equals("success")){
					userMsgsList = gsonUser.getData();
					return true;
				}
			}
			
			return false;
			
		}
		@Override
		protected void onPostExecute(final Boolean success) {

			if (success) {
				
				int count = userMsgsList.size();
				for (int i = 0; i < count; i++) {
					UserMsg userMsg = userMsgsList.get(i);
					userMsg.getSharetime();
				}
				
				Toast toast = Toast.makeText(com.zhitu.dzc.MainActivity.this, "刷新成功",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 200);
				toast.show();
				
				listViewAdapter = new ListViewAdapter(getApplication(),userMsgsList); 
			    listView.setAdapter(listViewAdapter);  
			    
				//finish();
				
			} else {
				
				Toast toast = Toast.makeText(com.zhitu.dzc.MainActivity.this, "刷新失败",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 200);
				toast.show();
			}
		}
    }

}