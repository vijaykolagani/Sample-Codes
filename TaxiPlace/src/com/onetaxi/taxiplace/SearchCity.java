package com.onetaxi.taxiplace;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchCity extends ActionBarActivity implements OnClickListener{

	LinearLayout llPopular1,llPopular2,llPopular3,llPopular4,llMain;
	TextView tvPopular1,tvPopular2,tvPopular3,tvPopular4,tvTittle;
	String city="";
	SharedPreferences pref;
	Bundle extras;

	ArrayAdapter<String> myAdapter;
	ListView listView;
	String[] dataArray = new String[] {"Hyderabad","Vijayawada", "Chennai", "Bangalore", "Delhi", "Mumbai"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_city);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		pref = this.getSharedPreferences(
				"com.onetaxi.taxiplace", Context.MODE_PRIVATE);
		extras = getIntent().getExtras();
		initViews();
	}

	void initViews(){
		llPopular1 = (LinearLayout) findViewById(R.id.ll_popular1);
		llPopular2 = (LinearLayout) findViewById(R.id.ll_popular2);
		llPopular3 = (LinearLayout) findViewById(R.id.ll_popular3);
		llPopular4 = (LinearLayout) findViewById(R.id.ll_popular4);
		llMain = (LinearLayout) findViewById(R.id.ll_main);

		tvPopular1 = (TextView) findViewById(R.id.tv_popolar1);
		tvPopular2 = (TextView) findViewById(R.id.tv_popolar2);
		tvPopular3 = (TextView) findViewById(R.id.tv_popolar3);
		tvPopular4 = (TextView) findViewById(R.id.tv_popolar4);
		tvTittle = (TextView) findViewById(R.id.tv);

		listView = (ListView) findViewById(R.id.listview);

		myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray);
		listView.setAdapter(myAdapter);
		listView.setTextFilterEnabled(true);


		llPopular1.setOnClickListener(this);
		llPopular2.setOnClickListener(this);
		llPopular3.setOnClickListener(this);
		llPopular4.setOnClickListener(this);
		
		 listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					city = ((TextView) arg1).getText().toString();
					if(extras.getInt("type") == 1){
						pref.edit().putString("from", city).commit();
					}
					if(extras.getInt("type") == 2)
						pref.edit().putString("to", city).commit();
					Intent intent = new Intent(SearchCity.this, MainActivity.class);
					startActivity(intent);
					overridePendingTransition(
							R.anim.push_down_out,
							R.anim.push_down_in);
					finish();
				
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// Inflate menu to add items to action bar if it is present.
		inflater.inflate(R.menu.search_city, menu);
		// Associate searchable configuration with the SearchView
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.menu_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		//searchView.setIconifiedByDefault(false);   

		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() 
		{
			@Override
			public boolean onQueryTextChange(String newText) 
			{
				// this is your adapter that will be filtered
				if(newText.length() > 0){
					myAdapter.getFilter().filter(newText);
					System.out.println("on text chnge text: "+newText);
					listView.setVisibility(View.VISIBLE);
					tvTittle.setVisibility(View.GONE);
					llMain.setVisibility(View.GONE);
				}else{
					listView.setVisibility(View.GONE);
					tvTittle.setVisibility(View.VISIBLE);
					llMain.setVisibility(View.VISIBLE);
				}
				return true;
			}
			@Override
			public boolean onQueryTextSubmit(String query) 
			{
				// this is your adapter that will be filtered
				myAdapter.getFilter().filter(query);
				System.out.println("on query submit: "+query);
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent intent = new Intent(SearchCity.this, MainActivity.class);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {

		case R.id.ll_popular1:
			city = tvPopular1.getText().toString();
			if(extras.getInt("type") == 1){
				pref.edit().putString("from", city).commit();
			}
			if(extras.getInt("type") == 2)
				pref.edit().putString("to", city).commit();
			intent = new Intent(SearchCity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(
					R.anim.push_down_out,
					R.anim.push_down_in);
			finish();
			break;
		case R.id.ll_popular2:
			city = tvPopular2.getText().toString();

			if(extras.getInt("type") == 1)
				pref.edit().putString("from", city).commit();
			if(extras.getInt("type") == 2)
				pref.edit().putString("to", city).commit();
			intent = new Intent(SearchCity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(
					R.anim.push_down_out,
					R.anim.push_down_in);
			finish();
			break;
		case R.id.ll_popular3:
			city = tvPopular3.getText().toString();

			if(extras.getInt("type") == 1)
				pref.edit().putString("from", city).commit();
			if(extras.getInt("type") == 2)
				pref.edit().putString("to", city).commit();
			intent = new Intent(SearchCity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(
					R.anim.push_down_out,
					R.anim.push_down_in);
			finish();
			break;
		case R.id.ll_popular4:
			city = tvPopular4.getText().toString();

			if(extras.getInt("type") == 1)
				pref.edit().putString("from", city).commit();
			if(extras.getInt("type") == 2)
				pref.edit().putString("to", city).commit();
			intent = new Intent(SearchCity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(
					R.anim.push_down_out,
					R.anim.push_down_in);
			finish();
			break;

		default:
			city ="";
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SearchCity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(
				R.anim.push_down_out,
				R.anim.push_down_in);
		finish();
		super.onBackPressed();
	}


}
