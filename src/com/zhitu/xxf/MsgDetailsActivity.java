package com.zhitu.xxf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.zhitu.hf.TimeConvert;

import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MsgDetailsActivity extends ActionBarActivity {

	private ListView commListView;
	private String usernameString = null;
	private String msgUsernameString = null;
	private String sendtimeString = null;
	private String contenttextsString = null;
	private int position;
	private String commcountsString = null;
	private Button commButton;
	private EditText commeEditText;
	ArrayList<UserComm> listsArrayList;
	
	private ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_msg_details);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		
		Intent intent;
		Bundle parameterBundle;
		if ((intent = getIntent()) != null
				&& (parameterBundle = intent.getExtras()) != null) {
			usernameString = parameterBundle.getString("username");
			sendtimeString = parameterBundle.getString("sendtime");
			position = parameterBundle.getInt("position");
			commcountsString = parameterBundle.getString("commcounts");
			contenttextsString = parameterBundle.getString("contenttext");
			msgUsernameString = parameterBundle.getString("msgusername");
		}
		listsArrayList = new ArrayList<UserComm>();
		commListView = (ListView) findViewById(R.id.comm_listview);
		commeEditText = (EditText) findViewById(R.id.comm_input_bar);
		commButton = (Button) findViewById(R.id.comm_ok_btn);
		commButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				AddNewComments addNewComments = new AddNewComments();
				addNewComments.setConstring(commeEditText.getText().toString(),
						usernameString, sendtimeString,commcountsString);
				addNewComments.execute((Void) null);
			}
		});
		PullMsgComments pullMsgComments = new PullMsgComments();
		pullMsgComments.setConstring(msgUsernameString, sendtimeString);
		pullMsgComments.execute((Void) null);
	}

	public class PullMsgComments extends AsyncTask<Void, Void, Boolean> {

		private String userString;
		private String shareString;

		public void setConstring(String user, String shre) {
			userString = user;
			shareString = shre;
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			SimpleDateFormat adFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String commtime = adFormat.format(new Date());
			ZhituNetWork netWork = new ZhituNetWork();
			String resString = netWork.getMsgComm(userString, shareString);
			if (resString != null) {
				if (new JsonHandle().getSimpleStatusValue(resString).equals(
						"success")) {
					Gson gson = new Gson();
					GsonCommUser gsonCommUser = gson.fromJson(resString,
							GsonCommUser.class);
					listsArrayList = gsonCommUser.getComments();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				makeCustomToast("评论已加载", Toast.LENGTH_LONG);

				commListView.setAdapter(new CommListViewAdapter(
						getApplicationContext(), listsArrayList));
			} else {
				makeCustomToast("还没有新评论", Toast.LENGTH_LONG);
			}
		}
	}

	public class AddNewComments extends AsyncTask<Void, Void, Boolean> {

		private String conString;
		private String userString;
		private String shareString;
		private String countString;

		public void setConstring(String con, String user, String shre,String commcounts) {
			conString = con;
			userString = user;
			shareString = shre;
			countString = commcounts;
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			SimpleDateFormat adFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String commtime = adFormat.format(new Date());
			ZhituNetWork netWork = new ZhituNetWork();
			String resString = netWork.addANewComm(userString, shareString,
					AppValues.username, commtime, conString,countString);
			if (resString != null) {
				if (new JsonHandle().getSimpleStatusValue(resString).equals(
						"success")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				makeCustomToast("评论成功", Toast.LENGTH_LONG);
				UserComm userComm = new UserComm();
				userComm.setSharetime(TimeConvert.getTimeDiff(shareString));
				userComm.setTextinfo(conString);
				userComm.setUsername(userString);
				listsArrayList.add(0, userComm);
				commListView.setAdapter(new CommListViewAdapter(
						getApplicationContext(), listsArrayList));
				getIntent().putExtra("addnewcomm", "yes");
				getIntent().putExtra("position", position);
			} else {
				makeCustomToast("评论失败", Toast.LENGTH_LONG);
				getIntent().putExtra("addnewcomm", "no");
			}
		}
	}

	public void makeCustomToast(String text, int duration) {
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

	class CommListViewAdapter extends BaseAdapter {
		private ArrayList<UserComm> listItems;
		private LayoutInflater listContainer; // 视图容器

		final class ListViewItem { // 自定义控件集合
			public ImageView headImageView;
			public TextView nameTextView;
			public TextView timeTextView;
			public TextView conTextView;
		}

		final class HeadViewItem { // 自定义控件集合
			public ImageView headmageView;
			public TextView shareDateTextView;
			public TextView nameTextView;
			public TextView contentTextView;
			public ImageView commentImageView;
			public ImageView retweetImageView;
			public ImageView ilikeitImageView;

		}

		public CommListViewAdapter(Context context,
				ArrayList<UserComm> listItems) {
			listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = listItems;
		}

		public int getCount() {
			return listItems.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			Log.e("method", "getView");

			if (convertView == null) {
				final ListViewItem listItemView = new ListViewItem();

				convertView = listContainer
						.inflate(R.layout.comment_card, null);
				listItemView.headImageView = (ImageView) convertView
						.findViewById(R.id.comm_head_photo);
				listItemView.conTextView = (TextView) convertView
						.findViewById(R.id.comm_content);
				listItemView.timeTextView = (TextView) convertView
						.findViewById(R.id.comm_time_text);
				listItemView.nameTextView = (TextView) convertView
						.findViewById(R.id.comm_name_text);
				listItemView.conTextView.setText((String) listItems.get(
						position).getTextinfo());
				listItemView.timeTextView.setText((String) listItems.get(
						position).getSharetime());
				listItemView.nameTextView.setText((String) listItems.get(
						position).getUsername());
				convertView.setTag(listItemView);
				return convertView;
			} else {
				final ListViewItem listItemView = (ListViewItem) convertView
						.getTag();

				listItemView.conTextView.setText((String) listItems.get(
						position).getTextinfo());
				listItemView.timeTextView.setText((String) listItems.get(
						position).getSharetime());
				listItemView.nameTextView.setText((String) listItems.get(
						position).getUsername());
				convertView.setTag(listItemView);
				return convertView;
			}
		}

	}
}
