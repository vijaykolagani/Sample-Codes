package com.savetheeggs.help;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.savetheeggs.R;

public class HelpFragmentTwo extends Fragment {

	private TextView pointOne, pointTwo, pointThree, pointFour, pointFive;
	private Typeface font;


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
		View view = inflater.inflate(R.layout.help_two, container,
				false);
		
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		pointOne = (TextView) view.findViewById(R.id.point_one);
		pointTwo = (TextView) view.findViewById(R.id.point_two);
		pointThree = (TextView) view.findViewById(R.id.point_three);
		pointFour = (TextView) view.findViewById(R.id.point_four);
		pointFive = (TextView) view.findViewById(R.id.point_five);

		pointOne.setTypeface(font);
		pointTwo.setTypeface(font);
		pointThree.setTypeface(font);
		pointFour.setTypeface(font);
		pointFive.setTypeface(font);
		
		return view;
	}
}