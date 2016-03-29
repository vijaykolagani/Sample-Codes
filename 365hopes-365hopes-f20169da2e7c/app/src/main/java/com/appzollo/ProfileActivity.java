package com.appzollo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.classes.UserProfile;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.profilefragments.EventProfileFragment;
import com.appzollo.profilefragments.InterestFragment;
import com.appzollo.profilefragments.PlacesFragment;
import com.appzollo.utils.CommonsUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


/**
 * The <code>TabsViewPagerFragmentActivity</code> class implements the Fragment activity that maintains a TabHost using a ViewPager.
 *
 * @author mwho
 */
public class ProfileActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, OnLoadCompleteListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ProfileActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;

    TextView follow,followers;

    JSONObject jObj;
    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, String> map_unfollow = new HashMap<String, String>();
    HashMap<String, String> map_followstatus = new HashMap<String, String>();
    SharedPreferences pref;
    LinearLayout llOthers;


    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                //  ProgressBar b = (ProgressBar)view.findViewById(R.id.progressBar);
                // b.setVisibility(View.GONE);
                Drawable d = new BitmapDrawable(bitmap);
                if (d != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        view.setBackground(d);
                    } else {
                        view.setBackgroundDrawable(d);
                    }
                }

            }
        }
    }

    /**
     * @author mwho
     *         Maintains extrinsic info of a tab's construct
     */
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    /**
     * A simple factory that returns dummy views to the Tabhost
     *
     * @author mwho
     */
    class TabFactory implements TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    private JSONParser jParser;
    GetSummaryTask gst;
    UserProfile userProfile;
    private ProgressDialog dialog;
    Bundle bundle;
    TextView follows;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.activity_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("com.appzollo", Context.MODE_PRIVATE);


        jParser = new JSONParser();

        gst = new GetSummaryTask();

        HashMap<String,String> map = new HashMap<String, String>();
        map.put("profileid",getIntent().getExtras().getString("userid"));
        CommonsUtils.putPrefStrings(getApplicationContext(),map);

        gst.execute(new String[]{"http://www.365hops.com/webservice/controller.php?Servicename=getUserProfile&userid=" + getIntent().getExtras().getString("userid")});


        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {
        bundle = new Bundle();
        bundle.putString("userid", getIntent().getExtras().getString("userid"));
        List<Fragment> fragments = new Vector<Fragment>();

        InterestFragment fragment3 = new InterestFragment();
        fragment3.setArguments(bundle);
        EventProfileFragment fragment2 = new EventProfileFragment();
        fragment2.setArguments(bundle);
        fragments.add(fragment2);
        fragments.add(Fragment.instantiate(this, PlacesFragment.class.getName()));
        fragments.add(fragment3);
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }



    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();


//        bundle.putString("userid", getIntent().getExtras().getString("userid"));
        TabInfo tabInfo = null;
        ProfileActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Events").setIndicator("Events"), (tabInfo = new TabInfo("Events", EventProfileFragment.class, bundle)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ProfileActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Places").setIndicator("Places"), (tabInfo = new TabInfo("Places", PlacesFragment.class, bundle)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        ProfileActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Interest").setIndicator("Interest"), (tabInfo = new TabInfo("Interest", InterestFragment.class, bundle)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        //this.onTabChanged("Tab1");
        //
        // mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 40;
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
        mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
        // mTabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 40;
        mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
        //mTabHost.getTabWidget().getChildAt(3).getLayoutParams().height = 40;
      //  mTabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.WHITE);

        mTabHost.getTabWidget().getChildAt(0)
                .setBackgroundResource(R.drawable.tab_selector);
        mTabHost.setOnTabChangedListener(this);
    }


    private static void AddTab(ProfileActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    /**
     * (non-Javadoc)
     *
     * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
     */
    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        if (pos == 0) {
            mTabHost.getTabWidget().getChildAt(0)
                    .setBackgroundResource(R.drawable.tab_selector);
            mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
            mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
            this.mViewPager.setCurrentItem(pos);
           // mTabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.WHITE);

        } else if (pos == 1) {
            mTabHost.getTabWidget().getChildAt(1)
                    .setBackgroundResource(R.drawable.tab_selector);

            mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
            mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
            this.mViewPager.setCurrentItem(pos);
          //  mTabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.WHITE);

        } else if (pos == 2) {
            mTabHost.getTabWidget().getChildAt(2)
                    .setBackgroundResource(R.drawable.tab_selector);
            mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
            mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
            this.mViewPager.setCurrentItem(pos);
          //  mTabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.WHITE);
        } /*else {
            mTabHost.getTabWidget().getChildAt(3)
                    .setBackgroundResource(R.drawable.tab_selector);
            mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
            mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
            mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
        }*/


    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub
        //   mTabHost.getChildAt(position).setBackgroundResource(R.drawable.tab_selector);

    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
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
                    jObj = new JSONObject(json);

                    String success = jObj.getString("success");

                    map_unfollow.put("success", success);
                    map_unfollow.put("message", jObj.getString("message"));

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
           /* if(map_unfollow.get("success").equalsIgnoreCase("0")){
                update_unfollow();
                follow.setText("Follow");
            }else if(map_unfollow.get("success").equalsIgnoreCase("1")){
                update_unfollow();
                follow.setText("Follow");
            }else{
                follow.setText("Unfollow");
            }*/

         //   Toast.makeText(getApplicationContext(),map_unfollow.get("message"),Toast.LENGTH_SHORT).show();
           // dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

           // dialog = ProgressDialog.show(ProfileActivity.this, "",
             //       "Loading. Please wait...", true);
            //dialog.setCancelable(false);

        }
    }
    void update_unfollow(){
        if(followers.getText().toString() != null){
            String follow = followers.getText().toString();
          //  Toast.makeText(getApplicationContext(),follow,Toast.LENGTH_LONG).show();
            followers.setText(Integer.parseInt(follow)-1+"");

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
                    jObj = new JSONObject(json);

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
           /* if(map.get("success").equalsIgnoreCase("0")){
                update_follow();
                follow.setText("Unfollow");
            }else if(map.get("success").equalsIgnoreCase("1")){
                update_follow();
                follow.setText("Unfollow");
            }else{
                follow.setText("Follow");
            }*/

          //  Toast.makeText(getApplicationContext(),map.get("message"),Toast.LENGTH_SHORT).show();
            //dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

            //dialog = ProgressDialog.show(ProfileActivity.this, "",
             //       "Loading. Please wait...", true);
            //dialog.setCancelable(false);

        }
    }

    void update_follow(){
        if(followers.getText().toString() != null){
            String follow = followers.getText().toString();
          //  Toast.makeText(getApplicationContext(),follow,Toast.LENGTH_LONG).show();
            followers.setText(Integer.parseInt(follow)+1+"");
        }

    }

    public class GetSummaryTask_followstatus extends AsyncTask<String, Void, String> {

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
                    jObj = new JSONObject(json);

                    String success = jObj.getString("success");

                    map_followstatus.put("success", success);
                    map_followstatus.put("message", jObj.getString("message"));
                    map_followstatus.put("following",jObj.getString("following"));

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
            if(map_followstatus.get("success").equalsIgnoreCase("0")){
                follow.setText("Follow");
            }else if(map_followstatus.get("success").equalsIgnoreCase("1")){

                follow.setText("Unfollow");
            }
            llOthers.setVisibility(View.VISIBLE);

          //  Toast.makeText(getApplicationContext(),map_followstatus.get("message"),Toast.LENGTH_SHORT).show();
          //  dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

           /* dialog = ProgressDialog.show(ProfileActivity.this, "",
                    "Loading. Please wait...", true);
            dialog.setCancelable(false);*/

        }
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
                    jObj = new JSONObject(json);

                    String success = jObj.getString("success");

                    map.put("sucess", success);
                    if (success.endsWith("1")) {
                        //   JSONArray data = jObj.getJSONArray("data");
                        JSONArray array = jObj.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);
                        String location;
                        if(!data.getString("location_full").toString().equalsIgnoreCase("null")) {
                             location = data.getString("location_full");
                        }else{
                            location = " ";
                        }
                        Log.d("kkkk",location);
                        //JSONArray data_image = data.getJSONArray("image");
                        map.put("user_id", data.getString("user_id"));
                        map.put("user_email", data.getString("user_email"));
                        map.put("user_fb_id", data.getString("user_fb_id"));
                        map.put("loginsource", data.getString("loginsource"));
                        map.put("usertype", data.getString("usertype"));
                        map.put("serviceid", data.getString("serviceid"));
                        map.put("user_dob", data.getString("user_dob"));
                        map.put("user_fullname", data.getString("user_fullname"));
                        map.put("user_img", data.getString("user_img"));
                        map.put("user_sex", data.getString("user_sex"));
                        map.put("userregisterdate", data.getString("userregisterdate"));
                        map.put("user_city", data.getString("user_city"));
                        map.put("location1", data.getString("location1"));
                        map.put("location2", data.getString("location2"));
                        map.put("location3", data.getString("location3"));
                        map.put("location_full", location);
                        map.put("last_login", data.getString("last_login"));
                        map.put("aboutyou", data.getString("aboutyou"));
                        map.put("phone", data.getString("phone"));
                        map.put("notification", data.getString("notification"));
                        map.put("occupation", data.getString("occupation"));
                        map.put("langauge", data.getString("langauge"));
                        map.put("follows", data.getString("follows"));
                        map.put("followers", data.getString("followers"));
                        map.put("lat", data.getString("lat"));
                        map.put("lon", data.getString("lon"));

                        Log.e("username", map.get("user_fullname"));
                    }
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

            handlePostsList();
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(ProfileActivity.this, "",
                    "Loading. Please wait...", true);
            dialog.setCancelable(false);

        }
    }



    private void handlePostsList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                updateViews();

                //  userProfile.getData();
/*
                if (userProfile != null) {
                    Log.e("username",userProfile.getData().getUser_fullname());
                }else{
                    Log.e("username","null");
                }
*/

            }
        });
    }

    public void updateViews() {
        TextView name = (TextView) findViewById(R.id.tv_name);
        TextView location = (TextView) findViewById(R.id.tv_location);
         followers = (TextView) findViewById(R.id.tv_followers);
         follows = (TextView) findViewById(R.id.tv_follows);
        TextView work = (TextView) findViewById(R.id.tv_work);
        TextView date = (TextView) findViewById(R.id.tv_created);
          llOthers = (LinearLayout) findViewById(R.id.ll_others);
         follow = (TextView) findViewById(R.id.tv_follow);
        TextView message = (TextView) findViewById(R.id.tv_message);
        TextView tvEdit = (TextView) findViewById(R.id.tv_edit);

        if (getIntent().getExtras().getString("userid").equals(CommonsUtils.getPrefString(ProfileActivity.this,"userid"))) {
            llOthers.setVisibility(View.GONE);
            tvEdit.setVisibility(View.VISIBLE);
            if(!map.get("location_full").equals("null")) {
                location.setText(map.get("location_full"));
                HashMap<String,String> map1 = new HashMap<String, String>();
                map1.put("location",map.get("location_full"));
                CommonsUtils.putPrefStrings(getApplicationContext(),map1);
            }
        } else {

            tvEdit.setVisibility(View.INVISIBLE);
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=checkUserFollowingStatus&reciever="+map.get("user_id")+"&sender="+CommonsUtils.getPrefString(getApplicationContext(),"userid");
            GetSummaryTask_followstatus gst_followstatus = new GetSummaryTask_followstatus();
          gst_followstatus.execute(url);

        }
        if(!map.get("location_full").equals("null")) {
            location.setText(map.get("location_full"));
            HashMap<String,String> map1 = new HashMap<String, String>();
            //map1.put("location",map.get("location_full"));
            //CommonsUtils.putPrefStrings(getApplicationContext(),map1);
        }

        if(!map.get("user_fullname").equals("null"))
        name.setText(map.get("user_fullname"));

        if(!map.get("occupation").equals("null"))
        work.setText(map.get("occupation"));

        if(!map.get("followers").equals("null"))
        followers.setText(map.get("followers"));

        if(!map.get("follows").equals("null"))
        follows.setText(map.get("follows"));
      /*  SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        String formattedTime=null;
        try {
            d = output.parse(map.get("userregisterdate"));
            formattedTime = output.format(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


      */
        date.setText("Member since " + map.get("userregisterdate"));

        // date.setText(map.get("userregisterdate"));
       // Toast.makeText(getApplicationContext(), map.get("user_id")+"", Toast.LENGTH_SHORT).show();
        HashMap<String,String> map1 = new HashMap<String, String>();
        map1.put("profile_userid",getIntent().getExtras().getString("userid"));
        CommonsUtils.putPrefStrings(getApplicationContext(),map1);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,InboxThread.class).putExtra("sender",CommonsUtils.getPrefString(ProfileActivity.this,"userid")).putExtra("receiver", getIntent().getExtras().getString("userid")).putExtra("title", map.get("user_fullname")));
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("followw",","+follow.getText().toString());
                if(follow.getText().toString().equalsIgnoreCase("Follow")) {
                    String url = "http://www.365hops.com/webservice/controller.php?Servicename=followUser&reciever=" + map.get("user_id") + "&sender=" + CommonsUtils.getPrefString(ProfileActivity.this, "userid");
                    follow.setText("Unfollow");
                    String follow = followers.getText().toString();
                    //  Toast.makeText(getApplicationContext(),follow,Toast.LENGTH_LONG).show();
                    followers.setText(Integer.parseInt(follow)+1+"");

                    //  Toast.makeText(getApplication(), map.get("user_id") + "," + CommonsUtils.getPrefString(ProfileActivity.this, "userid"), Toast.LENGTH_SHORT).show();
                    GetSummaryTask_follow gst = new GetSummaryTask_follow();
                    gst.execute(new String[]{url});


                }else if(follow.getText().toString().equalsIgnoreCase("Unfollow")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=unfollowUser&reciever=" + map.get("user_id") + "&sender=" + CommonsUtils.getPrefString(ProfileActivity.this, "userid");
                    follow.setText("Follow");
                    String follow = followers.getText().toString();
                    //  Toast.makeText(getApplicationContext(),follow,Toast.LENGTH_LONG).show();
                    followers.setText(Math.abs(Integer.parseInt(follow)-1)+"");
                    GetSummaryTask_unfollow gst = new GetSummaryTask_unfollow();
                        gst.execute(new String[]{url});
                    }





            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProfileActivity.this, EditProfileActivity.class).putExtra("name", map.get("user_fullname"))
                        .putExtra("gender", map.get("user_sex"))
                        .putExtra("location", map.get("location_full"))
                        .putExtra("mobile", map.get("phone"))
                        .putExtra("dob", map.get("user_dob"))
                        .putExtra("email", map.get("user_email"))
                        .putExtra("gender", map.get("user_sex"))
                        .putExtra("lat", map.get("lat"))
                        .putExtra("lon", map.get("lon"))
                        .putExtra("user_img", map.get("user_img"))
                        .putExtra("occupation", map.get("occupation")),1);
            }
        });


        RoundedImageView ivProfile = (RoundedImageView) this.findViewById(R.id.iv_profile);
        if (map.get("user_img") != null) {
            LoadProfileImageAsync task = new LoadProfileImageAsync(ivProfile, this);
            task.execute(map.get("user_img").replace("32", "120"));
        }
        final Boolean isVisible;
        if(llOthers.getVisibility()==View.VISIBLE){
            isVisible = true;
        }else{
            isVisible = false;
        }
        TextView tvFolloers = (TextView) findViewById(R.id.tv_followers_title);
        tvFolloers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                startActivity(new Intent(ProfileActivity.this, FollowersActivity.class).putExtra("userid", map.get("user_id")).putExtra("type", 0).putExtra("others",isVisible));
            finish();
            }
        });
        TextView tvFollows = (TextView) findViewById(R.id.tv_follows_title);

        tvFollows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this, FollowersActivity.class).putExtra("userid", map.get("user_id")).putExtra("type", 1).putExtra("others",isVisible));
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}