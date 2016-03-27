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

public class Help extends Activity {
	
	private Typeface font;
	private TextView title;
	private Button ok;
	
	private SoundPool soundPool;
	private int menuClick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
		
		menuClick = soundPool.load(this, R.raw.menu_click, 1);

		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		title = (TextView) this.findViewById(R.id.help_title);
		ok = (Button) this.findViewById(R.id.bt_ok);
		
		title.setTypeface(font);
		
		ok.setSoundEffectsEnabled(false);
		ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
				finish();
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
    	soundPool.play(menuClick, 1.0f, 1.0f, 0, 0, 1.0f);
		super.onBackPressed();
	}

}
