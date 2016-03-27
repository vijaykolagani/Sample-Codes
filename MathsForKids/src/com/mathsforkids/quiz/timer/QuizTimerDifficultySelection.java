package com.mathsforkids.quiz.timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.R;

public class QuizTimerDifficultySelection extends Activity implements OnClickListener {
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
			Intent easy = new Intent(QuizTimerDifficultySelection.this, QuizTimerEasy.class);
			startActivity(easy);
			break;
		case R.id.tv_medium:
			Intent medium = new Intent(QuizTimerDifficultySelection.this, QuizTimerMedium.class);
			startActivity(medium);
			break;
		case R.id.tv_hard:
			Intent hard = new Intent(QuizTimerDifficultySelection.this, QuizTimerHard.class);
			startActivity(hard);
			break;
		default: 
			break;
		}
		
	}
}