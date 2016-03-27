package com.mathsforkids.quiz.timer;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Chronometer.OnChronometerTickListener;

import com.mathsforkids.Constants;
import com.mathsforkids.R;
import com.mathsforkids.Utilities;

public class QuizTimerMedium extends Activity implements OnClickListener, OnChronometerTickListener {
	private TextView valueOne;
	private TextView valueTwo;
	private TextView valueThree;
	
	private TextView optionOne;
	private TextView optionTwo;
	private TextView optionThree;
	private TextView optionFour;
	
	private ImageView optionOneImage;
	private ImageView optionTwoImage;
	private ImageView optionThreeImage;
	private ImageView optionFourImage;
	private ImageView skip;
	
	private TextView symbolOne;
	private TextView symbolTwo;
	
	private TextView scoreCard, bestCard;
	
	private Chronometer timer;
	
	private int randomAnswer;
	private int randomQuestion;
	private int randomSymbol;
	
	private int[] question;
	private String[] symbol;
	
	private int randomValue;
	
	private Random random;
	
	private int score = 0;
	private int best;
	private int skipCount = 0;
	private int wrongCount = 0;
	
	private SharedPreferences prefs;
	
	private Dialog dialog;
	
	private SoundPool soundPool;
	private int alarm;
		
	private Integer[] questions = { R.array.quiz_medium_one, R.array.quiz_medium_two, R.array.quiz_medium_three, R.array.quiz_medium_four,
			R.array.quiz_medium_five, R.array.quiz_medium_six, R.array.quiz_medium_seven, R.array.quiz_medium_eight, R.array.quiz_medium_nine,
			R.array.quiz_medium_ten, R.array.quiz_medium_eleven, R.array.quiz_medium_twelve, R.array.quiz_medium_thirteen, R.array.quiz_medium_fourteen,
			R.array.quiz_medium_fifteen, R.array.quiz_medium_sixteen, R.array.quiz_medium_seventeen, R.array.quiz_medium_eighteen, R.array.quiz_medium_nineteen,
			R.array.quiz_medium_twenty, R.array.quiz_medium_twentyone, R.array.quiz_medium_twentytwo, R.array.quiz_medium_twentythree, R.array.quiz_medium_twentyfour,
			R.array.quiz_medium_twentyfive, R.array.quiz_medium_twentysix, R.array.quiz_medium_twentyseven, R.array.quiz_medium_twentyeight, R.array.quiz_medium_twentynine,
			R.array.quiz_medium_thirty, R.array.quiz_medium_thirtyone, R.array.quiz_medium_thirtytwo, R.array.quiz_medium_thirtythree, R.array.quiz_medium_thirtyfour,
			R.array.quiz_medium_thirtyfive, R.array.quiz_medium_thirtysix, R.array.quiz_medium_thirtyseven, R.array.quiz_medium_thirtyeight, R.array.quiz_medium_thirtynine,
			R.array.quiz_medium_fourty, R.array.quiz_medium_fourtyone, R.array.quiz_medium_fourtytwo, R.array.quiz_medium_fourtythree, R.array.quiz_medium_fourtyfour,
			R.array.quiz_medium_fourtyfive, R.array.quiz_medium_fourtysix, R.array.quiz_medium_fourtyseven, R.array.quiz_medium_fourtyeight, R.array.quiz_medium_fourtynine,
			R.array.quiz_medium_fifty };
	
	private Integer[] symbols = { R.array.plus_plus, R.array.minus_plus, R.array.cross_minus, R.array.plus_cross, 
			R.array.minus_cross, R.array.plus_cross, R.array.minus_plus, R.array.cross_minus, R.array.plus_minus, 
			R.array.cross_minus, R.array.plus_minus, R.array.cross_minus, R.array.cross_plus, R.array.plus_minus, 
			R.array.minus_minus, R.array.plus_minus, R.array.cross_cross, R.array.cross_plus, R.array.cross_minus, 
			R.array.plus_cross, R.array.minus_cross, R.array.plus_cross, R.array.cross_minus, R.array.plus_minus,
			R.array.cross_minus, R.array.plus_minus, R.array.cross_plus, R.array.cross_minus, R.array.cross_minus,
			R.array.cross_minus, R.array.plus_cross, R.array.minus_cross, R.array.plus_cross, R.array.minus_cross, 
			R.array.minus_cross, R.array.cross_minus, R.array.cross_plus, R.array.minus_cross, R.array.cross_minus, 
			R.array.plus_cross, R.array.minus_plus, R.array.plus_minus, R.array.cross_minus, R.array.plus_minus, 
			R.array.plus_minus, R.array.plus_minus, R.array.cross_minus, R.array.cross_plus, R.array.minus_plus, 
			R.array.cross_minus };
	
	private Integer[] answers = { R.id.option_four, R.id.option_three, R.id.option_four, R.id.option_two, R.id.option_one,
			R.id.option_three, R.id.option_two, R.id.option_four, R.id.option_one, R.id.option_two, R.id.option_three,
			R.id.option_two, R.id.option_four, R.id.option_four, R.id.option_two, R.id.option_three, R.id.option_four,
			R.id.option_one, R.id.option_three, R.id.option_two, R.id.option_one, R.id.option_two, R.id.option_one,
			R.id.option_four, R.id.option_four, R.id.option_one, R.id.option_one, R.id.option_four, R.id.option_one,
			R.id.option_two, R.id.option_four, R.id.option_three, R.id.option_two, R.id.option_four, R.id.option_one,
			R.id.option_one, R.id.option_three, R.id.option_two, R.id.option_four, R.id.option_three, R.id.option_three,
			R.id.option_one, R.id.option_two, R.id.option_four, R.id.option_three, R.id.option_one, R.id.option_three,
			R.id.option_four, R.id.option_two, R.id.option_one };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_timer_medium);

		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		best = prefs.getInt(Constants.QUIZ_TIMER_MEDIUM_BEST, 0);
		
		dialog = new Dialog(QuizTimerMedium.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		alarm = soundPool.load(this, R.raw.alarm, 1);
		
		valueOne = (TextView) this.findViewById(R.id.value_one);
		valueTwo = (TextView) this.findViewById(R.id.value_two);
		valueThree = (TextView) this.findViewById(R.id.value_three);
		
		optionOne = (TextView) this.findViewById(R.id.option_one);
		optionTwo = (TextView) this.findViewById(R.id.option_two);
		optionThree = (TextView) this.findViewById(R.id.option_three);
		optionFour = (TextView) this.findViewById(R.id.option_four);
		
		symbolOne = (TextView) this.findViewById(R.id.symbol_one);
		symbolTwo = (TextView) this.findViewById(R.id.symbol_two);
		
		scoreCard = (TextView) this.findViewById(R.id.score);
		bestCard = (TextView) this.findViewById(R.id.best);
		
		optionOneImage = (ImageView) this.findViewById(R.id.option_one_image);
		optionTwoImage = (ImageView) this.findViewById(R.id.option_two_image);
		optionThreeImage = (ImageView) this.findViewById(R.id.option_three_image);
		optionFourImage = (ImageView) this.findViewById(R.id.option_four_image);
		skip = (ImageView) this.findViewById(R.id.skip);
		
		timer = (Chronometer) this.findViewById(R.id.timer);
		
		bestCard.setText("Best : " +  best);
		
		changeQuestion();
		
		optionOne.setOnClickListener(this);
		optionTwo.setOnClickListener(this);
		optionThree.setOnClickListener(this);
		optionFour.setOnClickListener(this);
		skip.setOnClickListener(this);
		timer.setOnChronometerTickListener(this);
		
	}

	public void onClick(View v) {
		if (v.getId() == randomAnswer) {
			score++;
			scoreCard.setText("Score : " + score);
			changeQuestion();
		} else if (v.getId() == R.id.skip) {
			skipCount++;
			changeQuestion();
		} else {
			wrongCount++;
			changeQuestion();
		}
	}
	
	public void changeQuestion() {
		random = new Random();
		randomValue = random.nextInt(49);
		
		randomQuestion = questions[randomValue];
		randomAnswer = answers[randomValue];
		randomSymbol = symbols[randomValue];
		
		question = getResources().getIntArray(randomQuestion);
		symbol = getResources().getStringArray(randomSymbol);

		valueOne.setText("" + question[0]);
		valueTwo.setText("" + question[1]);
		valueThree.setText("" + question[2]);
		
		optionOne.setText("" + question[3]);
		optionTwo.setText("" + question[4]);
		optionThree.setText("" + question[5]);
		optionFour.setText("" + question[6]);
		
		symbolOne.setText(symbol[0]);
		symbolTwo.setText(symbol[1]);
		
		timer.start();
		
		optionOneImage.setVisibility(View.INVISIBLE);
		optionTwoImage.setVisibility(View.INVISIBLE);
		optionThreeImage.setVisibility(View.INVISIBLE);
		optionFourImage.setVisibility(View.INVISIBLE);
		
		optionOne.setClickable(true);
		optionTwo.setClickable(true);
		optionThree.setClickable(true);
		optionFour.setClickable(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (score > best) {
			Utilities.updateQuizBest(QuizTimerMedium.this, Constants.QUIZ_TIMER_MEDIUM_BEST, score);			
		}
	}
	
	public void onChronometerTick(Chronometer c) {
		String tick = (String) c.getText();
		if (tick.equals(Constants.QUIZ_TIMER_MEDIUM)) {
			soundPool.play(alarm, 1.0f, 1.0f, 0, 0, 1.4f);
			timer.stop();
			dialog.setCancelable(false);
			dialog.setContentView(R.layout.quiz_timer_finish);
			
			TextView correct = (TextView) dialog.findViewById(R.id.correct);
			TextView wrong = (TextView) dialog.findViewById(R.id.wrong);
			TextView skipped = (TextView) dialog.findViewById(R.id.skipped);
			
			correct.setText("Correct : " + score);
			wrong.setText("Wrong : " + wrongCount);
			skipped.setText("Skipped : " + skipCount);
			
			TextView ok = (TextView) dialog.findViewById(R.id.ok);
			ok.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					dialog.dismiss();
					finish();
				}
			});
			dialog.show();
		}
	}
	
}
