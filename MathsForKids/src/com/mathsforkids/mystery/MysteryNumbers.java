
package com.mathsforkids.mystery;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.mathsforkids.R;

public class MysteryNumbers extends Activity implements OnClickListener {

	private TextView number;
	private TextView chances;
	private TextView message;
	private Random random;
	private Dialog dialog;
	private int mysteryNumber;
	private int chanceCount = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mystery_layout);
		
		dialog = new Dialog(MysteryNumbers.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		random = new Random();
		
		mysteryNumber = random.nextInt(100);
		
		number = (TextView) this.findViewById(R.id.value);
		chances = (TextView) this.findViewById(R.id.tv_chances);
		message = (TextView) this.findViewById(R.id.message);
		
		number.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		if (v.getId() == R.id.value) {
			dialog.setContentView(R.layout.keypad);
			final TextView textBox = (TextView) dialog.findViewById(R.id.text_box);
			TextView one = (TextView) dialog.findViewById(R.id.tv_one);
			TextView two = (TextView) dialog.findViewById(R.id.tv_two);
			TextView three = (TextView) dialog.findViewById(R.id.tv_three);
			TextView four = (TextView) dialog.findViewById(R.id.tv_four);
			TextView five = (TextView) dialog.findViewById(R.id.tv_five);
			TextView six = (TextView) dialog.findViewById(R.id.tv_six);
			TextView seven = (TextView) dialog.findViewById(R.id.tv_seven);
			TextView eight = (TextView) dialog.findViewById(R.id.tv_eight);
			TextView nine = (TextView) dialog.findViewById(R.id.tv_nine);
			TextView zero = (TextView) dialog.findViewById(R.id.tv_zero);
			TextView clear = (TextView) dialog.findViewById(R.id.tv_clear);
			TextView ok = (TextView) dialog.findViewById(R.id.tv_ok);
			
			one.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("1");
				}
			});
			
			two.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("2");
				}
			});
			
			three.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("3");
				}
			});
			
			four.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("4");
				}
			});
			
			five.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("5");
				}
			});
			
			six.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("6");
				}
			});
			
			seven.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("7");
				}
			});
			
			eight.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("8");
				}
			});
			
			nine.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("9");
				}
			});
			
			zero.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.append("0");
				}
			});
			
			clear.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					textBox.setText("");
				}
			});

			ok.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					if (!textBox.getText().equals("") && textBox.getText() != null) {
						if (textBox.getText().length() <= 3 && textBox.getText().length() > 0) {
							number.setText(textBox.getText());
							int no = Integer.parseInt(number.getText().toString());
							if (chanceCount <= 0) {
								message.setText("Your chances are over! Tap to play again!");	
								number.setText("?");
								chanceCount = 10;
								chances.setText("Chances : " + chanceCount);
								mysteryNumber = random.nextInt(100);
							} else if (no > mysteryNumber) {
								message.setText("Sorry! Guess is High!");
								chanceCount--;
								chances.setText("Chances : " + chanceCount);
							} else if (no < mysteryNumber) {
								message.setText("Sorry! Guess is Low!");
								chanceCount--;
								chances.setText("Chances : " + chanceCount);
							} else {
								message.setText("Congrats! Guess is Correct! Tap to play again!");
								number.setText("?");
								chanceCount = 10;
								chances.setText("Chances : " + chanceCount);
								mysteryNumber = random.nextInt(100);
							}													
						} else {
							message.setText("Sorry! Number is Too High!");
						}
					}
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}
}
