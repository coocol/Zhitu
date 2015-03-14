package com.zhitu.xxf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.R.integer;


public class ZhituNetWork {

	public ZhituNetWork() {

	}

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpEntity httpEntity;
	
	private String rootUrl = "http://whuzhi.sinaapp.com/zhitu/server.action";
	
	private HttpClient getHttpClient(){
		BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams,12*1000);  
	    HttpConnectionParams.setSoTimeout(httpParams,12*1000);  
	    HttpClient client = new DefaultHttpClient(httpParams); 
	    return client;
	}
	
	public String getMsgComm(String name,String id){
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "6"));
		paramsList.add(new BasicNameValuePair("username",name));
		paramsList.add(new BasicNameValuePair("msgid",id));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public String addANewComm(String name,String id,String commname,String commtime,String comments,String countString){
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "7"));
		paramsList.add(new BasicNameValuePair("username",name));
		paramsList.add(new BasicNameValuePair("msgid",id));
		paramsList.add(new BasicNameValuePair("commtime", commtime));
		paramsList.add(new BasicNameValuePair("commname",commname));
		paramsList.add(new BasicNameValuePair("comments",comments));
		paramsList.add(new BasicNameValuePair("commcounts",countString));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public String getUserinfo(String username){
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "9"));
		paramsList.add(new BasicNameValuePair("username",username));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public String handleUserinfo(String username,String head,String gender,String label,String person,String birthday){
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "8"));
		paramsList.add(new BasicNameValuePair("username",username));
		paramsList.add(new BasicNameValuePair("head",head));
		paramsList.add(new BasicNameValuePair("gender", gender));
		paramsList.add(new BasicNameValuePair("label",label));
		paramsList.add(new BasicNameValuePair("person",person));
		paramsList.add(new BasicNameValuePair("birthday",birthday));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public String handleILikeIt(String username,String sharetime,String counts){
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "5"));
		paramsList.add(new BasicNameValuePair("username",username));
		paramsList.add(new BasicNameValuePair("msgid",sharetime));
		paramsList.add(new BasicNameValuePair("likecounts", counts));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public String pullMainPageData(String username,String lasttimesString){
		
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "4"));
		paramsList.add(new BasicNameValuePair("refreshnum",lasttimesString));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		HttpResponse thttpResponse = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
			httpPost.setEntity(httpEntity);
		
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} 
	}
	
	public boolean sendAMsg(String username,String textinfo,String pics){

		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "3"));
		paramsList.add(new BasicNameValuePair("username", username));
		paramsList.add(new BasicNameValuePair("textinfo", textinfo));
		paramsList.add(new BasicNameValuePair("pics", pics));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setEntity(httpEntity);
		HttpResponse thttpResponse = null;
		try {
			thttpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		}
		HttpEntity thttpEntity = thttpResponse.getEntity();
		try {
			s = EntityUtils.toString(thttpEntity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		}
		String resString = new JsonHandle().getSimpleStatusValue(s);
		if (resString.equals("Login Success!")
				|| resString.equals("Register Success!")
				|| resString.equals("release success")) {
			httpClient.getConnectionManager().shutdown();
			return true;
		} else {
			httpClient.getConnectionManager().shutdown();
			return false;
		}

	}
	
	public boolean isRegisterUserOK(final String name, final String pw,
			final String mail) {
		return checkUser("0", name, pw, mail);
	}

	public String isUserLoginValid(final String name, final String pw) {
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", "1"));
		paramsList.add(new BasicNameValuePair("username", name));
		paramsList.add(new BasicNameValuePair("password", pw));
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);
		String s = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(httpEntity);
		HttpResponse thttpResponse = null;
		try {
			thttpResponse = httpClient.execute(httpPost);
			HttpEntity thttpEntity = thttpResponse.getEntity();
			s = EntityUtils.toString(thttpEntity);
			httpClient.getConnectionManager().shutdown();
			return s;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return null;
		}
	}

	public boolean sendMail(String address, String usernameString) {
		return checkUser("2", usernameString, null, address);
	}

	private boolean checkUser(String ask, final String name, final String pw,
			final String mail) {
		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
		paramsList.add(new BasicNameValuePair("asktype", ask));
		paramsList.add(new BasicNameValuePair("username", name));
		paramsList.add(new BasicNameValuePair("password", pw));
		paramsList.add(new BasicNameValuePair("emailaddr", mail));
		if (ask.equals("0")) {
			paramsList.add(new BasicNameValuePair("email", mail));
		}
		httpClient = getHttpClient();
		httpPost = new HttpPost(rootUrl);

		String s = null;
		try {
			httpEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(httpEntity);
		HttpResponse thttpResponse = null;
		try {
			thttpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		}
		HttpEntity thttpEntity = thttpResponse.getEntity();
		try {
			s = EntityUtils.toString(thttpEntity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
			return false;
		}
		String resString = new JsonHandle().getSimpleStatusValue(s);
		if (resString.equals("Login Success!")
				|| resString.equals("Register Success!")
				|| resString.equals("send success")) {
			httpClient.getConnectionManager().shutdown();
			return true;
		} else {
			httpClient.getConnectionManager().shutdown();
			return false;
		}

	}

	
}
