package com.mathsforkids.games.fillbasket;

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

public class FillTheBasketGameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
	
	private SurfaceHolder holder;
	private FillTheBasketGameLoop gameLoop;
	
	private SensorManager manager;
	private Sensor accelerometer;
	
	private Bitmap background;
	private Bitmap basket;
	
	private Bitmap apple;
	private Bitmap banana;
	private Bitmap orange;
	private Bitmap strawberry;
	
	private Bitmap[] allFruits;
	private Bitmap selectedFruit;
	
	private Rect src, dst;
	
	private Fruits fruits;
	
	private float x, y;
	private float width, height;
	private float minX = 0;
	private float maxX;
	
	private final int FRUIT_WIDTH = 64;
	private final int FRUIT_HEIGHT = 64;
	
	private int fruitWidth, fruitHeight;
	
	private Random random;
	
	private int[] target;
	private int[] status;
	private Paint paint;
	
	private SoundPool soundPool;
	private int fruitCatched;
	private int fruit;
	
	private int flag = 0;
	
	private boolean gameOver;
		
	public FillTheBasketGameView(Context context) {
		super(context);
		gameLoop = new FillTheBasketGameLoop(this);
		holder = getHolder();
		holder.addCallback(this);
		
		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				
		if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
			Log.e("FillTheBasket", "Unable to register Sensors");
		}
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.nature_bg);
		basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
		
		apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
		orange = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
		strawberry = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
		
		allFruits = new Bitmap[4];
		allFruits[0] = apple;
		allFruits[1] = banana;
		allFruits[2] = orange;
		allFruits[3] = strawberry;
		
		random = new Random();
		paint = new Paint();
		
		target = new int[4];
		status = new int[4];
		target[0] = random.nextInt(25);
		target[1] = random.nextInt(25);
		target[2] = random.nextInt(25);
		target[3] = random.nextInt(25);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		fruitCatched = soundPool.load(context, R.raw.egg_shaker, 1);
		
		fruits = new Fruits();
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
		maxX = width - basket.getWidth();
		src = new Rect(0, 0, background.getWidth(), background.getHeight());
		dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());		
		canvas.drawBitmap(background, src, dst, null);
		y = height - basket.getHeight();
		canvas.drawBitmap(basket, x, y, null);
		
		paint.setColor(Color.BLACK);
		paint.setTextSize(22);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(2);
		canvas.drawText(Constants.TARGET + " : ", 20, 25, paint);
		paint.setStrokeWidth(1);
		paint.setTextSize(16);
		canvas.drawText("Apples : " + target[0], 20, 50, paint);
		canvas.drawText("Bananas : " + target[1], 20, 70, paint);
		canvas.drawText("Oranges : " + target[2], 20, 90, paint);
		canvas.drawText("Strawberrys : " + target[3], 20, 110, paint);
		paint.setTextSize(22);
		paint.setStrokeWidth(2);
		canvas.drawText(Constants.STATUS + " : ", width - 180, 25, paint);
		paint.setTextSize(16);
		paint.setStrokeWidth(1);
		canvas.drawText("Apples : " + status[0], width - 180, 50, paint);
		canvas.drawText("Bananas : " + status[1], width - 180, 70, paint);
		canvas.drawText("Oranges : " + status[2], width - 180, 90, paint);
		canvas.drawText("Strawberrys : " + status[3], width - 180, 110, paint);
		
		if (!fruits.isAvailable()) {
			fruit = random.nextInt(4);
			selectedFruit = allFruits[fruit];
			fruits.setFruit(selectedFruit);
			fruits.setFruitWidth(FRUIT_WIDTH);
			fruits.setFruitHeight(FRUIT_HEIGHT);
			fruitWidth = selectedFruit.getWidth();
			fruitHeight = selectedFruit.getHeight();
			int left = 0;
			int top = 0;
			int right = fruitWidth;
			int bottom = fruitHeight;
			fruits.setLeft(left);
			fruits.setRight(right);
			fruits.setTop(top);
			fruits.setBottom(bottom);
			fruits.setStartY(0);
		}
		paint.setTextSize(36);
		
		if (!checkStatus()) {
			setGameOver(false);
			fruits.drawEggs(canvas);			
		} else if (flag == 0) {
			setGameOver(true);
			canvas.drawText(Constants.GAME_WON, width / 2 - 170, height / 3, paint);
			canvas.drawText(Constants.TAP_TO_PLAY, width / 2 - 140, height / 3 + 50, paint);
		} else {
			setGameOver(true);
			canvas.drawText(Constants.GAME_LOST, width / 2 - 150, height / 3, paint);
			canvas.drawText(Constants.TAP_TO_PLAY, width / 2 - 140, height / 3 + 50, paint);
		}

		if(detectCollision()) {
			soundPool.play(fruitCatched, 1.0f, 1.0f, 0, 0, 1.5f);
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
	
	public boolean detectCollision() {
		int left = fruits.getStartX();
		int right = fruits.getStartX() + FRUIT_WIDTH;
		int bottom = fruits.getStartY() + FRUIT_HEIGHT;
		
		if (left >= x && right <= x + basket.getWidth() && bottom >= y + 10 && bottom <= y + 15) {
			switch (fruit) {
			case 0: 
				status[0]++;
				break;
			case 1:
				status[1]++;
				break;
			case 2:
				status[2]++;
				break;
			case 3:
				status[3]++;
				break;
			default:
				break;
			}
			fruits.setAvailable(false);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isGameOver()) {
				target[0] = random.nextInt(25);
				target[1] = random.nextInt(25);
				target[2] = random.nextInt(25);
				target[3] = random.nextInt(25);
				status[0] = 0;
				status[1] = 0;
				status[2] = 0;
				status[3] = 0;
				flag = 0;
				setGameOver(false);
				return true;
			}
		}
		return false;
	}
	
	public boolean checkStatus() {
		int count = 0;
		for (int i = 0; i < target.length; i++) {
			if (status[i] == target[i]) {
				count++;				
			} else if (status[i] > target[i]) {
				flag = 1;
				return true;
			}
		}
		if (count == 4)
			return true;
		return false;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
