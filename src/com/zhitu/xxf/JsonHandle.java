package com.zhitu.xxf;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandle {

	private JSONObject jObject;

	public String getSimpleStatusValue(String obj) {
		try {
			jObject = new JSONObject(obj);
			return jObject.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unknow error";
		}
	}

}
