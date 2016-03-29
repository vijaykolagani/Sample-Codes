package com.appzollo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.PostDataToServer;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MyCreditsActivity extends Activity implements OnPostCompleteListener {
    private String url = "http://www.365hops.com/webservice/controller.php?Servicename=availablecredits&userid=";
    private TextView credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credits);

        String userId = CommonsUtils.getPrefString(this, "userid");

        credits = (TextView) findViewById(R.id.tv_credits);

        PostDataToServer posData = new PostDataToServer(this);
        posData.execute(url + userId);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_credits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPostComplete(String json) {
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                int status = jObj.getInt("success");
                if (status == 1) {
                    String points = jObj.getString("points");
                    credits.setText("Rs " + points);
                } else {
                    credits.setText("Rs " + 0);
                }
            } catch (Exception e) {

            }

        }
    }
}
