package com.appzollo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.CircularImageView;
import com.appzollo.classes.EventSticker;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.NDSpinner;
import com.appzollo.classes.PlanStickerDetails;
import com.appzollo.classes.SearchAdapter;
import com.appzollo.fragments.EventsFragment;
import com.appzollo.fragments.PlacesFragment;
import com.appzollo.fragments.SegmentFollowers;
import com.appzollo.interfaces.OnLatlngCompleteListener;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.lists.ExpandableListAdapter;
import com.appzollo.utils.CommonsUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PagerActivity extends BaseActivity implements OnLoadCompleteListener, SearchView.OnQueryTextListener, OnLatlngCompleteListener {
    private static String[] titles = {"Events", "Places"};
    private static ArrayList<String> titlesList = new ArrayList<String>();
    private PagerAdapter pagerAdapter;
    private PagerTabStrip pagerTabStrip;
    private ViewPager viewPager;
    ImageView ivBroadcast;

    RelativeLayout rlDrawer;
    private SearchView searchView;
    static NDSpinner spinner1;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] spList = {"Motor Cycling", "Car"};
    private ExpandableListView mListView;
    private ExpandableListAdapter mAccListAdapter;
    private int lastExpandedPosition = -1;
    static Bundle eventBundle = new Bundle();
    static Bundle placeBundle = new Bundle();
    static Bundle segmentBundle = new Bundle();
    int[] itemId = {10, 11, 9, 48, 44, 12, 42, 13, 37, 55, 14, 39, 15, 16, 8, 46, 43, 53, 17, 18, 22, 45, 19, 20, 54};
    int[] itemLocalId1 = {62, 61, 63, 64};
    int[] itemLocalId2 = {57};
    int[] itemLocalId3 = {58};
    int[] itemLocalId4 = {59};
    SharedPreferences pref;
    int count = 0;
    List<String> childData, childLocaldata, childLocaldata1, childLocaldata2, childLocaldata3;
    int[] childImg = {R.drawable.bungee_jumping, R.drawable.camping, R.drawable.cycling, R.drawable.fishing_tours, R.drawable.flying_fox,
            R.drawable.hotairballooning, R.drawable.jeepsafari, R.drawable.kayaking, R.drawable.motorcycle_icon,
            R.drawable.mountainbiking, R.drawable.mountaineering, R.drawable.paintball, R.drawable.paragliding, R.drawable.parasailing, R.drawable.rafting, R.drawable.rappelling,
            R.drawable.rockclimbing, R.drawable.running, R.drawable.scubadiving, R.drawable.skiing,
            R.drawable.skydiving, R.drawable.snorkelling, R.drawable.trekking,
            R.drawable.wallclimbing, R.drawable.watersports};
    int[] childImg1 = {R.drawable.art_and_culture_trip, R.drawable.food_trip, R.drawable.historicaltrip, R.drawable.photographytrip};
    int[] childIm2 = {R.drawable.cultural};
    int[] childIm3 = {R.drawable.historicaltour};
    int[] childIm4 = {R.drawable.wildlife};
    ScrollView scrollview;

    ImageView ivSpinner;
    LinearLayout ll_bottom;
    TextView tvprofilename,tvLocation;
    MenuItem searchMenuItem;
    private JSONParser jParser;
    HashMap<String, String> map = new HashMap<String, String>();
    TextView tvInboxcout,tvNotificcations_count;
    String query = "";
    static ArrayAdapter<String> dataAdapter;
    String spinner_select = "";
    boolean spinner_selection = false;
    static boolean onTouch = false;
    Typeface tf;
    EditText tv_test;
    LinearLayout llInCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
       tf = Typeface.createFromAsset(getAssets(), "seguisb.ttf");
        scrollview = (ScrollView) findViewById(R.id.scrollView);
        pref = getSharedPreferences("com.appzollo", Context.MODE_PRIVATE);
        jParser = new JSONParser();
        titlesList.clear();
        titlesList.add("Events");
        titlesList.add("Places");
        pref.edit().putBoolean("logout", false).commit();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        eventBundle.putString("segid", "");
        eventBundle.putString("location", "");
        eventBundle.putBoolean("interest", false);
        placeBundle.putString("segid", "");
        placeBundle.putString("location", "");
        segmentBundle.putString("segid", "");

        pagerTabStrip = (PagerTabStrip) this.findViewById(R.id.pager_title_strip);

        viewPager = (ViewPager) this.findViewById(R.id.pager);
        ivBroadcast = (ImageView) this.findViewById(R.id.ivBroadcast);
        rlDrawer = (RelativeLayout) this.findViewById(R.id.left_drawer);
        TextView tvSettings = (TextView) this.findViewById(R.id.tv_settings);
        TextView tvInbox = (TextView) this.findViewById(R.id.tv_inbox);
        tvInboxcout = (TextView) findViewById(R.id.tv_inboxcount);
        TextView tvEnquiries = (TextView) this.findViewById(R.id.tv_enquiries);
        TextView tvNotificcations = (TextView) this.findViewById(R.id.tv_notifications);
        llInCount = (LinearLayout)this.findViewById(R.id.llInCount);
        tvNotificcations_count = (TextView) this.findViewById(R.id.tv_notifycount);
        TextView tvMyCredits = (TextView) this.findViewById(R.id.tv_mycredits);
        tvprofilename = (TextView) this.findViewById(R.id.tv_profilename);
        tvLocation = (TextView) this.findViewById(R.id.tv_location);
        TextView tvSegments = (TextView) this.findViewById(R.id.tv_segments);
        TextView tvFindPeople = (TextView) this.findViewById(R.id.tv_findpeople);
        TextView tvShowInterest = (TextView) this.findViewById(R.id.tv_interestshown);
        TextView tvActivities = (TextView) this.findViewById(R.id.tv_activities);
        TextView tvPlaces = (TextView) this.findViewById(R.id.tv_places);
        mListView = (ExpandableListView) this.findViewById(R.id.exp_list_view);
        mListView.setVisibility(View.GONE);

        tvEnquiries.setTypeface(tf);
        tvFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, FindPeopleActivity.class));
            }
        });
        tvSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListView.getVisibility() == View.VISIBLE) {
                    mListView.setVisibility(View.GONE);
                } else {
                    updateData();
                }
            }
        });
        tvShowInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMenuItem.setVisible(true);

                eventBundle.putString("segid", "");
                eventBundle.putString("location", "");
                eventBundle.putBoolean("interest", true);
                placeBundle.putString("segid", "");
                placeBundle.putString("location", "");
                placeBundle.putBoolean("interest", true);
                placeBundle.putBoolean("clear", true);
                segmentBundle.putString("segid", "");
                titlesList.clear();
                titlesList.add("Events");
                titlesList.add("Places");
                pagerAdapter.notifyDataSetChanged();
                EventsFragment.cbPlan.setChecked(true);
                EventsFragment.cbEvents.setChecked(true);
                getActionBar().setTitle("");

                getActionBar().setDisplayShowCustomEnabled(false);
                count = 0;
                ll_bottom.setVisibility(View.VISIBLE);

                spinner1.setSelection(0);
                final CharSequence[] items1 = {"All", "Adventure", "Local Tours", "Cultural Tour","Historical Tour","Wilidlife","Others"};
                dataAdapter.clear();
                for(int k = 0 ; k < items1.length ; k++ ){
                    dataAdapter.add(items1[k].toString());
                    dataAdapter.notifyDataSetChanged();
                }
                mDrawerLayout.closeDrawers();

            }
        });

        spinner1 = (NDSpinner) findViewById(R.id.spinner);

        //spinner1 = (NDSpinner) findViewById(R.id.spinner1);
        ivSpinner = (ImageView) findViewById(R.id.ivSpinner);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);


        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        final CharSequence[] items1 = {"All", "Adventure", "Local Tours", "Cultural Tour","Historical Tour","Wilidlife","Others"};
       for(int k = 0 ; k < items1.length ; k++ ){
           dataAdapter.add(items1[k].toString());
           dataAdapter.notifyDataSetChanged();
       }




        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long l) {
                //  Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
                if (count > 0) {

                    //   String kjkselectedItem = parent.getItemAtPosition(position).toString();
                    /*if(spinner_select.length() > 1 && position < 7){

                        if(spinner_selection) {
                            dataAdapter.remove(spinner_select);
                            dataAdapter.notifyDataSetChanged();
                            spinner_select = "";
                            spinner_selection = false;
                          //  Toast.makeText(getApplicationContext(),"ckuck",Toast.LENGTH_SHORT).show();
                        }
                        spinner_selection = true;

                    }*/
                    if (position == 1) {
                        final CharSequence[] items = {
                                "Bungee Jump",
                                "Camping", "Cycling", "Fishing Tours", "Flying Fox",
                                "Hot Air Bollooning", "Jeep Safari", "Kayaking", "Motorcycling", "Mountain Biking", "Mountaineering", "Paintball",
                                "Paragliding", "Parasailing", "Rafting", "Rappelling", "Rock Climbing", "Running", "Scuba Diving", "Skiing",
                                "Skydiving", "Snorkelling",
                                "Trekking", "Wall Climbing", "Water Sports"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(PagerActivity.this);
                        builder.setTitle("Make your selection");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                ivSpinner.setBackgroundResource(childImg[item]);
                                eventBundle.putString("segid", itemId[item] + "");
                                eventBundle.putBoolean("interest", false);
                                eventBundle.putBoolean("clear", true);
                                placeBundle.putBoolean("interest", false);
                                placeBundle.putString("segid", itemId[item] + "");
                                placeBundle.putBoolean("clear", true);
                                segmentBundle.putString("segid", itemId[item] + "");
                                pagerAdapter.notifyDataSetChanged();
                              //  spinner.setVisibility(View.GONE);
                               // spinner1.setVisibility(View.VISIBLE);
                              /*  dataAdapter.add(items[item].toString());
                                dataAdapter.notifyDataSetChanged();
                                    spinner_select = items[item].toString();
                                spinner_selection = false;
                                spinner1.setSelection(dataAdapter.getPosition(items[item].toString()));*/

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    } else if (position == 2) {

                       /* final CharSequence[] items = {
                                "Badminton", "Billiards", "Boxing", "Cricket", "Formula", "Hourse Riding", "Pool/Snooker", "Skating"
                                , "Swimming", "Tennis", "Basketball", "Bowling", "Chess", "Football", "Hockey", "Karate", "Shooting", "Squash", "Table Tennis", "Volleyball"
                        };*/

                        final CharSequence[] items = {"Art And Culture Trip", "Food Trip", "Historical Trip", "Photogarphy Trip"};

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(PagerActivity.this);
                        builder1.setTitle("Make your selection");
                        builder1.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // Do something with the selection
                                // mDoneButton.setText(items[item]);
                                ivSpinner.setBackgroundResource(childImg1[item]);
                                eventBundle.putString("segid", itemLocalId1[item] + "");
                                eventBundle.putBoolean("interest", false);
                                eventBundle.putBoolean("clear", true);
                                placeBundle.putBoolean("clear", true);
                                placeBundle.putBoolean("interest", false);
                                placeBundle.putString("segid", itemLocalId1[item] + "");
                                segmentBundle.putString("segid", itemLocalId1[item] + "");
                                pagerAdapter.notifyDataSetChanged();
                                //spinner.setVisibility(View.GONE);
                                //spinner1.setVisibility(View.VISIBLE);
                             /*   dataAdapter.add(items[item].toString());
                                dataAdapter.notifyDataSetChanged();
                                spinner_select = items[item].toString();
                                spinner_selection = false;
                                spinner1.setSelection(dataAdapter.getPosition(items[item].toString()));*/

                            }
                        });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    } else if (position == 3) {
                        ivSpinner.setBackgroundResource(childIm2[0]);
                        eventBundle.putString("segid", "57");
                        eventBundle.putBoolean("interest", false);
                        eventBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("interest", false);
                        placeBundle.putString("segid", "57");
                        segmentBundle.putString("segid", "57");
                        pagerAdapter.notifyDataSetChanged();
                    } else if (position == 4) {
                        ivSpinner.setBackgroundResource(childIm3[0]);
                        eventBundle.putString("segid", "58");
                        eventBundle.putBoolean("interest", false);
                        eventBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("interest", false);
                        placeBundle.putString("segid", "58");
                        segmentBundle.putString("segid", "58");
                        pagerAdapter.notifyDataSetChanged();
                    } else if (position == 5) {
                        ivSpinner.setBackgroundResource(childIm4[0]);
                        eventBundle.putString("segid", "59");
                        eventBundle.putBoolean("interest", false);
                        eventBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("interest", false);
                        placeBundle.putString("segid", "59");
                        segmentBundle.putString("segid", "59");
                        pagerAdapter.notifyDataSetChanged();
                    } else if (position == 6 && !onTouch) {
                        ivSpinner.setBackgroundResource(0);
                        eventBundle.putString("segid", "60");
                        eventBundle.putBoolean("interest", false);
                        eventBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("clear", true);
                        placeBundle.putString("segid", "60");
                        segmentBundle.putString("segid", "60");
                        pagerAdapter.notifyDataSetChanged();

                    }else if (position == 0 || (position == 6 &&onTouch)) {
                       if(onTouch)
                           spinner1.setSelection(0);
                        ivSpinner.setBackgroundResource(0);
                        eventBundle.putString("segid", "");
                        eventBundle.putBoolean("interest", false);
                        eventBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("clear", true);
                        placeBundle.putBoolean("interest", false);
                        placeBundle.putString("segid", "");
                        segmentBundle.putString("segid", "");
                        pagerAdapter.notifyDataSetChanged();
                        onTouch = false;
                    }

                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               Toast.makeText(getApplicationContext(), "test" + "", Toast.LENGTH_SHORT).show();

            }
        });

        LinearLayout llProfile = (LinearLayout) this.findViewById(R.id.ll_profile);
        CircularImageView ivProfile = (CircularImageView) this.findViewById(R.id.iv_profile);
        if (CommonsUtils.getPrefString(this, "profileimage") != null) {
            LoadProfileImageAsync task = new LoadProfileImageAsync(ivProfile, this);
            task.execute(CommonsUtils.getPrefString(this, "profileimage"));
        }

        tvprofilename.setText(CommonsUtils.getPrefString(this, "user"));
        tvLocation.setText(CommonsUtils.getPrefString(this, "location"));
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, ProfileActivity.class).putExtra("my", true).putExtra("userid", CommonsUtils.getPrefString(PagerActivity.this, "userid")));
            }
        });
        tvMyCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, MyCreditsActivity.class));
            }
        });
        tvEnquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, EnquiriesActivity.class));
            }
        });
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, SettingsActivity.class));
                finish();
            }
        });
        tvInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, InboxActivity.class));
            }
        });
        tvInboxcout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, InboxActivity.class));
            }
        });
        tvNotificcations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, NotificationsActivity.class));
            }
        });
        tvNotificcations_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PagerActivity.this, NotificationsActivity.class));
            }
        });
        viewPager.setAdapter(pagerAdapter);
        pagerTabStrip.setTabIndicatorColorResource(R.color.pager_title);
        ivBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getActionBar().setTitle("365Hops");
                if (mListView.getVisibility() == View.VISIBLE) {
                    mListView.setVisibility(View.GONE);

                }
                scrollview.scrollTo(0, 0);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                tvLocation.setText(CommonsUtils.getPrefString(PagerActivity.this, "location"));
                String url = "http://www.365hops.com/webservice/controller.php?Servicename=getNewInboxmsg&userid="+CommonsUtils.getPrefString(PagerActivity.this, "userid");
               GetSummaryTask gst = new GetSummaryTask();
                gst.execute(url);
                String url1 = "http://www.365hops.com/webservice/controller.php?Servicename=getNewNotification&userid="+CommonsUtils.getPrefString(PagerActivity.this, "userid");
                GetSummaryTask_notify gst1 = new GetSummaryTask_notify();
                gst1.execute(url1);
                tvprofilename.setText(CommonsUtils.getPrefString(getApplicationContext(), "user"));
                tvLocation.setText(CommonsUtils.getPrefString(getApplicationContext(), "location"));
                scrollview.scrollTo(0, 0);
                // getActionBar().setTitle("365Hops");
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);

        tvActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.closeDrawers();

                if (eventBundle.getBoolean("interest") || eventBundle.getString("segid").length() > 0) {
                    searchMenuItem.setVisible(true);
                    eventBundle.putString("segid", "");
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("latlon", false);
                    eventBundle.putBoolean("clear", true);
                    placeBundle.putString("segid", "");
                    placeBundle.putBoolean("clear", true);
                    placeBundle.putBoolean("latlon", false);
                    segmentBundle.putString("segid", "");
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    pagerAdapter.notifyDataSetChanged();
                    mDrawerLayout.closeDrawers();
                    getActionBar().setTitle("");
                    getActionBar().setDisplayShowCustomEnabled(false);
                  ll_bottom.setVisibility(View.VISIBLE);

                }
                viewPager.setCurrentItem(0);
            }
        });
        tvPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.closeDrawers();
                if (eventBundle.getBoolean("interest") || eventBundle.getString("segid").length() > 0) {
                   searchMenuItem.setVisible(true);
                    eventBundle.putString("segid", "");
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("clear", true);
                    placeBundle.putString("segid", "");
                    placeBundle.putBoolean("clear", true);
                    segmentBundle.putString("segid", "");
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    pagerAdapter.notifyDataSetChanged();
                    mDrawerLayout.closeDrawers();
                    getActionBar().setTitle("");
                    getActionBar().setDisplayShowCustomEnabled(false);
                   ll_bottom.setVisibility(View.VISIBLE);

                }
                viewPager.setCurrentItem(1);
            }
        });
//        handleIntent(getIntent());
      /*  spinner1.setOnTouchListener(Spinner_OnTouch);
        spinner1.setOnKeyListener(Spinner_OnKey);
 */   }


    /*private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                final CharSequence[] items1 = {"All", "Adventure", "Local Tours", "Cultural Tour","Historical Tour","Wilidlife","Others"};
                dataAdapter.clear();
                onTouch = true;

                for(int k = 0 ; k < items1.length ; k++ ){
                    dataAdapter.add(items1[k].toString());
                    dataAdapter.notifyDataSetChanged();
                }


            }
            return false;
        }
    };
    private static View.OnKeyListener Spinner_OnKey = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                final CharSequence[] items1 = {"All", "Adventure", "Local Tours", "Cultural Tour","Historical Tour","Wilidlife","Others"};
                dataAdapter.clear();
                onTouch =true;
                for(int k = 0 ; k < items1.length ; k++ ){
                    dataAdapter.add(items1[k].toString());
                    dataAdapter.notifyDataSetChanged();
                }

                return false;
            } else {
                return false;
            }
        }
    };*/

    @Override
    public void OnLatlngComplete(double lat, double lng) {
       // Toast.makeText(getApplicationContext(),"on submit",Toast.LENGTH_SHORT).show();
        double lat1=0,lon=0;
        eventBundle.putString("segid", "");
        eventBundle.putString("location", query);
        eventBundle.putString("lat", lat1+"");
        eventBundle.putString("lng", lon+"");
        eventBundle.putBoolean("latlon", true);
        eventBundle.putBoolean("interest", false);
        placeBundle.putString("segid", "");
        placeBundle.putString("location", query);
        placeBundle.putString("lat", lat1 + "");
        placeBundle.putString("lng", lon+"");
        placeBundle.putBoolean("latlon", false);
        segmentBundle.putString("segid", "");
        titlesList.clear();
        titlesList.add("Events");
        titlesList.add("Places");
        pagerAdapter.notifyDataSetChanged();
        EventsFragment.cbPlan.setChecked(true);
        EventsFragment.cbEvents.setChecked(true);
        mDrawerLayout.closeDrawers();


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


                   JSONObject jObj = new JSONObject(json);

                    String success = jObj.getString("error");
                    map.put("error", success);
                    if (success.equalsIgnoreCase("0")) {
                        //   JSONArray data = jObj.getJSONArray("data");

                        map.put("number", jObj.getString("number"));

                        }
                   /* Gson gson = new Gson();

                    planDetailStickers = gson.fromJson(json, PlanStickerDetails.class);
*/

                    content.close();
                } catch (Exception ex) {
                    Log.e("kjk", "Failed to parse JSON due to: " + ex);

                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("", "Failed to send HTTP POST request due to: " + ex);

            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            if(map.get("error").equalsIgnoreCase("0"))
                if(!map.get("number").equals("0")) {
                    tvInboxcout .setVisibility(View.VISIBLE);
                    tvInboxcout.setText("" + map.get("number"));
                }

        }

        @Override
        protected void onPreExecute() {

        }
    }


    public class GetSummaryTask_notify extends AsyncTask<String, Void, String> {

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


                    JSONObject jObj = new JSONObject(json);

                    String success = jObj.getString("error");
                    map.put("error", success);
                    if (success.equalsIgnoreCase("0")) {
                        //   JSONArray data = jObj.getJSONArray("data");

                        map.put("number", jObj.getString("number"));

                    }
                   /* Gson gson = new Gson();

                    planDetailStickers = gson.fromJson(json, PlanStickerDetails.class);
*/

                    content.close();
                } catch (Exception ex) {
                    Log.e("kjk", "Failed to parse JSON due to: " + ex);

                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("", "Failed to send HTTP POST request due to: " + ex);

            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {
            if(map.get("error").equalsIgnoreCase("0"))
                if(!map.get("number").equals("0")) {
                    tvNotificcations_count.setVisibility(View.VISIBLE);
                    llInCount.setVisibility(View.VISIBLE);
                    tvNotificcations_count.setText("" + map.get("number"));
                }

        }

        @Override
        protected void onPreExecute() {


        }
    }

    public void update() {
        startActivity(new Intent(PagerActivity.this, StartActivity.class));
        finish();
    }

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

    public static class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new EventsFragment();
                    fragment.setArguments(eventBundle);
                    return fragment;
                case 1:
                    fragment = new PlacesFragment();
                    fragment.setArguments(placeBundle);
                    return fragment;
                default:
                    fragment = new SegmentFollowers();
                    fragment.setArguments(segmentBundle);
                    return fragment;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return titlesList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlesList.get(position);
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            getPlace(intent.getStringExtra(SearchManager.EXTRA_DATA_KEY));
        }
    }

    private void doSearch(String query) {
//        Bundle data = new Bundle();
//        data.putString("query", query);
        GetPlaces task = new GetPlaces();
        task.execute(query.toString());

    }

    private void getPlace(String query) {
//        Bundle data = new Bundle();
//        data.putString("query", query);
        GetPlaces task = new GetPlaces();
        task.execute(query.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
         searchMenuItem = menu.findItem(R.id.device_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat
                .getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);

        return true;
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int arg0, Bundle query) {
//        CursorLoader cLoader = null;
//        if(arg0==0)
//            cLoader = new CursorLoader(getBaseContext(), PlacesSuggestionProvider.SEARCH_URI, null, null, new String[]{ query.getString("query") }, null);
//        else if(arg0==1)
//            cLoader = new CursorLoader(getBaseContext(), PlacesSuggestionProvider.DETAILS_URI, null, null, new String[]{ query.getString("query") }, null);
//        return cLoader;
//
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
//
//    }

//    @Override
//    public void onLoaderReset(Loader<Cursor> arg0) {
//        // TODO Auto-generated method stub
//    }

    public boolean onQueryTextChange(String newText) {
       // Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();

        GetPlaces task = new GetPlaces();
        task.execute(newText.toString());
        return true;
    }

    public boolean onQueryTextSubmit(String query) {

        this.query = query;
        GetLanLng task = new GetLanLng(query.replace(" ", "%20").toString(), PagerActivity.this);
        task.execute("");

      return true;
    }

    public boolean onClose() {
        //  mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event


        int upId = Resources.getSystem().getIdentifier("up", "id", "android");
        if (upId > 0) {
            ImageView up = (ImageView) findViewById(upId);

            if (up.getDrawable().equals(R.drawable.abc_ic_ab_back_holo_light)) {
               // Toast.makeText(PagerActivity.this, "drawer", Toast.LENGTH_SHORT).show();
            } else {
               // Toast.makeText(PagerActivity.this, "back", Toast.LENGTH_SHORT).show();
                if (mListView.getVisibility() == View.VISIBLE) {
                    mListView.setVisibility(View.GONE);


                }
               // ll_bottom.setVisibility(View.GONE);
            }
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        /*switch(item.getItemId()){
            case android.R.id.home:
                Toast.makeText(PagerActivity.this,"home",Toast.LENGTH_SHORT).show();
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

    void showDialog() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.overlay_image);
        dialog.setContentView(R.layout.broadcast_layout);
        ImageView ivMinimize = (ImageView) dialog.findViewById(R.id.iv_minimize);
        LinearLayout llCreatePlan = (LinearLayout) dialog.findViewById(R.id.ll_createPlan);
        LinearLayout llTagPlace = (LinearLayout) dialog.findViewById(R.id.ll_tagVisit);
        LinearLayout llSharePlace = (LinearLayout) dialog.findViewById(R.id.ll_shareEvent);
        llCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(PagerActivity.this, TagPlaceActivity.class));

            }
        });
        llTagPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                startActivity(new Intent(PagerActivity.this, AddPlaceActivityOne.class));

            }
        });
        llSharePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(PagerActivity.this, ShareNewEventActivity.class));

            }
        });
        ivMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    class GetPlaces extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... args) {
            ArrayList<String> predictionsArr = new ArrayList<String>();

            try {

                URL googlePlaces = new URL(
                        "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + URLEncoder.encode(args[0].toString(), "UTF-8") + "&types=geocode&language=en&sensor=true&key=AIzaSyAW2O9rBaQh4ooLuxBk_H-t62uHEz3AHFY");
                URLConnection tc = googlePlaces.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        tc.getInputStream()));

                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject predictions = new JSONObject(sb.toString());
                JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    predictionsArr.add(jo.getString("description"));
                }
            } catch (IOException e) {
                Log.e("YourApp", "GetPlaces : doInBackground", e);
            } catch (JSONException e) {
                Log.e("YourApp", "GetPlaces : doInBackground", e);
            }
            return predictionsArr;

        }

        @Override
        protected void onPostExecute(final ArrayList<String> result) {
            String[] columns = new String[]{"_id", "text"};
            Object[] temp = new Object[]{0, "default"};

            MatrixCursor cursor = new MatrixCursor(columns);

            for (int i = 0; i < result.size(); i++) {

                temp[0] = i;
                temp[1] = result.get(i);

                cursor.addRow(temp);

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                searchView.setSuggestionsAdapter(new SearchAdapter(PagerActivity.this, cursor, result));

            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int i) {
                  //  Toast.makeText(getApplicationContext(), "testjbjnj", Toast.LENGTH_LONG).show();

                    return false;
                }

                @Override
                public boolean onSuggestionClick(int i) {

                    double latitude = 0,longitude = 0;
                    Geocoder coder = new Geocoder(PagerActivity.this);
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(result.get(i), 50);
                        for(Address add : adresses){
                            longitude = add.getLongitude();
                            latitude = add.getLatitude();
                            // Toast.makeText(getApplication(),latitude+","+longitude,Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   // Toast.makeText(getApplication(),"on click",Toast.LENGTH_SHORT).show();
                    searchView.setQuery(result.get(i),false);
                    eventBundle.putString("segid", "");
                    eventBundle.putString("location", result.get(i).replace(" ", ""));
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("latlon", true);
                    eventBundle.putBoolean("clear", true);
                    eventBundle.putString("lat", latitude + "");
                    eventBundle.putString("lng", longitude + "");
                    placeBundle.putString("segid", "");
                    placeBundle.putString("location", result.get(i).replace(" ",""));
                    placeBundle.putBoolean("clear", true);
                    placeBundle.putBoolean("latlon", true);
                    placeBundle.putString("lat", latitude + "");
                    placeBundle.putString("lng", longitude + "");
                    segmentBundle.putString("segid", "");
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    pagerAdapter.notifyDataSetChanged();
                    EventsFragment.cbPlan.setChecked(true);
                    EventsFragment.cbEvents.setChecked(true);
                    mDrawerLayout.closeDrawers();


                    return false;
                }
            });
        }
    }

    private void updateData() {
        mListView.setVisibility(View.VISIBLE);

        mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 625));

        final List<String> headerList = new ArrayList<String>();
        final HashMap<String, List<String>> mExpListData;
        mExpListData = new HashMap<String, List<String>>();

        final List<String> headerData = new ArrayList<String>();
        headerData.add("Adventure");
        headerData.add("Local Tours");
        headerData.add("Cultural Tour");
        headerData.add("Historical Tour");
        headerData.add("Wildlife");
        //  headerData.add("Others");

        childData = new ArrayList<String>();
        childData.add("Bungee Jumping");
        childData.add("Camping");
        childData.add("Cycling");
        childData.add("Fishing Tours");
        childData.add("Flying Fox");
        childData.add("Hot Air Ballooning");
        childData.add("Jeep Safari");
        childData.add("Kayaking");
        childData.add("Motorcycling");
        childData.add("Mountain Biking");
        childData.add("Mountaineering");
        childData.add("Paintball");
        childData.add("Paragliding");
        childData.add("Parasailing");
        childData.add("Rafting");
        childData.add("Rappelling");
        childData.add("Rock Climbing");
        childData.add("Running");
        childData.add("Scuba Diving");
        childData.add("Skiing");
        childData.add("Skydiving");
        childData.add("Snorkelling");
        childData.add("Trekking");
        childData.add("Wall Climbing");
        childData.add("Water Sports");

        mExpListData.put(headerData.get(0), childData);

        childLocaldata = new ArrayList<String>();
        childLocaldata.add("Art and culture trip");
        childLocaldata.add("Food Trip");
        childLocaldata.add("Historical Trip");
        childLocaldata.add("Photography Trip");

        mExpListData.put(headerData.get(1), childLocaldata);

        childLocaldata1 = new ArrayList<String>();
      //  childLocaldata1.add("Cultural Tour");
        mExpListData.put(headerData.get(2), childLocaldata1);

        childLocaldata2 = new ArrayList<String>();
       // childLocaldata2.add("Historical Tour");
        mExpListData.put(headerData.get(3), childLocaldata2);

        childLocaldata3 = new ArrayList<String>();
       // childLocaldata3.add("Wildlife");
        mExpListData.put(headerData.get(4), childLocaldata3);


      /*  mExpListData.put(headerData.get(2), new ArrayList<String>());
        mExpListData.put(headerData.get(3), new ArrayList<String>());
        mExpListData.put(headerData.get(4), new ArrayList<String>());*/
        // mExpListData.put(headerData.get(5), new ArrayList<String>());

        mAccListAdapter = new ExpandableListAdapter(this, headerData, mExpListData);
      //  mListView.setGroupIndicator(getResources().getDrawable(R.drawable.segment_indicator));
        mListView.setAdapter(mAccListAdapter);
        mAccListAdapter.notifyDataSetChanged();

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

                if (lastExpandedPosition != -1
                        && i != lastExpandedPosition && i < 2) {
                    mListView.collapseGroup(lastExpandedPosition);
                    lastExpandedPosition = -1;

                } else {
                    if (i == 2) {
                        {
                            searchMenuItem.setVisible(false);
                            searchMenuItem.getIcon().setVisible(false, true);
                            eventBundle.putString("segid", itemLocalId2[0] + "");
                            eventBundle.putBoolean("interest", false);
                            eventBundle.putString("location", "");
                            eventBundle.putBoolean("latlon", false);
                            placeBundle.putBoolean("latlon", true);
                            placeBundle.putString("location", "");
                            placeBundle.putString("segid", itemLocalId2[0] + "");
                            segmentBundle.putString("segid", itemLocalId2[0] + "");
                            segmentBundle.putBoolean("clear", true);
                            titlesList.clear();
                            titlesList.add("Events");
                            titlesList.add("Places");
                            titlesList.add("Followers");
                            //  getActionBar().setCustomView(R.layout.actionbar_segment);
                         //   getActionBar().setDisplayShowTitleEnabled(false);
                            getActionBar().setTitle("");
                            getActionBar().setDisplayShowCustomEnabled(true);


                            LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                            View header = inflater.inflate(R.layout.actionbar_segment, null);

                            //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                            TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                            ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                            iv.setBackgroundResource(childIm2[0]);
                            tv.setText(headerData.get(2));
                            android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,

                                    ActionBar.LayoutParams.MATCH_PARENT);
                            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                            getActionBar().setCustomView(header, layoutParams);
                            //getActionBar().setTitle(childLocaldata.get(i2));
                            pagerAdapter.notifyDataSetChanged();
                            if (mListView.getVisibility() == View.VISIBLE) {
                                mListView.setVisibility(View.GONE);


                            }
                           ll_bottom.setVisibility(View.GONE);
                            mDrawerLayout.closeDrawers();


                        }
                    } else if (i == 3) {
                        {
                            searchMenuItem.setVisible(false);
                            searchMenuItem.getIcon().setVisible(false, true);

                            eventBundle.putString("segid", itemLocalId3[0] + "");
                            eventBundle.putBoolean("interest", false);
                            placeBundle.putString("segid", itemLocalId3[0] + "");
                            segmentBundle.putString("segid", itemLocalId3[0] + "");
                            segmentBundle.putBoolean("clear", true);
                            titlesList.clear();
                            titlesList.add("Events");
                            titlesList.add("Places");
                            titlesList.add("Followers");
                            //  getActionBar().setCustomView(R.layout.actionbar_segment);
                            getActionBar().setDisplayShowTitleEnabled(false);
                            getActionBar().setDisplayShowCustomEnabled(true);
                            LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                            View header = inflater.inflate(R.layout.actionbar_segment, null);

                            //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                            TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                            ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                            iv.setBackgroundResource(childIm3[0]);
                            tv.setText(headerData.get(3));
                            android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                                    ActionBar.LayoutParams.MATCH_PARENT);
                            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                            getActionBar().setCustomView(header, layoutParams);
                            //getActionBar().setTitle(childLocaldata.get(i2));
                            pagerAdapter.notifyDataSetChanged();
                            if (mListView.getVisibility() == View.VISIBLE) {
                                mListView.setVisibility(View.GONE);


                            }
                            ll_bottom.setVisibility(View.GONE);
                            mDrawerLayout.closeDrawers();


                        }
                    } else if (i == 4) {
                        {
                            searchMenuItem.setVisible(false);
                            searchMenuItem.getIcon().setVisible(false, true);

                            eventBundle.putString("segid", itemLocalId4[0] + "");
                            eventBundle.putBoolean("interest", false);
                            eventBundle.putString("location", "");
                            eventBundle.putBoolean("latlon", false);
                            placeBundle.putBoolean("latlon", true);
                            placeBundle.putString("location", "");
                            placeBundle.putString("segid", itemLocalId4[0] + "");
                            segmentBundle.putString("segid", itemLocalId4[0] + "");
                            segmentBundle.putBoolean("clear", true);
                            titlesList.clear();
                            titlesList.add("Events");
                            titlesList.add("Places");
                            titlesList.add("Followers");
                            //  getActionBar().setCustomView(R.layout.actionbar_segment);
                            getActionBar().setDisplayShowTitleEnabled(false);
                            getActionBar().setDisplayShowCustomEnabled(true);
                            LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                            View header = inflater.inflate(R.layout.actionbar_segment, null);

                            //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                            TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                            ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                            iv.setBackgroundResource(childIm4[0]);
                            tv.setText(headerData.get(4));
                            android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                                    ActionBar.LayoutParams.MATCH_PARENT);
                            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                            getActionBar().setCustomView(header, layoutParams);
                            //getActionBar().setTitle(childLocaldata.get(i2));
                            pagerAdapter.notifyDataSetChanged();
                            if (mListView.getVisibility() == View.VISIBLE) {
                                mListView.setVisibility(View.GONE);


                            }
                            ll_bottom.setVisibility(View.GONE);
                            mDrawerLayout.closeDrawers();


                        }
                    }
                }
                switch (i) {
                    case 0:
                        mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3000));
                        break;
                    case 1:
                        mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
                    case 2:
                        mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
                    case 3:
                        mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
                        break;
                }
                lastExpandedPosition = i;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                if (lastExpandedPosition != -1)
                    mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 625));
            }
        });

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                int itemId = 0;
                int imgId = 0;
                switch (i) {
                    case 2:
                        itemId = itemLocalId2[0];
                        imgId = childIm2[0];
                        break;
                    case 3:
                        itemId = itemLocalId3[0];
                        imgId = childIm3[0];
                        break;
                    case 4:
                        itemId = itemLocalId4[0];
                        imgId = childIm4[0];
                        break;
                }
                if (i > 1) {

                    searchMenuItem.setVisible(false);
                    searchMenuItem.getIcon().setVisible(false,true);
                    eventBundle.putString("segid", itemId + "");
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("clear", true);
                    placeBundle.putString("segid", itemId + "");
                    placeBundle.putBoolean("clear", true);
                    segmentBundle.putString("segid", itemId + "");
                    segmentBundle.putBoolean("clear", true);
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    titlesList.add("Followers");

                    getActionBar().setDisplayShowTitleEnabled(false);
                    getActionBar().setDisplayShowCustomEnabled(true);
                    LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                    View header = inflater.inflate(R.layout.actionbar_segment, null);

                    //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                    TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                    tv.setText(headerData.get(i));
                    ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                    iv.setBackgroundResource(imgId);
                    android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                    getActionBar().setCustomView(header, layoutParams);
                    // getActionBar().setTitle(childData.get(i2));
                    pagerAdapter.notifyDataSetChanged();

                    if (mListView.getVisibility() == View.VISIBLE) {
                        mListView.setVisibility(View.GONE);

                    }
                    ll_bottom.setVisibility(View.GONE);
                    EventsFragment.cbPlan.setChecked(true);
                    EventsFragment.cbEvents.setChecked(true);
                    mDrawerLayout.closeDrawers();
                }
                return false;
            }
        });

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                searchMenuItem.setVisible(false);
                searchMenuItem.getIcon().setVisible(false, true);
                if (i == 0) {

                    eventBundle.putString("segid", itemId[i2] + "");
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("clear", true);
                    eventBundle.putString("location", "");
                    eventBundle.putBoolean("latlon", false);
                    placeBundle.putBoolean("latlon", true);
                    placeBundle.putString("location", "");
                    placeBundle.putString("segid", itemId[i2] + "");
                    placeBundle.putBoolean("clear", true);
                    segmentBundle.putString("segid", itemId[i2] + "");
                    segmentBundle.putBoolean("clear", true);
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    titlesList.add("Followers");

                    getActionBar().setDisplayShowTitleEnabled(false);
                    getActionBar().setDisplayShowCustomEnabled(true);
                    LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                    View header = inflater.inflate(R.layout.actionbar_segment, null);

                    //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                    TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                    tv.setText(childData.get(i2));
                    ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                    iv.setBackgroundResource(childImg[i2]);
                    android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                    getActionBar().setCustomView(header, layoutParams);
                    // getActionBar().setTitle(childData.get(i2));
                    pagerAdapter.notifyDataSetChanged();

                    if (mListView.getVisibility() == View.VISIBLE) {
                        mListView.setVisibility(View.GONE);

                    }
                    ll_bottom.setVisibility(View.GONE);
                  //  Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                  //  ll_bottom.setVisibility(View.GONE);
                    EventsFragment.cbPlan.setChecked(true);
                    EventsFragment.cbEvents.setChecked(true);
                    mDrawerLayout.closeDrawers();

                } else if (i == 1) {
                    eventBundle.putString("segid", itemLocalId1[i2] + "");
                    eventBundle.putBoolean("interest", false);
                    eventBundle.putBoolean("clear",true);
                    eventBundle.putString("location", "");
                    eventBundle.putBoolean("latlon", false);
                    placeBundle.putBoolean("latlon", true);
                    placeBundle.putString("location", "");
                    placeBundle.putString("segid", itemLocalId1[i2] + "");
                    placeBundle.putBoolean("clear", true);
                    segmentBundle.putString("segid", itemLocalId1[i2] + "");
                    segmentBundle.putBoolean("clear", true);
                    titlesList.clear();
                    titlesList.add("Events");
                    titlesList.add("Places");
                    titlesList.add("Followers");
                    //  getActionBar().setCustomView(R.layout.actionbar_segment);
                    getActionBar().setDisplayShowTitleEnabled(false);
                    getActionBar().setDisplayShowCustomEnabled(true);
                    LayoutInflater inflater = LayoutInflater.from(PagerActivity.this);
                    View header = inflater.inflate(R.layout.actionbar_segment, null);

                    //Here do whatever you need to do with the view (set text if it's a textview or whatever)
                    TextView tv = (TextView) header.findViewById(R.id.tv_segment);
                    ImageView iv = (ImageView) header.findViewById(R.id.iv_segment);
                    iv.setBackgroundResource(childImg1[i2]);
                    tv.setText(childLocaldata.get(i2));
                    android.app.ActionBar.LayoutParams layoutParams = new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
                    getActionBar().setCustomView(header, layoutParams);
                    //getActionBar().setTitle(childLocaldata.get(i2));
                    pagerAdapter.notifyDataSetChanged();
                    if (mListView.getVisibility() == View.VISIBLE) {
                        mListView.setVisibility(View.GONE);


                    }
                 //   ll_bottom.setVisibility(View.GONE);
                    mDrawerLayout.closeDrawers();
                    ll_bottom.setVisibility(View.GONE);

                } else if (i == 2) {

                } else if (i == 3) {

                } else if (i == 4) {

                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawerLayout.closeDrawers();
        //  Toast.makeText(PagerActivity.this,"pause",Toast.LENGTH_SHORT).show();
       /* if(pref.getBoolean("logout",false)){
            startActivity(new Intent(PagerActivity.this,StartActivity.class));
            finish();
        }*/

    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        if (eventBundle.getBoolean("interest") || eventBundle.getString("segid").length() > 0 || eventBundle.getString("location").length() > 0) {
            onTouch = false;
            searchMenuItem.setVisible(true);
            searchMenuItem.getIcon().setVisible(true, true);
            eventBundle.putString("segid", "");
            eventBundle.putBoolean("interest", false);
            eventBundle.putBoolean("clear", true);
            eventBundle.putString("location", "");
            eventBundle.putBoolean("latlon", false);
            placeBundle.putBoolean("latlon", true);
            placeBundle.putString("location", "");
            placeBundle.putString("segid", "");
            placeBundle.putBoolean("clear", true);
            placeBundle.putBoolean("interest", false);
            segmentBundle.putString("segid", "");
            segmentBundle.putBoolean("clear", true);
            titlesList.clear();
            titlesList.add("Events");
            titlesList.add("Places");
            pagerAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawers();
            getActionBar().setTitle("");

            getActionBar().setDisplayShowCustomEnabled(false);
           ll_bottom.setVisibility(View.VISIBLE);
          //  spinner.setVisibility(View.VISIBLE);
            //spinner1.setVisibility(View.GONE);
            ivSpinner.setBackground(null);

            spinner1.setSelection(0);

        } else {
            super.onBackPressed();
        }

    }
}
