package com.savetheeggs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.savetheeggs.eggs.BlackEgg;
import com.savetheeggs.eggs.BlueEgg;
import com.savetheeggs.eggs.BrownEgg;
import com.savetheeggs.eggs.CyanEgg;
import com.savetheeggs.eggs.GoldenEgg;
import com.savetheeggs.eggs.GreenEgg;
import com.savetheeggs.eggs.GreyEgg;
import com.savetheeggs.eggs.NormalEgg;
import com.savetheeggs.eggs.OrangeEgg;
import com.savetheeggs.eggs.PinkEgg;
import com.savetheeggs.eggs.RedEgg;
import com.savetheeggs.eggs.VioletEgg;
import com.savetheeggs.objects.Bat;
import com.savetheeggs.objects.Bubbles;
import com.savetheeggs.objects.Butterfly;
import com.savetheeggs.objects.Fish;
import com.savetheeggs.objects.Ghost;
import com.savetheeggs.objects.JellyMonster;
import com.savetheeggs.objects.Lightening;
import com.savetheeggs.objects.Rain;

public class GameViewTouch extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder holder;
	private GameLoopTouch gameLoop;
	
	private int level;
	
	private Bitmap background;
	private Bitmap bucket;
	
	private Bubbles bubble, bubbleTwo;
	private JellyMonster monster;
	private Fish fish;
	private Score eggScore;
		
	private NormalEgg normalEgg;
	private BlackEgg blackEgg;
	private GoldenEgg goldenEgg;
	private BrownEgg brownEgg;
	private BlueEgg blueEgg;
	private CyanEgg cyanEgg;
	private GreyEgg greyEgg;
	private OrangeEgg orangeEgg;
	private PinkEgg pinkEgg;
	private VioletEgg violetEgg;
	private GreenEgg greenEgg;
	private RedEgg redEgg;
	
	private Butterfly butterfly;
	private Bat bat;
	private Ghost ghost;
	private Lightening lightening;
	private Rain rain;
	
	private PlayerLife playerLife;

	private Rect src, dst;
	
	private float x, y;
	private float width, height;
	
	private int score;
	private int lastScore;
	private int speed;
	private int scoreFactor;
	private int target;
	
	private int blackCounter;
	private int goldCounter;
	private int brownCounter;
	private int blueCounter;
	private int cyanCounter;
	private int greyCounter;
	private int orangeCounter;
	private int violetCounter;
	private int pinkCounter;
	private int greenCounter;
	private int redCounter;
	
	private int lighteningCounter;
	private int rainCounter;
	
	private Paint paint;
	
	private SoundPool soundPool;
	private int eggCatched;
	private int levelFail, levelClear;
	
	private boolean gameOver;
	private boolean levelCleared;
	private boolean dialogFlag;
	
	private int gameMode;
	
	private Typeface font;
	
	private OnLevelCompleteListener onLevelCompleteListener;
	
	public GameViewTouch(Context context) {
		super(context);
	}
	
	public GameViewTouch(Context context, int level, int gameMode) {
		super(context);
		
		this.level = level;
		this.gameMode = gameMode;
		
		gameLoop = new GameLoopTouch(this);
		holder = getHolder();
		holder.addCallback(this);
		
		font = Typeface.createFromAsset(context.getAssets(), "font.ttf");
				
		switch (gameMode) {
		case 0:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.water_bg);
			bubble = new Bubbles(context);
			bubbleTwo = new Bubbles(context);
			monster = new JellyMonster(context);
			fish = new Fish(context);
			break;
		case 1:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.halloween);
			bat = new Bat(context);
			lightening = new Lightening(context);
			ghost = new Ghost(context);
			break;
		case 2:
			background = BitmapFactory.decodeResource(getResources(), R.drawable.forest_bg);
			butterfly = new Butterfly(context);
			rain = new Rain(context);
			break;
		}

		bucket = BitmapFactory.decodeResource(getResources(), R.drawable.nest);
		
		paint = new Paint();
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		eggCatched = soundPool.load(context, R.raw.egg_catch, 1);
		levelFail = soundPool.load(context, R.raw.level_fail, 1);
		levelClear = soundPool.load(context, R.raw.level_complete, 1);
				
		eggScore = new Score(context);
		
		normalEgg = new NormalEgg(context);
		goldenEgg = new GoldenEgg(context);
		blackEgg = new BlackEgg(context);
		brownEgg = new BrownEgg(context);
		blueEgg = new BlueEgg(context);
		cyanEgg = new CyanEgg(context);
		greyEgg = new GreyEgg(context);
		orangeEgg = new OrangeEgg(context);
		pinkEgg = new PinkEgg(context);
		violetEgg = new VioletEgg(context);
		greenEgg = new GreenEgg(context);
		redEgg = new RedEgg(context);
		
		playerLife = new PlayerLife(context);
		
		initialize();
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
		src = new Rect(0, 0, background.getWidth(), background.getHeight());
		dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());		
		canvas.drawBitmap(background, src, dst, null);
		y = height - bucket.getHeight();
		canvas.drawBitmap(bucket, x, y, null);

		switch (gameMode) {
		case 0:
			if (!bubble.isAvailable()) {
				bubble.setStartX((int)width - 150);
				bubble.setStartY((int)height - 150);
				bubble.setCount(1);
			}
			bubble.draw(canvas);
			
			if (!bubbleTwo.isAvailable() && bubble.getStartY() <= height / 2 - 150) {
				bubbleTwo.setStartX(50);
				bubbleTwo.setStartY((int)height - 150);
				bubbleTwo.setCount(1);
			}
			bubbleTwo.draw(canvas);
			
			monster.draw(canvas);
			fish.draw(canvas);
			break;
		case 1:
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
		case 2:
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
		}
		
		paint.setColor(Color.parseColor("#00222B"));
		paint.setTextSize(22);
		paint.setTypeface(font);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		canvas.drawText("" + target, width - 100, 30, paint);
		canvas.drawText(Constants.TARGET, width - 220, 30, paint);
		canvas.drawText("" + score, width - 100, 60, paint);
		canvas.drawText(Constants.SCORE, width - 220, 60, paint);
		
		paint.setTextSize(36);
				
		if (!isGameOver()) {
			if (playerLife.isLifeAvailable()) {
				loadLevel(canvas);
				playerLife.draw(canvas);
			} else {
				setGameOver(true);
			}
		} else {
			if (isLevelCleared() && !isDialogFlag()) {
				onLevelCompleteListener.onLevelSuccess(playerLife.getLifeCount());
				soundPool.play(levelClear, 1.0f, 1.0f, 0, 0, 1.5f);
			} 
			if (!isLevelCleared() && !isDialogFlag()){
				onLevelCompleteListener.onLevelFailed();
				soundPool.play(levelFail, 1.0f, 1.0f, 0, 0, 1.5f);
			}
			setDialogFlag(true);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (event.getX() > 0 && event.getX() < width && event.getY() > (height - bucket.getHeight()) 
					&& event.getY() < height) {
				x = event.getX() - bucket.getWidth() / 2;					
			}
			break;
		}
		return true;
	}
			
	public void loadLevel(Canvas canvas) {
		switch (level) {
		case 1:
			levelOne(canvas);
			break;
		case 2:
			levelTwo(canvas);
			break;
		case 3:
			levelThree(canvas);
			break;
		case 4:
			levelFour(canvas);
			break;
		case 5:
			levelFive(canvas);
			break;
		case 6:
			levelSix(canvas);
			break;
		case 7:
			levelSeven(canvas);
			break;
		case 8:
			levelEight(canvas);
			break;
		case 9:
			levelNine(canvas);
			break;
		case 10:
			levelTen(canvas);
			break;
		case 11:
			levelEleven(canvas);
			break;
		case 12:
			levelTwelve(canvas);
			break;
		case 13:
			levelThirteen(canvas);
			break;
		case 14:
			levelFourteen(canvas);
			break;
		case 15:
			levelFifteen(canvas);
			break;
		case 16:
			levelSixteen(canvas);
			break;
		case 17:
			levelSeventeen(canvas);
			break;
		case 18:
			levelEighteen(canvas);
			break;
		case 19:
			levelNineteen(canvas);
			break;
		case 20:
			levelTwenty(canvas);
			break;
		case 21:
			levelTwentyOne(canvas);
			break;
		case 22:
			levelTwentyTwo(canvas);
			break;
		case 23:
			levelTwentyThree(canvas);
			break;
		case 24:
			levelTwentyFour(canvas);
			break;
		case 25:
			levelTwentyFive(canvas);
			break;
		case 26:
			levelTwentySix(canvas);
			break;
		case 27:
			levelTwentySeven(canvas);
			break;
		case 28:
			levelTwentyEight(canvas);
			break;
		case 29:
			levelTwentyNine(canvas);
			break;
		case 30:
			levelThirty(canvas);
			break;
		case 31:
			levelThirtyOne(canvas);
			break;
		case 32:
			levelThirtyTwo(canvas);
			break;
		case 33:
			levelThirtyThree(canvas);
			break;
		case 34:
			levelThirtyFour(canvas);
			break;
		case 35:
			levelThirtyFive(canvas);
			break;
		case 36:
			levelThirtySix(canvas);
			break;
		case 37:
			levelThirtySeven(canvas);
			break;
		case 38:
			levelThirtyEight(canvas);
			break;
		case 39:
			levelThirtyNine(canvas);
			break;
		case 40:
			levelFourty(canvas);
			break;
		case 41:
			levelFourtyOne(canvas);
			break;
		case 42:
			levelFourtyTwo(canvas);
			break;
		case 43:
			levelFourtyThree(canvas);
			break;
		case 44:
			levelFourtyFour(canvas);
			break;
		case 45:
			levelFourtyFive(canvas);
			break;
		case 46:
			levelFourtySix(canvas);
			break;
		case 47:
			levelFourtySeven(canvas);
			break;
		case 48:
			levelFourtyEight(canvas);
			break;
		case 49:
			levelFourtyNine(canvas);
			break;
		case 50:
			levelFifty(canvas);
			break;
		case 51:
			levelFiftyOne(canvas);
			break;
		case 52:
			levelFiftyTwo(canvas);
			break;
		case 53:
			levelFiftyThree(canvas);
			break;
		case 54:
			levelFiftyFour(canvas);
			break;
		case 55:
			levelFiftyFive(canvas);
			break;
		case 56:
			levelFiftySix(canvas);
			break;
		case 57:
			levelFiftySeven(canvas);
			break;
		case 58:
			levelFiftyEight(canvas);
			break;
		case 59:
			levelFiftyNine(canvas);
			break;
		case 60:
			levelSixty(canvas);
			break;
		case 61:
			levelSixtyOne(canvas);
			break;
		case 62:
			levelSixtyTwo(canvas);
			break;
		case 63:
			levelSixtyThree(canvas);
			break;
		case 64:
			levelSixtyFour(canvas);
			break;
		case 65:
			levelSixtyFive(canvas);
			break;
		case 66:
			levelSixtySix(canvas);
			break;
		case 67:
			levelSixtySeven(canvas);
			break;
		case 68:
			levelSixtyEight(canvas);
			break;
		case 69:
			levelSixtyNine(canvas);
			break;
		case 70:
			levelSeventy(canvas);
			break;
		case 71:
			levelSeventyOne(canvas);
			break;
		case 72:
			levelSeventyTwo(canvas);
			break;
		case 73:
			levelSeventyThree(canvas);
			break;
		case 74:
			levelSeventyFour(canvas);
			break;
		case 75:
			levelSeventyFive(canvas);
			break;
		case 76:
			levelSeventySix(canvas);
			break;
		case 77:
			levelSeventySeven(canvas);
			break;
		case 78:
			levelSeventyEight(canvas);
			break;
		case 79:
			levelSeventyNine(canvas);
			break;
		case 80:
			levelEighty(canvas);
			break;
		case 81:
			levelEightyOne(canvas);
			break;
		case 82:
			levelEightyTwo(canvas);
			break;
		case 83:
			levelEightyThree(canvas);
			break;
		case 84:
			levelEightyFour(canvas);
			break;
		case 85:
			levelEightyFive(canvas);
			break;
		case 86:
			levelEightySix(canvas);
			break;
		case 87:
			levelEightySeven(canvas);
			break;
		case 88:
			levelEightyEight(canvas);
			break;
		case 89:
			levelEightyNine(canvas);
			break;
		case 90:
			levelNinety(canvas);
			break;
		case 91:
			levelNinetyOne(canvas);
			break;
		case 92:
			levelNinetyTwo(canvas);
			break;
		case 93:
			levelNinetyThree(canvas);
			break;
		case 94:
			levelNinetyFour(canvas);
			break;
		case 95:
			levelNinetyFive(canvas);
			break;
		case 96:
			levelNinetySix(canvas);
			break;
		}
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean isLevelCleared() {
		return levelCleared;
	}

	public void setLevelCleared(boolean levelCleared) {
		this.levelCleared = levelCleared;
	}

	public void setOnLevelCompleteListener(
			OnLevelCompleteListener onLevelCompleteListener) {
		this.onLevelCompleteListener = onLevelCompleteListener;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}

	public int getScoreFactor() {
		return scoreFactor;
	}
	
	public void resume() {
		gameLoop.setRunning(true);
	}
	
	public void pause() {
		gameLoop.setRunning(false);
	}
	
	public PlayerLife getPlayerLife() {
		return playerLife;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isDialogFlag() {
		return dialogFlag;
	}

	public void setDialogFlag(boolean dialogFlag) {
		this.dialogFlag = dialogFlag;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public void initialize() {
		score = 0;
		speed = 8;
		blackCounter = 0;
		goldCounter = 0;
		brownCounter = 0;
		blueCounter = 0;
		cyanCounter = 0;
		greyCounter = 0;
		orangeCounter = 0;
		violetCounter = 0;
		pinkCounter = 0;
		greenCounter = 0;
		redCounter = 0;
		lighteningCounter = 0;
		rainCounter = 0;
		target = level * 100;
		scoreFactor = target / 3;
		lastScore = scoreFactor;
		setGameOver(false);
		setLevelCleared(false);
		setDialogFlag(false);
		playerLife.setLifeCount(3);
		playerLife.setLifeAvailable(true);
	}
	
	public void loadWhiteEgg(Canvas canvas) {
		normalEgg.setSpeed(speed);					
		normalEgg.draw(canvas);
		if (score >= lastScore) {
			lastScore += scoreFactor; 
			speed++;
		}
		if (score >= target) {
			setGameOver(true);
			setLevelCleared(true);
		}
		if (normalEgg.isBrokenEggVisible()) {
			if (!normalEgg.isBrokenEggFlag()) {
				playerLife.decrementLifeCount();
				normalEgg.setBrokenEggFlag(true);
			}			
		}
		if(normalEgg.detectCollision(x, y, bucket.getWidth())) {
			score += 5;
			soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
			eggScore.setNumber(5);
			eggScore.setStartX((int)x + bucket.getWidth() / 2);
			eggScore.setStartY((int)y - 15);
		}
		eggScore.draw(canvas);
	}
	
	public void loadBlackEgg(Canvas canvas) {
		blackCounter++;
		if (blackCounter == 100) {
			blackCounter = 0;
			blackEgg.setAvailableFlag(true);
		}
		if (blackEgg.isAvailableFlag()) {
			blackEgg.setSpeed(speed);
			blackEgg.draw(canvas);			
			if(blackEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 1;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(0);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}
	}
	
	public void loadGoldEgg(Canvas canvas) {
		goldCounter++;
		if (goldCounter == 200) {
			goldCounter = 0;
			goldenEgg.setAvailableFlag(true);
		}
		if (goldenEgg.isAvailableFlag()) {
			goldenEgg.setSpeed(speed);
			goldenEgg.draw(canvas);			
			if(goldenEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 10;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(10);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}		
	}
	
	public void loadBrownEgg(Canvas canvas) {
		brownCounter++;
		if (brownCounter == 300) {
			brownCounter = 0;
			brownEgg.setAvailableFlag(true);
		}
		if (brownEgg.isAvailableFlag()) {
			brownEgg.setSpeed(speed);
			brownEgg.draw(canvas);			
			if(brownEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 15;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(15);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}				
	}
	
	public void loadBlueEgg(Canvas canvas) {
		blueCounter++;
		if (blueCounter == 400) {
			blueCounter = 0;
			blueEgg.setAvailableFlag(true);
		}
		if (blueEgg.isAvailableFlag()) {
			blueEgg.setSpeed(speed);
			blueEgg.draw(canvas);			
			if(blueEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 20;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(20);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}						
	}
	
	public void loadCyanEgg(Canvas canvas) {
		cyanCounter++;
		if (cyanCounter == 500) {
			cyanCounter = 0;
			cyanEgg.setAvailableFlag(true);
		}
		if (cyanEgg.isAvailableFlag()) {
			cyanEgg.setSpeed(speed);
			cyanEgg.draw(canvas);			
			if(cyanEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 25;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(25);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}								
	}
	
	public void loadGreyEgg(Canvas canvas) {
		greyCounter++;
		if (greyCounter == 600) {
			greyCounter = 0;
			greyEgg.setAvailableFlag(true);
		}
		if (greyEgg.isAvailableFlag()) {
			greyEgg.setSpeed(speed);
			greyEgg.draw(canvas);			
			if(greyEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 50;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(50);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}										
	}
	
	public void loadOrangeEgg(Canvas canvas) {
		orangeCounter++;
		if (orangeCounter == 1000) {
			orangeCounter = 0;
			orangeEgg.setAvailableFlag(true);
		}
		if (orangeEgg.isAvailableFlag()) {
			orangeEgg.setSpeed(speed);
			orangeEgg.draw(canvas);			
			if(orangeEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 100;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(100);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}												
	}
	
	public void loadVioletEgg(Canvas canvas) {
		violetCounter++;
		if (violetCounter == 850) {
			violetCounter = 0;
			violetEgg.setAvailableFlag(true);
		}
		if (violetEgg.isAvailableFlag()) {
			violetEgg.setSpeed(speed);
			violetEgg.draw(canvas);			
			if(violetEgg.detectCollision(x, y, bucket.getWidth())) {
				score -= 150;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(-150);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}														
	}
	
	public void loadPinkEgg(Canvas canvas) {
		pinkCounter++;
		if (pinkCounter == 1500) {
			pinkCounter = 0;
			pinkEgg.setAvailableFlag(true);
		}
		if (pinkEgg.isAvailableFlag()) {
			pinkEgg.setSpeed(speed);
			pinkEgg.draw(canvas);			
			if(pinkEgg.detectCollision(x, y, bucket.getWidth())) {
				score += 200;
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(200);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}														
	}
	
	public void loadGreenEgg(Canvas canvas) {
		greenCounter++;
		if (greenCounter == 1250) {
			greenCounter = 0;
			greenEgg.setAvailableFlag(true);
		}
		if (greenEgg.isAvailableFlag()) {
			greenEgg.setSpeed(speed);
			greenEgg.draw(canvas);			
			if(greenEgg.detectCollision(x, y, bucket.getWidth())) {
				if (playerLife.getLifeCount() < 3) {
					playerLife.incrementLifeCount();
				}
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(+1);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}																		
	}
	
	public void loadRedEgg(Canvas canvas) {
		redCounter++;
		if (redCounter == 750) {
			redCounter = 0;
			redEgg.setAvailableFlag(true);
		}
		if (redEgg.isAvailableFlag()) {
			redEgg.setSpeed(speed);
			redEgg.draw(canvas);			
			if(redEgg.detectCollision(x, y, bucket.getWidth())) {
				if (playerLife.isLifeAvailable()) {
					playerLife.decrementLifeCount();
				}
				soundPool.play(eggCatched, 1.0f, 1.0f, 0, 0, 1.5f);
				eggScore.setNumber(-1);
				eggScore.setStartX((int)x + bucket.getWidth() / 2);
				eggScore.setStartY((int)y - 15);
			}
			eggScore.draw(canvas);		
		}																
	}
	
	public void levelOne(Canvas canvas) {
		loadWhiteEgg(canvas);
	}
	
	public void levelTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
	}

	public void levelThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
	}

	public void levelFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBlackEgg(canvas);
	}

	public void levelFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
	}

	public void levelSix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
	}

	public void levelSeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
	}

	public void levelEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
	}

	public void levelNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
	}

	public void levelTen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
	}

	public void levelEleven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelTwelve(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelFourteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelFifteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelSixteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelSeventeen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelEighteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);		

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelNineteen(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwenty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);

		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);		
	}
	
	public void levelTwentySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);		
	}
	
	public void levelTwentyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelTwentyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelThirty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelThirtyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelThirtyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
	
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFourtyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelFifty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlueEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelFiftyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelSixty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSixtyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelSeventy(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBrownEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlueEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadCyanEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGreyEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelSeventyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEighty(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadGreyEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightySeven(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyEight(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelEightyNine(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}
	
	public void levelNinety(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetyOne(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetyTwo(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetyThree(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetyFour(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetyFive(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

	public void levelNinetySix(Canvas canvas) {
		loadWhiteEgg(canvas);
		loadBlackEgg(canvas);
		loadGoldEgg(canvas);
		loadBrownEgg(canvas);
		loadBlueEgg(canvas);
		loadCyanEgg(canvas);
		loadGreyEgg(canvas);
		loadOrangeEgg(canvas);
		loadPinkEgg(canvas);
		
		loadGreenEgg(canvas);
		loadRedEgg(canvas);
		
		loadVioletEgg(canvas);
	}

}