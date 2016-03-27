package kysk.yogatrainer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
	private Button poses, rate, feed;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		poses = (Button) findViewById(R.id.poses);
		rate = (Button) findViewById(R.id.rate);
		feed = (Button) findViewById(R.id.feed);
		
		poses.getBackground().setColorFilter(Color.parseColor("#7CD3D3"), PorterDuff.Mode.MULTIPLY);
		rate.getBackground().setColorFilter(Color.parseColor("#7CD3D3"), PorterDuff.Mode.MULTIPLY);
		feed.getBackground().setColorFilter(Color.parseColor("#7CD3D3"), PorterDuff.Mode.MULTIPLY);
		
		poses.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Main.this, YogaTrainerActivity.class));
			}
		});
		
		rate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=syminc.yogatrainer"));
				startActivity(intent);
			}
		});
		
		feed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		    	emailIntent.setType("plain/text");
		    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"yogasaikrishna@gmail.com"});
		    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yoga Trainer Pro Feedback");

		    	startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
		});
	}

}
