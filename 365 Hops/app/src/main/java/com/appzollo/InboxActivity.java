package com.appzollo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.CircularImageView;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.utils.CommonsUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class InboxActivity extends Activity implements OnLoadCompleteListener {

    ListView listView;
    static String[] names= {};
    private JSONParser jParser;
    static ArrayList<ArrayList<String>> similar = new ArrayList<ArrayList<String>>();
    ProgressBar progress_main;
    Button bt_compose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        progress_main = (ProgressBar) findViewById(R.id.progressBar_main);
        listView = (ListView) findViewById(R.id.listView);
        bt_compose = (Button) findViewById(R.id.bt_compase);

        bt_compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InboxActivity.this, Compose.class).putExtra("userid",CommonsUtils.getPrefString(InboxActivity.this,"userid")).putExtra("type", 1).putExtra("others",true));

            }
        });

    getActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
                        // TODO Auto-generated method stub
                        startActivityForResult(new Intent(InboxActivity.this,InboxThread.class).putExtra("sender",similar.get(position).get(6)).putExtra("receiver", similar.get(position).get(7)).putExtra("title", similar.get(position).get(1)),1);
                       // Toast.makeText(getApplicationContext(),"You Selected Item "+Integer.toString(position), Toast.LENGTH_LONG).show();
                    }
                }
        );

        GetSummaryTask gst = new GetSummaryTask();
        gst.execute("http://www.365hops.com/webservice/controller.php?Servicename=getinboxUsers&userid="+CommonsUtils.getPrefString(this,"userid"));
    }


    public class GetSummaryTask extends AsyncTask<String, Void, String> {

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

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            content, "UTF8"), 8);


                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    content.close();


//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    //String json = jParser.getJSONFromUrl(urls[0]);
                    // Gson gson = new Gson();

                    //eventDetailStickers = gson.fromJson(json, EventDetailSticker.class);


                    JSONObject jObj = new JSONObject(sb.toString());
                        JSONArray data = jObj.getJSONArray("data");
                    Log.d("kkkkkkkkkkkkkkkkk",data.toString());
                    similar.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            ArrayList<String> info = new ArrayList<String>();
                            info.add(obj.getString("user_id"));
                            info.add(obj.getString("user_fullname"));
                            info.add(obj.getString("user_img"));
                            info.add(obj.getString("location"));
                            info.add(obj.getString("total_msg"));
                            info.add(obj.getString("message_id"));
                            info.add(obj.getString("sender_id"));
                            info.add(obj.getString("reciever_id"));
                            info.add(obj.getString("message"));
                            info.add(obj.getString("send_date"));
                            info.add(obj.getString("sendtime"));
                            info.add(obj.getString("message_status"));
                            info.add(obj.getString("sender_name"));
                            info.add(obj.getString("sender_img"));
                            info.add(obj.getString("reciever_name"));
                            info.add(obj.getString("reciever_img"));
                            info.add(obj.getString("msg_type"));
                            similar.add(info);


                        }

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e("Inbox", "Failed to parse JSON due to: " + ex);
                    failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("Inbox", "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {


         /*  for (int j = 1; j < similar.size(); j++) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.item__list_inbox, null);

              TextView  tv_message = (TextView) addView.findViewById(R.id.tv_details);
               TextView tv_similar_name = (TextView) addView.findViewById(R.id.tv_name);
               TextView tv_date = (TextView) addView.findViewById(R.id.tv_date);
               CircularImageView iv_user = (CircularImageView) addView.findViewById(R.id.imageView);

               LinearLayout ll_container = (LinearLayout) findViewById(R.id.ll_container);
               ll_container.addView(addView);

                tv_message.setText( similar.get(j).get(8) + "");
                tv_similar_name.setText(similar.get(j).get(1) + "");
               tv_date.setText(similar.get(j).get(10)+"");
               LoadProfileImageAsync task = new LoadProfileImageAsync(iv_user, InboxActivity.this);
               task.execute(similar.get(j).get(2));

               *//* Drawable d = new BitmapDrawable(getResources(), bitmap.get(j));
                iv_similar.setBackground(d);*//*

            }
         */  progress_main.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new EfficientAdapter(InboxActivity.this));
        }

        @Override
        protected void onPreExecute() {


        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inbox, menu);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public EfficientAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return  similar.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item__list_inbox, null);
                holder = new ViewHolder();
                holder.  tv_message = (TextView) convertView.findViewById(R.id.tv_details);
                holder. tv_similar_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder. tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder. iv_user = (CircularImageView) convertView.findViewById(R.id.imageView);
                holder.ivType = (ImageView)convertView.findViewById(R.id.ivType);




                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


           holder. tv_message.setText( similar.get(position).get(8) + "");
            holder.tv_similar_name.setText(similar.get(position).get(1) + "");
            holder.tv_date.setText(similar.get(position).get(10)+"");
            if(similar.get(position).get(16).equals("Receive")){
                holder.ivType.setBackgroundResource(R.drawable.inbox_icon);
            }else{
                holder.ivType.setBackgroundResource(R.drawable.outbox_icon);
            }
            LoadProfileImageAsync task = new LoadProfileImageAsync(holder.iv_user, InboxActivity.this);
            task.execute(similar.get(position).get(2));


            return convertView;
        }

        class ViewHolder {


            TextView tv_message;
            TextView   tv_similar_name;
            TextView tv_date;
            CircularImageView iv_user;
            ImageView ivType;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

      if(resultCode==Activity.RESULT_CANCELED){

      }else{
          GetSummaryTask gst = new GetSummaryTask();
          gst.execute("http://www.365hops.com/webservice/controller.php?Servicename=getinboxUsers&userid="+CommonsUtils.getPrefString(this,"userid"));
            similar.clear();
      }
    }
}
