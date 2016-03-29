package com.itcuties.android.apps;


import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.prasad.ibox.R;

public class ReplyActivity extends SherlockActivity {
	
	
	TextView reply,reply_number;
	EditText reply_text;
	Button cancel,send,speak;
	int position,recvposition,myposition;
	String contactName,message,conName;
	SharedPreferences prefs;
	private HttpClient client;
	private HttpGet getMethod;
	private ProgressDialog progressDialog;
	static String translatedText = "";
	Language languages[] = { Language.ARABIC,
			Language.CHINESE_SIMPLIFIED,
			Language.CHINESE_TRADITIONAL, Language.DANISH,
			Language.DUTCH, Language.ENGLISH, Language.FRENCH,
			Language.GERMAN, Language.GREEK, Language.HINDI,
			Language.HUNGARIAN, Language.INDONESIAN,
			Language.ITALIAN, Language.JAPANESE, Language.KOREAN,
			Language.PORTUGUESE, Language.ROMANIAN,
			Language.RUSSIAN, Language.SPANISH, Language.SWEDISH,Language.THAI,
			Language.TURKISH, Language.UKRANIAN,
			Language.VIETNAMESE };
	
	protected static final int RESULT_SPEECH = 1;
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTheme(R.style.Sherlock___Theme_Light);
		
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00aae9")));
	      
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

		setContentView(R.layout.reply_layout);
		
		Intent extras = getIntent();
	      contactName = extras.getStringExtra("number");
	      conName = extras.getStringExtra("name");
	      
	      prefs = this.getSharedPreferences(
			      "com.ducere.wallet", Context.MODE_PRIVATE);
		
		position = prefs.getInt(contactName, 5);
		//Log.d("messageact-name",contactName+position);
		
		reply = (TextView)findViewById(R.id.reply);
		reply_number = (TextView)findViewById(R.id.reply_number);
		reply_number.setText(conName);
		reply_text = (EditText)findViewById(R.id.editReply);
		//reply_text.setOnEditorActionListener(mWriteListener);
		cancel = (Button)findViewById(R.id.imCancle);
		send = (Button)findViewById(R.id.imSend);
		speak = (Button)findViewById(R.id.speak);
		speak.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, "en_US");

				try {
					 
					startActivityForResult(intent, RESULT_SPEECH);
					
					
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
				
			}
		});
		
		reply_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            	reply.setText(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            	reply.setTextColor(Color.parseColor("#4db849"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            	
            }
        });
		
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reply_text.setText("");
			}
		});
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message = reply_text.getText().toString();
				if (contactName.length()>0 && message.length()>0) 
					translate(message);                  
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
	}
	 public void translate(String body){

			// API Key
			Translate.setKey("4DACA9E5488E5EA4D8587A9F48D0244772011A04");
			prefs = this.getSharedPreferences(
				      "com.ducere.wallet", Context.MODE_PRIVATE);
			
			recvposition = prefs.getInt(contactName, 5);
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
					translatedText = Translate.execute(urls[0], Language.AUTO_DETECT,languages[recvposition]
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
				//Log.d("result", translatedText);
				sendSMS(contactName, translatedText); 
				
				//transText.setText(translatedText);
				//new AlertAd(MainActivity.this, "2931").showAlertAd();
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(ReplyActivity.this, "",
						"Loading...");
			}

		}

		private boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		}
	 
	private void sendSMS(String phoneNumber, final String transmessage)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", 
                                Toast.LENGTH_SHORT).show();
                        reply.append(" -Sent "+transmessage);
                        reply.setTextColor(Color.parseColor("#4db849"));
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        reply.append(" -Faild to send "+ transmessage);
                        reply.setTextColor(Color.RED);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        reply.append(" -Faild to send "+transmessage);
                        reply.setTextColor(Color.RED);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        reply.append(" -Faild to send "+transmessage);
                        reply.setTextColor(Color.RED);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        reply.append(" -Faild to send "+ transmessage);
                        reply.setTextColor(Color.RED);
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, transmessage, sentPI, deliveredPI);  
       // Log.d("reply","sent");
    }
	
	 @Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
	        case android.R.id.home:
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
		
	}
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	 
	        switch (requestCode) {
	        case RESULT_SPEECH: {
	            if (resultCode == RESULT_OK && null != data) {
	 
	                ArrayList<String> text = data
	                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	               reply_text.setText(text.get(0));
	 
	            }
	            break;
	        }
	       
	 
	        }

	 }
	
	
}
