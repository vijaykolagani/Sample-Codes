package com.appzollo.profilefragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appzollo.PlaceToVisitActivity;
import com.appzollo.R;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.Notifications;
import com.appzollo.classes.Places;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.RoundedImageView;
import com.appzollo.classes.RowItemPlace;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONObject;


public class PlacesFragment extends Fragment implements OnLoadCompleteListener, OnPostCompleteListener {

    String url = "http://www.365hops.com/webservice/controller.php?Servicename=getUserPlaces&userid=";
    LinearLayout listView;
    private ProgressDialog dialog;
    private TextView noFollowers;
    private Places places;
    String userId;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            if (parent != null)
                parent.removeAllViews();

        }
        try{
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_profile_places, null, false);
                listView = (LinearLayout) view.findViewById(R.id.listView);
                noFollowers = (TextView) view.findViewById(R.id.tv_no_followers);
               // String userId = CommonsUtils.getPrefString(getActivity(), "profile_userid");
                userId = CommonsUtils.getPrefString(getActivity(), "profileid");
                url += userId;
                Log.d("palcesurl",url);
                PostDataToServer post = new PostDataToServer(this);
                post.execute(url);
                dialog = ProgressDialog.show(getActivity(), "",
                        "Loading. Please wait...", true);
                dialog.setCancelable(true);
            }
        }catch (InflateException ex){

        }


        return view;
    }


    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof ImageView) {
            if (bitmap != null)
                ((ImageView) view).setImageBitmap(bitmap);
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
                    places = gson.fromJson(json, Places.class);
                    if (places != null
                            && places.getData() != null
                            && places.getData().size() > 0) {
                        Vector<Row> rowData = new Vector<Row>();
                        listView.removeAllViews();
                        for (int i = 0; i < places.getData().size(); i++) {
                          final  Places.Data  data = places.getData().get(i);
                            if (data != null) {
                                View convertView = View.inflate(getActivity(), R.layout.item_profile_palces, null);

                                TextView name = (TextView) convertView.findViewById(R.id.tv_name);;
                                TextView location = (TextView) convertView.findViewById(R.id.tv_location);;
                                TextView one = (TextView) convertView.findViewById(R.id.tv_one);;
                                TextView two = (TextView) convertView.findViewById(R.id.tv_two);;
                                TextView three = (TextView) convertView.findViewById(R.id.tv_three);;
                                TextView four = (TextView) convertView.findViewById(R.id.tv_four);;
                                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

                                name.setText(data.getName());
                                location.setText(data.getLocation());
                                one.setText(data.getPrice());
                                two.setText(data.getCurrency());
                                three.setText(data.getRoom_type());
                                four.setText(data.getStay_type());

                                convertView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent i = new Intent(getActivity(), PlaceToVisitActivity.class);
                                        i.putExtra("id", data.getId());
                                        i.putExtra("user_id", userId);
                                        i.putExtra("places_edit","1");
                                        startActivity(i);
                                        getActivity().finish();

                                    }
                                });
                                LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, PlacesFragment.this);
                                load.execute(data.getImage());

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

        private LayoutInflater mInflater;


        public CustomAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_profile_palces, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.location = (TextView) convertView.findViewById(R.id.tv_location);
            holder.one = (TextView) convertView.findViewById(R.id.tv_one);
            holder.two = (TextView) convertView.findViewById(R.id.tv_two);
            holder.three = (TextView) convertView.findViewById(R.id.tv_three);
            holder.four = (TextView) convertView.findViewById(R.id.tv_four);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            Row row = (Row) getItem(position);

            holder.name.setText(row.getName());
            holder.location.setText(row.getLocation());
            holder.one.setText(row.getOne());
            holder.two.setText(row.getTwo());
            holder.three.setText(row.getThree());
            holder.four.setText(row.getFour());

            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, PlacesFragment.this);
            load.execute(row.getImage());

            return convertView;

        }

        class ViewHolder {
            TextView name;
            TextView location;
            TextView one;
            TextView two;
            TextView three;
            TextView four;
            ImageView imageView;
        }
    }

}

class Row {
    private String name;
    private String location;
    private String one;
    private String two;
    private String three;
    private String four;
    private String image;

    Row(String name, String location, String one, String two, String three, String four, String image) {
        this.name = name;
        this.location = location;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

