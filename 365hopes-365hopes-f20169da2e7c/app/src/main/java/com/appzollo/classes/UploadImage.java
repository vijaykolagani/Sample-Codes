package com.appzollo.classes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * Created by prasad on 1/7/2015.
 */
public class UploadImage extends AsyncTask<String, String, String> {
    OnUploadImage onUpload;
    String[] data;

    protected void onPreExecute() {
        super.onPreExecute();

    }
    public UploadImage( String[] data, OnUploadImage onUpload){
        this.onUpload = onUpload;
        this.data = data;
    }

    @Override
    protected String doInBackground(String... params) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("count", data.length+""));
        for(int i=1;i<=data.length;i++){
            nameValuePairs.add(new BasicNameValuePair("image"+i, data[i-1]));
        }
        nameValuePairs.add(new BasicNameValuePair("location", params[0]));
        Log.d("",params[0]+"__"+data[0].length());
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
        Log.d("response" , result);
        onUpload.onUploadImage(result);

    }
}
