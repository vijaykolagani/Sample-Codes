package com.appzollo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.Comments;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadInterestIcons;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnLoadInterestListener;
import com.appzollo.classes.PlaceStickerDetailsPojo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlaceToVisitActivity extends FragmentActivity implements OnLoadCompleteListener,
        OnPostCompleteListener, OnLoadInterestListener {

    @Override
    public void onInterestLoadComplete(LinearLayout view, ArrayList<Bitmap> list) {

        if(list.size()>0){
            for (int i=0;i<list.size();i++) {
                ImageView iv = new ImageView(this);
                iv.setImageBitmap(list.get(i));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                iv.setLayoutParams(layoutParams);
                view.addView(iv);
            }
        }


    }

    String url = "http://www.365hops.com/webservice/controller.php?Servicename=getPlaceToVisit&id=";
    private String urlComments = "http://www.365hops.com/webservice/controller.php?Servicename=PlaceToVisit_comments&placeid=";

    private JSONParser jParser;
    GetSummaryTask gst;

    TextView tv_name, tv_timeago, tv_city, tv_howtoreach, tv_todo, tv_intrest, tv_comment, tv_heading, tv_similar_name, tv_similar_cost;

    Button bt_intrest, bt_comment;

    TextView tv_location;
    private PlaceStickerDetailsPojo placeDetailStickers;

    ArrayList<ArrayList<String>> similar = new ArrayList<ArrayList<String>>();
    ArrayList<String> ids = new ArrayList<String>();

    JSONObject jObj;
    HashMap<String, String> map = new HashMap<String, String>();

    RoundedImageView iv_profile;
    ProgressBar progress, progress_main;
    LinearLayout ll_main, ll_cotainer, llSegments;
    FrameLayout fl_pic;
    private LinearLayout commentsLayout, commentsLayout1;
    EditText etComment;
    boolean showComments;
    ProgressDialog mDialog;
    LinearLayout llVerify;
    String id, userId;
    Bundle bundle;
    private static final String TAG = "365hops";
    ImageView iv_similar;
    ScrollView scrollview;
    LinearLayout ll_comment;
    String type,places_edit;
    MenuItem edit,delete, offline , location,report ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plan_to_vist);


        bundle = getIntent().getExtras();
        if (bundle != null) {
            showComments = bundle.getBoolean("show_comments", false);
        }
        id = bundle.getString("id");
        userId = bundle.getString("user_id");
        type = bundle.getString("type");
        String userId = CommonsUtils.getPrefString(this, "userid");
        places_edit = bundle.getString("places_edit");
       // url += id + "&userid=" + userId;
        urlComments += id;
        scrollview = (ScrollView)findViewById(R.id.scrollView);
        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_timeago = (TextView) findViewById(R.id.tv_timeago);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_howtoreach = (TextView) findViewById(R.id.tv_howtoreach);
        tv_intrest = (TextView) findViewById(R.id.tv_interest);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        tv_todo = (TextView) findViewById(R.id.tv_todo);
        tv_heading = (TextView) findViewById(R.id.tv_heading);
        tv_heading.setVisibility(View.GONE);

        bt_intrest = (Button) findViewById(R.id.bt_interest);
        bt_comment = (Button) findViewById(R.id.bt_comment);

        iv_profile = (RoundedImageView) findViewById(R.id.iv_profile);
        fl_pic = (FrameLayout) findViewById(R.id.fl_pic);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress_main = (ProgressBar) findViewById(R.id.progressBar_main);
        commentsLayout = (LinearLayout) findViewById(R.id.comments_layout);
        commentsLayout1 = (LinearLayout) findViewById(R.id.comments_layout1);
        llVerify = (LinearLayout) findViewById(R.id.ll_verify);
        ll_cotainer = (LinearLayout) findViewById(R.id.ll_container);
        llSegments = (LinearLayout) findViewById(R.id.llSegemnts);

        etComment = (EditText) findViewById(R.id.etComment);
        Button btSend = (Button) findViewById(R.id.btSend);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(PlaceToVisitActivity.this, ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", map.get("user_id"));
                    i.putExtra("recieverid", CommonsUtils.getPrefString(PlaceToVisitActivity.this, "userid"));
                    startActivity(i);

            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pathUrl = "http://www.365hops.com/webservice/controller.php?Servicename=postPlaceToVisit_comments&id=" + map.get("id") + "&userid=" + CommonsUtils.getPrefString(PlaceToVisitActivity.this, "userid") + "&comment=" + etComment.getText().toString().replace(" ", "%20");
                PostDataToServer post = new PostDataToServer(PlaceToVisitActivity.this);
                post.execute(pathUrl);
                Log.d("placeurl", pathUrl);
                // commentsLayout1.removeAllViews();
                PostDataToServer post1 = new PostDataToServer(PlaceToVisitActivity.this);
                post1.execute(urlComments);
                etComment.setText("");
            }
        });
        ll_main = (LinearLayout) findViewById(R.id.ll_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        jParser = new JSONParser();
        gst = new GetSummaryTask();

        gst.execute(new String[]{url+ id + "&userid=" + userId});

        if (showComments) {
            focusOnView();
            PostDataToServer post = new PostDataToServer(PlaceToVisitActivity.this);
            post.execute(urlComments);
        }

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentsLayout.getVisibility() == View.VISIBLE) {
                    commentsLayout.setVisibility(View.GONE);
                } else {
                    PostDataToServer post = new PostDataToServer(PlaceToVisitActivity.this);
                    post.execute(urlComments);
                }
            }
        });


    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof RoundedImageView) {
                ((RoundedImageView) view).setImageBitmap(bitmap);
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
        }else if (bitmap == null && view != null){
            ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
            b.setVisibility(View.GONE);
           view.setBackgroundResource(R.drawable.bike);
        }
    }

    @Override
    public void onPostComplete(String json) {
        if (mDialog != null
                && mDialog.isShowing()) {
            try {

                JSONObject jObj = new JSONObject(json);
                String message = jObj.getString("message");
                Toast.makeText(this, "kk" + message, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {

            }
            GetSummaryTask gst1 = new GetSummaryTask();
            gst1.execute(new String[]{url});
            mDialog.dismiss();
        }
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                int status = jObj.getInt("success");

                if (status == 0) {
                    commentsLayout.setVisibility(View.VISIBLE);
                    String message = jObj.getString("message");
                    Toast.makeText(PlaceToVisitActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Comments.Data data = comments.getData().get(i);
                            View view = View.inflate(this, R.layout.comment_item, null);
                            TextView tvComment = (TextView) view.findViewById(R.id.tv_comment);
                            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                            RoundedImageView profilePic = (RoundedImageView) view.findViewById(R.id.iv_profile_icon1);

                            tvComment.setText(data.getComment());
                            tvDate.setText(data.getDate());
                            commentsLayout1.addView(view);
                            LoadProfileImageAsync task = new LoadProfileImageAsync(profilePic, PlaceToVisitActivity.this);
                            task.execute(data.getUser_img());
                        }
                    }
                    if(showComments) {
                        etComment.requestFocus();
                        //scrollview.scrollTo(0, ll_cotainer.getBottom());
                        //scrollview.fullScroll(View.FOCUS_DOWN);
                        Toast.makeText(getApplicationContext(),showComments+"",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {

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
                    jObj = new JSONObject(json);
                    Log.d("llllllllll", json);

                    String success = jObj.getString("success");

                    map.put("sucess", success);
                    if (success.endsWith("1")) {
                        //   JSONArray data = jObj.getJSONArray("data");
                        JSONArray array = jObj.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);

                        JSONArray data_image = data.getJSONArray("image");
                        map.put("image_size", data_image.length() + "");
                        for (int i = 0; i < data_image.length(); i++) {
                            map.put("image" + i, data_image.getString(i));
                        }
                        JSONArray interest_id = data.getJSONArray("interest_id");

                        if(interest_id.length()>0) {
                            map.put("interest_size", interest_id.length() + "");
                            for (int p = 0; p < interest_id.length(); p++) {
                                JSONObject interest_data = interest_id.getJSONObject(p);


                                    map.put("interest_id" + p, interest_data.getString("id"));
                                    map.put("icon_small" + p, "http://www.365hops.com/searching_files/timthumb.php?src="+interest_data.getString("icon_small")+"&h=50&w=50&zc=1");

                            }
                        }


                        //     Log.d("popopppppppppppp", map.get("image"));
                        map.put("id", data.getString("id"));
                        map.put("name", data.getString("name"));
                        id = data.getString("id");
                        map.put("location", data.getString("location"));
                        map.put("todo", data.getString("todo"));
                        map.put("howtoreach", data.getString("howtoreach"));
                        map.put("creat_date", data.getString("creat_date"));
                        map.put("creat_lat", data.getString("creat_lat"));
                        map.put("creat_lon", data.getString("creat_lon"));
                        map.put("creat_city", data.getString("creat_city"));
                        map.put("user_id", data.getString("user_id"));
                        map.put("user_fullname", data.getString("user_fullname"));
                        map.put("user_img", data.getString("user_img"));
                        map.put("comments", data.getString("comments"));
                        map.put("comment_status", data.getString("comment_status"));
                        map.put("beenthere", data.getString("beenthere"));
                        map.put("beenthere_status", data.getString("beenthere_status"));
                        map.put("timeago", data.getString("timeago"));

                    }
                   /* Gson gson = new Gson();

                    placeDetailStickers = gson.fromJson(json, PlaceStickerDetailsPojo.class);
*/

                    content.close();
                    // handlePostsList();
                    Gson gson = new Gson();
                    placeDetailStickers = gson.fromJson(json, PlaceStickerDetailsPojo.class);
                } catch (Exception ex) {
                    Log.e("Place To Visit Detail", "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("Place To Visit Detail", "Failed to send HTTP POST request due to: " + ex);
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

    private void failedLoadingPosts() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlePostsList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (map.get("sucess").toString().equalsIgnoreCase("1")) {
                    if (CommonsUtils.getPrefString(getApplicationContext(), "userid")
                            .equalsIgnoreCase(userId)) {
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
                    tv_heading.setVisibility(View.VISIBLE);
                    tv_location.setText(map.get("location"));
                    tv_timeago.setText(map.get("timeago"));
                    tv_name.setText(map.get("user_fullname"));
                    tv_city.setText(map.get("creat_city"));
                    tv_howtoreach.setText(map.get("howtoreach"));
                    bt_intrest.setText(map.get("beenthere"));
                    bt_comment.setText(map.get("comments"));
                    tv_todo.setText(map.get("todo"));
                    tv_heading.setText(map.get("name"));

                    Drawable verified, comments;
                    if (Integer.parseInt(map.get("beenthere_status")) > 0) {
                        verified = getResources().getDrawable(R.drawable.beenthere);
                    } else {
                        verified = getResources().getDrawable(R.drawable.beenthere_icon);
                    }
                    bt_intrest.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
                    if (Integer.parseInt(map.get("comment_status")) > 0) {
                        comments = getResources().getDrawable(R.drawable.comment);
                    } else {
                        comments = getResources().getDrawable(R.drawable.comment_icon);
                    }
                    bt_comment.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);
                    //mBroadcasted.setText();
                    if (map.get("beenthere_status").equalsIgnoreCase("0")) {
                        tv_intrest.setText("Been there? Verify");
                    } else {
                        tv_intrest.setText("Verified");
                    }
                    ll_main.setVisibility(View.VISIBLE);
                    progress_main.setVisibility(View.GONE);
                    LoadProfileImageAsync task = new LoadProfileImageAsync(fl_pic, PlaceToVisitActivity.this);
                    task.execute(map.get("image" + 0));

                    task = new LoadProfileImageAsync(iv_profile, PlaceToVisitActivity.this);
                    task.execute(map.get("user_img"));
                    fl_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(PlaceToVisitActivity.this, GalleryActivity.class);
                            String img[] = new String[Integer.parseInt(map.get("image_size"))];
                            for (int j = 0; j < Integer.parseInt(map.get("image_size")); j++) {
                                img[j] = map.get("image" + j);
                            }

                            i.putExtra("images", img);
                            startActivity(i);
                        }
                    });
                    List<String> list = new ArrayList<String>();
                    if(map.get("interest_size")!=null){
                        for(int p=0;p< Integer.parseInt(map.get("interest_size"));p++){
                            //    Log.d("icon",events.getSegment().get(p).getIcon_small());
                            list.add(map.get("icon_small"+p));
                            Log.d("sizeee",map.get("icon_small"+p)+""+id);


                        }
                    }

                    llSegments.removeAllViews();
                   LoadInterestIcons post = new LoadInterestIcons(llSegments, PlaceToVisitActivity.this, false,list);
                    post.execute("");
                    llVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tv_intrest.getText().toString().equalsIgnoreCase("Verified")){
                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=PlacetoVisitNotBeenThere&placeid=" + id + "&userid=" + CommonsUtils.getPrefString(PlaceToVisitActivity.this, "userid");
                                bt_intrest.setText(Math.abs(Integer.parseInt(bt_intrest.getText().toString())-1)+"");

                                Drawable verified = getResources().getDrawable(R.drawable.beenthere_icon);
                                tv_intrest.setText("Been there? Verify");

                                bt_intrest.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
                                PostDataToServer post = new PostDataToServer(PlaceToVisitActivity.this);

                                post.execute(url);
                                //mDialog = ProgressDialog.show(getActivity(), "", "Loading...");

                            }else {
                                String url = "http://www.365hops.com/webservice/controller.php?Servicename=PlacetoVisitBeenThere&placeid=" + id + "&userid=" + CommonsUtils.getPrefString(PlaceToVisitActivity.this, "userid");
                                bt_intrest.setText(Math.abs(Integer.parseInt(bt_intrest.getText().toString())+1)+"");
                               Drawable verified = getResources().getDrawable(R.drawable.beenthere);


                                tv_intrest.setText("Verified");

                                bt_intrest.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
                                PostDataToServer post = new PostDataToServer(PlaceToVisitActivity.this);

                                post.execute(url);
                                // mDialog = ProgressDialog.show(getActivity(), "", "Loading...");
                            }




                        }
                    });
                }else{
                    progress_main.setVisibility(View.GONE);
                }
                GetSummaryTask_similar gst_similar = new GetSummaryTask_similar();

                gst_similar.execute(new String[]{"http://www.365hops.com/webservice/controller.php?Servicename=getSimilarPlaceTovisit&id=" + id + "&limit=2"});


            }
        });
    }
    private final void focusOnView(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollview.scrollTo(0, etComment.getBottom());
            }
        });
    }
    public class GetSummaryTask_similar extends AsyncTask<String, Void, String> {

        private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
        String status;
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
                    status = jObj.getString("success");
                    if (jObj.getString("success").equalsIgnoreCase("1")) {
                        JSONArray data = jObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            ArrayList<String> info = new ArrayList<String>();
                            info.add(obj.getString("id"));
                            info.add(obj.getString("name"));
                            info.add(obj.getString("location"));
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
            if(status.equalsIgnoreCase("1")) {
                for (int j = 0; j < similar.size(); j++) {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.similar_layout, null);

                    tv_similar_cost = (TextView) addView.findViewById(R.id.tv_cost);
                   tv_similar_name = (TextView) addView.findViewById(R.id.tv_name);
                    iv_similar = (ImageView) addView.findViewById(R.id.iv_similar);


                    ids.add(similar.get(j).get(0));
                    tv_similar_cost.setText("Approx Cost: " + similar.get(j).get(2) + "");
                    tv_similar_name.setText(similar.get(j).get(1) + "");

                    ll_cotainer.addView(addView);
                    if (bitmap.size() > j) {
                        Drawable d = new BitmapDrawable(getResources(), bitmap.get(j));
                        iv_similar.setBackground(d);
                    }

                }
                similar.clear();

                if (ll_cotainer.getChildAt(0)!= null) {
                    ll_cotainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ll_cotainer.removeAllViews();
                            scrollview.scrollTo(0,0);
                            gst = new GetSummaryTask();
                            gst.execute(new String[]{url + ids.get(0) + "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid")});
                            //Toast.makeText(EventClicked.this,""+ll_cotainer.getChildCount(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (ll_cotainer.getChildAt(1) != null) {
                    ll_cotainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ll_cotainer.removeAllViews();
                            scrollview.scrollTo(0,0);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (places_edit != null && places_edit.equalsIgnoreCase("1")) {

                startActivity(new Intent(PlaceToVisitActivity.this, ProfileActivity.class).putExtra("my", true).putExtra("userid", userId));

            }
            finish();
        } else if (item.getItemId() == R.id.action_wrong_location) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=wronLocationTag&id=" + bundle.getString("id") +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=placetoVisit";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_report) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=reportAbuse&id=" + bundle.getString("id") +
                    "&userid=" + CommonsUtils.getPrefString(getApplicationContext(), "userid") + "&type=placetoVisit";
            GetSummaryTask_wrongloc gst = new GetSummaryTask_wrongloc();
            gst.execute(url);
        } else if (item.getItemId() == R.id.action_delete) {
            String url = "http://www.365hops.com/webservice/controller.php?Servicename=deletePlace&id=";
            url += this.id + "&userid=" + userId + "&type=" + type;
            Log.d(TAG, "Delete URL: " + url);
            GetSummaryTask_settings gst= new GetSummaryTask_settings();
            gst.execute(url);
            Intent goToMainActivity = new Intent(getApplicationContext(),PagerActivity.class);
            goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Will clear out your activity history stack till now
            //goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goToMainActivity);

            finish();
        } else if (item.getItemId() == R.id.action_offline) {

        } else if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(PlaceToVisitActivity.this, AddPlaceActivityOne.class);
            intent.putExtra("edit", true);
            intent.putExtra("place", placeDetailStickers);
            String img[] = new String[Integer.parseInt(map.get("image_size"))];
            for (int j = 0; j < Integer.parseInt(map.get("image_size")); j++) {
                img[j] = map.get("image" + j);
            }
            intent.putExtra("images", img);
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
          //  if(map_settings.get("success_set").equalsIgnoreCase("1"))
                Toast.makeText(getApplicationContext(),"Thanks for reporting this",Toast.LENGTH_SHORT).show();
            //else
              //  Toast.makeText(getApplicationContext(),map_settings.get("message_set"),Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(PlaceToVisitActivity.this, "", "Loading...");
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
                    Log.e("", "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("", "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            //if (mDialog != null
              //      && mDialog.isShowing()) {
               // mDialog.dismiss();
            //}
            Toast.makeText(getApplicationContext(), map_settings.get("message_set"), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            //mDialog = ProgressDialog.show(PlaceToVisitActivity.this, "", "Loading...");
            //mDialog.setCancelable(true);

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (places_edit != null && places_edit.equalsIgnoreCase("1")) {

            startActivity(new Intent(PlaceToVisitActivity.this, ProfileActivity.class).putExtra("my", true).putExtra("userid", userId));
        }
        finish();
    }
}
