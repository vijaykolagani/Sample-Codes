package com.savetheeggs.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class Bubbles {

	private Bitmap bubble;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;
	private int speed = 5;
	private int count;

	public Bubbles(Context context) {
		available = false;
		bubble = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			left = 0;
			top = 0;
			width = bubble.getWidth();
			height = bubble.getHeight();
			right = left + width;
			bottom = top + height;
			setAvailable(true);
		}
		if (startY >= 0) {
			startY -= speed;
		} else {
			setAvailable(false);
		}
				
		for (int i = 0; i < count; i++) {
			src = new Rect(left, top, right, bottom);
			dst = new Rect(startX, startY + 15 * i, (int)(startX + width), (int)(startY + height) + 15 * i);			
			canvas.drawBitmap(bubble, src, dst, null);									
		}
	}

	public Bitmap getBubble() {
		return bubble;
	}

	public void setBubble(Bitmap bubble) {
		this.bubble = bubble;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setCount(int count) {
		this.count = count;
	}
		
}
