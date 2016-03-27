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

public class LevelFragmentTwo extends Fragment implements OnClickListener {

	private ImageView levelTwentyFive, levelTwentySix, levelTwentySeven,
			levelTwentyEight, levelTwentyNine, levelThirty, levelThirtyOne,
			levelThirtyTwo, levelThirtyThree, levelThirtyFour, levelThirtyFive,
			levelThirtySix, levelThirtySeven, levelThirtyEight,
			levelThirtyNine, levelFourty, levelFourtyOne, levelFourtyTwo,
			levelFourtyThree, levelFourtyFour, levelFourtyFive, levelFourtySix,
			levelFourtySeven, levelFourtyEight;

	private TextView tvLevelTwentyFive, tvLevelTwentySix, tvLevelTwentySeven,
			tvLevelTwentyEight, tvLevelTwentyNine, tvLevelThirty,
			tvLevelThirtyOne, tvLevelThirtyTwo, tvLevelThirtyThree,
			tvLevelThirtyFour, tvLevelThirtyFive, tvLevelThirtySix,
			tvLevelThirtySeven, tvLevelThirtyEight, tvLevelThirtyNine,
			tvLevelFourty, tvLevelFourtyOne, tvLevelFourtyTwo,
			tvLevelFourtyThree, tvLevelFourtyFour, tvLevelFourtyFive,
			tvLevelFourtySix, tvLevelFourtySeven, tvLevelFourtyEight;

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
		View view = inflater.inflate(R.layout.level_layout_two, container,
				false);
		
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(getActivity(), R.raw.click, 1);
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		levelTwentyFive = (ImageView) view.findViewById(R.id.level_twenty_five);
		levelTwentySix = (ImageView) view.findViewById(R.id.level_twenty_six);
		levelTwentySeven = (ImageView) view
				.findViewById(R.id.level_twenty_seven);
		levelTwentyEight = (ImageView) view
				.findViewById(R.id.level_twenty_eight);
		levelTwentyNine = (ImageView) view.findViewById(R.id.level_twenty_nine);
		levelThirty = (ImageView) view.findViewById(R.id.level_thirty);
		levelThirtyOne = (ImageView) view.findViewById(R.id.level_thirty_one);
		levelThirtyTwo = (ImageView) view.findViewById(R.id.level_thirty_two);
		levelThirtyThree = (ImageView) view
				.findViewById(R.id.level_thirty_three);
		levelThirtyFour = (ImageView) view.findViewById(R.id.level_thirty_four);
		levelThirtyFive = (ImageView) view.findViewById(R.id.level_thirty_five);
		levelThirtySix = (ImageView) view.findViewById(R.id.level_thirty_six);
		levelThirtySeven = (ImageView) view
				.findViewById(R.id.level_thirty_seven);
		levelThirtyEight = (ImageView) view
				.findViewById(R.id.level_thirty_eight);
		levelThirtyNine = (ImageView) view.findViewById(R.id.level_thirty_nine);
		levelFourty = (ImageView) view.findViewById(R.id.level_fourty);
		levelFourtyOne = (ImageView) view.findViewById(R.id.level_fourty_one);
		levelFourtyTwo = (ImageView) view.findViewById(R.id.level_fourty_two);
		levelFourtyThree = (ImageView) view
				.findViewById(R.id.level_fourty_three);
		levelFourtyFour = (ImageView) view.findViewById(R.id.level_fourty_four);
		levelFourtyFive = (ImageView) view.findViewById(R.id.level_fourty_five);
		levelFourtySix = (ImageView) view.findViewById(R.id.level_fourty_six);
		levelFourtySeven = (ImageView) view
				.findViewById(R.id.level_fourty_seven);
		levelFourtyEight = (ImageView) view
				.findViewById(R.id.level_fourty_eight);
		
		tvLevelTwentyFive = (TextView) view.findViewById(R.id.tv_level_twenty_five);
		tvLevelTwentySix = (TextView) view.findViewById(R.id.tv_level_twenty_six);
		tvLevelTwentySeven = (TextView) view
				.findViewById(R.id.tv_level_twenty_seven);
		tvLevelTwentyEight = (TextView) view
				.findViewById(R.id.tv_level_twenty_eight);
		tvLevelTwentyNine = (TextView) view.findViewById(R.id.tv_level_twenty_nine);
		tvLevelThirty = (TextView) view.findViewById(R.id.tv_level_thirty);
		tvLevelThirtyOne = (TextView) view.findViewById(R.id.tv_level_thirty_one);
		tvLevelThirtyTwo = (TextView) view.findViewById(R.id.tv_level_thirty_two);
		tvLevelThirtyThree = (TextView) view
				.findViewById(R.id.tv_level_thirty_three);
		tvLevelThirtyFour = (TextView) view.findViewById(R.id.tv_level_thirty_four);
		tvLevelThirtyFive = (TextView) view.findViewById(R.id.tv_level_thirty_five);
		tvLevelThirtySix = (TextView) view.findViewById(R.id.tv_level_thirty_six);
		tvLevelThirtySeven = (TextView) view
				.findViewById(R.id.tv_level_thirty_seven);
		tvLevelThirtyEight = (TextView) view
				.findViewById(R.id.tv_level_thirty_eight);
		tvLevelThirtyNine = (TextView) view.findViewById(R.id.tv_level_thirty_nine);
		tvLevelFourty = (TextView) view.findViewById(R.id.tv_level_fourty);
		tvLevelFourtyOne = (TextView) view.findViewById(R.id.tv_level_fourty_one);
		tvLevelFourtyTwo = (TextView) view.findViewById(R.id.tv_level_fourty_two);
		tvLevelFourtyThree = (TextView) view
				.findViewById(R.id.tv_level_fourty_three);
		tvLevelFourtyFour = (TextView) view.findViewById(R.id.tv_level_fourty_four);
		tvLevelFourtyFive = (TextView) view.findViewById(R.id.tv_level_fourty_five);
		tvLevelFourtySix = (TextView) view.findViewById(R.id.tv_level_fourty_six);
		tvLevelFourtySeven = (TextView) view
				.findViewById(R.id.tv_level_fourty_seven);
		tvLevelFourtyEight = (TextView) view
				.findViewById(R.id.tv_level_fourty_eight);

		tvLevelTwentyFive.setTypeface(font);
		tvLevelTwentySix.setTypeface(font);
		tvLevelTwentySeven.setTypeface(font);
		tvLevelTwentyEight.setTypeface(font);
		tvLevelTwentyNine.setTypeface(font);
		tvLevelThirty.setTypeface(font);
		tvLevelThirtyOne.setTypeface(font);
		tvLevelThirtyTwo.setTypeface(font);
		tvLevelThirtyThree.setTypeface(font);
		tvLevelThirtyFour.setTypeface(font);
		tvLevelThirtyFive.setTypeface(font);
		tvLevelThirtySix.setTypeface(font);
		tvLevelThirtySeven.setTypeface(font);
		tvLevelThirtyEight.setTypeface(font);
		tvLevelThirtyNine.setTypeface(font);
		tvLevelFourty.setTypeface(font);
		tvLevelFourtyOne.setTypeface(font);
		tvLevelFourtyTwo.setTypeface(font);
		tvLevelFourtyThree.setTypeface(font);
		tvLevelFourtyFour.setTypeface(font);
		tvLevelFourtyFive.setTypeface(font);
		tvLevelFourtySix.setTypeface(font);
		tvLevelFourtySeven.setTypeface(font);
		tvLevelFourtyEight.setTypeface(font);

		levelTwentyFive.setOnClickListener(this);
		levelTwentySix.setOnClickListener(this);
		levelTwentySeven.setOnClickListener(this);
		levelTwentyEight.setOnClickListener(this);
		levelTwentyNine.setOnClickListener(this);
		levelThirty.setOnClickListener(this);
		levelThirtyOne.setOnClickListener(this);
		levelThirtyTwo.setOnClickListener(this);
		levelThirtyThree.setOnClickListener(this);
		levelThirtyFour.setOnClickListener(this);
		levelThirtyFive.setOnClickListener(this);
		levelThirtySix.setOnClickListener(this);
		levelThirtySeven.setOnClickListener(this);
		levelThirtyEight.setOnClickListener(this);
		levelThirtyNine.setOnClickListener(this);
		levelFourty.setOnClickListener(this);
		levelFourtyOne.setOnClickListener(this);
		levelFourtyTwo.setOnClickListener(this);
		levelFourtyThree.setOnClickListener(this);
		levelFourtyFour.setOnClickListener(this);
		levelFourtyFive.setOnClickListener(this);
		levelFourtySix.setOnClickListener(this);
		levelFourtySeven.setOnClickListener(this);
		levelFourtyEight.setOnClickListener(this);

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
		case R.id.level_twenty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 25);
			break;
		case R.id.level_twenty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 26);
			break;
		case R.id.level_twenty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 27);
			break;
		case R.id.level_twenty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 28);
			break;
		case R.id.level_twenty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 29);
			break;
		case R.id.level_thirty:
			intent.putExtra(Constants.LEVEL_NUMBER, 30);
			break;
		case R.id.level_thirty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 31);
			break;
		case R.id.level_thirty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 32);
			break;
		case R.id.level_thirty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 33);
			break;
		case R.id.level_thirty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 34);
			break;
		case R.id.level_thirty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 35);
			break;
		case R.id.level_thirty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 36);
			break;
		case R.id.level_thirty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 37);
			break;
		case R.id.level_thirty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 38);
			break;
		case R.id.level_thirty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 39);
			break;
		case R.id.level_fourty:
			intent.putExtra(Constants.LEVEL_NUMBER, 40);
			break;
		case R.id.level_fourty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 41);
			break;
		case R.id.level_fourty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 42);
			break;
		case R.id.level_fourty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 43);
			break;
		case R.id.level_fourty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 44);
			break;
		case R.id.level_fourty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 45);
			break;
		case R.id.level_fourty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 46);
			break;
		case R.id.level_fourty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 47);
			break;
		case R.id.level_fourty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 48);
			break;
		}
		startActivity(intent);
		getActivity().finish();
	}

	public void loadLevelStatus() {
		switch (world) {
		case 0:
			status = prefs.getInt(Constants.WATER_TWENTYFIVE_STARS, -1);
			setLevelStatus(levelTwentyFive, status);

			status = prefs.getInt(Constants.WATER_TWENTYSIX_STARS, -1);
			setLevelStatus(levelTwentySix, status);

			status = prefs.getInt(Constants.WATER_TWENTYSEVEN_STARS, -1);
			setLevelStatus(levelTwentySeven, status);

			status = prefs.getInt(Constants.WATER_TWENTYEIGHT_STARS, -1);
			setLevelStatus(levelTwentyEight, status);

			status = prefs.getInt(Constants.WATER_TWENTYNINE_STARS, -1);
			setLevelStatus(levelTwentyNine, status);

			status = prefs.getInt(Constants.WATER_THIRTY_STARS, -1);
			setLevelStatus(levelThirty, status);

			status = prefs.getInt(Constants.WATER_THIRTYONE_STARS, -1);
			setLevelStatus(levelThirtyOne, status);

			status = prefs.getInt(Constants.WATER_THIRTYTWO_STARS, -1);
			setLevelStatus(levelThirtyTwo, status);

			status = prefs.getInt(Constants.WATER_THIRTYTHREE_STARS, -1);
			setLevelStatus(levelThirtyThree, status);

			status = prefs.getInt(Constants.WATER_THIRTYFOUR_STARS, -1);
			setLevelStatus(levelThirtyFour, status);

			status = prefs.getInt(Constants.WATER_THIRTYFIVE_STARS, -1);
			setLevelStatus(levelThirtyFive, status);

			status = prefs.getInt(Constants.WATER_THIRTYSIX_STARS, -1);
			setLevelStatus(levelThirtySix, status);

			status = prefs.getInt(Constants.WATER_THIRTYSEVEN_STARS, -1);
			setLevelStatus(levelThirtySeven, status);

			status = prefs.getInt(Constants.WATER_THIRTYEIGHT_STARS, -1);
			setLevelStatus(levelThirtyEight, status);

			status = prefs.getInt(Constants.WATER_THIRTYNINE_STARS, -1);
			setLevelStatus(levelThirtyNine, status);

			status = prefs.getInt(Constants.WATER_FOURTY_STARS, -1);
			setLevelStatus(levelFourty, status);

			status = prefs.getInt(Constants.WATER_FOURTYONE_STARS, -1);
			setLevelStatus(levelFourtyOne, status);

			status = prefs.getInt(Constants.WATER_FOURTYTWO_STARS, -1);
			setLevelStatus(levelFourtyTwo, status);

			status = prefs.getInt(Constants.WATER_FOURTYTHREE_STARS, -1);
			setLevelStatus(levelFourtyThree, status);

			status = prefs.getInt(Constants.WATER_FOURTYFOUR_STARS, -1);
			setLevelStatus(levelFourtyFour, status);

			status = prefs.getInt(Constants.WATER_FOURTYFIVE_STARS, -1);
			setLevelStatus(levelFourtyFive, status);

			status = prefs.getInt(Constants.WATER_FOURTYSIX_STARS, -1);
			setLevelStatus(levelFourtySix, status);

			status = prefs.getInt(Constants.WATER_FOURTYSEVEN_STARS, -1);
			setLevelStatus(levelFourtySeven, status);

			status = prefs.getInt(Constants.WATER_FOURTYEIGHT_STARS, -1);
			setLevelStatus(levelFourtyEight, status);
			break;
		case 1:
			status = prefs.getInt(Constants.HALLOWEEN_TWENTYFIVE_STARS, -1);
			setLevelStatus(levelTwentyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYSIX_STARS, -1);
			setLevelStatus(levelTwentySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYSEVEN_STARS, -1);
			setLevelStatus(levelTwentySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYEIGHT_STARS, -1);
			setLevelStatus(levelTwentyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_TWENTYNINE_STARS, -1);
			setLevelStatus(levelTwentyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTY_STARS, -1);
			setLevelStatus(levelThirty, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYONE_STARS, -1);
			setLevelStatus(levelThirtyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYTWO_STARS, -1);
			setLevelStatus(levelThirtyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYTHREE_STARS, -1);
			setLevelStatus(levelThirtyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYFOUR_STARS, -1);
			setLevelStatus(levelThirtyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYFIVE_STARS, -1);
			setLevelStatus(levelThirtyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYSIX_STARS, -1);
			setLevelStatus(levelThirtySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYSEVEN_STARS, -1);
			setLevelStatus(levelThirtySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYEIGHT_STARS, -1);
			setLevelStatus(levelThirtyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_THIRTYNINE_STARS, -1);
			setLevelStatus(levelThirtyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTY_STARS, -1);
			setLevelStatus(levelFourty, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYONE_STARS, -1);
			setLevelStatus(levelFourtyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYTWO_STARS, -1);
			setLevelStatus(levelFourtyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYTHREE_STARS, -1);
			setLevelStatus(levelFourtyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYFOUR_STARS, -1);
			setLevelStatus(levelFourtyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYFIVE_STARS, -1);
			setLevelStatus(levelFourtyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYSIX_STARS, -1);
			setLevelStatus(levelFourtySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYSEVEN_STARS, -1);
			setLevelStatus(levelFourtySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_FOURTYEIGHT_STARS, -1);
			setLevelStatus(levelFourtyEight, status);
			break;
		case 2:
			status = prefs.getInt(Constants.FOREST_TWENTYFIVE_STARS, -1);
			setLevelStatus(levelTwentyFive, status);

			status = prefs.getInt(Constants.FOREST_TWENTYSIX_STARS, -1);
			setLevelStatus(levelTwentySix, status);

			status = prefs.getInt(Constants.FOREST_TWENTYSEVEN_STARS, -1);
			setLevelStatus(levelTwentySeven, status);

			status = prefs.getInt(Constants.FOREST_TWENTYEIGHT_STARS, -1);
			setLevelStatus(levelTwentyEight, status);

			status = prefs.getInt(Constants.FOREST_TWENTYNINE_STARS, -1);
			setLevelStatus(levelTwentyNine, status);

			status = prefs.getInt(Constants.FOREST_THIRTY_STARS, -1);
			setLevelStatus(levelThirty, status);

			status = prefs.getInt(Constants.FOREST_THIRTYONE_STARS, -1);
			setLevelStatus(levelThirtyOne, status);

			status = prefs.getInt(Constants.FOREST_THIRTYTWO_STARS, -1);
			setLevelStatus(levelThirtyTwo, status);

			status = prefs.getInt(Constants.FOREST_THIRTYTHREE_STARS, -1);
			setLevelStatus(levelThirtyThree, status);

			status = prefs.getInt(Constants.FOREST_THIRTYFOUR_STARS, -1);
			setLevelStatus(levelThirtyFour, status);

			status = prefs.getInt(Constants.FOREST_THIRTYFIVE_STARS, -1);
			setLevelStatus(levelThirtyFive, status);

			status = prefs.getInt(Constants.FOREST_THIRTYSIX_STARS, -1);
			setLevelStatus(levelThirtySix, status);

			status = prefs.getInt(Constants.FOREST_THIRTYSEVEN_STARS, -1);
			setLevelStatus(levelThirtySeven, status);

			status = prefs.getInt(Constants.FOREST_THIRTYEIGHT_STARS, -1);
			setLevelStatus(levelThirtyEight, status);

			status = prefs.getInt(Constants.FOREST_THIRTYNINE_STARS, -1);
			setLevelStatus(levelThirtyNine, status);

			status = prefs.getInt(Constants.FOREST_FOURTY_STARS, -1);
			setLevelStatus(levelFourty, status);

			status = prefs.getInt(Constants.FOREST_FOURTYONE_STARS, -1);
			setLevelStatus(levelFourtyOne, status);

			status = prefs.getInt(Constants.FOREST_FOURTYTWO_STARS, -1);
			setLevelStatus(levelFourtyTwo, status);

			status = prefs.getInt(Constants.FOREST_FOURTYTHREE_STARS, -1);
			setLevelStatus(levelFourtyThree, status);

			status = prefs.getInt(Constants.FOREST_FOURTYFOUR_STARS, -1);
			setLevelStatus(levelFourtyFour, status);

			status = prefs.getInt(Constants.FOREST_FOURTYFIVE_STARS, -1);
			setLevelStatus(levelFourtyFive, status);

			status = prefs.getInt(Constants.FOREST_FOURTYSIX_STARS, -1);
			setLevelStatus(levelFourtySix, status);

			status = prefs.getInt(Constants.FOREST_FOURTYSEVEN_STARS, -1);
			setLevelStatus(levelFourtySeven, status);

			status = prefs.getInt(Constants.FOREST_FOURTYEIGHT_STARS, -1);
			setLevelStatus(levelFourtyEight, status);
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
