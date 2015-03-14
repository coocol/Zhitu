package com.example.imagej.addmsg;

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

import com.zhitu.xxf.R;
import com.example.imagej.EdingJ.Global;
import com.example.imagej.EdingJ.MainActivity;

public class AddPhoto extends Activity{
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

        WV_View = (WebView) findViewById(R.id.WV_View);
//        WV_View.getSettings().setJavaScriptEnabled(true);
		WV_View.getSettings().setSupportZoom(true); // ����ҳ�������
		WV_View.getSettings().setBuiltInZoomControls(true);
		WV_View.setClickable(true); 
//		WV_View.loadUrl("file:///android_asset/load.html");
		
		TV_Info = (TextView) findViewById(R.id.TV_Info);
		TV_Info.setText("ͼƬδ��");
		
		
		String[] items = {"����", "����","��ʳ","�ռ�" };	
		//������ʾģ��
		try {
			strFormat_ImageZoom = Global.GetString(AddPhoto.this.getAssets().open("imagezoom.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ImageButton IB_Camera = (ImageButton) findViewById(R.id.IB_Camera);
        IB_Camera.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// ������л����Ƭ								
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
				// ���ಾ�л����Ƭ
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
				// �༭��Ƭ
				if(mFile == null)
				{
					new AlertDialog.Builder(AddPhoto.this)
						.setTitle("��δѡ��ͼƬ!").show();
					
				}
				else if(Global.isImageFile(mFile.getName())) 
				{
					Intent intent = new Intent(AddPhoto.this,AddPhotoZoom.class);
					intent.putExtra("mfile", mFile.getPath());
					startActivityForResult(intent, REQ_FROM_PHOTOEDIT);
				}
				
			}
		});
        
        ImageButton IB_Save = (ImageButton) findViewById(R.id.IB_Save);
        IB_Save.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// ������
				if(mFile != null)
				{			
					et = new EditText(AddPhoto.this);  
					et.setHint("���ڴ˴�����ͼƬ����");
					String[] items = {"����", "����","��ʳ","�ռ�" };	
					savepath = Global.DIR_Work;
					new AlertDialog.Builder(AddPhoto.this)
						.setTitle("����ͼƬ")
						.setIcon(android.R.drawable.ic_dialog_info)
						//.setMessage("�������û�����")
						.setView(et)
						//checkItem = 8ֻ��Ϊ�˸տ�ʼ��ѡ�κ���  0.0
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
						.setPositiveButton("ȷ��", SaveFile)
						.setNegativeButton("ȡ��", null).show();
				}
				else {
						new AlertDialog.Builder(AddPhoto.this)
							.setTitle("������ͼƬ!").show();
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
					new AlertDialog.Builder(AddPhoto.this)
						.setTitle("����ɹ�")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage(fd.getPath())
						.setPositiveButton("ȷ��", null).show();
					Intent intent = new Intent(AddPhoto.this,AddMsgActivity.class);
					//�ڴ˴�����
					///////////////////////////////////////////
					/////////////////////////////////////////
					//////////////////////////////////////
					////////////////////////////////////
					//////////////////////////////////
					/////////////////////////////////
					///////////////////////////////
					/////////////////////////////
					Bundle b = new Bundle();  
			        b.putString("mFile", fd.getPath());  
			        intent.putExtras(b);
					//intent.putExtra("mflie", mFile);
					setResult(RESULT_OK,intent);  
					finish();
				}else
				{
					new AlertDialog.Builder(AddPhoto.this)
						.setTitle("����ʧ��")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage(fd.getPath())
						.setPositiveButton("ȷ��", null).show();
					
					
				}
			}
	    	//�㲥������ͼ����
	    	sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
			
		}
	};
    
	/**
	 *  �����صĽ��
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode != Activity.RESULT_OK)
			return;
		
		if (requestCode == REQ_FROM_CAMERA) { // ������з��ص���Ƭ
			File f = new File(getExternalCacheDir(),"Camera_"+CameraPhoto_Num+++".jpg");
			if(f.length()>0)
			{
				UpdateUI(f);
			}
		}

		if (requestCode == REQ_FROM_PHOTO) { // ���ಾ�з��ص���Ƭ
			Uri uri= data.getData(); // ���ص���һ��Uri
			String path = uri.getPath();
			File f = new File(path);
			if (!f.exists()) {
				path = GetPath(uri);
				f = new File(path);
			}
			
			if (f.exists())
				UpdateUI(f);
		}
		
		if (requestCode == REQ_FROM_PHOTOEDIT) { // ��ͼ��༭�з���
			String path = data.getStringExtra("mFile");
			File f = new File(path);
			if (f.exists())
				UpdateUI(f);
		}
	}
	
	
	/**
	 * ����UI
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
	 * ��uri�õ���ʵ��file·��
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


}

