package com.appzollo;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.FindPeople;
import com.appzollo.classes.Followers;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class FindPeopleActivity extends ActionBarActivity implements OnPostCompleteListener, OnLoadCompleteListener, SearchView.OnQueryTextListener {
    private String url = "http://www.365hops.com/webservice/controller.php?Servicename=SearchUsers&limit=10";
    LinearLayout listView;
    private FindPeople followers;
    private Followers followers1;
    private ProgressDialog dialog;
    private TextView noFollowers;
    private JSONParser jParser;
    HashMap<String, String> map = new HashMap<String, String>();
    GPSTracker gps;
    private SearchView searchView;
    PostDataToServer post;
    String[] list_follows = null;
    List<String> ids = new ArrayList<String>();
    Boolean userFollows = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Find People");
        double latitude = 0, longitude = 0;
        gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

           } else {
            gps.showSettingsAlert();
        }
        url = url + "&latitude=" + latitude + "&longitude" + longitude;


        // post.execute(url + userId);
       // postData("a");

        dialog   = ProgressDialog.show(FindPeopleActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);
        dialog.dismiss();


        post = new PostDataToServer(this);
        if (!userFollows) {
            post.execute("http://www.365hops.com/webservice/controller.php?Servicename=UserFollowings&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid"));
          //  getActionBar().setTitle("Followers");
        }

        listView = (LinearLayout) findViewById(R.id.listView);
        noFollowers = (TextView) findViewById(R.id.tv_no_followers);
        noFollowers.setVisibility(View.GONE);
//

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.device_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat
                .getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostComplete(String json) {
        dialog.dismiss();
        if (json != null) {
            if (!userFollows) {

                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(json);
                    int status = jObj.getInt("success");
                   // Log.d("kkkkkkkkkkkkkkkkk", status + "");
                    if (status == 0) {
                        String message = jObj.getString("message");
                        //noFollowers.setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        Gson gson = new Gson();
                        followers1 = gson.fromJson(json, Followers.class);
                        if (followers1 != null
                                && followers1.getData() != null
                                && followers1.getData().size() > 0) {
                            list_follows = new String[followers1.getData().size()];
                            ids.clear();
                            for (int i = 0; i < followers1.getData().size(); i++) {
                                Followers.Data data = followers1.getData().get(i);
                                if (data != null) {

                                    list_follows[i] = data.getUser_id();
                                   //Log.d("kkkkkkkkkkkkkkkkkk", list_follows[i] + "");

                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onQueryTextChange("r");
                userFollows = true;


            } else {
                try {
                    JSONObject jObj = new JSONObject(json);
                    int status = jObj.getInt("status");
                    if (status == 0) {
                        String message = jObj.getString("message");
                        listView.removeAllViews();
                        noFollowers.setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        noFollowers.setVisibility(View.GONE);
                        Gson gson = new Gson();
                        followers = gson.fromJson(json, FindPeople.class);
                        if (followers != null
                                && followers.getData() != null
                                && followers.getData().size() > 0) {
                            Vector<RowData1> rowData = new Vector<RowData1>();
                            listView.removeAllViews();
                            for (int i = 0; i < followers.getData().size(); i++) {

                                final FindPeople.Data data = followers.getData().get(i);
                                if (!data.getUser_id().equalsIgnoreCase(CommonsUtils.getPrefString(getApplicationContext(), "userid"))) {


                                    if (data != null) {
                                        View convertView = View.inflate(this, R.layout.item_list_followers, null);

                                        TextView name = (TextView) convertView
                                                .findViewById(R.id.tv_name);

                                        TextView followers = (TextView) convertView.findViewById(R.id.tv_followers);

                                        TextView follow = (TextView) convertView.findViewById(R.id.tv_follow);

                                        final TextView bt_follow = (TextView) convertView.findViewById(R.id.bt_follow);
                                        final TextView tv_followers = (TextView) convertView.findViewById(R.id.tv_followers);

                                        RoundedImageView imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);
                                        map.put("user_id"+i,data.getUser_id());

                                        bt_follow.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (bt_follow.getText().toString().equalsIgnoreCase("Follow")) {
                                                    String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + data.getUser_id() + "&sender=" + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid");

                                                    //Toast.makeText(getApplication(), getIntent().getExtras().getString("userid") + "," + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                                                    GetSummaryTask_follow gst = new GetSummaryTask_follow();
                                                    gst.execute(new String[]{url.replace(" ","%20")}
                                                    );

                                                   tv_followers.setText(Integer.parseInt(tv_followers.getText().toString())+1+"");
                                                    bt_follow.setBackgroundColor(0xFF999999);
                                                    bt_follow.setText("Unfollow");
                                                } else if (bt_follow.getText().toString().equalsIgnoreCase("Unfollow")) {
                                                    String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" + data.getUser_id() + "&sender=" + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid");

                                                    //Toast.makeText(getApplication(), getIntent().getExtras().getString("userid") + "," + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                                                    GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                                                    gst.execute(new String[]{url});
                                                    tv_followers.setText(Math.abs(Integer.parseInt(tv_followers.getText().toString())-1)+"");
                                                    bt_follow.setBackgroundColor(0xFF437488);
                                                    bt_follow.setText("Follow");
                                                }
                                            }
                                        });

                                        name.setText(data.getUser_fullname());
                                        follow.setText(data.getFollows());
                                        followers.setText(data.getFollowers());
                                        bt_follow.setVisibility(View.VISIBLE);
                                        bt_follow.setText("Follow");
                                        bt_follow.setBackgroundColor(0xFF437488);

                                        if (list_follows != null) {
                                            Toast.makeText(getApplicationContext(),data.getUser_id()+","+list_follows.toString(),Toast.LENGTH_SHORT).show();

                                            for (int k = 0; k < list_follows.length; k++) {
                                                if (list_follows[k].equalsIgnoreCase(data.getUser_id())) {
                                                    //  Toast.makeText(getApplication(),list_follows[k],Toast.LENGTH_SHORT).show();

                                                    bt_follow.setVisibility(View.VISIBLE);
                                                    bt_follow.setText("Unfollow");
                                                    bt_follow.setBackgroundColor(0xFF999999);


                                                }
                                            }
                                        }else{
                                            bt_follow.setVisibility(View.VISIBLE);
                                            bt_follow.setText("Follow");
                                            bt_follow.setBackgroundColor(0xFF437488);
                                        }

                                        LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, FindPeopleActivity.this);
                                        load.execute(data.getUser_img());


                                        convertView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent i = new Intent(FindPeopleActivity.this, ProfileActivity.class);
                                                i.putExtra("my", false);
                                                i.putExtra("userid", data.getUser_id());
                                                i.putExtra("recieverid", CommonsUtils.getPrefString(getApplicationContext(), "userid"));
                                                startActivity(i);
                                            }
                                        });

                                        listView.addView(convertView);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("ee", e.toString());
                }
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(FindPeopleActivity.this, s, Toast.LENGTH_SHORT).show();
        post = new PostDataToServer(this);
        post.execute(url + "&name=" + s.replace(" ","%20"));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        post = new PostDataToServer(this);
        post.execute(url + "&name=" + s.replace(" ","%20"));
        return false;
    }
public void postData(String s){
    post = new PostDataToServer(this);
    post.execute(url + "&name=" + s.replace(" ","%20"));
}
    private class EfficientAdapter<T> extends ArrayAdapter<T> {
        private LayoutInflater mInflater;

        public EfficientAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_list_followers, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.followers = (TextView) convertView.findViewById(R.id.tv_followers);
            holder.follow = (TextView) convertView.findViewById(R.id.tv_follow);
            holder.bt_follow = (TextView) convertView.findViewById(R.id.bt_follow);
            holder.bt_follow.setTag(position);
            holder.imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);
            RowData1 row = (RowData1) getItem(position);

            holder.bt_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    if (holder.bt_follow.getText().toString().equalsIgnoreCase("Follow")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid");

                        //Toast.makeText(getApplication(), getIntent().getExtras().getString("userid") + "," + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_follow gst = new GetSummaryTask_follow();
                        gst.execute(new String[]{url.replace(" ","%20")}
                        );

                        holder.followers.setText(Integer.parseInt(holder.followers.getText().toString())+1+"");

                        holder.bt_follow.setText("Unfollow");

                    } else if (holder.bt_follow.getText().toString().equalsIgnoreCase("Unfollow")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid");

                        //Toast.makeText(getApplication(), getIntent().getExtras().getString("userid") + "," + CommonsUtils.getPrefString(FindPeopleActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                        gst.execute(new String[]{url});
                        holder.followers.setText(Math.abs(Integer.parseInt(holder.followers.getText().toString())-1)+"");

                        holder.bt_follow.setText("Follow");
                    }
                }
            });

            holder.name.setText(row.getName());
            holder.follow.setText(row.getFollows());
            holder.followers.setText(row.getFollowers());
            if (row.getName().equalsIgnoreCase(CommonsUtils.getPrefString(getApplicationContext(), "user"))) {
                Toast.makeText(getApplicationContext(), row.getName(), Toast.LENGTH_SHORT).show();
                holder.bt_follow.setVisibility(View.GONE);
            } else {
                holder.bt_follow.setVisibility(View.VISIBLE);

            }
            if (list_follows != null) {
                // Toast.makeText(getApplicationContext(),row.getName(),Toast.LENGTH_SHORT).show();
                for (int k = 0; k < list_follows.length; k++) {
                    if (list_follows[k].equalsIgnoreCase(row.getName())) {
                        //  Toast.makeText(getApplication(),list_follows[k],Toast.LENGTH_SHORT).show();

                        holder.bt_follow.setVisibility(View.VISIBLE);
                        holder.bt_follow.setText("Unfollow");

                    }
                }
            }

            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, FindPeopleActivity.this);
            load.execute(row.getImage());

            return convertView;
        }

        public class ViewHolder {
            TextView name;
            TextView followers;
            TextView follow;
            TextView bt_follow;
            RoundedImageView imageView;
        }
    }


    public class GetSummaryTask_follow extends AsyncTask<String, Void, String> {

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
           /* if(map.get("success").equalsIgnoreCase("0")){

               holder.bt_follow.setText("Follow");
            }else if(map.get("success").equalsIgnoreCase("1")){
                update_unfollow();
                follow.setText("Follow");
            }else{
                follow.setText("Unfollow");
            }*/

            // Toast.makeText(getApplicationContext(), map.get("message"), Toast.LENGTH_SHORT).show();
            //dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

          //  dialog = ProgressDialog.show(FindPeopleActivity.this, "",
            //        "Loading. Please wait...", true);
            //dialog.setCancelable(false);

        }
    }

    public class GetSummaryTask_unfollow extends AsyncTask<String, Void, String> {

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
           /* if(map.get("success").equalsIgnoreCase("0")){

               holder.bt_follow.setText("Follow");
            }else if(map.get("success").equalsIgnoreCase("1")){
                update_unfollow();
                follow.setText("Follow");
            }else{
                follow.setText("Unfollow");
            }*/

           // finish();
            //startActivity(new Intent(FindPeopleActivity.this, FindPeopleActivity.class));
            // Toast.makeText(getApplicationContext(), map.get("message"), Toast.LENGTH_SHORT).show();
            //dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

            //dialog = ProgressDialog.show(FindPeopleActivity.this, "",
              //      "Loading. Please wait...", true);
            //dialog.setCancelable(false);

        }
    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof RoundedImageView) {
            ((RoundedImageView) view).setImageBitmap(bitmap);
        }
    }

}

class RowData1 {
    private String name;
    private String followers;
    private String follows;
    private String image;

    public RowData1(String name, String image) {
        this.name = name;

        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
