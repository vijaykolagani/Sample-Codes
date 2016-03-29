package com.appzollo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class SettingsActivity extends Activity {
    SharedPreferences pref;
    LinearLayout ll_howitworks,ll_faq,ll_privacy,ll_terms,ll_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pref = getSharedPreferences("com.appzollo", Context.MODE_PRIVATE);
        ll_howitworks = (LinearLayout) findViewById(R.id.ll_howitworks);
        ll_faq = (LinearLayout) findViewById(R.id.ll_faq);
        ll_privacy = (LinearLayout) findViewById(R.id.ll_privacy);
        ll_terms = (LinearLayout) findViewById(R.id.ll_terms);
        ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout llSignout = (LinearLayout)findViewById(R.id.llSignOut);

        ll_howitworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,HowItWorks.class));
            }
        });
        ll_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,FAQ.class));
            }
        });
        ll_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,Privacy.class));
            }
        });
        ll_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(SettingsActivity.this,Terms.class));
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,Cancellation.class));
            }
        });
        llSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().putBoolean("success", false).commit();
                startActivity(new Intent(SettingsActivity.this,LoginActivity.class));
                finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
           startActivity(new Intent(SettingsActivity.this,PagerActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this,PagerActivity.class));
        finish();
    }
}
