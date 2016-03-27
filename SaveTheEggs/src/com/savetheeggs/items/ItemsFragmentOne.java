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

public class ItemsFragmentOne extends Fragment implements OnClickListener {

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
		View view = inflater.inflate(R.layout.collectable_one, container,
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
		status += prefs.getInt(Constants.WATER_ONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_THREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHT_STARS, 0);
		if (status == 24) {
			itemOne.setImageResource(Constants.WATER_ITEM_ONE_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.WATER_NINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_TEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_ELEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWELVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTEEN_STARS, 0);
		if (status == 24) {
			itemTwo.setImageResource(Constants.WATER_ITEM_TWO_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.WATER_SEVENTEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETEEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTY_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYFOUR_STARS, 0);
		if (status == 24) {
			itemThree.setImageResource(Constants.WATER_ITEM_THREE_DRAWABLE);
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_TWENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.WATER_TWENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTY_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYTWO_STARS, 0);
		if (status == 24) {
			itemFour.setImageResource(Constants.WATER_ITEM_FOUR_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.WATER_THIRTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.WATER_THIRTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTY_STARS, 0);
		if (status == 24) {
			itemFive.setImageResource(Constants.WATER_ITEM_FIVE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_FOURTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_FOURTYEIGHT_STARS, 0);
		if (status == 24) {
			itemSix.setImageResource(Constants.WATER_ITEM_SIX_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_FOURTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTY_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYSIX_STARS, 0);
		if (status == 24) {
			itemSeven.setImageResource(Constants.WATER_ITEM_SEVEN_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.WATER_FIFTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.WATER_FIFTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTY_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYFOUR_STARS, 0);
		if (status == 24) {
			itemEight.setImageResource(Constants.WATER_ITEM_EIGHT_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_SIXTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.WATER_SIXTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTY_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYTWO_STARS, 0);
		if (status == 24) {
			itemNine.setImageResource(Constants.WATER_ITEM_NINE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_SEVENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.WATER_SEVENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTY_STARS, 0);
		if (status == 24) {
			itemTen.setImageResource(Constants.WATER_ITEM_TEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_EIGHTYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYSIX_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.WATER_EIGHTYEIGHT_STARS, 0);
		if (status == 24) {
			itemEleven.setImageResource(Constants.WATER_ITEM_ELEVEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.WATER_EIGHTYNINE_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETY_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYONE_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYTWO_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYTHREE_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYFOUR_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYFIVE_STARS, 0);
		status += prefs.getInt(Constants.WATER_NINETYSIX_STARS, 0);
		if (status == 24) {
			itemTwelve.setImageResource(Constants.WATER_ITEM_TWELVE_DRAWABLE);			
		}
		
		return view;
	}

	public void onClick(View v) {

	}
}