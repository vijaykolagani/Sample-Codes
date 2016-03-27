package com.savetheeggs.menu;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.savetheeggs.R;
import com.savetheeggs.objects.Bat;
import com.savetheeggs.objects.Bubbles;
import com.savetheeggs.objects.Butterfly;
import com.savetheeggs.objects.Fish;
import com.savetheeggs.objects.Ghost;
import com.savetheeggs.objects.JellyMonster;
import com.savetheeggs.objects.Lightening;
import com.savetheeggs.objects.Rain;

public class MenuView extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder holder;
	private MenuLoop menuLoop;
	
	private Context context;
	
	private Bitmap background;
	
	private MenuEgg egg;
	private Butterfly butterfly;
	private Lightening lightening;
	private Rain rain;
	private Bat bat;
	private Ghost ghost;
	private Bubbles bubble, bubbleTwo;
	private JellyMonster monster;
	private Fish fish;
	
	private Random random;
	private int selection;
	
	private int lighteningCounter = 0;
	private int rainCounter = 0;
	
	private Rect src, dst;
	
	private float x = 0;
	private float width;
	
	public MenuView(Context context) {
		super(context);
		this.context = context;
		holder = getHolder();
		menuLoop = new MenuLoop(holder, this);
		holder.addCallback(this);
		
		random = new Random();
		selection = random.nextInt(4);
		
		initialize();
		
		egg = new MenuEgg(context);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		menuLoop.setRunning(true);
		menuLoop.start();
		Log.d("MenuView", "SurfaceCreated");
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		menuLoop.setRunning(false);
		while (retry) {
			try {
				menuLoop.join();
				retry = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d("MenuView", "SurfaceDestroyed");
	}

	@Override
	public void draw(Canvas canvas) {
		width = background.getWidth();
		src = new Rect((int)(x), 0, (int)(width + x), background.getHeight());
		dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());		
		canvas.drawBitmap(background, src, dst, null);
		egg.draw(canvas);
		
		switch (selection) {
		case 1:
			rainCounter++;
			if (rainCounter == 750) {
				rainCounter = 0;
				rain.setAvailableFlag(true);
			}
			if (rain.isAvailableFlag()) {
				rain.draw(canvas);
			}
			butterfly.draw(canvas);
			break;
		case 2:
			lighteningCounter++;
			if (lighteningCounter == 500) {
				lighteningCounter = 0;
				lightening.setAvailableFlag(true);
			}
			if (lightening.isAvailableFlag()) {
				lightening.draw(canvas);			
			}
			ghost.draw(canvas);
			bat.draw(canvas);
			break;
		case 3:
			if (!bubble.isAvailable()) {
				bubble.setStartX((int)canvas.getWidth() - 150);
				bubble.setStartY((int)canvas.getHeight() - 150);
				bubble.setCount(1);
			}
			bubble.draw(canvas);
			
			if (!bubbleTwo.isAvailable() && bubble.getStartY() <= canvas.getHeight() / 2 - 150) {
				bubbleTwo.setStartX(50);
				bubbleTwo.setStartY((int)canvas.getHeight() - 150);
				bubbleTwo.setCount(1);
			}
			bubbleTwo.draw(canvas);
			
			monster.draw(canvas);
			fish.draw(canvas);
			break;
		}
		
	}
	
	public MenuLoop getThread() {
		return menuLoop;
	}
	
	public void initialize() {
		if (selection > 0) {
			Log.d("MenuView", "Select: " + selection);
			switch (selection) {
			case 1:
				background = BitmapFactory.decodeResource(getResources(), R.drawable.forest_bg);
				butterfly = new Butterfly(context);
				rain = new Rain(context);
				break;
			case 2:
				background = BitmapFactory.decodeResource(getResources(), R.drawable.halloween);
				bat = new Bat(context);
				lightening = new Lightening(context);
				ghost = new Ghost(context);
				break;
			case 3:
				background = BitmapFactory.decodeResource(getResources(), R.drawable.water_bg);
				bubble = new Bubbles(context);
				bubbleTwo = new Bubbles(context);
				monster = new JellyMonster(context);
				fish = new Fish(context);
				break;
			}			
		} else {
			selection = random.nextInt(4);
			initialize();
		}
	}
	
}
