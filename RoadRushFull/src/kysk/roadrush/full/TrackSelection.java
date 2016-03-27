package kysk.roadrush.full;

import kysk.roadrush.full.tracks.TrackFour;
import kysk.roadrush.full.tracks.TrackThree;
import kysk.roadrush.full.tracks.TrackTwo;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TrackSelection extends Activity implements OnClickListener {
	
	private ImageView trackOne;
	private ImageView trackTwo;
	private ImageView trackThree;
	private ImageView trackFour;
	
	private SoundPool soundPool;
	private int menuClick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.track_selection);
		
		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);
	
		trackOne = (ImageView) this.findViewById(R.id.track_one);
		trackTwo = (ImageView) this.findViewById(R.id.track_two);
		trackThree = (ImageView) this.findViewById(R.id.track_three);
		trackFour = (ImageView) this.findViewById(R.id.track_four);
		
		trackOne.setOnClickListener(this);
		trackTwo.setOnClickListener(this);
		trackThree.setOnClickListener(this);
		trackFour.setOnClickListener(this);
		
		trackOne.setSoundEffectsEnabled(false);
		trackTwo.setSoundEffectsEnabled(false);
		trackThree.setSoundEffectsEnabled(false);
	}

	public void onClick(View v) {
		soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		switch (v.getId()) {
		case R.id.track_one:
			Intent trackOne = new Intent(TrackSelection.this, RoadRush.class);
			startActivity(trackOne);
			break;
		case R.id.track_two:
			Intent trackTwo = new Intent(TrackSelection.this, TrackTwo.class);
			startActivity(trackTwo);
			break;
		case R.id.track_three:
			Intent trackThree = new Intent(TrackSelection.this, TrackThree.class);
			startActivity(trackThree);
			break;
		case R.id.track_four:
			Intent trackFour = new Intent(TrackSelection.this, TrackFour.class);
			startActivity(trackFour);
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
    	soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		super.onBackPressed();
	}
	
}
