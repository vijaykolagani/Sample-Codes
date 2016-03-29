package com.appzollo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.JSONParser;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.ScrollViewExt;
import com.appzollo.classes.ScrollViewListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity implements ScrollViewListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnPostCompleteListener {

    ImageView iv_google,iv_fb,iv_mail;
    private String[] day = { "Date", "01", "02", "03",
            "04", "05", "06", "07",
            "08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24",
            "25","26","27","28","29","30","31"};
    private String[] month = { "Month", "JAN", "FEB", "MAR",
            "APR", "MAY", "JUN", "JUL",
            "AUG","SEP","OCT","NOV","DEC"};
    private String[] year = {"Year" , "1980", "1981",
            "1982", "1983", "1984", "1985",
            "1986","1987","1988","1989","1990", "1991", "1992", "1993",
            "1994", "1995", "1996", "1997",
            "1998","1999","2000"};
    Button bt_signup;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    LinearLayout llConnect,llSocial;
    ScrollViewExt scroll;
    SharedPreferences pref;

    private ConnectionResult mConnectionResult;
    private boolean mSignInClicked;
    private JSONParser jParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iv_google = (ImageView) findViewById(R.id.iv_google);
        iv_fb = (ImageView) findViewById(R.id.iv_fb);
        iv_mail = (ImageView) findViewById(R.id.iv_email);
        llConnect = (LinearLayout)findViewById(R.id.ll_connect);
        llSocial = (LinearLayout)findViewById(R.id.ll_social);
        pref = getSharedPreferences("com.appzollo", Context.MODE_PRIVATE);
        pref.edit().putBoolean("sucess",false).commit();
        llConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llSocial.isShown())
                    llSocial.setVisibility(View.GONE);
                else
                    llSocial.setVisibility(View.VISIBLE);
                // setLayoutAnim_slideup(llSocial,LoginActivity.this);
            }
        });
        scroll = (com.appzollo.classes.ScrollViewExt)findViewById(R.id.scrollView);
        scroll.setScrollViewListener(this);
// Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  startActivity(new Intent(LoginActivity.this,PagerActivity.class));
                mSignInClicked = true;
                mGoogleApiClient.connect();
            }
        });

        /*iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,PagerActivity.class));
            }
        });*/

        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb_login();
            }
        });
        showHashKey(getApplicationContext());
        iv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }

    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.appzollo", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        // We take the last son in the scrollview
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        // Toast.makeText(this,"on scroll",Toast.LENGTH_SHORT).show();
        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            llSocial.setVisibility(View.VISIBLE);
            //   Toast.makeText(this,"end",Toast.LENGTH_SHORT).show();
        }
    }


    public void fb_login(){/*
        List<String> permissions = new ArrayList<String>();
        permissions.add("email");
        Toast.makeText(getApplicationContext(),"fb",Toast.LENGTH_SHORT).show();
        // start facebook login
        Session.openActiveSession(this, true, new Session.StatusCallback()
        {
            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception)
            {
                if (session.isOpened())
                {
                    // make request to the /me API to get user details
                    Request.newMeRequest(session, new Request.GraphUserCallback()
                    {

                        // callback after Graph API response with user
                        // object
                        @Override
                        public void onCompleted(GraphUser user, Response response)
                        {
                            if (user != null)
                            {
                                // To display facebook user name of the user
                                // text.setText("Hello " + user.getName() + "!");
                                String servicename = "facebook";
                                String username = user.getName();
                                String dob = user.getBirthday();
                                String location = user.getLocation().toString();
                                String id = user.getId();
                                String email = user.getProperty("email").toString();
                                String gender = user.asMap().get("gender").toString();
                            *//*    Map<String, String> map = new HashMap<String, String>();
                                map.put("user", username);
                                map.put("dob", dob);
                                map.put("email", "email");
                                map.put("gender", gender);
                                CommonsUtils.putPrefStrings(LoginActivity.this, map);
                                GraphPlace photo = user.getLocation();
                                pref.edit().putBoolean("sucess",true).commit();
                                startActivity(new Intent(LoginActivity.this,PagerActivity.class));
                                finish();


*//*

                                Log.e("TAGGGGGG", "Name: " + username + ", plusProfile: "
                                        +   ", email: " + email
                                        + ", Image: " +","+dob);
                                String data = "http://www.365hops.com/webservice/controller.php?Servicename=LoginbyFacebook&facebookid="+id +
                                        "&name=" +username.replace(" ","%20")+
                                        "&dob=" +dob+
                                        "&email=" +email+
                                        "&gender=" +gender+
                                        "&photo=" +
                                        "&friends=" +
                                        "&Loginsource=Google" +
                                        "&location="+location;

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("loginby","google");
                                map.put("user", username);
                                map.put("dob", dob);
                                map.put("email", email);
                                map.put("photoUrl", "");
                                map.put("plusProfile", "");
                                map.put("location", location);
                                CommonsUtils.putPrefStrings(LoginActivity.this, map);
                                //  pref.edit().putBoolean("sucess",true).commit();
                                PostDataToServer postData = new PostDataToServer(LoginActivity.this);
                                postData.execute(data);



                            }
                        }
                    }).executeAsync();
                }

            }
        });
*/
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }


    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        getProfileInformation();
        mSignInClicked = false;
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String dob = currentPerson.getCurrentLocation();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                String location = currentPerson.getCurrentLocation();
                String id = currentPerson.getId();
                int sex = currentPerson.getGender();
                //  Toast.makeText(getApplicationContext(),personName+","+personGooglePlusProfile+""+email,Toast.LENGTH_SHORT).show();

                Log.e("TAGGGGGG", "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl+","+dob);
                String data = "http://www.365hops.com/webservice/controller.php?Servicename=LoginbyFacebook&facebookid="+id +
                        "&name=" +personName.replace(" ","%20")+
                        "&dob=" +dob+
                        "&email=" +email+
                        "&gender=" +sex+
                        "&photo=" +
                        "&friends=" +
                        "&loginsource=Google" +
                        "&location="+location;
            Log.d("urllll",data);
                Map<String, String> map = new HashMap<String, String>();
                map.put("loginby","google");
                map.put("user", personName);
                map.put("dob", dob);
                map.put("email", email);
                map.put("photoUrl", personPhotoUrl);
                map.put("plusProfile", personGooglePlusProfile);
                map.put("location", location);
                CommonsUtils.putPrefStrings(LoginActivity.this, map);
                //  pref.edit().putBoolean("sucess",true).commit();
                PostDataToServer postData = new PostDataToServer(LoginActivity.this);
                postData.execute(data);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /* Method to resolve any signin errors
    * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    private void showSignupDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.email_layout);
        final Spinner sp_day = (Spinner)dialog.findViewById(R.id.sp_date);
        final Spinner sp_month = (Spinner)dialog.findViewById(R.id.sp_month);
        final Spinner sp_year = (Spinner)dialog.findViewById(R.id.sp_year);
        final EditText mail = (EditText) dialog.findViewById(R.id.et_mail);
        final EditText pwd = (EditText) dialog.findViewById(R.id.et_password);
        final EditText name = (EditText) dialog.findViewById(R.id.et_name);
        Button signup = (Button) dialog.findViewById(R.id.bt_signup);

        ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, day);
        adapter_day
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(adapter_day);

        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, month);
        adapter_month
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(adapter_month);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, year);
        adapter_year
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(adapter_year);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getEditableText().toString();
                String passd = pwd.getEditableText().toString();
                String username = name.getEditableText().toString();

                String data = "http://www.365hops.com/webservice/controller.php?" +
                        "Servicename=UserRegistration&name=" + username + "&email=" + email + "&sex" +
                        "=2&pass=" + passd + "&confirmpass=" + passd + "&dob=2010-02-12";

                Map<String, String> map = new HashMap<String, String>();
                map.put("user", username);
                map.put("dob", "2010-02-12");
                map.put("email", email);
                CommonsUtils.putPrefStrings(LoginActivity.this, map);

                PostDataToServer posData = new PostDataToServer(LoginActivity.this);
                posData.execute(data);
                //    pref.edit().putBoolean("sucess",true).commit();
                dialog.dismiss();
            }
        });



        dialog.show();

    }

    private void showLoginDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.login);
        final EditText mail = (EditText) dialog.findViewById(R.id.et_mail);
        final EditText pwd = (EditText) dialog.findViewById(R.id.et_password);
        Button login = (Button) dialog.findViewById(R.id.bt_login);
        TextView signup = (TextView) dialog.findViewById(R.id.tv_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialog();
                dialog.dismiss();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getEditableText().toString();
                String passd = pwd.getEditableText().toString();

                PostDataToServer posData = new PostDataToServer(LoginActivity.this);
                String data = "http://www.365hops.com/webservice/controller.php?"
                        + "Servicename=checklogin&username=" + email + "&pass=" + passd;
                GetLoginDetails gld = new GetLoginDetails();
                jParser = new JSONParser();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("loginby","email");
                map.put("email",email);
                // gld.execute(data);
                CommonsUtils.putPrefStrings(getApplicationContext(),map);
                posData.execute(data);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onPostComplete(String json) {
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                int status = jObj.getInt("status");
                Toast.makeText(LoginActivity.this, status+"", Toast.LENGTH_SHORT).show();
                if (status == 0) {
                    String message = jObj.getString("message");
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                } else if (status == 1) {
                    if(CommonsUtils.getPrefString(getApplicationContext(),"loginby").equalsIgnoreCase("google")){

                        pref.edit().putBoolean("sucess", true).commit();

                        JSONObject c = jObj.getJSONObject("data");

                        Toast.makeText(LoginActivity.this, "use id"+c.getString("userid"), Toast.LENGTH_SHORT).show();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user", c.getString("username"));
                        map.put("userid", c.getString("userid"));
                        map.put("profileimage", c.getString("userimg"));
                        map.put("location",c.getString("location_full"));
                        map.put("lat",c.getString("lat"));
                        map.put("lon",c.getString("lon"));

                        CommonsUtils.putPrefStrings(LoginActivity.this, map);
                        startActivity(new Intent(LoginActivity.this, PagerActivity.class));
                        finish();
                    }else {
                        pref.edit().putBoolean("sucess", true).commit();

                        JSONArray array = jObj.getJSONArray("data");
                        JSONObject c = array.getJSONObject(0);

                        Toast.makeText(LoginActivity.this, c.getString("userid"), Toast.LENGTH_SHORT).show();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user", c.getString("username"));
                        map.put("userid", c.getString("userid"));
                        map.put("profileimage", c.getString("userimg"));
                        map.put("location",c.getString("location_full"));
                        CommonsUtils.putPrefStrings(LoginActivity.this, map);
                        startActivity(new Intent(LoginActivity.this, PagerActivity.class));
                        finish();
                    }
                }

            } catch (Exception e) {

            }
        }
    }
    class GetLoginDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                Log.d("url", args[0]);
                HttpPost post = new HttpPost(args[0]);

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
//
                    String json = jParser.getJSONFromUrl(args[0]);
                    // Gson gson = new Gson();

                    //eventDetailStickers = gson.fromJson(json, EventDetailSticker.class);

                    JSONObject jObj = new JSONObject(json);
                    if (jObj.getString("status").equalsIgnoreCase("1")) {
                        JSONArray jsonArray = jObj.getJSONArray("data");
                        JSONObject c = jsonArray.getJSONObject(0);

                        Toast.makeText(LoginActivity.this, c.getString("userid"), Toast.LENGTH_SHORT).show();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user", c.getString("username"));
                        map.put("userid", c.getString("userid"));
                        map.put("profileimage", c.getString("userimg"));
                        CommonsUtils.putPrefStrings(LoginActivity.this, map);

                    }

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.d("exc",ex.toString());
                }

            } catch (Exception ex) {
                Log.d("exc",ex.toString());
            }

            return null;

        }
        @Override
        protected void onPostExecute(String result) {
            startActivity(new Intent(LoginActivity.this,PagerActivity.class));
            finish();
        }
    }
}