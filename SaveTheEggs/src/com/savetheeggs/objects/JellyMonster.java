package com.savetheeggs.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.savetheeggs.R;

public class JellyMonster {

	private Bitmap monster;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private int left, top, right, bottom;
	private int frame;

	public JellyMonster(Context context) {
		frame = 0;
		monster = BitmapFactory.decodeResource(context.getResources(), R.drawable.jelly_monster);
	}

	public void draw(Canvas canvas) {
		if (frame == 10) {
			frame = 0;
		}
		width = monster.getWidth() / 10;
		height = monster.getHeight();
		left = frame * width;
		top = 0;
		right = left + width;
		bottom = height;
		
		startX = 145;
		startY = canvas.getHeight() - 115;
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, (int)(startX + 42), (int)(startY + 30));			
		canvas.drawBitmap(monster, src, dst, null);	
		
		frame++;
	}

}
