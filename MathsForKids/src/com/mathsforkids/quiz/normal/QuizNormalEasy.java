package com.mathsforkids.quiz.normal;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.Constants;
import com.mathsforkids.R;
import com.mathsforkids.Utilities;

public class QuizNormalEasy extends Activity implements OnClickListener {
	private TextView valueOne;
	private TextView valueTwo;
	
	private TextView optionOne;
	private TextView optionTwo;
	private TextView optionThree;
	private TextView optionFour;
	
	private TextView symbol;
	
	private TextView scoreCard, bestCard;
	
	private int randomAnswer;
	private int randomQuestion;
	private int randomSymbol;
	
	private int[] question;
	
	private int randomValue;
	
	private Random random;
	
	private int score = 0;
	private int best;
	
	private SharedPreferences prefs;
		
	private Integer[] questions = { R.array.quiz_easy_one, R.array.quiz_easy_two, R.array.quiz_easy_three, R.array.quiz_easy_four,
			R.array.quiz_easy_five, R.array.quiz_easy_six, R.array.quiz_easy_seven, R.array.quiz_easy_eight, R.array.quiz_easy_nine,
			R.array.quiz_easy_ten, R.array.quiz_easy_eleven, R.array.quiz_easy_twelve, R.array.quiz_easy_thirteen, R.array.quiz_easy_fourteen,
			R.array.quiz_easy_fifteen, R.array.quiz_easy_sixteen, R.array.quiz_easy_seventeen, R.array.quiz_easy_eighteen, R.array.quiz_easy_nineteen,
			R.array.quiz_easy_twenty, R.array.quiz_easy_twentyone, R.array.quiz_easy_twentytwo, R.array.quiz_easy_twentythree, R.array.quiz_easy_twentyfour,
			R.array.quiz_easy_twentyfive, R.array.quiz_easy_twentysix, R.array.quiz_easy_twentyseven, R.array.quiz_easy_twentyeight, R.array.quiz_easy_twentynine,
			R.array.quiz_easy_thirty, R.array.quiz_easy_thirtyone, R.array.quiz_easy_thirtytwo, R.array.quiz_easy_thirtythree, R.array.quiz_easy_thirtyfour,
			R.array.quiz_easy_thirtyfive, R.array.quiz_easy_thirtysix, R.array.quiz_easy_thirtyseven, R.array.quiz_easy_thirtyeight, R.array.quiz_easy_thirtynine,
			R.array.quiz_easy_fourty, R.array.quiz_easy_fourtyone, R.array.quiz_easy_fourtytwo, R.array.quiz_easy_fourtythree, R.array.quiz_easy_fourtyfour,
			R.array.quiz_easy_fourtyfive, R.array.quiz_easy_fourtysix, R.array.quiz_easy_fourtyseven, R.array.quiz_easy_fourtyeight, R.array.quiz_easy_fourtynine,
			R.array.quiz_easy_fifty };
	
	private Integer[] symbols = { R.string.plus, R.string.minus, R.string.cross, R.string.plus, R.string.minus,
			R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.plus, R.string.minus,
			R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.plus, R.string.minus, 
			R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.plus, R.string.minus,
			R.string.cross, R.string.plus, R.string.plus, R.string.cross, R.string.plus, R.string.minus,
			R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.cross, R.string.plus,
			R.string.minus, R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.plus,
			R.string.minus, R.string.cross, R.string.plus, R.string.minus, R.string.cross, R.string.plus,
			R.string.minus, R.string.cross, R.string.minus };
	
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
		setContentView(R.layout.quiz_normal_easy);

		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		best = prefs.getInt(Constants.QUIZ_NORMAL_EASY_BEST, 0);
		
		valueOne = (TextView) this.findViewById(R.id.value_one);
		valueTwo = (TextView) this.findViewById(R.id.value_two);
		
		optionOne = (TextView) this.findViewById(R.id.option_one);
		optionTwo = (TextView) this.findViewById(R.id.option_two);
		optionThree = (TextView) this.findViewById(R.id.option_three);
		optionFour = (TextView) this.findViewById(R.id.option_four);
		
		symbol = (TextView) this.findViewById(R.id.symbol);
		
		scoreCard = (TextView) this.findViewById(R.id.score);
		bestCard = (TextView) this.findViewById(R.id.best);
		
		bestCard.setText("Best : " +  best);
		
		changeQuestion();
		
		optionOne.setOnClickListener(this);
		optionTwo.setOnClickListener(this);
		optionThree.setOnClickListener(this);
		optionFour.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		if (v.getId() == randomAnswer) {
			score++;
			scoreCard.setText("Score : " + score);
			changeQuestion();
		} else {
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

		valueOne.setText("" + question[0]);
		valueTwo.setText("" + question[1]);
		
		optionOne.setText("" + question[2]);
		optionTwo.setText("" + question[3]);
		optionThree.setText("" + question[4]);
		optionFour.setText("" + question[5]);
		
		symbol.setText(randomSymbol);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (score > best) {
			Utilities.updateQuizBest(QuizNormalEasy.this, Constants.QUIZ_NORMAL_EASY_BEST, score);			
		}
	}
	
}
