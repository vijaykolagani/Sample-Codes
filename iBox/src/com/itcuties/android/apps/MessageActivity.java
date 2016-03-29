package com.itcuties.android.apps;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.prasad.ibox.R;

public class MessageActivity extends SherlockActivity {
	
	TextView number,transText,orgText;
	private HttpClient client;
	private HttpGet getMethod;
	private ProgressDialog progressDialog;
	static String translatedText = "",contactName,conName;
	int position,myposition;
	SharedPreferences prefs;
	Language languages[] = { Language.ARABIC,
			Language.CHINESE_SIMPLIFIED,
			Language.CHINESE_TRADITIONAL, Language.DANISH,
			Language.DUTCH, Language.ENGLISH, Language.FRENCH,
			Language.GERMAN, Language.GREEK, Language.HINDI,
			Language.HUNGARIAN, Language.INDONESIAN,
			Language.ITALIAN, Language.JAPANESE, Language.KOREAN,
			Language.PORTUGUESE, Language.ROMANIAN,
			Language.RUSSIAN, Language.SPANISH, Language.SWEDISH,
			Language.THAI,Language.TURKISH, Language.UKRANIAN,
			Language.VIETNAMESE };
	

	  @SuppressLint("NewApi")
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setTheme(R.style.Sherlock___Theme_Light);
	      
	      
	      
	      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00aae9")));
	      
	      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
	      
	      setContentView(R.layout.activity_box_main);
	      number = (TextView)findViewById(R.id.contactNum);
	      transText = (TextView)findViewById(R.id.transText);
	      orgText = (TextView)findViewById(R.id.originalText);
	      
	      Intent extras = getIntent();
	      contactName = extras.getStringExtra("number");
	      conName = extras.getStringExtra("name");
	      number.setText(conName);
	      orgText.setText(extras.getStringExtra("body"));
	      translate(extras.getStringExtra("body"));
	      
	      
	  }
	  public void translate(String body){

			// API Key
			Translate.setKey("4DACA9E5488E5EA4D8587A9F48D0244772011A04");
			prefs = this.getSharedPreferences(
				      "com.ducere.wallet", Context.MODE_PRIVATE);
			
			position = prefs.getInt(contactName, 5);
			myposition = prefs.getInt("mylanguage", 5);
			//Log.d("messageact-name",contactName+position);

			Translate.setHttpReferrer("http//www.dmathieu.com");

			client = new DefaultHttpClient();
			// Async Task
			if (isNetworkAvailable()) {
				// alert();
				GetSummaryTask gst = new GetSummaryTask();
				gst.execute(body);
				// gst.execute(new String[] {
				// "http://www.traininghouse.in/metrohospitals/cities.php?city_id=21"});
				//Log.d("onCreate", "68");
			} else {
				Toast.makeText(getApplicationContext(),
						"Please check Internet connection",
						Toast.LENGTH_SHORT).show();
			}

		
	  }
	  private class GetSummaryTask extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... urls) {

				try {
					translatedText = Translate.execute(urls[0], Language.AUTO_DETECT,languages[myposition]
					);
					//Log.d("transtext", translatedText);
					// builder=new AlertDialog.Builder(MainActivity.this);

				} catch (Exception e) {/*
					// TODO Auto-generated catch block
					e.printStackTrace();
					builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage(e.toString());
					builder.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();

								}
							});
					builder.create().show();
				*/} finally {
					// et2.setText("");
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {

				progressDialog.dismiss();
				transText.setText(translatedText);
				//new AlertAd(MainActivity.this, "2931").showAlertAd();
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(MessageActivity.this, "",
						"Translating...");
				
				
			}

		}

		private boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		}
		
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = true;

	       /* menu.add("Save")
	            .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/

	        menu.add("Reply")
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	       /* menu.add("Refresh")
	            .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
*/
	        return true;
	    }
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // If this callback does not handle the item click, onPerformDefaultAction
	        // of the ActionProvider is invoked. Hence, the provider encapsulates the
	        // complete functionality of the menu item.
			if(item.getItemId()== android.R.id.home){
				finish();
			}else if(item.getTitle().equals("Reply")){
				startActivity(new Intent(MessageActivity.this,ReplyActivity.class).putExtra("number", contactName).putExtra("name", conName));
			       
			}
	        return false;
	    }

}
