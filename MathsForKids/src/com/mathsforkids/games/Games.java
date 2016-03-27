package com.mathsforkids.games;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mathsforkids.R;
import com.mathsforkids.games.catcheggs.CatchTheEggs;
import com.mathsforkids.games.fillbasket.FillTheBasket;

public class Games extends Activity implements OnClickListener {

	private TextView catchEggs;
	private TextView fillBasket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		
		catchEggs = (TextView) this.findViewById(R.id.tv_catch_eggs);
		fillBasket = (TextView) this.findViewById(R.id.tv_fill_basket);
	
		catchEggs.setOnClickListener(this);
		fillBasket.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_catch_eggs:
			Intent catchEggsGame = new Intent(Games.this, CatchTheEggs.class);
			startActivity(catchEggsGame);
			break;
		case R.id.tv_fill_basket:
			Intent fillBasketGame = new Intent(Games.this, FillTheBasket.class);
			startActivity(fillBasketGame);
			break;
		default:
			break;
		}
	}
}
