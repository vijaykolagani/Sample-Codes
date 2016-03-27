package com.mathsforkids.multiplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.R;
import com.mathsforkids.multiplication.easy.MultiplicationEasyLevelSelect;
import com.mathsforkids.multiplication.hard.MultiplicationHardLevelSelect;
import com.mathsforkids.multiplication.medium.MultiplicationMediumLevelSelect;

public class MultiplicationModeSelection extends Activity implements OnClickListener {
	private TextView easy, medium, hard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.difficulty_layout);
		
		easy = (TextView) this.findViewById(R.id.tv_easy);
		medium = (TextView) this.findViewById(R.id.tv_medium);
		hard = (TextView) this.findViewById(R.id.tv_hard);
		
		easy.setOnClickListener(this);
		medium.setOnClickListener(this);
		hard.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_easy:
			Intent easy = new Intent(MultiplicationModeSelection.this, MultiplicationEasyLevelSelect.class);
			startActivity(easy);
			break;
		case R.id.tv_medium:
			Intent medium = new Intent(MultiplicationModeSelection.this, MultiplicationMediumLevelSelect.class);
			startActivity(medium);
			break;
		case R.id.tv_hard:
			Intent hard = new Intent(MultiplicationModeSelection.this, MultiplicationHardLevelSelect.class);
			startActivity(hard);
			break;
		default: 
			break;
		}
		
	}
}
