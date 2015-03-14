package com.zhitu.xxf;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class JSONHelper {

	
	public static Short convertToShort(String json){
		
		return Short.valueOf(String.valueOf(JSON.parse(json)));
	}

	public static Integer convertToInteger(String json){
		
		return Integer.valueOf(String.valueOf(JSON.parse(json)));
	}
	
	
	public static Long convertToLong(String json){
		
		return Long.valueOf(String.valueOf(JSON.parse(json)));
	}
	
	
	public static Float convertToFloat(String json){
		
		return Float.valueOf(String.valueOf(JSON.parse(json)));
	}
	
	
	public static Double convertToDouble(String json){
		
		return Double.valueOf(String.valueOf(JSON.parse(json)));
	}
	
	
	public static String convertToString(String json){
		return String.valueOf(JSON.parse(json));
	}
	
	
	public static Object convertToObject(String json){
		return JSON.parse(json);
	}
	
	
	public static <T> T convertToObject(String json,Class<T> objClass){
		return JSON.parseObject(json, objClass);
	}
	
	
	public static String convertToJSON(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static <T> List<T>  convertToList(String json,Class<T> objClass){
		
		return JSON.parseArray(json, objClass);
	}
	
}
