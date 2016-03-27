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

public class QuizInstantHard extends Activity implements OnClickListener {
	private TextView valueOne;
	private TextView valueTwo;
	private TextView valueThree;
	private TextView valueFour;
	
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
	private TextView symbolThree;
	
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
		
	private Integer[] questions = { R.array.quiz_hard_one, R.array.quiz_hard_two, R.array.quiz_hard_three, R.array.quiz_hard_four,
			R.array.quiz_hard_five, R.array.quiz_hard_six, R.array.quiz_hard_seven, R.array.quiz_hard_eight, R.array.quiz_hard_nine,
			R.array.quiz_hard_ten, R.array.quiz_hard_eleven, R.array.quiz_hard_twelve, R.array.quiz_hard_thirteen, R.array.quiz_hard_fourteen,
			R.array.quiz_hard_fifteen, R.array.quiz_hard_sixteen, R.array.quiz_hard_seventeen, R.array.quiz_hard_eighteen, R.array.quiz_hard_nineteen,
			R.array.quiz_hard_twenty, R.array.quiz_hard_twentyone, R.array.quiz_hard_twentytwo, R.array.quiz_hard_twentythree, R.array.quiz_hard_twentyfour,
			R.array.quiz_hard_twentyfive, R.array.quiz_hard_twentysix, R.array.quiz_hard_twentyseven, R.array.quiz_hard_twentyeight, R.array.quiz_hard_twentynine,
			R.array.quiz_hard_thirty, R.array.quiz_hard_thirtyone, R.array.quiz_hard_thirtytwo, R.array.quiz_hard_thirtythree, R.array.quiz_hard_thirtyfour,
			R.array.quiz_hard_thirtyfive, R.array.quiz_hard_thirtysix, R.array.quiz_hard_thirtyseven, R.array.quiz_hard_thirtyeight, R.array.quiz_hard_thirtynine,
			R.array.quiz_hard_fourty, R.array.quiz_hard_fourtyone, R.array.quiz_hard_fourtytwo, R.array.quiz_hard_fourtythree, R.array.quiz_hard_fourtyfour,
			R.array.quiz_hard_fourtyfive, R.array.quiz_hard_fourtysix, R.array.quiz_hard_fourtyseven, R.array.quiz_hard_fourtyeight, R.array.quiz_hard_fourtynine,
			R.array.quiz_hard_fifty };
	
	private Integer[] symbols = { R.array.plus_minus_plus, R.array.cross_plus_cross, R.array.minus_plus_cross, 
			R.array.cross_minus_plus, R.array.cross_plus_cross, R.array.minus_plus_minus, 
			R.array.cross_plus_minus, R.array.cross_cross_minus, R.array.plus_minus_cross, 
			R.array.cross_minus_plus, R.array.plus_minus_cross, R.array.cross_minus_cross, 
			R.array.plus_cross_minus, R.array.plus_cross_minus, R.array.minus_plus_cross, 
			R.array.cross_minus_cross, R.array.plus_minus_cross, R.array.plus_cross_plus, 
			R.array.cross_plus_cross, R.array.minus_plus_cross, R.array.cross_cross_minus, 
			R.array.plus_cross_minus, R.array.cross_plus_minus, R.array.plus_plus_cross,
			R.array.minus_plus_minus, R.array.plus_plus_cross, R.array.cross_minus_cross, 
			R.array.plus_cross_minus, R.array.cross_plus_cross, R.array.cross_minus_cross, 
			R.array.plus_minus_cross, R.array.cross_plus_minus, R.array.plus_minus_cross, 
			R.array.minus_plus_cross, R.array.cross_minus_cross, R.array.plus_cross_plus, 
			R.array.cross_plus_minus, R.array.cross_plus_minus, R.array.plus_minus_cross, 
			R.array.cross_minus_plus, R.array.cross_minus_minus, R.array.cross_minus_cross, 
			R.array.plus_cross_minus, R.array.plus_cross_minus,	R.array.minus_plus_cross, 
			R.array.cross_minus_cross, R.array.plus_cross_minus, R.array.plus_cross_minus, 
			R.array.cross_minus_plus, R.array.plus_minus_cross };
	
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
		setContentView(R.layout.quiz_instant_hard);

		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		best = prefs.getInt(Constants.QUIZ_INSTANT_HARD_BEST, 0);
		
		valueOne = (TextView) this.findViewById(R.id.value_one);
		valueTwo = (TextView) this.findViewById(R.id.value_two);
		valueThree = (TextView) this.findViewById(R.id.value_three);
		valueFour = (TextView) this.findViewById(R.id.value_four);
		
		optionOne = (TextView) this.findViewById(R.id.option_one);
		optionTwo = (TextView) this.findViewById(R.id.option_two);
		optionThree = (TextView) this.findViewById(R.id.option_three);
		optionFour = (TextView) this.findViewById(R.id.option_four);
		
		symbolOne = (TextView) this.findViewById(R.id.symbol_one);
		symbolTwo = (TextView) this.findViewById(R.id.symbol_two);
		symbolThree = (TextView) this.findViewById(R.id.symbol_three);
		
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
		valueFour.setText("" + question[3]);
		
		optionOne.setText("" + question[4]);
		optionTwo.setText("" + question[5]);
		optionThree.setText("" + question[6]);
		optionFour.setText("" + question[7]);
		
		symbolOne.setText(symbol[0]);
		symbolTwo.setText(symbol[1]);
		symbolThree.setText(symbol[2]);
		
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
			Utilities.updateQuizBest(QuizInstantHard.this, Constants.QUIZ_INSTANT_HARD_BEST, score);			
		}
	}
	
}
