package com.savetheeggs.menu;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.Constants;
import com.savetheeggs.R;
import com.savetheeggs.eggs.Egg;

public class MenuEgg extends Egg {

	private Bitmap egg;
	private Rect src;
	private Rect dst;
	private Random random;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;
	private int speed = 5;

	public MenuEgg(Context context) {
		random = new Random();
		available = false;
		egg = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg_white);
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
			startX = random.nextInt((int) (canvas.getWidth() - Constants.MENU_EGG_WIDTH));
			startY = 0;
		}
		if (startY <= canvas.getHeight()) {
			startY += speed;
			setAvailable(true);				
		} else {
			setAvailable(false);
		}
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + Constants.MENU_EGG_WIDTH), (int)(startY + Constants.MENU_EGG_HEIGHT));			
		
		if (isAvailable()) {
			canvas.drawBitmap(egg, src, dst, null);			
		}
	}

	@Override
	public boolean detectCollision(float x, float y, int width) {
		return false;	
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
