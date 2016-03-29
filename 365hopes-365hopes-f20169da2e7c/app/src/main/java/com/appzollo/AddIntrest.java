package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.appzollo.classes.Category;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.SettingsListAdapter;
import com.appzollo.utils.CommonsUtils;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Example application with ExpandableListView and CheckedTextView as list item.
 * Texts of selected list items are displayed in parent view.
 *
 * @author Lauri Nevala
 */
public class AddIntrest extends Activity {

    private SettingsListAdapter adapter;
    private ExpandableListView categoriesList;
    private ArrayList<Category> categories;
    ArrayList<String> data = new ArrayList<String>();
    protected Context mContext;
    Button bt_done;
    String url = "http://www.365hops.com/webservice/controller.php?Servicename=updateUserInterests&userid=";
    HashMap<String, String> map = new HashMap<String, String>();
    JSONParser jParser;
    private ProgressDialog dialog;
    int itemId[][]={{10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,21},{62,61,63,64},{57},{58},{59}};
    static ArrayList<String> interest_array = new ArrayList<String>();
    static ArrayList<String> interest_itemid = new ArrayList<String>();
   // String interest_items[];
    final String[] adv = {
            "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
            "Parasailing","Rafting","Rappelling"
            , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports"
            ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
            "Wildlife"};
    int[] items = {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,62,61,63,64,57,58,59};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_intrest);
        mContext = this;
        interest_array = getIntent().getExtras().getStringArrayList("interest_array");
        interest_itemid = getIntent().getExtras().getStringArrayList("interest_id");
        Log.d("array",interest_array+"");
       // interest_items = new String[interest_array.size()*2];
            for(int k =0; k < interest_array.size() ; k++){
                if(interest_array.get(k).equalsIgnoreCase("Skydiving")){
                    interest_array.add("Sky Diving");
                }
            }
        url = url + CommonsUtils.getPrefString(AddIntrest.this, "userid")+"&interests=";
        bt_done = (Button) findViewById(R.id.bt_done);
        categoriesList = (ExpandableListView) findViewById(R.id.categories);
        categories = Category.getCategories();
        adapter = new SettingsListAdapter(this,
                categories, categoriesList,interest_array);
        categoriesList.setAdapter(adapter);

        categoriesList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                adapter = new SettingsListAdapter(AddIntrest.this,
                        categories, categoriesList,interest_array);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int l = 0; l < adv.length; l++) {
                    for (int k = 0; k < interest_array.size(); k++) {
                        if (adv[l].equalsIgnoreCase(interest_array.get(k))  ) {
                            data.add(items[l] + "");
                        }
                    }
                }
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        url += data.get(i) + ",";
                    }
                    jParser = new JSONParser();
                    GetSummaryTask gst = new GetSummaryTask();
                    gst.execute(url);
                }
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        });

        categoriesList.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                CheckedTextView checkbox = (CheckedTextView) v.findViewById(R.id.list_item_text_child);
                checkbox.toggle();

                // find parent view by tag
                Category category = categories.get(groupPosition);
                if (checkbox.isChecked()) {
                    // add child category to parent's selection list
                   category.selection.add(checkbox.getText().toString());
                    data.add(itemId[groupPosition][childPosition]+"");
                    interest_array.add(checkbox.getText().toString());
                    // sort list in alphabetical order
                    Collections.sort(category.selection, new CustomComparator());
                } else {
                    // remove child category from parent's selection list
                    category.selection.remove(checkbox.getText().toString());
                    for(int l = 0 ; l < adv.length ; l++){
                        if(adv[l].equalsIgnoreCase(checkbox.getText().toString())){
                            interest_array.remove(checkbox.getText().toString());
                            Toast.makeText(getApplication(),interest_array+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).equalsIgnoreCase(itemId[groupPosition][childPosition]+"")) {
                            data.remove(i);
                        }
                    }

                }
//                View parentView = categoriesList.findViewWithTag(categories.get(groupPosition).name);
//                if (parentView != null) {
//                    TextView sub = (TextView) parentView.findViewById(R.id.list_item_text_subscriptions);
//
//                    if (sub != null) {
//
//                        // display selection list
//                        sub.setText(category.selection.toString());
//                    }
//                }
                return true;
            }
        });
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
               Toast.makeText(getApplicationContext(), map.get("message"), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish();
        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(AddIntrest.this, "",
                    "Loading. Please wait...", true);
            dialog.setCancelable(false);
        }
    }

    public class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
}