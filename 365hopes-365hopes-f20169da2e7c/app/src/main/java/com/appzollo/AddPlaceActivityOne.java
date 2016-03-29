package com.appzollo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appzollo.classes.AddImgAdp;
import com.appzollo.classes.AddSDImgAdp;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnUploadImage;
import com.appzollo.classes.PlaceStickerDetailsPojo;
import com.appzollo.classes.UploadImage;
import com.appzollo.interfaces.OnLatlngCompleteListener;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddPlaceActivityOne extends FragmentActivity implements OnLatlngCompleteListener,
        OnLoadCompleteListener,OnUploadImage {
    private Button cancel, done;
    private GoogleMap map;
    private GPSTracker gps;
    LinearLayout llgallery,llCamera;
    EditText et1;

    private double latitude;
    private double longitude;
    String place;
    int count = 0;

    public ArrayAdapter<String> adapter;
    public AutoCompleteTextView textView;
    LinearLayout ll_image;
    private String selectedImagePath;
    private boolean mEdit;
    private PlaceStickerDetailsPojo placeDetails;
    String img[];
    LinearLayout llDone,llCancel;


    String[] base64 = new String[1];
    String imgName="";
    JSONObject jObj;
    Gallery gallery;
    ArrayList<Bitmap> bitmapList= new ArrayList<Bitmap>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_12a_1);

        mEdit = getIntent().getBooleanExtra("edit", false);
        gallery = (Gallery)findViewById(R.id.gallery);
        if (mEdit) {
            placeDetails = getIntent().getParcelableExtra("place");
            img = getIntent().getStringArrayExtra("images");
            gallery.setAdapter(new AddImgAdp(this,img));
        }

        map = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();
        et1 = (EditText)findViewById(R.id.etName);

        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCamera = (LinearLayout) findViewById(R.id.llCamera);
        llgallery = (LinearLayout) findViewById(R.id.llGallery);

        ll_image = (LinearLayout) findViewById(R.id.ll_image);
        textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        gps = new GPSTracker(this);

        if(gps.canGetLocation()) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).title("Current Location ");

            map.addMarker(marker);
            final CameraPosition currentlocation =
                    new CameraPosition.Builder().target(new LatLng(gps.getLatitude(), gps.getLongitude()))
                            .zoom(4f)
                            .bearing(0)
                            .tilt(25)
                            .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(currentlocation));
            if(!mEdit) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(AddPlaceActivityOne.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    textView.setText(address + "," + city + "," + country);
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            gps.showSettingsAlert();
        }
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(AddPlaceActivityOne.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
                    textView.setText(address+","+city+","+country);
                    map.clear();
                    drawMarker(latLng);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        llDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().length() > 0 && textView.getText().toString().length() > 0) {
                    if (latitude == 0 || longitude ==0) {
                        Toast.makeText(AddPlaceActivityOne.this, "please enter a valid location",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String place1 = "";
                    String place2 = "";
                    String place3 = "";
                    String places[] = textView.getText().toString().split(",");

                    if (places.length > 0) {
                        place1 = places[0];

                        if (places.length > 1) {
                            place2 = places[1];

                            if (places.length > 2) {

                                place3 = places[2];

                            }
                        }
                    }
                    startActivity(new Intent(AddPlaceActivityOne.this, AddPlaceActivityTwo.class)
                                    .putExtra("name", et1.getText().toString().replace(" ", "%20"))
                                    .putExtra("latitude", latitude+"")
                                    .putExtra("longitude", longitude+"")
                                    .putExtra("img", imgName)
                                    .putExtra("place1", place1)
                                    .putExtra("place2", place2)
                                    .putExtra("place3", place3)
                                    .putExtra("placefull", textView.getText().toString().replace(" ",""))
                                    .putExtra("edit", mEdit)
                                    .putExtra("place", placeDetails)
                    );
                }else{
                    Toast.makeText(getApplicationContext(),"Fields should not be empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlaceActivityOne.this.finish();
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.auto_list_item);
        textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        adapter.setNotifyOnChange(true);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);

                GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), AddPlaceActivityOne.this);
                task.execute("");

              /*  Geocoder coder = new Geocoder(AddPlaceActivityOne.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(selection, 50);
                    for(Address add : adresses){
                         longitude = add.getLongitude();
                         latitude = add.getLatitude();
                        Toast.makeText(getApplication(),latitude+","+longitude,Toast.LENGTH_SHORT).show();
                        map.clear();
                        drawMarker(new LatLng(latitude, longitude));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //TODO Do something with the selected text
            }
        });
        textView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count%3 == 1) {
                    latitude = 0;
                    longitude = 0;
                    adapter.clear();
                    GetPlaces task = new GetPlaces();
                    //now pass the argument in the textview to the task
                    task.execute(textView.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
// TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

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
        llgallery.setOnClickListener(new View.OnClickListener() {
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
            et1.setText("" + placeDetails.getData().get(0).getName());
            textView.setText("" + placeDetails.getData().get(0).getLocation());
            latitude = Double.parseDouble(placeDetails.getData().get(0).getCreat_lat());
            longitude = Double.parseDouble(placeDetails.getData().get(0).getCreat_lon());
            if (img != null) {
                for (int i = 0; i < img.length; i++) {
                    View view = View.inflate(this, R.layout.image, null);
                    ImageView image = (ImageView) view.findViewById(R.id.imageView);
                    LoadProfileImageAsync load = new LoadProfileImageAsync(image, this);
                    load.execute(img[i]);
                }
            }
        }
    }

    @Override
    public void OnLatlngComplete(double lat, double lng) {
        longitude =lng;
        latitude = lat;
        Toast.makeText(getApplication(),latitude+","+longitude,Toast.LENGTH_SHORT).show();
        map.clear();
        drawMarker(new LatLng(latitude, longitude));
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
                  //  ll_image.addView(a);
                    bitmapList.add(bitmap);
                    gallery.setAdapter(new AddSDImgAdp(this,bitmapList));
                    //  ll_image.addView(a);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] ba = stream.toByteArray();

                    base64[0] =  Base64.encodeToString(ba, 0);

                    UploadImage upi = new UploadImage(base64,this);
                    upi.execute(new String[]{"plan"});


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
                  //  ll_image.addView(a);
                    bitmapList.add(bitmap);
                    gallery.setAdapter(new AddSDImgAdp(this,bitmapList));
                    //  ll_image.addView(a);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] ba = stream.toByteArray();

                    base64[0] =  Base64.encodeToString(ba, 0);

                    UploadImage upi = new UploadImage(base64,this);
                    upi.execute(new String[]{"place"});

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
                      //  ll_image.addView(a);
                        bitmapList.add(image);
                        gallery.setAdapter(new AddSDImgAdp(this,bitmapList));
                        //  ll_image.addView(a);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] ba = stream.toByteArray();

                        base64[0] =  Base64.encodeToString(ba, 0);

                        UploadImage upi = new UploadImage(base64,this);
                        upi.execute(new String[]{"place"});

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

    private void drawMarker(LatLng point){
        // map.clear();
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        map.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .zoom(15)
                .target(point)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
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
            textView.setAdapter(adapter);

            for (String string : result) {

                Log.d("YourApp", "onPostExecute : result = " + string);
                adapter.add(string);
                adapter.notifyDataSetChanged();

            }

            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEdit = getIntent().getBooleanExtra("edit", false);
        if (mEdit) {
            if (textView != null) {
                String selection = textView.getText().toString();
                if (selection != null
                        && !selection.equals("")) {
                    GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), AddPlaceActivityOne.this);
                    task.execute("");
                }
            }
        }
    }
}