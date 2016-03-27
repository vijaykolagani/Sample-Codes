package com.mathsforkids.addition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.R;
import com.mathsforkids.addition.above.AdditionAboveLevelSelect;
import com.mathsforkids.addition.four.AdditionFourLevelSelect;
import com.mathsforkids.addition.nine.AdditionNineLevelSelect;
import com.mathsforkids.addition.seven.AdditionSevenLevelSelect;

public class AdditionAgeSelection extends Activity implements OnClickListener {
	
	private TextView ageFour, ageSeven, ageNine, ageAbove;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.age_selection);
		
		ageFour = (TextView) this.findViewById(R.id.tv_zero_four);
		ageSeven = (TextView) this.findViewById(R.id.tv_four_seven);
		ageNine = (TextView) this.findViewById(R.id.tv_seven_nine);
		ageAbove = (TextView) this.findViewById(R.id.tv_nine_above);
		
		ageFour.setOnClickListener(this);
		ageSeven.setOnClickListener(this);
		ageNine.setOnClickListener(this);
		ageAbove.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_zero_four:
			Intent ageFourIntent = new Intent(AdditionAgeSelection.this, AdditionFourLevelSelect.class);
			startActivity(ageFourIntent);
			break;
		case R.id.tv_four_seven:
			Intent ageSevenIntent = new Intent(AdditionAgeSelection.this, AdditionSevenLevelSelect.class);
			startActivity(ageSevenIntent);
			break;
		case R.id.tv_seven_nine:
			Intent ageNineIntent = new Intent(AdditionAgeSelection.this, AdditionNineLevelSelect.class);
			startActivity(ageNineIntent);
			break;
		case R.id.tv_nine_above:
			Intent ageAboveIntent = new Intent(AdditionAgeSelection.this, AdditionAboveLevelSelect.class);
			startActivity(ageAboveIntent);
			break;
		default:
			break;
		}
	}
}
