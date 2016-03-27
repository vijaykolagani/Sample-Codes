package com.mathsforkids.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.R;
import com.mathsforkids.quiz.instant.QuizInstantDifficultySelection;
import com.mathsforkids.quiz.normal.QuizNormalDifficultySelection;
import com.mathsforkids.quiz.timer.QuizTimerDifficultySelection;

public class QuizModeSelection extends Activity implements OnClickListener {
	private TextView normal, instant, timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_mode_layout);
		
		normal = (TextView) this.findViewById(R.id.tv_normal);
		instant = (TextView) this.findViewById(R.id.tv_instant);
		timer = (TextView) this.findViewById(R.id.tv_timer);
		
		normal.setOnClickListener(this);
		instant.setOnClickListener(this);
		timer.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_normal:
			Intent normal = new Intent(QuizModeSelection.this, QuizNormalDifficultySelection.class);
			startActivity(normal);
			break;
		case R.id.tv_instant:
			Intent instant = new Intent(QuizModeSelection.this, QuizInstantDifficultySelection.class);
			startActivity(instant);
			break;
		case R.id.tv_timer:
			Intent timer = new Intent(QuizModeSelection.this, QuizTimerDifficultySelection.class);
			startActivity(timer);
			break;
		default: 
			break;
		}
		
	}
}
