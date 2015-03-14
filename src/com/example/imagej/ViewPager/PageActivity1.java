package com.example.imagej.ViewPager;



import java.lang.reflect.Field;


import java.lang.reflect.Method;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.os.Build;

import com.example.imagej.EdingJ.MainActivity;
import com.example.imagej.ScanJ.MainScanActivity;
import com.zhitu.xxf.R;
public class PageActivity1  extends FragmentActivity{

	private FoodFragment foodFragment ;
	private TravelFragment travelFragment;
	private SpaceFragment spaceFragment;
	private ClothesFragment clothesFragment;
	private PagerSlidingTabStrip tabs;
	private DisplayMetrics dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pageview1);

		setOverflowShowingAlways();
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "����", "����", "��ʳ","�ռ�"};

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (travelFragment == null) {
					travelFragment = new TravelFragment();
				}
				return travelFragment;
				
			case 1:
				if (clothesFragment == null) {
					clothesFragment = new ClothesFragment();
				}
				return clothesFragment;
			case 2:
				if (foodFragment == null) {
					foodFragment = new FoodFragment();
				}
				return foodFragment;
			case 3:
				if (spaceFragment == null) {
					spaceFragment = new SpaceFragment();
				}
				return spaceFragment;
			default:
				return null;
			}
		}

	}
	
	
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕�?		
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是�?明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高�?		
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大�?		
		tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜�?		
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜�?(这是我自定义的一个方�?
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景�?		
		tabs.setTabBackground(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
 
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_center, menu);
		return true; 
	} 
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) { 
				}
			}
		}
		return super.onMenuOpened(featureId, menu); 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();  
		switch (id) {  
		case R.id.action_personalzhitu: 

			break;
		case R.id.action_imagescan:  
			Intent intent2 = new Intent(PageActivity1.this,MainScanActivity.class);
			startActivity(intent2);
			this.finish();
			break;
		case R.id.action_edting:
			Intent intent3 = new Intent(PageActivity1.this,MainActivity.class);
			startActivity(intent3);
			this.finish();
			break;
		default: 
			break;
		}
		return super.onOptionsItemSelected(item);
	}




	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
