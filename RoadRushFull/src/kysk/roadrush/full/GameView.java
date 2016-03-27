package kysk.roadrush.full;

import java.util.Random;

import kysk.roadrush.full.objects.Explosion;
import kysk.roadrush.full.sounds.SoundManager;
import kysk.roadrush.full.vehicles.Ambulance;
import kysk.roadrush.full.vehicles.BettleBlue;
import kysk.roadrush.full.vehicles.BettleGreen;
import kysk.roadrush.full.vehicles.BettleRed;
import kysk.roadrush.full.vehicles.BettleYellow;
import kysk.roadrush.full.vehicles.BuggyBlue;
import kysk.roadrush.full.vehicles.BuggyGreen;
import kysk.roadrush.full.vehicles.BuggyRed;
import kysk.roadrush.full.vehicles.BuggyYellow;
import kysk.roadrush.full.vehicles.Dumper;
import kysk.roadrush.full.vehicles.JeepBlue;
import kysk.roadrush.full.vehicles.JeepGreen;
import kysk.roadrush.full.vehicles.JeepRed;
import kysk.roadrush.full.vehicles.JeepYellow;
import kysk.roadrush.full.vehicles.Police;
import kysk.roadrush.full.vehicles.Taxi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder holder;
	private GameLoop gameLoop;
	private Typeface font;
	
	private Bitmap background;
	
	private OnGameOverListener gameOverListener;
	
	private Explosion explosion;
	private SoundManager soundManager;
	
	private Ambulance ambulance;
	
	private BettleBlue bettleBlue;
	private BettleGreen bettleGreen;
	private BettleRed bettleRed;
	private BettleYellow bettleYellow;
	
	private BuggyBlue buggyBlue;
	private BuggyGreen buggyGreen;
	private BuggyRed buggyRed;
	private BuggyYellow buggyYellow;
	
	private Dumper dumper;

	private JeepBlue jeepBlue;
	private JeepGreen jeepGreen;
	private JeepRed jeepRed;
	private JeepYellow jeepYellow;
	
	private Police police;
	private Taxi taxi;
	
	private int ambulanceCounter;
	
	private int bettleGreenCounter;
	private int bettleRedCounter;
	private int bettleYellowCounter;
	
	private int buggyBlueCounter;
	private int buggyGreenCounter;
	private int buggyRedCounter;
	private int buggyYellowCounter;
	
	private int dumperCounter;
	
	private int jeepBlueCounter;
	private int jeepGreenCounter;
	private int jeepRedCounter;
	private int jeepYellowCounter;
	
	private int policeCounter;
	
	private int taxiCounter;
	
	private int ambulanceNumber;
	private int bettleBlueNumber;
	private int bettleGreenNumber;
	private int bettleRedNumber;
	private int bettleYellowNumber;
	private int buggyBlueNumber;
	private int buggyGreenNumber;
	private int buggyRedNumber;
	private int buggyYellowNumber;
	private int dumperNumber;
	private int jeepBlueNumber;
	private int jeepGreenNumber;
	private int jeepRedNumber;
	private int jeepYellowNumber;
	private int policeNumber;
	private int taxiNumber;
	
	private Rect src, dst;
	
	private int width, height;
	private int oldX, oldY;
	
	private int direction;
	private Random random;
	
	private boolean gameOver;
	
	private int score = 0;
	private int swipes = 0;
	private int best;
	
	private Paint paint;
	
	private boolean explosionStarted;
	private int explosionVehicleNumber = -1;
	
	private boolean isFirstRun = false;
	private int streamId;
	
	private long startTime, elapsedTime;
	
	private int stopCounter = 0;
	
	private int track;
	private int[] trackTwo = {1, 2, 5, 6, 9, 10, 13, 14};
	private int[] trackThree = {1, 2, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15};
	
	public GameView(Context context) {
		super(context);
	}
	
	public GameView(Context context, SoundManager soundManager, int track) {
		super(context);
		
		this.soundManager = soundManager;
		this.track = track;
		
		gameLoop = new GameLoop(this);
		holder = getHolder();
		holder.addCallback(this);
		
		random = new Random();
		
		font = Typeface.createFromAsset(context.getAssets(), "font.ttf");
		
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTypeface(font);
		
		switch (track) {
		case 1:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
			break;
		case 2:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.road_2);
			break;
		case 3:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.road_3);
			break;
		case 4:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.road_4);
			break;
		}
		
		explosion = new Explosion(context);
		
		ambulance = new Ambulance(context, soundManager, track);
		
		bettleBlue = new BettleBlue(context, soundManager, track);
		bettleGreen = new BettleGreen(context, soundManager, track);
		bettleRed = new BettleRed(context, soundManager, track);
		bettleYellow = new BettleYellow(context, soundManager, track);
	
		buggyBlue = new BuggyBlue(context, soundManager, track);
		buggyGreen = new BuggyGreen(context, soundManager, track);
		buggyRed = new BuggyRed(context, soundManager, track);
		buggyYellow = new BuggyYellow(context, soundManager, track);
		
		dumper = new Dumper(context, soundManager, track);
	
		jeepBlue = new JeepBlue(context, soundManager, track);
		jeepGreen = new JeepGreen(context, soundManager, track);
		jeepRed = new JeepRed(context, soundManager, track);
		jeepYellow = new JeepYellow(context, soundManager, track);
		
		police = new Police(context, soundManager, track);
		taxi = new Taxi(context, soundManager, track);
		
		ambulanceCounter = 0;
		
		bettleGreenCounter = 0;
		bettleRedCounter = 0;
		bettleYellowCounter = 0;
		
		buggyBlueCounter = 0;
		buggyGreenCounter = 0;
		buggyRedCounter = 0;
		buggyYellowCounter = 0;
		
		dumperCounter = 0;
		
		jeepBlueCounter = 0;
		jeepGreenCounter = 0;
		jeepRedCounter = 0;
		jeepYellowCounter = 0;
		
		policeCounter = 0;
		taxiCounter = 0;		
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
		soundManager.stopMusic(streamId);
	}

	@Override
	public void draw(Canvas canvas) {
		width = canvas.getWidth();
		height = canvas.getHeight();
		src = new Rect(0, 0, background.getWidth(), background.getHeight());
		dst = new Rect(0, 0, width, height);		
		canvas.drawBitmap(background, src, dst, null);
		
		if (!isFirstRun) {
			startTime = System.currentTimeMillis();
			isFirstRun = true;
			streamId = soundManager.playMusic();
		}
		
		ambulanceCounter++;
		
		bettleGreenCounter++;
		bettleRedCounter++;
		bettleYellowCounter++;
		
		buggyBlueCounter++;
		buggyGreenCounter++;
		buggyRedCounter++;
		buggyYellowCounter++;
		
		dumperCounter++;
		
		jeepBlueCounter++;
		jeepGreenCounter++;
		jeepRedCounter++;
		jeepYellowCounter++;
		
		policeCounter++;
		taxiCounter++;
		
		if (isGameOver()) {
			stopCounter++;
			swipes = ambulance.getSwipes() + bettleBlue.getSwipes() + bettleGreen.getSwipes()
						+ bettleRed.getSwipes() + bettleYellow.getSwipes() + buggyBlue.getSwipes()
						+ buggyGreen.getSwipes() + buggyRed.getSwipes() + buggyYellow.getSwipes()
						+ dumper.getSwipes() + jeepBlue.getSwipes() + jeepGreen.getSwipes()
						+ jeepRed.getSwipes() + jeepYellow.getSwipes() + police.getSwipes()
						+ taxi.getSwipes();
			paint.setColor(Color.WHITE);
			paint.setTextSize(20);
			canvas.drawText("Game Over!", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2, paint);
			ambulance.setStopped(true);
			bettleBlue.setStopped(true);
			bettleGreen.setStopped(true);
			bettleRed.setStopped(true);
			bettleYellow.setStopped(true);
			buggyBlue.setStopped(true);
			buggyGreen.setStopped(true);
			buggyRed.setStopped(true);
			buggyYellow.setStopped(true);
			dumper.setStopped(true);
			jeepBlue.setStopped(true);
			jeepGreen.setStopped(true);
			jeepRed.setStopped(true);
			jeepYellow.setStopped(true);
			police.setStopped(true);
			taxi.setStopped(true);
			if (explosion.isFinishExplosion() || stopCounter == 100) {
				stopCounter = 0;
				elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
				Log.d("Elapsed Time : ", elapsedTime / 1000 + "Seconds");
				gameOverListener.onGameOver(score, swipes, elapsedTime);
				gameLoop.setRunning(false);	
			}
		}
		
		if (!bettleBlue.isAvailableFlag()) {
			direction = getDirection();			
			bettleBlue.setDirection(direction);
			bettleBlue.setAvailableFlag(true);
		}

		if (bettleBlue.isAvailableFlag()) {
			loadBettleBlue(canvas);			
		}

		if (buggyGreenCounter == 50) {
			buggyGreenCounter = 0;
			if (!buggyGreen.isAvailableFlag()) {
				direction = getDirection();			
				buggyGreen.setDirection(direction);
				buggyGreen.setAvailableFlag(true);
			}
		}
		
		if (buggyGreen.isAvailableFlag()) {
			loadBuggyGreen(canvas);			
		}
		
		if (jeepRedCounter == 100) {
			jeepRedCounter = 0;
			if (!jeepRed.isAvailableFlag()) {
				direction = getDirection();
				jeepRed.setDirection(direction);
				jeepRed.setAvailableFlag(true);
			}
		}
		
		if (jeepRed.isAvailableFlag()) {
			loadJeepRed(canvas);			
		}
		
		if (taxiCounter == 150) {
			taxiCounter = 0;
			if (!taxi.isAvailableFlag()) {
				direction = getDirection();
				taxi.setDirection(direction);
				taxi.setAvailableFlag(true);
			}
		}
		
		if (taxi.isAvailableFlag()) {
			loadTaxi(canvas);			
		}
		
		if (bettleGreenCounter == 200) {
			bettleGreenCounter = 0;
			if (!bettleGreen.isAvailableFlag()) {
				direction = getDirection();
				bettleGreen.setDirection(direction);
				bettleGreen.setAvailableFlag(true);
			}
		}

		if (bettleGreen.isAvailableFlag()) {
			loadBettleGreen(canvas);			
		}

		if (buggyRedCounter == 250) {
			buggyRedCounter = 0;
			if (!buggyRed.isAvailableFlag()) {
				direction = getDirection();
				buggyRed.setDirection(direction);
				buggyRed.setAvailableFlag(true);
			}
		}
		
		if (buggyRed.isAvailableFlag()) {
			loadBuggyRed(canvas);			
		}
		
		if (jeepYellowCounter == 300) {
			jeepYellowCounter = 0;
			if (!jeepYellow.isAvailableFlag()) {
				direction = getDirection();
				jeepYellow.setDirection(direction);
				jeepYellow.setAvailableFlag(true);
			}
		}
		
		if (jeepYellow.isAvailableFlag()) {
			loadJeepYellow(canvas);			
		}
		
		if (dumperCounter == 350) {
			dumperCounter = 0;
			if (!dumper.isAvailableFlag()) {
				direction = getDirection();
				dumper.setDirection(direction);
				dumper.setAvailableFlag(true);
			}
		}
		
		if (dumper.isAvailableFlag()) {
			loadDumper(canvas);			
		}
		
		if (bettleRedCounter == 400) {
			bettleRedCounter = 0;
			if (!bettleRed.isAvailableFlag()) {
				direction = getDirection();
				bettleRed.setDirection(direction);
				bettleRed.setAvailableFlag(true);
			}
		}
		
		if (bettleRed.isAvailableFlag()) {
			loadBettleRed(canvas);			
		}
		
		if (buggyYellowCounter == 450) { 
			buggyYellowCounter = 0;
			if (!buggyYellow.isAvailableFlag()) {
				direction = getDirection();
				buggyYellow.setDirection(direction);
				buggyYellow.setAvailableFlag(true);
			}
		}
		
		if (buggyYellow.isAvailableFlag()) {
			loadBuggyYellow(canvas);			
		}
		
		if (jeepBlueCounter == 500) {
			jeepBlueCounter = 0;
			if (!jeepBlue.isAvailableFlag()) {
				direction = getDirection();
				jeepBlue.setDirection(direction);
				jeepBlue.setAvailableFlag(true);
			}
		}
		
		if (jeepBlue.isAvailableFlag()) {
			loadJeepBlue(canvas);			
		}
		
		if (ambulanceCounter == 750) {
			ambulanceCounter = 0;
			if (!ambulance.isAvailableFlag()) {
				direction = getDirection();
				ambulance.setDirection(direction);
				ambulance.setAvailableFlag(true);
			}
		}
		
		if (ambulance.isAvailableFlag()) {
			loadAmbulance(canvas);			
		}

		if (bettleYellowCounter == 550) {
			bettleYellowCounter = 0;
			if (!bettleYellow.isAvailableFlag()) {
				direction = getDirection();
				bettleYellow.setDirection(direction);
				bettleYellow.setAvailableFlag(true);
			}
		}

		if (bettleYellow.isAvailableFlag()) {
			loadBettleYellow(canvas);			
		}

		if (buggyBlueCounter == 600) {
			buggyBlueCounter = 0;
			if (!buggyBlue.isAvailableFlag()) {
				direction = getDirection();
				buggyBlue.setDirection(direction);
				buggyBlue.setAvailableFlag(true);
			}
		}
		
		if (buggyBlue.isAvailableFlag()) {
			loadBuggyBlue(canvas);			
		}
		
		if (jeepGreenCounter == 650) {
			jeepGreenCounter = 0;
			if (!jeepGreen.isAvailableFlag()) {
				direction = getDirection();
				jeepGreen.setDirection(direction);
				jeepGreen.setAvailableFlag(true);
			}
		}
		
		if (jeepGreen.isAvailableFlag()) {
			loadJeepGreen(canvas);			
		}
		
		if (policeCounter == 1000) {
			policeCounter = 0;
			if (!police.isAvailableFlag()) {
				direction = getDirection();
				police.setDirection(direction);
				police.setAvailableFlag(true);
			}
		}
		
		if (police.isAvailableFlag()) {
			loadPolice(canvas);			
		}
		
		score = ambulance.getScore() + bettleBlue.getScore() + bettleGreen.getScore() + bettleRed.getScore()
					+ bettleYellow.getScore() + buggyBlue.getScore() + buggyGreen.getScore() + buggyRed.getScore()
					+ buggyYellow.getScore() + dumper.getScore() + jeepBlue.getScore() + jeepGreen.getScore()
					+ jeepRed.getScore() + jeepYellow.getScore() + police.getScore() + taxi.getScore();
		paint.setTextSize(36);
		canvas.drawText("" + score, canvas.getWidth() - 100, 75, paint);
		canvas.drawText("BEST: " + best, 75, 75, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventX = (int) event.getX();
		int eventY = (int) event.getY();
		int action = event.getActionMasked();
		
		if (!isGameOver()) {
			if (action == MotionEvent.ACTION_DOWN) {
				oldX = eventX;
				oldY = eventY;
			} else if (action == MotionEvent.ACTION_UP) {
				ambulance.onSwipe(oldX, oldY, eventX, eventY);
				
				bettleBlue.onSwipe(oldX, oldY, eventX, eventY);
				bettleGreen.onSwipe(oldX, oldY, eventX, eventY);
				bettleRed.onSwipe(oldX, oldY, eventX, eventY);
				bettleYellow.onSwipe(oldX, oldY, eventX, eventY);
				
				buggyBlue.onSwipe(oldX, oldY, eventX, eventY);
				buggyGreen.onSwipe(oldX, oldY, eventX, eventY);
				buggyRed.onSwipe(oldX, oldY, eventX, eventY);
				buggyYellow.onSwipe(oldX, oldY, eventX, eventY);
				
				dumper.onSwipe(oldX, oldY, eventX, eventY);
				
				jeepBlue.onSwipe(oldX, oldY, eventX, eventY);
				jeepGreen.onSwipe(oldX, oldY, eventX, eventY);
				jeepRed.onSwipe(oldX, oldY, eventX, eventY);
				jeepYellow.onSwipe(oldX, oldY, eventX, eventY);
				
				police.onSwipe(oldX, oldY, eventX, eventY);
				taxi.onSwipe(oldX, oldY, eventX, eventY);
			}			
		}
		
		return true;
	}

	public void setBest(int best) {
		this.best = best;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean isExplosionStarted() {
		return explosionStarted;
	}

	public void setExplosionStarted(boolean explosionStarted) {
		this.explosionStarted = explosionStarted;
	}
	
	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	public void setGameOverListener(OnGameOverListener gameOverListener) {
		this.gameOverListener = gameOverListener;
	}

	public void loadAmbulance(Canvas canvas) {
		int direction = ambulance.getDirection();
		if (!ambulance.isAvailable()) {
			int multiplier = 0;
			ambulanceNumber = 0;
			multiplier = checkDirection(Constants.AMBULANCE_NUMBER, direction);
			if (multiplier != 0) {
				ambulance.setSameDirection(true);
				ambulance.setMultiplier(multiplier);
				ambulanceNumber = vehicleNumber(multiplier, direction);
			}
		}
		
		if (isAvailable(ambulanceNumber, direction)) {
			if (!ambulance.isTouchStopped()) {
				if (isStopped(ambulanceNumber)) {
					ambulance.setStopValue(getStopValue(ambulanceNumber, direction));
					ambulance.setTrafficStopped(true);
					ambulance.setStopped(true);
				} else {
					ambulance.setStopped(false);
					ambulance.setTrafficStopped(false);
				}
			}				
		}		
		
		ambulance.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.AMBULANCE_NUMBER, direction, ambulance.getStartX(), 
				ambulance.getStartY(), ambulance.getWidth() / 2, ambulance.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.AMBULANCE_NUMBER) {
				explode(canvas, Constants.AMBULANCE_NUMBER, direction, ambulance.getStartX(), ambulance.getStartY(), 
						ambulance.getWidth() / 2, ambulance.getHeight() / 2);				
			}
		}
	}
	
	public void loadBettleBlue(Canvas canvas) {
		int direction = bettleBlue.getDirection();
		
		if (!bettleBlue.isAvailable()) {
			int multiplier = 0;
			bettleBlueNumber = 0;
			multiplier = checkDirection(Constants.BETTLEBLUE_NUMBER, direction);
			if (multiplier != 0) {
				bettleBlue.setSameDirection(true);
				bettleBlue.setMultiplier(multiplier);
				bettleBlueNumber = vehicleNumber(multiplier, direction);
			}
		}

		if (isAvailable(bettleBlueNumber, direction)) {
			if (!bettleBlue.isTouchStopped()) {
				if (isStopped(bettleBlueNumber)) {
					bettleBlue.setStopValue(getStopValue(bettleBlueNumber, direction));
					bettleBlue.setTrafficStopped(true);
					bettleBlue.setStopped(true);
				} else {
					bettleBlue.setStopped(false);
					bettleBlue.setTrafficStopped(false);
				}				
			}				
		}		
		
		bettleBlue.loadVehicle(canvas, ambulance.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());

		if (detectCollision(Constants.BETTLEBLUE_NUMBER, direction, bettleBlue.getStartX(), 
				bettleBlue.getStartY(), bettleBlue.getWidth() / 2, bettleBlue.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BETTLEBLUE_NUMBER) {
				explode(canvas, Constants.BETTLEBLUE_NUMBER, direction, bettleBlue.getStartX(), bettleBlue.getStartY(),
						bettleBlue.getWidth() / 2, bettleBlue.getHeight() / 2);				
			}
		}
	}
	
	public void loadBettleGreen(Canvas canvas) {
		int direction = bettleGreen.getDirection();
		
		if (!bettleGreen.isAvailable()) {
			int multiplier = 0;
			bettleGreenNumber = 0;
			multiplier = checkDirection(Constants.BETTLEGREEN_NUMBER, direction);
			if (multiplier != 0) {
				bettleGreen.setSameDirection(true);
				bettleGreen.setMultiplier(multiplier);
				bettleGreenNumber = vehicleNumber(multiplier, direction);
			}
		}
		
		if (isAvailable(bettleGreenNumber, direction)) {
			if (!bettleGreen.isTouchStopped()) {
				if (isStopped(bettleGreenNumber)) {
					bettleGreen.setStopValue(getStopValue(bettleGreenNumber, direction));
					bettleGreen.setTrafficStopped(true);
					bettleGreen.setStopped(true);
				} else {
					bettleGreen.setStopped(false);
					bettleGreen.setTrafficStopped(false);
				}				
			}				
		}		
		
		bettleGreen.loadVehicle(canvas, ambulance.getDirection(), bettleBlue.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BETTLEGREEN_NUMBER, direction, bettleGreen.getStartX(), 
				bettleGreen.getStartY(), bettleGreen.getWidth() / 2, bettleGreen.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BETTLEGREEN_NUMBER) {
				explode(canvas, Constants.BETTLEGREEN_NUMBER, direction, bettleGreen.getStartX(), bettleGreen.getStartY(), 
						bettleGreen.getWidth() / 2, bettleGreen.getHeight() / 2);				
			}
		}
	}
	
	public void loadBettleRed(Canvas canvas) {
		int direction = bettleRed.getDirection();
		
		if (!bettleRed.isAvailable()) {
			int multiplier = 0;
			bettleRedNumber = 0;
			multiplier = checkDirection(Constants.BETTLERED_NUMBER, direction);
			if (multiplier != 0) {
				bettleRed.setSameDirection(true);
				bettleRed.setMultiplier(multiplier);
				bettleRedNumber = vehicleNumber(multiplier, direction);
			} 
		}
		
		if (isAvailable(bettleRedNumber, direction)) {
			if (!bettleRed.isTouchStopped()) {
				if (isStopped(bettleRedNumber)) {
					bettleRed.setStopValue(getStopValue(bettleRedNumber, direction));
					bettleRed.setTrafficStopped(true);
					bettleRed.setStopped(true);
				} else {
					bettleRed.setStopped(false);
					bettleRed.setTrafficStopped(false);
				}				
			}				
		}		
		
		bettleRed.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				ambulance.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BETTLERED_NUMBER, direction, bettleRed.getStartX(), 
				bettleRed.getStartY(), bettleRed.getWidth() / 2, bettleRed.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BETTLERED_NUMBER) {
				explode(canvas, Constants.BETTLERED_NUMBER, direction, bettleRed.getStartX(), bettleRed.getStartY(), 
						bettleRed.getWidth() / 2, bettleRed.getHeight() / 2);				
			}
		}
	}
	
	public void loadBettleYellow(Canvas canvas) {
		int direction = bettleYellow.getDirection();
		
		if (!bettleYellow.isAvailable()) {
			int multiplier = 0;
			bettleYellowNumber = 0;
			multiplier = checkDirection(Constants.BETTLEYELLOW_NUMBER, direction);
			if (multiplier != 0) {
				bettleYellow.setSameDirection(true);
				bettleYellow.setMultiplier(multiplier);
				bettleYellowNumber = vehicleNumber(multiplier, direction);
			} 				
		}
		
		if (isAvailable(bettleYellowNumber, direction)) {
			if (!bettleYellow.isTouchStopped()) {
				if (isStopped(bettleYellowNumber)) {
					bettleYellow.setStopValue(getStopValue(bettleYellowNumber, direction));
					bettleYellow.setTrafficStopped(true);
					bettleYellow.setStopped(true);
				} else {
					bettleYellow.setStopped(false);
					bettleYellow.setTrafficStopped(false);
				}				
			}				
		}		

		bettleYellow.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), ambulance.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BETTLEYELLOW_NUMBER, direction, bettleYellow.getStartX(), 
				bettleYellow.getStartY(), bettleYellow.getWidth() / 2, bettleYellow.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BETTLEYELLOW_NUMBER) {
				explode(canvas, Constants.BETTLEYELLOW_NUMBER, direction, bettleYellow.getStartX(), bettleYellow.getStartY(), 
						bettleYellow.getWidth() / 2, bettleYellow.getHeight() / 2);				
			}
		}
	}
	
	public void loadBuggyBlue(Canvas canvas) {
		int direction = buggyBlue.getDirection();
		
		if (!buggyBlue.isAvailable()) {
			int multiplier = 0;
			buggyBlueNumber = 0;
			multiplier = checkDirection(Constants.BUGGYBLUE_NUMBER, direction);
			if (multiplier != 0) {
				buggyBlue.setSameDirection(true);
				buggyBlue.setMultiplier(multiplier);
				buggyBlueNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(buggyBlueNumber, direction)) {
			if (!buggyBlue.isTouchStopped()) {
				if (isStopped(buggyBlueNumber)) {
					buggyBlue.setStopValue(getStopValue(buggyBlueNumber, direction));
					buggyBlue.setTrafficStopped(true);
					buggyBlue.setStopped(true);
				} else {
					buggyBlue.setStopped(false);
					buggyBlue.setTrafficStopped(false);
				}				
			}				
		}		

		buggyBlue.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), ambulance.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BUGGYBLUE_NUMBER, direction, buggyBlue.getStartX(), 
				buggyBlue.getStartY(), buggyBlue.getWidth() / 2, buggyBlue.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BUGGYBLUE_NUMBER) {
				explode(canvas, Constants.BUGGYBLUE_NUMBER, direction, buggyBlue.getStartX(), buggyBlue.getStartY(), 
						buggyBlue.getWidth() / 2, buggyBlue.getHeight() / 2);				
			}
		}
	}

	public void loadBuggyGreen(Canvas canvas) {
		int direction = buggyGreen.getDirection();
		
		if (!buggyGreen.isAvailable()) {			
			int multiplier = 0;
			buggyGreenNumber = 0;
			multiplier = checkDirection(Constants.BUGGYGREEN_NUMBER, direction);
			if (multiplier != 0) {
				buggyGreen.setSameDirection(true);
				buggyGreen.setMultiplier(multiplier);
				buggyGreenNumber = vehicleNumber(multiplier, direction);
			}		
		}
		
		if (isAvailable(buggyGreenNumber, direction)) {
			if (!buggyGreen.isTouchStopped()) {
				if (isStopped(buggyGreenNumber)) {
					buggyGreen.setStopValue(getStopValue(buggyGreenNumber, direction));
					buggyGreen.setTrafficStopped(true);
					buggyGreen.setStopped(true);
				} else {
					buggyGreen.setStopped(false);
					buggyGreen.setTrafficStopped(false);
				}				
			}				
		}		

		buggyGreen.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				ambulance.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BUGGYGREEN_NUMBER, direction, buggyGreen.getStartX(), 
				buggyGreen.getStartY(), buggyGreen.getWidth() / 2, buggyGreen.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BUGGYGREEN_NUMBER) {
				explode(canvas, Constants.BUGGYGREEN_NUMBER, direction, buggyGreen.getStartX(), buggyGreen.getStartY(), 
						buggyGreen.getWidth() / 2, buggyGreen.getHeight() / 2);				
			}
		}
	}
	
	public void loadBuggyRed(Canvas canvas) {
		int direction = buggyRed.getDirection();
		
		if (!buggyRed.isAvailable()) {
			int multiplier = 0;
			buggyRedNumber = 0;
			multiplier = checkDirection(Constants.BUGGYRED_NUMBER, direction);
			if (multiplier != 0) {
				buggyRed.setSameDirection(true);
				buggyRed.setMultiplier(multiplier);
				buggyRedNumber = vehicleNumber(multiplier, direction);
			} 
		}

		if (isAvailable(buggyRedNumber, direction)) {
			if (!buggyRed.isTouchStopped()) {
				if (isStopped(buggyRedNumber)) {
					buggyRed.setStopValue(getStopValue(buggyRedNumber, direction));
					buggyRed.setTrafficStopped(true);
					buggyRed.setStopped(true);
				} else {
					buggyRed.setStopped(false);
					buggyRed.setTrafficStopped(false);
				}				
			}				
		}		

		buggyRed.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), ambulance.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BUGGYRED_NUMBER, direction, buggyRed.getStartX(), 
				buggyRed.getStartY(), buggyRed.getWidth() / 2, buggyRed.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BUGGYRED_NUMBER) {
				explode(canvas, Constants.BUGGYRED_NUMBER, direction, buggyRed.getStartX(), buggyRed.getStartY(), 
						buggyRed.getWidth() / 2, buggyRed.getHeight() / 2);				
			}
		}
	}
	
	public void loadBuggyYellow(Canvas canvas) {
		int direction = buggyYellow.getDirection();
		
		if (!buggyYellow.isAvailable()) {
			int multiplier = 0;
			buggyYellowNumber = 0;
			multiplier = checkDirection(Constants.BUGGYYELLOW_NUMBER, direction);
			if (multiplier != 0) {
				buggyYellow.setSameDirection(true);
				buggyYellow.setMultiplier(multiplier);
				buggyYellowNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(buggyYellowNumber, direction)) {
			if (!buggyYellow.isTouchStopped()) {
				if (isStopped(buggyYellowNumber)) {
					buggyYellow.setStopValue(getStopValue(buggyYellowNumber, direction));
					buggyYellow.setTrafficStopped(true);
					buggyYellow.setStopped(true);
				} else {
					buggyYellow.setStopped(false);
					buggyYellow.setTrafficStopped(false);
				}				
			}				
		}	

		buggyYellow.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), ambulance.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.BUGGYYELLOW_NUMBER, direction, buggyYellow.getStartX(), 
				buggyYellow.getStartY(), buggyYellow.getWidth() / 2, buggyYellow.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.BUGGYYELLOW_NUMBER) {
				explode(canvas, Constants.BUGGYYELLOW_NUMBER, direction, buggyYellow.getStartX(), buggyYellow.getStartY(), 
						buggyYellow.getWidth() / 2, buggyYellow.getHeight() / 2);				
			}
		}
	}
	
	public void loadDumper(Canvas canvas) {
		int direction = dumper.getDirection();
		
		if (!dumper.isAvailable()) {
			int multiplier = 0;
			dumperNumber = 0;
			multiplier = checkDirection(Constants.DUMPER_NUMBER, direction);
			if (multiplier != 0) {
				dumper.setSameDirection(true);
				dumper.setMultiplier(multiplier);
				dumperNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(dumperNumber, direction)) {
			if (!dumper.isTouchStopped()) {
				if (isStopped(dumperNumber)) {
					dumper.setStopValue(getStopValue(dumperNumber, direction));
					dumper.setTrafficStopped(true);
					dumper.setStopped(true);
				} else {
					dumper.setStopped(false);
					dumper.setTrafficStopped(false);
				}				
			}				
		}		

		dumper.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				ambulance.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.DUMPER_NUMBER, direction, dumper.getStartX(), 
				dumper.getStartY(), dumper.getWidth() / 2, dumper.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.DUMPER_NUMBER) {
				explode(canvas, Constants.DUMPER_NUMBER, direction, dumper.getStartX(), dumper.getStartY(), 
						dumper.getWidth() / 2, dumper.getHeight() / 2);				
			}
		}
	}
	
	public void loadJeepBlue(Canvas canvas) {
		int direction = jeepBlue.getDirection();
		
		if (!jeepBlue.isAvailable()) {
			int multiplier = 0;
			jeepBlueNumber = 0;
			multiplier = checkDirection(Constants.JEEPBLUE_NUMBER, direction);
			if (multiplier != 0) {
				jeepBlue.setSameDirection(true);
				jeepBlue.setMultiplier(multiplier);
				jeepBlueNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(jeepBlueNumber, direction)) {
			if (!jeepBlue.isTouchStopped()) {
				if (isStopped(jeepBlueNumber)) {
					jeepBlue.setStopValue(getStopValue(jeepBlueNumber, direction));
					jeepBlue.setTrafficStopped(true);
					jeepBlue.setStopped(true);
				} else {
					jeepBlue.setStopped(false);
					jeepBlue.setTrafficStopped(false);
				}				
			}				
		}		

		jeepBlue.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), ambulance.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.JEEPBLUE_NUMBER, direction, jeepBlue.getStartX(), 
				jeepBlue.getStartY(), jeepBlue.getWidth() / 2, jeepBlue.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.JEEPBLUE_NUMBER) {
				explode(canvas, Constants.JEEPBLUE_NUMBER, direction, jeepBlue.getStartX(), jeepBlue.getStartY(), 
						jeepBlue.getWidth() / 2, jeepBlue.getHeight() / 2);				
			}
		}
	}
	
	public void loadJeepGreen(Canvas canvas) {
		int direction = jeepGreen.getDirection();
		
		if (!jeepGreen.isAvailable()) {
			int multiplier = 0;
			jeepGreenNumber = 0;
			multiplier = checkDirection(Constants.JEEPGREEN_NUMBER, direction);
			if (multiplier != 0) {
				jeepGreen.setSameDirection(true);
				jeepGreen.setMultiplier(multiplier);
				jeepGreenNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(jeepGreenNumber, direction)) {
			if (!jeepGreen.isTouchStopped()) {
				if (isStopped(jeepGreenNumber)) {
					jeepGreen.setStopValue(getStopValue(jeepGreenNumber, direction));
					jeepGreen.setTrafficStopped(true);
					jeepGreen.setStopped(true);
				} else {
					jeepGreen.setStopped(false);
					jeepGreen.setTrafficStopped(false);
				}				
			}				
		}		

		jeepGreen.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), ambulance.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.JEEPGREEN_NUMBER, direction, jeepGreen.getStartX(), 
				jeepGreen.getStartY(), jeepGreen.getWidth() / 2, jeepGreen.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.JEEPGREEN_NUMBER) {
				explode(canvas, Constants.JEEPGREEN_NUMBER, direction, jeepGreen.getStartX(), jeepGreen.getStartY(), 
						jeepGreen.getWidth() / 2, jeepGreen.getHeight() / 2);				
			}
		}
	}
	
	public void loadJeepRed(Canvas canvas) {
		int direction = jeepRed.getDirection();
		
		if (!jeepRed.isAvailable()) {
			int multiplier = 0;
			jeepRedNumber = 0;
			multiplier = checkDirection(Constants.JEEPRED_NUMBER, direction);
			if (multiplier != 0) {
				jeepRed.setSameDirection(true);
				jeepRed.setMultiplier(multiplier);
				jeepRedNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(jeepRedNumber, direction)) {
			if (!jeepRed.isTouchStopped()) {
				if (isStopped(jeepRedNumber)) {
					jeepRed.setStopValue(getStopValue(jeepRedNumber, direction));
					jeepRed.setTrafficStopped(true);
					jeepRed.setStopped(true);
				} else {
					jeepRed.setStopped(false);
					jeepRed.setTrafficStopped(false);
				}				
			}				
		}		

		jeepRed.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				ambulance.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.JEEPRED_NUMBER, direction, jeepRed.getStartX(), 
				jeepRed.getStartY(), jeepRed.getWidth() / 2, jeepRed.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.JEEPRED_NUMBER) {
				explode(canvas, Constants.JEEPRED_NUMBER, direction, jeepRed.getStartX(), jeepRed.getStartY(), 
						jeepRed.getWidth() / 2, jeepRed.getHeight() / 2);				
			}
		}
	}
	
	public void loadJeepYellow(Canvas canvas) {
		int direction = jeepYellow.getDirection();
		
		if (!jeepYellow.isAvailable()) {
			int multiplier = 0;
			jeepYellowNumber = 0;
			multiplier = checkDirection(Constants.JEEPYELLOW_NUMBER, direction);
			if (multiplier != 0) {
				jeepYellow.setSameDirection(true);
				jeepYellow.setMultiplier(multiplier);
				jeepYellowNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(jeepYellowNumber, direction)) {
			if (!jeepYellow.isTouchStopped()) {
				if (isStopped(jeepYellowNumber)) {
					jeepYellow.setStopValue(getStopValue(jeepYellowNumber, direction));
					jeepYellow.setTrafficStopped(true);
					jeepYellow.setStopped(true);
				} else {
					jeepYellow.setStopped(false);	
					jeepYellow.setTrafficStopped(false);
				}				
			}				
		}		

		jeepYellow.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), ambulance.getDirection(), police.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.JEEPYELLOW_NUMBER, direction, jeepYellow.getStartX(), 
				jeepYellow.getStartY(), jeepYellow.getWidth() / 2, jeepYellow.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.JEEPYELLOW_NUMBER) {
				explode(canvas, Constants.JEEPYELLOW_NUMBER, direction, jeepYellow.getStartX(), jeepYellow.getStartY(), 
						jeepYellow.getWidth() / 2, jeepYellow.getHeight() / 2);				
			}
		}
	}
	
	public void loadPolice(Canvas canvas) {
		int direction = police.getDirection();
		
		if (!police.isAvailable()) {
			int multiplier = 0;
			policeNumber = 0;
			multiplier = checkDirection(Constants.POLICE_NUMBER, direction);
			if (multiplier != 0) {
				police.setSameDirection(true);
				police.setMultiplier(multiplier);
				policeNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(policeNumber, direction)) {
			if (!police.isTouchStopped()) {
				if (isStopped(policeNumber)) {
					police.setStopValue(getStopValue(policeNumber, direction));
					police.setTrafficStopped(true);
					police.setStopped(true);
				} else {
					police.setStopped(false);
					police.setTrafficStopped(false);
				}				
			}				
		}		

		police.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), ambulance.getDirection(),
				taxi.getDirection());
		
		if (detectCollision(Constants.POLICE_NUMBER, direction, police.getStartX(), 
				police.getStartY(), police.getWidth() / 2, police.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.POLICE_NUMBER) {
				explode(canvas, Constants.POLICE_NUMBER, direction, police.getStartX(), police.getStartY(), 
						police.getWidth() / 2, police.getHeight() / 2);				
			}
		}
	}
	
	public void loadTaxi(Canvas canvas) {
		int direction = taxi.getDirection();
		
		if (!taxi.isAvailable()) {
			int multiplier = 0;
			taxiNumber = 0;
			multiplier = checkDirection(Constants.TAXI_NUMBER, direction);
			if (multiplier != 0) {
				taxi.setSameDirection(true);
				taxi.setMultiplier(multiplier);
				taxiNumber = vehicleNumber(multiplier, direction);
			} 				
		}

		if (isAvailable(taxiNumber, direction)) {
			if (!taxi.isTouchStopped()) {
				if (isStopped(taxiNumber)) {
					taxi.setStopValue(getStopValue(taxiNumber, direction));
					taxi.setTrafficStopped(true);
					taxi.setStopped(true);
				} else {
					taxi.setStopped(false);
					taxi.setTrafficStopped(false);
				}				
			}				
		}		

		taxi.loadVehicle(canvas, bettleBlue.getDirection(), bettleGreen.getDirection(), 
				bettleRed.getDirection(), bettleYellow.getDirection(), buggyBlue.getDirection(), 
				buggyGreen.getDirection(), buggyRed.getDirection(), buggyYellow.getDirection(), 
				dumper.getDirection(), jeepBlue.getDirection(), jeepGreen.getDirection(), 
				jeepRed.getDirection(), jeepYellow.getDirection(), police.getDirection(),
				ambulance.getDirection());
		
		if (detectCollision(Constants.TAXI_NUMBER, direction, taxi.getStartX(), 
				taxi.getStartY(), taxi.getWidth() / 2, taxi.getHeight() / 2)) {
			setGameOver(true);
			if (!isExplosionStarted() || explosionVehicleNumber == Constants.TAXI_NUMBER) {
				explode(canvas, Constants.TAXI_NUMBER, direction, taxi.getStartX(), taxi.getStartY(), 
						taxi.getWidth() / 2, taxi.getHeight() / 2);				
			}
		}
	}
	
	public int checkDirection(int vehicleNumber, int direction) {
		int multiplier = 0;
		
		if (vehicleNumber != Constants.AMBULANCE_NUMBER) {
			if (ambulance.getDirection() == direction) {
				multiplier = ambulance.getStartValue(multiplier);
			}			
		} 
		if (vehicleNumber != Constants.BETTLEBLUE_NUMBER) {
			if (bettleBlue.getDirection() == direction) {
				multiplier = bettleBlue.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BETTLEGREEN_NUMBER) {
			if (bettleGreen.getDirection() == direction) {
				multiplier = bettleGreen.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BETTLERED_NUMBER) {
			if (bettleRed.getDirection() == direction) {
				multiplier = bettleRed.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BETTLEYELLOW_NUMBER) {
			if (bettleYellow.getDirection() == direction) {
				multiplier = bettleYellow.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BUGGYBLUE_NUMBER) {
			if (buggyBlue.getDirection() == direction) {
				multiplier = buggyBlue.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BUGGYGREEN_NUMBER) {
			if (buggyGreen.getDirection() == direction) {
				multiplier = buggyGreen.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BUGGYRED_NUMBER) {
			if (buggyRed.getDirection() == direction) {
				multiplier = buggyRed.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.BUGGYYELLOW_NUMBER) {
			if (buggyYellow.getDirection() == direction) {
				multiplier = buggyYellow.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.DUMPER_NUMBER) {
			if (dumper.getDirection() == direction) {
				multiplier = dumper.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.JEEPBLUE_NUMBER) {
			if (jeepBlue.getDirection() == direction) {
				multiplier = jeepBlue.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.JEEPGREEN_NUMBER) {
			if (jeepGreen.getDirection() == direction) {
				multiplier = jeepGreen.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.JEEPRED_NUMBER) {
			if (jeepRed.getDirection() == direction) {
				multiplier = jeepRed.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.JEEPYELLOW_NUMBER) {
			if (jeepYellow.getDirection() == direction) {
				multiplier = jeepYellow.getStartValue(multiplier);
			}			
		}
		if (vehicleNumber != Constants.POLICE_NUMBER) {
			if (police.getDirection() == direction) {
				multiplier = police.getStartValue(multiplier);
			}
		}
		if (vehicleNumber != Constants.TAXI_NUMBER) {
			if (taxi.getDirection() == direction) {
				multiplier = taxi.getStartValue(multiplier);
			}								
		}
		return multiplier;
	}
	
	public int vehicleNumber(int multiplier, int direction) {
		int vehicleNumber = 0;
		if (ambulance.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.AMBULANCE_NUMBER;
		} else if (bettleBlue.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BETTLEBLUE_NUMBER;
		} else if (bettleGreen.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BETTLEGREEN_NUMBER;				
		} else if (bettleRed.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BETTLERED_NUMBER;				
		} else if (bettleYellow.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BETTLEYELLOW_NUMBER;	
		} else if (buggyBlue.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BUGGYBLUE_NUMBER;				
		} else if (buggyGreen.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BUGGYGREEN_NUMBER;
		} else if (buggyRed.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BUGGYRED_NUMBER;	
		} else if (buggyYellow.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.BUGGYYELLOW_NUMBER;	
		} else if (dumper.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.DUMPER_NUMBER;	
		} else if (jeepBlue.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.JEEPBLUE_NUMBER;	
		} else if (jeepGreen.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.JEEPGREEN_NUMBER;	
		} else if (jeepRed.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.JEEPRED_NUMBER;	
		} else if (jeepYellow.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.JEEPYELLOW_NUMBER;	
		} else if (police.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.POLICE_NUMBER;	
		} else if (taxi.isBlocked(multiplier, direction)) {
			vehicleNumber = Constants.TAXI_NUMBER;				
		}
		return vehicleNumber;
	}
	
	public int getStopValue(int vehicleNumber, int direction) {
		int stopValue = 0;
		switch (vehicleNumber) {
		case Constants.AMBULANCE_NUMBER:
			stopValue = ambulance.getStopValue(direction);
			break;
		case Constants.BETTLEBLUE_NUMBER:
			stopValue = bettleBlue.getStopValue(direction);
			break;
		case Constants.BETTLEGREEN_NUMBER:
			stopValue = bettleGreen.getStopValue(direction);
			break;
		case Constants.BETTLERED_NUMBER:
			stopValue = bettleRed.getStopValue(direction);
			break;
		case Constants.BETTLEYELLOW_NUMBER:
			stopValue = bettleYellow.getStopValue(direction);
			break;
		case Constants.BUGGYBLUE_NUMBER:
			stopValue = buggyBlue.getStopValue(direction);
			break;
		case Constants.BUGGYGREEN_NUMBER:
			stopValue = buggyGreen.getStopValue(direction);
			break;
		case Constants.BUGGYRED_NUMBER:
			stopValue = buggyRed.getStopValue(direction);
			break;
		case Constants.BUGGYYELLOW_NUMBER:
			stopValue = buggyYellow.getStopValue(direction);
			break;
		case Constants.DUMPER_NUMBER:
			stopValue = dumper.getStopValue(direction);
			break;
		case Constants.JEEPBLUE_NUMBER:
			stopValue = jeepBlue.getStopValue(direction);
			break;
		case Constants.JEEPGREEN_NUMBER:
			stopValue = jeepGreen.getStopValue(direction);
			break;
		case Constants.JEEPRED_NUMBER:
			stopValue = jeepRed.getStopValue(direction);
			break;
		case Constants.JEEPYELLOW_NUMBER:
			stopValue = jeepYellow.getStopValue(direction);
			break;
		case Constants.POLICE_NUMBER:
			stopValue = police.getStopValue(direction);
			break;
		case Constants.TAXI_NUMBER:
			stopValue = taxi.getStopValue(direction);
			break;
		}
		return stopValue;
	}
	
	public boolean isStopped(int vehicleNumber) {
		boolean isStopped = false;
		switch (vehicleNumber) {
		case Constants.AMBULANCE_NUMBER:
			if (ambulance.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BETTLEBLUE_NUMBER:
			if (bettleBlue.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BETTLEGREEN_NUMBER:
			if (bettleGreen.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BETTLERED_NUMBER:
			if (bettleRed.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BETTLEYELLOW_NUMBER:
			if (bettleYellow.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BUGGYBLUE_NUMBER:
			if (buggyBlue.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BUGGYGREEN_NUMBER:
			if (buggyGreen.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BUGGYRED_NUMBER:
			if (buggyRed.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.BUGGYYELLOW_NUMBER:
			if (buggyYellow.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.DUMPER_NUMBER:
			if (dumper.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.JEEPBLUE_NUMBER:
			if (jeepBlue.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.JEEPGREEN_NUMBER:
			if (jeepGreen.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.JEEPRED_NUMBER:
			if (jeepRed.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.JEEPYELLOW_NUMBER:
			if (jeepYellow.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.POLICE_NUMBER:
			if (police.isStopped()) {
				isStopped = true;
			}
			break;
		case Constants.TAXI_NUMBER:
			if (taxi.isStopped()) {
				isStopped = true;
			}
			break;
		}
		return isStopped;
	}
	
	public boolean isAvailable(int vehicleNumber, int direction) {
		boolean available = false;
		switch (vehicleNumber) {
		case Constants.AMBULANCE_NUMBER:
			if (ambulance.isAvailable() 
					&& ambulance.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BETTLEBLUE_NUMBER:
			if (bettleBlue.isAvailable()
					&& bettleBlue.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BETTLEGREEN_NUMBER:
			if (bettleGreen.isAvailable()
					&& bettleGreen.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BETTLERED_NUMBER:
			if (bettleRed.isAvailable()
					&& bettleRed.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BETTLEYELLOW_NUMBER:
			if (bettleYellow.isAvailable()
					&& bettleYellow.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BUGGYBLUE_NUMBER:
			if (buggyBlue.isAvailable()
					&& buggyBlue.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BUGGYGREEN_NUMBER:
			if (buggyGreen.isAvailable()
					&& buggyGreen.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BUGGYRED_NUMBER:
			if (buggyRed.isAvailable()
					&& buggyRed.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.BUGGYYELLOW_NUMBER:
			if (buggyYellow.isAvailable()
					&& buggyYellow.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.DUMPER_NUMBER:
			if (dumper.isAvailable()
					&& dumper.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.JEEPBLUE_NUMBER:
			if (jeepBlue.isAvailable()
					&& jeepBlue.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.JEEPGREEN_NUMBER:
			if (jeepGreen.isAvailable()
					&& jeepGreen.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.JEEPRED_NUMBER:
			if (jeepRed.isAvailable()
					&& jeepRed.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.JEEPYELLOW_NUMBER:
			if (jeepYellow.isAvailable()
					&& jeepYellow.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.POLICE_NUMBER:
			if (police.isAvailable()
					&& police.getDirection() == direction) {
				available = true;
			}
			break;
		case Constants.TAXI_NUMBER:
			if (taxi.isAvailable()
					&& taxi.getDirection() == direction) {
				available = true;
			}
			break;
		}
		return available;
	}
	
	public boolean detectCollision(int vehicleNumber, int direction, int startX, int startY, 
			int width, int height) {
		boolean collided = false;
		if (vehicleNumber != Constants.AMBULANCE_NUMBER
				&& ambulance.getDirection() != -1) {
			collided = ambulance.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		} 
		if (vehicleNumber != Constants.BETTLEBLUE_NUMBER
				&& bettleBlue.getDirection() != -1) {
			collided = bettleBlue.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BETTLEGREEN_NUMBER
				&& bettleGreen.getDirection() != -1) {
			collided = bettleGreen.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BETTLERED_NUMBER
				&& bettleRed.getDirection() != -1) {
			collided = bettleRed.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BETTLEYELLOW_NUMBER
				&& bettleYellow.getDirection() != -1) {
			collided = bettleYellow.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BUGGYBLUE_NUMBER
				&& buggyBlue.getDirection() != -1) {
			collided = buggyBlue.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BUGGYGREEN_NUMBER
				&& buggyGreen.getDirection() != -1) {
			collided = buggyGreen.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BUGGYRED_NUMBER
				&& buggyRed.getDirection() != -1) {
			collided = buggyRed.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.BUGGYYELLOW_NUMBER
				&& buggyYellow.getDirection() != -1) {
			collided = buggyYellow.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.DUMPER_NUMBER
				&& dumper.getDirection() != -1) {
			collided = dumper.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.JEEPBLUE_NUMBER
				&& jeepBlue.getDirection() != -1) {
			collided = jeepBlue.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.JEEPGREEN_NUMBER
				&& jeepGreen.getDirection() != -1) {
			collided = jeepGreen.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.JEEPRED_NUMBER
				&& jeepRed.getDirection() != -1) {
			collided = jeepRed.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.JEEPYELLOW_NUMBER
				&& jeepYellow.getDirection() != -1) {
			collided = jeepYellow.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.POLICE_NUMBER
				&& police.getDirection() != -1) {
			collided = police.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		if (vehicleNumber != Constants.TAXI_NUMBER
				&& taxi.getDirection() != -1) {
			collided = taxi.detectCollision(direction, startX, startY, width, height);
			if (collided) {
				return collided;
			}
		}
		return collided;
	}
	
	public void explode(Canvas canvas, int vehicleNumber,
			int direction, int startX, int startY, int width, int height) {
		if (!isExplosionStarted()) {
			soundManager.playCrashSound();						
		}
		setExplosionStarted(true);
		explosionVehicleNumber = vehicleNumber;
		explosion.setStartExplosion(true);
		switch (track) {
		case 1:
		case 4:
			switch (direction) {
			case 0:
			case 1:
			case 2:
			case 3:
				explosion.setStartX(startX - width / 2 + 5);
				explosion.setStartY(startY - height - 10);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				explosion.setStartX(startX - width - 15);
				explosion.setStartY(startY - height - 10);
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height + 25);
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height - 15);
				break;
			}
			break;
		case 2:
			switch (direction) {
			case 1:
			case 2:
				explosion.setStartX(startX - width / 2 + 5);
				explosion.setStartY(startY - height - 10);
				break;
			case 5:
			case 6:
				explosion.setStartX(startX - width - 15);
				explosion.setStartY(startY - height - 10);
				break;
			case 9:
			case 10:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height + 25);
				break;
			case 13:
			case 14:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height - 15);
				break;
			}
			break;
		case 3:
			switch (direction) {
			case 1:
			case 2:
				explosion.setStartX(startX - width / 2 + 5);
				explosion.setStartY(startY - height - 10);
				break;
			case 5:
			case 6:
				explosion.setStartX(startX - width - 15);
				explosion.setStartY(startY - height - 10);
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height + 25);
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				explosion.setStartX(startX - width - 10);
				explosion.setStartY(startY - height - 15);
				break;
			}
			break;
		}
		explosion.draw(canvas);
	}
	
	public int getDirection() {
		int direction = 0;
		switch (track) {
		case 1:
		case 4:
			direction = random.nextInt(16);
			break;
		case 2:
			direction = trackTwo[random.nextInt(8)];
			break;
		case 3:
			direction = trackThree[random.nextInt(12)];
			break;
		}
		return direction;
	}
	
	public void pause() {
		soundManager.pauseMusic(streamId);
		gameLoop.setPaused(true);
	}
	
	public void resume() {
		if (gameLoop.isPaused()) {
			gameLoop.setPaused(false);
			soundManager.resumeMusic(streamId);
		}
	}
}