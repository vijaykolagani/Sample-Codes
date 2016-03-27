package com.mathsforkids.games.catcheggs;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.SoundPool;

public class Eggs {

	private Bitmap eggs;
	private Bitmap brokenEgg;
	private float eggWidth;
	private float eggHeight;
	private Rect src;
	private Rect dst;
	private Random random;
	private int randX, randY;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;
	private int number;
	private int brokenEggLeft, brokenEggTop, brokenEggRight, brokenEggBottom;
	private boolean brokenEggVisible;
	private int x, y;
	private SoundPool soundPool;
	private int eggDropped;

	public Eggs(SoundPool soundPool) {
		random = new Random();
		available = false;
		brokenEggVisible = false;
		this.soundPool = soundPool;
	}

	public void drawEggs(Canvas canvas) {
		brokenEggLeft = 0;
		brokenEggTop = 0;
		brokenEggRight = brokenEgg.getWidth();
		brokenEggBottom = brokenEgg.getHeight();
		
		if (!isAvailable()) {
			startX = random.nextInt((int) (canvas.getWidth() - eggWidth));
		}
		if (startY <= canvas.getHeight() - eggHeight) {
			startY+=5;
			if (startY >= canvas.getHeight() / 2) {
				setBrokenEggVisible(false);
			}
			setAvailable(true);
		} else {
			x = startX;
			y = canvas.getHeight() - brokenEgg.getHeight() / 3;
			soundPool.play(eggDropped, 1.0f, 1.0f, 0, 0, 1.5f);
			setBrokenEggVisible(true);
			setAvailable(false);
		}
				
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + eggWidth), (int)(startY + eggHeight));			

		canvas.drawBitmap(eggs, src, dst, null);
		
		if (isBrokenEggVisible()) {
			src = new Rect(brokenEggLeft, brokenEggTop, brokenEggRight, brokenEggBottom);
			dst = new Rect(x, y, (int)(x + brokenEgg.getWidth()/3), (int)(y + brokenEgg.getHeight()/3));
			canvas.drawBitmap(brokenEgg, src, dst, null);
		}
	}

	public Bitmap getEggs() {
		return eggs;
	}

	public void setEggs(Bitmap eggs) {
		this.eggs = eggs;
	}

	public Bitmap getBrokenEgg() {
		return brokenEgg;
	}

	public void setBrokenEgg(Bitmap brokenEgg) {
		this.brokenEgg = brokenEgg;
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

	public int getRandX() {
		return randX;
	}

	public void setRandX(int randX) {
		this.randX = randX;
	}

	public int getRandY() {
		return randY;
	}

	public void setRandY(int randY) {
		this.randY = randY;
	}

	public float getEggWidth() {
		return eggWidth;
	}

	public void setEggWidth(float eggWidth) {
		this.eggWidth = eggWidth;
	}

	public float getEggHeight() {
		return eggHeight;
	}

	public void setEggHeight(float eggHeight) {
		this.eggHeight = eggHeight;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isBrokenEggVisible() {
		return brokenEggVisible;
	}

	public void setBrokenEggVisible(boolean brokenEggVisible) {
		this.brokenEggVisible = brokenEggVisible;
	}

	public void setEggDropped(int eggDropped) {
		this.eggDropped = eggDropped;
	}
}
