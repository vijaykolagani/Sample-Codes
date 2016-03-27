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

public class LevelFragmentThree extends Fragment implements OnClickListener {

	private ImageView levelFourtyNine, levelFifty, levelFiftyOne,
			levelFiftyTwo, levelFiftyThree, levelFiftyFour, levelFiftyFive,
			levelFiftySix, levelFiftySeven, levelFiftyEight,
			levelFiftyNine, levelSixty, levelSixtyOne, levelSixtyTwo,
			levelSixtyThree, levelSixtyFour, levelSixtyFive, levelSixtySix,
			levelSixtySeven, levelSixtyEight, levelSixtyNine, levelSeventy,
			levelSeventyOne, levelSeventyTwo;

	private TextView tvLevelFourtyNine, tvLevelFifty, tvLevelFiftyOne,
	tvLevelFiftyTwo, tvLevelFiftyThree, tvLevelFiftyFour, tvLevelFiftyFive,
	tvLevelFiftySix, tvLevelFiftySeven, tvLevelFiftyEight,
	tvLevelFiftyNine, tvLevelSixty, tvLevelSixtyOne, tvLevelSixtyTwo,
	tvLevelSixtyThree, tvLevelSixtyFour, tvLevelSixtyFive, tvLevelSixtySix,
	tvLevelSixtySeven, tvLevelSixtyEight, tvLevelSixtyNine, tvLevelSeventy,
	tvLevelSeventyOne, tvLevelSeventyTwo;
	
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
		View view = inflater.inflate(R.layout.level_layout_three, container,
				false);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(getActivity(), R.raw.click, 1);
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		levelFourtyNine = (ImageView) view.findViewById(R.id.level_fourty_nine);
		levelFifty = (ImageView) view.findViewById(R.id.level_fifty);
		levelFiftyOne = (ImageView) view.findViewById(R.id.level_fifty_one);
		levelFiftyTwo = (ImageView) view.findViewById(R.id.level_fifty_two);
		levelFiftyThree = (ImageView) view.findViewById(R.id.level_fifty_three);
		levelFiftyFour = (ImageView) view.findViewById(R.id.level_fifty_four);
		levelFiftyFive = (ImageView) view.findViewById(R.id.level_fifty_five);
		levelFiftySix = (ImageView) view.findViewById(R.id.level_fifty_six);
		levelFiftySeven = (ImageView) view.findViewById(R.id.level_fifty_seven);
		levelFiftyEight = (ImageView) view.findViewById(R.id.level_fifty_eight);
		levelFiftyNine = (ImageView) view.findViewById(R.id.level_fifty_nine);
		levelSixty = (ImageView) view.findViewById(R.id.level_sixty);
		levelSixtyOne = (ImageView) view.findViewById(R.id.level_sixty_one);
		levelSixtyTwo = (ImageView) view.findViewById(R.id.level_sixty_two);
		levelSixtyThree = (ImageView) view.findViewById(R.id.level_sixty_three);
		levelSixtyFour = (ImageView) view.findViewById(R.id.level_sixty_four);
		levelSixtyFive = (ImageView) view.findViewById(R.id.level_sixty_five);
		levelSixtySix = (ImageView) view.findViewById(R.id.level_sixty_six);
		levelSixtySeven = (ImageView) view.findViewById(R.id.level_sixty_seven);
		levelSixtyEight = (ImageView) view.findViewById(R.id.level_sixty_eight);
		levelSixtyNine = (ImageView) view.findViewById(R.id.level_sixty_nine);
		levelSeventy = (ImageView) view.findViewById(R.id.level_seventy);
		levelSeventyOne = (ImageView) view.findViewById(R.id.level_seventy_one);
		levelSeventyTwo = (ImageView) view.findViewById(R.id.level_seventy_two);

		tvLevelFourtyNine = (TextView) view.findViewById(R.id.tv_level_fourty_nine);
		tvLevelFifty = (TextView) view.findViewById(R.id.tv_level_fifty);
		tvLevelFiftyOne = (TextView) view.findViewById(R.id.tv_level_fifty_one);
		tvLevelFiftyTwo = (TextView) view.findViewById(R.id.tv_level_fifty_two);
		tvLevelFiftyThree = (TextView) view.findViewById(R.id.tv_level_fifty_three);
		tvLevelFiftyFour = (TextView) view.findViewById(R.id.tv_level_fifty_four);
		tvLevelFiftyFive = (TextView) view.findViewById(R.id.tv_level_fifty_five);
		tvLevelFiftySix = (TextView) view.findViewById(R.id.tv_level_fifty_six);
		tvLevelFiftySeven = (TextView) view.findViewById(R.id.tv_level_fifty_seven);
		tvLevelFiftyEight = (TextView) view.findViewById(R.id.tv_level_fifty_eight);
		tvLevelFiftyNine = (TextView) view.findViewById(R.id.tv_level_fifty_nine);
		tvLevelSixty = (TextView) view.findViewById(R.id.tv_level_sixty);
		tvLevelSixtyOne = (TextView) view.findViewById(R.id.tv_level_sixty_one);
		tvLevelSixtyTwo = (TextView) view.findViewById(R.id.tv_level_sixty_two);
		tvLevelSixtyThree = (TextView) view.findViewById(R.id.tv_level_sixty_three);
		tvLevelSixtyFour = (TextView) view.findViewById(R.id.tv_level_sixty_four);
		tvLevelSixtyFive = (TextView) view.findViewById(R.id.tv_level_sixty_five);
		tvLevelSixtySix = (TextView) view.findViewById(R.id.tv_level_sixty_six);
		tvLevelSixtySeven = (TextView) view.findViewById(R.id.tv_level_sixty_seven);
		tvLevelSixtyEight = (TextView) view.findViewById(R.id.tv_level_sixty_eight);
		tvLevelSixtyNine = (TextView) view.findViewById(R.id.tv_level_sixty_nine);
		tvLevelSeventy = (TextView) view.findViewById(R.id.tv_level_seventy);
		tvLevelSeventyOne = (TextView) view.findViewById(R.id.tv_level_seventy_one);
		tvLevelSeventyTwo = (TextView) view.findViewById(R.id.tv_level_seventy_two);

		tvLevelFourtyNine.setTypeface(font);
		tvLevelFifty.setTypeface(font);
		tvLevelFiftyOne.setTypeface(font);
		tvLevelFiftyTwo.setTypeface(font);
		tvLevelFiftyThree.setTypeface(font);
		tvLevelFiftyFour.setTypeface(font);
		tvLevelFiftyFive.setTypeface(font);
		tvLevelFiftySix.setTypeface(font);
		tvLevelFiftySeven.setTypeface(font);
		tvLevelFiftyEight.setTypeface(font);
		tvLevelFiftyNine.setTypeface(font);
		tvLevelSixty.setTypeface(font);
		tvLevelSixtyOne.setTypeface(font);
		tvLevelSixtyTwo.setTypeface(font);
		tvLevelSixtyThree.setTypeface(font);
		tvLevelSixtyFour.setTypeface(font);
		tvLevelSixtyFive.setTypeface(font);
		tvLevelSixtySix.setTypeface(font);
		tvLevelSixtySeven.setTypeface(font);
		tvLevelSixtyEight.setTypeface(font);
		tvLevelSixtyNine.setTypeface(font);
		tvLevelSeventy.setTypeface(font);
		tvLevelSeventyOne.setTypeface(font);
		tvLevelSeventyTwo.setTypeface(font);
		
		levelFourtyNine.setOnClickListener(this);
		levelFifty.setOnClickListener(this);
		levelFiftyOne.setOnClickListener(this);
		levelFiftyTwo.setOnClickListener(this);
		levelFiftyThree.setOnClickListener(this);
		levelFiftyFour.setOnClickListener(this);
		levelFiftyFive.setOnClickListener(this);
		levelFiftySix.setOnClickListener(this);
		levelFiftySeven.setOnClickListener(this);
		levelFiftyEight.setOnClickListener(this);
		levelFiftyNine.setOnClickListener(this);
		levelSixty.setOnClickListener(this);
		levelSixtyOne.setOnClickListener(this);
		levelSixtyTwo.setOnClickListener(this);
		levelSixtyThree.setOnClickListener(this);
		levelSixtyFour.setOnClickListener(this);
		levelSixtyFive.setOnClickListener(this);
		levelSixtySix.setOnClickListener(this);
		levelSixtySeven.setOnClickListener(this);
		levelSixtyEight.setOnClickListener(this);
		levelSixtyNine.setOnClickListener(this);
		levelSeventy.setOnClickListener(this);
		levelSeventyOne.setOnClickListener(this);
		levelSeventyTwo.setOnClickListener(this);

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
		case R.id.level_fourty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 49);
			break;
		case R.id.level_fifty:
			intent.putExtra(Constants.LEVEL_NUMBER, 50);
			break;
		case R.id.level_fifty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 51);
			break;
		case R.id.level_fifty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 52);
			break;
		case R.id.level_fifty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 53);
			break;
		case R.id.level_fifty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 54);
			break;
		case R.id.level_fifty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 55);
			break;
		case R.id.level_fifty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 56);
			break;
		case R.id.level_fifty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 57);
			break;
		case R.id.level_fifty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 58);
			break;
		case R.id.level_fifty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 59);
			break;
		case R.id.level_sixty:
			intent.putExtra(Constants.LEVEL_NUMBER, 60);
			break;
		case R.id.level_sixty_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 61);
			break;
		case R.id.level_sixty_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 62);
			break;
		case R.id.level_sixty_three:
			intent.putExtra(Constants.LEVEL_NUMBER, 63);
			break;
		case R.id.level_sixty_four:
			intent.putExtra(Constants.LEVEL_NUMBER, 64);
			break;
		case R.id.level_sixty_five:
			intent.putExtra(Constants.LEVEL_NUMBER, 65);
			break;
		case R.id.level_sixty_six:
			intent.putExtra(Constants.LEVEL_NUMBER, 66);
			break;
		case R.id.level_sixty_seven:
			intent.putExtra(Constants.LEVEL_NUMBER, 67);
			break;
		case R.id.level_sixty_eight:
			intent.putExtra(Constants.LEVEL_NUMBER, 68);
			break;
		case R.id.level_sixty_nine:
			intent.putExtra(Constants.LEVEL_NUMBER, 69);
			break;
		case R.id.level_seventy:
			intent.putExtra(Constants.LEVEL_NUMBER, 70);
			break;
		case R.id.level_seventy_one:
			intent.putExtra(Constants.LEVEL_NUMBER, 71);
			break;
		case R.id.level_seventy_two:
			intent.putExtra(Constants.LEVEL_NUMBER, 72);
			break;
		}
		startActivity(intent);
		getActivity().finish();
	}

	public void loadLevelStatus() {
		switch (world) {
		case 0:
			status = prefs.getInt(Constants.WATER_FOURTYNINE_STARS, -1);
			setLevelStatus(levelFourtyNine, status);

			status = prefs.getInt(Constants.WATER_FIFTY_STARS, -1);
			setLevelStatus(levelFifty, status);

			status = prefs.getInt(Constants.WATER_FIFTYONE_STARS, -1);
			setLevelStatus(levelFiftyOne, status);

			status = prefs.getInt(Constants.WATER_FIFTYTWO_STARS, -1);
			setLevelStatus(levelFiftyTwo, status);

			status = prefs.getInt(Constants.WATER_FIFTYTHREE_STARS, -1);
			setLevelStatus(levelFiftyThree, status);

			status = prefs.getInt(Constants.WATER_FIFTYFOUR_STARS, -1);
			setLevelStatus(levelFiftyFour, status);

			status = prefs.getInt(Constants.WATER_FIFTYFIVE_STARS, -1);
			setLevelStatus(levelFiftyFive, status);

			status = prefs.getInt(Constants.WATER_FIFTYSIX_STARS, -1);
			setLevelStatus(levelFiftySix, status);

			status = prefs.getInt(Constants.WATER_FIFTYSEVEN_STARS, -1);
			setLevelStatus(levelFiftySeven, status);

			status = prefs.getInt(Constants.WATER_FIFTYEIGHT_STARS, -1);
			setLevelStatus(levelFiftyEight, status);

			status = prefs.getInt(Constants.WATER_FIFTYNINE_STARS, -1);
			setLevelStatus(levelFiftyNine, status);

			status = prefs.getInt(Constants.WATER_SIXTY_STARS, -1);
			setLevelStatus(levelSixty, status);

			status = prefs.getInt(Constants.WATER_SIXTYONE_STARS, -1);
			setLevelStatus(levelSixtyOne, status);

			status = prefs.getInt(Constants.WATER_SIXTYTWO_STARS, -1);
			setLevelStatus(levelSixtyTwo, status);

			status = prefs.getInt(Constants.WATER_SIXTYTHREE_STARS, -1);
			setLevelStatus(levelSixtyThree, status);

			status = prefs.getInt(Constants.WATER_SIXTYFOUR_STARS, -1);
			setLevelStatus(levelSixtyFour, status);

			status = prefs.getInt(Constants.WATER_SIXTYFIVE_STARS, -1);
			setLevelStatus(levelSixtyFive, status);

			status = prefs.getInt(Constants.WATER_SIXTYSIX_STARS, -1);
			setLevelStatus(levelSixtySix, status);

			status = prefs.getInt(Constants.WATER_SIXTYSEVEN_STARS, -1);
			setLevelStatus(levelSixtySeven, status);

			status = prefs.getInt(Constants.WATER_SIXTYEIGHT_STARS, -1);
			setLevelStatus(levelSixtyEight, status);

			status = prefs.getInt(Constants.WATER_SIXTYNINE_STARS, -1);
			setLevelStatus(levelSixtyNine, status);

			status = prefs.getInt(Constants.WATER_SEVENTY_STARS, -1);
			setLevelStatus(levelSeventy, status);

			status = prefs.getInt(Constants.WATER_SEVENTYONE_STARS, -1);
			setLevelStatus(levelSeventyOne, status);

			status = prefs.getInt(Constants.WATER_SEVENTYTWO_STARS, -1);
			setLevelStatus(levelSeventyTwo, status);
			break;
		case 1:
			status = prefs.getInt(Constants.HALLOWEEN_FOURTYNINE_STARS, -1);
			setLevelStatus(levelFourtyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTY_STARS, -1);
			setLevelStatus(levelFifty, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYONE_STARS, -1);
			setLevelStatus(levelFiftyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYTWO_STARS, -1);
			setLevelStatus(levelFiftyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYTHREE_STARS, -1);
			setLevelStatus(levelFiftyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYFOUR_STARS, -1);
			setLevelStatus(levelFiftyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYFIVE_STARS, -1);
			setLevelStatus(levelFiftyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYSIX_STARS, -1);
			setLevelStatus(levelFiftySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYSEVEN_STARS, -1);
			setLevelStatus(levelFiftySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYEIGHT_STARS, -1);
			setLevelStatus(levelFiftyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_FIFTYNINE_STARS, -1);
			setLevelStatus(levelFiftyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTY_STARS, -1);
			setLevelStatus(levelSixty, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYONE_STARS, -1);
			setLevelStatus(levelSixtyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYTWO_STARS, -1);
			setLevelStatus(levelSixtyTwo, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYTHREE_STARS, -1);
			setLevelStatus(levelSixtyThree, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYFOUR_STARS, -1);
			setLevelStatus(levelSixtyFour, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYFIVE_STARS, -1);
			setLevelStatus(levelSixtyFive, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYSIX_STARS, -1);
			setLevelStatus(levelSixtySix, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYSEVEN_STARS, -1);
			setLevelStatus(levelSixtySeven, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYEIGHT_STARS, -1);
			setLevelStatus(levelSixtyEight, status);

			status = prefs.getInt(Constants.HALLOWEEN_SIXTYNINE_STARS, -1);
			setLevelStatus(levelSixtyNine, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTY_STARS, -1);
			setLevelStatus(levelSeventy, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYONE_STARS, -1);
			setLevelStatus(levelSeventyOne, status);

			status = prefs.getInt(Constants.HALLOWEEN_SEVENTYTWO_STARS, -1);
			setLevelStatus(levelSeventyTwo, status);
			break;
		case 2:
			status = prefs.getInt(Constants.FOREST_FOURTYNINE_STARS, -1);
			setLevelStatus(levelFourtyNine, status);

			status = prefs.getInt(Constants.FOREST_FIFTY_STARS, -1);
			setLevelStatus(levelFifty, status);

			status = prefs.getInt(Constants.FOREST_FIFTYONE_STARS, -1);
			setLevelStatus(levelFiftyOne, status);

			status = prefs.getInt(Constants.FOREST_FIFTYTWO_STARS, -1);
			setLevelStatus(levelFiftyTwo, status);

			status = prefs.getInt(Constants.FOREST_FIFTYTHREE_STARS, -1);
			setLevelStatus(levelFiftyThree, status);

			status = prefs.getInt(Constants.FOREST_FIFTYFOUR_STARS, -1);
			setLevelStatus(levelFiftyFour, status);

			status = prefs.getInt(Constants.FOREST_FIFTYFIVE_STARS, -1);
			setLevelStatus(levelFiftyFive, status);

			status = prefs.getInt(Constants.FOREST_FIFTYSIX_STARS, -1);
			setLevelStatus(levelFiftySix, status);

			status = prefs.getInt(Constants.FOREST_FIFTYSEVEN_STARS, -1);
			setLevelStatus(levelFiftySeven, status);

			status = prefs.getInt(Constants.FOREST_FIFTYEIGHT_STARS, -1);
			setLevelStatus(levelFiftyEight, status);

			status = prefs.getInt(Constants.FOREST_FIFTYNINE_STARS, -1);
			setLevelStatus(levelFiftyNine, status);

			status = prefs.getInt(Constants.FOREST_SIXTY_STARS, -1);
			setLevelStatus(levelSixty, status);

			status = prefs.getInt(Constants.FOREST_SIXTYONE_STARS, -1);
			setLevelStatus(levelSixtyOne, status);

			status = prefs.getInt(Constants.FOREST_SIXTYTWO_STARS, -1);
			setLevelStatus(levelSixtyTwo, status);

			status = prefs.getInt(Constants.FOREST_SIXTYTHREE_STARS, -1);
			setLevelStatus(levelSixtyThree, status);

			status = prefs.getInt(Constants.FOREST_SIXTYFOUR_STARS, -1);
			setLevelStatus(levelSixtyFour, status);

			status = prefs.getInt(Constants.FOREST_SIXTYFIVE_STARS, -1);
			setLevelStatus(levelSixtyFive, status);

			status = prefs.getInt(Constants.FOREST_SIXTYSIX_STARS, -1);
			setLevelStatus(levelSixtySix, status);

			status = prefs.getInt(Constants.FOREST_SIXTYSEVEN_STARS, -1);
			setLevelStatus(levelSixtySeven, status);

			status = prefs.getInt(Constants.FOREST_SIXTYEIGHT_STARS, -1);
			setLevelStatus(levelSixtyEight, status);

			status = prefs.getInt(Constants.FOREST_SIXTYNINE_STARS, -1);
			setLevelStatus(levelSixtyNine, status);

			status = prefs.getInt(Constants.FOREST_SEVENTY_STARS, -1);
			setLevelStatus(levelSeventy, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYONE_STARS, -1);
			setLevelStatus(levelSeventyOne, status);

			status = prefs.getInt(Constants.FOREST_SEVENTYTWO_STARS, -1);
			setLevelStatus(levelSeventyTwo, status);
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
