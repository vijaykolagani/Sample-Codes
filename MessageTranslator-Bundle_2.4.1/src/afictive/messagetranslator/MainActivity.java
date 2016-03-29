package afictive.messagetranslator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.svaoaowsx.kkpqiooew40407.AdListener;
import com.svaoaowsx.kkpqiooew40407.MA;

public class MainActivity extends Activity implements

TextToSpeech.OnInitListener,AdListener {
	/** Called when the activity is first created. */
	// voice
	protected static final int RESULT_SPEECH = 1;
	private static final String TAG = "TextToSpeechDemo";
	private TextToSpeech mTts;
	private MA ma; 
	SharedPreferences prefs;

	private HttpClient client;
	private HttpGet getMethod;
	String string1;

	private AlertDialog.Builder builder;
	ImageButton send, listen, mail, speak, camera;
	Button share;
	Button translate, reverse, browse, cancel, feedback,copy;
	public static EditText et1;
	static TextView et2, textSource;
	public int pos1, pos2;
	Spinner source, destination;
	TextView tvsource, tvdestination;
	private ProgressDialog progressDialog;
	int x, y;
	int result;
	static String translatedText = "";
	String provider;

	static Locale languages1[] = { Locale.SIMPLIFIED_CHINESE,
			Locale.TRADITIONAL_CHINESE, Locale.ENGLISH, Locale.FRENCH,
			Locale.GERMAN, Locale.ITALIAN, Locale.JAPANESE, Locale.KOREAN };
	String languageList[] = {"ar","zh-CN","zh-TW","da","nl","en","fr","de","el",
			"hi","hu","id","it","ja","ko","pt","ro","ru","es","sv","tr","uk","vi"};

	static Language languages[] = { Language.ARABIC,
			Language.CHINESE_SIMPLIFIED, Language.CHINESE_TRADITIONAL,
			Language.DANISH, Language.DUTCH, Language.ENGLISH, Language.FRENCH,
			Language.GERMAN, Language.GREEK, Language.HINDI,
			Language.HUNGARIAN, Language.INDONESIAN, Language.ITALIAN,
			Language.JAPANESE, Language.KOREAN, Language.PORTUGUESE,
			Language.ROMANIAN, Language.RUSSIAN, Language.SPANISH,
			Language.SWEDISH, Language.TURKISH, Language.UKRANIAN,
			Language.VIETNAMESE };

	void getPos2(int pos) {
		pos2 = pos;

	}

	void getPos1(int pos) {
		pos1 = pos;

	}

	SocialAuthAdapter adapter;
	Profile profileMap;

	String sourcelang, destlang;
	
	//D
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);
		  if(ma==null)
          	ma=new MA(MainActivity.this, null,true);
          ma.callSmartWallAd();
		prefs = this.getSharedPreferences("shared", Context.MODE_PRIVATE);

		
		source = (Spinner) findViewById(R.id.spSource);
		destination = (Spinner) findViewById(R.id.spDest);
		
		source.setSelection(prefs.getInt("sourcepos", 5));
		destination.setSelection(prefs.getInt("destinationpos", 0));
		
		et1 = (EditText) findViewById(R.id.etSource);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		textSource = (TextView) findViewById(R.id.tvSource);
		et2 = (TextView) findViewById(R.id.tvDestination);
		tvsource = (TextView) findViewById(R.id.langS);
		tvdestination = (TextView) findViewById(R.id.langD);
		tvsource.setText(prefs.getString("source", "English"));
		tvdestination.setText(prefs.getString("destination", "Arabic"));

		listen = (ImageButton) findViewById(R.id.listen);
		send = (ImageButton) findViewById(R.id.send);
		mail = (ImageButton) findViewById(R.id.mail);
		share = (Button) findViewById(R.id.share);
		speak = (ImageButton) findViewById(R.id.speak);
		camera = (ImageButton) findViewById(R.id.camera);
		feedback = (Button) findViewById(R.id.feedback);
		copy = (Button) findViewById(R.id.copy);

		reverse = (Button) findViewById(R.id.reverse);
		translate = (Button) findViewById(R.id.translate);
		cancel = (Button) findViewById(R.id.cancel);
		// translate=(Button)findViewById(R.id.translate);
		registerForContextMenu(tvdestination);
		copy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				    clipboard.setText(et2.getText().toString());
				    Toast.makeText(getApplicationContext(), "Copied to Clipboard",
				    		 Toast.LENGTH_SHORT).show();
				} else {
				    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", et2.getText().toString());
				            clipboard.setPrimaryClip(clip);
				            Toast.makeText(getApplicationContext(), "Copied to Clipboard",
				            		 Toast.LENGTH_SHORT).show();
				}
							
			}
		});
		et2.setOnLongClickListener(new OnLongClickListener() {
			
			@SuppressLint("NewApi") @Override
			public boolean onLongClick(View v) {
				if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				    clipboard.setText(et2.getText().toString());
				    Toast.makeText(getApplicationContext(), "Copied to Clipboard",
				    		 Toast.LENGTH_SHORT).show();
				} else {
				    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", et2.getText().toString());
				            clipboard.setPrimaryClip(clip);
				            Toast.makeText(getApplicationContext(), "Copied to Clipboard",
				            		 Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers

		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);

		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.addCallBack(Provider.LINKEDIN, "http://www.dcwn.org/");

		// Enable Provider
		adapter.enable(share);

		feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
						Uri.fromParts("mailto", "adprasad.amballa@gmail.com",
								null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MessageTranslator");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et1.setText("");
			}
		});
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,ImagetoTextActivity.class));
				startActivity(new Intent(MainActivity.this,
						com.itcuties.android.apps.MainActivity.class));

			}
		});
		speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// et1.setText("");
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					et1.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
				

			}
		});

		// voice
		mTts = new TextToSpeech(this, this);
		// listen = (Button)findViewById(R.id.listen);

		listen.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("SdCardPath") public void onClick(View v) { 
				
				MediaPlayer mediaPlayer = new MediaPlayer();
	        final File root = android.os.Environment.getExternalStorageDirectory();
	        try {

	            mediaPlayer.setDataSource("file:///mnt/sdcard/MessageTranslator/test.mp3");
	          //  Log.d("uri", "file://"+root.getAbsolutePath() + "MessageTranslator"+"/test.mp3");
	            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	            mediaPlayer.prepare();
	            mediaPlayer.start();
	        } catch (IllegalArgumentException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IllegalStateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        }
		});
		// Spinner Adapter
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.planets_array, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		source.setAdapter(adapter);
		destination.setAdapter(adapter);

		source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos1, long id) {
				// Object item1 = parent.getItemAtPosition(pos1);
				x = pos1;

				tvsource.setText(parent.getItemAtPosition(pos1).toString());
				
				 tvsource.setText(parent.getItemAtPosition(pos1)
							.toString());
				 source.setSelection(pos1);
				 
				 prefs.edit().putString("source", parent.getItemAtPosition(pos1)
							.toString()).commit();
				 prefs.edit().putInt("sourcepos", pos1).commit();
				
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		destination
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos2, long id) {
						// Object item2 = parent.getItemAtPosition(pos2);
						y = pos2;

						tvdestination.setText(parent.getItemAtPosition(pos2)
								.toString());

						
						  tvdestination.setText( parent
									.getItemAtPosition(pos2).toString());
						  destination.setSelection(pos2);
						 
						  prefs.edit().putString("destination", parent.getItemAtPosition(pos2)
									.toString()).commit();
						 prefs.edit().putInt("destinationpos", pos2).commit();

						/*SharedPreferences.Editor editor = prefs.edit();

						editor.putString("destination", parent
								.getItemAtPosition(pos2).toString());
						editor.putInt("destinationpos", pos2);
						editor.commit();*/

					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		// tvsource.setText(prefs.getString("source", "English"));
		source.setSelection(prefs.getInt("sourcepos", 5));
		// tvdestination.setText(prefs.getString("destination", "French"));
		destination.setSelection(prefs.getInt("destinationpos", 1));
		reverse.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int langSource = source.getSelectedItemPosition();
				int langDest = destination.getSelectedItemPosition();
				source.setSelection(langDest);
				destination.setSelection(langSource);
			}
		});

		translate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// API Key
				Translate.setKey("4DACA9E5488E5EA4D8587A9F48D0244772011A04");
				string1 = et1.getText().toString();
				textSource.setText(string1);
				Language languages[] = { Language.ARABIC,
						Language.CHINESE_SIMPLIFIED,
						Language.CHINESE_TRADITIONAL, Language.DANISH,
						Language.DUTCH, Language.ENGLISH, Language.FRENCH,
						Language.GERMAN, Language.GREEK, Language.HINDI,
						Language.HUNGARIAN, Language.INDONESIAN,
						Language.ITALIAN, Language.JAPANESE, Language.KOREAN,
						Language.PORTUGUESE, Language.ROMANIAN,
						Language.RUSSIAN, Language.SPANISH, Language.SWEDISH,
						Language.TURKISH, Language.UKRANIAN,
						Language.VIETNAMESE };

				Translate.setHttpReferrer("http//www.dmathieu.com");

				client = new DefaultHttpClient();
				// Async Task
				if (isNetworkAvailable()) {
					// alert();
					GetSummaryTask gst = new GetSummaryTask();
					gst.execute();
					// gst.execute(new String[] {
					// "http://www.traininghouse.in/metrohospitals/cities.php?city_id=21"});
					Log.d("onCreate", "68");
				} else {
					Toast.makeText(getApplicationContext(),
							"Please check Internet connection",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final CharSequence[] items = { "Messaging", "Email" };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Share");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {

						if (item == 0) {
							Intent smsIntent = new Intent(Intent.ACTION_VIEW);
							smsIntent.setType("vnd.android-dir/mms-sms");
							smsIntent.putExtra("sms_body", translatedText);
							startActivity(smsIntent);
						}
						if (item == 1) {
							Intent email = new Intent(Intent.ACTION_SEND);
							email.putExtra(Intent.EXTRA_EMAIL,
									new String[] { "" });
							email.putExtra(Intent.EXTRA_SUBJECT, "Hi");
							email.putExtra(Intent.EXTRA_TEXT, translatedText);
							email.setType("message/rfc822");
							startActivity(Intent.createChooser(email,
									"Choose an Email client :"));
						}
					}
				}).show();

			}
		});

		send.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(Lmessenger.this,SMS.class));
				Log.d("on", "274");
				Intent smsIntent = new Intent(Intent.ACTION_VIEW);
				smsIntent.setType("vnd.android-dir/mms-sms");
				smsIntent.putExtra("sms_body", translatedText);
				startActivity(smsIntent);

			}
		});
		
		
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}
		super.onDestroy();
		// new InterstitialAd(this, "2931").show();

	}

	// Implements TextToSpeech.OnInitListener.
	public void onInit(int status) {

		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			 if (y == 2)
				result = mTts.setLanguage(languages1[0]);
			else if (y == 3)
				result = mTts.setLanguage(languages1[1]);
			else if (y == 5)
				result = mTts.setLanguage(languages1[2]);
			else if (y == 6)
				result = mTts.setLanguage(languages1[3]);
			else if (y == 7)
				result = mTts.setLanguage(languages1[4]);
			else if (y == 12)
				result = mTts.setLanguage(languages1[5]);
			else if (y == 13)
				result = mTts.setLanguage(languages1[6]);
			else if (y == 14)
				result = mTts.setLanguage(languages1[7]);
			else
				result = mTts.setLanguage(Locale.CHINESE);
			result = mTts.setLanguage(Locale.CHINESE);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				Log.e(TAG, "Language is not available.");
			} else {
				// Check the documentation for other possible result codes.
				// For example, the language may be available for the locale,
				// but not for the specified country and variant.
				// The TTS engine has been successfully initialized.
				// Allow the user to press the button for the app to speak
				// again.
				listen.setEnabled(true);
				// Greet the user.
				// sayHello();
			}
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

	int i = 0;

	private void sayHello() {
		// Select a random hello.
		// String speak=et2.getText().toString();

		mTts.speak(translatedText, TextToSpeech.QUEUE_FLUSH, // Drop allpending
																// entries in
																// the playback
																// queue.
				null);
	}

	public void optinCompleted(Context arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	private class GetSummaryTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			try {
				translatedText = Translate.execute(string1, languages[x],
						languages[y]);
				Log.d("transtext", translatedText);
				ttsChinese(translatedText,y);
				// builder=new AlertDialog.Builder(MainActivity.this);

			} catch (Exception e) {
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
			} finally {
				// et2.setText("");
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			progressDialog.dismiss();
			et2.setText(translatedText);
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
			//new AlertAd(MainActivity.this, "2931").showAlertAd();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = ProgressDialog.show(MainActivity.this, "",
					"Loading...");
		}

	}
	public void ttsChinese(String tts,int p){
		try{
			
			String path ="http://translate.google.com/translate_tts?ie=UTF-8&tl="+languageList[p]+"&q="+tts.replace(" ", "%20");
	        //this is the name of the local file you will create
	        String targetFileName = "test.mp3";
	        Log.d("code",path);
			File cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MessageTranslator");
			if(!cacheDir.exists())
			   cacheDir.mkdirs();

			File f=new File(cacheDir,targetFileName);
			URL url = new URL(path); 
			URI uri = new URI(path);
	        URL u = new URL(uri.toASCIIString());
			            InputStream input = new BufferedInputStream(u.openStream());
			            OutputStream output = new FileOutputStream(f);

			            byte data[] = new byte[1024];
			            long total = 0;
			            int count=0;
			            while ((count = input.read(data)) != -1) {
			                total++;
			                Log.e("while","A"+total);

			                output.write(data, 0, count);
			            }

			            output.flush();
			            output.close();
			            input.close();
			}
			catch(Exception e){
				e.printStackTrace();
				}
		//try {
		/*   
			Log.d("code",languageList[p]);
	        String path ="http://translate.google.com/translate_tts?ie=UTF-8&tl="+languageList[p]+"&q="+tts;
	        //this is the name of the local file you will create
	        String targetFileName = "test.mp3";
	            boolean eof = false;
	       // URI uri = new URI(path);
	        URI uri = new URI(path);
	        URL u = new URL(uri.toASCIIString());
	        
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.addRequestProperty("User-Agent", "Mozilla/5.0");
	        c.setRequestMethod("GET");
	        c.setDoOutput(true);
	        c.connect();
	        FileOutputStream f = new FileOutputStream(new File(Environment.getExternalStorageDirectory()
	                +targetFileName));
	            InputStream in = c.getInputStream();
	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            while ( (len1 = in.read(buffer)) > 0 ) {
	            f.write(buffer,0, len1);
	                     }
	        f.close();
	        */ 
	}
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				et1.setText(text.get(0));
			}
			break;
		}

		}
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			Log.d("ShareButton", "Authentication Successful");

			adapter.updateStatus(translatedText);
			provider = SocialAuthAdapter.PROVIDER;
			
			final String providerName = values
					.getString(provider);
			Toast.makeText(MainActivity.this,
					"Message posted on " + providerName, Toast.LENGTH_LONG)
					.show();
			//adapter.getUserProfileAsync(new ProfileDataListener());
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}

	}
	// To receive the profile response after authentication
		private final class ProfileDataListener implements SocialAuthListener<Profile> {


			@Override
			public void onExecute(Profile arg0) {
				// TODO Auto-generated method stub
				Profile profileMap = arg0;
				Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
				
				intent.putExtra("provider", provider);
				intent.putExtra("profile", profileMap);
				//startActivity(intent);
				Log.d("", profileMap+"");
			}

			@Override
			public void onError(SocialAuthError arg0) {
				// TODO Auto-generated method stub
				
			}
		}
	@Override
	public void onBackPressed() {
	   try{
		   ma.showCachedAd(this, AdType.smartwall); // for closing the activity call finish() method in onSmartWallClosed(). 
	   }catch (Exception e) {
	     // close the activity if ad is not available. 
	     finish();
	   }
	}


	@Override
	public void noAdAvailableListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdCached(AdType arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdError(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSDKIntegrationError(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSmartWallAdClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSmartWallAdShowing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Feedback")){
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.fromParts("mailto", "adprasad.amballa@gmail.com",
							null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MessageTranslator");
			startActivity(Intent
					.createChooser(emailIntent, "Send email..."));
		}
		return super.onOptionsItemSelected(item);
	}
	

}
