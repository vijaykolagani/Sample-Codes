package com.appzollo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.appzollo.classes.Enquiry;
import com.appzollo.classes.Followers;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;


public class EnquiriesActivity extends Activity implements OnPostCompleteListener, OnLoadCompleteListener {
    private String url = "http://www.365hops.com/webservice/controller.php?Servicename=getUserReceivedQueries&userid=";

    LinearLayout listView;
    private Enquiry enquiry;
    private ProgressDialog dialog;
    private TextView noFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiries);
        listView = (LinearLayout) findViewById(R.id.listView);
        noFollowers = (TextView) findViewById(R.id.tv_no_followers);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        String userId = CommonsUtils.getPrefString(this, "userid");

        PostDataToServer post = new PostDataToServer(this);
        post.execute(url + userId);
        dialog = ProgressDialog.show(EnquiriesActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof RoundedImageView) {
            ((RoundedImageView) view).setImageBitmap(bitmap);
        }
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
                    enquiry = gson.fromJson(json, Enquiry.class);
                    if (enquiry != null
                            && enquiry.getData() != null
                            && enquiry.getData().size() > 0) {
                        Vector<EnquiryRow> rowData = new Vector<EnquiryRow>();
                        for (int i = 0; i < enquiry.getData().size(); i++) {
                            Enquiry.Data data = enquiry.getData().get(i);
                            if (data != null) {
                                View convertView = View.inflate(this, R.layout.item_list_enquiries, null);
                                TextView title;
                                TextView name;
                                TextView number;
                                TextView people;
                                TextView date;
                                TextView email;
                                RoundedImageView profilePic;

                                title = (TextView) convertView
                                        .findViewById(R.id.tv_title);
                                name = (TextView) convertView
                                        .findViewById(R.id.tv_name);
                                number = (TextView) convertView.findViewById(R.id.tv_number);
                                people = (TextView) convertView.findViewById(R.id.tv_people);
                                date = (TextView) convertView.findViewById(R.id.tv_date);
                                profilePic = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon);
                                email = (TextView) convertView.findViewById(R.id.tv_email);

                                title.setText(data.getActivityname());
                                name.setText(data.getName());
                                number.setText(data.getMobile());
                                people.setText(data.getPeoples());
                                date.setText(data.getDate());
                                email.setText(data.getEmail());

                                LoadProfileImageAsync load = new LoadProfileImageAsync(profilePic, EnquiriesActivity.this);
                                load.execute(data.getUserimage());

                                listView.addView(convertView);
                            }
                        }
                    }
                }
            } catch (Exception e) {

            }

        }
    }


    private class EfficientAdapter<T> extends ArrayAdapter<T> {
        private LayoutInflater mInflater;

        public EfficientAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_list_enquiries, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.people = (TextView) convertView.findViewById(R.id.tv_people);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.profilePic = (RoundedImageView) convertView.findViewById(R.id.iv_profile_icon);
            holder.email = (TextView) convertView.findViewById(R.id.tv_email);

            EnquiryRow row = (EnquiryRow) getItem(position);

            holder.title.setText(row.getTitle());
            holder.name.setText(row.getName());
            holder.number.setText(row.getNumber());
            holder.people.setText(row.getPeople());
            holder.date.setText(row.getDate());
            holder.email.setText(row.getEmail());

            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.profilePic, EnquiriesActivity.this);
            load.execute(row.getImage());
            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView name;
            TextView number;
            TextView people;
            TextView date;
            TextView email;
            RoundedImageView profilePic;
        }
    }
}

class EnquiryRow {
    private String title;
    private String name;
    private String number;
    private String people;
    private String date;
    private String image;
    private String email;

    public EnquiryRow(String title, String name, String number, String people, String date, String image, String email) {
        this.title = title;
        this.name = name;
        this.number = number;
        this.people = people;
        this.date = date;
        this.image = image;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
