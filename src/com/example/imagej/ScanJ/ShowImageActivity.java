package com.example.imagej.ScanJ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.imagej.ScanJ.Constant.gridItemEntity;




import com.zhitu.xxf.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class ShowImageActivity extends Activity{
	private GridView mGridView;
	private ArrayList<String> list=new ArrayList<String>();
	private ChildAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_image_activity);
		
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");
		
		adapter = new ChildAdapter(this, list, mGridView);
		mGridView.setAdapter(adapter);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id){
				Intent it = new Intent(ShowImageActivity.this, com.example.imagej.ScanJ.ImageSwitcher.class);
				it.putStringArrayListExtra("pathes", list);
				it.putExtra("index", position);
				startActivity(it);

			}
		});
		
		
	}

	
}
