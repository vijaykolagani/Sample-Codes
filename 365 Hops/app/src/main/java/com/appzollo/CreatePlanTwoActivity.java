package com.appzollo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.AddImgAdp;
import com.appzollo.classes.AddSDImgAdp;
import com.appzollo.classes.Category;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnUploadImage;
import com.appzollo.classes.PlanStickerDetails;
import com.appzollo.classes.Segments;
import com.appzollo.classes.SettingsListAdapter;
import com.appzollo.classes.UploadImage;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.appzollo.utils.ImageAdapter;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.google.android.gms.maps.GoogleMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by saikrishna on 11/11/14.
 */
public class CreatePlanTwoActivity extends BaseActivity implements DatePickerDialogFragment.DatePickerDialogHandler,
        OnLoadCompleteListener,OnUploadImage {
    private GoogleMap map;
    private GPSTracker gps;
    private double latitude, longitude;
    private Button done, cancel, bt_gallery,bt_camera;
    LinearLayout llGallery,llCamera;
    private EditText dateFrom, dateTo;
    TextView segments;
    private final int PICK_IMAGE_MULTIPLE = 1;
    LinearLayout ll_image;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    Uri imageUri;
    int count = 0;
    CreatePlanTwoActivity CameraActivity = null;
    private boolean mEdit;
    private PlanStickerDetails plan;
    ArrayList<Category>   categories;
    ExpandableListView myList;
    private int year;
    private int month;
    private int day;
    int from_to = 0;
    private String selectedImagePath;
    static final int DATE_FROM_PICKER_ID = 1111,DATE_TO_PICKER_ID  = 2222;
    List<String> childData, childLocaldata, childLocaldata1, childLocaldata2, childLocaldata3;
    String img[];
    ArrayList<String> interest_array = new ArrayList<String>();
    String[] base64 = new String[1];
    String imgName="";
    JSONObject jObj;
    Gallery gallery;
    ArrayList<Bitmap> bitmapList= new ArrayList<Bitmap>();

String intIds = "";
    final String[] adv = {
            "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
            "Parasailing","Rafting","Rappelling"
            , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports","Wild Life Safari"
            ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
            "Wildlife"};
    int items[]= {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,21,62,61,63,64,57,58,59};
LinearLayout llDone,llCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.screen_14_form_2);

        mEdit = getIntent().getBooleanExtra("edit", false);
        gallery = (Gallery)findViewById(R.id.gallery);
        if (mEdit) {
            plan = getIntent().getParcelableExtra("plan");
            img = getIntent().getStringArrayExtra("images");
            gallery.setAdapter(new AddImgAdp(this,img));
        }
        Calendar now = Calendar.getInstance();
         year = now.get(Calendar.YEAR);
         month = now.get(Calendar.MONTH); // Note: zero based!
         day = now.get(Calendar.DAY_OF_MONTH);
        segments = (TextView) findViewById(R.id.spSegments);
        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);

        llGallery = (LinearLayout) findViewById(R.id.llGallery);
        llCamera = (LinearLayout) findViewById(R.id.llCamera);

        dateFrom = (EditText) findViewById(R.id.et_date_from);
        dateTo = (EditText) findViewById(R.id.et_date_two);
      /*  bt_gallery = (Button) findViewById(R.id.bt_galllery);
        bt_camera = (Button) findViewById(R.id.bt_camera);*/
        ll_image = (LinearLayout) findViewById(R.id.ll_image);


        segments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSegements();
            }
        });

        gps = new GPSTracker(this);

        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

//            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
//            map.addMarker(marker);

        llDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateFrom.getText().toString().length() > 0 && dateTo.getText().toString().length() > 0) {
                    Date fromDate = CommonsUtils.getDateFromString(dateFrom.getText().toString());
                    Date toDate = CommonsUtils.getDateFromString(dateTo.getText().toString());
                    if (toDate.compareTo(fromDate) <= 0) {
                        Toast.makeText(CreatePlanTwoActivity.this,
                                "To date should be greater then from date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(CreatePlanTwoActivity.this, BroadcastActivity.class)
                                    .putExtra("latitude", getIntent().getExtras().getString("latitude"))
                                    .putExtra("longitude", getIntent().getExtras().getString("longitude"))
                                    .putExtra("palce1", getIntent().getExtras().getString("place1"))
                                    .putExtra("palce2", getIntent().getExtras().getString("place2"))
                                    .putExtra("place3", getIntent().getExtras().getString("place3"))
                                    .putExtra("placefull", getIntent().getExtras().getString("placefull"))
                                    .putExtra("from", dateFrom.getText().toString())
                                    .putExtra("to", dateTo.getText().toString())
                                    .putExtra("segment", intIds)
                                    .putExtra("img", imgName)
                                    .putExtra("message", getIntent().getExtras().getString("message"))
                                    .putExtra("lat_creat", latitude + "")
                                    .putExtra("lon_creat", longitude + "")
                                    .putExtra("which", 1)
                                    .putExtra("edit", mEdit)
                                    .putExtra("plan", plan)
                    );
                } else
                    Toast.makeText(CreatePlanTwoActivity.this, "Date fields should not be empty", Toast.LENGTH_SHORT).show();
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePlanTwoActivity.this.finish();
            }
        });

        dateFrom.setFocusable(false);
        dateTo.setFocusable(false);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_date_from);
                dpb.show();*/
                if (mEdit) {
                    dateFrom.setText(plan.getData().getDate_from());
                    String dateTosplit[], dateFromsplit[];
                    dateTosplit = dateFrom.getText().toString().split("-");
                    year = Integer.parseInt(dateTosplit[0]);
                    month = Integer.parseInt(dateTosplit[1]) - 1;
                    day = Integer.parseInt(dateTosplit[2]);

                }
                    showDialog(DATE_FROM_PICKER_ID);
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_date_two);
                dpb.show();*/
                if (mEdit) {

                    dateTo.setText(plan.getData().getDate_to());
                    String dateTosplit[], dateFromsplit[];
                    dateTosplit = dateTo.getText().toString().split("-");
                    year = Integer.parseInt(dateTosplit[0]);
                    month = Integer.parseInt(dateTosplit[1]) - 1;
                    day = Integer.parseInt(dateTosplit[2]);

                }
                    showDialog(DATE_TO_PICKER_ID);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp"+count+".jpg");


                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                //pic = f;

                startActivityForResult(intent, 1);
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 2);
            }
        });

        if (mEdit) {
            dateFrom.setText(plan.getData().getDate_from());
            dateTo.setText(plan.getData().getDate_to());
            String dateTosplit[],dateFromsplit[];
            dateTosplit = dateFrom.getText().toString().split("-");
            year = Integer.parseInt(dateTosplit[0]);
            month = Integer.parseInt(dateTosplit[1])-1;
            day = Integer.parseInt(dateTosplit[2]);



            //segments.setSelection(getSegmentPos(plan.getData().get);
            if (img != null) {
                for (int i = 0; i < img.length; i++) {
                    View view = View.inflate(this, R.layout.image, null);
                    ImageView image = (ImageView) view.findViewById(R.id.imageView);
                    LoadProfileImageAsync load = new LoadProfileImageAsync(image, this);
                    load.execute(img[i]);
                }
            }
            if (plan.getData().getSegmentid() != null
                    && plan.getData().getSegmentid().size() > 0) {
                for(int l = 0 ; l < plan.getData().getSegmentid().size() ; l++){

                    segments.append("" + Segments.getSegmentName(plan.getData().getSegmentid().get(l).getId()) + ",");
                    segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));
                   // Toast.makeText(getApplicationContext(),plan.getData().getSegmentid().get(l).getId()+",",Toast.LENGTH_SHORT).show();
                    interest_array.add(Segments.getSegmentName(plan.getData().getSegmentid().get(l).getId()));
                    intIds +=  plan.getData().getSegmentid().get(l).getId()+ ",";
                }

            }
        }

    }


    public static String leftPad(int n, int padding) {
        return String.format("%0" + padding + "d", n);
    }

    private int getSegmentPos(String segment) {
        String segments[] = getResources().getStringArray(R.array.events_array);
        int index = 0;
        for (int i = 0; i < segments.length; i++) {
            if (segments[i].equalsIgnoreCase(segment)) {
                return index;
            }
        }
        return index;
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_FROM_PICKER_ID:
                from_to = 0;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);

            case DATE_TO_PICKER_ID:
                from_to = 1;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener1, year, month,day+1);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

                dateFrom.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(" "));

            // Show selected date


        }
    };

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            if (mEdit) {
                dateTo.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(" "));
            }else {
                dateTo.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(" "));
            }

            // Show selected date


        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp"+count+".jpg")) {

                        f = temp;
                        File photo = new File(Environment.getExternalStorageDirectory(), "temp+"+count+".jpg");
                        //pic = photo;
                        break;

                    }

                }

                try {
                    count++;
                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    Toast.makeText(getApplicationContext(),"temp"+count+".jpg",Toast.LENGTH_LONG).show();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);

                    ImageView a = new ImageView(this);
                    a.setImageBitmap(bitmap);
                    bitmapList.add(bitmap);
                    gallery.setAdapter(new AddSDImgAdp(this,bitmapList));
                 //  ll_image.addView(a);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] ba = stream.toByteArray();

                    base64[0] =  Base64.encodeToString(ba, 0);

                    UploadImage upi = new UploadImage(base64,this);
                    upi.execute(new String[]{"plan"});

                   /* uploadToServer up = new uploadToServer();
                    up.execute();*/

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }else if(requestCode == 2){
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();

                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    selectedImagePath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                    ImageView a = new ImageView(this);
                    a.setImageBitmap(bitmap);
                    ll_image.addView(a);

                }
                else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                        ImageView a = new ImageView(this);
                        a.setImageBitmap(image);
                        bitmapList.add(image);
                        gallery.setAdapter(new AddSDImgAdp(this,bitmapList));
                       // ll_image.addView(a);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] ba = stream.toByteArray();

                        base64[0] =  Base64.encodeToString(ba, 0);
                       /* uploadToServer up = new uploadToServer();
                        up.execute();*/
                       UploadImage upi = new UploadImage(base64,this);
                        upi.execute(new String[]{"plan"});

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onDialogDateSet(int i, int year, int month, int date) {
        switch (i) {
            case R.id.et_date_from:
                dateFrom.setText(date + "/" + month + "/" + year);
                break;
            case R.id.et_date_two:
                dateTo.setText(date + "/" + month + "/" + year);
                break;
        }
    }


    /**
     * ********* Convert Image Uri path to physical path *************
     */

    public static String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {


                //imageDetails.setText("No Image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + Path + "\n";

                    // Show Captured Image detail on activity
                    //imageDetails.setText( CapturedImageDetails );

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return "" + imageID;
    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onUploadImage(String result) {
        try {
            jObj = new JSONObject(result);
            JSONArray jsonArray = jObj.getJSONArray("ImageName");
            for(int i=0;i<jsonArray.length();i++){
                imgName =  jsonArray.getString(i);

                Log.d("namess",imgName);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("response" , imgName);
    }


    /**
     * Async task for loading the images from the SD card.
     *
     * @author Android Example
     */

// Class with extends AsyncTask class

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(CreatePlanTwoActivity.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {

                /**  Uri.withAppendedPath Method Description
                 * Parameters
                 *    baseUri  Uri to append path segment to
                 *    pathSegment  encoded path segment to append
                 * Returns
                 *    a new Uri based on baseUri with the given segment appended to the path
                 */

                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);

                /**************  Decode an input stream into a bitmap. *********/
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    /********* Creates a new bitmap, scaled from an existing bitmap. ***********/

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (mBitmap != null) {
                // Set Image to ImageView
                try {
                    ll_image.removeAllViews();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                ImageView img = new ImageView(getApplicationContext());
                img.setImageBitmap(mBitmap);
                ll_image.addView(img);
            }

        }

    }
    public void showSegements(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select something");


        builder
                .setCancelable(false)
                .setPositiveButton("Done",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });
         myList = new ExpandableListView(this);
         categories = Category.getCategories();

        SettingsListAdapter  adapter = new SettingsListAdapter(this,
                categories, myList,interest_array);
       /* ExpandableCheckListAdapter myAdapter = new ExpandableCheckListAdapter(this,headerData, mExpListData);
        myList.setAdapter(myAdapter);
*/
        myList.setAdapter(adapter);
        builder.setView(myList);
        AlertDialog dialog = builder.create();
        dialog.show();
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                CheckedTextView checkbox = (CheckedTextView) v.findViewById(R.id.list_item_text_child);
                checkbox.toggle();

                // find parent view by tag

                if(checkbox.isChecked()) {
                    interest_array.add(checkbox.getText().toString());
                    if (segments.getText().toString().length() == 0) {

                        segments.append(categories.get(groupPosition).children.get(childPosition).name);
                        segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    } else {
                        if (segments.getText().toString().contains(categories.get(groupPosition).children.get(childPosition).name)) {

                        } else
                            segments.append("," + categories.get(groupPosition).children.get(childPosition).name);
                        segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    }
                    for (int l = 0; l < adv.length; l++) {

                        if (adv[l].equalsIgnoreCase(categories.get(groupPosition).children.get(childPosition).name)) {
                            intIds += items[l] + ",";
                        }

                    }

                }else{
                    for(int l = 0 ; l < adv.length ; l++){
                        if(adv[l].equalsIgnoreCase(checkbox.getText().toString())){
                            interest_array.remove(checkbox.getText().toString());
                           // Toast.makeText(getApplication(),interest_array+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name+",",""));
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name,""));
                    for (int l = 0; l < adv.length; l++) {

                        if (adv[l].equalsIgnoreCase(categories.get(groupPosition).children.get(childPosition).name)) {
                            intIds = intIds.replaceFirst(items[l] + ",","");
                        }

                    }
                    // Toast.makeText(getApplicationContext(),segments.getText().toString(),Toast.LENGTH_SHORT).show();
                }

                Log.d("items", intIds + "");
                View parentView = myList.findViewWithTag(categories.get(groupPosition).name);
                if (parentView != null) {
                    TextView sub = (TextView) parentView.findViewById(R.id.list_item_text_subscriptions);

                }
                return true;
            }

        });
    }
    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(CreatePlanTwoActivity.this);
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", base64[0]));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            nameValuePairs.add(new BasicNameValuePair("location", "plan"));
            String st="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://www.365hops.com/webservice/exiv2.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return st;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("plan log",result);


            pd.hide();
            pd.dismiss();
        }
    }
}
