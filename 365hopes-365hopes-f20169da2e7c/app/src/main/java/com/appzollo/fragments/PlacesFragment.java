package com.appzollo.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.EventClicked;
import com.appzollo.GPSTracker;
import com.appzollo.PlaceToStayClicked;
import com.appzollo.PlaceToVisitActivity;
import com.appzollo.ProfileActivity;
import com.appzollo.R;
import com.appzollo.classes.EventSticker;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.PlacestickerDetails;
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


public class PlacesFragment extends Fragment implements OnLoadCompleteListener, OnPostCompleteListener,
        ScrollViewListener {


    public FragmentManager fragmentManager;
    private static final String TAG = "365hopes";

    private final String url = "http://www.365hops.com/webservice/controller.php?Servicename=SearchPlaces&limit=5&&default=0&location_tags=";
    private final String url1 = "http://www.365hops.com/webservice/controller.php?Servicename=getverifiedplacesbyUser&limit=10";

    private boolean isStay, isVisit;
    private CheckBox cbStay, cbVisit;

    private JSONParser jParser;
    GetSummaryTask gst;
    private LinearLayout placeLayout;
    private PlacestickerDetails stayStickers, visitStickers;
    GPSTracker gps;
    ProgressBar progress_main;
    TextView tvNoPlaces;
    ProgressDialog mDialog;
    Drawable verified, comments;
    private boolean mClear;
    private ScrollViewExt scrollViewExt;
    private boolean isScrolled;
    private boolean isCheckedChange;
    PlacestickerDetails tmpStay, tmpPlace;
    String lat,lng;

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PlacesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        jParser = new JSONParser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        cbVisit = (CheckBox) view.findViewById(R.id.cb_placevisit);
        cbStay = (CheckBox) view.findViewById(R.id.cb_placestay);

        placeLayout = (LinearLayout) view.findViewById(R.id.place_layout);
        progress_main = (ProgressBar) view.findViewById(R.id.progressBar_main);
        tvNoPlaces = (TextView) view.findViewById(R.id.tv_no_places);
        scrollViewExt = (ScrollViewExt) view.findViewById(R.id.scrollView);
        scrollViewExt.setScrollViewListener(this);

        gps = new GPSTracker(getActivity());

        double latitude = 0, longitude = 0;
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
        if (this.getArguments().getBoolean("latlon")) {
            lat = this.getArguments().getString("lat");
            lng = this.getArguments().getString("lng");
        } else {
            lat = latitude + "";
            lng = longitude + "";
        }
        String tmpUrl = url;
        tmpUrl += "&location_tags=" + this.getArguments().getString("location") + "&latitude="+lat+"&longitude=" +lng+ "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                + "&segment=" + this.getArguments().getString("segid");


        updateViews(tmpUrl);

        cbStay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String tmpUrl = url;
                tmpUrl += "&location_tags=" + getArguments().getString("location") + "&latitude="+lat+"&longitude="+lng + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                        + "&segment=" + getArguments().getString("segid");
                updateViews(tmpUrl);
                isCheckedChange = true;
            }
        });

        cbVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String tmpUrl = url;
                tmpUrl += "&location_tags=" + getArguments().getString("location") + "&latitude="+lat+"&longitude="+lng + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid")
                        + "&segment=" + getArguments().getString("segid");
                updateViews(tmpUrl);
                isCheckedChange = true;
            }
        });


        return view;

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
            bitmap = null;
        }
    }

    @Override
    public void onPostComplete(String json) {
        //updateViews();
        /*if (mDialog != null
                && mDialog.isShowing()) {*/
        try {
            JSONObject jObj = new JSONObject(json);
            String message = jObj.getString("message");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
          /*  mDialog.dismiss();
        }*/
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            if (!isScrolled) {
                if(stayStickers != null) {
                    if (stayStickers.getSuccess() == 0) {
                        tvNoPlaces.setVisibility(View.VISIBLE);

                        progress_main.setVisibility(View.GONE);
                        scrollViewExt.setVisibility(View.GONE);
                        isScrolled = false;
                    } else {
                        isScrolled = true;
                        tvNoPlaces.setVisibility(View.GONE);
                        progress_main.setVisibility(View.GONE);
                        scrollViewExt.setVisibility(View.VISIBLE);
                    }
                    if (visitStickers != null) {
                        if (visitStickers.getSuccess() == 0) {
                            tvNoPlaces.setVisibility(View.VISIBLE);

                            progress_main.setVisibility(View.GONE);
                            scrollViewExt.setVisibility(View.GONE);
                            isScrolled = false;
                        } else {
                            isScrolled = true;
                            tvNoPlaces.setVisibility(View.GONE);
                            progress_main.setVisibility(View.GONE);
                            scrollViewExt.setVisibility(View.VISIBLE);
                        }
                    }
                }
                StringBuilder stayIds = new StringBuilder();
                StringBuilder visitIds = new StringBuilder();
                if (stayStickers != null) {
                    int size = stayStickers.getData().size();
                    for (int i = 0; i < size - 1; i++) {
                        stayIds.append(stayStickers.getData().get(i).getId()).append(",");
                    }
                    if (size > 0)
                        stayIds.append(stayStickers.getData().get(size - 1).getId());
                }
                if (visitStickers != null) {
                    int size = visitStickers.getData().size();
                    for (int i = 0; i < size - 1; i++) {
                        visitIds.append(visitStickers.getData().get(i).getId()).append(",");
                    }
                    if (size > 0)
                        visitIds.append(visitStickers.getData().get(size - 1).getId());
                }
                String tmpUrl;
                if (this.getArguments().getBoolean("interest")) {
                    tmpUrl = url1;

                } else {
                    tmpUrl = url;
                }
                if (this.getArguments().getString("segid").toString().length() > 0) {
                    tmpUrl += "&segment=" + this.getArguments().getString("segid");
                }

                tmpUrl += "&stayp_ids=" + stayIds.toString();
                tmpUrl += "&visitp_ids=" + visitIds.toString();
                View loadMore = View.inflate(getActivity(), R.layout.load_more, null);
                placeLayout.addView(loadMore);
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
                            Log.d("url_test",urls[i]);
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

                        if (isStay && isVisit) {
                            if (i == 1) {
                                if (isScrolled) {
                                    if (stayStickers != null) {
                                        tmpStay = gson.fromJson(json, PlacestickerDetails.class);
                                        for (int k = 0; k < tmpStay.getData().size(); k++) {
                                            stayStickers.getData().add(tmpStay.getData().get(k));
                                        }
                                    } else {
                                        stayStickers = gson.fromJson(json, PlacestickerDetails.class);
                                    }
                                } else {
                                    stayStickers = gson.fromJson(json, PlacestickerDetails.class);
                                }
                            } else {
                                if (isScrolled) {
                                    if (visitStickers != null) {
                                        tmpPlace = gson.fromJson(json, PlacestickerDetails.class);
                                        for (int k = 0; k < tmpPlace.getData().size(); k++) {
                                            visitStickers.getData().add(tmpPlace.getData().get(k));
                                        }
                                    } else {
                                        visitStickers = gson.fromJson(json, PlacestickerDetails.class);
                                    }
                                } else {
                                    visitStickers = gson.fromJson(json, PlacestickerDetails.class);
                                }
                            }
                        } else if (isStay) {
                            if (isScrolled) {
                                if (stayStickers != null) {
                                    tmpStay = gson.fromJson(json, PlacestickerDetails.class);
                                    for (int k = 0; k < tmpStay.getData().size(); k++) {
                                        stayStickers.getData().add(tmpStay.getData().get(k));
                                    }
                                } else {
                                    stayStickers = gson.fromJson(json, PlacestickerDetails.class);
                                }
                            } else {
                                stayStickers = gson.fromJson(json, PlacestickerDetails.class);
                            }
                        } else if (isVisit) {
                            if (isScrolled) {
                                if (visitStickers != null) {
                                    tmpPlace = gson.fromJson(json, PlacestickerDetails.class);
                                    for (int k = 0; k < tmpPlace.getData().size(); k++) {
                                        visitStickers.getData().add(tmpPlace.getData().get(k));
                                    }
                                } else {
                                    visitStickers = gson.fromJson(json, PlacestickerDetails.class);
                                }
                            } else {
                                visitStickers = gson.fromJson(json, PlacestickerDetails.class);
                            }
                        }

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
            }
            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            progress_main.setVisibility(View.GONE);
                handlePostsList();

        }

        @Override
        protected void onPreExecute() {

            if (!isScrolled) {
                progress_main.setVisibility(View.VISIBLE);

            }
            tvNoPlaces.setVisibility(View.GONE);
        }
    }

    private void handlePostsList() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isScrolled) {
                        int size = placeLayout.getChildCount();
                        placeLayout.removeViewAt(size - 1);
                        isScrolled = false;
                        if (isStay && tmpStay != null) {
                            tvNoPlaces.setVisibility(View.GONE);
                            for (int i = 0; i < tmpStay.getData().size(); i++) {
                                View view = getView_visit(tmpStay.getData().get(i));
                                placeLayout.addView(view);
                            }
                        } else {
                            if(tmpStay == null &&stayStickers == null ) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);
                            }

                        }
                        if (isVisit && tmpPlace != null) {
                            tvNoPlaces.setVisibility(View.GONE);
                            progress_main.setVisibility(View.GONE);
                            for (int i = 0; i < tmpPlace.getData().size(); i++) {
                                View view = getView_visit(tmpPlace.getData().get(i));
                                placeLayout.addView(view);
                            }
                        } else {
                            if(tmpPlace == null &&  visitStickers == null) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        placeLayout.removeAllViews();
                        if (isStay && stayStickers != null) {
                            if(stayStickers.getSuccess() == 0) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);

                            }else {
                                tvNoPlaces.setVisibility(View.GONE);
                                for (int i = 0; i < stayStickers.getData().size(); i++) {
                                    View view = getView_visit(stayStickers.getData().get(i));
                                    placeLayout.addView(view);
                                }
                            }
                        } else {
                            if(stayStickers == null) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);
                            }
                        }
                        if (isVisit && visitStickers != null) {
                            if(visitStickers.getSuccess() == 0) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);

                            }else {
                                tvNoPlaces.setVisibility(View.GONE);
                                progress_main.setVisibility(View.GONE);
                                for (int i = 0; i < visitStickers.getData().size(); i++) {
                                    View view = getView_visit(visitStickers.getData().get(i));
                                    placeLayout.addView(view);
                                }
                            }
                        } else {
                            if(visitStickers == null) {
                                tvNoPlaces.setVisibility(View.VISIBLE);
                                progress_main.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }
    }

    private void failedLoadingPosts() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View getView_visit(final PlacestickerDetails.PlaceDetails event) {

        View view = View.inflate(getActivity(), R.layout.place_visit, null);

        TextView mHeading = (TextView) view.findViewById(R.id.tv_heading);
        TextView mHowToReach = (TextView) view.findViewById(R.id.tv_howtoreach);
        TextView mName = (TextView) view.findViewById(R.id.tv_name);
        TextView mTimeAgo = (TextView) view.findViewById(R.id.tv_timeago);
        TextView mCity = (TextView) view.findViewById(R.id.tv_city);
        TextView mDistance = (TextView) view.findViewById(R.id.tv_distance);
        final TextView mBeenthere = (TextView) view.findViewById(R.id.tv_beenthere);
        final TextView mverify = (TextView) view.findViewById(R.id.tv_place_interest_visit);
        TextView mComment = (TextView) view.findViewById(R.id.tv_comment);
        RoundedImageView iv = (RoundedImageView) view.findViewById(R.id.iv_profile_icon_visit);

        FrameLayout fl_placevisit_pic = (FrameLayout) view.findViewById(R.id.fl_place_visit_pic);
        LinearLayout layoutVisit = (LinearLayout) view.findViewById(R.id.linearLayout_visit);
        LinearLayout layoutVerify = (LinearLayout) view.findViewById(R.id.ll_beenthere);



        if (event != null) {
            tvNoPlaces.setVisibility(View.GONE);

            mHeading.setText(event.getName());
            mHowToReach.setText(event.getHowToReach());
            mName.setText(event.getUser_fullname());
            mTimeAgo.setText(event.getTimeago());
            mCity.setText("near " + event.getCreat_city());
            mDistance.setText(event.getLocation() + " km");


            mComment.setText(event.getComments());
            mBeenthere.setText(event.getBeenthere());
            Log.d("been there",event.getBeenthere_status()+"");
            if (Integer.parseInt(event.getBeenthere_status()) > 0) {
                verified = getResources().getDrawable(R.drawable.beenthere);
            } else {
                verified = getResources().getDrawable(R.drawable.beenthere_icon);
            }
            if (event.getBeenthere_status().equals("0")) {
                mverify.setText("Been there? Verify");
            } else {
                mverify.setText("Verified");
            }
            mBeenthere.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
            if (Integer.parseInt(event.getComment_status()) > 0) {
                comments = getResources().getDrawable(R.drawable.comment);
            } else {
                comments = getResources().getDrawable(R.drawable.comment_icon);
            }
            mComment.setCompoundDrawablesWithIntrinsicBounds(comments, null, null, null);
            //mBroadcasted.setText();
            //mCastPlace.setText();
            LoadProfileImageAsync task = new LoadProfileImageAsync(iv, this);
            task.execute(event.getUser_img());

            task = new LoadProfileImageAsync(fl_placevisit_pic, this);
            task.execute(event.getImage());

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", event.getUserid());
                    i.putExtra("recieverid", event.getId());
                    startActivity(i);

                }
            });

            layoutVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), PlaceToVisitActivity.class);
                    i.putExtra("id", event.getId());
                    i.putExtra("user_id", event.getUserid());
                    i.putExtra("type", event.getData_type());
                    i.putExtra("show_comments", true);
                    startActivity(i);
                }
            });
            layoutVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), PlaceToVisitActivity.class);
                    i.putExtra("id", event.getId());
                    i.putExtra("user_id", event.getUserid());
                    i.putExtra("type", event.getData_type());
                    i.putExtra("show_comments", true);
                    startActivity(i);
                }
            });

            fl_placevisit_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isVisit) {
                        Intent i = new Intent(getActivity(), PlaceToVisitActivity.class);
                        i.putExtra("id", event.getId());
                        i.putExtra("user_id", event.getUserid());
                        i.putExtra("type", event.getData_type());
                        startActivity(i);
                    }
                }
            });

            layoutVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            layoutVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mverify.getText().toString().equalsIgnoreCase("Verified")) {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=PlacetoVisitNotBeenThere&placeid=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                        mBeenthere.setText(Math.abs(Integer.parseInt(mBeenthere.getText().toString()) - 1) + "");

                        verified = getResources().getDrawable(R.drawable.beenthere_icon);
                        mverify.setText("Been there? Verify");

                        mBeenthere.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
                        PostDataToServer post = new PostDataToServer(PlacesFragment.this);

                        post.execute(url);
                        //mDialog = ProgressDialog.show(getActivity(), "", "Loading...");

                    } else {
                        String url = "http://www.365hops.com/webservice/controller.php?Servicename=PlacetoVisitBeenThere&placeid=" + event.getId() + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                        mBeenthere.setText(Math.abs(Integer.parseInt(mBeenthere.getText().toString()) + 1) + "");
                        verified = getResources().getDrawable(R.drawable.beenthere);


                        mverify.setText("Verified");

                        mBeenthere.setCompoundDrawablesWithIntrinsicBounds(verified, null, null, null);
                        PostDataToServer post = new PostDataToServer(PlacesFragment.this);

                        post.execute(url);
                        // mDialog = ProgressDialog.show(getActivity(), "", "Loading...");
                    }
                }
            });
        } else {
            tvNoPlaces.setVisibility(View.VISIBLE);
            progress_main.setVisibility(View.GONE);
        }
        return view;
    }

    public View getPlanView(PlacestickerDetails.PlaceDetails event) {

        View view = View.inflate(getActivity(), R.layout.plan_sticker, null);

       /* TextView mHeading = (TextView) view.findViewById(R.id.tv_text);
        TextView mName = (TextView) view.findViewById(R.id.tv_name_plansticker);
        TextView mLastActive = (TextView) view.findViewById(R.id.tv_last_active_plansticker);
        TextView mCastPlace = (TextView) view.findViewById(R.id.tv_cast_place_plansticker);
        TextView mPlace = (TextView) view.findViewById(R.id.tv_place_plansticker);

        Button mInterests = (Button) view.findViewById(R.id.bt_interest_plansticker);
        Button mComments = (Button) view.findViewById(R.id.bt_comment_plansticker);

        FrameLayout fl_planstickerpic = (FrameLayout) view.findViewById(R.id.fl_planstickerpic);

        fl_planstickerpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisit){
                    startActivity(new Intent(getActivity(), PlanStickerClick.class));
                }
            }
        });

        if (event != null) {
            mHeading.setText(event.getTitle());
            //mDays.setText(event.getDate());
            mName.setText(event.getUser_fullname());
            mLastActive.setText(event.getTimeago());
            //mBroadcasted.setText();
            //mCastPlace.setText();
            mPlace.setText(event.getWhere_area());

            mInterests.setText(event.getIntrests());
            mComments.setText(event.getComments());
        }*/
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.events_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateViews(String url) {
        isVisit = cbVisit.isChecked();
        isStay = cbStay.isChecked();
        Log.d("interest",url);
        mClear = getArguments().getBoolean("clear");
        if (mClear) {
            placeLayout.removeAllViews();
            progress_main.setVisibility(View.VISIBLE);
            stayStickers = null;
            visitStickers = null;
            getArguments().putBoolean("clear", false);
        }
        gst = new GetSummaryTask();
        if (this.getArguments().getBoolean("interest")) {

            if (isStay && isVisit) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || (stayStickers == null && visitStickers == null)) {
                    gst.execute(new String[]{url1 + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Visit",
                            url1 + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Stay"});
                    Log.d("placeurl", url + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Visit");
                }
            } else if (isStay) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || stayStickers == null) {
                    gst.execute(url1 + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Stay");
                }
            } else if (isVisit) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || visitStickers == null) {
                    gst.execute(url1 + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid") + "&type=Visit");
                }
            } else {
                placeLayout.removeAllViews();
            }
        } else {
            if (isStay && isVisit) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || (stayStickers == null && visitStickers == null)) {
                    gst.execute(new String[]{url + "&type=Visit", url + "&type=Stay"});
                }
            } else if (isStay) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || stayStickers == null) {
                    gst.execute(url + "&type=Stay");
                }
            } else if (isVisit) {
                if (isScrolled || mClear
                        || isCheckedChange
                        || visitStickers == null) {
                    gst.execute(url + "&type=Visit");
                }
            } else {
                placeLayout.removeAllViews();
            }
        }


        isCheckedChange = false;
    }
}