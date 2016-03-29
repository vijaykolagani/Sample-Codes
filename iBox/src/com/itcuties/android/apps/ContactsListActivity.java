package com.itcuties.android.apps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Window;
import com.itcuties.android.apps.data.ContactsData;
import com.prasad.ibox.R;

public class ContactsListActivity extends SherlockListActivity {
    /** Called when the activity is first created. */
	
	String number,conName;
	int position;
	SharedPreferences prefs;
	String name,contactName,id,image_uri;
	List<ContactsData> contactList;
	Bitmap bitmap;
	ListView lv;
	//ContentResolver cr;
	
	private ProgressDialog progressDialog; //declare it global
	//Context context;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00aae9")));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        lv = getListView();
        lv.setFastScrollEnabled(true);

       contactList = new ArrayList<ContactsData>();
        
        prefs = this.getSharedPreferences(
			      "com.ducere.wallet", Context.MODE_PRIVATE);
        

		//Log.d("in backgrnd","bg");
		GetSummaryTask gst = new GetSummaryTask();
		gst.execute();
		
       
				
      
        
    }
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ContactsData contact = (ContactsData)getListAdapter().getItem(position);
		
		number = contact.getNumber();
		conName = contact.getName();
		//Log.d("number", number);

    	
    	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                ContactsListActivity.this);
        builderSingle.setIcon(R.drawable.icon);
        builderSingle.setTitle("Select Action:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        		ContactsListActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Select Language For Contact");
        arrayAdapter.add("Send Message");
       
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
                    	if(which==0){
                    		languageList();
                    	}else{
                    		startActivity(new Intent(ContactsListActivity.this,ReplyActivity.class).putExtra("number", number).putExtra("name", conName));
         			       
                    	}
                    }
                });
        builderSingle.show();
    
		//languageList();
		
		
	}
    
   
    
    
    public void languageList(){
    	
    	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                ContactsListActivity.this);
        builderSingle.setIcon(R.drawable.icon);
        builderSingle.setTitle("Select Friend's Language:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        		ContactsListActivity.this,
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
                                ContactsListActivity.this);
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
                                    	//Log.d("contactlist-name",number+position);
                                    	
                                    	/*SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                                    	 editor.putInt("prasad", which);
                                    	 
                                    	 editor.commit();*/
                                    	
                                    	prefs.edit().putInt(number.replace(" ", ""), position).commit();
                                    	//Log.d("test",number.replace(" ", ""));
                                    	
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }
    
    public void getContacts(){}
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
    private class GetSummaryTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			ContentResolver cr = getContentResolver();
	        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
	                null, null, null, Phone.DISPLAY_NAME + " ASC");

			try {
				while (cur.moveToNext()) {
			        id = cur.getString(
		                        cur.getColumnIndex(ContactsContract.Contacts._ID));
			name = cur.getString(
		                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				
				//Log.d("cur",name);
							
		 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
		 			Cursor c = getContentResolver().query(Phone.CONTENT_URI, new String[]{Phone.NUMBER, Phone.TYPE, Phone.PHOTO_URI}, 
		                    " DISPLAY_NAME = '"+cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME))+"'", null, Phone.DISPLAY_NAME + " ASC");
		            while(c.moveToNext()){
		                switch(c.getInt(c.getColumnIndex(Phone.TYPE))){
		                case Phone.TYPE_MOBILE :
		                	//number = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)+"";

		                	ContactsData contacts = new ContactsData();
		        			contacts.setName(name);
		        			contacts.setNumber(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		        			image_uri = c.getString(c.getColumnIndex(Phone.PHOTO_URI));
		        			 if(image_uri !=null){
		        		    	 try {
		        						bitmap = MediaStore.Images.Media
		        						    .getBitmap(getContentResolver(),
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
		        			 contacts.setBitmap(bitmap);
		        			contactList.add(contacts);
		                	//Log.d("num", name+"-"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		                		break;
		                case Phone.TYPE_HOME :
		                	ContactsData contacts2 = new ContactsData();
		        			contacts2.setName(name);
		        			contacts2.setNumber(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		        			image_uri = c.getString(c.getColumnIndex(Phone.PHOTO_URI));
		        			 if(image_uri !=null){
		        		    	 try {
		        						bitmap = MediaStore.Images.Media
		        						    .getBitmap(getContentResolver(),
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
		        			 contacts2.setBitmap(bitmap);
		        			contactList.add(contacts2);
		                	//Log.d("num", name+"-"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		                		break;
		                case Phone.TYPE_WORK :
		                	ContactsData contacts3 = new ContactsData();
		        			contacts3.setName(name);
		        			contacts3.setNumber(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		        			image_uri = c.getString(c.getColumnIndex(Phone.PHOTO_URI));
		        			 if(image_uri !=null){
		        		    	 try {
		        						bitmap = MediaStore.Images.Media
		        						    .getBitmap(getContentResolver(),
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
		        			 contacts3.setBitmap(bitmap);
		        			contactList.add(contacts3);
		                	//Log.d("num", name+"-"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		                		break;
		                case Phone.TYPE_OTHER :
		                	ContactsData contacts1 = new ContactsData();
		        			contacts1.setName(name);
		        			contacts1.setNumber(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		        			image_uri = c.getString(c.getColumnIndex(Phone.PHOTO_URI));
		        			 if(image_uri !=null){
		        		    	 try {
		        						bitmap = MediaStore.Images.Media
		        						    .getBitmap(getContentResolver(),
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
		        			 contacts1.setBitmap(bitmap);
		        			contactList.add(contacts1);
		                	//Log.d("num", name+"-"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		                		break;
		                }
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

			setSupportProgressBarIndeterminateVisibility(false);
			setListAdapter(new ListAdapterContacts(ContactsListActivity.this, contactList));
			
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			setSupportProgressBarIndeterminateVisibility(true);
		}

	}
}