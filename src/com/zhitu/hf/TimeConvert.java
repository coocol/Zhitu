package com.zhitu.hf;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConvert{
	@SuppressLint("SimpleDateFormat")
	public static String getTimeDiff(String s_date) {
		java.text.SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date;
		long diff = 0;
		Calendar cal = Calendar.getInstance();
		Date dnow = cal.getTime();
		String str = "";
		try {
			date = format.parse(s_date);
			diff = dnow.getTime() - date.getTime();
			if (diff > 31104000000L) {// 360 * 24 * 60 * 60 * 1000=31104000000
										// 毫秒
				str = (int) Math.floor(diff / 31104000000L) + "年前";
			} else if (diff > 15552000000L) {// 180 * 24 * 60 * 60 *
												// 1000=15552000000 毫秒
				str = "半年前";
			} else if (diff > 7776000000L) {// 90 * 24 * 60 * 60 *
											// 1000=7776000000 毫秒
				str = "3个月前";
			} else if (diff > 5184000000L) {// 60 * 24 * 60 * 60 *
											// 1000=5184000000 毫秒
				str = "2个月前";
			} else if (diff > 2592000000L) {// 30 * 24 * 60 * 60 *
											// 1000=2592000000 毫秒
				str = "1个月前";
			} else if (diff > 1814400000) {// 21 * 24 * 60 * 60 *
											// 1000=1814400000 毫秒
				str = "3周前";
			} else if (diff > 1209600000) {// 14 * 24 * 60 * 60 *
											// 1000=1209600000 毫秒
				str = "2周前";
			} else if (diff > 604800000) {// 7 * 24 * 60 * 60 * 1000=604800000
											// 毫秒
				str = "1周前";
			} else if (diff > 86400000) { // 24 * 60 * 60 * 1000=86400000 毫秒
				// System.out.println("X天前");
				str = (int) Math.floor(diff / 86400000f) + "天前";
			} else if (diff > 3600000) {// 1 * 60 * 60 * 1000=3600000 毫秒
				// System.out.println("X小时前");
				str = (int) Math.floor(diff / 3600000f) + "小时前";
			} else if (diff > 60000) {// 1 * 60 * 1000=60000 毫秒
				// System.out.println("X分钟前");
				str = (int) Math.floor(diff / 60000) + "分钟前";
			} else {
				str = "刚刚";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
