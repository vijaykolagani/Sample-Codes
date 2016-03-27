package com.mathsforkids.addition.four;

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

public class AdditionFourLevelSelect extends Activity implements
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

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_layout);

		intent = new Intent(AdditionFourLevelSelect.this, AdditionFour.class);
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
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_one);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_ONE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_ONE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_two:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_two);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWO_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWO_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_three:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_three);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_THREE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_THREE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_four:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_four);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_FOUR_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_FOUR_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_five:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_five);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_FIVE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_FIVE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_six:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_six);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_SIX_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_SIX_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_seven:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_seven);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_SEVEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_SEVEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_eight:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_eight);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_EIGHT_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_EIGHT_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_nine:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_nine);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_NINE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_NINE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_ten:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_ten);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_eleven:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_eleven);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_ELEVEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_ELEVEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twelve:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twelve);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWELVE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWELVE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_thirteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_thirteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_THIRTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_THIRTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_fourteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_fourteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_FOURTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_FOURTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_fifteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_fifteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_FIFTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_FIFTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_sixteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_sixteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_SIXTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_SIXTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_seventeen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_seventeen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_SEVENTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_SEVENTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_eighteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_eighteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_EIGHTEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_EIGHTEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_nineteen:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_nineteen);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_NINETEEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_NINETEEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twenty:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twenty);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTY_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTY_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentyone:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentyone);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYONE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYONE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentytwo:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentytwo);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYTWO_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYTWO_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentythree:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentythree);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYTHREE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYTHREE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentyfour:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentyfour);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYFOUR_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYFOUR_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentyfive:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentyfive);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYFIVE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYFIVE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentysix:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentysix);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYSIX_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYSIX_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentyseven:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentyseven);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYSEVEN_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYSEVEN_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentyeight:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentyeight);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYEIGHT_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYEIGHT_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_twentynine:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_twentynine);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_TWENTYNINE_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_TWENTYNINE_TICK);
			startActivity(intent);
			finish();
			break;
		case R.id.level_thirty:
			intent.putExtra(Constants.LEVEL, R.layout.addition_four_thirty);
			intent.putExtra(Constants.ANSWER,
					Constants.OPTION_FOUR_LEVEL_THIRTY_ANSWER);
			intent.putExtra(Constants.LEVEL_TICK, Constants.ADDITION_FOUR_THIRTY_TICK);
			startActivity(intent);
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
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_ONE_TICK, false);
		if(!tickStatus) {
			levelOneTick.setVisibility(View.INVISIBLE);
		}

		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWO_TICK, false);
		if(!tickStatus) {
			levelTwoTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_THREE_TICK, false);
		if(!tickStatus) {
			levelThreeTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_FOUR_TICK, false);
		if(!tickStatus) {
			levelFourTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_FIVE_TICK, false);
		if(!tickStatus) {
			levelFiveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_SIX_TICK, false);
		if(!tickStatus) {
			levelSixTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_SEVEN_TICK, false);
		if(!tickStatus) {
			levelSevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_EIGHT_TICK, false);
		if(!tickStatus) {
			levelEightTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_NINE_TICK, false);
		if(!tickStatus) {
			levelNineTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TEN_TICK, false);
		if(!tickStatus) {
			levelTenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_ELEVEN_TICK, false);
		if(!tickStatus) {
			levelElevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWELVE_TICK, false);
		if(!tickStatus) {
			levelTwelveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_THIRTEEN_TICK, false);
		if(!tickStatus) {
			levelThirteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_FOURTEEN_TICK, false);
		if(!tickStatus) {
			levelFourteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_FIFTEEN_TICK, false);
		if(!tickStatus) {
			levelFifteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_SIXTEEN_TICK, false);
		if(!tickStatus) {
			levelSixteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_SEVENTEEN_TICK, false);
		if(!tickStatus) {
			levelSeventeenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_EIGHTEEN_TICK, false);
		if(!tickStatus) {
			levelEighteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_NINETEEN_TICK, false);
		if(!tickStatus) {
			levelNineteenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTY_TICK, false);
		if(!tickStatus) {
			levelTwentyTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYONE_TICK, false);
		if(!tickStatus) {
			levelTwentyOneTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYTWO_TICK, false);
		if(!tickStatus) {
			levelTwentyTwoTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYTHREE_TICK, false);
		if(!tickStatus) {
			levelTwentyThreeTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYFOUR_TICK, false);
		if(!tickStatus) {
			levelTwentyFourTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYFIVE_TICK, false);
		if(!tickStatus) {
			levelTwentyFiveTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYSIX_TICK, false);
		if(!tickStatus) {
			levelTwentySixTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYSEVEN_TICK, false);
		if(!tickStatus) {
			levelTwentySevenTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYEIGHT_TICK, false);
		if(!tickStatus) {
			levelTwentyEightTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_TWENTYNINE_TICK, false);
		if(!tickStatus) {
			levelTwentyNineTick.setVisibility(View.INVISIBLE);
		}
		
		tickStatus = prefs.getBoolean(Constants.ADDITION_FOUR_THIRTY_TICK, false);
		if(!tickStatus) {
			levelThirtyTick.setVisibility(View.INVISIBLE);
		}
	}

}
