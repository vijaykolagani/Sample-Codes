package com.savetheeggs.items;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.savetheeggs.Constants;
import com.savetheeggs.R;

public class ItemsFragmentThree extends Fragment implements OnClickListener {

	private ImageView itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix, itemSeven, itemEight,
						itemNine, itemTen, itemEleven, itemTwelve;
	private SharedPreferences prefs;
	private int status;

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
		View view = inflater.inflate(R.layout.collectable_three, container,
				false);
		prefs = getActivity().getSharedPreferences(Constants.PREFS,
				Context.MODE_PRIVATE);
		
		itemOne = (ImageView) view.findViewById(R.id.item_one);
		itemTwo = (ImageView) view.findViewById(R.id.item_two);
		itemThree = (ImageView) view.findViewById(R.id.item_three);
		itemFour = (ImageView) view.findViewById(R.id.item_four);
		itemFive = (ImageView) view.findViewById(R.id.item_five);
		itemSix = (ImageView) view.findViewById(R.id.item_six);
		itemSeven = (ImageView) view.findViewById(R.id.item_seven);
		itemEight = (ImageView) view.findViewById(R.id.item_eight);
		itemNine = (ImageView) view.findViewById(R.id.item_nine);
		itemTen = (ImageView) view.findViewById(R.id.item_ten);
		itemEleven = (ImageView) view.findViewById(R.id.item_eleven);
		itemTwelve = (ImageView) view.findViewById(R.id.item_twelve);
		
		status = 0;
		status += prefs.getInt(Constants.FOREST_ONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHT_STARS, 0);
		if (status == 24) {
			itemOne.setImageResource(Constants.FOREST_ITEM_ONE_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.FOREST_NINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_ELEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWELVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTEEN_STARS, 0);
		if (status == 24) {
			itemTwo.setImageResource(Constants.FOREST_ITEM_TWO_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.FOREST_SEVENTEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETEEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYFOUR_STARS, 0);
		if (status == 24) {
			itemThree.setImageResource(Constants.FOREST_ITEM_THREE_DRAWABLE);
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_TWENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.FOREST_TWENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYTWO_STARS, 0);
		if (status == 24) {
			itemFour.setImageResource(Constants.FOREST_ITEM_FOUR_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.FOREST_THIRTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.FOREST_THIRTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTY_STARS, 0);
		if (status == 24) {
			itemFive.setImageResource(Constants.FOREST_ITEM_FIVE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_FOURTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FOURTYEIGHT_STARS, 0);
		if (status == 24) {
			itemSix.setImageResource(Constants.FOREST_ITEM_SIX_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_FOURTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYSIX_STARS, 0);
		if (status == 24) {
			itemSeven.setImageResource(Constants.FOREST_ITEM_SEVEN_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.FOREST_FIFTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.FOREST_FIFTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYFOUR_STARS, 0);
		if (status == 24) {
			itemEight.setImageResource(Constants.FOREST_ITEM_EIGHT_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_SIXTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SIXTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYTWO_STARS, 0);
		if (status == 24) {
			itemNine.setImageResource(Constants.FOREST_ITEM_NINE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_SEVENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.FOREST_SEVENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTY_STARS, 0);
		if (status == 24) {
			itemTen.setImageResource(Constants.FOREST_ITEM_TEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_EIGHTYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYSIX_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.FOREST_EIGHTYEIGHT_STARS, 0);
		if (status == 24) {
			itemEleven.setImageResource(Constants.FOREST_ITEM_ELEVEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.FOREST_EIGHTYNINE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETY_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYONE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYTWO_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYTHREE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYFOUR_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYFIVE_STARS, 0);
		status += prefs.getInt(Constants.FOREST_NINETYSIX_STARS, 0);
		if (status == 24) {
			itemTwelve.setImageResource(Constants.FOREST_ITEM_TWELVE_DRAWABLE);			
		}
		
		return view;
	}

	public void onClick(View v) {

	}
}