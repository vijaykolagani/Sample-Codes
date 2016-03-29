package com.itcuties.android.apps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.itcuties.android.apps.data.SMSData;
import com.prasad.ibox.R;


public class MainActivity extends SherlockListActivity implements OnScrollListener{
	
	Cursor c;
	Uri uri;
	int position,check;
	SharedPreferences prefs;
	static int loadnumber;
	List<SMSData> smsList;
	private ProgressDialog progressDialog; 
	ListView lv;
	boolean isitfirst;
	String image,image_uri;
	Bitmap bitmap;
	
	String languages[] = { " ARABIC",
			"CHINESE_SIMPLIFIED",
			"CHINESE_TRADITIONAL", "DANISH",
			"DUTCH", "ENGLISH", "FRENCH",
			"GERMAN", "GREEK", "HINDI",
			"HUNGARIAN", "INDONESIAN",
			"ITALIAN", "JAPANESE", "KOREAN",
			"PORTUGUESE", "ROMANIAN",
		"RUSSIAN", "SPANISH", "SWEDISH","THAI",
			"TURKISH", "UKRANIAN",
			"VIETNAMESE" };

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setTheme(R.style.Sherlock___Theme_Light);
		
		
		
		 prefs = this.getSharedPreferences(
			      "com.ducere.wallet", Context.MODE_PRIVATE);
		 
		 //prefs.edit().putInt("mylanguage", position).commit();
     	
		
		if(prefs.getBoolean("isitfirst", true))
			languageList();
		
		lv = getListView();
		lv.setFooterDividersEnabled(true);
		//lv.setLongClickable(true);
		//lv.addFooterView(getListView());
		lv.setOnScrollListener(new EndlessScrollListener());
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00aae9")));

		smsList = new ArrayList<SMSData>();
		
		 uri = Uri.parse("content://sms/inbox");
			c= getContentResolver().query(uri, null, null ,null,null);
			startManagingCursor(c);
			
			check = loadnumber;
			GetSummaryTask gst = new GetSummaryTask();
			gst.execute();
			// Read the sms data and store it in the list
			
			//c.close();
			//setSupportProgressBarIndeterminateVisibility(false);
			// Set smsList in the ListAdapter
			
			
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Log.d("onresume","onresume");
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Log.d("onstart","onstart");
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SMSData sms = (SMSData)getListAdapter().getItem(position);
		//Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();
		//finish();
		startActivity(new Intent(MainActivity.this,MessageActivity.class).putExtra("number", sms.getNumber()).putExtra("body", sms.getBody()).putExtra("name", sms.getDisname()));
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		c.close();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		c.close();
		loadnumber=0;
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = false;

        menu.add("Contacts")
          .setIcon(isLight ? R.drawable.ic_menu_allfriends : R.drawable.ic_menu_allfriends)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Settings")
        	.setIcon(isLight ? R.drawable.settings_dark : R.drawable.settings_lite)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("LoadMore")
           // .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // If this callback does not handle the item click, onPerformDefaultAction
	        // of the ActionProvider is invoked. Hence, the provider encapsulates the
	        // complete functionality of the menu item.
		 	if(item.getTitle().toString().equals("Contacts")){
		 		startActivity(new Intent(MainActivity.this,ContactsListActivity.class));
		 	}else if(item.getTitle().toString().equals("Settings")){
		 		languageList();
		 		//startActivity(new Intent(MainActivity.this,ContactsListActivity.class));
		 	}else if(item.getTitle().toString().equals("LoadMore")){
		 		loadMore();
		 	}
	       
	        return false;
	    }
	
	 
	 public void loadMore(){

	 		//languageList();
	 		//startActivity(new Intent(MainActivity.this,ContactsListActivity.class));
	 		//Log.d("size",""+loadnumber);
	 		//c.close();
	 		uri = Uri.parse("content://sms/inbox");
			c= getContentResolver().query(uri, null, null ,null,null);
			startManagingCursor(c);
			int check=loadnumber;
							
			// Read the sms data and store it in the list
			if(c.moveToPosition(loadnumber)&&loadnumber<c.getCount()) {
				setSupportProgressBarIndeterminateVisibility(true);
				for(int i=0; i < 20; i++) {
					if((c.getCount()-(check+1))>0){
					
						SMSData sms = new SMSData();
						sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
						sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
						String tempDis = uploadContactPhoto(this,c.getString(c.getColumnIndexOrThrow("address")).toString());
						sms.setDisname(tempDis);
						sms.setImage(bitmap);
						smsList.add(sms);
						
						c.moveToNext();
						check = check+i;
						//Log.d("i num",i+"");
					}else{
						//Log.d("nomsgs","nomsgs");
						Toast.makeText(MainActivity.this, "Loaded More Messages", Toast.LENGTH_SHORT).show();
					break;
					}
					
				}
			}else{
				//Log.d("nomsgs","nomsgs");
				Toast.makeText(MainActivity.this, "No More Messages", Toast.LENGTH_SHORT).show();
			
			}
			//c.close();
			setSupportProgressBarIndeterminateVisibility(false);
			loadnumber = loadnumber+20;
			
			// Set smsList in the ListAdapter
			setListAdapter(new ListAdapter(this, smsList));
			
	 	
	 }
	 public void languageList(){
	    	
	    	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
	                MainActivity.this);
	        builderSingle.setIcon(R.drawable.icon);
	        if(prefs.getBoolean("isitfirst", true)){
	        	builderSingle.setTitle("Set your Language");
		        
	        }else{
	        	builderSingle.setTitle("Current Selection: "+languages[prefs.getInt("mylanguage", 5)]);
		        
	        }
	        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	        		MainActivity.this,
	                android.R.layout.select_dialog_singlechoice);
	        arrayAdapter.add("ARABIC");
	        arrayAdapter.add("CHINESE_SIMPLIFIED");
	        arrayAdapter.add("CHINESE_TRADITIONAL");
	        arrayAdapter.add("DANISH");
	        arrayAdapter.add("DUTCH");
	        arrayAdapter.add("ENGLISH");
	        arrayAdapter.add("FRENCH");
	        arrayAdapter.add("GERMAN");
	        arrayAdapter.add("GREEK");
	        arrayAdapter.add("HINDI");
	        arrayAdapter.add("HUNGARIAN");
	        arrayAdapter.add("INDONESIAN");
	        arrayAdapter.add("ITALIAN");
	        arrayAdapter.add("JAPANESE");
	        arrayAdapter.add("KOREAN");
	        arrayAdapter.add("PORTUGUESE");
	        arrayAdapter.add("ROMANIAN");
	        arrayAdapter.add("RUSSIAN");
	        arrayAdapter.add("SPANISH");
	        arrayAdapter.add("SWEDISH");
	        arrayAdapter.add("THAI");
	        arrayAdapter.add("TURKISH");
	        arrayAdapter.add("UKRANIAN");
	        arrayAdapter.add("VIETNAMESE ");
	        builderSingle.setNegativeButton("cancel",
	                new DialogInterface.OnClickListener() {

	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                    }
	                });

	        builderSingle.setAdapter(arrayAdapter,
	                new DialogInterface.OnClickListener() {

	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        final String strName = arrayAdapter.getItem(which);
	                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
	                        		MainActivity.this);
	                        builderInner.setMessage(strName);
	                        position = which;
	                        builderInner.setTitle("Your Language is");
	                        builderInner.setPositiveButton("Ok",
	                                new DialogInterface.OnClickListener() {

	                                    @Override
	                                    public void onClick(
	                                            DialogInterface dialog,
	                                            int which) {
	                                       // dialog.dismiss();
	                                    	
	                                    	
	                                    	/*SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
	                                    	 editor.putInt("prasad", which);
	                                    	 
	                                    	 editor.commit();*/
	                                    	
	                                    	prefs.edit().putInt("mylanguage", position).commit();
	                                    	prefs.edit().putBoolean("isitfirst", false).commit();
	                                    	
	                                    	//Log.d("test",number.replace(" ", ""));
	                                    	
	                                    }
	                                });
	                        builderInner.show();
	                    }
	                });
	        builderSingle.show();
	    }
	 
	 private String uploadContactPhoto(Context context, String number) {

		// Log.v("ffnet", "Started uploadcontactphoto...");

		 String name = null;
		 String contactId = null;
		 InputStream input = null;

		 // define the columns I want the query to return
		 String[] projection = new String[] {
		         ContactsContract.PhoneLookup.DISPLAY_NAME,
		         ContactsContract.PhoneLookup._ID,
		         ContactsContract.CommonDataKinds.Phone.PHOTO_URI};

		 // encode the phone number and build the filter URI
		 Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

		 // query time
		 Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

		 if (cursor.moveToFirst()) {

		     // Get values from contacts database:
		     contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
		     name =      cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
		     image_uri = cursor.getString(cursor
		        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
		     //Log.d("contactName",name);
		     // Get photo of contactId as input stream:
		     Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
		     /*InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
	                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId)));
	 */
		     InputStream inputa = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), uri);
		     Log.d("imageuri",image_uri+name);
		     if(image_uri !=null){
		    	 try {
						bitmap = MediaStore.Images.Media
						    .getBitmap(this.getContentResolver(),
						      Uri.parse(image_uri));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.d("image",bitmap+"");

		     }else{
		    	 bitmap = null;
		     }
		   
		     		   
		   // Log.v("ffnet", "Started uploadcontactphoto: Contact Found @ " + input.toString());            
		    /* Log.v("ffnet", "Started uploadcontactphoto: Contact name  = " + name);
		     Log.v("ffnet", "Started uploadcontactphoto: Contact id    = " + contactId);*/
		     return name;

		 } else {
			 image = "";
			 bitmap = null;
		    // Log.v("ffnet", "Started uploadcontactphoto: Contact Not Found @ " + number);
		     return number; // contact not found

		 }

	 }
	 private class GetSummaryTask extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... urls) {

				try {
					
					if(c.moveToPosition(loadnumber)&&c.getCount()>=loadnumber) {
						
						for(int i=loadnumber; i < 19; i++) {
							if((c.getCount()-(check+1))>0){
								SMSData sms = new SMSData();
								sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
								sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
								String tempDis = uploadContactPhoto(MainActivity.this,c.getString(c.getColumnIndexOrThrow("address")).toString());
								sms.setDisname(tempDis);
								sms.setImage(bitmap);
								smsList.add(sms);
								
								c.moveToNext();
								check = check+1;
								loadnumber = i;
							}else{
								break;
							}
							
						}
					}
					
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

				//progressDialog.dismiss();
				setSupportProgressBarIndeterminateVisibility(false);
				setListAdapter(new ListAdapter(MainActivity.this, smsList));
				
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				setSupportProgressBarIndeterminateVisibility(true);
				/*progressDialog = ProgressDialog.show(MainActivity.this, "",
						"Loading...");*/
			}

		}

	@Override
	 public void onScroll(AbsListView view, int firstVisibleItem,
	 int visibleItemCount, int totalItemCount) {
		Toast.makeText(getApplicationContext(), "scroll", Toast.LENGTH_SHORT).show();
	}
	

	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	public class EndlessScrollListener implements OnScrollListener {

	    //private int currentPage = 0;
	    private int visibleThreshold = 0;
	    private int previousTotal = 0;
	    private boolean loading = true;

	    public EndlessScrollListener() {
	    }
	    public EndlessScrollListener(int visibleThreshold) {
	        this.visibleThreshold = visibleThreshold;
	    }

	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	        if (loading) {
	            if (totalItemCount > previousTotal) {
	                loading = false;
	                previousTotal = totalItemCount;
	                //counter +=12;
	            }
	        }
	        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
	            // Load The New Posts
	            loadMore();
	            loading = true;
	        }
	    }

	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	    }
	}

			
}

