package com.mathsforkids.subtraction.above;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathsforkids.Constants;
import com.mathsforkids.R;

public class SubtractionAboveLevelSelect extends Activity implements
		OnClickListener {
	private TextView levelOne, levelTwo, levelThree, levelFour, levelFive,
			levelSix, levelSeven, levelEight, levelNine, levelTen, levelEleven,
			levelTwelve, levelThirteen, levelFourteen, levelFifteen,
			levelSixteen, levelSeventeen, levelEighteen, levelNineteen,
			levelTwenty, levelTwentyOne, levelTwentyTwo, levelTwentyThree,
			levelTwentyFour, levelTwentyFive, levelTwentySix, levelTwentySeven,
			levelTwentyEight, levelTwentyNine, levelThirty;

	private ImageView levelOneTick, levelTwoTick, levelThreeTick,
			levelFourTick, levelFiveTick, levelSixTick, levelSevenTick,
			levelEightTick, levelNineTick, levelTenTick, levelElevenTick,
			levelTwelveTick, levelThirteenTick, levelFourteenTick,
			levelFifteenTick, levelSixteenTick, levelSeventeenTick,
			levelEighteenTick, levelNineteenTick, levelTwentyTick,
			levelTwentyOneTick, levelTwentyTwoTick, levelTwentyThreeTick,
			levelTwentyFourTick, levelTwentyFiveTick, levelTwentySixTick,
			levelTwentySevenTick, levelTwentyEightTick, levelTwentyNineTick,
			levelThirtyTick;

	private boolean tickStatus;
	
	private SharedPreferences prefs;

	private Intent intentOneToTen, intentElevenToTwenty,
			intentTwentyOneToThirty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_layout);

		intentOneToTen = new Intent(SubtractionAboveLevelSelect.this,
				SubtractionAboveLevelOneToTen.class);
		intentElevenToTwenty = new Intent(SubtractionAboveLevelSelect.this,
				SubtractionAboveLevelElevenToTwenty.class);
		intentTwentyOneToThirty = new Intent(SubtractionAboveLevelSelect.this,
				SubtractionAboveLevelTwentyOneToThirty.class);

		prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
		
		intializeTicks();

		levelOne = (TextView) this.findViewById(R.id.level_one);
		levelTwo = (TextView) this.findViewById(R.id.level_two);
		levelThree = (TextView) this.findViewById(R.id.level_three);
		levelFour = (TextView) this.findViewById(R.id.level_four);
		levelFive = (TextView) this.findViewById(R.id.level_five);
		levelSix = (TextView) this.findViewById(R.id.level_six);
		levelSeven = (TextView) this.findViewById(R.id.level_seven);
		levelEight = (TextView) this.findViewById(R.id.level_eight);
		levelNine = (TextView) this.findViewById(R.id.level_nine);
		levelTen = (TextView) this.findViewById(R.id.level_ten);
		levelEleven = (TextView) this.findViewById(R.id.level_eleven);
		levelTwelve = (TextView) this.findViewById(R.id.level_twelve);
		levelThirteen = (TextView) this.findViewById(R.id.level_thirteen);
		levelFourteen = (TextView) this.findViewById(R.id.level_fourteen);
		levelFifteen = (TextView) this.findViewById(R.id.level_fifteen);
		levelSixteen = (TextView) this.findViewById(R.id.level_sixteen);
		levelSeventeen = (TextView) this.findViewById(R.id.level_seventeen);
		levelEighteen = (TextView) this.findViewById(R.id.level_eighteen);
		levelNineteen = (TextView) this.findViewById(R.id.level_nineteen);
		levelTwenty = (TextView) this.findViewById(R.id.level_twenty);
		levelTwentyOne = (TextView) this.findViewById(R.id.level_twentyone);
		levelTwentyTwo = (TextView) this.findViewById(R.id.level_twentytwo);
		levelTwentyThree = (TextView) this.findViewById(R.id.level_twentythree);
		levelTwentyFour = (TextView) this.findViewById(R.id.level_twentyfour);
		levelTwentyFive = (TextView) this.findViewById(R.id.level_twentyfive);
		levelTwentySix = (TextView) this.findViewById(R.id.level_twentysix);
		levelTwentySeven = (TextView) this.findViewById(R.id.level_twentyseven);
		levelTwentyEight = (TextView) this.findViewById(R.id.level_twentyeight);
		levelTwentyNine = (TextView) this.findViewById(R.id.level_twentynine);
		levelThirty = (TextView) this.findViewById(R.id.level_thirty);

		levelOne.setOnClickListener(this);
		levelTwo.setOnClickListener(this);
		levelThree.setOnClickListener(this);
		levelFour.setOnClickListener(this);
		levelFive.setOnClickListener(this);
		levelSix.setOnClickListener(this);
		levelSeven.setOnClickListener(this);
		levelEight.setOnClickListener(this);
		levelNine.setOnClickListener(this);
		levelTen.setOnClickListener(this);
		levelEleven.setOnClickListener(this);
		levelTwelve.setOnClickListener(this);
		levelThirteen.setOnClickListener(this);
		levelFourteen.setOnClickListener(this);
		levelFifteen.setOnClickListener(this);
		levelSixteen.setOnClickListener(this);
		levelSeventeen.setOnClickListener(this);
		levelEighteen.setOnClickListener(this);
		levelNineteen.setOnClickListener(this);
		levelTwenty.setOnClickListener(this);
		levelTwentyOne.setOnClickListener(this);
		levelTwentyTwo.setOnClickListener(this);
		levelTwentyThree.setOnClickListener(this);
		levelTwentyFour.setOnClickListener(this);
		levelTwentyFive.setOnClickListener(this);
		levelTwentySix.setOnClickListener(this);
		levelTwentySeven.setOnClickListener(this);
		levelTwentyEight.setOnClickListener(this);
		levelTwentyNine.setOnClickListener(this);
		levelThirty.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.level_one:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_one);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_ONE_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_ONE_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_two:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_two);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWO_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWO_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_three:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_three);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_THREE_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_THREE_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_four:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_four);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_FOUR_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_FOUR_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_five:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_five);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_FIVE_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_FIVE_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_six:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_six);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_SIX_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_SIX_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_seven:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_seven);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_SEVEN_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_SEVEN_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_eight:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_eight);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_EIGHT_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_EIGHT_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_nine:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_nine);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_NINE_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_NINE_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_ten:
			intentOneToTen.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_ten);
			intentOneToTen.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TEN_ANSWER);
			intentOneToTen.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TEN_TICK);
			startActivity(intentOneToTen);
			finish();
			break;
		case R.id.level_eleven:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_eleven);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_ELEVEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_ELEVEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_twelve:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twelve);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWELVE_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWELVE_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_thirteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_thirteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_THIRTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_THIRTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_fourteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_fourteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_FOURTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_FOURTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_fifteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_fifteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_FIFTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_FIFTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_sixteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_sixteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_SIXTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_SIXTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_seventeen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_seventeen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_SEVENTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_SEVENTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_eighteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_eighteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_EIGHTEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_EIGHTEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_nineteen:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_nineteen);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_NINETEEN_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_NINETEEN_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_twenty:
			intentElevenToTwenty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twenty);
			intentElevenToTwenty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTY_ANSWER);
			intentElevenToTwenty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTY_TICK);
			startActivity(intentElevenToTwenty);
			finish();
			break;
		case R.id.level_twentyone:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentyone);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYONE_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYONE_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentytwo:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentytwo);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYTWO_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYTWO_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentythree:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentythree);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYTHREE_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYTHREE_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentyfour:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentyfour);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYFOUR_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYFOUR_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentyfive:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentyfive);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYFIVE_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYFIVE_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentysix:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentysix);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYSIX_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYSIX_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentyseven:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentyseven);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYSEVEN_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYSEVEN_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentyeight:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentyeight);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYEIGHT_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYEIGHT_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_twentynine:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_twentynine);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_TWENTYNINE_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_TWENTYNINE_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		case R.id.level_thirty:
			intentTwentyOneToThirty.putExtra(Constants.LEVEL,
					R.array.subtraction_above_level_thirty);
			intentTwentyOneToThirty.putExtra(Constants.ANSWER,
					Constants.OPTION_ABOVE_LEVEL_THIRTY_ANSWER);
			intentTwentyOneToThirty.putExtra(Constants.LEVEL_TICK, Constants.SUBTRACTION_ABOVE_THIRTY_TICK);
			startActivity(intentTwentyOneToThirty);
			finish();
			break;
		default:
			break;
		}
	}
	
	public void intializeTicks() {
		levelOneTick = (ImageView) this.findViewById(R.id.level_one_tick);
		levelTwoTick = (ImageView) this.findViewById(R.id.level_two_tick);
		levelThreeTick = (ImageView) this.findViewById(R.id.level_three_tick);
		levelFourTick = (ImageView) this.findViewById(R.id.level_four_tick);
		levelFiveTick = (ImageView) this.findViewById(R.id.level_five_tick);
		levelSixTick = (ImageView) this.findViewById(R.id.level_six_tick);
		levelSevenTick = (ImageView) this.findViewById(R.id.level_seven_tick);
		levelEightTick = (ImageView) this.findViewById(R.id.level_eight_tick);
		levelNineTick = (ImageView) this.findViewById(R.id.level_nine_tick);
		levelTenTick = (ImageView) this.findViewById(R.id.level_ten_tick);
		levelElevenTick = (ImageView) this.findViewById(R.id.level_eleven_tick);
		levelTwelveTick = (ImageView) this.findViewById(R.id.level_twelve_tick);
		levelThirteenTick = (ImageView) this.findViewById(R.id.level_thirteen_tick);
		levelFourteenTick = (ImageView) this.findViewById(R.id.level_fourteen_tick);
		levelFifteenTick = (ImageView) this.findViewById(R.id.level_fifteen_tick);
		levelSixteenTick = (ImageView) this.findViewById(R.id.level_sixteen_tick);
		levelSeventeenTick = (ImageView) this.findViewById(R.id.level_seventeen_tick);
		levelEighteenTick = (ImageView) this.findViewById(R.id.level_eighteen_tick);
		levelNineteenTick = (ImageView) this.findViewById(R.id.level_nineteen_tick);
		levelTwentyTick = (ImageView) this.findViewById(R.id.level_twenty_tick);
		levelTwentyOneTick = (ImageView) this.findViewById(R.id.level_twentyone_tick);
		levelTwentyTwoTick = (ImageView) this.findViewById(R.id.level_twentytwo_tick);
		levelTwentyThreeTick = (ImageView) this.findViewById(R.id.level_twentythree_tick);
		levelTwentyFourTick = (ImageView) this.findViewById(R.id.level_twentyfour_tick);
		levelTwentyFiveTick = (ImageView) this.findViewById(R.id.level_twentyfive_tick);
		levelTwentySixTick = (ImageView) this.findViewById(R.id.level_twentysix_tick);
		levelTwentySevenTick = (ImageView) this.findViewById(R.id.level_twentyseven_tick);
		levelTwentyEightTick = (ImageView) this.findViewById(R.id.level_twentyeight_tick);
		levelTwentyNineTick = (ImageView) this.findViewById(R.id.level_twentynine_tick);
		levelThirtyTick = (ImageView) this.findViewById(R.id.level_thirty_tick);
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_ONE_TICK, false);
		if(!tickStatus) {
			levelOneTick.setVisibility(View.INVISIBLE);
		}

		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWO_TICK, false);
		if(!tickStatus) {
			levelTwoTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_THREE_TICK, false);
		if(!tickStatus) {
			levelThreeTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_FOUR_TICK, false);
		if(!tickStatus) {
			levelFourTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_FIVE_TICK, false);
		if(!tickStatus) {
			levelFiveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_SIX_TICK, false);
		if(!tickStatus) {
			levelSixTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_SEVEN_TICK, false);
		if(!tickStatus) {
			levelSevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_EIGHT_TICK, false);
		if(!tickStatus) {
			levelEightTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_NINE_TICK, false);
		if(!tickStatus) {
			levelNineTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TEN_TICK, false);
		if(!tickStatus) {
			levelTenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_ELEVEN_TICK, false);
		if(!tickStatus) {
			levelElevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWELVE_TICK, false);
		if(!tickStatus) {
			levelTwelveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_THIRTEEN_TICK, false);
		if(!tickStatus) {
			levelThirteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_FOURTEEN_TICK, false);
		if(!tickStatus) {
			levelFourteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_FIFTEEN_TICK, false);
		if(!tickStatus) {
			levelFifteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_SIXTEEN_TICK, false);
		if(!tickStatus) {
			levelSixteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_SEVENTEEN_TICK, false);
		if(!tickStatus) {
			levelSeventeenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_EIGHTEEN_TICK, false);
		if(!tickStatus) {
			levelEighteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_NINETEEN_TICK, false);
		if(!tickStatus) {
			levelNineteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTY_TICK, false);
		if(!tickStatus) {
			levelTwentyTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYONE_TICK, false);
		if(!tickStatus) {
			levelTwentyOneTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYTWO_TICK, false);
		if(!tickStatus) {
			levelTwentyTwoTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYTHREE_TICK, false);
		if(!tickStatus) {
			levelTwentyThreeTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYFOUR_TICK, false);
		if(!tickStatus) {
			levelTwentyFourTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYFIVE_TICK, false);
		if(!tickStatus) {
			levelTwentyFiveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYSIX_TICK, false);
		if(!tickStatus) {
			levelTwentySixTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYSEVEN_TICK, false);
		if(!tickStatus) {
			levelTwentySevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYEIGHT_TICK, false);
		if(!tickStatus) {
			levelTwentyEightTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_TWENTYNINE_TICK, false);
		if(!tickStatus) {
			levelTwentyNineTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.SUBTRACTION_ABOVE_THIRTY_TICK, false);
		if(!tickStatus) {
			levelThirtyTick.setVisibility(View.INVISIBLE);
		}
	}

}
