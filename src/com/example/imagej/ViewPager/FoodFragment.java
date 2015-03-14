package com.example.imagej.ViewPager;

import java.io.File;
import java.util.ArrayList;

import com.zhitu.xxf.R;
import com.example.imagej.ScanJ.ChildAdapter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class FoodFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ChildAdapter adapter;
		GridView mGridView;
		ArrayList<String> list=new ArrayList<String>();
		View view = inflater.inflate(R.layout.show_image_activity, container, false);
		mGridView =(GridView) view.findViewById(R.id.child_grid);
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver mContentResolver =getActivity().getContentResolver();
		//只查询jpeg和png的图片
		Cursor mCursor = mContentResolver.query(mImageUri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
		
		while (mCursor.moveToNext()) {
			//获取图片的路径
			String path = mCursor.getString(mCursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			//获取父文件夹的路径
			String parentName = new File(path).getParentFile().getName();
			String folderName = "food";
			if(parentName.equals(folderName))
			{
				list.add(path);
			}
		}
		adapter = new ChildAdapter(getActivity(), list, mGridView);
		mGridView.setAdapter(adapter);
		return view;
	}
}
