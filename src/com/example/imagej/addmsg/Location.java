package com.example.imagej.addmsg;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import android.app.Application;
import android.widget.TextView;

public class Location extends Application {

	public LocationClient mLocationClient = null;
	private String mData;
	public MyLocationListenner myListener = new MyLocationListenner();
	TextView mTv;

	@Override
	public void onCreate() {
		
		mLocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );
		
	}
	/**
	 * ��ʾ�ַ���
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if ( mTv != null )
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������������λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\nλ�� : ");
				sb.append(location.getAddrStr());
			}
			logMsg(sb.toString());
		}

	}

}