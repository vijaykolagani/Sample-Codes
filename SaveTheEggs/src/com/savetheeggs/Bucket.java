package com.savetheeggs;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bucket {
	private Bitmap bitmap;
	private int width;
	private int height;
	
	public Bucket() {
	}
	
	public void draw(Canvas canvas) {
		
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
