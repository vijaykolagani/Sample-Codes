package com.appzollo;

import android.os.AsyncTask;
import android.widget.Toast;

import com.appzollo.interfaces.OnLatlngCompleteListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Prasad on 11-Nov-14.
 */
class GetLanLng extends AsyncTask<String, String, LatLng> {
    LatLng latlng;
    OnLatlngCompleteListener loadCompleteListener;
    Double latitude, longitude;
    String place;

    public GetLanLng(String place,OnLatlngCompleteListener loadCompleteListener) {
        this.place = place;
        this.loadCompleteListener = loadCompleteListener;
    }


    @Override
    protected void onPreExecute() {
    }

    protected LatLng doInBackground(String... args) {
        try {
            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                    + place + "&sensor=false";

            URL url = new URL(googleMapUrl);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(
                    conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            String a = "";
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray resultJsonArray = jsonObj.getJSONArray("results");

            // Extract the Place descriptions from the results
            // resultList = new ArrayList<String>(resultJsonArray.length());

            JSONObject before_geometry_jsonObj = resultJsonArray
                    .getJSONObject(0);

            JSONObject geometry_jsonObj = before_geometry_jsonObj
                    .getJSONObject("geometry");

            JSONObject location_jsonObj = geometry_jsonObj
                    .getJSONObject("location");

            String lat_helper = location_jsonObj.getString("lat");
            latitude = Double.valueOf(lat_helper);


            String lng_helper = location_jsonObj.getString("lng");
            longitude = Double.valueOf(lng_helper);
            latlng = new LatLng(latitude, longitude);
            return latlng;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlng;
    }

    protected void onPostExecute(LatLng image) {
        if(latlng != null)
        loadCompleteListener.OnLatlngComplete(latlng.latitude, latlng.longitude);


    }
}



