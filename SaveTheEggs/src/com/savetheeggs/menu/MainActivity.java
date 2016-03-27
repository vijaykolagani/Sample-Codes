package com.savetheeggs.menu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.savetheeggs.R;
import com.savetheeggs.help.HelpViewPager;
import com.savetheeggs.items.ItemsViewPager;
import com.savetheeggs.worlds.WorldViewPager;

public class MainActivity extends Activity implements OnClickListener {

	private Button play;
	private Button help;
	private Button settings;
	private Button rateUs;
	private MenuView menuView;
	private FrameLayout frameLayout;
	private Dialog dialog;
	private ImageView yes, no;
	private TextView exitMessage;
	private Typeface font;
	private SoundPool soundPool;
	private int click;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(this, R.raw.click, 1);
		
		menuView = new MenuView(this);
		dialog = new Dialog(this);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		font = Typeface.createFromAsset(getAssets(), "font.ttf");
		
		frameLayout = (FrameLayout) this.findViewById(R.id.frame_layout);
		frameLayout.addView(menuView, 0);
		
		play = (Button) this.findViewById(R.id.bt_play);
		help = (Button) this.findViewById(R.id.help);
		settings = (Button) this.findViewById(R.id.settings);
		rateUs = (Button) this.findViewById(R.id.rate_us);

		play.setOnClickListener(this);
		help.setOnClickListener(this);
		settings.setOnClickListener(this);
		rateUs.setOnClickListener(this);
	}

	public void onClick(View v) {
		soundPool.play(click, 1.0f, 1.0f, 0, 0, 1.5f);
		switch (v.getId()) {
		case R.id.bt_play:
			Intent intent = new Intent(MainActivity.this, WorldViewPager.class);
			startActivity(intent);
			finish();
			break;
		case R.id.help:
			Intent helpIntent = new Intent(MainActivity.this, HelpViewPager.class);
			startActivity(helpIntent);
			finish();
			break;
		case R.id.settings:
			Intent settingsIntent = new Intent(MainActivity.this, ItemsViewPager.class);
			startActivity(settingsIntent);
			finish();
			break;
		case R.id.bt_yes:
			dialog.dismiss();
			MainActivity.this.finish();
			break;
		case R.id.bt_no:
			dialog.dismiss();
			break;
		case R.id.rate_us:
			Intent rateUsIntent = new Intent(Intent.ACTION_VIEW);
			rateUsIntent.setData(Uri.parse("market://details?id=com.savetheeggs"));
			startActivity(rateUsIntent);
			this.finish();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.setContentView(R.layout.confirmation_dialog);
			yes = (ImageView) dialog.findViewById(R.id.bt_yes);
			no = (ImageView) dialog.findViewById(R.id.bt_no);
			exitMessage = (TextView) dialog.findViewById(R.id.exit_message);
			
			exitMessage.setTypeface(font);
			
			yes.setOnClickListener(this);
			no.setOnClickListener(this);
			
			dialog.setCancelable(false);
			dialog.show();

	        return true;
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }

	}	
}
