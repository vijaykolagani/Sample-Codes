package kysk.roadrush.full;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashScreen.this, MainActivity.class);
				SplashScreen.this.startActivity(intent);
				SplashScreen.this.finish();
			}
		}, 2000);
	}

}
