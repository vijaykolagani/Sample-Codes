package kysk.yogatrainer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SwanPose extends Activity {

	private TextView pose_name, pose_duration;
	private ImageView pose_image;
	private Button taking, releasing, benefits;
	private Dialog dialog;
	private Button start_timer, stop_timer, select_time;
	private Button button_ok, button_cancel;
	private Chronometer clock;
	private EditText time_input;
	private MediaPlayer player;

	private double time = 0;
	private int FLAG = -1;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main_layout);

		pose_name = (TextView) findViewById(R.id.pose_name);
		pose_name.setText(getResources().getStringArray(
				R.array.sitting_position)[14]);

		pose_image = (ImageView) findViewById(R.id.pose_image);
		pose_image.setImageResource(R.drawable.swan_pose);

		pose_duration = (TextView) findViewById(R.id.pose_duration);
		pose_duration.setText(getResources().getStringArray(
				R.array.sitting_durations)[14]);

		taking = (Button) findViewById(R.id.taking);
		releasing = (Button) findViewById(R.id.releasing);
		benefits = (Button) findViewById(R.id.benefits);

		start_timer = (Button) findViewById(R.id.start);
		select_time = (Button) findViewById(R.id.timer);
		clock = (Chronometer) findViewById(R.id.clock);
		stop_timer = (Button) findViewById(R.id.stop);

		taking.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);
		releasing.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);
		benefits.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);
		start_timer.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);
		select_time.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);
		stop_timer.getBackground().setColorFilter(Color.parseColor("#009AFA"),
				PorterDuff.Mode.MULTIPLY);

		dialog = new Dialog(SwanPose.this);
		player = new MediaPlayer();

		taking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SwanPose.this, PoseTaking.class);
				i.putExtra("pose", 32);
				i.putExtra("pose_name", pose_name.getText());
				startActivity(i);

			}
		});

		releasing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SwanPose.this, PoseReleasing.class);
				i.putExtra("pose", 32);
				i.putExtra("pose_name", pose_name.getText());
				startActivity(i);
			}
		});

		benefits.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SwanPose.this, PoseBenefits.class);
				i.putExtra("pose", 32);
				i.putExtra("pose_name", pose_name.getText());
				startActivity(i);
			}
		});
		select_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.setContentView(R.layout.timer_layout);
				dialog.setTitle(getString(R.string.settime));

				button_ok = (Button) dialog.findViewById(R.id.button_ok);
				button_ok.getBackground().setColorFilter(
						Color.parseColor("#009AFA"), PorterDuff.Mode.MULTIPLY);
				button_cancel = (Button) dialog
						.findViewById(R.id.button_cancel);
				button_cancel.getBackground().setColorFilter(
						Color.parseColor("#009AFA"), PorterDuff.Mode.MULTIPLY);
				time_input = (EditText) dialog.findViewById(R.id.time_input);

				dialog.show();

				button_ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (time_input.getText().toString().length() <= 0)
							Toast.makeText(getApplicationContext(),
									getString(R.string.bt_ok_text),
									Toast.LENGTH_SHORT).show();
						else {
							time = Double.parseDouble(time_input.getText()
									.toString());
							dialog.dismiss();
						}
					}
				});

				button_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
			}
		});

		start_timer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (time == 0) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.st_text), Toast.LENGTH_SHORT).show();
				} else {
					clock.setBase(SystemClock.elapsedRealtime());
					clock.start();
					Thread timerThread = new Thread() {
						@Override
						public void run() {
							try {
								FLAG = 0;
								sleep((long) (time * 60 * 1000 + 1000));
								clock.stop();
								if (FLAG != -1)
									alertNotification();
							} catch (InterruptedException e) {
							} finally {
								time = 0;
							}
						}
					};
					timerThread.start();
				}
			}
		});

		stop_timer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (player.isPlaying()) {
					player.reset();
				}
				clock.stop();
				FLAG = -1;
				time = 0;

			}
		});
	}

	public void alertNotification() {
		try {
			Uri alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			player.setDataSource(getApplicationContext(), alert);
			player.setAudioStreamType(AudioManager.STREAM_RING);
			player.prepare();
			player.start();
		} catch (Exception e) {
		}
	}
}