package com.mathsforkids;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.addition.AdditionAgeSelection;
import com.mathsforkids.games.Games;
import com.mathsforkids.multiplication.MultiplicationModeSelection;
import com.mathsforkids.mystery.MysteryNumbers;
import com.mathsforkids.quiz.QuizModeSelection;
import com.mathsforkids.subtraction.SubtractionAgeSelection;

public class MainActivity extends Activity implements OnClickListener {
	
	private TextView addition;
	private TextView subtraction;
	private TextView multiplication;
	private TextView quiz;
	private TextView games;
	private TextView mystery;
	private TextView rateUs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addition = (TextView) this.findViewById(R.id.tv_addition);
		subtraction = (TextView) this.findViewById(R.id.tv_subtraction);
		multiplication = (TextView) this.findViewById(R.id.tv_multiplication);
		quiz = (TextView) this.findViewById(R.id.tv_quiz);
		games = (TextView) this.findViewById(R.id.tv_games);
		mystery = (TextView) this.findViewById(R.id.tv_mystery);
		rateUs = (TextView) this.findViewById(R.id.rate_us);
		
		addition.setOnClickListener(this);
		subtraction.setOnClickListener(this);
		multiplication.setOnClickListener(this);
		quiz.setOnClickListener(this);
		games.setOnClickListener(this);
		mystery.setOnClickListener(this);
		rateUs.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_addition:
			Intent addtionIntent = new Intent(MainActivity.this, AdditionAgeSelection.class);
			startActivity(addtionIntent);
			break;
		case R.id.tv_subtraction:
			Intent subtractionIntent = new Intent(MainActivity.this, SubtractionAgeSelection.class);
			startActivity(subtractionIntent);
			break;
		case R.id.tv_multiplication:
			Intent multiplicationIntent = new Intent(MainActivity.this, MultiplicationModeSelection.class);
			startActivity(multiplicationIntent);
			break;
		case R.id.tv_quiz:
			Intent quizIntent = new Intent(MainActivity.this, QuizModeSelection.class);
			startActivity(quizIntent);
			break;
		case R.id.tv_games:
			Intent gamesIntent = new Intent(MainActivity.this, Games.class);
			startActivity(gamesIntent);
			break;
		case R.id.tv_mystery:
			Intent mysteryIntent = new Intent(MainActivity.this, MysteryNumbers.class);
			startActivity(mysteryIntent);
			break;
		case R.id.rate_us:
			Intent rateUsIntent = new Intent(Intent.ACTION_VIEW);
			rateUsIntent.setData(Uri.parse("market://details?id=com.mathsforkids"));
			startActivity(rateUsIntent);
			break;
		default:
			break;
		}
	}
}
