package com.onetaxi.taxiplace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Caldroid extends FragmentActivity {
	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	SharedPreferences pref;
	Bundle extras;
	private void setCustomResourceForDates() {
		Calendar cal = Calendar.getInstance();
		
		// Min date is last 7 days
		cal.add(Calendar.DATE, 0);
		Date blueDate = cal.getTime();

		// Max date is next 7 days
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 16);
		Date greenDate = cal.getTime();



		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.blue,
					blueDate);

			caldroidFragment.setTextColorForDate(R.color.white, blueDate);

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caldroid);
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		extras = getIntent().getExtras();
		pref = this.getSharedPreferences(
				"com.onetaxi.taxiplace", Context.MODE_PRIVATE);
		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
		//		 caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();

			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
			args.putInt(CaldroidFragment.MIN_DATE, cal.get(Calendar.DATE));
			args.putBoolean(caldroidFragment.DISABLE_DATES, true);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			Calendar cal1 = Calendar.getInstance();

			// Min date is last 7 days
			cal1.add(Calendar.DATE, 0);
			Date presentDate = cal1.getTime();
			cal1.add(Calendar.DATE, 60);
			Date maxDate = cal1.getTime();
			caldroidFragment.setArguments(args);
			caldroidFragment.setMaxDate(maxDate);
			caldroidFragment.setMinDate(presentDate);
			
		}

		setCustomResourceForDates();

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				/*Toast.makeText(getApplicationContext(), formatter.format(date),
								Toast.LENGTH_SHORT).show();*/
				Calendar cal = Calendar.getInstance();

				// Min date is last 7 days
				cal.add(Calendar.DATE, 0);
				Date presentDate = cal.getTime();
				if(caldroidFragment != null){
					caldroidFragment.setBackgroundResourceForDate(R.color.white,
							presentDate);
					caldroidFragment.setTextColorForDate(R.color.green, presentDate);
					caldroidFragment.setBackgroundResourceForDate(R.color.blue,
							date);
					caldroidFragment.setTextColorForDate(R.color.white, date);
					caldroidFragment.refreshView();
					if(extras.getInt("type") == 1)
						pref.edit().putString("dateoneway", date.toString()).commit();
					else
						pref.edit().putString("datereturn", date.toString()).commit();
					Intent intent = new Intent(Caldroid.this, MainActivity.class);
					startActivity(intent);
					overridePendingTransition(
							R.anim.push_down_out,
							R.anim.push_down_in);
					finish();

				}

			}

			@Override
			public void onChangeMonth(int month, int year) {
				String text = "month: " + month + " year: " + year;
				/*Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_SHORT).show();*/
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				/*Toast.makeText(getApplicationContext(),
								"Long click " + formatter.format(date),
								Toast.LENGTH_SHORT).show();*/
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
					/*Toast.makeText(getApplicationContext(),
									"Caldroid view is created", Toast.LENGTH_SHORT)
									.show();*/
				}
			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);

	}

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent intent = new Intent(Caldroid.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(
					R.anim.push_down_out,
					R.anim.push_down_in);
			finish();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Caldroid.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(
				R.anim.push_down_out,
				R.anim.push_down_in);
		finish();
		super.onBackPressed();
	}

}
