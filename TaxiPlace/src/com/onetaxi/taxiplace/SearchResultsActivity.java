package com.onetaxi.taxiplace;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class SearchResultsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_city);
		//handleIntent(getIntent());
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.search_city, menu);
	        SearchManager searchManager =
	                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    	SearchView searchView =
					(SearchView) menu.findItem(R.id.menu_search).getActionView();
	        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
			return true;
	    }

	    @Override
	    protected void onNewIntent(Intent intent) {
	        handleIntent(intent);
	    }

	    private void handleIntent(Intent intent) {
	    	/* String query1 = intent.getStringExtra(SearchManager.QUERY);
	            Toast.makeText(getApplicationContext(), query1, 1).show();
	        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	            String query = intent.getStringExtra(SearchManager.QUERY);
	           // Toast.makeText(getApplicationContext(), query, 1).show();
	            //use the query to search
	        }*/
	
	    }
}

