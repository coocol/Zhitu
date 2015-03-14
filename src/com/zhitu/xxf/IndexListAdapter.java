package com.zhitu.xxf;
import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class IndexListAdapter extends BaseAdapter {
	
	private ArrayList<String> mDataList;
	private Context context;
	
	public IndexListAdapter(Context context){
		this.context = context;
	}
	public void setArrayList(ArrayList<String> list) {
		mDataList = list;
	}
	public void init(){
		mDataList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {			
			mDataList.add("haha"+i);
		} 

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mDataList==null){
			return 0;
		}
		else {
			return mDataList.size();
		}
	}

	@Override
	public String getItem(int arg0) {
		return mDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(context);
		textView.setText(getItem(arg0));
		return textView;
	}

}
