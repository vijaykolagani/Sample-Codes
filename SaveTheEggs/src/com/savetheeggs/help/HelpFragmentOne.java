package com.savetheeggs.help;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.savetheeggs.R;

public class HelpFragmentOne extends Fragment {

	private TextView helpOne;
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
		View view = inflater.inflate(R.layout.help_one, container,
				false);
		
		font = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		helpOne = (TextView) view.findViewById(R.id.help_one);

		helpOne.setTypeface(font);
		
		return view;
	}
}
