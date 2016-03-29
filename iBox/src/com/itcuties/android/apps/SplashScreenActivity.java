package com.itcuties.android.apps;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.prasad.ibox.R;

public class SplashScreenActivity extends Activity {
	private long ms = 0;
	private long splashDuration = 2000;
	private boolean splashActive = true;
	private boolean paused = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		Thread mythread = new Thread() {
			public void run() {
				try {
					while (splashActive && ms < splashDuration) {
						if (!paused)
							ms = ms + 500;
						
						sleep(500);
					}
				} catch (Exception e) {
				} finally {
					Intent intent = new Intent(SplashScreenActivity.this,
							MainActivity.class);
					startActivity(intent);
				}
			}
		};
		mythread.start();
	}
}
