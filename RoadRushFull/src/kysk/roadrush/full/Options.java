package kysk.roadrush.full;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Options extends Activity implements OnClickListener {

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private Typeface font;
	
	private TextView options;
	private TextView music;
	private TextView sound;
	private TextView reset;
	
	private Button funStats;
	private Button help;
	private Button rateUs;
	private Button musicOn, musicOff;
	private Button soundOn, soundOff;
	private Button resetBest;
	
	private SoundPool soundPool;
	private int menuClick;

	private boolean isMusicOn;
	private boolean isSoundOn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);

		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		
		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		
		options = (TextView) this.findViewById(R.id.options);
		music = (TextView) this.findViewById(R.id.music);
		sound = (TextView) this.findViewById(R.id.sound);
		reset = (TextView) this.findViewById(R.id.reset_best);
		
		options.setTypeface(font);
		music.setTypeface(font);
		sound.setTypeface(font);
		reset.setTypeface(font);
		
		funStats = (Button) this.findViewById(R.id.bt_stats);
		help = (Button) this.findViewById(R.id.bt_help);
		rateUs = (Button) this.findViewById(R.id.rate_us);
		musicOn = (Button) this.findViewById(R.id.bt_music_on);
		musicOff = (Button) this.findViewById(R.id.bt_music_off);
		
		soundOn = (Button) this.findViewById(R.id.bt_sound_on);
		soundOff = (Button) this.findViewById(R.id.bt_sound_off);
		
		resetBest = (Button) this.findViewById(R.id.bt_reset);
		
		funStats.setOnClickListener(this);
		help.setOnClickListener(this);
		rateUs.setOnClickListener(this);
		musicOn.setOnClickListener(this);
		musicOff.setOnClickListener(this);
		
		soundOn.setOnClickListener(this);
		soundOff.setOnClickListener(this);
		
		resetBest.setOnClickListener(this);
		
		funStats.setSoundEffectsEnabled(false);
		help.setSoundEffectsEnabled(false);
		rateUs.setSoundEffectsEnabled(false);
		musicOn.setSoundEffectsEnabled(false);
		musicOff.setSoundEffectsEnabled(false);
		soundOn.setSoundEffectsEnabled(false);
		soundOff.setSoundEffectsEnabled(false);
		resetBest.setSoundEffectsEnabled(false);
		
		isMusicOn = prefs.getBoolean(Constants.PREFS_MUSIC, true);
		isSoundOn = prefs.getBoolean(Constants.PREFS_SOUND, true);
		
		if (isMusicOn) {
			musicOn.setBackgroundResource(R.drawable.on_clicked);
			musicOff.setBackgroundResource(R.drawable.off);
		} else {
			musicOn.setBackgroundResource(R.drawable.on);
			musicOff.setBackgroundResource(R.drawable.off_clicked);
		}
		
		if (isSoundOn) {
			soundOn.setBackgroundResource(R.drawable.on_clicked);
			soundOff.setBackgroundResource(R.drawable.off);
		} else {
			soundOn.setBackgroundResource(R.drawable.on);
			soundOff.setBackgroundResource(R.drawable.off_clicked);
		}
	}

	public void onClick(View v) {
		soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		switch (v.getId()) {
		case R.id.bt_stats:
			Intent stats = new Intent(Options.this, FunStats.class);
			stats.putExtra(Constants.EXTRA_SAVED, prefs.getInt(Constants.PREFS_SAVED, 0));
			stats.putExtra(Constants.EXTRA_SWIPES, prefs.getInt(Constants.PREFS_SWIPES, 0));
			stats.putExtra(Constants.EXTRA_DURATION, prefs.getLong(Constants.PREFS_TIME, 0));
			startActivity(stats);
			break;
		case R.id.bt_help:
			Intent helpIntent = new Intent(Options.this, Help.class);
			startActivity(helpIntent);
			break;
		case R.id.rate_us:
			Intent rateUsIntent = new Intent(Intent.ACTION_VIEW);
			rateUsIntent.setData(Uri.parse("market://details?id=kysk.roadrush.full"));
			startActivity(rateUsIntent);
			break;
		case R.id.bt_music_on:
			editor = prefs.edit();
			if (!isMusicOn) {
				musicOn.setBackgroundResource(R.drawable.on_clicked);
				musicOff.setBackgroundResource(R.drawable.off);
				editor.putBoolean(Constants.PREFS_MUSIC, true);
			}
			isMusicOn = true;
			editor.commit();
			break;
		case R.id.bt_music_off:
			editor = prefs.edit();
			if (isMusicOn) {
				musicOn.setBackgroundResource(R.drawable.on);
				musicOff.setBackgroundResource(R.drawable.off_clicked);
				editor.putBoolean(Constants.PREFS_MUSIC, false);
			}
			isMusicOn = false;
			editor.commit();
			break;
		case R.id.bt_sound_on:
			editor = prefs.edit();
			if (!isSoundOn) {
				soundOn.setBackgroundResource(R.drawable.on_clicked);
				soundOff.setBackgroundResource(R.drawable.off);
				editor.putBoolean(Constants.PREFS_SOUND, true);
			}
			isSoundOn = true;
			editor.commit();
			break;
		case R.id.bt_sound_off:
			editor = prefs.edit();
			if (isSoundOn) {
				soundOn.setBackgroundResource(R.drawable.on);
				soundOff.setBackgroundResource(R.drawable.off_clicked);
				editor.putBoolean(Constants.PREFS_SOUND, false);
			}
			isSoundOn = false;
			editor.commit();
			break;
		case R.id.bt_reset:
			new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Are you sure?")
	        .setMessage("Do you want to reset?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	    			editor = prefs.edit();
	    			editor.putInt(Constants.PREFS_BEST, 0);
	    			editor.putInt(Constants.PREFS_TRACKTWO_BEST, 0);
	    			editor.putInt(Constants.PREFS_TRACKTHREE_BEST, 0);
	    			editor.putInt(Constants.PREFS_TRACKFOUR_BEST, 0);
	    			editor.commit();	            	
	            }
	        })
	        .setNegativeButton("No", null)
	        .show();
			break;
		}
	}

	@Override
	public void onBackPressed() {
    	soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		super.onBackPressed();
	}
	
	
}
