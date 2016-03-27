package com.onetaxi.taxiplace;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.onetaxi.slidingmenu.adapter.SearchAdapter;

public class SearchTaxi extends Activity {

	ListView lvSearch;
	TextView tvTimeSort,tvCostSort;

	ArrayList<String> alTime = new ArrayList<String>();
	ArrayList<String> alCost = new ArrayList<String>();
	ArrayList<String> alTravelsName = new ArrayList<String>();
	ArrayList<String> alEta = new ArrayList<String>();
	ArrayList<String> alCarType = new ArrayList<String>();
	ArrayList<String> alSeats = new ArrayList<String>();
	int[] images = {R.drawable.ic_home};

	int costClick = 0,timeClick = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_taxi);
		initViews();

	}

	void initViews(){
		lvSearch = (ListView) findViewById(R.id.lv_search);
		alTime.add("8:30 pm - 7:30 am");
		alTime.add("9:10 pm - 6:30 am");
		alTime.add("7:20 pm - 4:30 am");
		alTime.add("5:10 pm - 8:30 am");
		alTime.add("2:10 pm - 1:30 am");
		alTime.add("3:10 pm - 2:30 am");
		alTime.add("12:20 pm - 4:30 am");
		alTime.add("11:10 pm - 8:30 am");
		alTime.add("8:10 pm - 1:30 am");
		alTime.add("3:10 pm - 2:30 am");


		alCost.add("5000");
		alCost.add("4000");
		alCost.add("10000");
		alCost.add("8000");
		alCost.add("3000");
		alCost.add("2000");
		alCost.add("6000");
		alCost.add("1000");
		alCost.add("7000");
		alCost.add("9000");

		alTravelsName.add("Morning Star");
		alTravelsName.add("Kesineni ");
		alTravelsName.add("Orange Travels");
		alTravelsName.add("Ytragenie ");
		alTravelsName.add("KVR Travels");
		alTravelsName.add("KCR Travels");
		alTravelsName.add("Sai Travels");
		alTravelsName.add("SVR ");
		alTravelsName.add("Iapple Travels");
		alTravelsName.add("KCR Travels");

		alEta.add("11.20 hours");
		alEta.add("11 hours");
		alEta.add("11.20 hours");
		alEta.add("11 hours");
		alEta.add("11.20 hours");
		alEta.add("9 hours");
		alEta.add("4.20 hours");
		alEta.add("7 hours");
		alEta.add("7.20 hours");
		alEta.add("6 hours");

		alCarType.add("Sedan");
		alCarType.add("Hatchback");
		alCarType.add("Sedan");
		alCarType.add("Hatchback");
		alCarType.add("Sedan");
		alCarType.add("Hatchback");
		alCarType.add("Sedan");
		alCarType.add("Hatchback");
		alCarType.add("Sedan");
		alCarType.add("Hatchback");

		alSeats.add("4 Seats");
		alSeats.add("7 seats");
		alSeats.add("4 Seats");
		alSeats.add("7 seats");
		alSeats.add("4 Seats");
		alSeats.add("7 seats");
		alSeats.add("4 Seats");
		alSeats.add("7 seats");
		alSeats.add("4 Seats");
		alSeats.add("7 seats");



		tvTimeSort = (TextView) findViewById(R.id.tv_sorttime);
		tvCostSort = (TextView) findViewById(R.id.tv_sortcost);

		final SearchAdapter adapter = new SearchAdapter(
				SearchTaxi.this, alTime,alCost,alTravelsName,alEta,alCarType,alSeats);
		lvSearch.setTextFilterEnabled(true);
		lvSearch.setAdapter(adapter);

		/*TextWatcher filtro = new TextWatcher(){

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub 

			} 

			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub 

			} 

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub 
				adapter.getFilter().filter(e.getText().toString()); 
			} 

		}; 

		e.addTextChangedListener(filtro);
		 */

		tvCostSort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(costClick == 0){
					adapter.getFilter().filter("1");
					costClick = 1;
				}else{
					costClick = 0;
					adapter.getFilter().filter("2");
				}
			}
		});
		tvTimeSort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(timeClick == 0){
					adapter.getFilter().filter("3");
					timeClick = 1;
				}else{
					timeClick = 0;
					adapter.getFilter().filter("4");
				}
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_taxi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition( 
				R.anim.push_side_left_in,
				R.anim.push_side_left_out);


	}


}
