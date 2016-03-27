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

public class ItemsFragmentTwo extends Fragment implements OnClickListener {

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
		View view = inflater.inflate(R.layout.collectable_two, container,
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
		status += prefs.getInt(Constants.HALLOWEEN_ONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHT_STARS, 0);
		if (status == 24) {
			itemOne.setImageResource(Constants.HALLOWEEN_ITEM_ONE_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_NINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_ELEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWELVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTEEN_STARS, 0);
		if (status == 24) {
			itemTwo.setImageResource(Constants.HALLOWEEN_ITEM_TWO_DRAWABLE);
		}
		
		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETEEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYFOUR_STARS, 0);
		if (status == 24) {
			itemThree.setImageResource(Constants.HALLOWEEN_ITEM_THREE_DRAWABLE);
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_TWENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYTWO_STARS, 0);
		if (status == 24) {
			itemFour.setImageResource(Constants.HALLOWEEN_ITEM_FOUR_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_THIRTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTY_STARS, 0);
		if (status == 24) {
			itemFive.setImageResource(Constants.HALLOWEEN_ITEM_FIVE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYEIGHT_STARS, 0);
		if (status == 24) {
			itemSix.setImageResource(Constants.HALLOWEEN_ITEM_SIX_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_FOURTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYSIX_STARS, 0);
		if (status == 24) {
			itemSeven.setImageResource(Constants.HALLOWEEN_ITEM_SEVEN_DRAWABLE);			
		}
		
		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_FIFTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYFOUR_STARS, 0);
		if (status == 24) {
			itemEight.setImageResource(Constants.HALLOWEEN_ITEM_EIGHT_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SIXTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYTWO_STARS, 0);
		if (status == 24) {
			itemNine.setImageResource(Constants.HALLOWEEN_ITEM_NINE_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYEIGHT_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_SEVENTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTY_STARS, 0);
		if (status == 24) {
			itemTen.setImageResource(Constants.HALLOWEEN_ITEM_TEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYSIX_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYSEVEN_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYEIGHT_STARS, 0);
		if (status == 24) {
			itemEleven.setImageResource(Constants.HALLOWEEN_ITEM_ELEVEN_DRAWABLE);			
		}

		status = 0;
		status += prefs.getInt(Constants.HALLOWEEN_EIGHTYNINE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETY_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYONE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYTWO_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYTHREE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYFOUR_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYFIVE_STARS, 0);
		status += prefs.getInt(Constants.HALLOWEEN_NINETYSIX_STARS, 0);
		if (status == 24) {
			itemTwelve.setImageResource(Constants.HALLOWEEN_ITEM_TWELVE_DRAWABLE);			
		}
		
		return view;
	}

	public void onClick(View v) {

	}
}