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

public class LevelFragmentFour extends Fragment implements OnClickListener {

	private ImageView levelSeventyThree, levelSeventyFour, levelSeventyFive,
	levelSeventySix, levelSeventySeven, levelSeventyEight, levelSeventyNine,
	levelEighty, levelEightyOne, levelEightyTwo,
	levelEightyThree, levelEightyFour, levelEightyFive, levelEightySix,
	levelEightySeven, levelEightyEight, levelEightyNine, levelNinety,
	levelNinetyOne, levelNinetyTwo, levelNinetyThree, levelNinetyFour,
	levelNinetyFive, levelNinetySix;
	
	private TextView tvLevelSeventyThree, tvLevelSeventyFour, tvLevelSeventyFive,
	tvLevelSeventySix, tvLevelSeventySeven, tvLevelSeventyEight, tvLevelSeventyNine,
	tvLevelEighty, tvLevelEightyOne, tvLevelEightyTwo,
	tvLevelEightyThree, tvLevelEightyFour, tvLevelEightyFive, tvLevelEightySix,
	tvLevelEightySeven, tvLevelEightyEight, tvLevelEightyNine, tvLevelNinety,
	tvLevelNinetyOne, tvLevelNinetyTwo, tvLevelNinetyThree, tvLevelNinetyFour,
	tvLevelNinetyFive, tvLevelNinetySix;
	
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
		View view = inflater.inflate(R.layout.level_layout_four, container,
				false);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(getActivity(), R.raw.click, 1);
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		levelSeventyThree = (ImageView) view.findViewById(R.id.level_seventy_three);
		levelSeventyFour = (ImageView) view.findViewById(R.id.level_seventy_four);
		levelSeventyFive = (ImageView) view.findViewById(R.id.level_seventy_five);
		levelSeventySix = (ImageView) view.findViewById(R.id.level_seventy_six);
		levelSeventySeven = (ImageView) view.findViewById(R.id.level_seventy_seven);
		levelSeventyEight = (ImageView) view.findViewById(R.id.level_seventy_eight);
		levelSeventyNine = (ImageView) view.findViewById(R.id.level_seventy_nine);
		levelEighty = (ImageView) view.findViewById(R.id.level_eighty);
		levelEightyOne = (ImageView) view.findViewById(R.id.level_eighty_one);
		levelEightyTwo = (ImageView) view.findViewById(R.id.level_eighty_two);
		levelEightyThree = (ImageView) view.findViewById(R.id.level_eighty_three);
		levelEightyFour = (ImageView) view.findViewById(R.id.level_eighty_four);
		levelEightyFive = (ImageView) view.findViewById(R.id.level_eighty_five);
		levelEightySix = (ImageView) view.findViewById(R.id.level_eighty_six);
		levelEightySeven = (ImageView) view.findViewById(R.id.level_eighty_seven);
		levelEightyEight = (ImageView) view.findViewById(R.id.level_eighty_eight);
		levelEightyNine = (ImageView) view.findViewById(R.id.level_eighty_nine);
		levelNinety = (ImageView) view.findViewById(R.id.level_ninety);
		levelNinetyOne = (ImageView) view.findViewById(R.id.level_ninety_one);
		levelNinetyTwo = (ImageView) view.findViewById(R.id.level_ninety_two);
		levelNinetyThree = (ImageView) view.findViewById(R.id.level_ninety_three);
		levelNinetyFour = (ImageView) view.findViewById(R.id.level_ninety_four);
		levelNinetyFive = (ImageView) view.findViewById(R.id.level_ninety_five);
		levelNinetySix = (ImageView) view.findViewById(R.id.level_ninety_six);
		
		tvLevelSeventyThree = (TextView) view.findViewById(R.id.tv_level_seventy_three);
		tvLevelSeventyFour = (TextView) view.findViewById(R.id.tv_level_seventy_four);
		tvLevelSeventyFive = (TextView) view.findViewById(R.id.tv_level_seventy_five);
		tvLevelSeventySix = (TextView) view.findViewById(R.id.tv_level_seventy_six);
		tvLevelSeventySeven = (TextView) view.findViewById(R.id.tv_level_seventy_seven);
		tvLevelSeventyEight = (TextView) view.findViewById(R.id.tv_level_seventy_eight);
		tvLevelSeventyNine = (TextView) view.findViewById(R.id.tv_level_seventy_nine);
		tvLevelEighty = (TextView) view.findViewById(R.id.tv_level_eighty);
		tvLevelEightyOne = (TextView) view.findViewById(R.id.tv_level_eighty_one);
		tvLevelEightyTwo = (TextView) view.findViewById(R.id.tv_level_eighty_two);
		tvLevelEightyThree = (TextView) view.findViewById(R.id.tv_level_eighty_three);
		tvLevelEightyFour = (TextView) view.findViewById(R.id.tv_level_eighty_four);
		tvLevelEightyFive = (TextView) view.findViewById(R.id.tv_level_eighty_five);
		tvLevelEightySix = (TextView) view.findViewById(R.id.tv_level_eighty_six);
		tvLevelEightySeven = (TextView) view.findViewById(R.id.tv_level_eighty_seven);
		tvLevelEightyEight = (TextView) view.findViewById(R.id.tv_level_eighty_eight);
		tvLevelEightyNine = (TextView) view.findViewById(R.id.tv_level_eighty_nine);
		tvLevelNinety = (TextView) view.findViewById(R.id.tv_level_ninety);
		tvLevelNinetyOne = (TextView) view.findViewById(R.id.tv_level_ninety_one);
		tvLevelNinetyTwo = (TextView) view.findViewById(R.id.tv_level_ninety_two);
		tvLevelNinetyThree = (TextView) view.findViewById(R.id.tv_level_ninety_three);
		tvLevelNinetyFour = (TextView) view.findViewById(R.id.tv_level_ninety_four);
		tvLevelNinetyFive = (TextView) view.findViewById(R.id.tv_level_ninety_five);
		tvLevelNinetySix = (TextView) view.findViewById(R.id.tv_level_ninety_six);

		tvLevelSeventyThree.setTypeface(font);
		tvLevelSeventyFour.setTypeface(font);
		tvLevelSeventyFive.setTypeface(font);
		tvLevelSeventySix.setTypeface(font);
		tvLevelSeventySeven.setTypeface(font);
		tvLevelSeventyEight.setTypeface(font);
		tvLevelSeventyNine.setTypeface(font);
		tvLevelEighty.setTypeface(font);
		tvLevelEightyOne.setTypeface(font);
		tvLevelEightyTwo.setTypeface(font);
		tvLevelEightyThree.setTypeface(font);
		tvLevelEightyFour.setTypeface(font);
		tvLevelEightyFive.setTypeface(font);
		tvLevelEightySix.setTypeface(font);
		tvLevelEightySeven.setTypeface(font);
		tvLevelEightyEight.setTypeface(font);
		tvLevelEightyNine.setTypeface(font);
		tvLevelNinety.setTypeface(font);
		tvLevelNinetyOne.setTypeface(font);
		tvLevelNinetyTwo.setTypeface(font);
		tvLevelNinetyThree.setTypeface(font);
		tvLevelNinetyFour.setTypeface(font);
		tvLevelNinetyFive.setTypeface(font);
		tvLevelNinetySix.setTypeface(font);

		levelSeventyThree.setOnClickListener(this);
		levelSeventyFour.setOnClickListener(this);
		levelSeventyFive.setOnClickListener(this);
		levelSeventySix.setOnClickListener(this);
		levelSeventySeven.setOnClickListener(this);
		levelSeventyEight.setOnClickListener(this);
		levelSeventyNine.setOnClickListener(this);
		levelEighty.setOnClickListener(this);
		levelEightyOne.setOnClickListener(this);
		levelEightyTwo.setOnClickListener(this);
		levelEightyThree.setOnClickListener(this);
		levelEightyFour.setOnClickListener(this);
		levelEightyFive.setOnClickListener(this);
		levelEightySix.setOnClickListener(this);
		levelEightySeven.setOnClickListener(this);
		levelEightyEight.setOnClickListener(this);
		levelEightyNine.setOnClickListener(this);
		levelNinety.setOnClickListener(this);
		levelNinetyOne.setOnClickListener(this);
		levelNinetyTwo.setOnClickListener(this);
		levelNinetyThree.setOnClickListener(this);
		levelNinetyFour.setOnClickListener(this);
		levelNinetyFive.setOnClickListener(this);
		levelNinetySix.setOnClickListener(this);

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
		case R.id.level_seventy_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 73);
			break;
		case R.id.level_seventy_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 74);
			break;
		case R.id.level_seventy_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 75);
			break;
		case R.id.level_seventy_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 76);
			break;
		case R.id.level_seventy_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 77);
			break;
		case R.id.level_seventy_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 78);
			break;
		case R.id.level_seventy_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 79);
			break;
		case R.id.level_eighty:
			intent.putExtra(Constants.LEVEL_NUMBER, 80);
			break;
		case R.id.level_eighty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 81);
			break;
		case R.id.level_eighty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 82);
			break;
		case R.id.level_eighty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 83);
			break;
		case R.id.level_eighty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 84);
			break;
		case R.id.level_eighty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 85);
			break;
		case R.id.level_eighty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 86);
			break;
		case R.id.level_eighty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 87);
			break;
		case R.id.level_eighty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 88);
			break;
		case R.id.level_eighty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 89);
			break;
		case R.id.level_ninety:
			intent.putExtra(Constants.LEVEL_NUMBER, 90);
			break;
		case R.id.level_ninety_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 91);
			break;
		case R.id.level_ninety_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 92);
			break;
		case R.id.level_ninety_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 93);
			break;
		case R.id.level_ninety_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 94);
			break;
		case R.id.level_ninety_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 95);
			break;
		case R.id.level_ninety_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 96);
			break;
		}
		startActivity(intent);
		getActivity().finish();
	}

	public void loadLevelStatus() {
		switch (world) {
		case 0:
			status = prefs.getInt(Constants.WATER_SEVENTYTHREE_STARS, -1);
			setLevelStatus(levelSeventyThree, status);

			status = prefs.getInt(Constants.WATER_SEVENTYFOUR_STARS, -1);
			setLevelStatus(levelSeventyFour, status);

			status = prefs.getInt(Constants.WATER_SEVENTYFIVE_STARS, -1);
			setLevelStatus(levelSeventyFive, status);

			status = prefs.getInt(Constants.WATER_SEVENTYSIX_STARS, -1);
			setLevelStatus(levelSeventySix, status);

			status = prefs.getInt(Constants.WATER_SEVENTYSEVEN_STARS, -1);
			setLevelStatus(levelSeventySeven, status);

			status = prefs.getInt(Constants.WATER_SEVENTYEIGHT_STARS, -1);
			setLevelStatus(levelSeventyEight, status);

			status = prefs.getInt(Constants.WATER_SEVENTYNINE_STARS, -1);
			setLevelStatus(levelSeventyNine, status);

			status = prefs.getInt(Constants.WATER_EIGHTY_STARS, -1);
			setLevelStatus(levelEighty, status);

			status = prefs.getInt(Constants.WATER_EIGHTYONE_STARS, -1);
			setLevelStatus(levelEightyOne, status);

			status = prefs.getInt(Constants.WATER_EIGHTYTWO_STARS, -1);
			setLevelStatus(levelEightyTwo, status);

			status = prefs.getInt(Constants.WATER_EIGHTYTHREE_STARS, -1);
			setLevelStatus(levelEightyThree, status);

			status = prefs.getInt(Constants.WATER_EIGHTYFOUR_STARS, -1);
			setLevelStatus(levelEightyFour, status);

			status = prefs.getInt(Constants.WATER_EIGHTYFIVE_STARS, -1);
			setLevelStatus(levelEightyFive, status);

			status = prefs.getInt(Constants.WATER_EIGHTYSIX_STARS, -1);
			setLevelStatus(levelEightySix, status);

			status = prefs.getInt(Constants.WATER_EIGHTYSEVEN_STARS, -1);
			setLevelStatus(levelEightySeven, status);

			status = prefs.getInt(Constants.WATER_EIGHTYEIGHT_STARS, -1);
			setLevelStatus(levelEightyEight, status);

			status = prefs.getInt(Constants.WATER_EIGHTYNINE_STARS, -1);
			setLevelStatus(levelEightyNine, status);

			status = prefs.getInt(Constants.WATER_NINETY_STARS, -1);
			setLevelStatus(levelNinety, status);

			status = prefs.getInt(Constants.WATER_NINETYONE_STARS, -1);
			setLevelStatus(levelNinetyOne, status);

			status = prefs.getInt(Constants.WATER_NINETYTWO_STARS, -1);
			setLevelStatus(levelNinetyTwo, status);

			status = prefs.getInt(Constants.WATER_NINETYTHREE_STARS, -1);
			setLevelStatus(levelNinetyThree, status);

			status = prefs.getInt(Constants.WATER_NINETYFOUR_STARS, -1);
			setLevelStatus(levelNinetyFour, status);

			status = prefs.getInt(Constants.WATER_NINETYFIVE_STARS, -1);
			setLevelStatus(levelNinetyFive, status);

			status = prefs.getInt(Constants.WATER_NINETYSIX_STARS, -1);
			setLevelStatus(levelNinetySix, status);
			break;
		case 1:
			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYTHREE_STARS, -1);
			setLevelStatus(levelSeventyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYFOUR_STARS, -1);
			setLevelStatus(levelSeventyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYFIVE_STARS, -1);
			setLevelStatus(levelSeventyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYSIX_STARS, -1);
			setLevelStatus(levelSeventySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYSEVEN_STARS, -1);
			setLevelStatus(levelSeventySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYEIGHT_STARS, -1);
			setLevelStatus(levelSeventyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYNINE_STARS, -1);
			setLevelStatus(levelSeventyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTY_STARS, -1);
			setLevelStatus(levelEighty, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYONE_STARS, -1);
			setLevelStatus(levelEightyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYTWO_STARS, -1);
			setLevelStatus(levelEightyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYTHREE_STARS, -1);
			setLevelStatus(levelEightyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYFOUR_STARS, -1);
			setLevelStatus(levelEightyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYFIVE_STARS, -1);
			setLevelStatus(levelEightyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYSIX_STARS, -1);
			setLevelStatus(levelEightySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYSEVEN_STARS, -1);
			setLevelStatus(levelEightySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYEIGHT_STARS, -1);
			setLevelStatus(levelEightyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_EIGHTYNINE_STARS, -1);
			setLevelStatus(levelEightyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETY_STARS, -1);
			setLevelStatus(levelNinety, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYONE_STARS, -1);
			setLevelStatus(levelNinetyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYTWO_STARS, -1);
			setLevelStatus(levelNinetyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYTHREE_STARS, -1);
			setLevelStatus(levelNinetyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYFOUR_STARS, -1);
			setLevelStatus(levelNinetyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYFIVE_STARS, -1);
			setLevelStatus(levelNinetyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_NINETYSIX_STARS, -1);
			setLevelStatus(levelNinetySix, status);
			break;
		case 2:
			status = prefs.getInt(Constants.FOREST_SEVENTYTHREE_STARS, -1);
			setLevelStatus(levelSeventyThree, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYFOUR_STARS, -1);
			setLevelStatus(levelSeventyFour, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYFIVE_STARS, -1);
			setLevelStatus(levelSeventyFive, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYSIX_STARS, -1);
			setLevelStatus(levelSeventySix, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYSEVEN_STARS, -1);
			setLevelStatus(levelSeventySeven, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYEIGHT_STARS, -1);
			setLevelStatus(levelSeventyEight, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYNINE_STARS, -1);
			setLevelStatus(levelSeventyNine, status);

			status = prefs.getInt(Constants.FOREST_EIGHTY_STARS, -1);
			setLevelStatus(levelEighty, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYONE_STARS, -1);
			setLevelStatus(levelEightyOne, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYTWO_STARS, -1);
			setLevelStatus(levelEightyTwo, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYTHREE_STARS, -1);
			setLevelStatus(levelEightyThree, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYFOUR_STARS, -1);
			setLevelStatus(levelEightyFour, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYFIVE_STARS, -1);
			setLevelStatus(levelEightyFive, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYSIX_STARS, -1);
			setLevelStatus(levelEightySix, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYSEVEN_STARS, -1);
			setLevelStatus(levelEightySeven, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYEIGHT_STARS, -1);
			setLevelStatus(levelEightyEight, status);

			status = prefs.getInt(Constants.FOREST_EIGHTYNINE_STARS, -1);
			setLevelStatus(levelEightyNine, status);

			status = prefs.getInt(Constants.FOREST_NINETY_STARS, -1);
			setLevelStatus(levelNinety, status);

			status = prefs.getInt(Constants.FOREST_NINETYONE_STARS, -1);
			setLevelStatus(levelNinetyOne, status);

			status = prefs.getInt(Constants.FOREST_NINETYTWO_STARS, -1);
			setLevelStatus(levelNinetyTwo, status);

			status = prefs.getInt(Constants.FOREST_NINETYTHREE_STARS, -1);
			setLevelStatus(levelNinetyThree, status);

			status = prefs.getInt(Constants.FOREST_NINETYFOUR_STARS, -1);
			setLevelStatus(levelNinetyFour, status);

			status = prefs.getInt(Constants.FOREST_NINETYFIVE_STARS, -1);
			setLevelStatus(levelNinetyFive, status);

			status = prefs.getInt(Constants.FOREST_NINETYSIX_STARS, -1);
			setLevelStatus(levelNinetySix, status);
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
