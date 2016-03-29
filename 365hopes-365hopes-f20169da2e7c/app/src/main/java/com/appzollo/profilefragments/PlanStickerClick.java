package com.appzollo.profilefragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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

import com.appzollo.GalleryActivity;
import com.appzollo.PagerActivity;
import com.appzollo.ProfileActivity;
import com.appzollo.R;
import com.appzollo.TagPlaceActivity;
import com.appzollo.classes.CircularImageView;
import com.appzollo.classes.Comments;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadInterestIcons;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnLoadInterestListener;
import com.appzollo.classes.PlanStickerDetails;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PlanStickerClick extends FragmentActivity implements OnLoadCompleteListener, OnPostCompleteListener, OnLoadInterestListener {

    public String url = "http://www.365hops.com/webservice/controller.php?Servicename=getPlaneSticker&id=";
    private static final String TAG = "365hops";
    private String urlCOmments = "http://www.365hops.com/webservice/controller.php?Servicename=geteventcomments&type=Plan&id=";
    String pathUrl = "http://www.365hops.com/webservice/controller.php?Servicename=posteventcomments&type=Plan&id=";


    private JSONParser jParser;
    GetSummaryTask gst;
    JSONObject jObj;
    String id;

    ProgressBar progress, progress_main;

    private PlanStickerDetails planDetailStickers;

    TextView tv_timeago, tv_city, tv_wherearea, tv_createddate, tv_title, tv_name, tv_interest;
    Button bt_intrest, bt_comment;
    HashMap<String, String> map = new HashMap<String, String>();
    FrameLayout fl_pic;
    LinearLayout ll_main;
    CircularImageView iv_profile;
    private LinearLayout commentsLayout, commentsLayout1, llSegments,llEdit;
    EditText etComment;
    Button btSend;
    LinearLayout lInterest;
    ProgressDialog mDialog;
    String planId;
    Comments.Data data;
    String userId, planUserId;
    List<String> list = new ArrayList<String>();
    ScrollView scrollview;

    boolean showComments;
    String plan_edit = "";
    MenuItem edit,delete, offline, location , report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_sticker_click);

        scrollview = (ScrollView) findViewById(R.id.sv_scroll);
        Bundle  bundle = getIntent().getExtras();
        planId = bundle.getString("id");
        id = bundle.getString("userid");
        showComments = bundle.getBoolean("show_comments", false);
        userId = CommonsUtils.getPrefString(this, "userid");
        planUserId = bundle.getString("userid");
        plan_edit = bundle.getString("plan" +
                "_edit");
        urlCOmments += planId + "&userid=" + userId;
        pathUrl += planId + "&userid=" + userId;
        llSegments = (LinearLayout) findViewById(R.id.llSegemnts);
        tv_interest = (TextView) findViewById(R.id.tv_interest);
        bt_intrest = (Button) findViewById(R.id.bt_interest);
        bt_comment = (Button) findViewById(R.id.bt_comment);
        iv_profile = (CircularImageView) findViewById(R.id.iv_profile_icon);
        lInterest = (LinearLayout) findViewById(R.id.ll_interest);
        llEdit = (LinearLayout) findViewById(R.id.lledit);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress_main = (ProgressBar) findViewById(R.id.progressBar_main);

        fl_pic = (FrameLayout) findViewById(R.id.fl_pic);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_name = (TextView) findViewById(R.id.tv_name);

        tv_timeago = (TextView) findViewById(R.id.tv_timeago);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_wherearea = (TextView) findViewById(R.id.tv_wherearea);
        tv_createddate = (TextView) findViewById(R.id.tv_date_selected);

        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        commentsLayout = (LinearLayout) findViewById(R.id.comments_layout);
        commentsLayout1 = (LinearLayout) findViewById(R.id.comments_layout1);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(0xffffffff));
        jParser = new JSONParser();
        gst = new GetSummaryTask();

        gst.execute(new String[]{url + planId + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid")});
        Log.d("url", url + planId + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid"));
        if (showComments) {
            PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
            post.execute(urlCOmments);
        }


        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentsLayout.getVisibility() == View.VISIBLE) {
                    commentsLayout.setVisibility(View.GONE);
                } else {

                    showComments = true;
                    PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
                    post.execute(urlCOmments);
                }
            }
        });
        etComment = (EditText) findViewById(R.id.etComment);
        btSend = (Button) findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
                post.execute(pathUrl + "&comment=" + etComment.getText().toString().replace(" ", "%20"));
                //commentsLayout1.removeAllViews();
                PostDataToServer post1 = new PostDataToServer(PlanStickerClick.this);
                post1.execute(urlCOmments);
                etComment.setText("");
            }
        });


    }

    @Override
    public void onInterestLoadComplete(LinearLayout view, ArrayList<Bitmap> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ImageView iv = new ImageView(this);
                iv.setImageBitmap(list.get(i));
                view.addView(iv);
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
                    Toast.makeText(PlanStickerClick.this, message, Toast.LENGTH_SHORT).show();
                    if(showComments) {
                        scrollview.scrollTo(0, scrollview.getBottom());
                        //scrollview.fullScroll(View.FOCUS_DOWN);
                        Toast.makeText(getApplicationContext(),showComments+"",Toast.LENGTH_SHORT).show();
                    }
                } else if (status == 1) {
                    commentsLayout.setVisibility(View.VISIBLE);
                    if (commentsLayout1.getChildCount() > 1)
                        commentsLayout1.removeViews(1, commentsLayout1.getChildCount());
                    Gson gson = new Gson();
                    Comments comments = gson.fromJson(json, Comments.class);


                    if (comments != null
                            && comments.getData() != null
                            && comments.getData().size() > 0) {

                        for (int i = 0; i < comments.getData().size(); i++) {
                            data = comments.getData().get(i);
                            View view = View.inflate(this, R.layout.comment_item, null);
                            TextView tvComment = (TextView) view.findViewById(R.id.tv_comment);
                            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                            RoundedImageView profilePic = (RoundedImageView) view.findViewById(R.id.iv_profile_icon1);
                            tvComment.setText(data.getComment());
                            tvDate.setText(data.getDate());
                            commentsLayout1.addView(view);
                            LoadProfileImageAsync task = new LoadProfileImageAsync(profilePic, PlanStickerClick.this);
                            task.execute(data.getUser_img());
                        }
                    }
                    if(showComments) {
                        llEdit.requestFocus();
                       // scrollview.scrollTo(0, llEdit.getBottom());
                        //scrollview.fullScroll(View.FOCUS_DOWN);
                       // Toast.makeText(getApplicationContext(),showComments+"",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {

            }
        }

        //gst = new GetSummaryTask();
        //gst.execute(new String[]{url+planId+"&userid="+CommonsUtils.getPrefString(getApplicationContext(),"userid")});

    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {

                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                b.setVisibility(View.GONE);
                ImageView iv = (ImageView)view.findViewById(R.id.iv_pic);
                iv.setVisibility(View.VISIBLE);
                LinearLayout llTop =  (LinearLayout)view.findViewById(R.id.llStickerTop);
                llTop.setVisibility(View.VISIBLE);
                LinearLayout llBottom =  (LinearLayout)view.findViewById(R.id.llSticker);
                llBottom.setVisibility(View.GONE);
                Drawable d = new BitmapDrawable(bitmap);
                if (d != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        iv.setBackground(d);
                    } else {
                        iv.setBackgroundDrawable(d);
                    }
                }

            }
        }else {
            if (bitmap == null && view != null) {
                if (view instanceof FrameLayout) {
                    ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                    b.setVisibility(View.GONE);
                    ImageView iv = (ImageView)view.findViewById(R.id.iv_pic);
                    iv.setVisibility(View.GONE);
                    LinearLayout llTop =  (LinearLayout)view.findViewById(R.id.llStickerTop);
                    llTop.setVisibility(View.GONE);
                    LinearLayout llBottom =  (LinearLayout)view.findViewById(R.id.llSticker);
                    llBottom.setVisibility(View.VISIBLE);
                   /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    view.setLayoutParams(params);*/

                    // view.setVisibility(View.GONE);
                    //  view.setBackgroundResource(R.drawable.bike);
                }else  if (view instanceof ImageView) {
                  /*  ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                    b.setVisibility(View.GONE);
                    ((ImageView) view).setVisibility(View.GONE);*/
                }
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
                Log.d("url", urls[0]);
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
                    Gson  gson = new Gson();
                    Log.d("JSON", "" + json);
                    planDetailStickers = gson.fromJson(json, PlanStickerDetails.class);


                    jObj = new JSONObject(json);

                    String success = jObj.getString("success");
                    map.put("sucess", success);
                    if (success.endsWith("1")) {
                        //   JSONArray data = jObj.getJSONArray("data");
                        JSONObject data = jObj.getJSONObject("data");

                        map.put("user_img", data.getString("user_img"));
                        map.put("image", data.getString("image"));
                        map.put("timeago", data.getString("timeago"));
                        map.put("create_date", data.getString("creation_date"));
                        map.put("date_to", data.getString("date_to"));
                        map.put("date_from", data.getString("date_from"));
                        map.put("comments", data.getString("comments"));
                        map.put("comments_status", data.getString("comments_status"));
                        map.put("intrests", data.getString("intrests"));
                        map.put("intrest_status", data.getString("intrest_status"));
                        map.put("segmentid", data.getString("segmentid"));
                        map.put("title", data.getString("title"));
                        map.put("where_area", data.getString("where_area"));
                        map.put("latitude_creat", data.getString("latitude_creat"));
                        map.put("longitude_creat", data.getString("longitude_creat"));
                        map.put("city_creat", data.getString("city_creat"));
                        map.put("user_id", data.getString("user_id"));
                        map.put("user_fullname", data.getString("user_fullname"));
                        JSONArray dataInterests = data.getJSONArray("segmentid");

                        for (int i = 0; i < dataInterests.length(); i++) {
                            JSONObject obj = dataInterests.getJSONObject(i);
                            list.add("http://www.365hops.com/searching_files/timthumb.php?src="+obj.getString("icon_small")+"&h=50&w=50&zc=1");
                        }

                        Log.d("plan url", map.get("image"));
                       /* JSONArray segment = data.getJSONArray("segmentid");
                        map.put("segment_size",segment.length()+"");
                        for(int i = 0 ; i < segment.length() ; i++){
                            map.put("segment_id",segment.getJSONObject(i).getString("id"));
                            list.add(obj.getString("icon_small"));
                            map.put("icon_small"+i,segment.getJSONObject(i).getString("icon_small"));
                            Log.d("ppppppppp",map.get("icon_small"+i));
                        }*/
                    }
                   /* Gson gson = new Gson();

                    planDetailStickers = gson.fromJson(json, PlanStickerDetails.class);
*/

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


    private void handlePostsList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (map.get("sucess").toString().equalsIgnoreCase("1")) {
                    if (CommonsUtils.getPrefString(getApplicationContext(), "userid")
                            .equalsIgnoreCase(planUserId)) {
                        edit.setVisible(true);
                        delete.setVisible(true);
                        location.setVisible(false);
                        report.setVisible(false);
                    } else {
                        edit.setVisible(false);
                        delete.setVisible(false);
                        location.setVisible(true);
                        report.setVisible(true);
                    }
                    //   PlanStickerDetails.Events events = planDetailStickers.getData();
                    tv_timeago.setText(map.get("timeago"));

                    if(map.get("city_creat") != null){
                        if(!map.get("city_creat").equals("null"))
                            tv_city.setText(map.get("city_creat"));
                    }


                    tv_wherearea.setText(map.get("where_area"));
                    tv_title.setText(map.get("title"));
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (map.get("create_date") != null) {
                        Date d;
                        String formattedTime = null;

                        try {
                            d = output.parse(map.get("create_date"));
                            formattedTime = output.format(d);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //   tv_createddate.setText("" + (new SimpleDateFormat("MMMM yyyy")).format(Timestamp.valueOf(formattedTime)));
                        SimpleDateFormat output1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1, d2;
                        String formattedTime1 = null;
                        String formattedTime2 = null;
                        try {
                            d1 = output1.parse(map.get("date_from") + " 00:00:00");
                            formattedTime1 = output1.format(d1);

                            d2 = output1.parse(map.get("date_to") + " 00:00:00");
                            formattedTime2 = output1.format(d2);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        tv_createddate.setText("" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime1)) +
                                " to " + "" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime2)));


                        // tv_createddate.setText(map.get("date_from") + " to " + map.get("date_to"));

                    }
                    if (map.get("intrest_status") != null){
                        if (map.get("intrest_status").equals("0")) {
                        tv_interest.setText("Show Interest");

                         } else {
                        tv_interest.setText("Interested");

                     }
                    }

                    tv_name.setText(map.get("user_fullname"));
                    bt_intrest.setText(map.get("intrests"));
                    bt_comment.setText(map.get("comments"));

                    Drawable interest, comments = null;
                      try {
                          if (Integer.parseInt(map.get("intrest_status")) > 0) {
                              interest = getResources().getDrawable(R.drawable.interest_icon);
                          } else {
                              interest = getResources().getDrawable(R.drawable.showinterest);
                          }
                          bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);
                          if (Integer.parseInt(map.get("comments_status")) > 0) {
                              comments = getResources().getDrawable(R.drawable.comment);
                          } else {
                              comments = getResources().getDrawable(R.drawable.comment_icon);
                          }

                      } catch (NumberFormatException e) {
                          // not an integer!
                      }

                    lInterest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (map.get("intrest_status").equals("0")) {

                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowintrest&type=Plan&id=" + planId + "&userid=" + CommonsUtils.getPrefString(PlanStickerClick.this, "userid");
                                tv_interest.setText("Interested");
                                bt_intrest.setText("" + (Integer.parseInt(bt_intrest.getText().toString()) + 1));
                                Drawable interest = getResources().getDrawable(R.drawable.interest_icon);
                                bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                                map.put("intrest_status", "1");
                                PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
                                post.execute(url);
                                //showDialog(planId);
                            } else {
                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowunintrest&type=Plan&id=" + planId + "&userid=" + CommonsUtils.getPrefString(PlanStickerClick.this, "userid");
                                tv_interest.setText("Show Interest");
                                bt_intrest.setText("" + (Integer.parseInt(bt_intrest.getText().toString()) - 1));
                                Drawable interest = getResources().getDrawable(R.drawable.showinterest);
                                bt_intrest.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                                map.put("intrest_status", "0");
                                PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
                                post.execute(url);
                            }
                            //  mDialog = ProgressDialog.show(PlanStickerClick.this, "", "Loading...");

                        }
                    });

                    bt_comment.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);
                    ll_main.setVisibility(View.VISIBLE);
                    progress_main.setVisibility(View.GONE);


                    LoadInterestIcons post = new LoadInterestIcons(llSegments, PlanStickerClick.this, false, list);
                    post.execute("");

                    LoadProfileImageAsync task = new LoadProfileImageAsync(iv_profile, PlanStickerClick.this);
                    task.execute(map.get("user_img"));
                    iv_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(PlanStickerClick.this, ProfileActivity.class);
                            i.putExtra("my", false);
                            i.putExtra("userid", map.get("user_id"));
                            i.putExtra("recieverid", CommonsUtils.getPrefString(PlanStickerClick.this, "userid"));
                            startActivity(i);
                        }
                    });

                    task = new LoadProfileImageAsync(fl_pic, PlanStickerClick.this);
                    task.execute(map.get("image"));
                    fl_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(PlanStickerClick.this, GalleryActivity.class);
                            /*String img[] = new String[Integer.parseInt(map.get("segment_size"))];
                            for(int j = 0 ; j < Integer.parseInt(map.get("segment_size")) ; j++){
                                img[j] = map.get("icon_small"+i);
                            }*/
                            String images[] = {map.get("image")};
                            i.putExtra("images", images);
                            startActivity(i);
                        }
                    });

                }


            }
        });
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

        offline.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (plan_edit != null && plan_edit.equalsIgnoreCase("1")) {

                startActivity(new Intent(PlanStickerClick.this, ProfileActivity.class).putExtra("my", true).putExtra("userid", id));

            }
            finish();
        } else if (item.getItemId() == R.id.action_wrong_location) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=wronLocationTag&id=" + planId +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=Plan";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_report) {

            String url = "http://www.365hops.com/webservice/controller.php?Servicename=reportAbuse&id=" + planId +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=Plan";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_delete) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=deletePlan&id=";
            url += planId + "&userid=" + id;
            GetSummaryTask_settings gst= new GetSummaryTask_settings();
            gst.execute(url);
            Intent goToMainActivity = new Intent(getApplicationContext(),PagerActivity.class);
            goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Will clear out your activity history stack till now
            //goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goToMainActivity);

            finish();
        } else if (item.getItemId() == R.id.action_offline) {

        } else if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(PlanStickerClick.this, TagPlaceActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("plan", planDetailStickers);
            String images[] = {map.get("image")};
            intent.putExtra("images", images);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
                    map_settings.put("success_set",jObj.getString("success"));
                    map_settings.put("message_set",jObj.getString("message"));
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
            //if(map_settings.get("success_set").equalsIgnoreCase("1"))
                Toast.makeText(getApplicationContext(),"Thanks for reporting this",Toast.LENGTH_SHORT).show();
            //else
              //  Toast.makeText(getApplicationContext(),map_settings.get("message_set"),Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(PlanStickerClick.this, "", "Loading...");
            mDialog.setCancelable(true);

        }
    }


    public class GetSummaryTask_settings extends AsyncTask<String, Void, String> {

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
           // mDialog.dismiss();
            if(url.contains("delete")){
                /*Toast.makeText(getApplicationContext(), map_settings.get("message_set"), Toast.LENGTH_SHORT).show();

                Intent goToMainActivity = new Intent(getApplicationContext(),PagerActivity.class);
                goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Will clear out your activity history stack till now
                startActivity(goToMainActivity);
                finish();*/
            }
            Toast.makeText(getApplicationContext(), map_settings.get("message_set"), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(PlanStickerClick.this, "", "Loading...");
            mDialog.setCancelable(true);

        }
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

                String url = "http://www.365hops.com/webservice/controller.php?Servicename=SendInquiryPlace&" +
                        "id=" + id + "&mobile=" + (mcode + "" + mobile) + "&email=&peoples=" + pcount + "&check=" + check;
                PostDataToServer post = new PostDataToServer(PlanStickerClick.this);
                post.execute(url.replace(" ", "%20"));
                mDialog = ProgressDialog.show(PlanStickerClick.this, "", "Loading...");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (plan_edit != null && plan_edit.equalsIgnoreCase("1")) {

            startActivity(new Intent(PlanStickerClick.this, ProfileActivity.class).putExtra("my", true).putExtra("userid",id));


        }
        finish();
    }
}
