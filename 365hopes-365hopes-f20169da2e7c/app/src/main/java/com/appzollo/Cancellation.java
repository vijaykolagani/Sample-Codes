package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appzollo.classes.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;


public class Cancellation extends Activity {
    private JSONParser jParser;
    HashMap<String,String> map = new HashMap<String, String>();
    private ProgressDialog dialog;
    TextView tv_html;
    String url = "http://www.365hops.com/webservice/controller.php?Servicename=cancellationpolicy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tv_html = (TextView) findViewById(R.id.tv_html);
        jParser = new JSONParser();
        GetSummaryTask gst = new GetSummaryTask();
        gst.execute(url);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){
            //startActivity(new Intent(HowItWorks.this,PagerActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public class GetSummaryTask extends AsyncTask<String, Void, String> {

        private int asy = 0;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(urls[0]);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                // if(statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                try {
                    //Read the server response and attempt to parse it as JSON
                    Reader reader = new InputStreamReader(content);
                    Log.d("", reader.toString());
//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    String json = jParser.getJSONFromUrl(urls[0]);
                    JSONObject jObj = new JSONObject(json);

                    String success = jObj.getString("success");

                    map.put("success", success);
                    map.put("message", jObj.getString("message"));

                   /* Gson gson = new Gson();

                    placeDetailStickers = gson.fromJson(json, PlaceStickerDetailsPojo.class);
*/

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e("Place To Visit Detail", "Failed to parse JSON due to: " + ex);
                    // failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("Place To Visit Detail", "Failed to send HTTP POST request due to: " + ex);
                //failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {

            tv_html.setText((Html.fromHtml(map.get("message"))));
            //   Toast.makeText(getApplicationContext(),map_unfollow.get("message"),Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(Cancellation.this, "",
                    "Loading. Please wait...", true);
            dialog.setCancelable(false);

        }
    }

}
