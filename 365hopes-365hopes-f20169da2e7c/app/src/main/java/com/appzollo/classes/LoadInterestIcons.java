package com.appzollo.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.appzollo.interfaces.OnLoadCompleteListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasad on 11-Nov-14.
 */
public class LoadInterestIcons extends AsyncTask<String, String, ArrayList<Bitmap>> {
    Bitmap  bitmap;
    ArrayList<Bitmap> list;
    OnLoadInterestListener loadCompleteListener;
    LinearLayout view;
    boolean check;
    List<String> listIcon;
    int items[]= {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,21,62,61,63,64,57,58,59};


    public LoadInterestIcons(LinearLayout view, OnLoadInterestListener loadCompleteListener, boolean check,List<String> listIcon) {
        this.view = view;
        this.check = check;
        this.listIcon = listIcon;
        this.loadCompleteListener = loadCompleteListener;
    }



    @Override
    protected void onPreExecute() {
    }
    protected ArrayList<Bitmap> doInBackground(String... args) {
        if(check ) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(args[0]);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                // if(statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                try {
                    //Read the server response and attempt to parse it as JSON

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            content, "UTF8"), 8);


                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    content.close();


                    JSONObject jObj = new JSONObject(sb.toString());
                    JSONObject data = jObj.getJSONObject("data");
                    JSONArray dataInterests = data.getJSONArray("interest");
                    list = new ArrayList<Bitmap>();
                    for (int i = 0; i < dataInterests.length(); i++) {
                        JSONObject obj = dataInterests.getJSONObject(i);
                         for(int j=0;j<items.length;j++){
                            // Log.d("ids",items[j]+","+obj.getString("interest_id"));
                             if(obj.getString("interest_id").equals(items[j]+"")){
                                 try {

                                     URL url = new URL(obj.getString("icon_small"));
                                     HttpURLConnection connection;
                                     connection = (HttpURLConnection) url.openConnection();
                                     connection.setDoInput(true);
                                     connection.connect();
                                     InputStream input = connection.getInputStream();
                                     bitmap = BitmapFactory.decodeStream(input);
                                     list.add(bitmap);

                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }


                        // list.add(obj.getString("interest_id"));
                        //Log.e("list", obj.getString("interest_id"));
                    }

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e("Inbox", "Failed to parse JSON due to: " + ex);

                }

            } catch (Exception ex) {

            }
        }else{
            list = new ArrayList<Bitmap>();
            for (int i = 0; i < listIcon.size(); i++) {



                try {

                    URL url = new URL(listIcon.get(i));
                    HttpURLConnection connection;
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    list.add(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }



        return list;

    }
    protected void onPostExecute(ArrayList<Bitmap> list) {
        loadCompleteListener.onInterestLoadComplete(view, list);


    }
}
