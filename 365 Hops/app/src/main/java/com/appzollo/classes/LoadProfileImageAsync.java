package com.appzollo.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;

import com.appzollo.interfaces.OnLoadCompleteListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prasad on 11-Nov-14.
 */
public class LoadProfileImageAsync extends AsyncTask<String, String, Bitmap> {
    Bitmap  bitmap;
    OnLoadCompleteListener loadCompleteListener;
    View view;

    public LoadProfileImageAsync(View view, OnLoadCompleteListener loadCompleteListener) {
        this.view = view;
        this.loadCompleteListener = loadCompleteListener;
    }



    @Override
    protected void onPreExecute() {
    }
    protected Bitmap doInBackground(String... args) {
        try {

            URL url = new URL(args[0]);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    protected void onPostExecute(Bitmap image) {
        loadCompleteListener.onLoadComplete(view, bitmap);


    }
}
