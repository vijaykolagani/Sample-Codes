package com.savetheeggs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PlayerLife {
	
	private int lifeCount;
	private boolean lifeAvailable;
	private Bitmap life;
	private Rect src, dst;
	
	public PlayerLife(Context context) {
		lifeCount = 3;
		life = BitmapFactory.decodeResource(context.getResources(), R.drawable.egg_green);
		lifeAvailable = true;
	}
	
	public int getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	public boolean isLifeAvailable() {
		return lifeAvailable;
	}

	public void setLifeAvailable(boolean lifeAvailable) {
		this.lifeAvailable = lifeAvailable;
	}

	public void incrementLifeCount() {
		if (lifeCount < 3) {
			lifeCount++;
			setLifeAvailable(true);
		}
	}
	
	public void decrementLifeCount() {
		if (lifeCount > 0)
			lifeCount--;
		if (lifeCount == 0)
			setLifeAvailable(false);
	}
	
	public void draw(Canvas canvas) {
		
		for (int i=0; i< lifeCount; i++) {
			src = new Rect(0, 0, life.getWidth(), life.getHeight());
			dst = new Rect(25 + Constants.LIFE_WIDTH * i, 10, 25 + (Constants.LIFE_WIDTH * i) + Constants.LIFE_WIDTH, 
					10 + Constants.LIFE_HEIGHT);			
			canvas.drawBitmap(life, src, dst, null);
		}
	}

}
