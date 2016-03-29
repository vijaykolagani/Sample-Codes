package com.itcuties.android.apps.data;

import android.graphics.Bitmap;

public class ContactsData {

	// Number from witch the sms was send
	private String number;
	// SMS text name
	private String name;
	private Bitmap bitmap;;
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}

