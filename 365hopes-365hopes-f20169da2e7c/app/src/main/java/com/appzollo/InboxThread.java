package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appzollo.classes.CircularImageView;
import com.appzollo.classes.Followers;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.Messages;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;



public class InboxThread extends Activity implements OnPostCompleteListener, OnLoadCompleteListener {

    private String url= "";
     private ProgressDialog dialog;
    private TextView noFollowers;
    Messages message;
    LinearLayout llScroll;
    EditText etMsg;
    Button btSend;
     String userId1;
    String userId2;
    String uId;
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_thread);
         getActionBar().setDisplayHomeAsUpEnabled(true);
        llScroll = (LinearLayout)findViewById(R.id.ll_scroll);
    etMsg = (EditText)findViewById(R.id.etMsg);
        btSend = (Button)findViewById(R.id.bt_send);
       userId1 = getIntent().getExtras().getString("sender");
        userId2 = getIntent().getExtras().getString("receiver");
        getActionBar().setTitle(getIntent().getExtras().getString("title"));
        etMsg.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        if(!userId1.equals(CommonsUtils.getPrefString(InboxThread.this,"userid"))){
            uId = userId1;
        }else{
            uId = userId2;
        }

        url = "http://www.365hops.com/webservice/controller.php?Servicename=getUsersConversation&userid1="+uId+"&userid2="+CommonsUtils.getPrefString(InboxThread.this,"userid");
        PostDataToServer post = new PostDataToServer(this);
        Log.d("url", url);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                String path ="http://www.365hops.com/webservice/controller.php?%20Servicename=sendNewMessage&to="+uId+"&userid="+CommonsUtils.getPrefString(InboxThread.this,"userid")+"&message="+etMsg.getText().toString().replace(" ","%20");
               Log.d("urlsend", path);
                PostDataToServer post = new PostDataToServer(InboxThread.this);
                post.execute(path);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout  llMsg1 = new LinearLayout(InboxThread.this);
                llMsg1.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout  llContent = new LinearLayout(InboxThread.this);
                llContent.setOrientation(LinearLayout.VERTICAL);

                TextView tvMsg = new TextView(InboxThread.this);
                TextView tvTime = new TextView(InboxThread.this);
                CircularImageView iv = new CircularImageView(InboxThread.this);
                //  iv.getLayoutParams().height = 200;
                //  iv.getLayoutParams().width = 200;
                iv.setMinimumHeight(82);
                iv.setMinimumWidth(82);
                tvMsg.setText(etMsg.getText().toString());
                tvTime.setText("0 seconds ago");
                //    llMsg1.addView(iv);
                llContent.addView(tvMsg);
                llContent.addView(tvTime);
                    llMsg1.setGravity(Gravity.RIGHT);

                    llContent.setBackground(new ColorDrawable(0xFFACC0C6));
                    llContent.setPadding(30,30,30,30);

                llMsg1.addView(llContent,params1);
                llMsg1.setPadding(0, 30, 10, 30);
                llScroll.addView(llMsg1, params);
               // llScroll.removeAllViews();
                //PostDataToServer post1 = new PostDataToServer(InboxThread.this);
                //post1.execute(url);
               // llScroll.scrollTo(0,llScroll.getBottom());
                etMsg.setText("");

            }
        });

            post.execute(url);


        dialog = ProgressDialog.show(InboxThread.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inbox_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent intent=new Intent();
            intent.putExtra("edit", "1");
            if(check)
            setResult(RESULT_OK, intent);
            else
                setResult(RESULT_CANCELED, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("edit", "1");
        if(check)
            setResult(RESULT_OK, intent);
        else
            setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
        //finish();
    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof ImageView) {

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

    @Override
    public void onPostComplete(String json) {
        dialog.dismiss();

        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                JSONArray dataArray = jObj.getJSONArray("data");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for(int i=dataArray.length()-1 ;i>-1;i--){
                    JSONObject dataObj = dataArray.getJSONObject(i);

                    LinearLayout  llMsg1 = new LinearLayout(this);
                    llMsg1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout  llContent = new LinearLayout(this);
                    llContent.setOrientation(LinearLayout.VERTICAL);

                    TextView tvMsg = new TextView(this);
                    TextView tvTime = new TextView(this);
                    CircularImageView iv = new CircularImageView(this);
                  //  iv.getLayoutParams().height = 200;
                  //  iv.getLayoutParams().width = 200;
                    iv.setMinimumHeight(82);
                    iv.setMinimumWidth(82);
                    LoadProfileImageAsync task = new LoadProfileImageAsync(iv, InboxThread.this);
                    task.execute(dataObj.getString("sender_img"));
                       // Log.d("msg",dataObj.getString("message"));
                        tvMsg.setText(dataObj.getString("message"));
                    tvTime.setText(dataObj.getString("sendtime"));
                //    llMsg1.addView(iv);
                        llContent.addView(tvMsg);
                    llContent.addView(tvTime);
                    if(dataObj.getString("sender_id").equals(CommonsUtils.getPrefString(this, "userid"))){
                        llMsg1.setGravity(Gravity.RIGHT);

                       llContent.setBackground(new ColorDrawable(0xFFACC0C6));
                        llContent.setPadding(30,30,30,30);
                    }else{
                        llMsg1.setGravity(Gravity.LEFT);
                        llContent.setPadding(30,30,30,30);
                           llMsg1.addView(iv);
                        llContent.setBackground(new ColorDrawable(0xFFFFFFFF));
                    }
                    llMsg1.addView(llContent,params1);
                    llMsg1.setPadding(0, 30, 10, 30);
                    llScroll.addView(llMsg1, params);
                }


            } catch (Exception e) {

            }

        }
    }
}
