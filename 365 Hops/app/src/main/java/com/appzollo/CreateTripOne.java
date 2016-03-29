package com.appzollo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.appzollo.classes.EventDetail;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saikrishna on 11/11/14.
 */
public class CreateTripOne extends BaseActivity  {
    private GoogleMap map;
    private GPSTracker gps;
    private double latitude, longitude;
    private Button done, cancel, bt_gallery,bt_camera;
    Spinner segments;
    private final int PICK_IMAGE_MULTIPLE = 1;
    LinearLayout ll_image;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    Uri imageUri;
    int count = 0;
    CreatePlanTwoActivity CameraActivity = null;
    private boolean edit;
    private EventDetail events;

    int from_to = 0;
    private String selectedImagePath;
    static final int DATE_FROM_PICKER_ID = 1111,DATE_TO_PICKER_ID  = 2222;

    String[] items = {
            "Bungee Jump",
            "Camping","Cycling", "Fishing Tours","Flying Fox",
            "Hot Aur Bollooning", "Jeep Safari", "Kayaking", "Motorcycling", "Mountain Biking","Mountaineering", "Paintball",
            "Paragliding", "Parasailing","Rafting", "Rappelling","Rock Climbing",  "Running", "Scuba Diving", "Skiing",
            "Skydiving","Snorkelling",
            "Trekking", "Wall Climbing","Water Sports","Art And Culture Trip","Food Trip","Historical Trip","Photogarphy Triop",
            "Cultural Tour","Historical Tour","Wildlife"
    };
    int[] itemId = {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,62,61,63,64,57,58,59};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_create_trip_one);

        edit = getIntent().getBooleanExtra("edit", false);
        if (edit) {
            events = getIntent().getParcelableExtra("event");
        }


        segments = (Spinner) findViewById(R.id.spSegments);
        done = (Button) findViewById(R.id.bt_done);
        cancel = (Button) findViewById(R.id.bt_cancel);
        bt_gallery = (Button) findViewById(R.id.bt_galllery);
        bt_camera = (Button) findViewById(R.id.bt_camera);
        ll_image = (LinearLayout) findViewById(R.id.ll_image);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
        segments.setAdapter(adapter);

        gps = new GPSTracker(this);

        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

//            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
//            map.addMarker(marker);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startActivity(new Intent(CreateTripOne.this, TripDetailsOneActivity.class)
                                    .putExtra("name", getIntent().getExtras().getString("name"))
                                    .putExtra("where", getIntent().getExtras().getString("where"))
                                    .putExtra("latitude", getIntent().getExtras().getString("latitude"))
                                    .putExtra("longitude", getIntent().getExtras().getString("longitude"))
                                    .putExtra("place1", getIntent().getExtras().getString("place1"))
                                    .putExtra("place2", getIntent().getExtras().getString("place2"))
                                    .putExtra("place3", getIntent().getExtras().getString("place3"))
                                    .putExtra("placefull", getIntent().getExtras().getString("placefull"))
                                    .putExtra("segment", itemId[segments.getSelectedItemPosition()]+"")
                                    .putExtra("img", "")
                                    .putExtra("edit", edit)
                                    .putExtra("event", events)

                    );
                    }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTripOne.this.finish();
            }
        });


        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp"+count+".jpg");


                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                //pic = f;

                startActivityForResult(intent, 1);
            }
        });

        bt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 2);
            }
        });

    }







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
                    ll_image.addView(a);


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
                        ll_image.addView(a);

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


    /**
     * Async task for loading the images from the SD card.
     *
     * @author Android Example
     */

// Class with extends AsyncTask class

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(CreateTripOne.this);

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


}
