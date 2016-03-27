package com.mathsforkids.games.catcheggs;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mathsforkids.Constants;
import com.mathsforkids.R;

public class CatchTheEggsGameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
	
	private SurfaceHolder holder;
	private CatchTheEggsGameLoop gameLoop;
	
	private SensorManager manager;
	private Sensor accelerometer;
	
	private Bitmap background;
	private Bitmap bucket;
	private Bitmap eggSheet;
	private Bitmap brokenEgg;
	
	private Rect src, dst;
	
	private Eggs eggs;
	
	private float x, y;
	private float width, height;
	private float minX = 0;
	private float maxX;
	
	private final int EGG_WIDTH = 64;
	private final int EGG_HEIGHT = 80;
	
	private int eggWidth, eggHeight;
	
	private Random random;
	private int randX, randY;
	
	private int target;
	private int status;
	private Paint paint;
	
	private SoundPool soundPool;
	private int eggCatched, eggDropped;
	
	private boolean gameOver;
		
	public CatchTheEggsGameView(Context context) {
		super(context);
		gameLoop = new CatchTheEggsGameLoop(this);
		holder = getHolder();
		holder.addCallback(this);
		
		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				
		if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
			Log.e("CatchTheEggs", "Unable to register Sensors");
		}
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.catch_the_eggs);
		bucket = BitmapFactory.decodeResource(getResources(), R.drawable.bucket);
		eggSheet = BitmapFactory.decodeResource(getResources(), R.drawable.egg_number_sheet);
		brokenEgg = BitmapFactory.decodeResource(getResources(), R.drawable.broken_egg);
		
		random = new Random();
		paint = new Paint();
		target = random.nextInt(100);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		eggCatched = soundPool.load(context, R.raw.egg_shaker, 1);
		eggDropped = soundPool.load(context, R.raw.egg_break, 1);
		
		eggs = new Eggs(soundPool);
		eggs.setEggDropped(eggDropped);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		gameLoop.setRunning(true);
		gameLoop.start();		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		gameLoop.setRunning(false);
		while (retry) {
			try {
				gameLoop.join();
				retry = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public void draw(Canvas canvas) {
		width = canvas.getWidth();
		height = canvas.getHeight();
		maxX = width - bucket.getWidth();
		src = new Rect(0, 0, background.getWidth(), background.getHeight());
		dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());		
		canvas.drawBitmap(background, src, dst, null);
		y = height - bucket.getHeight();
		canvas.drawBitmap(bucket, x, y, null);
		
		paint.setColor(Color.BLACK);
		paint.setTextSize(22);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(2);
		canvas.drawText(Constants.TARGET + " : " + target, 20, 25, paint);
		canvas.drawText(Constants.STATUS + " : " + status, width - 120, 25, paint);
		
		eggs.setEggs(eggSheet);
		eggs.setBrokenEgg(brokenEgg);
		if (!eggs.isAvailable()) {
			randX = random.nextInt(5);
			randY = random.nextInt(2);
			eggs.setEggWidth(EGG_WIDTH);
			eggs.setEggHeight(EGG_HEIGHT);
			eggWidth = eggSheet.getWidth() / 5;
			eggHeight = eggSheet.getHeight() / 2;
			int left = randX * eggWidth;
			int top = randY * eggHeight;
			int right = left + eggWidth;
			int bottom = top + eggHeight;
			eggs.setLeft(left);
			eggs.setRight(right);
			eggs.setTop(top);
			eggs.setBottom(bottom);
			eggs.setStartY(0);
			eggs.setNumber(findNumber(left, top, right, bottom));
		}
		paint.setTextSize(36);
		if (status < target) {
			setGameOver(false);
			eggs.drawEggs(canvas);			
		} else if (target == status) {
			setGameOver(true);
			canvas.drawText(Constants.GAME_WON, width / 2 - 170, height / 4, paint);
			canvas.drawText(Constants.TAP_TO_PLAY, width / 2 - 140, height / 4 + 50, paint);
			
		} else {
			setGameOver(true);
			canvas.drawText(Constants.GAME_LOST, width / 2 - 150, height / 4, paint);
			canvas.drawText(Constants.TAP_TO_PLAY, width / 2 - 140, height / 4 + 50, paint);
		}
		if(detectCollision()) {
			soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
			status += eggs.getNumber();
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.values[1] > 0) {
			if (x <= maxX) {
				x += Math.pow(event.values[1], 2);
			}
		} else {
			if (x >= minX) {
				x -= Math.pow(event.values[1], 2);
			}
		}
	}
	
	public int findNumber(int left, int top, int right, int bottom) {
		int number = 0;
		if (left >= 0 && top >= 0 && right <= eggWidth && bottom <= eggHeight) {
			number = 5;
		} else if (left >= eggWidth && top >= 0 && right <= 2 * eggWidth && bottom <= eggHeight) {
			number = 6;
		} else if (left >= 2 * eggWidth && top >= 0 && right <= 3 * eggWidth && bottom <= eggHeight) {
			number = 7;
		} else if (left >= 3 * eggWidth && top >= 0 && right <= 4 * eggWidth && bottom <= eggHeight) {
			number = 8;
		} else if (left >= 4 * eggWidth && top >= 0 && right <= 5 * eggWidth && bottom <= eggHeight) {
			number = 9;
		} else if (left >= 0 && top >= eggHeight && right <= eggWidth && bottom <= 2 * eggHeight) {
			number = 0;
		} else if (left >= eggWidth && top >= eggHeight && right <= 2 * eggWidth && bottom <= 2 * eggHeight) {
			number = 1;
		} else if (left >= 2 * eggWidth && top >= eggHeight && right <= 3 * eggWidth && bottom <= 2 * eggHeight) {
			number = 2;
		} else if (left >= 3 * eggWidth && top >= eggHeight && right <= 4 * eggWidth && bottom <= 2 * eggHeight) {
			number = 3;
		} else if (left >= 4 * eggWidth && top >= eggHeight && right <= 5 * eggWidth && bottom <= 2 * eggHeight) {
			number = 4;
		}
		return number;
	}
	
	public boolean detectCollision() {
		int left = eggs.getStartX();
		int right = eggs.getStartX() + EGG_WIDTH;
		int bottom = eggs.getStartY() + EGG_HEIGHT;
		
		if (left >= x && right <= x + bucket.getWidth() && bottom >= y + 10 && bottom <= y + 15) {
			eggs.setAvailable(false);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isGameOver()) {
				target = random.nextInt(100);
				status = 0;
				setGameOver(false);
				return true;
			}
		}
		return false;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
