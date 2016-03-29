package com.appzollo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appzollo.classes.EventDetail;
import com.appzollo.classes.PlaceStickerDetailsPojo;
import com.appzollo.classes.PlanStickerDetails;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.interfaces.OnLatlngCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;

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

/**
 * Created by saikrishna on 11/11/14.
 */
public class BroadcastActivity extends BaseActivity implements OnPostCompleteListener, OnLatlngCompleteListener {
    private Button done, cancel;
    public ArrayAdapter<String> adapter;
    public AutoCompleteTextView textView;
    Bundle extras;
    int form;
    private boolean edit;
    private EventDetail events;
    private PlanStickerDetails plan;
    private double lat;
    private double lon;
    String[] broadcast;
    private PlaceStickerDetailsPojo placeDetails;
    GPSTracker gps;
LinearLayout llDone,llCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.broadcast_done);


        edit = getIntent().getBooleanExtra("edit", false);
        gps = new GPSTracker(this);
        if (edit) {
            events = getIntent().getParcelableExtra("event");
            plan = getIntent().getParcelableExtra("plan");
            placeDetails = getIntent().getParcelableExtra("place");
        }

        extras = getIntent().getExtras();
        Log.d("hjhj", extras.getString("segment")+"");
        form = extras.getInt("which");
        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);

        if(!edit){
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(BroadcastActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                textView.setText(address + "," + city + "," + country);
                lat = gps.getLatitude();
                lon = gps.getLongitude();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (events != null) {
            textView.setText(events.getFull_creat());
            GetLanLng task = new GetLanLng(textView.getText().toString().replace(" ", "%20").toString(), BroadcastActivity.this);
            task.execute("");

        } else if (plan != null) {
            textView.setText(plan.getData().getCreat_full());
           GetLanLng task = new GetLanLng(textView.getText().toString().replace(" ", "%20").toString(), BroadcastActivity.this);
            task.execute("");
          /*   Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(plan.getData().getLatitude_creat()),Double.parseDouble(plan.getData().getLongitude_creat()), 1);
                if(addresses != null) {
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    if(address == null){
                        address = "";
                    }
                    if(city == null){
                        city = "";
                    }
                    if(country == null){
                        country = "";
                    }
                    textView.setText(address+","+city+","+country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
        } else if(placeDetails != null){
            textView.setText(placeDetails.getData().get(0).getCreat_full());
            GetLanLng task = new GetLanLng(textView.getText().toString().replace(" ", "%20").toString(), BroadcastActivity.this);
            task.execute("");
           /* GetLanLng task = new GetLanLng(textView.getText().toString().replace(" ", "%20").toString(), BroadcastActivity.this);
            task.execute("");
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(placeDetails.getData().get(0).getCreat_lat()),Double.parseDouble(placeDetails.getData().get(0).getCreat_lon()), 1);
                if(addresses != null) {
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    if(address == null){
                        address = "";
                    }
                    if(city == null){
                        city = "";
                    }
                    if(country == null){
                        country = "";
                    }
                    textView.setText(address+","+city+","+country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
        }

        llDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lat == 0 || lon == 0) {
                    Toast.makeText(BroadcastActivity.this, "please enter a valid location",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textView.getText().toString().length() > 0) {
                    broadcast = textView.getText().toString().split(",");
                    if (form == 1) {
                        String latitude = extras.getString("latitude");
                        String longitude = extras.getString("latitude");
                        String place1 = "";
                        String place2 = "";
                        String place3 = extras.getString("place3");
                        String places[] =  extras.getString("placefull").split(",");
                        if(places.length>0){
                            place1 = places[0];
                            if(places.length>1)
                                place2 = places[1];
                        }
                        String userid = CommonsUtils.getPrefString(BroadcastActivity.this, "userid");
                        String data = "http://www.365hops.com/webservice/controller.php?Servicename=addBroadcostMessage&" +
                                "location=" + extras.getString("placefull").replace(" ", "%20") +
                                "&latitude=" + latitude +
                                "&longitude=" + extras.getString("longitude") +
                                "&place1=" + place1 +
                                "&place2=" + place2 +
                                "&place3=" + place3 +
                                "&placefull=" + extras.getString("placefull").replace(" ", "%20") +
                                "&from=" + extras.getString("from") +
                                "&to=" + extras.getString("to") +
                                "&segment=" + extras.getString("segment").replace(" ","")+""+
                                "&message=" + extras.getString("message") +
                                "&img=" + extras.getString("img") +
                                "&lat_creat=" + lat +
                                "&lon_creat=" + lon +
                                "&creat_full=" + textView.getText().toString().replace(" ","%20");

                                if (broadcast == null) {
                                    data += "&creat_city=" + broadcast[0];
                                } else {
                                    data += "&creat_city=" + broadcast[0];
                                }
                        if(edit){
                            data +="&broadcstid="+plan.getData().getId();
                        }
                                data += "&userid=" + userid ;
                        Log.d("url", data);

                        PostDataToServer posData = new PostDataToServer(BroadcastActivity.this);
                        posData.execute(data.replace(" ", "%20"));

                    } else if (form == 2) {
                        String userid = CommonsUtils.getPrefString(BroadcastActivity.this, "userid");
                        String name = extras.getString("name").replace(" ", "%20");
                        String location = extras.getString("placefull").replace(" ", "%20");
                        String place1 = "";
                        String place2 = "";
                        String place3 = extras.getString("place3");
                        String places[] =  extras.getString("placefull").split(",");
                        if(places.length>0){
                            place1 = places[0];
                            if(places.length>1)
                                place2 = places[1];
                        }
                        String palcefull = extras.getString("placefull").replace(" ", "%20");
                        String latitude = extras.getString("latitude");
                        String longitude = extras.getString("longitude");
                        String todo = extras.getString("thingstodo").replace(" ", "%20");
                        String howto = extras.getString("howtoreach").replace(" ", "%20");
                        String create_lat = extras.getString("lat_creat");
                        String create_lon = extras.getString("lon_creat");
                        String create_city = textView.getText().toString().replace(" ", "%20");
                        String segment = extras.getString("segment");

                        String data;

                        if (edit) {
                            data = "http://www.365hops.com/webservice/controller.php?Servicename=editPlacetoVisit" +
                                    "&placeid=" + placeDetails.getData().get(0).getId() +
                                    "&userid=" + userid +
                                    "&name=" + name +
                                    "&location=" + location +
                                    "&place1=" + place1 +
                                    "&place2=" + place2 +
                                    "&place3=" + place3 +
                                    "&placefull=" + palcefull +
                                    "&latitude=" + latitude +
                                    "&longitude=" + longitude +
                                    "&todo=" + todo +
                                    "&howtoreach=" + howto +
                                    "&creat_lat=" + lat +
                                    "&creat_lon=" + lon+
                                    "&creat_full=" + textView.getText().toString().replace(" ","%20");
                                    if (broadcast == null) {
                                        data += "&creat_city=" +broadcast[0];
                                    } else {
                                        data += "&creat_city=" + broadcast[0];
                                    }

                                    data += "&interests[]=" +segment+
                                    "&pictures="+extras.getString("img");;
                        } else {
                            data = "http://www.365hops.com/webservice/controller.php?Servicename=addPlacetoVisit" +
                                    "&userid=" + userid +
                                    "&name=" + name +
                                    "&location=" + location +
                                    "&place1=" + place1 +
                                    "&place2=" + place2 +
                                    "&place3=" + place3 +
                                    "&placefull=" + palcefull +
                                    "&latitude=" + latitude +
                                    "&longitude=" + longitude +
                                    "&todo=" + todo +
                                    "&howtoreach=" + howto +
                                    "&creat_lat=" + lat +
                                    "&creat_lon=" + lon+
                                    "&creat_full=" + textView.getText().toString().replace(" ","%20");
                                    if (broadcast == null) {
                                        data += "&creat_city=" + broadcast[0];
                                    } else {
                                        data += "&creat_city=" + broadcast[0];
                                    }

                                    data += "&interests[]=" +segment+
                                    "&pictures="+extras.getString("img");
                        }

                        Log.d("url", data);

                        PostDataToServer posData = new PostDataToServer(BroadcastActivity.this);
                        posData.execute(data.replace(" ", "%20"));
                    } else if (form == 3) {
                        String userid = CommonsUtils.getPrefString(BroadcastActivity.this, "userid");
                        String name = extras.getString("name").replace(" ", "%20");
                        String where = extras.getString("where");
                        String location = extras.getString("placefull").replace(" ", "%20");
                        String place1 = extras.getString("place1");
                        String place2 = extras.getString("place2");
                        String place3 = extras.getString("place3");
                        String latitude = extras.getString("latitude");
                        String longitude = extras.getString("longitude");
                        String isfood = extras.getString("isfood");
                        String istrans = extras.getString("istrans");
                        String isaccom = extras.getString("isaccom");
                        String details = extras.getString("cost_details");
                        String costitenery = extras.getString("detail_itinerary");
                        String segments = extras.getString("segment");
                        String costPlan = extras.getString("cost_plan");

                        String food = extras.getString("food");
                        String trans = extras.getString("trans");
                        String accom = extras.getString("accom");
                        String others = extras.getString("others");
                        String date_type = extras.getString("date_type");
                        String start_date = extras.getString("start_date").replace("/", "-");
                        String end_date = extras.getString("end_date").replace("/", "-");


                        String duration = extras.getString("duration");
                        String duration1 = extras.getString("duration1");
                        String cost = extras.getString("cost");
                        String currency = extras.getString("currency");
                        String data = null;
                        String multiple_dates = extras.getString("multiple_date");


                        if (edit) {
                            data = "http://www.365hops.com/webservice/controller.php?Servicename=editEvent" +
                                    "&eventid=" + events.getId() +
                                    "&userid=" + userid +
                                    "&name=" + name +
                                    "&where=" + where +
                                    "&location_latitude[]=" + latitude +
                                    "&location_longitude[]=" + longitude +
                                    "&location_tags1[]=" + place1 +
                                    "&location_tags2[]=" + place2 +
                                    "&location_tags3[]=" + place3 +
                                    "&location_full[]=" +location +
                                    "&when=" + "when" +
                                    "&date_type=" + date_type +
                                    "&multipledate_type=" +multiple_dates+
                                    "&selected_dates[]=" + start_date +
                                    "&duration=" + duration +
                                    "&duration2=" + duration1 +
                                    "&cost=" + cost +
                                    "&currency=" + currency +
                                    "&interests[]=" + segments +
                                    "&isfood=" + isfood +
                                    "&food=" + food +
                                    "&istravel=" + istrans +
                                    "&travel=" + trans +
                                    "&isaccommodation=" + isaccom +
                                    "&accommodation=" + accom +
                                    "&others=" + others +
                                    "&detail_itinerary=" + costitenery +
                                    "&cost_description=" + costitenery+
                                    "&cost_plans[]=" + costPlan+
                                    "&costplan_detail[]=" +details+
                                    "&costplan_currency[]=Rupees" +
                                    "&steps[]=" +
                                    "&creat_lat=" +lat+
                                    "&creat_lon=" +lon +
                                    "&creat_full=" + textView.getText().toString().replace(" ","%20");
                                    if (broadcast == null) {
                                        data += "&creat_city=" + broadcast[0];
                                    } else {
                                        data += "&creat_city=" + broadcast[0];
                                    }

                                    data += "&difficulty_level=1" +
                                    "&image[]="+extras.getString("img");;
                        } else {
                            data = "http://www.365hops.com/webservice/controller.php?Servicename=addEvent" +
                                    "&userid=" + userid +
                                    "&name=" + name +
                                    "&where=" + where +
                                    "&location_latitude[]=" + latitude +
                                    "&location_longitude[]=" + longitude +
                                    "&location_tags1[]=" + place3 +
                                    "&location_tags2[]=" + place2 +
                                    "&location_tags3[]=" + place3 +
                                    "&location_full[]=" + location +
                                    "&when=" + "when" +
                                    "&date_type=" + date_type +
                                    "&multipledate_type=" +multiple_dates+
                                    "&selected_dates[]=" + start_date +
                                    "&duration=" + duration +
                                    "&duration2=" + duration1 +
                                    "&cost=" + cost +
                                    "&currency=" + currency +
                                    "&interests[]=" + segments +
                                    "&isfood=" + isfood +
                                    "&food=" + food +
                                    "&istravel=" + istrans +
                                    "&travel=" + trans +
                                    "&isaccommodation=" + isaccom +
                                    "&accommodation=" + accom +
                                    "&others=" + others +
                                    "&detail_itinerary=" + costitenery +
                                    "&cost_description=" + costitenery +
                                    "&cost_plans[]=" +costPlan+
                                    "&costplan_detail[]=" + details +
                                    "&costplan_currency[]=Rupees" +
                                    "&steps[]=" +
                                    "&creat_lat=" +lat+""+
                                    "&creat_lon=" +lon+""+
                                    "&creat_full=" + textView.getText().toString().replace(" ","%20");
                                    if (broadcast == null) {
                                        data += "&creat_city=" + broadcast[0];
                                    } else {
                                        data += "&creat_city=" + broadcast[0];
                                    }

                                    data += "&difficulty_level=1" +
                                    "&image[]="+extras.getString("img");;
                        }
                        Log.d("url", data);
                        PostDataToServer posData = new PostDataToServer(BroadcastActivity.this);
                        posData.execute(data.replace(" ", "%20").replace("\n", "").replace("\r", ""));
                    } else {

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" +
                            "place field should not be empty", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(BroadcastActivity.this, PagerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BroadcastActivity.this.finish();
            }
        });


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.auto_list_item);
        adapter.setNotifyOnChange(true);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                Geocoder coder = new Geocoder(BroadcastActivity.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(selection, 50);
                    for(Address add : adresses){
                        lon = add.getLongitude();
                        lat = add.getLatitude();
                        Toast.makeText(getApplicationContext(),lon+","+lat,Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //TODO Do something with the selected text
            }
        });
        textView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count % 3 == 1) {
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


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onPostComplete(String json) {
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);

                if (json.contains("status")) {

                    int status = jObj.getInt("status");

                    if (status == 0) {
                        String message = jObj.getString("message");
                        Toast.makeText(BroadcastActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(BroadcastActivity.this, "Broadcasted Successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BroadcastActivity.this, PagerActivity.class));
                        finish();
                    }

                } else {
                    String success = jObj.getString("success");
                    if (success.equalsIgnoreCase("0")) {
                        Toast.makeText(BroadcastActivity.this, "Broadcasted Successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BroadcastActivity.this, PagerActivity.class));
                        finish();
                    } else if (success.equalsIgnoreCase("1")) {
                        String message = jObj.getString("message");
                        Toast.makeText(BroadcastActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void OnLatlngComplete(double lat, double lng) {
        this.lat = lat;
        lon = lng;
       // Toast.makeText(getApplication(),this.lat+","+lon,Toast.LENGTH_SHORT).show();

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
        edit = getIntent().getBooleanExtra("edit", false);
        if (edit) {
            if (textView != null) {
                String selection = textView.getText().toString();
                if (selection != null
                        && !selection.equals("")) {
                    GetLanLng task = new GetLanLng(selection.replaceAll("\\s+", "").replace(" ","%20").toString(), BroadcastActivity.this);
                    task.execute("");
                }
            }
        }
    }
}
