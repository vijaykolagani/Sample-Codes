package com.appzollo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appzollo.classes.PlanStickerDetails;
import com.appzollo.interfaces.OnLatlngCompleteListener;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TagPlaceActivity extends FragmentActivity implements OnLatlngCompleteListener {
    private Button done, cancel;
    private GoogleMap map;
    private GPSTracker gps;
    private double latitude;
    private double longitude;
    String place;

    public ArrayAdapter<String> adapter;
    public AutoCompleteTextView textView;
    EditText et1,et2,et3,etAbout;
    private boolean mEdit;
    private PlanStickerDetails plan;
    String img[];
    LinearLayout llDone,llCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_14_form_1);

        mEdit = getIntent().getBooleanExtra("edit", false);
        if (mEdit) {
            plan = getIntent().getParcelableExtra("plan");
            img = getIntent().getStringArrayExtra("images");
        }

        map = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();
        et1 = (EditText)findViewById(R.id.et_about1);
        et2 = (EditText)findViewById(R.id.et_about2);
        et3 = (EditText)findViewById(R.id.et_about3);
        etAbout = (EditText)findViewById(R.id.et_about);

        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        gps = new GPSTracker(this);
    if(gps.canGetLocation()) {
        MarkerOptions marker = new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLatitude())).title("Current Location ");

        map.addMarker(marker);
        final CameraPosition currentloc =
                new CameraPosition.Builder().target(new LatLng(gps.getLatitude(), gps.getLongitude()))
                        .zoom(4f)
                        .bearing(0)
                        .tilt(25)
                        .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(currentloc));
        if(!mEdit){
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(TagPlaceActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                textView.setText(address+","+city+","+country);
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
                geocoder = new Geocoder(TagPlaceActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    textView.setText(address+","+city+","+country);
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
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
                if(etAbout.getText().toString().length() > 0 ) {
                    if (latitude == 0 || longitude ==0) {
                        Toast.makeText(TagPlaceActivity.this, "please enter a valid location",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textView.getText().toString().length() > 0) {
                        Toast.makeText(getApplicationContext(),textView.getText().toString(),Toast.LENGTH_LONG).show();
                        String place1 = "";
                        String place2 = "";
                        String place3 = "";
                        String places[] = textView.getText().toString().split(",");

                        if (places.length > 0) {
                            place1 = places[0].replace(" ", "");

                            if (places.length > 1) {
                                place2 = places[1].replace(" ", "");

                                if (places.length > 2) {

                                    place3 = places[2].replace(" ", "");

                                }
                            }
                        }
                        startActivity(new Intent(TagPlaceActivity.this, CreatePlanTwoActivity.class)
                                        .putExtra("message", etAbout.getText().toString() )
                                        .putExtra("latitude", latitude+"")
                                        .putExtra("longitude", longitude+"")
                                        .putExtra("place1", place1)
                                        .putExtra("place2", place2)
                                        .putExtra("place3", place3)
                                        .putExtra("placefull", textView.getText().toString())
                                        .putExtra("edit", mEdit)
                                        .putExtra("plan", plan)
                                        .putExtra("images", img)
                        );
                    }else{
                        Toast.makeText(getApplicationContext(),"Tag your place should not be empty",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Fill in about your plan should not be empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagPlaceActivity.this.finish();
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.auto_list_item);

        adapter.setNotifyOnChange(true);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), TagPlaceActivity.this);
                task.execute("");


              /*  Geocoder coder = new Geocoder(TagPlaceActivity.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(selection, 50);
                    for(Address add : adresses){
                             longitude = add.getLongitude();
                               latitude = add.getLatitude();
                        //Toast.makeText(getApplication(),latitude+","+longitude,Toast.LENGTH_SHORT).show();
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

        if (mEdit) {
            etAbout.setText(plan.getData().getTitle());
            textView.setText(plan.getData().getWhere_area());
            latitude = Double.parseDouble(plan.getData().getLatitude_creat());
            longitude = Double.parseDouble(plan.getData().getLongitude_creat());
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
                    GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), TagPlaceActivity.this);
                    task.execute("");
                }
            }
        }
    }
}