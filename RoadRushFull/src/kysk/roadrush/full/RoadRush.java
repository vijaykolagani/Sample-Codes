package kysk.roadrush.full;

import kysk.roadrush.full.sounds.SoundManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class RoadRush extends Activity implements OnGameOverListener, OnClickListener {
	
	private GameView gameView;
	private SoundManager soundManager;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private int best;
	private int totalSaved;
	private int totalSwipes;
	private long duration;
	private boolean isMusicOn, isSoundOn;
	
	private FrameLayout frameLayout;
	private Button pause;
	private boolean isPaused;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.gameview);

		isPaused = false;
		
		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		best = prefs.getInt(Constants.PREFS_BEST, 0);
		totalSaved = prefs.getInt(Constants.PREFS_SAVED, 0);
		totalSwipes = prefs.getInt(Constants.PREFS_SWIPES, 0);
		duration = prefs.getLong(Constants.PREFS_TIME, 0);
		isMusicOn = prefs.getBoolean(Constants.PREFS_MUSIC, true);
		isSoundOn = prefs.getBoolean(Constants.PREFS_SOUND, true);
		
		soundManager = new SoundManager(this);
		soundManager.setSoundOn(isSoundOn);
		soundManager.setMusicOn(isMusicOn);
		
		gameView = new GameView(this, soundManager, Constants.TRACK_ONE);
		gameView.setBest(best);
		gameView.setGameOverListener(this);

		frameLayout = (FrameLayout) this.findViewById(R.id.game_view);
		frameLayout.addView(gameView, 0);
		
		pause = (Button) this.findViewById(R.id.bt_pause);
		pause.setOnClickListener(this);
	}
	public void onGameOver(int score, int swipes, long elapsedTime) {
		int prevBest = prefs.getInt(Constants.PREFS_BEST, 0);
		duration += elapsedTime;
		totalSaved += score;
		totalSwipes += swipes;
		editor = prefs.edit();
		if (score > prevBest) {
			editor.putInt(Constants.PREFS_BEST, score);
		}
		editor.putLong(Constants.PREFS_TIME, duration);
		editor.putInt(Constants.PREFS_SAVED, totalSaved);
		editor.putInt(Constants.PREFS_SWIPES, totalSwipes);
		editor.commit();
		Intent summary = new Intent(RoadRush.this, Summary.class);
		summary.putExtra(Constants.EXTRA_TRACK, Constants.TRACK_ONE);
		summary.putExtra(Constants.EXTRA_SAVED, score);
		summary.putExtra(Constants.EXTRA_SWIPES, swipes);
		summary.putExtra(Constants.EXTRA_DURATION, elapsedTime);
		startActivity(summary);
		this.finish();
	}
	
	public void onClick(View v) {
		if (v.getId() == R.id.bt_pause) {
			if (isPaused) {
				isPaused = false;
				pause.setBackgroundResource(R.drawable.pause);
				gameView.resume();
			} else {
				isPaused = true;
				pause.setBackgroundResource(R.drawable.resume);
				gameView.pause();
			}
		}
	}
}
