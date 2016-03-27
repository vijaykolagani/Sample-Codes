package kysk.roadrush.full;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FunStats extends Activity implements OnClickListener {
	
	private Typeface font;
	
	private TextView title;
	private TextView duration;
	private TextView saved;
	private TextView savedCount;
	private TextView elapsedTime;
	private TextView swipes;
	private TextView swipeCount;
	
	private Button ok;
	
	private int hours, minutes, seconds;
	private long time;
	
	private String timeStr = "";
	
	private SoundPool soundPool;
	private int menuClick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);

		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);

		Bundle b = getIntent().getExtras();
		
		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		
		title = (TextView) this.findViewById(R.id.title);
		duration = (TextView) this.findViewById(R.id.duration);
		saved = (TextView) this.findViewById(R.id.saved);
		savedCount = (TextView) this.findViewById(R.id.saved_count);
		elapsedTime = (TextView) this.findViewById(R.id.elapsed_time);
		swipes = (TextView) this.findViewById(R.id.swipes);
		swipeCount = (TextView) this.findViewById(R.id.swipe_count);
		
		ok = (Button) this.findViewById(R.id.bt_ok);
		
		title.setTypeface(font);
		duration.setTypeface(font);
		saved.setTypeface(font);
		savedCount.setTypeface(font);
		elapsedTime.setTypeface(font);
		swipes.setTypeface(font);
		swipeCount.setTypeface(font);
		
		savedCount.setText("" + b.getInt(Constants.EXTRA_SAVED));
		swipeCount.setText("" + b.getInt(Constants.EXTRA_SWIPES));
		
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
		
		ok.setOnClickListener(this);
		ok.setSoundEffectsEnabled(false);
	}

	public void onClick(View v) {
		soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		switch (v.getId()) {
		case R.id.bt_ok:
			finish();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
    	soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		super.onBackPressed();
	}
	
}
