package com.example.imagej.EdingJ;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.os.Build;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;



import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.imagej.ScanJ.MainScanActivity;
import com.example.imagej.ViewPager.PageActivity1;
import com.zhitu.xxf.R;

public class MainActivity extends Activity{

	public static final int REQ_FROM_PHOTO  = 1;	
	public static final int REQ_FROM_CAMERA = 2;
	public static final int REQ_FROM_PHOTOEDIT = 3; 
	public static final int REQ_FROM_SETUP  = 4; 
	
	WebView WV_View;
	TextView TV_Info;
	
	int CameraPhoto_Num = 0;
	String strFormat_ImageZoom;
	File mFile;
	
	String savepath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		ImageButton IB_LogoImg = (ImageButton) findViewById(R.id.IB_LogoImg);
//      IB_LogoImg.setBackgroundResource(R.drawable.ic_launcher);
//		ImageButton IB_Function = (ImageButton) findViewById(R.id.IB_Function);
//		IB_Function.setVisibility(ImageButton.INVISIBLE);
        
//      TextView TV_Title = (TextView) findViewById(R.id.TV_Title);
//      yTV_Title.setText("图像处理(⊙v⊙)");
       
        WV_View = (WebView) findViewById(R.id.WV_View);
//        WV_View.getSettings().setJavaScriptEnabled(true);
		WV_View.getSettings().setSupportZoom(true); // 启用页面的缩放
		WV_View.getSettings().setBuiltInZoomControls(true);
		WV_View.setClickable(true); 
//		WV_View.loadUrl("file:///android_asset/load.html");
		
		TV_Info = (TextView) findViewById(R.id.TV_Info);
		TV_Info.setText("图片未打开");
		
		
		String[] items = {"旅行", "穿搭","美食","空间" };	
		//载入显示模板
		try {
			strFormat_ImageZoom = Global.GetString(MainActivity.this.getAssets().open("imagezoom.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ImageButton IB_Camera = (ImageButton) findViewById(R.id.IB_Camera);
        IB_Camera.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// 从相机中获得照片								
				Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);		
				File f = new File(getExternalCacheDir(),"Camera_"+CameraPhoto_Num+".jpg");
				intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				startActivityForResult(intentCamera, REQ_FROM_CAMERA);
			}
		});
        
        ImageButton IB_Photo = (ImageButton) findViewById(R.id.IB_Photo);
        IB_Photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 从相簿中获得照片
				Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
				mIntent.addCategory(Intent.CATEGORY_OPENABLE);
				mIntent.setType("image/*");
				startActivityForResult(mIntent, REQ_FROM_PHOTO);
			}
		});
        
        ImageButton IB_Edit = (ImageButton) findViewById(R.id.IB_Painting);
        IB_Edit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// 编辑相片
				if(mFile == null)
				{
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("尚未选中图片!").show();
					
				}
				else if(Global.isImageFile(mFile.getName())) 
				{
					Intent intent = new Intent(MainActivity.this, com.example.imagej.EdingJ.Activity_ImageZoom.class);
					intent.putExtra("mfile", mFile.getPath());
					startActivityForResult(intent, REQ_FROM_PHOTOEDIT);
				}
				
			}
		});
        
        ImageButton IB_Save = (ImageButton) findViewById(R.id.IB_Save);
        IB_Save.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// 保存结果
				if(mFile != null)
				{			
					et = new EditText(MainActivity.this);  
					et.setHint("请在此处输入图片名称");
					String[] items = {"旅行", "穿搭","美食","空间" };	
					savepath = Global.DIR_Work;
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("保存图片")
						.setIcon(android.R.drawable.ic_dialog_info)
						//.setMessage("请输入用户名：")
						.setView(et)
						//checkItem = 8只是为了刚开始不选任何项  0.0
						.setSingleChoiceItems(items,8,new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) { 
									switch (which) {
									case 0:
										savepath = Global.DIR_Work;
										savepath += "/try/travel/";
										break;
									case 1:
										savepath = Global.DIR_Work;
										savepath += "/try/clothes/";
										break;
									case 2:
										savepath = Global.DIR_Work;
										savepath += "/try/food/";
										break;
									case 3:
										savepath = Global.DIR_Work;
										savepath += "/try/space/";
										break;
									default:
										savepath = Global.DIR_Work;
										break;
									}
								} 

						})
						//.setView(spinner)
						.setPositiveButton("确定", SaveFile)
						.setNegativeButton("取消", null).show();
				}
				else {
						new AlertDialog.Builder(MainActivity.this)
							.setTitle("不存在图片!").show();
				}
			}
		});
 }

	EditText et;  
    
    DialogInterface.OnClickListener SaveFile = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			String filename = et.getText().toString();
	    	if(!Global.isImageFile(filename))
			{
				filename = filename + ".jpg";
				File fd = new File(savepath,filename);				
				if(Global.CopyFile(mFile, fd))
				{
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("保存成功")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage(fd.getPath())
						.setPositiveButton("确定", null).show();
				}else
				{
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("保存失败")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage(fd.getPath())
						.setPositiveButton("确定", null).show();
				}
			}
	    	//广播！更新图！！
	    	sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
			
		}
	};
    
	/**
	 *  处理返回的结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode != Activity.RESULT_OK)
			return;
		
		if (requestCode == REQ_FROM_CAMERA) { // 从相机中返回的照片
			File f = new File(getExternalCacheDir(),"Camera_"+CameraPhoto_Num+++".jpg");
			if(f.length()>0)
			{
				UpdateUI(f);
			}
		}

		if (requestCode == REQ_FROM_PHOTO) { // 从相簿中返回的照片
			Uri uri= data.getData(); // 返回的是一个Uri
			String path = uri.getPath();
			File f = new File(path);
			if (!f.exists()) {
				path = GetPath(uri);
				f = new File(path);
			}
			
			if (f.exists())
				UpdateUI(f);
		}
		
		if (requestCode == REQ_FROM_PHOTOEDIT) { // 从图像编辑中返回
			String path = data.getStringExtra("mFile");
			File f = new File(path);
			if (f.exists())
				UpdateUI(f);
		}
	}
	
	
	/**
	 * 更新UI
	 * @param f
	 */
	private void UpdateUI(File f)
	{
		if(f != null)
		{
			if(Global.isImageFile(f.getName()))
			{				
				mFile = f;
				
				BitmapFactory.Options opts = null;
	            opts = new BitmapFactory.Options();
	            opts.inJustDecodeBounds = true;
	            BitmapFactory.decodeFile(f.getPath(), opts);
	            
	            int w = opts.outWidth;
	            int h = opts.outHeight;
				
	            WV_View.clearCache(true);
				String strHtml = String.format(strFormat_ImageZoom, f.getPath());
				String baseUrl = "file:///assets";
				WV_View.loadDataWithBaseURL(baseUrl, strHtml, "text/html", "utf-8",	null);
				
	            TV_Info.setText(Global.Format_Size(String.valueOf(f.length()))+" [" +w+" x "+h+"]");
			}else
			{
				String strHtml = String.format(strFormat_ImageZoom, "file:///assets/attachment.png");
				String baseUrl = "file:///assets";
				WV_View.loadDataWithBaseURL(baseUrl, strHtml, "text/html", "utf-8",	null);
			}
		}
	}

	/**
	 * 从uri得到真实的file路径
	 * @param uri
	 * @return
	 */
	private String GetPath(Uri uri) {
		// 
		String path = uri.getPath();
		File mfile = new File(path);
		if (mfile.exists()) 
			return mfile.getPath();
					
		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String s = cursor.getString(column_index);
//			cursor.close();
			return (s);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return (null);
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
			Intent intent1 = new Intent(MainActivity.this,PageActivity1.class);
			startActivity(intent1); 
			this.finish();
			break;
		case R.id.action_imagescan:  
			Intent intent2 = new Intent(MainActivity.this,MainScanActivity.class);
			startActivity(intent2);
			this.finish();
			break;
		case R.id.action_edting:
			
			break;
		default: 
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
