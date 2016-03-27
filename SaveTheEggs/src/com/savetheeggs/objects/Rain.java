package com.savetheeggs.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Rain {

	private Bitmap[] rain;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private boolean availableFlag;
	private int counter = 0;
	private int frameCounter = 0;

	public Rain(Context context) {
		frame = 0;
		available = false;
		availableFlag = false;
		rain = new Bitmap[6];
		rain[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_1);
		rain[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_2);
		rain[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_3);
		rain[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_4);
		rain[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_5);
		rain[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rain_6);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = rain[0].getWidth();
			height = rain[0].getHeight();
			setAvailable(true);
		}
		if (frame == 6) {
			frame = 0;
		}

		if (counter == 500) {
			counter = 0;
			setAvailable(false);
			setAvailableFlag(false);
		}
		
		left = 0;
		top = 0;
		right = left + width;
		bottom = top + height;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());			
		canvas.drawBitmap(rain[frame], src, dst, null);
		
		if (frameCounter == 2) {
			frameCounter = 0;
			frame++;			
		}
		frameCounter++;
		counter++;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(boolean availableFlag) {
		this.availableFlag = availableFlag;
	}

}