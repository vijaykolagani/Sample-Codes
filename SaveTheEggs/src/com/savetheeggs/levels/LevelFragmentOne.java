package com.savetheeggs.levels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.savetheeggs.Constants;
import com.savetheeggs.R;
import com.savetheeggs.worlds.SaveTheEggsForest;
import com.savetheeggs.worlds.SaveTheEggsHalloween;
import com.savetheeggs.worlds.SaveTheEggsWater;

public class LevelFragmentOne extends Fragment implements OnClickListener {

	private ImageView levelOne, levelTwo, levelThree, levelFour, levelFive,
			levelSix, levelSeven, levelEight, levelNine, levelTen, levelEleven,
			levelTwelve, levelThirteen, levelFourteen, levelFifteen,
			levelSixteen, levelSeventeen, levelEighteen, levelNineteen,
			levelTwenty, levelTwentyOne, levelTwentyTwo, levelTwentyThree,
			levelTwentyFour;

	private TextView tvLevelOne, tvLevelTwo, tvLevelThree, tvLevelFour,
			tvLevelFive, tvLevelSix, tvLevelSeven, tvLevelEight, tvLevelNine,
			tvLevelTen, tvLevelEleven, tvLevelTwelve, tvLevelThirteen,
			tvLevelFourteen, tvLevelFifteen, tvLevelSixteen, tvLevelSeventeen,
			tvLevelEighteen, tvLevelNineteen, tvLevelTwenty, tvLevelTwentyOne,
			tvLevelTwentyTwo, tvLevelTwentyThree, tvLevelTwentyFour;

	private Intent intent;
	private Bitmap oneStar, twoStar, threeStar, locked, unLocked;
	private SharedPreferences prefs;
	private int status;
	private int world;
	private Typeface font;
	private SoundPool soundPool;
	private int click;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.level_layout_one, container,
				false);

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(getActivity(), R.raw.click, 1);
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");

		levelOne = (ImageView) view.findViewById(R.id.level_one);
		levelTwo = (ImageView) view.findViewById(R.id.level_two);
		levelThree = (ImageView) view.findViewById(R.id.level_three);
		levelFour = (ImageView) view.findViewById(R.id.level_four);
		levelFive = (ImageView) view.findViewById(R.id.level_five);
		levelSix = (ImageView) view.findViewById(R.id.level_six);
		levelSeven = (ImageView) view.findViewById(R.id.level_seven);
		levelEight = (ImageView) view.findViewById(R.id.level_eight);
		levelNine = (ImageView) view.findViewById(R.id.level_nine);
		levelTen = (ImageView) view.findViewById(R.id.level_ten);
		levelEleven = (ImageView) view.findViewById(R.id.level_eleven);
		levelTwelve = (ImageView) view.findViewById(R.id.level_twelve);
		levelThirteen = (ImageView) view.findViewById(R.id.level_thirteen);
		levelFourteen = (ImageView) view.findViewById(R.id.level_fourteen);
		levelFifteen = (ImageView) view.findViewById(R.id.level_fifteen);
		levelSixteen = (ImageView) view.findViewById(R.id.level_sixteen);
		levelSeventeen = (ImageView) view.findViewById(R.id.level_seventeen);
		levelEighteen = (ImageView) view.findViewById(R.id.level_eighteen);
		levelNineteen = (ImageView) view.findViewById(R.id.level_nineteen);
		levelTwenty = (ImageView) view.findViewById(R.id.level_twenty);
		levelTwentyOne = (ImageView) view.findViewById(R.id.level_twenty_one);
		levelTwentyTwo = (ImageView) view.findViewById(R.id.level_twenty_two);
		levelTwentyThree = (ImageView) view
				.findViewById(R.id.level_twenty_three);
		levelTwentyFour = (ImageView) view.findViewById(R.id.level_twenty_four);

		tvLevelOne = (TextView) view.findViewById(R.id.tv_level_one);
		tvLevelTwo = (TextView) view.findViewById(R.id.tv_level_two);
		tvLevelThree = (TextView) view.findViewById(R.id.tv_level_three);
		tvLevelFour = (TextView) view.findViewById(R.id.tv_level_four);
		tvLevelFive = (TextView) view.findViewById(R.id.tv_level_five);
		tvLevelSix = (TextView) view.findViewById(R.id.tv_level_six);
		tvLevelSeven = (TextView) view.findViewById(R.id.tv_level_seven);
		tvLevelEight = (TextView) view.findViewById(R.id.tv_level_eight);
		tvLevelNine = (TextView) view.findViewById(R.id.tv_level_nine);
		tvLevelTen = (TextView) view.findViewById(R.id.tv_level_ten);
		tvLevelEleven = (TextView) view.findViewById(R.id.tv_level_eleven);
		tvLevelTwelve = (TextView) view.findViewById(R.id.tv_level_twelve);
		tvLevelThirteen = (TextView) view.findViewById(R.id.tv_level_thirteen);
		tvLevelFourteen = (TextView) view.findViewById(R.id.tv_level_fourteen);
		tvLevelFifteen = (TextView) view.findViewById(R.id.tv_level_fifteen);
		tvLevelSixteen = (TextView) view.findViewById(R.id.tv_level_sixteen);
		tvLevelSeventeen = (TextView) view.findViewById(R.id.tv_level_seventeen);
		tvLevelEighteen = (TextView) view.findViewById(R.id.tv_level_eighteen);
		tvLevelNineteen = (TextView) view.findViewById(R.id.tv_level_nineteen);
		tvLevelTwenty = (TextView) view.findViewById(R.id.tv_level_twenty);
		tvLevelTwentyOne = (TextView) view.findViewById(R.id.tv_level_twenty_one);
		tvLevelTwentyTwo = (TextView) view.findViewById(R.id.tv_level_twenty_two);
		tvLevelTwentyThree = (TextView) view.findViewById(R.id.tv_level_twenty_three);
		tvLevelTwentyFour = (TextView) view.findViewById(R.id.tv_level_twenty_four);
		
		tvLevelOne.setTypeface(font);
		tvLevelTwo.setTypeface(font);
		tvLevelThree.setTypeface(font);
		tvLevelFour.setTypeface(font);
		tvLevelFive.setTypeface(font);
		tvLevelSix.setTypeface(font);
		tvLevelSeven.setTypeface(font);
		tvLevelEight.setTypeface(font);
		tvLevelNine.setTypeface(font);
		tvLevelTen.setTypeface(font);
		tvLevelEleven.setTypeface(font);
		tvLevelTwelve.setTypeface(font);
		tvLevelThirteen.setTypeface(font);
		tvLevelFourteen.setTypeface(font);
		tvLevelFifteen.setTypeface(font);
		tvLevelSixteen.setTypeface(font);
		tvLevelSeventeen.setTypeface(font);
		tvLevelEighteen.setTypeface(font);
		tvLevelNineteen.setTypeface(font);
		tvLevelTwenty.setTypeface(font);
		tvLevelTwentyOne.setTypeface(font);
		tvLevelTwentyTwo.setTypeface(font);
		tvLevelTwentyThree.setTypeface(font);
		tvLevelTwentyFour.setTypeface(font);
		
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

		Bundle b = getActivity().getIntent().getExtras();
		world = b.getInt(Constants.WORLD_NUMBER);

		switch (world) {
		case 0:
			intent = new Intent(getActivity(), SaveTheEggsWater.class);
			break;
		case 1:
			intent = new Intent(getActivity(), SaveTheEggsHalloween.class);
			break;
		case 2:
			intent = new Intent(getActivity(), SaveTheEggsForest.class);
			break;
		}

		prefs = getActivity().getSharedPreferences(Constants.PREFS,
				Context.MODE_PRIVATE);

		oneStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_one_star);
		twoStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_two_star);
		threeStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_three_star);
		locked = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_locked);
		unLocked = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_unlocked);

		loadLevelStatus();
		return view;
	}

	public void onClick(View v) {
		soundPool.play(click, 1.0f, 1.0f, 0, 0, 1.5f);
		switch (v.getId()) {
		case R.id.level_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 1);
			break;
		case R.id.level_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 2);
			break;
		case R.id.level_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 3);
			break;
		case R.id.level_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 4);
			break;
		case R.id.level_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 5);
			break;
		case R.id.level_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 6);
			break;
		case R.id.level_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 7);
			break;
		case R.id.level_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 8);
			break;
		case R.id.level_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 9);
			break;
		case R.id.level_ten:
			intent.putExtra(Constants.LEVEL_NUMBER, 10);
			break;
		case R.id.level_eleven:
			intent.putExtra(Constants.LEVEL_NUMBER, 11);
			break;
		case R.id.level_twelve:
			intent.putExtra(Constants.LEVEL_NUMBER, 12);
			break;
		case R.id.level_thirteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 13);
			break;
		case R.id.level_fourteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 14);
			break;
		case R.id.level_fifteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 15);
			break;
		case R.id.level_sixteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 16);
			break;
		case R.id.level_seventeen:
			intent.putExtra(Constants.LEVEL_NUMBER, 17);
			break;
		case R.id.level_eighteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 18);
			break;
		case R.id.level_nineteen:
			intent.putExtra(Constants.LEVEL_NUMBER, 19);
			break;
		case R.id.level_twenty:
			intent.putExtra(Constants.LEVEL_NUMBER, 20);
			break;
		case R.id.level_twenty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 21);
			break;
		case R.id.level_twenty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 22);
			break;
		case R.id.level_twenty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 23);
			break;
		case R.id.level_twenty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 24);
			break;
		}
		startActivity(intent);
		getActivity().finish();
	}

	public void loadLevelStatus() {
		switch (world) {
		case 0:
			status = prefs.getInt(Constants.WATER_ONE_STARS, -1);
			if (status > -1) {
				setLevelStatus(levelOne, status);
			}

			status = prefs.getInt(Constants.WATER_TWO_STARS, -1);
			setLevelStatus(levelTwo, status);

			status = prefs.getInt(Constants.WATER_THREE_STARS, -1);
			setLevelStatus(levelThree, status);

			status = prefs.getInt(Constants.WATER_FOUR_STARS, -1);
			setLevelStatus(levelFour, status);

			status = prefs.getInt(Constants.WATER_FIVE_STARS, -1);
			setLevelStatus(levelFive, status);

			status = prefs.getInt(Constants.WATER_SIX_STARS, -1);
			setLevelStatus(levelSix, status);

			status = prefs.getInt(Constants.WATER_SEVEN_STARS, -1);
			setLevelStatus(levelSeven, status);

			status = prefs.getInt(Constants.WATER_EIGHT_STARS, -1);
			setLevelStatus(levelEight, status);

			status = prefs.getInt(Constants.WATER_NINE_STARS, -1);
			setLevelStatus(levelNine, status);

			status = prefs.getInt(Constants.WATER_TEN_STARS, -1);
			setLevelStatus(levelTen, status);

			status = prefs.getInt(Constants.WATER_ELEVEN_STARS, -1);
			setLevelStatus(levelEleven, status);

			status = prefs.getInt(Constants.WATER_TWELVE_STARS, -1);
			setLevelStatus(levelTwelve, status);

			status = prefs.getInt(Constants.WATER_THIRTEEN_STARS, -1);
			setLevelStatus(levelThirteen, status);

			status = prefs.getInt(Constants.WATER_FOURTEEN_STARS, -1);
			setLevelStatus(levelFourteen, status);

			status = prefs.getInt(Constants.WATER_FIFTEEN_STARS, -1);
			setLevelStatus(levelFifteen, status);

			status = prefs.getInt(Constants.WATER_SIXTEEN_STARS, -1);
			setLevelStatus(levelSixteen, status);

			status = prefs.getInt(Constants.WATER_SEVENTEEN_STARS, -1);
			setLevelStatus(levelSeventeen, status);

			status = prefs.getInt(Constants.WATER_EIGHTEEN_STARS, -1);
			setLevelStatus(levelEighteen, status);

			status = prefs.getInt(Constants.WATER_NINETEEN_STARS, -1);
			setLevelStatus(levelNineteen, status);

			status = prefs.getInt(Constants.WATER_TWENTY_STARS, -1);
			setLevelStatus(levelTwenty, status);

			status = prefs.getInt(Constants.WATER_TWENTYONE_STARS, -1);
			setLevelStatus(levelTwentyOne, status);

			status = prefs.getInt(Constants.WATER_TWENTYTWO_STARS, -1);
			setLevelStatus(levelTwentyTwo, status);

			status = prefs.getInt(Constants.WATER_TWENTYTHREE_STARS, -1);
			setLevelStatus(levelTwentyThree, status);

			status = prefs.getInt(Constants.WATER_TWENTYFOUR_STARS, -1);
			setLevelStatus(levelTwentyFour, status);
			break;
		case 1:
			status = prefs.getInt(Constants.HALLOWEEN_ONE_STARS, -1);
			if (status > -1) {
				setLevelStatus(levelOne, status);
			}

			status = prefs.getInt(Constants.HALLOWEEN_TWO_STARS, -1);
			setLevelStatus(levelTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_THREE_STARS, -1);
			setLevelStatus(levelThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOUR_STARS, -1);
			setLevelStatus(levelFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIVE_STARS, -1);
			setLevelStatus(levelFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIX_STARS, -1);
			setLevelStatus(levelSix, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVEN_STARS, -1);
			setLevelStatus(levelSeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHT_STARS, -1);
			setLevelStatus(levelEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINE_STARS, -1);
			setLevelStatus(levelNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_TEN_STARS, -1);
			setLevelStatus(levelTen, status);

			status = prefs.getInt(Constants.HALLOWEEN_ELEVEN_STARS, -1);
			setLevelStatus(levelEleven, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWELVE_STARS, -1);
			setLevelStatus(levelTwelve, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTEEN_STARS, -1);
			setLevelStatus(levelThirteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTEEN_STARS, -1);
			setLevelStatus(levelFourteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTEEN_STARS, -1);
			setLevelStatus(levelFifteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTEEN_STARS, -1);
			setLevelStatus(levelSixteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTEEN_STARS, -1);
			setLevelStatus(levelSeventeen, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTEEN_STARS, -1);
			setLevelStatus(levelEighteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETEEN_STARS, -1);
			setLevelStatus(levelNineteen, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTY_STARS, -1);
			setLevelStatus(levelTwenty, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYONE_STARS, -1);
			setLevelStatus(levelTwentyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYTWO_STARS, -1);
			setLevelStatus(levelTwentyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYTHREE_STARS, -1);
			setLevelStatus(levelTwentyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYFOUR_STARS, -1);
			setLevelStatus(levelTwentyFour, status);
			break;
		case 2:
			status = prefs.getInt(Constants.FOREST_ONE_STARS, -1);
			if (status > -1) {
				setLevelStatus(levelOne, status);
			}

			status = prefs.getInt(Constants.FOREST_TWO_STARS, -1);
			setLevelStatus(levelTwo, status);

			status = prefs.getInt(Constants.FOREST_THREE_STARS, -1);
			setLevelStatus(levelThree, status);

			status = prefs.getInt(Constants.FOREST_FOUR_STARS, -1);
			setLevelStatus(levelFour, status);

			status = prefs.getInt(Constants.FOREST_FIVE_STARS, -1);
			setLevelStatus(levelFive, status);

			status = prefs.getInt(Constants.FOREST_SIX_STARS, -1);
			setLevelStatus(levelSix, status);

			status = prefs.getInt(Constants.FOREST_SEVEN_STARS, -1);
			setLevelStatus(levelSeven, status);

			status = prefs.getInt(Constants.FOREST_EIGHT_STARS, -1);
			setLevelStatus(levelEight, status);

			status = prefs.getInt(Constants.FOREST_NINE_STARS, -1);
			setLevelStatus(levelNine, status);

			status = prefs.getInt(Constants.FOREST_TEN_STARS, -1);
			setLevelStatus(levelTen, status);

			status = prefs.getInt(Constants.FOREST_ELEVEN_STARS, -1);
			setLevelStatus(levelEleven, status);

			status = prefs.getInt(Constants.FOREST_TWELVE_STARS, -1);
			setLevelStatus(levelTwelve, status);

			status = prefs.getInt(Constants.FOREST_THIRTEEN_STARS, -1);
			setLevelStatus(levelThirteen, status);

			status = prefs.getInt(Constants.FOREST_FOURTEEN_STARS, -1);
			setLevelStatus(levelFourteen, status);

			status = prefs.getInt(Constants.FOREST_FIFTEEN_STARS, -1);
			setLevelStatus(levelFifteen, status);

			status = prefs.getInt(Constants.FOREST_SIXTEEN_STARS, -1);
			setLevelStatus(levelSixteen, status);

			status = prefs.getInt(Constants.FOREST_SEVENTEEN_STARS, -1);
			setLevelStatus(levelSeventeen, status);

			status = prefs.getInt(Constants.FOREST_EIGHTEEN_STARS, -1);
			setLevelStatus(levelEighteen, status);

			status = prefs.getInt(Constants.FOREST_NINETEEN_STARS, -1);
			setLevelStatus(levelNineteen, status);

			status = prefs.getInt(Constants.FOREST_TWENTY_STARS, -1);
			setLevelStatus(levelTwenty, status);

			status = prefs.getInt(Constants.FOREST_TWENTYONE_STARS, -1);
			setLevelStatus(levelTwentyOne, status);

			status = prefs.getInt(Constants.FOREST_TWENTYTWO_STARS, -1);
			setLevelStatus(levelTwentyTwo, status);

			status = prefs.getInt(Constants.FOREST_TWENTYTHREE_STARS, -1);
			setLevelStatus(levelTwentyThree, status);

			status = prefs.getInt(Constants.FOREST_TWENTYFOUR_STARS, -1);
			setLevelStatus(levelTwentyFour, status);
			break;
		}
	}

	public void setLevelStatus(ImageView level, int status) {
		switch (status) {
		case -1:
			level.setImageBitmap(locked);
			level.setClickable(false);
			break;
		case 0:
			level.setImageBitmap(unLocked);
			level.setClickable(true);
			break;
		case 1:
			level.setImageBitmap(oneStar);
			level.setClickable(true);
			break;
		case 2:
			level.setImageBitmap(twoStar);
			level.setClickable(true);
			break;
		case 3:
			level.setImageBitmap(threeStar);
			level.setClickable(true);
			break;
		}
	}

}
