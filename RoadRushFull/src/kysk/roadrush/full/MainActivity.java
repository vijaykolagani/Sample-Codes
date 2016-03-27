package kysk.roadrush.full;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private Button start;
	private Button options;
	private Button exit;
	
	private SoundPool soundPool;
	private int menuClick;
	private int intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);
		intro = soundPool.load(this, R.raw.drums, 1);
		setContentView(R.layout.activity_main);
		

		start = (Button) this.findViewById(R.id.bt_start);
		options = (Button) this.findViewById(R.id.bt_options);
		exit = (Button) this.findViewById(R.id.bt_exit);
		
		start.setOnClickListener(this);
		options.setOnClickListener(this);
		exit.setOnClickListener(this);
		
		start.setSoundEffectsEnabled(false);
		options.setSoundEffectsEnabled(false);
		exit.setSoundEffectsEnabled(false);
		
		soundPool.play(intro, 1.0f, 1.0f, 0, 0, 1.0f);
	}

	public void onClick(View v) {
		soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		switch (v.getId()) {
		case R.id.bt_start:
			Intent intent = new Intent(MainActivity.this, TrackSelection.class);
			startActivity(intent);
			break;
		case R.id.bt_options:
			Intent optionsIntent = new Intent(MainActivity.this, Options.class);
			startActivity(optionsIntent);
			break;
		case R.id.bt_exit:
			finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {

	}
	
	
}
