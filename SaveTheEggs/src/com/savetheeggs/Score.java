package com.savetheeggs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Score {

	private Bitmap score;
	private Bitmap zero, five, ten, fifteen, twenty, twentyFive, fifty, hundred, twoHundred, 
			minusOneFifty, plusOne, minusOne;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;
	private int speed = 4;
	private int number;

	public Score(Context context) {
		available = false;
		zero = BitmapFactory.decodeResource(context.getResources(), R.drawable.one);
		five = BitmapFactory.decodeResource(context.getResources(), R.drawable.five);
		ten = BitmapFactory.decodeResource(context.getResources(), R.drawable.ten);
		fifteen = BitmapFactory.decodeResource(context.getResources(), R.drawable.fifteen);
		twenty = BitmapFactory.decodeResource(context.getResources(), R.drawable.twenty);
		twentyFive = BitmapFactory.decodeResource(context.getResources(), R.drawable.twenty_five);
		fifty = BitmapFactory.decodeResource(context.getResources(), R.drawable.fifty);
		hundred = BitmapFactory.decodeResource(context.getResources(), R.drawable.hundred);
		twoHundred = BitmapFactory.decodeResource(context.getResources(), R.drawable.two_hundred);
		minusOneFifty = BitmapFactory.decodeResource(context.getResources(), R.drawable.minus_one_fifty);
		plusOne = BitmapFactory.decodeResource(context.getResources(), R.drawable.plus_one);
		minusOne = BitmapFactory.decodeResource(context.getResources(), R.drawable.minus_one);
	}

	public void draw(Canvas canvas) {
		switch (number) {
		case 0:
			setScore(zero);
			break;
		case 5:
			setScore(five);
			break;
		case 10:
			setScore(ten);
			break;
		case 15:
			setScore(fifteen);
			break;
		case 20:
			setScore(twenty);
			break;
		case 25:
			setScore(twentyFive);
			break;
		case 50:
			setScore(fifty);
			break;
		case 100:
			setScore(hundred);
			break;
		case 200:
			setScore(twoHundred);
			break;
		case -150:
			setScore(minusOneFifty);
			break;
		case +1:
			setScore(plusOne);
			break;
		case -1:
			setScore(minusOne);
			break;		
		}
		if (!isAvailable()) {
			left = 0;
			top = 0;
			width = score.getWidth();
			height = score.getHeight();
			right = left + width;
			bottom = top + height;
			setAvailable(true);
		}
		if (startY >= canvas.getHeight() / 2) {
			startY -= speed;
		} else {
			setAvailable(false);
		}
				
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + width), (int)(startY + height));		
		if (isAvailable()) {
			canvas.drawBitmap(score, src, dst, null);												
		}
	}

	public void setScore(Bitmap score) {
		this.score = score;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
}
