package com.zhitu.xxf;

import java.util.ArrayList;


public class GsonCommUser {
	
	private ArrayList<UserComm> comments;
	private int counts;
	private String status;
	public ArrayList<UserComm> getComments() {
		return comments;
	}
	public void setComments(ArrayList<UserComm> comments) {
		this.comments = comments;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
