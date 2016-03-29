package com.appzollo.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.EventClicked;
import com.appzollo.GPSTracker;
import com.appzollo.ProfileActivity;
import com.appzollo.R;
import com.appzollo.classes.CircularImageView;
import com.appzollo.classes.EventSticker;
import com.appzollo.classes.FontTypeFace;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.classes.ScrollViewExt;
import com.appzollo.classes.ScrollViewListener;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.profilefragments.PlanStickerClick;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventsFragment extends Fragment implements OnLoadCompleteListener, OnPostCompleteListener,
        ScrollViewListener {

    private JSONParser jParser;
    GetSummaryTask gst;
    public FragmentManager fragmentManager;
    private static final String TAG = "365hopes";
    private final String url = "http://www.365hops.com/webservice/controller.php?Servicename=EventSearch&default=1&limit=5&default=0";
    private final String url1 = "http://www.365hops.com/webservice/controller.php?Servicename=getinterestshownbyUser&&default=0&userid=";

    GPSTracker gps;
    private List<EventSticker> eventsList = new ArrayList<EventSticker>();
    private EventSticker eventStickers, planStickers;
    private LinearLayout eventLayout;
    private boolean isEvent, isPlan, isShownIntertest;
    static public CheckBox cbPlan, cbEvents;
    Bitmap bitmap;
    String photo;
    ProgressBar progress_main;
    private ProgressDialog mDialog;
    private TextView noEvents;
    View view;
    boolean checkInterest = false;
    ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
    TextView tv_cost;
    public ScrollViewExt scrollview;
    private boolean mClear;
    private boolean isScrolled;
    private boolean isChekedChange;
    FontTypeFace tf;
    EventSticker tmpEvents, tmpPlans;
    String lat = "", lng = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jParser = new JSONParser();
        //  tf = new FontTypeFace(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeAllViews();

        }
        try {
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_events, container, false);
                scrollview = (ScrollViewExt) view.findViewById(R.id.scrollview1);
                cbPlan = (CheckBox) view.findViewById(R.id.cb_plan);
                cbEvents = (CheckBox) view.findViewById(R.id.cb_events);
                progress_main = (ProgressBar) view.findViewById(R.id.progressBar_main);
                noEvents = (TextView) view.findViewById(R.id.tv_no_events);
                scrollview.setScrollViewListener(this);
            }
        } catch (InflateException ex) {

        }

        checkInterest = this.getArguments().getBoolean("interest");
        double latitude = 0, longitude = 0;
        gps = new GPSTracker(getActivity());
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            //Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        eventLayout = (LinearLayout) view.findViewById(R.id.event_layout);
        //   Toast.makeText(getActivity(), this.getArguments().getString("location"), Toast.LENGTH_SHORT).show();
        String tmpUrl = url;

        if (this.getArguments().getBoolean("latlon")) {
            lat = this.getArguments().getString("lat");
            lng = this.getArguments().getString("lng");
        } else {
            lat = latitude + "";
            lng = longitude + "";
        }

        tmpUrl += "&latitude=" + lat + "&longitude=" + lng + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                + "&segment=" + this.getArguments().getString("segid") + "&location_tags=" + this.getArguments().getString("location");
        //  eventStickers =null;
        // planStickers = null;
        //  eventLayout.removeAllViews();
        if (this.getArguments().getBoolean("interest")) {
            eventStickers = null;
            planStickers = null;
            eventLayout.removeAllViews();
            // url = "http://www.365hops.com/webservice/controller.php?%20Servicename=getinterestshownbyUser&limit=10"+"&userid="+CommonsUtils.getPrefString(getActivity(),"userid");
        }


        updateViews(tmpUrl);
        //setHasOptionsMenu(true);

        cbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                    planStickers = null;

                isChekedChange = true;
                String tmpUrl = url;
                tmpUrl += "&latitude=" + getArguments().getString("lat") + "&longitude=" + getArguments().getString("lng") + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                        + "&segment=" + getArguments().getString("segid") + "&location_tags=" + getArguments().getString("location");
                updateViews(tmpUrl);
            }
        });

        cbEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                    eventStickers = null;

                isChekedChange = true;
                String tmpUrl = url;
                tmpUrl += "&latitude=" + getArguments().getString("lat") + "&longitude=" + getArguments().getString("lng") + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                        + "&segment=" + getArguments().getString("segid") + "&location_tags=" + getArguments().getString("location");
                updateViews(tmpUrl);
            }
        });

        return view;

    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                b.setVisibility(View.GONE);
                if(view.getId()==R.id.fl_planstickerpic){
                    LinearLayout llParent = (LinearLayout)view.getParent();
                    llParent.setBackgroundResource(R.drawable.middle_pannel_bg);
                    llParent.setPadding(0,0,0,0);
                    ImageView iv = (ImageView)view.findViewById(R.id.iv_plan);
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
                }else{
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
            if (bitmap != null) {
                // bitmap.recycle();
                bitmap = null;
            }
        } else {
            if (bitmap == null && view != null) {
                if (view instanceof FrameLayout) {
                    ProgressBar b = (ProgressBar) view.findViewById(R.id.progressBar);
                    b.setVisibility(View.GONE);
                    if(view.getId()==R.id.fl_planstickerpic){
                        LinearLayout llParent = (LinearLayout)view.getParent();
                        llParent.setBackgroundResource(R.drawable.small_box);
                        llParent.setPadding(0,0,0,50);
                        ImageView iv = (ImageView)view.findViewById(R.id.iv_plan);
                        iv.setVisibility(View.GONE);
                        LinearLayout llTop =  (LinearLayout)view.findViewById(R.id.llStickerTop);
                        llTop.setVisibility(View.GONE);
                        LinearLayout llBottom =  (LinearLayout)view.findViewById(R.id.llSticker);
                        llBottom.setVisibility(View.VISIBLE);
                    }
                   // view.setBackgroundResource(R.drawable.bike);
                }
            }
        }
    }

    @Override
    public void onPostComplete(String json) {
        updateViews(url);
        if (mDialog != null
                && mDialog.isShowing()) {
            try {
                JSONObject jObj = new JSONObject(json);
                String message = jObj.getString("message");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
            mDialog.dismiss();
        }
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            if (!isScrolled) {
                isScrolled = true;
                StringBuilder eventIds = new StringBuilder();
                StringBuilder planIds = new StringBuilder();
                if (eventStickers != null) {
                    int size = eventStickers.getData().size();
                    for (int i = 0; i < size - 1; i++) {
                        eventIds.append(eventStickers.getData().get(i).getId()).append(",");
                    }
                    if (size > 0)
                        eventIds.append(eventStickers.getData().get(size - 1).getId());
                }
                if (planStickers != null) {
                    int size = planStickers.getData().size();
                    for (int i = 0; i < size - 1; i++) {
                        planIds.append(planStickers.getData().get(i).getId()).append(",");
                    }
                    if (size > 0)
                        planIds.append(planStickers.getData().get(size - 1).getId());
                }
                String tmpUrl = null;
                if (getArguments().getBoolean("interest")) {
                    tmpUrl = url1;
                } else {
                    tmpUrl = url;
                }
                tmpUrl += "&eventids=" + eventIds.toString();
                tmpUrl += "&planids=" + planIds.toString();
                tmpUrl += "&latitude=" + this.getArguments().getString("lat") + "&longitude=" + this.getArguments().getString("lng") + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                        + "&segment=" + this.getArguments().getString("segid") + "&location_tags=" + this.getArguments().getString("location");

                View loadMore = View.inflate(getActivity(), R.layout.load_more, null);
                eventLayout.addView(loadMore);
                updateViews(tmpUrl);
            }
        }
    }

    public class GetSummaryTask extends AsyncTask<String, Void, String> {

        private int asy = 0;

        @Override
        protected String doInBackground(String... urls) {
            for (int i = 0; i < urls.length; i++) {
                try {
                    //Create an HTTP client
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(urls[i]);
                    Log.d(TAG, "URL: " + urls[i]);
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
                        String json = jParser.getJSONFromUrl(urls[i]);
                        Gson gson = new Gson();

                        if (isEvent && isPlan) {
                            if (i == 1) {
                                if (isScrolled) {
                                    if (eventStickers != null) {
                                        tmpEvents = gson.fromJson(json, EventSticker.class);
                                        for (int k = 0; k < tmpEvents.getData().size(); k++) {
                                            eventStickers.getData().add(tmpEvents.getData().get(k));
                                        }
                                    } else {
                                        eventStickers = gson.fromJson(json, EventSticker.class);
                                    }
                                } else {
                                    eventStickers = gson.fromJson(json, EventSticker.class);
                                }
                            } else {
                                if (isScrolled) {
                                    if (planStickers != null) {
                                        tmpPlans = gson.fromJson(json, EventSticker.class);
                                        for (int k = 0; k < tmpPlans.getData().size(); k++) {
                                            planStickers.getData().add(tmpPlans.getData().get(k));
                                        }
                                    } else {
                                        planStickers = gson.fromJson(json, EventSticker.class);
                                    }
                                } else {
                                    planStickers = gson.fromJson(json, EventSticker.class);
                                }
                            }
                        } else if (isEvent) {
                            if (isScrolled) {
                                if (eventStickers != null) {
                                    tmpEvents = gson.fromJson(json, EventSticker.class);
                                    for (int k = 0; k < tmpEvents.getData().size(); k++) {
                                        eventStickers.getData().add(tmpEvents.getData().get(k));
                                    }
                                } else {
                                    eventStickers = gson.fromJson(json, EventSticker.class);
                                }
                            } else {
                                eventStickers = gson.fromJson(json, EventSticker.class);
                            }
                        } else if (isPlan) {
                            if (isScrolled) {
                                if (planStickers != null) {
                                    tmpPlans = gson.fromJson(json, EventSticker.class);
                                    for (int k = 0; k < tmpPlans.getData().size(); k++) {
                                        planStickers.getData().add(tmpPlans.getData().get(k));
                                    }
                                } else {
                                    planStickers = gson.fromJson(json, EventSticker.class);
                                }
                            } else {
                                planStickers = gson.fromJson(json, EventSticker.class);
                            }
                        }

                        content.close();
                        //handlePostsList();
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
            }
            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            progress_main.setVisibility(View.GONE);
            if (!isScrolled) {
                scrollview.scrollTo(0, 0);
            }
            handlePostsList();


        }

        @Override
        protected void onPreExecute() {
            if (!isScrolled) {
               progress_main.setVisibility(View.VISIBLE);

            }
            noEvents.setVisibility(View.GONE);
        }
    }


    private void handlePostsList() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (isScrolled) {
                        int size = eventLayout.getChildCount();
                        eventLayout.removeViewAt(size - 1);
                        isScrolled = false;
                        if (isPlan && tmpPlans != null && !checkInterest) {
                            for (int i = 0; i < tmpPlans.getData().size(); i++) {
                                View view = getPlanView(tmpPlans.getData().get(i));
                                eventLayout.addView(view);
                            }
                        }
                        if (isEvent && tmpEvents != null && !checkInterest) {
                            for (int i = 0; i < tmpEvents.getData().size(); i++) {
                                View view = getView(tmpEvents.getData().get(i));
                                eventLayout.addView(view);
                            }
                        }
                        if (isPlan && tmpPlans != null && checkInterest) {
                            for (int i = 0; i < tmpPlans.getData().size(); i++) {
                                if (tmpPlans.getData().get(i).getType().equalsIgnoreCase("Plan")) {
                                    View view = getPlanView(tmpPlans.getData().get(i));
                                    eventLayout.addView(view);
                                } else {
                                    View view = getView(tmpPlans.getData().get(i));
                                    eventLayout.addView(view);
                                }

                            }
                        } else if (isEvent && tmpEvents != null && checkInterest) {
                            for (int i = 0; i < tmpEvents.getData().size(); i++) {
                                if (tmpEvents.getData().get(i).getType().equalsIgnoreCase("Event")) {
                                    View view = getView(tmpEvents.getData().get(i));
                                    eventLayout.addView(view);
                                } else {
                                    View view = getView(tmpEvents.getData().get(i));
                                    eventLayout.addView(view);
                                }

                            }
                        }
                    } else {
                        eventLayout.removeAllViews();

                        if (isPlan && planStickers != null && !checkInterest) {
                            if (planStickers.getSuccess() == 1) {
                                noEvents.setVisibility(View.GONE);
                                scrollview.setVisibility(View.VISIBLE);
                                for (int i = 0; i < planStickers.getData().size(); i++) {
                                    View view = getPlanView(planStickers.getData().get(i));
                                    eventLayout.addView(view);
                                }
                            } else {

                                noEvents.setVisibility(View.VISIBLE);

                                scrollview.setVisibility(View.GONE);
                            }
                        } else {
                            if(planStickers == null && eventStickers == null) {
                                noEvents.setVisibility(View.VISIBLE);

                                scrollview.setVisibility(View.GONE);
                            }
                        }

                        if (isEvent && eventStickers != null && !checkInterest) {
                            if (eventStickers.getSuccess() == 1) {
                                noEvents.setVisibility(View.GONE);
                                scrollview.setVisibility(View.VISIBLE);
                                for (int i = 0; i < eventStickers.getData().size(); i++) {
                                    View view = getView(eventStickers.getData().get(i));
                                    eventLayout.addView(view);
                                }
                            } else {
                                noEvents.setVisibility(View.VISIBLE);
                                scrollview.setVisibility(View.GONE);
                            }
                        } else {
                            if(planStickers == null && eventStickers == null) {
                                noEvents.setVisibility(View.VISIBLE);
                                scrollview.setVisibility(View.GONE);
                            }
                        }
                        if (isPlan && planStickers != null && checkInterest) {
                            if (planStickers.getSuccess() == 1) {
                                for (int i = 0; i < planStickers.getData().size(); i++) {
                                    if (planStickers.getData().get(i).getType().equalsIgnoreCase("Plan")) {
                                        View view = getPlanView(planStickers.getData().get(i));
                                        eventLayout.addView(view);
                                    } else {
                                        View view = getView(planStickers.getData().get(i));
                                        eventLayout.addView(view);
                                    }
                                }
                            }else{
                                noEvents.setVisibility(View.VISIBLE);
                                scrollview.setVisibility(View.GONE);
                            }
                        } else if (isEvent && eventStickers != null && checkInterest) {
                            if(eventStickers.getSuccess() == 1){
                                noEvents.setVisibility(View.GONE);
                                scrollview.setVisibility(View.VISIBLE);
                               for (int i = 0; i < eventStickers.getData().size(); i++) {
                                if (eventStickers.getData().get(i).getType().equalsIgnoreCase("Event")) {
                                    View view = getView(eventStickers.getData().get(i));
                                    eventLayout.addView(view);
                                } else {
                                    View view = getView(eventStickers.getData().get(i));
                                    eventLayout.addView(view);
                                }
                            }
                            }else{
                                noEvents.setVisibility(View.VISIBLE);
                                scrollview.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }
    }

    private void handleImage(final String photo) {
        getActivity().runOnUiThread(new Runnable() {
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Failed to load Posts. Check Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View getView(final EventSticker.Events event) {

        View view = View.inflate(getActivity(), R.layout.event_sticker, null);
        ImageView mBus = (ImageView) view.findViewById(R.id.iv_bus);
        ImageView mFood = (ImageView) view.findViewById(R.id.iv_food);
        ImageView mBed = (ImageView) view.findViewById(R.id.iv_bed);
        FrameLayout fl_eventpic = (FrameLayout) view.findViewById(R.id.fl_eventpic);

        TextView mHeading = (TextView) view.findViewById(R.id.tv_heading);
        // mHeading.setTypeface(tf.typeface);
        TextView mPrice = (TextView) view.findViewById(R.id.tv_amt_price);
        TextView mDays = (TextView) view.findViewById(R.id.tv_days);
        TextView mDate = (TextView) view.findViewById(R.id.tv_date);
        TextView mName = (TextView) view.findViewById(R.id.tv_name);
        TextView mLastActive = (TextView) view.findViewById(R.id.tv_last_active);
        TextView mBroadcasted = (TextView) view.findViewById(R.id.tv_cast_place);
        TextView mCastPlace = (TextView) view.findViewById(R.id.tv_cast_place);
        TextView mPlace = (TextView) view.findViewById(R.id.tv_place);
        TextView mPlaceTwo = (TextView) view.findViewById(R.id.tv_place_two);

        LinearLayout llComments = (LinearLayout) view.findViewById(R.id.llComment);
        LinearLayout llInterest = (LinearLayout) view.findViewById(R.id.ll_intrest);

        final Button mInterests = (Button) view.findViewById(R.id.bt_interest);
        Button mComments = (Button) view.findViewById(R.id.bt_comment);
        mComments.setClickable(false);
        mInterests.setClickable(false);

        final TextView tvInterest = (TextView) view.findViewById(R.id.tv_interest);
        tvInterest.setClickable(false);

        RoundedImageView iv = (RoundedImageView) view.findViewById(R.id.iv_profile_icon1);


        if (event != null) {
            noEvents.setVisibility(View.GONE);
            if (event.getIsfood().equals("1")) {
                mFood.setBackgroundResource(R.drawable.dinner);
                //mFood.setVisibility(View.INVISIBLE);
            }
            if (event.getIstravel().equals("1")) {
                mBus.setBackgroundResource(R.drawable.bus);
               // mBus.setVisibility(View.INVISIBLE);
            }
            if (event.getIsaccommodation().equals("1")) {
                mBed.setBackgroundResource(R.drawable.sleep);
               // mBed.setVisibility(View.INVISIBLE);
            }
            mHeading.setText(event.getTitle());
            mPrice.setText("Rs "+event.getCost());
            mDays.setText(event.getDuration());
            mLastActive.setText(event.getTimeago());
            mCastPlace.setText(event.getCityCreat());

            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1, d2;
            String formattedTime1 = null;
            String formattedTime2 = null;
            try {
                d1 = output.parse(event.getDate_from() + " 00:00:00");
                formattedTime1 = output.format(d1);

                d2 = output.parse(event.getDate_to() + " 00:00:00");
                formattedTime2 = output.format(d2);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            if (event.getCustomeDate() == null)
                mDate.setText("" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime1)) +
                        " to " + "" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime2)));
            else
                mDate.setText(event.getCustomeDate());
            mName.setText(event.getUser_fullname());
            String place[] = event.getWhere_area().split(",");
            mPlace.setText(place[0]);
            if (place.length > 1)
                mPlaceTwo.setText(place[1]);
            view.setId(Integer.parseInt(event.getUserId()));
            if (event.getIntrestStatus().equals("0")) {
                tvInterest.setText("Show Interest");
            } else {
                tvInterest.setText("Interested");
            }
            Drawable interest, comments;
            mInterests.setText(event.getIntrests());
            if (Integer.parseInt(event.getIntrestStatus()) > 0) {
                interest = getResources().getDrawable(R.drawable.interest_icon);
            } else {
                interest = getResources().getDrawable(R.drawable.showinterest);
            }
            mInterests.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);
            if (Integer.parseInt(event.getCommentsStatus()) > 0) {
                comments = getResources().getDrawable(R.drawable.comment);
            } else {
                comments = getResources().getDrawable(R.drawable.comment_icon);
            }
            mComments.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);

            mComments.setText(event.getComments());
            if (event.getCityCreat() != null) {
                mBroadcasted.setText(event.getCityCreat());

            } else {
                mBroadcasted.setText("");

            }

            LoadProfileImageAsync task = new LoadProfileImageAsync(iv, this);
            task.execute(event.getUser_Img().replace("32", "50"));
            task = new LoadProfileImageAsync(fl_eventpic, this);
            task.execute(event.getImage());

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", event.getUserId());
                    i.putExtra("recieverid", CommonsUtils.getPrefString(getActivity(), "userid"));
                    startActivity(i);

                }
            });

            fl_eventpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEvent) {
                        Intent i = new Intent(getActivity(), EventClicked.class);
                        i.putExtra("id", event.getId());
                        i.putExtra("userid", event.getUserId());
                        startActivity(i);
                    }
                }
            });
            llComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), EventClicked.class);
                    i.putExtra("id", event.getId());
                    i.putExtra("userid", event.getUserId());
                    i.putExtra("show_comments", true);
                    startActivity(i);
                }
            });

            final PostDataToServer post = new PostDataToServer(this);
            final PostDataToServer post1 = new PostDataToServer(this);
            llInterest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (event.getIntrestStatus().equals("0")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowintrest&type=Event&id=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                        PostDataToServer post = new PostDataToServer(null);
                        post.execute(url);
                        showDialog(event.getId());
                        tvInterest.setText("Interested");
                        mInterests.setText("" + (Integer.parseInt(mInterests.getText().toString()) + 1));

                        Drawable interest1 = getResources().getDrawable(R.drawable.interest_icon);
                        mInterests.setCompoundDrawablesWithIntrinsicBounds(interest1, null, null, null);


                        event.setIntrestStatus("1");
                    } else {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowunintrest&type=Event&id=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                        PostDataToServer post1 = new PostDataToServer(null);
                        post1.execute(url);
                        tvInterest.setText("Show Interest");
                        mInterests.setText("" + (Integer.parseInt(mInterests.getText().toString()) - 1));
                        Drawable interest = getResources().getDrawable(R.drawable.showinterest);
                        mInterests.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                        event.setIntrestStatus("0");
                    }
                }
            });


        } else {
            noEvents.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"visible 9",Toast.LENGTH_SHORT).show();
            progress_main.setVisibility(View.GONE);
        }
        return view;
    }

    public View getPlanView(final EventSticker.Events event) {

        View view = View.inflate(getActivity(), R.layout.plan_sticker, null);

        TextView mHeading = (TextView) view.findViewById(R.id.tv_text);
        TextView mName = (TextView) view.findViewById(R.id.tv_name_plansticker);
        TextView mLastActive = (TextView) view.findViewById(R.id.tv_last_active_plansticker);
        TextView mCastPlace = (TextView) view.findViewById(R.id.tv_cast_place_plansticker);
        TextView mPlace = (TextView) view.findViewById(R.id.tv_place_plansticker);
        TextView mMonth = (TextView) view.findViewById(R.id.tv_month_plansticker);
        TextView mYear = (TextView) view.findViewById(R.id.tv_year_plansticker);
        CircularImageView iv = (CircularImageView) view.findViewById(R.id.iv_profile_icon1);
        TextView mPlaceTwo = (TextView) view.findViewById(R.id.tv_place_two1);

        LinearLayout llComments = (LinearLayout) view.findViewById(R.id.llComment);
        LinearLayout llInerests = (LinearLayout) view.findViewById(R.id.llInterest);
        final Button mInterests = (Button) view.findViewById(R.id.bt_interest_plansticker);
        Button mComments = (Button) view.findViewById(R.id.bt_comment_plansticker);

        mComments.setClickable(false);
        mInterests.setClickable(false);
        final TextView tvInterest = (TextView) view.findViewById(R.id.tv_interest_plansticker);
        tvInterest.setClickable(false);
        final FrameLayout fl_planstickerpic = (FrameLayout) view.findViewById(R.id.fl_planstickerpic);

        if (event != null) {
            noEvents.setVisibility(View.GONE);
            mHeading.setText(event.getTitle());
            //mDays.setText(event.getDate());
            mName.setText(event.getUser_fullname());
            mLastActive.setText(event.getTimeago());
            //mBroadcasted.setText();
            if(event.getCityCreat()!=null){
                String city[] = event.getCityCreat().split(",");
                if(city.length>0)
                    mCastPlace.setText(city[0]);
            }


            String place[] = event.getWhere_area().split(",");
            mPlace.setText(place[0]);
            if (place.length > 1)
                mPlaceTwo.setText(place[1]);

            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1, d2;
            String formattedTime1 = null;
            String formattedTime2 = null;
            try {
                d1 = output.parse(event.getDate_from() + " 00:00:00");
                formattedTime1 = output.format(d1);

                d2 = output.parse(event.getDate_to() + " 00:00:00");
                formattedTime2 = output.format(d2);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (event.getCustomeDate() == null)
                mMonth.setText("" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime1)) +
                        "-\n" + "" + (new SimpleDateFormat("dd MMM, yy")).format(Timestamp.valueOf(formattedTime2)));
            else
                mMonth.setText(event.getCustomeDate());

            // mMonth.setText("" + (new SimpleDateFormat("MMMM")).format(Timestamp.valueOf(formattedTime)));
            //   mYear.setText(""+(new SimpleDateFormat("yyyy")).format(Timestamp.valueOf(formattedTime)));
            Drawable interest, comments;
            mInterests.setText(event.getIntrests());
            if (Integer.parseInt(event.getIntrestStatus()) > 0) {
                interest = getResources().getDrawable(R.drawable.interest_icon);
            } else {
                interest = getResources().getDrawable(R.drawable.showinterest);
            }
            mInterests.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);
            if (Integer.parseInt(event.getCommentsStatus()) > 0) {
                comments = getResources().getDrawable(R.drawable.comment);
            } else {
                comments = getResources().getDrawable(R.drawable.comment_icon);
            }
            mComments.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);


            mInterests.setText(event.getIntrests());
            mComments.setText(event.getComments());
            if (event.getIntrestStatus().equals("0")) {
                tvInterest.setText("Show Interest");
            } else {
                tvInterest.setText("Interested");
            }
            LoadProfileImageAsync task = new LoadProfileImageAsync(iv, this);
            task.execute(event.getUser_Img().replace("32", "50"));
            task = new LoadProfileImageAsync(fl_planstickerpic, this);
            task.execute(event.getImage());

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", event.getUserId());
                    i.putExtra("recieverid", event.getId());
                    startActivity(i);

                }
            });


            fl_planstickerpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isPlan) {
                        Intent intent = new Intent(getActivity(), PlanStickerClick.class);
                        Toast.makeText(getActivity(),event.getId()+"",Toast.LENGTH_SHORT).show();
                        intent.putExtra("id", event.getId());
                        intent.putExtra("userid", event.getUserId());
                        startActivity(intent);
                    }
                }
            });
            llComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), PlanStickerClick.class);
                    i.putExtra("id", event.getId());
                    i.putExtra("userid", event.getUserId());
                    i.putExtra("show_comments", true);
                    startActivity(i);

                }
            });
            final PostDataToServer post = new PostDataToServer(this);
            final PostDataToServer post1 = new PostDataToServer(this);
            llInerests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (event.getIntrestStatus().equals("0")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowintrest&type=Plan&id=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");

                        PostDataToServer post = new PostDataToServer(null);
                        post.execute(url);
                        // showDialog(event.getId());
                        tvInterest.setText("Interested");
                        mInterests.setText("" + (Integer.parseInt(mInterests.getText().toString()) + 1));
                        Drawable interest = getResources().getDrawable(R.drawable.interest_icon);
                        mInterests.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                        event.setIntrestStatus("1");
                    } else {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=eventshowunintrest&type=Plan&id=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                        PostDataToServer post1 = new PostDataToServer(null);
                        post1.execute(url);
                        tvInterest.setText("Show Interest");
                        mInterests.setText("" + (Integer.parseInt(mInterests.getText().toString()) - 1));
                        Drawable interest = getResources().getDrawable(R.drawable.showinterest);
                        mInterests.setCompoundDrawablesWithIntrinsicBounds(interest, null, null, null);

                        event.setIntrestStatus("0");
                    }
                }
            });


        } else {
            noEvents.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"visible 10",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.events_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void updateViews(String url) {
        isEvent = cbEvents.isChecked();
        isPlan = cbPlan.isChecked();

        mClear = getArguments().getBoolean("clear");
        if (mClear) {
            eventLayout.removeAllViews();
            progress_main.setVisibility(View.VISIBLE);
            eventStickers = null;
            planStickers = null;
            getArguments().putBoolean("clear", false);
        }

        gst = new GetSummaryTask();
        if (this.getArguments().getBoolean("interest")) {

            if (isEvent && isPlan) {
                gst.execute(url1 + CommonsUtils.getPrefString(getActivity(), "userid")
                );

            } else if (isEvent) {
                Log.d("interest event", url1 + CommonsUtils.getPrefString(getActivity(), "userid"));
                gst.execute(url1 + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Event");

            } else if (isPlan) {
                Log.d("interest", url1 + CommonsUtils.getPrefString(getActivity(), "userid"));
                gst.execute(url1 + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Plan");

            } else {
                eventLayout.removeAllViews();
            }
        } else {
            Log.d("url", url);
            if (isEvent && isPlan) {
                if (isScrolled || mClear
                        || isChekedChange
                        || (eventStickers == null && planStickers == null)) {
                    gst.execute(new String[]{url + "&type=Plan",
                            url + "&type=Event"});

                //    Toast.makeText(getActivity(),"Both",Toast.LENGTH_SHORT).show();
                }
            } else if (isEvent) {
                if (isScrolled || mClear
                        || isChekedChange
                        || eventStickers == null) {
                    gst.execute(url + "&type=Event");
              //      Toast.makeText(getActivity(),"is event",Toast.LENGTH_SHORT).show();
                }
            } else if (isPlan) {
                if (isScrolled || mClear
                        || isChekedChange
                        || planStickers == null) {
                    gst.execute(url + "&type=Plan");
                 //   Toast.makeText(getActivity(),"isplan",Toast.LENGTH_SHORT).show();
                }
            } else {
                eventLayout.removeAllViews();
                //Toast.makeText(getActivity(),"none",Toast.LENGTH_SHORT).show();
            }
            isChekedChange = false;
        }
    }

    void showDialog(final String id) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(dividerId);
        divider.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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
                        "id=" + id + "&mobile=" + (mcode + "" + mobile) + "&email=" + CommonsUtils.getPrefString(getActivity(), "email")
                        + "&peoples=" + pcount + "&check=" + check + "&name=" + CommonsUtils.getPrefString(getActivity(), "user");
                PostDataToServer post = new PostDataToServer(null);
                if (mobile.length() > 0) {
                    post.execute(url.replace(" ", "%20"));
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }


}