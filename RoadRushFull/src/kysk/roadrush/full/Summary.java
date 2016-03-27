package kysk.roadrush.full;

import kysk.roadrush.full.tracks.TrackFour;
import kysk.roadrush.full.tracks.TrackThree;
import kysk.roadrush.full.tracks.TrackTwo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends Activity implements OnClickListener {
	
	private Typeface font;
	
	private TextView title;
	private TextView duration;
	private TextView saved;
	private TextView savedCount;
	private TextView elapsedTime;
	private TextView speed;
	private TextView avgSpeed;
	
	private Button retry;
	private Button ok;
	
	private int hours, minutes, seconds;
	private long time;
	
	private String timeStr = "";
	
	private int track;
	
	private SoundPool soundPool;
	private int menuClick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);

		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);

		Bundle b = getIntent().getExtras();
		
		track = b.getInt(Constants.EXTRA_TRACK);
		
		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		
		title = (TextView) this.findViewById(R.id.title);
		duration = (TextView) this.findViewById(R.id.duration);
		saved = (TextView) this.findViewById(R.id.saved);
		savedCount = (TextView) this.findViewById(R.id.saved_count);
		elapsedTime = (TextView) this.findViewById(R.id.elapsed_time);
		speed = (TextView) this.findViewById(R.id.speed);
		avgSpeed = (TextView) this.findViewById(R.id.avg_speed);
		
		retry = (Button) this.findViewById(R.id.bt_retry);
		ok = (Button) this.findViewById(R.id.bt_ok);
		
		title.setTypeface(font);
		duration.setTypeface(font);
		saved.setTypeface(font);
		savedCount.setTypeface(font);
		elapsedTime.setTypeface(font);
		speed.setTypeface(font);
		avgSpeed.setTypeface(font);
		
		savedCount.setText("" + b.getInt(Constants.EXTRA_SAVED));
		avgSpeed.setText("" + b.getInt(Constants.EXTRA_SWIPES));
		
		time = b.getLong(Constants.EXTRA_DURATION);

		seconds = (int) (time % 60);
		
		if (time > 60) {
			minutes = (int) (time / 60);
		}
		
		if (minutes > 60) {
			hours = minutes / 60;
		}
		
		if (hours > 0) {
			timeStr += hours + " Hrs ";
		}
		
		if (minutes > 0) {
			timeStr += minutes + " Min ";
		}
		
		if (seconds >= 0) {
			timeStr += seconds + " Sec ";
		}
		elapsedTime.setText("" + timeStr);
		
		
		retry.setOnClickListener(this);
		ok.setOnClickListener(this);
		
		retry.setSoundEffectsEnabled(false);
		ok.setSoundEffectsEnabled(false);
		
	}

	public void onClick(View v) {
		soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		switch (v.getId()) {
		case R.id.bt_retry:
			Intent retryIntent = null;
			switch (track) {
			case 1:
				retryIntent = new Intent(Summary.this, RoadRush.class);
				break;
			case 2:
				retryIntent = new Intent(Summary.this, TrackTwo.class);
				break;
			case 3:
				retryIntent = new Intent(Summary.this, TrackThree.class);
				break;
			case 4:
				retryIntent = new Intent(Summary.this, TrackFour.class);
				break;
			}
			startActivity(retryIntent);
			finish();
			break;
		case R.id.bt_ok:
			finish();
			break;
		}
	}
}
