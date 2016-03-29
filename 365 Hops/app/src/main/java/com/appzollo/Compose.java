package com.appzollo;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class Compose extends ActionBarActivity implements OnPostCompleteListener, OnLoadCompleteListener, SearchView.OnQueryTextListener {
    private String url = "http://www.365hops.com/webservice/controller.php?Servicename=UserFollowers&userid=";
    private String url1 = "http://www.365hops.com/webservice/controller.php?Servicename=UserFollowings&userid=";
    LinearLayout listView;
    private Followers followers;
    private ProgressDialog dialog;
   // private TextView noFollowers;
    private JSONParser jParser;
    HashMap<String, String> map = new HashMap<String, String>();
    String userId;
    Boolean isVisible;
    Boolean userFollows = false;
    PostDataToServer post;
    String[] list_follows = null;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Following");
        userId = getIntent().getExtras().getString("userid");
        isVisible = getIntent().getExtras().getBoolean("others");
        jParser = new JSONParser();
        Toast.makeText(getApplicationContext(), "hhhh" + userId + "," + getIntent().getExtras().getInt("type")+","+isVisible, Toast.LENGTH_LONG).show();
        post = new PostDataToServer(this);
        if (!userFollows) {
            post.execute(url1 + CommonsUtils.getPrefString(getApplicationContext(), "userid"));
           // getActionBar().setTitle("Followers");
        }


        dialog = ProgressDialog.show(Compose.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);

        listView = (LinearLayout) findViewById(R.id.followers);
     //   noFollowers = (TextView) findViewById(R.id.tv_no_followers);
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
        searchView.setOnQueryTextListener(Compose.this);
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
                    if (status == 0) {
                        String message = jObj.getString("message");
           //             noFollowers.setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        Gson gson = new Gson();
                        followers = gson.fromJson(json, Followers.class);
                        if (followers != null
                                && followers.getData() != null
                                && followers.getData().size() > 0) {
                            list_follows = new String[followers.getData().size()];
                            for (int i = 0; i < followers.getData().size(); i++) {
                                Followers.Data data = followers.getData().get(i);
                                if (data != null) {
                                    map.put("user_id"+i,data.getUser_id());
                                    Log.d("TAG", "Image: " + data.getUser_fullname());
                                    list_follows[i] = data.getUser_fullname();
                                    Log.d("nnnn",list_follows[i]+"");

                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                userFollows = true;
                if (getIntent().getExtras().getInt("type") == 0) {
                    PostDataToServer post1 = new PostDataToServer(this);
                    post1.execute(url + userId);
                    getActionBar().setTitle("Followers");
                } else {
                    PostDataToServer post1 = new PostDataToServer(this);
                    post1.execute(url1 + userId);
                    getActionBar().setTitle("Following");
                }

            } else {
                try {
                    JSONObject jObj = new JSONObject(json);

                    int status = jObj.getInt("success");
                    Log.d("jkjk",status+"k");
                    if (status == 0) {
                        String message = jObj.getString("message");
             //           noFollowers.setVisibility(View.VISIBLE);
                    } else if (status == 1) {
               //         noFollowers.setVisibility(View.GONE);
                        Log.d("jkjk","klk"+"");
                        Gson gson = new Gson();
                        followers = gson.fromJson(json, Followers.class);

                        if (followers != null
                                && followers.getData() != null
                                && followers.getData().size() > 0) {
                            listView.removeAllViews();
                            Log.d("jkjk",3+"");
                            for (int i = 0; i < followers.getData().size(); i++) {
                                final Followers.Data data = followers.getData().get(i);
                                Log.d("jkjk",34+"");
                                if (data != null) {
                                    Log.d("jkjk",35+"");
                                    View convertView = View.inflate(this, R.layout.item_list_compose, null);

                                    TextView name;

                                    RoundedImageView imageView;

                                    name = (TextView) convertView
                                            .findViewById(R.id.tv_name);
                                        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
                                    imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);

                                    ll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivityForResult(new Intent(Compose.this,InboxThread.class).putExtra("sender",CommonsUtils.getPrefString(Compose.this, "userid")).putExtra("receiver", data.getUser_id()).putExtra("title", data.getUser_fullname()),1);
                                            finish();
                                        }
                                    });

                                    name.setText(data.getUser_fullname());
                                    LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, Compose.this);
                                    load.execute(data.getUser_img());

                                    listView.addView(convertView);
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
        //Toast.makeText(FindPeopleActivity.this, s, Toast.LENGTH_SHORT).show();

        if (followers != null
                && followers.getData() != null
                && followers.getData().size() > 0) {
            listView.removeAllViews();
            for (int i = 0; i < followers.getData().size(); i++) {
                final Followers.Data data = followers.getData().get(i);
                if (data != null) {
                    Log.d("jkjk",35+"");
                    View convertView = View.inflate(this, R.layout.item_list_compose, null);

                    TextView name;

                    RoundedImageView imageView;

                    name = (TextView) convertView
                            .findViewById(R.id.tv_name);
                    LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
                    imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);

                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(Compose.this,InboxThread.class).putExtra("sender",CommonsUtils.getPrefString(Compose.this, "userid")).putExtra("receiver", data.getUser_id()).putExtra("title", data.getUser_fullname()),1);
                            finish();
                        }
                    });

                    name.setText(data.getUser_fullname());
                    LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, Compose.this);
                    load.execute(data.getUser_img());
                    if(data.getUser_fullname().toLowerCase().contains(s.toString().toLowerCase()))
                        listView.addView(convertView);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (followers != null
                && followers.getData() != null
                && followers.getData().size() > 0) {
            listView.removeAllViews();
            for (int i = 0; i < followers.getData().size(); i++) {
                final Followers.Data data = followers.getData().get(i);
                if (data != null) {
                    Log.d("jkjk",35+"");
                    View convertView = View.inflate(this, R.layout.item_list_compose, null);

                    TextView name;

                    RoundedImageView imageView;

                    name = (TextView) convertView
                            .findViewById(R.id.tv_name);
                    LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
                    imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);

                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(Compose.this,InboxThread.class).putExtra("sender",CommonsUtils.getPrefString(Compose.this, "userid")).putExtra("receiver", data.getUser_id()).putExtra("title", data.getUser_fullname()),1);
                            finish();
                        }
                    });

                    name.setText(data.getUser_fullname());
                    LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, Compose.this);
                    load.execute(data.getUser_img());
                    if(data.getUser_fullname().toLowerCase().contains(s.toString().toLowerCase()))
                        listView.addView(convertView);
                }
            }
        }
        return false;
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
                convertView = mInflater.inflate(R.layout.item_list_compose, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);

            holder.imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);
            final RowData_ row = (RowData_) getItem(position);

           /* holder.bt_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    if (holder.bt_follow.getText().toString().contains("  follow  ")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(Compose.this, "userid");
                        holder.bt_follow.setText("unfollow");
                        holder. bt_follow.setBackgroundColor(0xFF999999);
                        holder.followers.setText(Integer.parseInt(holder.followers.getText().toString())+1+"");
                        Toast.makeText(getApplication(), map.get("user_id" + position) + "," + CommonsUtils.getPrefString(Compose.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_follow gst = new GetSummaryTask_follow();
                        gst.execute(new String[]{url});

                    } else if (holder .bt_follow.getText().toString().contains("unfollow")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(Compose.this, "userid");
                        holder.bt_follow.setText("  follow  ");
                        holder.bt_follow.setBackgroundColor(0xFF437488);
                        holder.followers.setText(Math.abs(Integer.parseInt(holder.followers.getText().toString())-1)+"");

                        Toast.makeText(getApplication(), map.get("user_id" + position) + "," + CommonsUtils.getPrefString(Compose.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                        gst.execute(new String[]{url});
                    }
                }
            });*/
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(Compose.this,InboxThread.class).putExtra("sender",CommonsUtils.getPrefString(Compose.this, "userid")).putExtra("receiver", "7").putExtra("title", row.getName()),1);
                    finish();
                }
            });
            holder.name.setText(row.getName());
            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, Compose.this);
            load.execute(row.getImage());

            return convertView;
        }

        class ViewHolder {
            TextView name;
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
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof RoundedImageView) {
            ((RoundedImageView) view).setImageBitmap(bitmap);
        }
    }

}

class RowData_ {
    private String name;
    private String followers;
    private String follows;
    private String image;

    public RowData_(String name, String followers, String follows, String image) {
        this.name = name;
        this.followers = followers;
        this.follows = follows;
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
