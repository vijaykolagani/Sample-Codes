package com.savetheeggs.objects;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Fish {

	private Bitmap fish;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private Random random;
	private Bubbles bubble;

	public Fish(Context context) {
		frame = 0;
		available = false;
		random = new Random();
		bubble = new Bubbles(context);
		fish = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = fish.getWidth() / 18;
			height = fish.getHeight();
			startX = canvas.getWidth();
			startY = random.nextInt(canvas.getHeight() / 2);			
			setAvailable(true);
		}
		if (frame == 18) {
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
		canvas.drawBitmap(fish, src, dst, null);
		
		if (!bubble.isAvailable()) {
			bubble.setStartX(startX);
			bubble.setStartY(startY);
			bubble.setCount(1);
		}
		bubble.draw(canvas);
		frame++;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
