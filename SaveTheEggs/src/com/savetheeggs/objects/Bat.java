package com.savetheeggs.objects;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Bat {

	private Bitmap bat;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private int counter = 0;
	private Random random;

	public Bat(Context context) {
		frame = 0;
		available = false;
		random = new Random();
		bat = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = bat.getWidth() / 4;
			height = bat.getHeight();
			startX = random.nextInt(canvas.getWidth() - width);
			startY = 0;			
			setAvailable(true);
		}
		if (frame == 4) {
			frame = 0;
		}
		if (startY <= canvas.getHeight()) {
			startY += 2;
		} else {
			setAvailable(false);
		}
		left = frame * width;
		top = 0;
		right = left + width;
		bottom = height;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + 128), (int)(startY + 58));			
		canvas.drawBitmap(bat, src, dst, null);
		
		if (counter == 5) {
			frame++;
			counter = 0;
		}
		counter++;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}