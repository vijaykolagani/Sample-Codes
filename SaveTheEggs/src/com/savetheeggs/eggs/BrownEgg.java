package com.savetheeggs.eggs;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.Constants;
import com.savetheeggs.R;

public class BrownEgg extends Egg {

	private Bitmap egg;
	private Rect src;
	private Rect dst;
	private Random random;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private boolean availableFlag;
	private int left, top, right, bottom;
	private int speed;

	public BrownEgg(Context context) {
		random = new Random();
		available = false;
		availableFlag = false;
		egg = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg_brown);
	}

	@Override
	public void draw(Canvas canvas) {		
		if (!isAvailable()) {
			left = 0;
			top = 0;
			width = egg.getWidth();
			height = egg.getHeight();
			right = left + width;
			bottom = top + height;
			startX = random.nextInt((int) (canvas.getWidth() - Constants.EGG_WIDTH));
			startY = 0;
			setAvailable(true);	
			setAvailableFlag(true);
		}
		if (startY <= canvas.getHeight()) {
			startY += speed;
		} else {
			setAvailable(false);
			setAvailableFlag(false);
		}
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + Constants.EGG_WIDTH), (int)(startY + Constants.EGG_HEIGHT));			
		
		if (isAvailable()) {
			canvas.drawBitmap(egg, src, dst, null);			
		}
	}

	@Override
	public boolean detectCollision(float x, float y, int width) {
		if (startX >= x 
				&& startX + Constants.EGG_WIDTH <= x + width 
				&& startY + Constants.EGG_HEIGHT >= y
				&& startY + Constants.EGG_HEIGHT <= y + 15) {
			setAvailable(false);
			setAvailableFlag(false);
			return true;
		}
		return false;	}

	public Bitmap getEgg() {
		return egg;
	}

	public void setEgg(Bitmap egg) {
		this.egg = egg;
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

	public boolean isAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(boolean availableFlag) {
		this.availableFlag = availableFlag;
	}
}
