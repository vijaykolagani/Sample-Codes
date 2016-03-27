package com.savetheeggs.objects;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Butterfly {

	private Bitmap butterfly;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private Random random;

	public Butterfly(Context context) {
		frame = 0;
		available = false;
		random = new Random();
		butterfly = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = butterfly.getWidth() / 10;
			height = butterfly.getHeight();
			startX = canvas.getWidth();
			startY = random.nextInt(canvas.getHeight() / 2 + 150);			
			setAvailable(true);
		}
		if (frame == 10) {
			frame = 0;
		}
		if (startX >= 0) {
			startX -= 2;
		} else {
			setAvailable(false);
		}
		left = frame * width;
		top = 0;
		right = left + width;
		bottom = height;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + 48), (int)(startY + 48));			
		canvas.drawBitmap(butterfly, src, dst, null);
		
		frame++;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}