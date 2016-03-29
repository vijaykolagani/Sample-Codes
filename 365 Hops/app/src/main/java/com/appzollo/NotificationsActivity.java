package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.Followers;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.Notifications;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.classes.RowItemPlace;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.profilefragments.PlanStickerClick;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class NotificationsActivity extends Activity implements OnLoadCompleteListener, OnPostCompleteListener {

    LinearLayout listView;

    String url = "http://www.365hops.com/webservice/controller.php?Servicename=getUserNotification&userid=";
    private ProgressDialog dialog;
    private TextView noFollowers;
    private Notifications notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        listView = (LinearLayout) findViewById(R.id.listView);
        noFollowers = (TextView) findViewById(R.id.tv_no_followers);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        String userId = CommonsUtils.getPrefString(this, "userid");
        url += userId;
        PostDataToServer post = new PostDataToServer(this);
        post.execute(url);
        dialog = ProgressDialog.show(NotificationsActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);

    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof RoundedImageView) {

            ((RoundedImageView) view).setImageBitmap(bitmap);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPostComplete(String json) {
        dialog.dismiss();
        if (json != null) {
            try {
                JSONObject jObj = new JSONObject(json);
                int status = jObj.getInt("success");
                if (status == 0) {
                    String message = jObj.getString("message");
                    noFollowers.setVisibility(View.VISIBLE);
                } else if (status == 1) {
                    noFollowers.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    notifications = gson.fromJson(json, Notifications.class);
                    if (notifications != null
                            && notifications.getData() != null
                            && notifications.getData().size() > 0) {
                        Vector<Row> rowData = new Vector<Row>();
                        for (int i = 0; i < notifications.getData().size(); i++) {
                            final Notifications.Data data = notifications.getData().get(i);
                            if (data != null) {
                                View convertView = View.inflate(this, R.layout.item_list_feed, null);
                                TextView feed;
                                TextView date;
                                RoundedImageView imageView;
                                LinearLayout ll;

                                ll = (LinearLayout) convertView.findViewById(R.id.ll);

                                feed = (TextView) convertView
                                        .findViewById(R.id.tv_feed);
                                date = (TextView) convertView.findViewById(R.id.tv_date);
                                imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon);

                                ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(data.getPpostname() != null) {
                                            if (data.getPpostname().toString().equalsIgnoreCase("Place")) {
                                                Intent i = new Intent(getApplicationContext(), PlaceToVisitActivity.class);
                                                i.putExtra("id", data.getPostid());
                                                i.putExtra("user_id", data.getSender_id());
                                                i.putExtra("type", "placetoVisit");
                                                startActivity(i);
                                                Log.d("lkl", data.getPostid() + "," + CommonsUtils.getPrefString(NotificationsActivity.this, "userid"));

                                            } else if (data.getPpostname().toString().equalsIgnoreCase("Plan")) {
                                                Intent intent = new Intent(getApplicationContext(), PlanStickerClick.class);
                                                intent.putExtra("id", data.getPostid());
                                                intent.putExtra("userid", data.getSender_id());
                                                startActivity(intent);
                                                Log.d("lkl", data.getPostid() + "," + CommonsUtils.getPrefString(NotificationsActivity.this, "userid"));

                                            } else if (data.getPpostname().toString().equalsIgnoreCase("Trip")) {
                                                Intent i = new Intent(getApplicationContext(), EventClicked.class);
                                                i.putExtra("id", data.getPostid());
                                                i.putExtra("userid", data.getSender_id());
                                                startActivity(i);
                                                Log.d("lkl", data.getPostid() + "," + CommonsUtils.getPrefString(NotificationsActivity.this, "userid"));
                                            } else if (data.getPpostname().toString().equalsIgnoreCase("Follow")) {
                                                startActivity(new Intent(NotificationsActivity.this, ProfileActivity.class).putExtra("my", false).putExtra("userid", CommonsUtils.getPrefString(NotificationsActivity.this, "userid")));
                                                Log.d("lkl", data.getPostid() + "," + CommonsUtils.getPrefString(NotificationsActivity.this, "userid"));
                                            } else {

                                            }
                                        }

                                    }
                                });
                               // name.setText(data.getSender_name());
                                feed.setText(Html.fromHtml("<b>" + data.getSender_name() + "</b>" + data.getMessage().replace(data.getSender_name(), "")));
                                date.setText(data.getTime());

                                LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, NotificationsActivity.this);
                                load.execute(data.getSender_img());

                                listView.addView(convertView);

                            }
                        }
                    }
                }
            } catch (Exception e) {

            }

        }

    }

    public class CustomAdapter<T> extends ArrayAdapter<T> {

        Context context;
        private LayoutInflater mInflater;


        public CustomAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_list_feed, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ll = (LinearLayout) findViewById(R.id.ll);
            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_feed);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);

            final Row row = (Row) getItem(position);
                holder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),row.getName()+"",Toast.LENGTH_SHORT).show();
                    }
                });
            holder.name.setText(row.getName());
            holder.date.setText(row.getDate());
            holder.imageView = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon);

            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, NotificationsActivity.this);
            load.execute(row.getImage());


            return convertView;


        }

        class ViewHolder {
            TextView name;
            TextView date;
            RoundedImageView imageView;
            LinearLayout ll;
        }

    }

}

class Row {
    private String name;
    private String date;
    private String image;

    public Row(String name, String date, String image) {
        this.name = name;
        this.date = date;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
