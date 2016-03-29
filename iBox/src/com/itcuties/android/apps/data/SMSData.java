package com.itcuties.android.apps.data;

import android.graphics.Bitmap;


public class SMSData {

	// Number from witch the sms was send
	private String number;
	// SMS text body
	private String body;
	private String disname;
	
	private Bitmap image;
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getDisname() {
		return disname;
	}
	
	public void setDisname(String disname) {
		this.disname = disname;
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
}
