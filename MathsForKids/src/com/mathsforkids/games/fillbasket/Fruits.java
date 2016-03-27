package com.mathsforkids.games.fillbasket;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Fruits {

	private Bitmap fruit;
	private float fruitWidth;
	private float fruitHeight;
	private Rect src;
	private Rect dst;
	private Random random;
	private int startX, startY;
	private boolean available;
	private int left, top, right, bottom;

	public Fruits() {
		random = new Random();
		available = false;
	}

	public void drawEggs(Canvas canvas) {

		if (!isAvailable()) {
			startX = random.nextInt((int) (canvas.getWidth() - fruitWidth));
		}
		if (startY <= canvas.getHeight() - fruitHeight) {
			startY+=5;
			setAvailable(true);
		} else {
			setAvailable(false);
		}
				
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + fruitWidth), (int)(startY + fruitHeight));			

		canvas.drawBitmap(fruit, src, dst, null);
	}

	public Bitmap getFruit() {
		return fruit;
	}

	public void setFruit(Bitmap fruit) {
		this.fruit = fruit;
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

	public float getFruitWidth() {
		return fruitWidth;
	}

	public void setFruitWidth(float fruitWidth) {
		this.fruitWidth = fruitWidth;
	}

	public float getFruitHeight() {
		return fruitHeight;
	}

	public void setFruitHeight(float fruitHeight) {
		this.fruitHeight = fruitHeight;
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
}
