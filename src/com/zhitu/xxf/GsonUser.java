package com.zhitu.xxf;

import java.util.ArrayList;

public class GsonUser {
	
	private String status;
	private String msgcount;
	private ArrayList<UserMsg> data;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgcount() {
		return msgcount;
	}
	public void setMsgcount(String msgcount) {
		this.msgcount = msgcount;
	}
	public ArrayList<UserMsg> getData() {
		return data;
	}
	public void setData(ArrayList<UserMsg> data) {
		this.data = data;
	}
	

}
