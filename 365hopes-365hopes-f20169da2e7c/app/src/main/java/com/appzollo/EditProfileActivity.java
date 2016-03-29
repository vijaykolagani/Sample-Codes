package com.appzollo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.MultipartEntity;
import com.appzollo.classes.OnUploadImage;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.classes.UploadImage;
import com.appzollo.interfaces.OnLatlngCompleteListener;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EditProfileActivity extends FragmentActivity implements View.OnClickListener,
        DatePickerDialogFragment.DatePickerDialogHandler,OnLoadCompleteListener,
        OnLatlngCompleteListener,OnUploadImage {

    EditText etName, etWork, etGender,  etEmail, etMobile;
    Spinner spGender;
    LinearLayout llDob;
    ImageView ivSave;
    RoundedImageView ivdp;
    TextView tvDOb;
    int date, month, year,day;
    // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;
    private JSONParser jParser;
    HashMap<String, String> map = new HashMap<String, String>();
    public ArrayAdapter<String> adapter;
    public AutoCompleteTextView etLocation;
    static final int DATE_START_PICKER_ID = 1111,DATE_END_PICKER_ID  = 2222;
    double latitude=0,longitude=0;
    boolean adressCheck = false;
    String[] base64 = new String[1];
    String imgName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        jParser = new JSONParser();
        etName = (EditText) findViewById(R.id.et_name);
        etWork = (EditText) findViewById(R.id.et_work);
        spGender = (Spinner) findViewById(R.id.et_gender);
        tvDOb = (TextView) findViewById(R.id.et_dob);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        //  etLocation = (EditText) findViewById(R.id.et_location);
        ivSave = (ImageView) findViewById(R.id.ivSave);
        ivdp = (RoundedImageView) findViewById(R.id.imageView);
        llDob = (LinearLayout)findViewById(R.id.ll_dob);

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc1=null,loc2=null,loc3=null;
                if (latitude == 0
                        || longitude == 0) {
                    Toast.makeText(EditProfileActivity.this, "please enter a valid location",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String loc[] = etLocation.getText().toString().split(",");
                if(loc.length>0){
                    loc1=loc[0];
                    if(loc.length>1) {
                        loc2 = loc[1];
                        if (loc.length > 2)
                            loc3 = loc[2];
                    }
                }
                Geocoder coder = new Geocoder(EditProfileActivity.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(etLocation.getText().toString(), 50);
                    for(Address add : adresses){
                        longitude = add.getLongitude();
                        latitude = add.getLatitude();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String url = "http://www.365hops.com/webservice/controller.php?Servicename=editUserProfile&userid=" + CommonsUtils.getPrefString(EditProfileActivity.this, "userid")
                        + "&name=" + etName.getText().toString() + "&about=&gender=" + spGender.getSelectedItem().toString() +
                        "&dob=" + day + "-" + month + "-" + year + "&phone="+etMobile.getText().toString()+"&occupation="
                        + etWork.getText().toString() + "&language=English&location1="+loc1+"&location2="+loc2+"&location3="+loc3+"&location_full="
                        + etLocation.getText().toString() +"&lat="+latitude+""+"&lon="+longitude+"";
                Log.d("urlll",url);
                GetSummaryTask gst = new GetSummaryTask();
                //  Toast.makeText(getApplication(), spGender.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if(latitude!=0&&longitude!=0&adressCheck)
                     gst.execute(url.replace(" ","%20"));
                else
                    Toast.makeText(getApplicationContext(),"Please enter proper location details!!",Toast.LENGTH_SHORT).show();
            }
        });

        llDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_dob);
                dpb.show();*/
                showDialog(DATE_START_PICKER_ID);
            }
        });
        if(getIntent().getExtras().getString("name").toString().equalsIgnoreCase("null")){
            etName.setText("");
        }else
            etName.setText(getIntent().getExtras().getString("name"));
        if(getIntent().getExtras().getString("gender").equalsIgnoreCase("Male")){
            spGender.setSelection(1);
        }else{
            spGender.setSelection(2);
        }
        if(getIntent().getExtras().getString("occupation").toString().equalsIgnoreCase("null"))
            etWork.setText(" ");
        else
            etWork.setText(getIntent().getExtras().getString("occupation"));
        if(getIntent().getExtras().getString("mobile").toString().equalsIgnoreCase("null"))
            etMobile.setText(" ");
        else
            etMobile.setText(getIntent().getExtras().getString("mobile"));
        if(getIntent().getExtras().getString("email").toString().equalsIgnoreCase("null"))
            etEmail.setText(" ");
        else
            etEmail.setText(getIntent().getExtras().getString("email"));
        if(getIntent().getExtras().getString("dob").toString().equalsIgnoreCase("null"))
            tvDOb.setText(" ");
        else {
            tvDOb.setText(getIntent().getExtras().getString("dob"));
            String str[] = tvDOb.getText().toString().split("-");
            year = Integer.parseInt(str[0]);
            month = Integer.parseInt(str[1]);
            day = Integer.parseInt(str[2]);

        }

        LoadProfileImageAsync task = new LoadProfileImageAsync(ivdp, this);
        task.execute(getIntent().getExtras().getString("user_img"));
        ivdp.setMinimumHeight(210);
        ivdp.setMinimumWidth(210);
        ivdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);*/
                takePhoto();
            }
        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.auto_list_item);
        etLocation = (AutoCompleteTextView)
                findViewById(R.id.et_location);
        if(getIntent().getExtras().getString("location").toString()!="null")
            etLocation.setText(getIntent().getExtras().getString("location"));

        if(etLocation.getText().toString() != null){
            GetPlaces task1 = new GetPlaces();
            task1.execute(etLocation.getText().toString());
        }
        adapter.setNotifyOnChange(true);
        etLocation.setAdapter(adapter);
        etLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                Geocoder coder = new Geocoder(EditProfileActivity.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(selection, 50);
                    for(Address add : adresses){
                      longitude = add.getLongitude();
                     latitude = add.getLatitude();

                    }
                    GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), EditProfileActivity.this);
                    task.execute("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
              //  Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
                //TODO Do something with the selected text
            }
        });

        etLocation.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count%3 == 1) {
                    latitude = 0;
                    longitude = 0;
                    adapter.clear();
                    GetPlaces task = new GetPlaces();
                    //now pass the argument in the textview to the task

                    task.execute(etLocation.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
// TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_START_PICKER_ID:
                //  from_to = 0;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month-1,day);
            case DATE_END_PICKER_ID:
                // from_to = 1;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month-1,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth+1;
            day   = selectedDay;


            tvDOb.setText(new StringBuilder().append(year)
                    .append("-").append(month).append("-").append(day)
                    .append(" "));

            // Show selected date

        }
    };

    @Override
    public void OnLatlngComplete(double lat, double lng) {
        longitude =lng;
        latitude = lat;
    }

    @Override
    public void onUploadImage(String result) {
        Log.d("response" , result);
    }

    class GetPlaces extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        // three dots is java for an array of strings
        protected ArrayList<String> doInBackground(String... args) {

            Log.d("gottaGo", "doInBackground");

            ArrayList<String> predictionsArr = new ArrayList<String>();

            try {

                URL googlePlaces = new URL(
                        // URLEncoder.encode(url,"UTF-8");
                        "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + URLEncoder.encode(args[0].toString(), "UTF-8") + "&types=geocode&language=en&sensor=true&key=AIzaSyAW2O9rBaQh4ooLuxBk_H-t62uHEz3AHFY");
                URLConnection tc = googlePlaces.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        tc.getInputStream()));

                String line;
                StringBuffer sb = new StringBuffer();
                //take Google's legible JSON and turn it into one big string.
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                //turn that string into a JSON object
                JSONObject predictions = new JSONObject(sb.toString());
                //now get the JSON array that's inside that object
                JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    //add each entry to our array
                    predictionsArr.add(jo.getString("description"));
                }
            } catch (IOException e) {

                Log.e("YourApp", "GetPlaces : doInBackground", e);

            } catch (JSONException e) {

                Log.e("YourApp", "GetPlaces : doInBackground", e);

            }

            return predictionsArr;

        }

//then our post

        @Override
        protected void onPostExecute(ArrayList<String> result) {

                Log.d("YourApp", "onPostExecute : " + result.size());
//update the adapter
                adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.auto_list_item);
                adapter.setNotifyOnChange(true);
//attach the adapter to textview
                etLocation.setAdapter(adapter);

                for (String string : result) {

                    Log.d("YourApp", "onPostExecute : result = " + string);
                    adapter.add(string);
                    adapter.notifyDataSetChanged();

                }
                adressCheck = true;
                Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());


        }
    }

    public class GetSummaryTask extends AsyncTask<String, Void, String> {

        private int asy = 0;
        ProgressDialog dialog;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(urls[0]);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                // if(statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                try {
                    //Read the server response and attempt to parse it as JSON
                    Reader reader = new InputStreamReader(content);
                    Log.d("", reader.toString());
//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");

                    String json = jParser.getJSONFromUrl(urls[0]);
                    JSONObject jObj = new JSONObject(json);

                    String success = jObj.getString("success");

                    map.put("sucess", success);

                    //JSONArray data_image = data.getJSONArray("image");
                    map.put("message", jObj.getString("message"));


                   /* Gson gson = new Gson();

                    placeDetailStickers = gson.fromJson(json, PlaceStickerDetailsPojo.class);
*/

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e("Place To Visit Detail", "Failed to parse JSON due to: " + ex);
                    // failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("Place To Visit Detail", "Failed to send HTTP POST request due to: " + ex);
                //failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("user", etName.getText().toString());
            map.put("location", etLocation.getText().toString());
            CommonsUtils.putPrefStrings(getApplicationContext(),map);
            dialog.dismiss();
            //  Toast.makeText(getApplicationContext(), map.get("message"), Toast.LENGTH_SHORT);
            Intent intent=new Intent();
            intent.putExtra("edit", "0");
            setResult(RESULT_OK, intent);
            Intent i = new Intent(EditProfileActivity.this,ProfileActivity.class);
            i.putExtra("my", true);
            i.putExtra("userid", CommonsUtils.getPrefString(EditProfileActivity.this, "userid"));

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(i);
            finish();


        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(EditProfileActivity.this, "",
                    "Loading. Please wait...", true);
            dialog.setCancelable(false);
        }
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            ivdp.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent=new Intent();
            intent.putExtra("edit", "1");
            setResult(RESULT_OK, intent);
            finish();
            finish();

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSave:
                //String url =
        }
    }

    @Override
    public void onDialogDateSet(int i, int year, int month, int date) {
        switch (i) {
            case R.id.et_dob:
                tvDOb.setText(date + "/" + month + "/" + year);
                this.date = date;
                this.month = month;
                this.year = year;

                break;

        }
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("edit", "1");
        setResult(RESULT_OK, intent);

        super.onBackPressed();
        //finish();
    }
    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                //  ProgressBar b = (ProgressBar)view.findViewById(R.id.progressBar);
                // b.setVisibility(View.GONE);
                Drawable d = new BitmapDrawable(bitmap);
                if (d != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        view.setBackground(d);
                    } else {
                        view.setBackgroundDrawable(d);
                    }
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras().getString("location").toString()!="null") {
            GetLanLng latlng = new GetLanLng(getIntent().getExtras().getString("location").toString().replaceAll("\\s+", "")
                    .replace(" ","%20").toString(), EditProfileActivity.this);
            latlng.execute("");
        }
    }

    private void takePhoto() {
//		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//		startActivityForResult(intent, 0);
        dispatchTakePictureIntent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.i("pic", "onActivityResult: " + this);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            setPic();
//			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//			if (bitmap != null) {
//				mImageView.setImageBitmap(bitmap);
//				try {
//					sendPhoto(bitmap);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
        }
    }

    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);
    }

    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        protected Void doInBackground(Bitmap... bitmaps) {
            if (bitmaps[0] == null)
                return null;
            setProgress(0);

            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httppost = new HttpPost(
                        "http://www.365hops.com/webservice/directory/exiv2.phpsocial/ProfileThumb/Thumb/"); // server

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("myFile",
                        System.currentTimeMillis() + ".jpg", in);
                httppost.setEntity(reqEntity);

                Log.i("pic", "request " + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    response = httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (response != null)
                        Log.i("Pic", "response " + response.getStatusLine().toString());
                } finally {

                }
            } finally {

            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(EditProfileActivity.this, "uploaded", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        Log.i("pic", "onSaveInstanceState");
    }

    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;
    File photoFile = null;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * http://developer.android.com/training/camera/photobasics.html
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("pic", "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivdp.getWidth();
        int targetH = ivdp.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Matrix mtx = new Matrix();
        mtx.postRotate(90);
        // Rotating Bitmap
        Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap)
            bitmap.recycle();

        ivdp.setImageBitmap(rotatedBMP);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        rotatedBMP.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] ba = stream.toByteArray();

        base64[0] =  Base64.encodeToString(ba, 0);
       
        UploadImage upi = new UploadImage(base64,this);
        upi.execute(new String[]{"profile"});

        try {
            //sendPhoto(rotatedBMP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}