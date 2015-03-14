package com.zhitu.xxf;

import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagej.addmsg.AddMsgActivity;
import com.example.imagej.ScanJ.ShowImageActivity;
import com.example.imagej.ViewPager.PageActivity1;
import com.google.gson.Gson;
import com.zhitu.dzc.MyListView.OnRefreshListener;
import com.zhitu.hf.TimeConvert;
import com.zhitu.xxf.AppValues;
import com.zhitu.xxf.GsonUser;
import com.zhitu.xxf.UserMsg;
import com.zhitu.xxf.R;
import com.zhitu.xxf.ZhituNetWork;
import com.zhitu.xxf.MsgDetailsActivity.CommListViewAdapter.HeadViewItem;

public class MainPageActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks{

	private NavigationDrawerFragment mNavigationDrawerFragment;
	public static com.zhitu.dzc.MyListView listView;
	private CharSequence mTitle;
	static IndexListViewAdapter adapter;
	private String itemUsernameString;
	 MenuItem userinfoOkItem;
	 MenuItem addMsgItem;
	UserInfo tmpUserInfo = new UserInfo();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		itemUsernameString = AppValues.username;
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		tmpUserInfo = AppValues.userInfo;
	}
	
	public boolean onKeyDown(int kCode, KeyEvent kEvent) {
	    switch (kCode) {
	    case KeyEvent.KEYCODE_DPAD_LEFT: {
	        return true;
	    }

	    case KeyEvent.KEYCODE_DPAD_UP: {
	        return true;
	    }

	    case KeyEvent.KEYCODE_DPAD_RIGHT: {
	        return true;
	    }

	    case KeyEvent.KEYCODE_DPAD_DOWN: {
	        return true;
	    }
	    case KeyEvent.KEYCODE_DPAD_CENTER: {
	        return true;
	    }
	    case KeyEvent.KEYCODE_BACK: {
	        return false;
	    }
	    }
	    return super.onKeyDown(kCode, kEvent);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments

		switch (position + 1) {
		case 1: {
			if(userinfoOkItem!=null){
				userinfoOkItem.setVisible(true);
			}
			if(addMsgItem!=null){
				addMsgItem.setVisible(false);
			}
			mTitle = itemUsernameString;// getString(R.string.title_section1);
			PlaceholderFragment placeholderFragment = new PlaceholderFragment();
			placeholderFragment.setPosition(position + 1);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container,// inFragment).commit();
					placeholderFragment).commit();
			
		}
			break;
		case 2: {
			if(userinfoOkItem!=null){
				userinfoOkItem.setVisible(false);
			}
			if(addMsgItem!=null){
				addMsgItem.setVisible(true);
			}
			mTitle = "广场";// getString(R.string.title_section2);
			// Fragment inFragment =
			// PlaceholderFragment.newInstance(position+1);
			PlaceholderFragment placeholderFragment = new PlaceholderFragment();
			placeholderFragment.setPosition(position + 1);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container,// inFragment).commit();
					placeholderFragment).commit();
			
		}

			break;
		case 3:
			mTitle = "我的空间";// getString(R.string.title_section3);
			
			Intent intent = new Intent(MainPageActivity.this,
					PageActivity1.class);
			startActivity(intent);
			break;
		case 4: {
			mTitle = "设置";
			if(userinfoOkItem!=null){
				userinfoOkItem.setVisible(false);
			}
			if(addMsgItem!=null){
				addMsgItem.setVisible(false);
			} 
			PlaceholderFragment placeholderFragment = new PlaceholderFragment();
			placeholderFragment.setPosition(position + 1);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container,// inFragment).commit();
					placeholderFragment).commit();
			
		}
			break;
		case 5: {
		
			System.exit(1);
		}
			break;
		}
	}

	public void setContent() {
		adapter = new IndexListViewAdapter(
				getApplicationContext(), AppValues.userMsgsLists);
		listView.setAdapter(adapter);

	}
	
	public int getNewCommNum(){
		Intent intent = getIntent();
		if(intent!=null && intent.getExtras()!=null){
			String string = intent.getExtras().getString("addnewcomm");
			if(string!=null && string.equals("yes")){
				int p = intent.getExtras().getInt("position");
				return p;
			}
		}
		return -10;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		actionBar.setDisplayUseLogoEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main_page, menu);
			/*addMsgItem =  menu.findItem(R.id.action_sendmsg);
			 userinfoOkItem = menu.findItem(R.id.userinfo_ok);
		        userinfoOkItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem arg0) {
						HandleUserinfo handleUserinfo = new HandleUserinfo();
						handleUserinfo.setData(tmpUserInfo.getHeadphoto(), tmpUserInfo.getGender(), tmpUserInfo.getLabel(), tmpUserInfo.getPerson(), tmpUserInfo.getBirthday());
						handleUserinfo.execute((Void)null);
						return true;
					}
				});
		        userinfoOkItem.setVisible(true);*/
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
       
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		super.onOptionsItemSelected(item);
		int id = item.getItemId();
		if (id == R.id.home) {
			
		} else if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_sendmsg) {
			Intent intent = new Intent();
			intent.setClass(MainPageActivity.this, AddMsgActivity.class);
			startActivity(intent);
		}
		return true;
	}

	private ArrayList<UserMsg> userMsgsList;

	public static class PlaceholderFragment extends Fragment {

		private Context context;
		private Activity activity;
		private int selectionInt = 0;

		public PlaceholderFragment() {
		}

		public void setPosition(int p) {
			selectionInt = p;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			this.activity = activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = null;
			switch (selectionInt) {
			case 1: {
				rootView = inflater.inflate(R.layout.personal_detial,// R.layout.fragment_main_page,
						container, false);
				((TextView)rootView.findViewById(R.id.userinfo_birthday)).setText(AppValues.userInfo.getBirthday());
				((TextView)rootView.findViewById(R.id.userinfo_name)).setText(AppValues.userInfo.getUsername());
				((TextView)rootView.findViewById(R.id.userinfo_email)).setText(AppValues.userInfo.getEmail());
				((TextView)rootView.findViewById(R.id.userinfo_gender)).setText(AppValues.userInfo.getGender());
				((TextView)rootView.findViewById(R.id.userinfo_label)).setText(AppValues.userInfo.getLabel());
				((TextView)rootView.findViewById(R.id.userinfo_person)).setText(AppValues.userInfo.getPerson());
			}
				break;
			case 2: {
				rootView = inflater.inflate(R.layout.fragment_index_page,// R.layout.fragment_main_page,
						container, false);
				listView = (com.zhitu.dzc.MyListView) rootView
						.findViewById(R.id.index_listView);
				String string = "0";
				if (AppValues.userMsgsLists == null
						|| AppValues.userMsgsLists.size() == 0) {
					string = "0";
				} else {
					string = AppValues.userMsgsLists.get(0).getSharetime();
				}
				UpdateMainPage ump = new UpdateMainPage();
				ump.setLasttimeString(string);
				ump.execute((Void) null);
				listView.setonRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh() {

						String string = "0";
						if (AppValues.userMsgsLists == null
								|| AppValues.userMsgsLists.size() == 0) {
							string = "0";
						} else {
							string = AppValues.userMsgsLists.get(0)
									.getSharetime();
						}
						UpdateMainPage ump = new UpdateMainPage();
						ump.setLasttimeString(string);
						ump.execute((Void) null);
					}
				});
				listView.setOnScrollListener(new OnScrollListener() {  
		            private int lastItemIndex;//当前ListView中最后一个Item的索引  
		            //当ListView不在滚动，并且ListView的最后一项的索引等于adapter的项数减一时则自动加载（因为索引是从0开始的）  
		            @Override  
		            public void onScrollStateChanged(AbsListView view, int scrollState) {  
		                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
		                        && lastItemIndex == adapter.getCount()) { 
		                	Log.e("log", "滑到底部");
		                	//加载数据代码，此处省略了  
		                       try {  
		                           Thread.sleep(1000);  
		                       } catch (Exception e) {  
		                           e.printStackTrace();  
		                       } 
		                       adapter.notifyDataSetChanged();
		                   }
		               }  
		            
		            @Override  
		            public void onScroll(AbsListView view, int firstVisibleItem,  
		                    int visibleItemCount, int totalItemCount) {  
		                //ListView 的FooterView也会算到visibleItemCount中去，所以要再减去一  
		                lastItemIndex = firstVisibleItem + visibleItemCount -1-1;
		            }
		                	
		        });
					
			}
				break;
			case 3: {
				rootView = inflater.inflate(R.layout.fragment_index_page,// R.layout.fragment_main_page,
						container, false);
			}
				break;
			case 4: {
				rootView = inflater.inflate(R.layout.main_setting,// R.layout.fragment_main_page,
						container, false);
			}
				break;
			default:
				break;
			}
			return rootView;
		}

		public class UpdateMainPage extends AsyncTask<Void, Void, Boolean> {

			private String lastTimesString = "0";

			public void setLasttimeString(String lasString) {
				lastTimesString = lasString;
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				ZhituNetWork netWork = new ZhituNetWork();
				String jsonString = netWork.pullMainPageData(
						AppValues.username, lastTimesString);
				if (jsonString != null) {
					Gson gson = new Gson();
					GsonUser gsonUser = gson.fromJson(jsonString,
							GsonUser.class);
					if (gsonUser.getStatus() != null
							&& gsonUser.getStatus().equals("success")) {
						ArrayList<UserMsg> newdataArrayList = gsonUser
								.getData();
						int c = 0;
						if (AppValues.userMsgsLists != null) {
							c = AppValues.userMsgsLists.size();
						}
						ArrayList<UserMsg> tmp = new ArrayList<UserMsg>();
						for (int i = 0; i < newdataArrayList.size(); i++) {
							tmp.add(newdataArrayList.get(i));
						}
						for (int i = 0; i < c; i++) {
							tmp.add(AppValues.userMsgsLists.get(i));
						}
						AppValues.userMsgsLists = tmp;
						return true;
					}
				}
				return false;
			}

			public void makeCustomToast(String text, int duration) {

				View layout = getActivity().getLayoutInflater().inflate(
						R.layout.custom_toast,
						(ViewGroup) getActivity().findViewById(
								R.id.custom_toast_layout_id));
				// set a message
				TextView toastText = (TextView) layout
						.findViewById(R.id.toasttext);
				toastText.setText(text);

				// Toast...
				Toast toast = new Toast(getActivity());
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(duration);
				toast.setView(layout);
				toast.show();
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				if (success) {
					((MainPageActivity) activity).setContent();

					makeCustomToast("更新成功!", Toast.LENGTH_LONG);
					listView.onRefreshComplete();
					// finish();

				} else {
					((MainPageActivity) activity).setContent();
					makeCustomToast("没有更新", Toast.LENGTH_LONG);
					listView.onRefreshComplete();
				}
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
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}
	
	public class HandleUserinfo extends AsyncTask<Void, Void, Boolean> {

		private String head;
		private String gender;
		private String label;
		private String person;
		private String birthday;

		public void setData(String head, String gender, String label,String person, String birthday) {
			this.head = head;
			this.gender = gender;
			this.label = label;
			this.person = person;
			this.birthday = birthday;
		}

		public class GsonUser {
			private String status;
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
		}
		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String jsString = new ZhituNetWork().handleUserinfo(AppValues.username, head, gender, label, person, birthday);
			if (jsString != null) {
				Gson gson = new Gson();
				GsonUser gsonLike = gson.fromJson(jsString, GsonUser.class);
				if (gsonLike.getStatus()!=null && gsonLike.getStatus().equals("success")) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				AppValues.userInfo = tmpUserInfo;
				makeCustomToast("修改成功！", Toast.LENGTH_LONG);
			}else {
				makeCustomToast("修改失败", Toast.LENGTH_LONG);
			}
		}
	}

	public class IndexListViewAdapter extends BaseAdapter {
		private ArrayList<UserMsg> listItems;
		private LayoutInflater listContainer; // 视图容器

		public final class ListViewItem { // 自定义控件集合
			public ImageView[] indexImage = new ImageView[3];
			private LinearLayout centeLinearLayout;
			private ImageView headImageView;
			public ImageView image;
			public ImageView image2;
			public ImageView image3;
			public TextView date;
			public TextView name;
			public TextView content;
			public TextView commcountsTextView;
			public TextView likecountsTextView;
			public ImageView commentImageView;
			public ImageView retweetImageView;
			public ImageView ilikeitImageView;
			public LinearLayout likeLinearLayout;
			public LinearLayout commlLinearLayout;
			public LinearLayout retweetLinearLayout;
			private ArrayList<String> indexImageArrayList = new ArrayList<String>();

		}

		public IndexListViewAdapter(Context context,
				ArrayList<UserMsg> listItems) {
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Log.e("method", "getView");
			if (convertView == null) {
				final ListViewItem listItemView = new ListViewItem();

				convertView = listContainer.inflate(R.layout.msg_card, null);
				
				listItemView.ilikeitImageView = (ImageView) convertView
						.findViewById(R.id.msg_ilike_image);
				listItemView.commlLinearLayout = (LinearLayout) convertView
						.findViewById(R.id.msg_comment_viewgroup);
				listItemView.likeLinearLayout = (LinearLayout) convertView
						.findViewById(R.id.msg_like_viewgroup);
				listItemView.retweetLinearLayout = (LinearLayout) convertView
						.findViewById(R.id.msg_retweet_viewgroup);
				listItemView.commentImageView = (ImageView) convertView
						.findViewById(R.id.msg_comment_image);
				listItemView.retweetImageView = (ImageView) convertView
						.findViewById(R.id.msg_retweet_image);
				listItemView.content = (TextView) convertView
						.findViewById(R.id.image_index_text);
				listItemView.date = (TextView) convertView
						.findViewById(R.id.time_text);
				listItemView.name = (TextView) convertView
						.findViewById(R.id.name_text);
				listItemView.commcountsTextView = (TextView) convertView
						.findViewById(R.id.msg_comment_num_textview);
				listItemView.likecountsTextView = (TextView) convertView
						.findViewById(R.id.msg_like_num_textview);
				
				listItemView.headImageView = (ImageView)convertView.findViewById(R.id.head_photo);
				if(listItemView.name.getText().toString().equals("飞翔的企鹅")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp4.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("范海辛")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp1.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("武大郎")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp6.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}
				else if(listItemView.name.getText().toString().equals("coocol")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp5.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("飞翔的企鹅")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp2.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("春暖花开")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp3.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}
				listItemView.headImageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						getIntent().putExtra("itemusername", listItemView.name.getText().toString());
						Intent intent = new Intent(getApplicationContext(),ItemUserInfoActivity.class);
						startActivity(intent);
						
					}
				});
				final String picString = listItems.get(position).getPicString();
				if(picString!=null &&!picString.equals("")&& !picString.equals("null")){
					listItemView.image = (ImageView)convertView.findViewById(R.id.image_index);
					listItemView.image2 = (ImageView)convertView.findViewById(R.id.image_index2);
					listItemView.image3 = (ImageView)convertView.findViewById(R.id.image_index3);
					String[] picStrings = picString.split("&");
					if(picStrings.length==1){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						listItemView.image2.setImageBitmap(null);
						listItemView.image3.setImageBitmap(null);
						listItemView.indexImageArrayList.add(picStrings[0]);
					}if(picStrings.length==2){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[1]);
						listItemView.image2.setImageBitmap(bmpBitmap);
						listItemView.image3.setImageBitmap(null);
						listItemView.indexImageArrayList.add(picStrings[1]);
					}if(picStrings.length==3){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[1]);
						listItemView.image2.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[2]);
						listItemView.image3.setImageBitmap(bmpBitmap);
					}
				}
				listItemView.centeLinearLayout = (LinearLayout)convertView.findViewById(R.id.msg_card_centerbar);
				listItemView.centeLinearLayout.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ArrayList<String> list= new ArrayList<String>();;
						
						String ss = picString;
						Intent intent =  new Intent(MainPageActivity.this, com.example.imagej.ScanJ.ImageSwitcher.class);
						
						String picsc[] = ss.split("&");
						
						if(picsc.length==1){
							list.add(picsc[0]);
							intent.putStringArrayListExtra("pathes", list);
							intent.putExtra("index", 0);
							startActivity(intent);
						}if(picsc.length==2){
							list.add(picsc[0]);
							list.add(picsc[1]);
							intent.putStringArrayListExtra("pathes", list);
							intent.putExtra("index", 0);
							startActivity(intent);
						}
					}
				});
				listItemView.likecountsTextView.setText(String
						.valueOf(listItems.get(position).getLikecounts()));
				int commnum = getNewCommNum();
				if(commnum!=-10 && commnum==position){
					int t = Integer.parseInt(listItems.get(commnum).getCommcounts());
					String tmpString = String.valueOf(t+1);
					listItems.get(commnum).setCommcounts(tmpString);
				}
					listItemView.commcountsTextView.setText(String
							.valueOf(listItems.get(position).getCommcounts()));
				
				listItemView.content.setText((String) listItems.get(position)
						.getTextinfo());
				listItemView.date.setHint((String) listItems.get(position)
						.getSharetime());
				listItemView.date.setText(TimeConvert
						.getTimeDiff((String) listItems.get(position)
								.getSharetime()));
				listItemView.name.setText((String) listItems.get(position)
						.getUsername());
				listItemView.ilikeitImageView
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								listItemView.ilikeitImageView
										.setImageResource(R.drawable.skin_feed_icon_praise);
								int likwcounts = Integer
										.parseInt(listItemView.likecountsTextView
												.getText().toString()) + 1;
								listItemView.likecountsTextView.setText(String
										.valueOf(likwcounts));
								makeCustomToast("赞  +1 ", Toast.LENGTH_SHORT);
								AppValues.userMsgsLists.get(position)
										.setLikecounts(
												String.valueOf(likwcounts));
								HandleILikeIt handleILikeIt = new HandleILikeIt();
								handleILikeIt.setData(listItemView.name
										.getText().toString(),
										listItemView.date.getText().toString(),
										String.valueOf(likwcounts));
								handleILikeIt.execute((Void) null);
							}
						});
				listItemView.likeLinearLayout
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								listItemView.ilikeitImageView
										.setImageResource(R.drawable.skin_feed_icon_praise);
								int likwcounts = Integer
										.parseInt(listItemView.likecountsTextView
												.getText().toString()) + 1;
								listItemView.likecountsTextView.setText(String
										.valueOf(likwcounts));
								makeCustomToast("赞  +1 ", Toast.LENGTH_SHORT);
								AppValues.userMsgsLists.get(position)
										.setLikecounts(
												String.valueOf(likwcounts));
								HandleILikeIt handleILikeIt = new HandleILikeIt();
								handleILikeIt.setData(listItemView.name
										.getText().toString(),
										listItemView.date.getHint().toString(),
										String.valueOf(likwcounts));
								handleILikeIt.execute((Void) null);
							}
						});
				listItemView.commentImageView
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent();
								intent.setClass(MainPageActivity.this,
										MsgDetailsActivity.class);
								intent.putExtra("msgusername",
										listItemView.name.getText());
								String namesString = listItems.get(position).getUsername();
								intent.putExtra("username", namesString);
								intent.putExtra("sendtime", listItemView.date
										.getHint().toString());
								intent.putExtra("postion", position);
								intent.putExtra("contenttext",
										listItemView.content.getText());
								String commcountString = listItemView.commcountsTextView
										.getText().toString();
								intent.putExtra("commcounts", commcountString);
								startActivity(intent);
							}
						});
				listItemView.commlLinearLayout
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub

								Intent intent = new Intent();
								intent.setClass(MainPageActivity.this,
										MsgDetailsActivity.class);
								intent.putExtra("msgusername",
										listItemView.name.getText());
								String namesString = listItems.get(position).getUsername();
								intent.putExtra("username", namesString);
								intent.putExtra("postion", position);
								intent.putExtra("sendtime", listItemView.date
										.getHint().toString());
								intent.putExtra("contenttext",
										listItemView.content.getText());
								String commcountString = listItemView.commcountsTextView
										.getText().toString();
								intent.putExtra("commcounts", commcountString);
								startActivity(intent);
							}
						});
				convertView.setTag(listItemView);
				return convertView;
			} else {
				final ListViewItem listItemView = (ListViewItem) convertView
						.getTag();
				listItemView.headImageView = (ImageView)convertView.findViewById(R.id.head_photo);
				if(listItemView.name.getText().toString().equals("飞翔的企鹅")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp4.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("范海辛")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp1.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("武大郎")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp6.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}
				else if(listItemView.name.getText().toString().equals("coocol")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp5.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("飞翔的企鹅")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp2.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}else if(listItemView.name.getText().toString().equals("春暖花开")){
					Bitmap bmpBitmap = BitmapFactory.decodeFile("/storage/emulated/0/headpic/hhpp3.jpg");
					listItemView.headImageView.setImageBitmap(bmpBitmap);
				}
				listItemView.headImageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						itemUsernameString = listItemView.name.getText().toString();
						PlaceholderFragment placeholderFragment = new PlaceholderFragment();
						placeholderFragment.setPosition(1);
						FragmentManager fragmentManager = getSupportFragmentManager();
						fragmentManager.beginTransaction().replace(R.id.container,// inFragment).commit();
								placeholderFragment).commit();
						
					}
				});
				final String picString = listItems.get(position).getPicString();
				if(picString!=null &&!picString.equals("")&& !picString.equals("null")){
					String[] picStrings = picString.split("&");
					if(picStrings.length==1){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						listItemView.image2.setImageBitmap(null);
						listItemView.image3.setImageBitmap(null);
						listItemView.indexImageArrayList.add(picStrings[0]);
					}if(picStrings.length==2){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[1]);
						listItemView.image2.setImageBitmap(bmpBitmap);
						listItemView.image3.setImageBitmap(null);
						listItemView.indexImageArrayList.add(picStrings[1]);
					}if(picStrings.length==3){
						Bitmap bmpBitmap = BitmapFactory.decodeFile(picStrings[0]);
						listItemView.image.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[1]);
						listItemView.image2.setImageBitmap(bmpBitmap);
						bmpBitmap = BitmapFactory.decodeFile(picStrings[2]);
						listItemView.image3.setImageBitmap(bmpBitmap);
					}
					
				}
				listItemView.likeLinearLayout
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// listItemView.ilikeitImageView.setImageResource(R.drawable.skin_feed_icon_praise);
								listItemView.ilikeitImageView
										.setImageResource(R.drawable.skin_feed_icon_praise);
								int likwcounts = Integer
										.parseInt(listItemView.likecountsTextView
												.getText().toString()) + 1;
								listItemView.likecountsTextView.setText(String
										.valueOf(likwcounts));
								AppValues.userMsgsLists.get(position)
										.setLikecounts(
												String.valueOf(likwcounts));
								makeCustomToast("赞  +1 ", Toast.LENGTH_SHORT);
								HandleILikeIt handleILikeIt = new HandleILikeIt();
								handleILikeIt.setData(listItemView.name
										.getText().toString(),
										listItemView.date.getHint().toString(),
										String.valueOf(likwcounts));
								handleILikeIt.execute((Void) null);

							}
						});
				listItemView.ilikeitImageView
						.setOnClickListener(new View.OnClickListener() {
							@SuppressLint("ResourceAsColor")
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// listItemView.ilikeitImageView.setImageResource(R.drawable.skin_feed_icon_praise);
								listItemView.ilikeitImageView
										.setImageResource(R.drawable.skin_feed_icon_praise);
								int likwcounts = Integer
										.parseInt(listItemView.likecountsTextView
												.getText().toString()) + 1;
								listItemView.likecountsTextView.setText(String
										.valueOf(likwcounts));
								makeCustomToast("赞  +1 ", Toast.LENGTH_SHORT);
								AppValues.userMsgsLists.get(position)
										.setLikecounts(
												String.valueOf(likwcounts));
								HandleILikeIt handleILikeIt = new HandleILikeIt();
								handleILikeIt.setData(listItemView.name
										.getText().toString(),
										listItemView.date.getText().toString(),
										String.valueOf(likwcounts));
								handleILikeIt.execute((Void) null);

							}
						});
				listItemView.commlLinearLayout
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.setClass(MainPageActivity.this,
										MsgDetailsActivity.class);
								intent.putExtra("msgusername",
										listItemView.name.getText());
								String namesString = listItems.get(position).getUsername();
								intent.putExtra("username", namesString);
								intent.putExtra("postion", position);
								intent.putExtra("sendtime", listItemView.date
										.getHint().toString());
								intent.putExtra("contenttext",
										listItemView.content.getText());
								String commcountString = listItemView.commcountsTextView
										.getText().toString();
								intent.putExtra("commcounts", commcountString);
								startActivity(intent);
							}
						});

				listItemView.commentImageView
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.setClass(MainPageActivity.this,
										MsgDetailsActivity.class);
								intent.putExtra("msgusername",
										listItemView.name.getText());
								String namesString = listItems.get(position).getUsername();
								intent.putExtra("username", namesString);
								intent.putExtra("postion", position);
								intent.putExtra("sendtime", listItemView.date
										.getHint().toString());
								intent.putExtra("contenttext",
										listItemView.content.getText());
								String commcountString = listItemView.commcountsTextView
										.getText().toString();
								intent.putExtra("commcounts", commcountString);
								startActivity(intent);
							}
						});
				listItemView.likecountsTextView.setText(String
						.valueOf(listItems.get(position).getLikecounts()));
				int commnum = getNewCommNum();
				if(commnum!=-10 && commnum==position){
					int t = Integer.parseInt(listItems.get(commnum).getCommcounts());
					String tmpString = String.valueOf(t+1);
					listItems.get(commnum).setCommcounts(tmpString);
				}
				
				listItemView.centeLinearLayout.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ArrayList<String> list= new ArrayList<String>();;
						
						String ss = picString;
						Intent intent =  new Intent(MainPageActivity.this, com.example.imagej.ScanJ.ImageSwitcher.class);
						
						String picsc[] = ss.split("&");
						
						if(picsc.length==1){
							list.add(picsc[0]);
							intent.putStringArrayListExtra("pathes", list);
							intent.putExtra("index", 0);
							startActivity(intent);
						}if(picsc.length==2){
							list.add(picsc[0]);
							list.add(picsc[1]);
							intent.putStringArrayListExtra("pathes", list);
							intent.putExtra("index", 0);
							startActivity(intent);
						}
					
					}
				});
			
				listItemView.commcountsTextView.setText(String
						.valueOf(listItems.get(position).getCommcounts()));
				listItemView.content.setText((String) listItems.get(position)
						.getTextinfo());
				listItemView.date.setText(TimeConvert
						.getTimeDiff((String) listItems.get(position)
								.getSharetime()));
				listItemView.date.setHint((String) listItems.get(position)
						.getSharetime());
				listItemView.name.setText((String) listItems.get(position)
						.getUsername());
				convertView.setTag(listItemView);
				return convertView;
			}
		}
		

		public class HandleILikeIt extends AsyncTask<Void, Void, Boolean> {

			private String resString;

			private String name;
			private String time;
			private String counts;

			public void setData(String name, String time, String counts) {
				this.name = name;
				this.time = time;
				this.counts = counts;
			}

			public class GsonLike {
				private String status;

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getLikecounts() {
					return likecounts;
				}

				public void setLikecounts(String likecounts) {
					this.likecounts = likecounts;
				}

				private String likecounts;

			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				String jsString = new ZhituNetWork().handleILikeIt(name, time,
						counts);

				if (jsString != null) {
					Gson gson = new Gson();
					GsonLike gsonLike = gson.fromJson(jsString, GsonLike.class);
					if (gsonLike.getStatus().equals("success")) {
						resString = gsonLike.getLikecounts();
						return true;
					} else {
						return false;
					}
				}
				return false;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				if (success) {

				}
			}
		}

	}
	
	public void UserHeadPhoto(View V){
		//调用图库
		String path = "/f/f.jpg";
		tmpUserInfo.setHeadphoto(path);
	}

	public void UserName(View v) {

		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView text1 = (TextView) findViewById(R.id.userinfo_name);
						String BirthdayDate = editText.getText().toString();
						text1.setText(BirthdayDate);
					}
				}).setNegativeButton("取消", null).show();

		/*
		 * Intent intent = new Intent();
		 * 
		 * intent.setClass(PersonalDetial.this,UserName.class); TextView text =
		 * (TextView) findViewById(R.id.textView2); String
		 * name=text.getText().toString(); intent.putExtra("username",name);
		 * startActivity(intent); //finish();
		 */
	}

	public void Set_Gender(View v) {

		new AlertDialog.Builder(this)
				.setTitle("请选择")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(new String[] { "男", "女", "第三种" }, 8,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
								{
									TextView text = (TextView) findViewById(R.id.userinfo_gender);
									text.setText("男");
									tmpUserInfo.setGender(text.getText().toString());
								}
									break;
								case 1:
								{
									TextView text1 = (TextView) findViewById(R.id.userinfo_gender);
									text1.setText("女");
									tmpUserInfo.setGender(text1.getText().toString());
								}
									break;
								case 2:
								{
									TextView text3 = (TextView) findViewById(R.id.userinfo_gender);
									text3.setText("第三种");
								}
									break;
								}
							}
						}

				).setPositiveButton("确定", null).show();
	}

	public void Set_Lable(View v) {


		final String[] arrayFruit = new String[] { "死宅", "萌妹子", "女汉子", "腐女",
				"萝莉", "御姐", "屌丝", "男神", "宠物", "旅行", "时尚", "摄影", "音乐" };
		final boolean[] arrayFruitSelected = new boolean[] { false, false,
				false, false, false, false, false, false, false, false, false,
				false, false };

		Dialog alertDialog = new AlertDialog.Builder(this)
				.setTitle("个性标签")
				.setIcon(R.drawable.ic_launcher)
				.setMultiChoiceItems(arrayFruit, arrayFruitSelected,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								arrayFruitSelected[which] = isChecked;
							}
						})
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder stringBuilder = new StringBuilder();
						for (int i = 0; i < arrayFruitSelected.length; i++) {
							if (arrayFruitSelected[i] == true) {
								stringBuilder.append(arrayFruit[i] + "、");
							}
						}
						TextView text3 = (TextView) findViewById(R.id.userinfo_label);
						text3.setText(stringBuilder.toString());
						tmpUserInfo.setLabel(text3.getText().toString());
						// Toast.makeText(PersonalDetial.this,
						// stringBuilder.toString(), Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
		alertDialog.show();
	}

	public void Autograph(View v) {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView text1 = (TextView) findViewById(R.id.userinfo_person);
						String BirthdayDate = editText.getText().toString();
						text1.setText(BirthdayDate);
					}
				}).setNegativeButton("取消", null).show();
		tmpUserInfo.setPerson(editText.getText().toString());
	}

	public void Set_Birthday(View v) {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView text1 = (TextView) findViewById(R.id.userinfo_birthday);
						String BirthdayDate = editText.getText().toString();
						text1.setText(BirthdayDate);
					}
				}).setNegativeButton("取消", null).show();
		tmpUserInfo.setBirthday(editText.getText().toString());
	}

	public void MailBox(View v) {

		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView text1 = (TextView) findViewById(R.id.userinfo_email);
						String BirthdayDate = editText.getText().toString();
						text1.setText(BirthdayDate);
					}
				}).setNegativeButton("取消", null).show();
		
	}



}
