package com.appzollo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.Comments;
import com.appzollo.classes.EventDetail;
import com.appzollo.classes.EventDetailSticker;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadInterestIcons;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnLoadInterestListener;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class EventClicked extends FragmentActivity implements OnLoadCompleteListener, OnLoadInterestListener,
        OnPostCompleteListener {

    public String url = "http://www.365hops.com/webservice/controller.php?Servicename=geteventdetails&id=";
    private String urlCOmments = "http://www.365hops.com/webservice/controller.php?Servicename=geteventcomments&type=Event&id=";
    String pathUrl = "http://www.365hops.com/webservice/controller.php?Servicename=posteventcomments&type=Event&id=";

    private static final String TAG = "365hops";

    private JSONParser jParser;
    GetSummaryTask gst;
    Bundle extras = new Bundle();
    Button bt_details, bt_cost, bt_action;
    LinearLayout ll_details, ll_cost, ll_action, ll_cotainer, ll_stickerlayout;
    private EventDetailSticker eventDetailStickers;

    TextView tv_heading, tv_amount, tv_date, tv_where, tv_duration, tv_difficulty,
            tv_details, tv_cost, tv_action, tv_similar_name, tv_similar_cost;
    FrameLayout fl_eventpic;

    ImageView mFood, mBed, mBus, iv_similar;
    ProgressBar progress, progress_main;
    Button bt_intrest, bt_comment;
    private LinearLayout commentsLayout, commentsLayout1;
    EditText etComment;
    LinearLayout llInterest, llSegments;
    TextView tvInterest;
    Button btSend;
    String status;
    ArrayList<String> ids = new ArrayList<String>();
    String id, eventUserId;
    ArrayList<ArrayList<String>> similar = new ArrayList<ArrayList<String>>();
    PostDataToServer post;
    ProgressDialog mDialog;
    private ScrollView scrollView;
    EventDetail events;
    RoundedImageView iv;
    boolean showComments;
    MenuItem offline;
    MenuItem edit, delete, location, report;

    String event_edit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_details);

        final Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        showComments = bundle.getBoolean("show_comments", false);
        eventUserId = bundle.getString("userid");
        String userId = CommonsUtils.getPrefString(this, "userid");

        // url += id;
        urlCOmments += id + "&userid=" + userId;
        pathUrl += id + "&userid=" + userId;
        event_edit = bundle.getString("event_edit");


        scrollView = (ScrollView) findViewById(R.id.event_scroll_view);
        bt_details = (Button) findViewById(R.id.bt_details);
        bt_action = (Button) findViewById(R.id.bt_action);
        bt_cost = (Button) findViewById(R.id.bt_cost);
        bt_intrest = (Button) findViewById(R.id.bt_interest);
        bt_comment = (Button) findViewById(R.id.bt_comment);
        iv = (RoundedImageView) findViewById(R.id.iv_profile_icon1);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress_main = (ProgressBar) findViewById(R.id.progressBar_main);

        tv_heading = (TextView) findViewById(R.id.tv_heading);
        tv_heading.setVisibility(View.GONE);
        tv_amount = (TextView) findViewById(R.id.tv_amt_price);
        tv_date = (TextView) findViewById(R.id.tv_date_selected);
        tv_where = (TextView) findViewById(R.id.tv_places);
        tv_duration = (TextView) findViewById(R.id.tv_durationTime);
        tv_difficulty = (TextView) findViewById(R.id.tv_difficulty_level);
        tv_details = (TextView) findViewById(R.id.tv_details);
        tv_cost = (TextView) findViewById(R.id.tv_cost);
        tv_action = (TextView) findViewById(R.id.tv_action);

        mFood = (ImageView) findViewById(R.id.iv_food);
        mBed = (ImageView) findViewById(R.id.iv_bed);
        mBus = (ImageView) findViewById(R.id.iv_bus);

        fl_eventpic = (FrameLayout) findViewById(R.id.fl_framelayout);

        ll_details = (LinearLayout) findViewById(R.id.ll_details);
        ll_cost = (LinearLayout) findViewById(R.id.ll_cost);
        ll_action = (LinearLayout) findViewById(R.id.ll_actions);
        ll_cotainer = (LinearLayout) findViewById(R.id.ll_container);
        ll_stickerlayout = (LinearLayout) findViewById(R.id.sticker_layout);
        commentsLayout = (LinearLayout) findViewById(R.id.comments_layout);
        commentsLayout1 = (LinearLayout) findViewById(R.id.comments_layout1);
        llInterest = (LinearLayout) findViewById(R.id.ll_interest);
        llSegments = (LinearLayout) findViewById(R.id.llSegemnts);
        tvInterest = (TextView) findViewById(R.id.tv_interest);


        bt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_details.setBackgroundColor(0xff437185);
                // bt_details.setTextColor(0xffffffff);
                bt_action.setBackgroundColor(0xfff4f4f4);
                bt_cost.setBackgroundColor(0xfff4f4f4);

                ll_details.setVisibility(View.VISIBLE);
                ll_cost.setVisibility(View.GONE);
                ll_action.setVisibility(View.GONE);

            }
        });

        bt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_details.setBackgroundColor(0xfff4f4f4);
                // bt_action.setTextColor(0xffffffff);
                bt_action.setBackgroundColor(0xff437185);
                bt_cost.setBackgroundColor(0xfff4f4f4);

                ll_details.setVisibility(View.GONE);
                ll_action.setVisibility(View.VISIBLE);
                ll_cost.setVisibility(View.GONE);

            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventDetailStickers != null) {
                    Intent i = new Intent(EventClicked.this, ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", eventDetailStickers.getData().getUser_id());
                    i.putExtra("recieverid", CommonsUtils.getPrefString(EventClicked.this, "userid"));
                    startActivity(i);
                }
            }
        });
        bt_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_details.setBackgroundColor(0xfff4f4f4);
                bt_action.setBackgroundColor(0xfff4f4f4);
                bt_cost.setBackgroundColor(0xff437185);
                // bt_cost.setTextColor(0xffffffff);

                ll_cost.setVisibility(View.VISIBLE);
                ll_action.setVisibility(View.GONE);
                ll_details.setVisibility(View.GONE);
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(0xffffffff));
        jParser = new JSONParser();
        gst = new GetSummaryTask();

        gst.execute(new String[]{url + id + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid")});
        if (showComments) {
            focusOnView();
            PostDataToServer post = new PostDataToServer(EventClicked.this);
            post.execute(urlCOmments);
        }

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentsLayout.getVisibility() == View.VISIBLE) {
                    commentsLayout.setVisibility(View.GONE);
                } else {
                    PostDataToServer post = new PostDataToServer(EventClicked.this);
                    post.execute(urlCOmments);
                }
            }
        });
        etComment = (EditText) findViewById(R.id.etComment);
        btSend = (Button) findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDataToServer post = new PostDataToServer(EventClicked.this);
                post.execute(pathUrl + "&comment=" + etComment.getText().toString().replace(" ", "%20"));
                //commentsLayout1.removeAllViews();
                PostDataToServer post1 = new PostDataToServer(EventClicked.this);
                post1.execute(urlCOmments);
                etComment.setText("");
            }
        });
        /*final PostDataToServer post = new PostDataToServer(this);
        final PostDataToServer post1 = new PostDataToServer(this);
        */
        llInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("0")) {
                    String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowintrest&type=Event&id=" + bundle.getString("id") + "&userid=" + CommonsUtils.getPrefString(EventClicked.this, "userid");
                    tvInterest.setText("Interested");
                    bt_intrest.setText("" + (Integer.parseInt(bt_intrest.getText().toString()) + 1));
                    Drawable interest = getResources().getDrawable(R.drawable.interest_icon);
                    bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                    status = "1";
                    post = new PostDataToServer(EventClicked.this);
                    post.execute(url);
                    showDialog(bundle.getString("id"));

                } else {
                    String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowunintrest&type=Event&id=" + bundle.getString("id") + "&userid=" + CommonsUtils.getPrefString(EventClicked.this, "userid");
                    tvInterest.setText("Show Interest");
                    bt_intrest.setText("" + (Integer.parseInt(bt_intrest.getText().toString()) - 1));
                    Drawable interest = getResources().getDrawable(R.drawable.showinterest);
                    bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                    status = "0";
                    post = new PostDataToServer(EventClicked.this);
                    post.execute(url);
                }
                //mDialog = ProgressDialog.show(EventClicked.this, "", "Loading...");

            }
        });


    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                b.setVisibility(View.GONE);
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
        if (bitmap == null && view != null) {
            if (view instanceof FrameLayout) {
                ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                b.setVisibility(View.GONE);
               view.setBackgroundResource(R.drawable.bike);

                // view.setVisibility(View.GONE);
                //  view.setBackgroundResource(R.drawable.bike);
            }
        }
    }

    @Override
    public void onPostComplete(String json) {
        if (mDialog != null
                && mDialog.isShowing()) {
            try {
                JSONObject jObj = new JSONObject(json);
                String message = jObj.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
            mDialog.dismiss();

        }
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                int status = jObj.getInt("success");

                if (status == 0) {
                    String message = jObj.getString("message");
                    commentsLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(EventClicked.this, message, Toast.LENGTH_SHORT).show();
                    if (showComments) {
                        scrollView.scrollTo(0, scrollView.getBottom());
                        //scrollview.fullScroll(View.FOCUS_DOWN);
                        Toast.makeText(getApplicationContext(), showComments + "", Toast.LENGTH_SHORT).show();
                    }
                } else if (status == 1) {
                    commentsLayout.setVisibility(View.VISIBLE);
                    if (commentsLayout1.getChildCount() > 1)
                        commentsLayout1.removeViews(1, commentsLayout1.getChildCount());
                    Gson gson = new Gson();
                    final Comments comments = gson.fromJson(json, Comments.class);
                    if (comments != null
                            && comments.getData() != null
                            && comments.getData().size() > 0) {
                        for (int i = 0; i < comments.getData().size(); i++) {
                            final Comments.Data data = comments.getData().get(i);
                            View view = View.inflate(this, R.layout.comment_item, null);
                            TextView tvComment = (TextView) view.findViewById(R.id.tv_comment);
                            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                            RoundedImageView profilePic = (RoundedImageView) view.findViewById(R.id.iv_profile_icon1);
                            profilePic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(EventClicked.this, ProfileActivity.class);
                                    i.putExtra("my", false);
                                    i.putExtra("userid", data.getUser_id());
                                    i.putExtra("recieverid", CommonsUtils.getPrefString(EventClicked.this, "userid"));
                                    startActivity(i);
                                }
                            });
                            tvComment.setText(data.getComment());

                            tvDate.setText(data.getDate());
                            commentsLayout1.addView(view);
                            LoadProfileImageAsync task = new LoadProfileImageAsync(profilePic, EventClicked.this);
                            task.execute(data.getUser_img());
                        }
                    }
                    if (showComments) {
                        etComment.requestFocus();
                        //  scrollView.scrollTo(0, scrollView.getBottom());
                        //scrollview.fullScroll(View.FOCUS_DOWN);
                        Toast.makeText(getApplicationContext(), showComments + "", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {

            }
        }
        //  gst = new GetSummaryTask();

        //gst.execute(new String[]{url+id+"&userid="+CommonsUtils.getPrefString(getApplicationContext(),"userid")});
    }

    @Override
    public void onInterestLoadComplete(LinearLayout view, ArrayList<Bitmap> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ImageView iv = new ImageView(this);
                iv.setImageBitmap(list.get(i));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                iv.setLayoutParams(layoutParams);
                view.addView(iv);
            }
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
                    Gson gson = new Gson();

                    eventDetailStickers = gson.fromJson(json, EventDetailSticker.class);


                    content.close();

                } catch (Exception ex) {
                    Log.e(TAG, "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            handlePostsList();
        }

        @Override
        protected void onPreExecute() {


        }
    }

    public class GetSummaryTask_similar extends AsyncTask<String, Void, String> {

        private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();

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
                    // Gson gson = new Gson();

                    //eventDetailStickers = gson.fromJson(json, EventDetailSticker.class);

                    JSONObject jObj = new JSONObject(json);
                    if (jObj.getString("success").equalsIgnoreCase("1")) {
                        JSONArray data = jObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            ArrayList<String> info = new ArrayList<String>();
                            info.add(obj.getString("id"));
                            info.add(obj.getString("name"));
                            info.add(obj.getString("cost"));
                            info.add(obj.getString("image"));
                            similar.add(info);
                            URL url = new URL(obj.getString("image"));
                            HttpURLConnection connection;

                            connection = (HttpURLConnection) url.openConnection();

                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap bitmap1 = BitmapFactory.decodeStream(input);
                            bitmap.add(bitmap1);
                            Log.d("ikkkkkkkkk", info.toString());

                        }
                    }
                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e(TAG, "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            ll_cotainer.removeAllViews();
            ids.clear();
            for (int j = 0; j < similar.size(); j++) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.similar_layout, null);

                tv_similar_cost = (TextView) addView.findViewById(R.id.tv_cost);
                tv_similar_name = (TextView) addView.findViewById(R.id.tv_name);
                iv_similar = (ImageView) addView.findViewById(R.id.iv_similar);

                ll_cotainer.addView(addView);
                ids.add(similar.get(j).get(0));
                tv_similar_cost.setText("Approx Cost: " + similar.get(j).get(2) + "");
                tv_similar_name.setText(similar.get(j).get(1) + "");

                if (bitmap.size() > j) {
                    Drawable d = new BitmapDrawable(getResources(), bitmap.get(j));
                    iv_similar.setBackground(d);
                }

            }
            similar.clear();
            if (ll_cotainer.getChildCount() > 0) {
                ll_cotainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ll_cotainer.removeAllViews();

                        gst = new GetSummaryTask();

                        gst.execute(new String[]{url + ids.get(0) + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid")});
                        //Toast.makeText(EventClicked.this,""+ll_cotainer.getChildCount(),Toast.LENGTH_SHORT).show();
                    }
                });
                if (ll_cotainer.getChildCount() > 1) {
                    ll_cotainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ll_cotainer.removeAllViews();

                            gst = new GetSummaryTask();

                            gst.execute(new String[]{url + ids.get(1) + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid")});
                            //Toast.makeText(EventClicked.this,""+ll_cotainer.getChildCount(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }


        }

        @Override
        protected void onPreExecute() {


        }
    }


    public class GetSummaryTask_wrongloc extends AsyncTask<String, Void, String> {

        private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
        HashMap<String, String> map_settings = new HashMap<String, String>();

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

                    map_settings.put("success_set", jObj.getString("success"));
                    map_settings.put("message_set", jObj.getString("message"));
                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e(TAG, "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            mDialog.dismiss();
            // if(map_settings.get("success_set").equalsIgnoreCase("1"))
            Toast.makeText(getApplicationContext(), "Thanks for reporting this", Toast.LENGTH_SHORT).show();
            //else
            //      Toast.makeText(getApplicationContext(),map_settings.get("message_set"),Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(EventClicked.this, "", "Loading...");
            mDialog.setCancelable(true);

        }
    }


    public class GetSummaryTask_settings extends AsyncTask<String, Void, String> {

        private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
        HashMap<String, String> map_settings = new HashMap<String, String>();
        String url = "";
        @Override
        protected String doInBackground(String... urls) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(urls[0]);
                url = urls[0];
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
                    Log.d("offlineobj", json);
                    JSONObject jObj = new JSONObject(json);

                    map_settings.clear();
                    map_settings.put("success_set", jObj.getString("success"));
                    map_settings.put("message_set", jObj.getString("message"));
                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e(TAG, "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            // mDialog.dismiss();
            if(url.contains("delete")){
                Toast.makeText(getApplicationContext(), map_settings.get("message_set"), Toast.LENGTH_SHORT).show();

                Intent goToMainActivity = new Intent(getApplicationContext(),PagerActivity.class);
                goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Will clear out your activity history stack till now
                startActivity(goToMainActivity);
                finish();
            }
           else if (offline.getTitle().toString().equalsIgnoreCase("Make Offline")) {
                Toast.makeText(getApplicationContext(), "Onlined successfully", Toast.LENGTH_SHORT).show();

            } else if (offline.getTitle().toString().equalsIgnoreCase("Make Online")) {
                Toast.makeText(getApplicationContext(), "Offlined successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), map_settings.get("message_set"), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            //mDialog = ProgressDialog.show(EventClicked.this, "", "Loading...");
            //mDialog.setCancelable(true);

        }
    }

    private void handlePostsList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (eventDetailStickers != null) {
                    if (eventDetailStickers.getSuccess() == 1) {

                        events = eventDetailStickers.getData();
                        if (CommonsUtils.getPrefString(getApplicationContext(), "userid")
                                .equalsIgnoreCase(eventUserId)) {
                            edit.setVisible(true);
                            delete.setVisible(true);

                            offline.setVisible(true);
                            location.setVisible(false);
                            report.setVisible(false);
                        } else {
                            edit.setVisible(false);
                            delete.setVisible(false);
                            offline.setVisible(false);
                            location.setVisible(true);
                            report.setVisible(true);
                        }
                        if (events.getLive().equals("0")) {
                            offline.setTitle("Make Online");
                        } else {
                            offline.setTitle("Make Offline");
                        }

                        tv_heading.setVisibility(View.VISIBLE);
                        tv_heading.setText(events.getTitle());
                        tv_amount.setText("Rs " + events.getCost());
                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d;
                        String formattedTime = null;
                        //  Log.d("ppppppppppllllllll", events.getImage().get(0));

                        try {
                            d = output.parse(events.getDate());
                            formattedTime = output.format(d);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        //   tv_date.setText("" + (new SimpleDateFormat("MMMM yyyy")).format(Timestamp.valueOf(formattedTime)));
                        SimpleDateFormat output1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1, d2;
                        String formattedTime1 = null;
                        String formattedTime2 = null;
                        try {
                            d1 = output1.parse(events.getDate_from() + " 00:00:00");
                            formattedTime1 = output1.format(d1);

                            d2 = output1.parse(events.getDate_to() + " 00:00:00");
                            formattedTime2 = output1.format(d2);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        if (events.getCustome_date() == null)
                            tv_date.setText("" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime1)));
                        else
                            tv_date.setText(events.getCustome_date());
                        if (events.getIsfood().equals("1")) {
                            mFood.setBackgroundResource(R.drawable.dinner);
                            //mFood.setVisibility(View.INVISIBLE);
                        }
                        if (events.getIstravel().equals("1")) {
                            mBus.setBackgroundResource(R.drawable.bus);
                            // mBus.setVisibility(View.INVISIBLE);
                        }
                        if (events.getIsaccommodation().equals("1")) {
                            mBed.setBackgroundResource(R.drawable.sleep);
                            //  mBed.setVisibility(View.INVISIBLE);
                        }

                        if (events.getIntrest_status().equals("0")) {
                            tvInterest.setText("Show Interest");
                        } else {
                            tvInterest.setText("Interested");
                        }

                        tv_where.setText(events.getWhere_area());
                        tv_duration.setText(events.getDuration());
                        tv_difficulty.setText(events.getDifficulty_level());
                        tv_details.setText(Html.fromHtml(events.getDescription()));
                        Log.d("id", id);
                        if (events.getCost_description() != null)
                            tv_cost.setText(Html.fromHtml(events.getCost_description()));

                        TextView extraFood = (TextView) findViewById(R.id.tv_extra_food);
                        TextView extraBed = (TextView) findViewById(R.id.tv_extra_accm);
                        TextView extraTravel = (TextView) findViewById(R.id.tv_extra_travel);
                        TextView extraSafe = (TextView) findViewById(R.id.tv_safety_travel);

                        TextView tvAgo = (TextView) findViewById(R.id.tv_last_active);
                        TextView tvCast = (TextView) findViewById(R.id.tv_cast_place);
                        tvAgo.setText(events.getTimeago());
                        tvCast.setText(events.getCity_creat());

                        extraFood.setText(events.getFood());
                        extraBed.setText(events.getAccommodation());
                        extraTravel.setText(events.getTravel());
                        extraSafe.setText(events.getOther());
                        List<String> list = new ArrayList<String>();
                        for (int p = 0; p < events.getSegment().size(); p++) {
                            //    Log.d("icon",events.getSegment().get(p).getIcon_small());
                            list.add(events.getSegment().get(p).getIcon_small().replace("32","50"));
                        }
                        llSegments.removeAllViews();
                        LoadInterestIcons post = new LoadInterestIcons(llSegments, EventClicked.this, false, list);
                        post.execute("");

                        if (events.getSteps() != null)
                            tv_action.setText(Html.fromHtml(events.getSteps().replace("##", "<br>")));

                        status = events.getIntrest_status();
                        bt_intrest.setText(events.getIntrests());
                        bt_comment.setText(events.getComments());

                        Drawable interest, comments;
                        if (Integer.parseInt(events.getIntrest_status()) > 0) {
                            interest = getResources().getDrawable(R.drawable.interest_icon);
                        } else {
                            interest = getResources().getDrawable(R.drawable.showinterest);
                        }
                        bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);
                        if (Integer.parseInt(events.getComments_status()) > 0) {
                            comments = getResources().getDrawable(R.drawable.comment);
                        } else {
                            comments = getResources().getDrawable(R.drawable.comment_icon);
                        }

                        bt_comment.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);

                        ll_stickerlayout.setVisibility(View.VISIBLE);
                        progress_main.setVisibility(View.GONE);
                        LoadProfileImageAsync task = new LoadProfileImageAsync(fl_eventpic, EventClicked.this);
                        task.execute(events.getImage().get(0));
                        GetSummaryTask_similar gst_similar = new GetSummaryTask_similar();

                        task = new LoadProfileImageAsync(iv, EventClicked.this);
                        task.execute(events.getUser_img());

                        TextView mName = (TextView) findViewById(R.id.tv_name);
                        mName.setText(events.getUser_fullname());

                        gst_similar.execute(new String[]{"http://www.365hops.com/webservice/controller.php?Servicename=GetSimilarEvents&eventid=" + id});
                        fl_eventpic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(EventClicked.this, GalleryActivity.class);
                                String img[] = new String[events.getImage().size()];
                                for (int j = 0; j < events.getImage().size(); j++) {
                                    img[j] = events.getImage().get(j);
                                }
                                i.putExtra("images", img);
                                startActivity(i);
                            }
                        });
                        scrollView.smoothScrollTo(0, 0);
                    } else {
                        progress_main.setVisibility(View.GONE);
                    }
                } else {
                    progress_main.setVisibility(View.GONE);
                }


            }
        });
    }

    private final void focusOnView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, etComment.getBottom());
            }
        });
    }

    private void handleImage(final String photo) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = getImg(photo);


            }
        });
    }

    public Bitmap getImg(String photo) {
        String st;

        Bitmap myBitmap = null;

        return myBitmap;
    }

    private void failedLoadingPosts() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_clicked, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
         edit = menu.findItem(R.id.action_edit);
         delete = menu.findItem(R.id.action_delete);
        offline = menu.findItem(R.id.action_offline);
         location = menu.findItem(R.id.action_wrong_location);
         report = menu.findItem(R.id.action_report);


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (event_edit != null && event_edit.equalsIgnoreCase("1")) {

                startActivity(new Intent(EventClicked.this, ProfileActivity.class).putExtra("my", true).putExtra("userid",eventUserId));
            }

            finish();
        } else if (item.getItemId() == R.id.action_wrong_location) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=wronLocationTag&id=" + id +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=Event";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_report) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=reportAbuse&id=" + id +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=Event";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_delete) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=deleteEvent&eventid=";
            url += id + "&userid=" + eventUserId;
            GetSummaryTask_settings gst = new GetSummaryTask_settings();
            gst.execute(url);

        } else if (item.getItemId() == R.id.action_offline) {
            String url = "";
            if (offline.getTitle().equals("Make Online")) {
                url = "http://www.365hops.com/webservice/controller.php?Servicename=makeEventOnline&eventid=";
                offline.setTitle("Make Offline");

            } else {
                url = "http://www.365hops.com/webservice/controller.php?Servicename=makeEventOffline&eventid=";
                offline.setTitle("Make Online");
            }

            url += id + "&userid=" + eventUserId;
            GetSummaryTask_settings gst = new GetSummaryTask_settings();
            Log.d("offline", url);
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(EventClicked.this, ShareNewEventActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("event", events);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void showDialog(final String id) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.overlay_image);
        dialog.setContentView(R.layout.interested_form);
        final EditText number = (EditText) dialog.findViewById(R.id.et_number);
        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.checkBox);
        final Spinner code = (Spinner) dialog.findViewById(R.id.spinner);
        final Spinner people = (Spinner) dialog.findViewById(R.id.spinner2);
        TextView done = (TextView) dialog.findViewById(R.id.bt_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = number.getEditableText().toString();
                String check;
                check = checkBox.isChecked() ? "1" : "0";
                String mcode = code.getSelectedItem().toString();
                String pcount = people.getSelectedItem().toString();

                String url = "http://www.365hops.com/webservice/controller.php?Servicename=sendEventInquiry&" +
                        "id=" + id + "&mobile=" + (mcode + "" + mobile) + "&email=" + CommonsUtils.getPrefString(EventClicked.this, "email")
                        + "&peoples=" + pcount + "&check=" + check + "&name=" + CommonsUtils.getPrefString(getApplicationContext(), "user");
                PostDataToServer post = new PostDataToServer(EventClicked.this);
                if (mobile.length() > 0) {
                    post.execute(url.replace(" ", "%20"));
                    mDialog = ProgressDialog.show(EventClicked.this, "", "Loading...");
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (event_edit != null && event_edit.equalsIgnoreCase("1")) {

            startActivity(new Intent(EventClicked.this, ProfileActivity.class).putExtra("my", true).putExtra("userid", eventUserId));

        }
        finish();
    }
}
