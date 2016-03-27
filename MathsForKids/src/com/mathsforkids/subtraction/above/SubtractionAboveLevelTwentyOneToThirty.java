package com.mathsforkids.subtraction.above;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.Constants;
import com.mathsforkids.R;
import com.mathsforkids.Utilities;

public class SubtractionAboveLevelTwentyOneToThirty extends Activity implements OnClickListener {
	private TextView valueOne;
	private TextView valueTwo;
	private TextView valueThree;
	private TextView valueFour;
	
	private TextView optionOne;
	private TextView optionTwo;
	private TextView optionThree;
	private TextView optionFour;
	
	private Dialog dialog;
	private int LEVEL_ANSWER;
	private int levelArray;
	private String levelTick;
	
	private int[] level;
	
	private SoundPool soundPool;
	private int successSound, failureSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subtraction_level_twentyone_to_thirty);
		Bundle b = getIntent().getExtras();
		levelArray = b.getInt(Constants.LEVEL);
		LEVEL_ANSWER = b.getInt(Constants.ANSWER);
		levelTick = b.getString(Constants.LEVEL_TICK);
		
		dialog = new Dialog(SubtractionAboveLevelTwentyOneToThirty.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		successSound = soundPool.load(this, R.raw.cool_man, 1);
		failureSound = soundPool.load(this, R.raw.yo_man, 1);
		
		level = getResources().getIntArray(levelArray);
		
		valueOne = (TextView) this.findViewById(R.id.value_one);
		valueTwo = (TextView) this.findViewById(R.id.value_two);
		valueThree = (TextView) this.findViewById(R.id.value_three);
		valueFour = (TextView) this.findViewById(R.id.value_four);
		
		optionOne = (TextView) this.findViewById(R.id.option_one);
		optionTwo = (TextView) this.findViewById(R.id.option_two);
		optionThree = (TextView) this.findViewById(R.id.option_three);
		optionFour = (TextView) this.findViewById(R.id.option_four);
		
		valueOne.setText("" + level[0]);
		valueTwo.setText("" + level[1]);
		valueThree.setText("" + level[2]);
		valueFour.setText("" + level[3]);
		optionOne.setText("" + level[4]);
		optionTwo.setText("" + level[5]);
		optionThree.setText("" + level[6]);
		optionFour.setText("" + level[7]);
		
		optionOne.setOnClickListener(this);
		optionTwo.setOnClickListener(this);
		optionThree.setOnClickListener(this);
		optionFour.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		if (v.getId() == LEVEL_ANSWER) {
			dialog.setContentView(R.layout.success);
			TextView next = (TextView) dialog.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					dialog.dismiss();
					Utilities.updateTickVisibility(SubtractionAboveLevelTwentyOneToThirty.this, levelTick);
					Intent intent = new Intent(SubtractionAboveLevelTwentyOneToThirty.this, SubtractionAboveLevelSelect.class);
					startActivity(intent);
					finish();
				}
			});
			dialog.show();
			soundPool.play(successSound, 1.0f, 1.0f, 0, 0, 1.4f);
		} else {
			dialog.setContentView(R.layout.failure);
			TextView ok = (TextView) dialog.findViewById(R.id.ok);
			ok.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			soundPool.play(failureSound, 1.0f, 1.0f, 0, 0, 1.4f);
		}
	}
}
