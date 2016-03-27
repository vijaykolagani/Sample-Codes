package com.savetheeggs.worlds;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.savetheeggs.Constants;
import com.savetheeggs.GameView;
import com.savetheeggs.GameViewTouch;
import com.savetheeggs.OnLevelCompleteListener;
import com.savetheeggs.R;
import com.savetheeggs.levels.LevelViewPager;

public class SaveTheEggsForest extends Activity implements OnLevelCompleteListener, OnClickListener {
	
	private int levelNo;
	private GameView gameView;
	private GameViewTouch gameViewTouch;
	private Dialog dialog;
	private Button menu, replay, next;
	private ImageView starTwo, starThree;
	private ImageView gravity, touch;
	private Bitmap star;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private int status;
	private TextView message, tvGravity, tvTouch;
	private Typeface font;
	private SoundPool soundPool;
	private int click;
	private int control;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.control_layout);
		
		gravity = (ImageView) this.findViewById(R.id.gravity);
		touch = (ImageView) this.findViewById(R.id.touch);
		
		tvGravity = (TextView) this.findViewById(R.id.tv_gravity);
		tvTouch = (TextView) this.findViewById(R.id.tv_touch);
		
		Bundle b = getIntent().getExtras();
		levelNo = b.getInt(Constants.LEVEL_NUMBER);
		
		dialog = new Dialog(this);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		star = BitmapFactory.decodeResource(getResources(), R.drawable.star_black);
		
		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(this, R.raw.click, 1);
		
		tvGravity.setTypeface(font);
		tvTouch.setTypeface(font);
		
		gravity.setOnClickListener(this);
		touch.setOnClickListener(this);
	}

	public void onLevelSuccess(final int stars) {
		saveLevelStars(stars);
		unlockNextLevel(levelNo);
		int level = prefs.getInt(Constants.FOREST_WORLD_LEVELS, 0);
		if (levelNo >= level) {
			editor = prefs.edit();
			editor.putInt(Constants.FOREST_WORLD_LEVELS, levelNo);
			editor.commit();			
		}
		runOnUiThread(new Runnable() {
			
			public void run() {
				Log.d("SaveTheEggsForest", "onLevelSuccess");
				dialog.setContentView(R.layout.level_dialog);
				menu = (Button) dialog.findViewById(R.id.bt_menu);
				replay = (Button) dialog.findViewById(R.id.bt_replay);
				next = (Button) dialog.findViewById(R.id.bt_next);
				message = (TextView) dialog.findViewById(R.id.level_message);
				
				message.setTypeface(font);
				
				starTwo = (ImageView) dialog.findViewById(R.id.star_two);
				starThree = (ImageView) dialog.findViewById(R.id.star_three);
				
				if (stars == 2) {
					starThree.setImageBitmap(star);
				} else if (stars == 1) {
					starTwo.setImageBitmap(star);
					starThree.setImageBitmap(star);					
				}
				
				menu.setOnClickListener(SaveTheEggsForest.this);
				replay.setOnClickListener(SaveTheEggsForest.this);
				next.setOnClickListener(SaveTheEggsForest.this);
				
				dialog.show();						
			}
		});
	}

	public void onLevelFailed() {
		runOnUiThread(new Runnable() {
			
			public void run() {
				Log.d("SaveTheEggsForest", "onLevelFailed");
				dialog.setContentView(R.layout.level_fail_dialog);
				menu = (Button) dialog.findViewById(R.id.bt_menu);
				replay = (Button) dialog.findViewById(R.id.bt_replay);
				message = (TextView) dialog.findViewById(R.id.level_message);
				
				message.setTypeface(font);

				menu.setOnClickListener(SaveTheEggsForest.this);
				replay.setOnClickListener(SaveTheEggsForest.this);
				
				dialog.show();						
			}
		});		
	}

	public void onClick(View v) {
		soundPool.play(click, 1.0f, 1.0f, 0, 0, 1.5f);
		switch (v.getId()) {
		case R.id.bt_replay:
			switch (control) {
			case 0:
				gameView.initialize();
				break;
			case 1:
				gameViewTouch.initialize();
				break;
			}
			dialog.dismiss();
			break;
		case R.id.bt_menu:
			Intent intent = new Intent(SaveTheEggsForest.this, LevelViewPager.class);
			intent.putExtra(Constants.WORLD_NUMBER, Constants.FOREST_GAME_MODE);
			startActivity(intent);
			dialog.dismiss();
			finish();
			break;
		case R.id.bt_next:
			if (levelNo < 96) {
				levelNo += 1;				
			} else {
				levelNo = 1;
			}
			switch (control) {
			case 0:
				gameView.setLevel(levelNo);
				gameView.initialize();
				break;
			case 1:
				gameViewTouch.setLevel(levelNo);
				gameViewTouch.initialize();
				break;
			}
			dialog.dismiss();
			break;
		case R.id.gravity:
			gameView = new GameView(this, levelNo, Constants.FOREST_GAME_MODE);
			setContentView(gameView);
			gameView.setOnLevelCompleteListener(this);
			control = 0;
			break;
		case R.id.touch:
			gameViewTouch = new GameViewTouch(this, levelNo, Constants.FOREST_GAME_MODE);
			setContentView(gameViewTouch);
			gameViewTouch.setOnLevelCompleteListener(this);
			control = 1;
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent(SaveTheEggsForest.this, LevelViewPager.class);
			intent.putExtra(Constants.WORLD_NUMBER, Constants.FOREST_GAME_MODE);
			startActivity(intent);
			this.finish();
	        return true;
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }
	}	
	
	public void saveLevelStars(int stars) {
		editor = prefs.edit();
		switch (levelNo) {
		case 1:
			status = prefs.getInt(Constants.FOREST_ONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_ONE_STARS, stars);				
			}
			break;
		case 2:
			status = prefs.getInt(Constants.FOREST_TWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWO_STARS, stars);				
			}
			break;
		case 3:
			status = prefs.getInt(Constants.FOREST_THREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THREE_STARS, stars);
			}
			break;
		case 4:
			status = prefs.getInt(Constants.FOREST_FOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOUR_STARS, stars);
			}
			break;
		case 5:
			status = prefs.getInt(Constants.FOREST_FIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIVE_STARS, stars);
			}
			break;
		case 6:
			status = prefs.getInt(Constants.FOREST_SIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIX_STARS, stars);
			}
			break;
		case 7:
			status = prefs.getInt(Constants.FOREST_SEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVEN_STARS, stars);
			}
			break;
		case 8:
			status = prefs.getInt(Constants.FOREST_EIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHT_STARS, stars);
			}
			break;
		case 9:
			status = prefs.getInt(Constants.FOREST_NINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINE_STARS, stars);
			}
			break;
		case 10:
			status = prefs.getInt(Constants.FOREST_TEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TEN_STARS, stars);
			}
			break;
		case 11:
			status = prefs.getInt(Constants.FOREST_ELEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_ELEVEN_STARS, stars);
			}
			break;
		case 12:
			status = prefs.getInt(Constants.FOREST_TWELVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWELVE_STARS, stars);
			}
			break;
		case 13:
			status = prefs.getInt(Constants.FOREST_THIRTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTEEN_STARS, stars);
			}
			break;
		case 14:
			status = prefs.getInt(Constants.FOREST_FOURTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTEEN_STARS, stars);
			}
			break;
		case 15:
			status = prefs.getInt(Constants.FOREST_FIFTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTEEN_STARS, stars);
			}
			break;
		case 16:
			status = prefs.getInt(Constants.FOREST_SIXTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTEEN_STARS, stars);
			}
			break;
		case 17:
			status = prefs.getInt(Constants.FOREST_SEVENTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTEEN_STARS, stars);
			}
			break;
		case 18:
			status = prefs.getInt(Constants.FOREST_EIGHTEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTEEN_STARS, stars);
			}
			break;
		case 19:
			status = prefs.getInt(Constants.FOREST_NINETEEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETEEN_STARS, stars);
			}
			break;
		case 20:
			status = prefs.getInt(Constants.FOREST_TWENTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTY_STARS, stars);
			}
			break;
		case 21:
			status = prefs.getInt(Constants.FOREST_TWENTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYONE_STARS, stars);
			}
			break;
		case 22:
			status = prefs.getInt(Constants.FOREST_TWENTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYTWO_STARS, stars);
			}
			break;
		case 23:
			status = prefs.getInt(Constants.FOREST_TWENTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYTHREE_STARS, stars);
			}
			break;
		case 24:
			status = prefs.getInt(Constants.FOREST_TWENTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYFOUR_STARS, stars);
			}
			break;
		case 25:
			status = prefs.getInt(Constants.FOREST_TWENTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYFIVE_STARS, stars);				
			}
			break;
		case 26:
			status = prefs.getInt(Constants.FOREST_TWENTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYSIX_STARS, stars);				
			}
			break;
		case 27:
			status = prefs.getInt(Constants.FOREST_TWENTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYSEVEN_STARS, stars);
			}
			break;
		case 28:
			status = prefs.getInt(Constants.FOREST_TWENTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYEIGHT_STARS, stars);
			}
			break;
		case 29:
			status = prefs.getInt(Constants.FOREST_TWENTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_TWENTYNINE_STARS, stars);
			}
			break;
		case 30:
			status = prefs.getInt(Constants.FOREST_THIRTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTY_STARS, stars);
			}
			break;
		case 31:
			status = prefs.getInt(Constants.FOREST_THIRTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYONE_STARS, stars);
			}
			break;
		case 32:
			status = prefs.getInt(Constants.FOREST_THIRTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYTWO_STARS, stars);
			}
			break;
		case 33:
			status = prefs.getInt(Constants.FOREST_THIRTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYTHREE_STARS, stars);
			}
			break;
		case 34:
			status = prefs.getInt(Constants.FOREST_THIRTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYFOUR_STARS, stars);
			}
			break;
		case 35:
			status = prefs.getInt(Constants.FOREST_THIRTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYFIVE_STARS, stars);
			}
			break;
		case 36:
			status = prefs.getInt(Constants.FOREST_THIRTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYSIX_STARS, stars);
			}
			break;
		case 37:
			status = prefs.getInt(Constants.FOREST_THIRTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYSEVEN_STARS, stars);
			}
			break;
		case 38:
			status = prefs.getInt(Constants.FOREST_THIRTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYEIGHT_STARS, stars);
			}
			break;
		case 39:
			status = prefs.getInt(Constants.FOREST_THIRTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_THIRTYNINE_STARS, stars);
			}
			break;
		case 40:
			status = prefs.getInt(Constants.FOREST_FOURTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTY_STARS, stars);
			}
			break;
		case 41:
			status = prefs.getInt(Constants.FOREST_FOURTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYONE_STARS, stars);
			}
			break;
		case 42:
			status = prefs.getInt(Constants.FOREST_FOURTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYTWO_STARS, stars);
			}
			break;
		case 43:
			status = prefs.getInt(Constants.FOREST_FOURTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYTHREE_STARS, stars);
			}
			break;
		case 44:
			status = prefs.getInt(Constants.FOREST_FOURTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYFOUR_STARS, stars);
			}
			break;
		case 45:
			status = prefs.getInt(Constants.FOREST_FOURTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYFIVE_STARS, stars);
			}
			break;
		case 46:
			status = prefs.getInt(Constants.FOREST_FOURTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYSIX_STARS, stars);
			}
			break;
		case 47:
			status = prefs.getInt(Constants.FOREST_FOURTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYSEVEN_STARS, stars);
			}
			break;
		case 48:
			status = prefs.getInt(Constants.FOREST_FOURTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYEIGHT_STARS, stars);
			}
			break;
		case 49:
			status = prefs.getInt(Constants.FOREST_FOURTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FOURTYNINE_STARS, stars);				
			}
			break;
		case 50:
			status = prefs.getInt(Constants.FOREST_FIFTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTY_STARS, stars);				
			}
			break;
		case 51:
			status = prefs.getInt(Constants.FOREST_FIFTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYONE_STARS, stars);
			}
			break;
		case 52:
			status = prefs.getInt(Constants.FOREST_FIFTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYTWO_STARS, stars);
			}
			break;
		case 53:
			status = prefs.getInt(Constants.FOREST_FIFTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYTHREE_STARS, stars);
			}
			break;
		case 54:
			status = prefs.getInt(Constants.FOREST_FIFTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYFOUR_STARS, stars);
			}
			break;
		case 55:
			status = prefs.getInt(Constants.FOREST_FIFTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYFIVE_STARS, stars);
			}
			break;
		case 56:
			status = prefs.getInt(Constants.FOREST_FIFTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYSIX_STARS, stars);
			}
			break;
		case 57:
			status = prefs.getInt(Constants.FOREST_FIFTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYSEVEN_STARS, stars);
			}
			break;
		case 58:
			status = prefs.getInt(Constants.FOREST_FIFTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYEIGHT_STARS, stars);
			}
			break;
		case 59:
			status = prefs.getInt(Constants.FOREST_FIFTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_FIFTYNINE_STARS, stars);
			}
			break;
		case 60:
			status = prefs.getInt(Constants.FOREST_SIXTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTY_STARS, stars);
			}
			break;
		case 61:
			status = prefs.getInt(Constants.FOREST_SIXTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYONE_STARS, stars);
			}
			break;
		case 62:
			status = prefs.getInt(Constants.FOREST_SIXTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYTWO_STARS, stars);
			}
			break;
		case 63:
			status = prefs.getInt(Constants.FOREST_SIXTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYTHREE_STARS, stars);
			}
			break;
		case 64:
			status = prefs.getInt(Constants.FOREST_SIXTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYFOUR_STARS, stars);
			}
			break;
		case 65:
			status = prefs.getInt(Constants.FOREST_SIXTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYFIVE_STARS, stars);
			}
			break;
		case 66:
			status = prefs.getInt(Constants.FOREST_SIXTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYSIX_STARS, stars);
			}
			break;
		case 67:
			status = prefs.getInt(Constants.FOREST_SIXTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYSEVEN_STARS, stars);
			}
			break;
		case 68:
			status = prefs.getInt(Constants.FOREST_SIXTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYEIGHT_STARS, stars);
			}
			break;
		case 69:
			status = prefs.getInt(Constants.FOREST_SIXTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SIXTYNINE_STARS, stars);
			}
			break;
		case 70:
			status = prefs.getInt(Constants.FOREST_SEVENTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTY_STARS, stars);
			}
			break;
		case 71:
			status = prefs.getInt(Constants.FOREST_SEVENTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYONE_STARS, stars);
			}
			break;
		case 72:
			status = prefs.getInt(Constants.FOREST_SEVENTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYTWO_STARS, stars);
			}
			break;
		case 73:
			status = prefs.getInt(Constants.FOREST_SEVENTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYTHREE_STARS, stars);				
			}
			break;
		case 74:
			status = prefs.getInt(Constants.FOREST_SEVENTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYFOUR_STARS, stars);				
			}
			break;
		case 75:
			status = prefs.getInt(Constants.FOREST_SEVENTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYFIVE_STARS, stars);
			}
			break;
		case 76:
			status = prefs.getInt(Constants.FOREST_SEVENTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYSIX_STARS, stars);
			}
			break;
		case 77:
			status = prefs.getInt(Constants.FOREST_SEVENTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYSEVEN_STARS, stars);
			}
			break;
		case 78:
			status = prefs.getInt(Constants.FOREST_SEVENTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYEIGHT_STARS, stars);
			}
			break;
		case 79:
			status = prefs.getInt(Constants.FOREST_SEVENTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_SEVENTYNINE_STARS, stars);
			}
			break;
		case 80:
			status = prefs.getInt(Constants.FOREST_EIGHTY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTY_STARS, stars);
			}
			break;
		case 81:
			status = prefs.getInt(Constants.FOREST_EIGHTYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYONE_STARS, stars);
			}
			break;
		case 82:
			status = prefs.getInt(Constants.FOREST_EIGHTYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYTWO_STARS, stars);
			}
			break;
		case 83:
			status = prefs.getInt(Constants.FOREST_EIGHTYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYTHREE_STARS, stars);
			}
			break;
		case 84:
			status = prefs.getInt(Constants.FOREST_EIGHTYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYFOUR_STARS, stars);
			}
			break;
		case 85:
			status = prefs.getInt(Constants.FOREST_EIGHTYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYFIVE_STARS, stars);
			}
			break;
		case 86:
			status = prefs.getInt(Constants.FOREST_EIGHTYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYSIX_STARS, stars);
			}
			break;
		case 87:
			status = prefs.getInt(Constants.FOREST_EIGHTYSEVEN_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYSEVEN_STARS, stars);
			}
			break;
		case 88:
			status = prefs.getInt(Constants.FOREST_EIGHTYEIGHT_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYEIGHT_STARS, stars);
			}
			break;
		case 89:
			status = prefs.getInt(Constants.FOREST_EIGHTYNINE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_EIGHTYNINE_STARS, stars);
			}
			break;
		case 90:
			status = prefs.getInt(Constants.FOREST_NINETY_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETY_STARS, stars);
			}
			break;
		case 91:
			status = prefs.getInt(Constants.FOREST_NINETYONE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYONE_STARS, stars);
			}
			break;
		case 92:
			status = prefs.getInt(Constants.FOREST_NINETYTWO_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYTWO_STARS, stars);
			}
			break;
		case 93:
			status = prefs.getInt(Constants.FOREST_NINETYTHREE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYTHREE_STARS, stars);
			}
			break;
		case 94:
			status = prefs.getInt(Constants.FOREST_NINETYFOUR_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYFOUR_STARS, stars);
			}
			break;
		case 95:
			status = prefs.getInt(Constants.FOREST_NINETYFIVE_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYFIVE_STARS, stars);
			}
			break;
		case 96:
			status = prefs.getInt(Constants.FOREST_NINETYSIX_STARS, 0);
			if (status < 3) {
				editor.putInt(Constants.FOREST_NINETYSIX_STARS, stars);
			}
			break;		
		}
		editor.commit();
	}
	
	public void unlockNextLevel(int level) {
		int nextLevel = level + 1;
		if (nextLevel < 96) {
			editor = prefs.edit();
			switch (nextLevel) {
			case 2:
				status = prefs.getInt(Constants.FOREST_TWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWO_STARS, 0);				
				}
				break;
			case 3:
				status = prefs.getInt(Constants.FOREST_THREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THREE_STARS, 0);
				}
				break;
			case 4:
				status = prefs.getInt(Constants.FOREST_FOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOUR_STARS, 0);
				}
				break;
			case 5:
				status = prefs.getInt(Constants.FOREST_FIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIVE_STARS, 0);
				}
				break;
			case 6:
				status = prefs.getInt(Constants.FOREST_SIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIX_STARS, 0);
				}
				break;
			case 7:
				status = prefs.getInt(Constants.FOREST_SEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVEN_STARS, 0);
				}
				break;
			case 8:
				status = prefs.getInt(Constants.FOREST_EIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHT_STARS, 0);
				}
				break;
			case 9:
				status = prefs.getInt(Constants.FOREST_NINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINE_STARS, 0);
				}
				break;
			case 10:
				status = prefs.getInt(Constants.FOREST_TEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TEN_STARS, 0);
				}
				break;
			case 11:
				status = prefs.getInt(Constants.FOREST_ELEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_ELEVEN_STARS, 0);
				}
				break;
			case 12:
				status = prefs.getInt(Constants.FOREST_TWELVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWELVE_STARS, 0);
				}
				break;
			case 13:
				status = prefs.getInt(Constants.FOREST_THIRTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTEEN_STARS, 0);
				}
				break;
			case 14:
				status = prefs.getInt(Constants.FOREST_FOURTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTEEN_STARS, 0);
				}
				break;
			case 15:
				status = prefs.getInt(Constants.FOREST_FIFTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTEEN_STARS, 0);
				}
				break;
			case 16:
				status = prefs.getInt(Constants.FOREST_SIXTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTEEN_STARS, 0);
				}
				break;
			case 17:
				status = prefs.getInt(Constants.FOREST_SEVENTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTEEN_STARS, 0);
				}
				break;
			case 18:
				status = prefs.getInt(Constants.FOREST_EIGHTEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTEEN_STARS, 0);
				}
				break;
			case 19:
				status = prefs.getInt(Constants.FOREST_NINETEEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETEEN_STARS, 0);
				}
				break;
			case 20:
				status = prefs.getInt(Constants.FOREST_TWENTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTY_STARS, 0);
				}
				break;
			case 21:
				status = prefs.getInt(Constants.FOREST_TWENTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYONE_STARS, 0);
				}
				break;
			case 22:
				status = prefs.getInt(Constants.FOREST_TWENTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYTWO_STARS, 0);
				}
				break;
			case 23:
				status = prefs.getInt(Constants.FOREST_TWENTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYTHREE_STARS, 0);
				}
				break;
			case 24:
				status = prefs.getInt(Constants.FOREST_TWENTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYFOUR_STARS, 0);
				}
				break;
			case 25:
				status = prefs.getInt(Constants.FOREST_TWENTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYFIVE_STARS, 0);				
				}
				break;
			case 26:
				status = prefs.getInt(Constants.FOREST_TWENTYSIX_STARS, -1);
				if (status == 1) {
					editor.putInt(Constants.FOREST_TWENTYSIX_STARS, 0);				
				}
				break;
			case 27:
				status = prefs.getInt(Constants.FOREST_TWENTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYSEVEN_STARS, 0);
				}
				break;
			case 28:
				status = prefs.getInt(Constants.FOREST_TWENTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYEIGHT_STARS, 0);
				}
				break;
			case 29:
				status = prefs.getInt(Constants.FOREST_TWENTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_TWENTYNINE_STARS, 0);
				}
				break;
			case 30:
				status = prefs.getInt(Constants.FOREST_THIRTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTY_STARS, 0);
				}
				break;
			case 31:
				status = prefs.getInt(Constants.FOREST_THIRTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYONE_STARS, 0);
				}
				break;
			case 32:
				status = prefs.getInt(Constants.FOREST_THIRTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYTWO_STARS, 0);
				}
				break;
			case 33:
				status = prefs.getInt(Constants.FOREST_THIRTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYTHREE_STARS, 0);
				}
				break;
			case 34:
				status = prefs.getInt(Constants.FOREST_THIRTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYFOUR_STARS, 0);
				}
				break;
			case 35:
				status = prefs.getInt(Constants.FOREST_THIRTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYFIVE_STARS, 0);
				}
				break;
			case 36:
				status = prefs.getInt(Constants.FOREST_THIRTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYSIX_STARS, 0);
				}
				break;
			case 37:
				status = prefs.getInt(Constants.FOREST_THIRTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYSEVEN_STARS, 0);
				}
				break;
			case 38:
				status = prefs.getInt(Constants.FOREST_THIRTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYEIGHT_STARS, 0);
				}
				break;
			case 39:
				status = prefs.getInt(Constants.FOREST_THIRTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_THIRTYNINE_STARS, 0);
				}
				break;
			case 40:
				status = prefs.getInt(Constants.FOREST_FOURTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTY_STARS, 0);
				}
				break;
			case 41:
				status = prefs.getInt(Constants.FOREST_FOURTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYONE_STARS, 0);
				}
				break;
			case 42:
				status = prefs.getInt(Constants.FOREST_FOURTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYTWO_STARS, 0);
				}
				break;
			case 43:
				status = prefs.getInt(Constants.FOREST_FOURTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYTHREE_STARS, 0);
				}
				break;
			case 44:
				status = prefs.getInt(Constants.FOREST_FOURTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYFOUR_STARS, 0);
				}
				break;
			case 45:
				status = prefs.getInt(Constants.FOREST_FOURTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYFIVE_STARS, 0);
				}
				break;
			case 46:
				status = prefs.getInt(Constants.FOREST_FOURTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYSIX_STARS, 0);
				}
				break;
			case 47:
				status = prefs.getInt(Constants.FOREST_FOURTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYSEVEN_STARS, 0);
				}
				break;
			case 48:
				status = prefs.getInt(Constants.FOREST_FOURTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYEIGHT_STARS, 0);
				}
				break;
			case 49:
				status = prefs.getInt(Constants.FOREST_FOURTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FOURTYNINE_STARS, 0);				
				}
				break;
			case 50:
				status = prefs.getInt(Constants.FOREST_FIFTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTY_STARS, 0);				
				}
				break;
			case 51:
				status = prefs.getInt(Constants.FOREST_FIFTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYONE_STARS, 0);
				}
				break;
			case 52:
				status = prefs.getInt(Constants.FOREST_FIFTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYTWO_STARS, 0);
				}
				break;
			case 53:
				status = prefs.getInt(Constants.FOREST_FIFTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYTHREE_STARS, 0);
				}
				break;
			case 54:
				status = prefs.getInt(Constants.FOREST_FIFTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYFOUR_STARS, 0);
				}
				break;
			case 55:
				status = prefs.getInt(Constants.FOREST_FIFTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYFIVE_STARS, 0);
				}
				break;
			case 56:
				status = prefs.getInt(Constants.FOREST_FIFTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYSIX_STARS, 0);
				}
				break;
			case 57:
				status = prefs.getInt(Constants.FOREST_FIFTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYSEVEN_STARS, 0);
				}
				break;
			case 58:
				status = prefs.getInt(Constants.FOREST_FIFTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYEIGHT_STARS, 0);
				}
				break;
			case 59:
				status = prefs.getInt(Constants.FOREST_FIFTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_FIFTYNINE_STARS, 0);
				}
				break;
			case 60:
				status = prefs.getInt(Constants.FOREST_SIXTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTY_STARS, 0);
				}
				break;
			case 61:
				status = prefs.getInt(Constants.FOREST_SIXTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYONE_STARS, 0);
				}
				break;
			case 62:
				status = prefs.getInt(Constants.FOREST_SIXTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYTWO_STARS, 0);
				}
				break;
			case 63:
				status = prefs.getInt(Constants.FOREST_SIXTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYTHREE_STARS, 0);
				}
				break;
			case 64:
				status = prefs.getInt(Constants.FOREST_SIXTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYFOUR_STARS, 0);
				}
				break;
			case 65:
				status = prefs.getInt(Constants.FOREST_SIXTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYFIVE_STARS, 0);
				}
				break;
			case 66:
				status = prefs.getInt(Constants.FOREST_SIXTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYSIX_STARS, 0);
				}
				break;
			case 67:
				status = prefs.getInt(Constants.FOREST_SIXTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYSEVEN_STARS, 0);
				}
				break;
			case 68:
				status = prefs.getInt(Constants.FOREST_SIXTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYEIGHT_STARS, 0);
				}
				break;
			case 69:
				status = prefs.getInt(Constants.FOREST_SIXTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SIXTYNINE_STARS, 0);
				}
				break;
			case 70:
				status = prefs.getInt(Constants.FOREST_SEVENTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTY_STARS, 0);
				}
				break;
			case 71:
				status = prefs.getInt(Constants.FOREST_SEVENTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYONE_STARS, 0);
				}
				break;
			case 72:
				status = prefs.getInt(Constants.FOREST_SEVENTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYTWO_STARS, 0);
				}
				break;
			case 73:
				status = prefs.getInt(Constants.FOREST_SEVENTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYTHREE_STARS, 0);				
				}
				break;
			case 74:
				status = prefs.getInt(Constants.FOREST_SEVENTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYFOUR_STARS, 0);				
				}
				break;
			case 75:
				status = prefs.getInt(Constants.FOREST_SEVENTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYFIVE_STARS, 0);
				}
				break;
			case 76:
				status = prefs.getInt(Constants.FOREST_SEVENTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYSIX_STARS, 0);
				}
				break;
			case 77:
				status = prefs.getInt(Constants.FOREST_SEVENTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYSEVEN_STARS, 0);
				}
				break;
			case 78:
				status = prefs.getInt(Constants.FOREST_SEVENTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYEIGHT_STARS, 0);
				}
				break;
			case 79:
				status = prefs.getInt(Constants.FOREST_SEVENTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_SEVENTYNINE_STARS, 0);
				}
				break;
			case 80:
				status = prefs.getInt(Constants.FOREST_EIGHTY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTY_STARS, 0);
				}
				break;
			case 81:
				status = prefs.getInt(Constants.FOREST_EIGHTYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYONE_STARS, 0);
				}
				break;
			case 82:
				status = prefs.getInt(Constants.FOREST_EIGHTYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYTWO_STARS, 0);
				}
				break;
			case 83:
				status = prefs.getInt(Constants.FOREST_EIGHTYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYTHREE_STARS, 0);
				}
				break;
			case 84:
				status = prefs.getInt(Constants.FOREST_EIGHTYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYFOUR_STARS, 0);
				}
				break;
			case 85:
				status = prefs.getInt(Constants.FOREST_EIGHTYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYFIVE_STARS, 0);
				}
				break;
			case 86:
				status = prefs.getInt(Constants.FOREST_EIGHTYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYSIX_STARS, 0);
				}
				break;
			case 87:
				status = prefs.getInt(Constants.FOREST_EIGHTYSEVEN_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYSEVEN_STARS, 0);
				}
				break;
			case 88:
				status = prefs.getInt(Constants.FOREST_EIGHTYEIGHT_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYEIGHT_STARS, 0);
				}
				break;
			case 89:
				status = prefs.getInt(Constants.FOREST_EIGHTYNINE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_EIGHTYNINE_STARS, 0);
				}
				break;
			case 90:
				status = prefs.getInt(Constants.FOREST_NINETY_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETY_STARS, 0);
				}
				break;
			case 91:
				status = prefs.getInt(Constants.FOREST_NINETYONE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYONE_STARS, 0);
				}
				break;
			case 92:
				status = prefs.getInt(Constants.FOREST_NINETYTWO_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYTWO_STARS, 0);
				}
				break;
			case 93:
				status = prefs.getInt(Constants.FOREST_NINETYTHREE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYTHREE_STARS, 0);
				}
				break;
			case 94:
				status = prefs.getInt(Constants.FOREST_NINETYFOUR_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYFOUR_STARS, 0);
				}
				break;
			case 95:
				status = prefs.getInt(Constants.FOREST_NINETYFIVE_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYFIVE_STARS, 0);
				}
				break;
			case 96:
				status = prefs.getInt(Constants.FOREST_NINETYSIX_STARS, -1);
				if (status == -1) {
					editor.putInt(Constants.FOREST_NINETYSIX_STARS, 0);
				}
				break;		
			}
			editor.commit();
		}
	}

}