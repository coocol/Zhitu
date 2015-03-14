package com.example.imagej;


import com.example.imagej.EdingJ.Activity_ImageZoom;
import com.example.imagej.EdingJ.MainActivity;
import com.example.imagej.ScanJ.MainScanActivity;
import com.example.imagej.ViewPager.PageActivity1;
import com.zhitu.xxf.R.color;
import com.zhitu.xxf.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.os.Build;

public class ImageViewActivity extends TabActivity implements OnCheckedChangeListener{

	private RadioGroup group;
	private TabHost tabHost;
	public static final String TAB_ITEM_1 = "tabItem1";
	public static final String TAB_ITEM_2 = "tabItem2";
	public static final String TAB_ITEM_3 = "tabItem3";
	public static final String TAB_ITEM_4 = "tabItem4";
	public static final String TAB_ITEM_5 = "tabItem5";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		group = (RadioGroup) findViewById(R.id.main_radio);
		group.setOnCheckedChangeListener(this);
		
		tabHost = this.getTabHost();
		
		TabSpec tab1 = tabHost.newTabSpec(TAB_ITEM_1);
		TabSpec tab2 = tabHost.newTabSpec(TAB_ITEM_2);
		TabSpec tab3 = tabHost.newTabSpec(TAB_ITEM_3);
		TabSpec tab4 = tabHost.newTabSpec(TAB_ITEM_4);
		TabSpec tab5 = tabHost.newTabSpec(TAB_ITEM_5);
		
		tab1.setIndicator(TAB_ITEM_1).setContent(new Intent(this, PageActivity1.class));
		tab2.setIndicator(TAB_ITEM_2).setContent(new Intent(this, MainScanActivity.class));
		tab3.setIndicator(TAB_ITEM_3).setContent(new Intent(this, MainActivity.class));
		tab4.setIndicator(TAB_ITEM_4).setContent(new Intent(this, MainActivity.class));
		tab5.setIndicator(TAB_ITEM_5).setContent(new Intent(this, MainActivity.class));
		
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		tabHost.addTab(tab4);
		tabHost.addTab(tab5);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.radio_button1:
			tabHost.setCurrentTabByTag(TAB_ITEM_1);
			break;
		case R.id.radio_button2:
			tabHost.setCurrentTabByTag(TAB_ITEM_2);
			break;
		case R.id.radio_button3:
			tabHost.setCurrentTabByTag(TAB_ITEM_3);
			break;
		case R.id.radio_button4:
			tabHost.setCurrentTabByTag(TAB_ITEM_4);
			break;
		case R.id.radio_button5:
			tabHost.setCurrentTabByTag(TAB_ITEM_5);
			break;
		default:
			break;
		}
	}
}
