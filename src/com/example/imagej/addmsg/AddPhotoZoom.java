package com.example.imagej.addmsg;

import java.io.File;
import java.io.IOException;
import com.example.imagej.EdingJ.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhitu.xxf.R;
@TargetApi(8)
public class AddPhotoZoom extends Activity {
	private static final String IMAGE_UNSPECIFIED = "image/*";  
	
	private static final int PHOTORESOULT = 3;// 结果   
	
	private static final int CROP_BIG_PICTURE = 0x12;

	File mfile,RetFile;
	
	WebView WV_View;
	TextView TV_Info,TV_Title;
	LinearLayout LL_ToolBar;
	
	
	String strFormat_ImageZoom;
	float scale = 1;
	float degree = 0;
	int reverseflag = 0;

    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_imagezoom);
		
		TV_Title = (TextView) findViewById(R.id.TV_Title);

		WV_View = (WebView) findViewById(R.id.WV_View);
		WV_View.getSettings().setSupportZoom(true); // 启用页面的缩放
		WV_View.getSettings().setBuiltInZoomControls(true);
		WV_View.setClickable(true);
//		WV_View.getSettings().setSupportZoom(true); // 启用页面的缩放
//		WV_View.setOnTouchListener(OnTouch_WebView);// 监听触摸事件
//		WV_View.setClickable(true);  
		
		TV_Info = (TextView) findViewById(R.id.TV_Info);
		LL_ToolBar = (LinearLayout)findViewById(R.id.LL_ToolBar);
		
		ImageButton IB_ZoomIn = (ImageButton) findViewById(R.id.IB_ZoomIn);
		IB_ZoomIn.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(scale>0.1)
				{
					scale -= 0.1;
					ImageMatrix();
				}
			}
		});
		ImageButton IB_ZoomOut = (ImageButton) findViewById(R.id.IB_ZoomOut);
		IB_ZoomOut.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(scale<1)
				{
					scale += 0.1;
					ImageMatrix();
				}
			}
		});
		ImageButton IB_RotateLeft = (ImageButton) findViewById(R.id.IB_RotateLeft);
		IB_RotateLeft.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				degree -= 90;
				degree = degree%360;
				ImageMatrix();
			}
		});
		ImageButton IB_RotateRight = (ImageButton) findViewById(R.id.IB_RotateRight);
		IB_RotateRight.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				degree += 90;
				degree = degree%360;
				ImageMatrix();
			}
		});
		ImageButton IB_Crop = (ImageButton) findViewById(R.id.IB_Crop);
		IB_Crop.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				RetFile.renameTo(mfile);
				mfile = RetFile;
				scale = 1;
				degree = 0;
				reverseflag = 0;
				File f = new File(getExternalCacheDir(),"c"+mfile.getName());
				CropImageUri(Uri.fromFile(mfile),Uri.fromFile(f),CROP_BIG_PICTURE);
			}
		});
		ImageButton IB_SwapH = (ImageButton) findViewById(R.id.IB_SwapH);
		IB_SwapH.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				reverseflag ^= 1;
				ImageMatrix();
			}
		});
		ImageButton IB_SwapV = (ImageButton) findViewById(R.id.IB_SwapV);
		IB_SwapV.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				reverseflag ^= 2;
				ImageMatrix();
			}
		});
		
		ImageButton IB_LogoImg = (ImageButton) findViewById(R.id.IB_LogoImg);
		IB_LogoImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ImageButton IB_Setup = (ImageButton) findViewById(R.id.IB_Function);
		IB_Setup.setBackgroundResource(R.drawable.bt_ok);
		IB_Setup.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//保存结果
//				Global.SaveFile(getCacheDir(), mfile.getName(),	ImageDispose.Bitmap2Bytes(RetBmp));
//				RetFile.renameTo(mfile);
				scale = 1;
				degree = 0;
				reverseflag = 0;

//				Intent i = new Intent();  
//		        Bundle b = new Bundle();  
//		        b.putString("CALCULATION", value);  
//		        i.putExtras(b);  
				Intent aintent = new Intent(AddPhotoZoom.this, AddPhoto.class);
				/* 将数据打包到aintent Bundle 的过程略 */
		        Bundle b = new Bundle();  
		        b.putString("mFile", RetFile.getPath());  
		        aintent.putExtras(b);  
		        setResult(RESULT_OK,aintent);  			
		        finish();
			}
		});

		//载入显示模板
		try {
			strFormat_ImageZoom = Global.GetString(AddPhotoZoom.this.getAssets().open("imagezoom.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent callerIntent = getIntent();  
		mfile = new File(callerIntent.getStringExtra("mfile")); 
		TV_Title.setText(mfile.getName());
		UpdateUI(mfile);
	}
	

	float lastTouchX, lastTouchY,ScaleDefault;
	long downTime,UpTime = 0;
	boolean isZoomIn = false;
	/**
	 * 浏览器手势
	 * 上下查看回复邮件
	 * 左右切换主邮件
	 * 双击缩放
	 */
	OnTouchListener OnTouch_WebView = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent evt) {
			// TODO Auto-generated method stub
			switch (evt.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastTouchX = evt.getX();
				lastTouchY = evt.getY();
				downTime = evt.getEventTime();			
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				float currentX = evt.getX();
				float currentY = evt.getY();
				long currentTime = evt.getEventTime();
				float xdifference = Math.abs(lastTouchX - currentX);
				float ydifference = Math.abs(lastTouchY - currentY);
				long time = currentTime - downTime;
				
				if(ydifference<100 && xdifference<100 && time<200)
				{
					Long uptime = currentTime - UpTime;
					UpTime = currentTime;

					float s = WV_View.getScale();
					if(ScaleDefault == 0)
						ScaleDefault = s;
					
					if(uptime < 300)
					{
						if(s == ScaleDefault)
							isZoomIn = true;
						
						if(s == 2*ScaleDefault)
							isZoomIn = false;
							
						if(isZoomIn)
						{
							WV_View.zoomIn();
							WV_View.zoomIn();
							WV_View.zoomIn();
							WV_View.zoomIn();
						}else
						{
							WV_View.zoomOut();
							WV_View.zoomOut();
							WV_View.zoomOut();
							WV_View.zoomOut();
						}
						
						
//						TV_Title.setText("附件处理:"+(int)(WV_View.getScale()*50/ScaleDefault)+"%");
					}
				}else 
				{
					UpTime = 0;
				}
			}
			return false;
		}
	};

	/**
	 * 图像处理
	 */
	private void ImageMatrix()
	{
		if(degree==0 && reverseflag==0 && scale == 1)
		{
			UpdateUI(mfile);
			return;
		}
		
		Bitmap bm = ImageDispose.getBitmapFromFile(mfile,1800,1800);
		Bitmap bmp = ImageDispose.toMatrix(bm, degree,scale,reverseflag);
		bm.recycle();
		bm=null;
		System.gc();
		
		File f = new File(getExternalCacheDir(),"z"+mfile.getName());
		if(Global.SaveFile(f,ImageDispose.Bitmap2Bytes(bmp)))
		{
			UpdateUI(f);
		}
		bmp.recycle();
		bmp=null;
		System.gc();
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
				RetFile = f;
				
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
				LL_ToolBar.setVisibility(LinearLayout.INVISIBLE);
			}
		}
	}
	
	public void startPhotoCrop(Uri uri) {  
        Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);  
//      intent.setDataAndType(Uri.fromFile(tempFile), "image/*");

        intent.putExtra("scale", true); 
		intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);  
        intent.putExtra("output", uri);			// 保存到原文件
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, PHOTORESOULT);  
    }  
	
	/**
	 * 图像剪裁
	 * @param uri		原始图片uri
	 * @param outuri	输出的图片uri
	 * @param requestCode 
	 */
	private void CropImageUri(Uri uri,Uri outuri, int requestCode){
		 Intent intent = new Intent("com.android.camera.action.CROP");
		 intent.setDataAndType(uri, "image/*");
		 intent.putExtra("crop", "true");
//		 intent.putExtra("aspectX", 2);
//		 intent.putExtra("aspectY", 1);
//		 intent.putExtra("outputX", outputX);
//		 intent.putExtra("outputY", outputY);
		 intent.putExtra("scale", true);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
		 intent.putExtra("return-data", false);
		 intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		 intent.putExtra("noFaceDetection", true); // no face detection
		 startActivityForResult(intent, requestCode);
	}

	
	 public void startPhotoCrop(File f) {  
        startPhotoCrop(Uri.fromFile(f));
    } 

	// 处理返回的结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
	case CROP_BIG_PICTURE://from crop_big_picture
//		 Log.d(TAG, "CROP_BIG_PICTURE: data = " + data);//it seems to be null
//			imageUri = data.getData();
		File f = new File(getExternalCacheDir(),"c"+mfile.getName());
		if(f.length()>0)
		{
			f.renameTo(mfile);
			UpdateUI(mfile);
		}
		
//			if(imageUri != null){
//			  Bitmap bitmap = decodeUriAsBitmap(imageUri);
//			  img_shower.setImageBitmap(bitmap);
//			 }
			 break;
		}

	}
}
