package com.savetheeggs.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class GoofyGhost {

	private Bitmap ghost;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;
	private boolean available;
	private boolean availableFlag;
	private int counter = 0;

	public GoofyGhost(Context context) {
		frame = 0;
		available = false;
		availableFlag = false;
		ghost = BitmapFactory.decodeResource(context.getResources(), R.drawable.goofy_ghost);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			width = ghost.getWidth() / 10;
			height = ghost.getHeight();
			startX = 25;
			startY = canvas.getHeight() - 50;			
			setAvailable(true);
		}
		if (frame == 10) {
			frame = 0;
		}
		if (startY >= 0 - height) {
			startY -= 1;
		} else {
			setAvailable(false);
			setAvailableFlag(false);
		}
		left = frame * width;
		top = 0;
		right = left + width;
		bottom = height;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + 48), (int)(startY + 74));			
		canvas.drawBitmap(ghost, src, dst, null);
		
		frame++;
		counter++;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(boolean availableFlag) {
		this.availableFlag = availableFlag;
	}

}