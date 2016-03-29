package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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


public class FollowersActivity extends Activity implements OnPostCompleteListener, OnLoadCompleteListener {
    private String url = "http://www.365hops.com/webservice/controller.php?Servicename=UserFollowers&userid=";
    private String url1 = "http://www.365hops.com/webservice/controller.php?Servicename=UserFollowings&userid=";
    LinearLayout listView;
    private Followers followers;
    private ProgressDialog dialog;
    private TextView noFollowers;
    private JSONParser jParser;
    HashMap<String, String> map = new HashMap<String, String>();
    String userId;
    Boolean isVisible;
    Boolean userFollows = false;
    PostDataToServer post;
    String[] list_follows = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getString("userid");
        isVisible = getIntent().getExtras().getBoolean("others");
        jParser = new JSONParser();
        Toast.makeText(getApplicationContext(), "hhhh" + userId + "," + getIntent().getExtras().getInt("type")+","+isVisible, Toast.LENGTH_LONG).show();
        post = new PostDataToServer(this);
        if (!userFollows) {
            post.execute(url1 + CommonsUtils.getPrefString(getApplicationContext(), "userid"));
            getActionBar().setTitle("Followers");
        }
        if(getIntent().getExtras().getInt("type") == 0)
            getActionBar().setTitle("Followers");
        else
            getActionBar().setTitle("Following");


        dialog = ProgressDialog.show(FollowersActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);

        listView = (LinearLayout) findViewById(R.id.followers);
        noFollowers = (TextView) findViewById(R.id.tv_no_followers);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            startActivity(new Intent(FollowersActivity.this, ProfileActivity.class).putExtra("userid", userId));
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
                    Log.d("kkkkkkkkkkkkkkkkk",status+"");
                    if (status == 0) {
                        String message = jObj.getString("message");
                        noFollowers.setVisibility(View.VISIBLE);
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
                    if (status == 0) {
                        String message = jObj.getString("message");
                        noFollowers.setVisibility(View.VISIBLE);
                    } else if (status == 1) {
                        noFollowers.setVisibility(View.GONE);
                        Gson gson = new Gson();
                        followers = gson.fromJson(json, Followers.class);
                        if (followers != null
                                && followers.getData() != null
                                && followers.getData().size() > 0) {
                            listView.removeAllViews();
                            for (int i = 0; i < followers.getData().size(); i++) {
                                final Followers.Data data = followers.getData().get(i);

                                if (data != null) {
                                    View convertView = View.inflate(this, R.layout.item_list_followers, null);

                                    TextView name;
                                    final TextView followers;
                                    TextView follow;
                                    final TextView bt_follow;
                                    RoundedImageView imageView;

                                    name = (TextView) convertView
                                            .findViewById(R.id.tv_name);
                                    followers = (TextView) convertView.findViewById(R.id.tv_followers);
                                    follow = (TextView) convertView.findViewById(R.id.tv_follow);
                                    bt_follow = (TextView) convertView.findViewById(R.id.bt_follow);
                                    bt_follow.setTag(i);
                                    imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon1);

                                    bt_follow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int position = (Integer) view.getTag();

                                            if (bt_follow.getText().toString().contains("  follow  ")) {
                                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + data.getUser_id() + "&sender=" + CommonsUtils.getPrefString(FollowersActivity.this, "userid");
                                                bt_follow.setText("unfollow");
                                                bt_follow.setBackgroundColor(0xFF999999);
                                                followers.setText(Integer.parseInt(followers.getText().toString())+1+"");
                                                Toast.makeText(getApplication(), data.getUser_id() + "," + CommonsUtils.getPrefString(FollowersActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                                                GetSummaryTask_follow gst = new GetSummaryTask_follow();
                                                gst.execute(new String[]{url.replace(" ","%20")});

                                            } else if (bt_follow.getText().toString().contains("unfollow")) {
                                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" +  data.getUser_id() + "&sender=" + CommonsUtils.getPrefString(FollowersActivity.this, "userid");
                                                bt_follow.setText("  follow  ");
                                                bt_follow.setBackgroundColor(0xFF437488);
                                                followers.setText(Math.abs(Integer.parseInt(followers.getText().toString())-1)+"");

                                                Toast.makeText(getApplication(), data.getUser_id() + "," + CommonsUtils.getPrefString(FollowersActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                                                GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                                                gst.execute(new String[]{url.replace(" ","%20")});
                                            }
                                        }
                                    });

                                    if (getIntent().getExtras().getInt("type") != 0 && !isVisible && userId.equalsIgnoreCase(CommonsUtils.getPrefString(getApplicationContext(),"userid"))) {
                                        bt_follow.setText("unfollow");
                                        bt_follow.setBackgroundColor(0xFF999999);
                                    }else{
                                         bt_follow.setText("  follow  ");
                                        bt_follow.setBackgroundColor(0xFF437488);
                                    }

                                    name.setText(data.getUser_fullname());
                                    followers.setText("" + data.getFollowers());
                                    follow.setText("" + data.getFollows());
                                    if(data.getUser_fullname().equalsIgnoreCase(
                                            CommonsUtils.getPrefString(getApplicationContext(),"user"))){
                                        Toast.makeText(getApplicationContext(),data.getUser_fullname(),Toast.LENGTH_SHORT).show();
                                        bt_follow.setVisibility(View.GONE);
                                    }else {
                                        bt_follow.setVisibility(View.VISIBLE);

                                    }
                                    if(list_follows != null ) {
                                        //Toast.makeText(getApplicationContext(),row.getName(),Toast.LENGTH_SHORT).show();
                                        for (int k = 0; k < list_follows.length; k++) {
                                            if (list_follows[k].equalsIgnoreCase(data.getUser_fullname())) {
                                                  Toast.makeText(getApplication(),"test"+list_follows[k],Toast.LENGTH_SHORT).show();

                                                bt_follow.setVisibility(View.VISIBLE);
                                                bt_follow.setText("unfollow");
                                                bt_follow.setBackgroundColor(0xFF999999);

                                            }
                                        }
                                    }
                                    LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, FollowersActivity.this);
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
            RowData row = (RowData) getItem(position);

            holder.bt_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                 if (holder.bt_follow.getText().toString().contains("  follow  ")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(FollowersActivity.this, "userid");
                        holder.bt_follow.setText("unfollow");
                    holder. bt_follow.setBackgroundColor(0xFF999999);
                        holder.followers.setText(Integer.parseInt(holder.followers.getText().toString())+1+"");
                        Toast.makeText(getApplication(), map.get("user_id" + position) + "," + CommonsUtils.getPrefString(FollowersActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_follow gst = new GetSummaryTask_follow();
                        gst.execute(new String[]{url});

                    } else if (holder .bt_follow.getText().toString().contains("unfollow")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" + map.get("user_id" + position) + "&sender=" + CommonsUtils.getPrefString(FollowersActivity.this, "userid");
                        holder.bt_follow.setText("  follow  ");
                     holder.bt_follow.setBackgroundColor(0xFF437488);
                        holder.followers.setText(Math.abs(Integer.parseInt(holder.followers.getText().toString())-1)+"");

                        Toast.makeText(getApplication(), map.get("user_id" + position) + "," + CommonsUtils.getPrefString(FollowersActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                        GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                        gst.execute(new String[]{url});
                    }
                }
            });
            if (getIntent().getExtras().getInt("type") != 0 && !isVisible) {
                holder.bt_follow.setText("unfollow");
                holder.bt_follow.setBackgroundColor(0xFF999999);
            }else{
                holder.bt_follow.setText("  follow  ");
                holder.bt_follow.setBackgroundColor(0xFF437488);
            }
            holder.name.setText(row.getName());
            holder.followers.setText(row.getFollowers());
            holder.follow.setText(row.getFollows());
            if(row.getName().equalsIgnoreCase(CommonsUtils.getPrefString(getApplicationContext(),"user"))){
                Toast.makeText(getApplicationContext(),row.getName(),Toast.LENGTH_SHORT).show();
                holder.bt_follow.setVisibility(View.GONE);
            }else {
                holder.bt_follow.setVisibility(View.VISIBLE);

            }
            if(list_follows != null) {
               // Toast.makeText(getApplicationContext(),row.getName(),Toast.LENGTH_SHORT).show();
                for (int k = 0; k < list_follows.length; k++) {
                    if (list_follows[k].equalsIgnoreCase(row.getName())) {
                      //  Toast.makeText(getApplication(),list_follows[k],Toast.LENGTH_SHORT).show();

                            holder.bt_follow.setVisibility(View.VISIBLE);
                            holder.bt_follow.setText("unfollow");
                        holder.bt_follow.setBackgroundColor(0xFF999999);

                    }
                }
            }
            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, FollowersActivity.this);
            load.execute(row.getImage());

            return convertView;
        }

        class ViewHolder {
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
    public void onBackPressed(){
        super.onBackPressed();;
        startActivity(new Intent(FollowersActivity.this, ProfileActivity.class).putExtra("userid", userId));
        finish();
    }
    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof RoundedImageView) {
            ((RoundedImageView) view).setImageBitmap(bitmap);
        }
    }

}

class RowData {
    private String name;
    private String followers;
    private String follows;
    private String image;

    public RowData(String name, String followers, String follows, String image) {
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
