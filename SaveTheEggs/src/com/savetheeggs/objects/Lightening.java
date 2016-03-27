package com.savetheeggs.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Lightening {

	private Bitmap lightening;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private boolean availableFlag;

	public Lightening(Context context) {
		frame = 0;
		available = false;
		availableFlag = false;
		lightening = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightening);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = lightening.getWidth();
			height = lightening.getHeight() / 13;
			startX = 25;
			startY = 25;			
			setAvailable(true);
		}
		if (frame == 13) {
			frame = 0;
			setAvailable(false);
			setAvailableFlag(false);
		}

		left = 0;
		top = frame * height;
		right = left + width;
		bottom = top + height;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + width), (int)(startY + height));			
		canvas.drawBitmap(lightening, src, dst, null);
		
		frame++;
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