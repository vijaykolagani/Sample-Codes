package com.savetheeggs.worlds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.savetheeggs.levels.LevelViewPager;

public class WorldFragmentTwo extends Fragment implements OnClickListener {

	private ImageView world;
	private TextView score, levels;
	private Intent intent;
	private SharedPreferences prefs;
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
		View view = inflater.inflate(R.layout.world_layout_two, container,
				false);

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
		click = soundPool.load(getActivity(), R.raw.click, 1);
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		world = (ImageView) view.findViewById(R.id.halloween_world);
		score = (TextView) view.findViewById(R.id.halloween_score);
		levels = (TextView) view.findViewById(R.id.halloween_levels);
		
		score.setTypeface(font);
		levels.setTypeface(font);
		
		intent = new Intent(getActivity(), LevelViewPager.class);
		intent.putExtra(Constants.WORLD_NUMBER, Constants.HALLOWEEN_GAME_MODE);
		
		prefs = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
		loadWorldStatus();
		
		world.setOnClickListener(this);
		
		return view;
	}

	public void onClick(View v) {
		soundPool.play(click, 1.0f, 1.0f, 0, 0, 1.5f);
		switch (v.getId()) {
		case R.id.halloween_world:
			startActivity(intent);
			getActivity().finish();
			break;
		}
	}
	
	
	public void loadWorldStatus() {
		int savedLevels = prefs.getInt(Constants.HALLOWEEN_WORLD_LEVELS, 0);
		levels.setText(savedLevels + " / 96");
		
		int savedScore = 0;
		for (int i = 1; i <= savedLevels; i++) {
			savedScore += i * 100;
		}
		score.setText("" + savedScore);		
	}
}
