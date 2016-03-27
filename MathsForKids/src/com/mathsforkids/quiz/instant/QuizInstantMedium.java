package com.mathsforkids.quiz.instant;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathsforkids.Constants;
import com.mathsforkids.R;
import com.mathsforkids.Utilities;

public class QuizInstantMedium extends Activity implements OnClickListener {
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
	private ImageView next;
	
	private TextView symbolOne;
	private TextView symbolTwo;
	
	private TextView scoreCard, bestCard;
	
	private int randomAnswer;
	private int randomQuestion;
	private int randomSymbol;
	
	private int[] question;
	private String[] symbol;
	
	private int randomValue;
	
	private Random random;
	
	private int score = 0;
	private int best;
	
	private SharedPreferences prefs;
		
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
		setContentView(R.layout.quiz_instant_medium);

		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		best = prefs.getInt(Constants.QUIZ_INSTANT_MEDIUM_BEST, 0);
		
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
		next = (ImageView) this.findViewById(R.id.next);
		
		bestCard.setText("Best : " +  best);
		
		changeQuestion();
		
		optionOne.setOnClickListener(this);
		optionTwo.setOnClickListener(this);
		optionThree.setOnClickListener(this);
		optionFour.setOnClickListener(this);
		next.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		optionOne.setClickable(false);
		optionTwo.setClickable(false);
		optionThree.setClickable(false);
		optionFour.setClickable(false);
		
		if (v.getId() == randomAnswer) {
			score++;
			scoreCard.setText("Score : " + score);
		} else {			
			switch (v.getId()) {
			case R.id.option_one:
				optionOneImage.setVisibility(View.VISIBLE);
				optionOneImage.setImageResource(R.drawable.wrong_mark);
				break;
			case R.id.option_two:
				optionTwoImage.setVisibility(View.VISIBLE);
				optionTwoImage.setImageResource(R.drawable.wrong_mark);
				break;
			case R.id.option_three:
				optionThreeImage.setVisibility(View.VISIBLE);
				optionThreeImage.setImageResource(R.drawable.wrong_mark);
				break;
			case R.id.option_four:
				optionFourImage.setVisibility(View.VISIBLE);
				optionFourImage.setImageResource(R.drawable.wrong_mark);
				break;
			default:
				break;
			}			
		}

		switch (randomAnswer) {
		case R.id.option_one:
			optionOneImage.setVisibility(View.VISIBLE);
			optionOneImage.setImageResource(R.drawable.tick_mark);
			break;
		case R.id.option_two:
			optionTwoImage.setVisibility(View.VISIBLE);
			optionTwoImage.setImageResource(R.drawable.tick_mark);
			break;
		case R.id.option_three:
			optionThreeImage.setVisibility(View.VISIBLE);
			optionThreeImage.setImageResource(R.drawable.tick_mark);
			break;
		case R.id.option_four:
			optionFourImage.setVisibility(View.VISIBLE);
			optionFourImage.setImageResource(R.drawable.tick_mark);
			break;
		default:
			break;			
		}

		if (v.getId() == R.id.next) {
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
			Utilities.updateQuizBest(QuizInstantMedium.this, Constants.QUIZ_INSTANT_MEDIUM_BEST, score);			
		}
	}
	
}
