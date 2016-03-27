package com.savetheeggs.eggs;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;

import com.savetheeggs.Constants;
import com.savetheeggs.R;

public class NormalEgg extends Egg {

	private Bitmap egg;
	private Bitmap brokenEgg;
	private Rect src;
	private Rect dst;
	private Random random;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;
	private int brokenEggLeft, brokenEggTop, brokenEggRight, brokenEggBottom;
	private boolean brokenEggVisible;
	private boolean brokenEggFlag;
	private int x, y;
	private SoundPool soundPool;
	private int eggDropped;
	private int speed;

	public NormalEgg(Context context) {
		random = new Random();
		available = false;
		brokenEggVisible = false;
		brokenEggFlag = true;
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		egg = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg_white);
		brokenEgg = BitmapFactory.decodeResource(context.getResources(), R.drawable.broken_egg);
		eggDropped = soundPool.load(context, R.raw.egg_drop, 1);
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
		}
		if (startY <= canvas.getHeight() - Constants.EGG_HEIGHT) {
			startY += getSpeed();
			if (startY >= canvas.getHeight() / 2) {
				setBrokenEggVisible(false);
			}
			setAvailable(true);
		} else {
			x = startX;
			y = canvas.getHeight() - brokenEgg.getHeight() / 3;
			soundPool.play(eggDropped, 1.0f, 1.0f, 0, 0, 1.5f);
			setBrokenEggVisible(true);
			setBrokenEggFlag(false);
			setAvailable(false);				
		}
				
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + Constants.EGG_WIDTH), (int)(startY + Constants.EGG_HEIGHT));			

		canvas.drawBitmap(egg, src, dst, null);
		
		if (isBrokenEggVisible()) {
			brokenEggLeft = 0;
			brokenEggTop = 0;				
			brokenEggRight = brokenEgg.getWidth();
			brokenEggBottom = brokenEgg.getHeight();

			src = new Rect(brokenEggLeft, brokenEggTop, brokenEggRight, brokenEggBottom);
			dst = new Rect(x, y, (int)(x + brokenEgg.getWidth()/3), (int)(y + brokenEgg.getHeight()/3));
			canvas.drawBitmap(brokenEgg, src, dst, null);
		}
	}
	
	@Override
	public boolean detectCollision(float x, float y, int width) {
		if (startX >= x 
				&& startX + Constants.EGG_WIDTH <= x + width 
				&& startY + Constants.EGG_HEIGHT >= y
				&& startY + Constants.EGG_HEIGHT <= y + 15) {
			setAvailable(false);
			return true;
		}
		return false;
	}

	public Bitmap getEgg() {
		return egg;
	}

	public void setEgg(Bitmap egg) {
		this.egg = egg;
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

	public boolean isBrokenEggVisible() {
		return brokenEggVisible;
	}

	public void setBrokenEggVisible(boolean brokenEggVisible) {
		this.brokenEggVisible = brokenEggVisible;
	}

	public void setEggDropped(int eggDropped) {
		this.eggDropped = eggDropped;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isBrokenEggFlag() {
		return brokenEggFlag;
	}

	public void setBrokenEggFlag(boolean brokenEggFlag) {
		this.brokenEggFlag = brokenEggFlag;
	}

}
