package com.appzollo.classes;

import android.os.AsyncTask;
import android.util.Log;

import com.appzollo.interfaces.OnPostCompleteListener;

/**
 * Created by saikrishna on 12/11/14.
 */

public class PostDataToServer extends AsyncTask<String, Void, String> {
    private OnPostCompleteListener postCompleteListener;
    private JSONParser jParser;
    private String json;

    public PostDataToServer(OnPostCompleteListener postCompleteListener) {
        this.postCompleteListener = postCompleteListener;
    }

    @Override
    protected String doInBackground(String... urls) {
        jParser = new JSONParser();
        json = jParser.getJSONFromUrl(urls[0]);
        Log.d("PostDataToServer", "" + json);
        return json;
    }

    @Override
    protected void onPostExecute(String result) {
        if (postCompleteListener != null)
            postCompleteListener.onPostComplete(json);
    }
}
