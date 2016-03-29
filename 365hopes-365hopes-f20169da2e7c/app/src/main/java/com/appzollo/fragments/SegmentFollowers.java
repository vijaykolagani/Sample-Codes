package com.appzollo.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appzollo.ProfileActivity;
import com.appzollo.R;
import com.appzollo.classes.LoadInterestIcons;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.OnLoadInterestListener;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.classes.SegmentFollowersPojo;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
/**
 * Created by saikrishna on 18/11/14.
 */
public class SegmentFollowers extends Fragment implements OnLoadCompleteListener, OnPostCompleteListener,OnLoadInterestListener {

    String url = "http://www.365hops.com/webservice/controller.php?Servicename=getActivityProfile&type=Followers&segmentid=";
    LinearLayout listView;
    private ProgressBar progress_main;
    private TextView noFollowers;
    private SegmentFollowersPojo events;
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> intList = new ArrayList<String>();
    boolean interests = false;
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
                view = inflater.inflate(R.layout.fragment_segemnt_follwers, null, false);
                listView = (LinearLayout) view.findViewById(R.id.segment_followers);
                noFollowers = (TextView) view.findViewById(R.id.tv_no_followers);

                String userId = CommonsUtils.getPrefString(getActivity(), "profile_userid");
//        String userId = this.getArguments().getString("userid");

                /*url = url + this.getArguments().getString("segid") + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");
                PostDataToServer post = new PostDataToServer(this);
                post.execute(url);*/
               /* dialog = ProgressDialog.show(getActivity(), "",
                        "Loading. Please wait...", true);
                dialog.setCancelable(true);*/
            }

            progress_main = (ProgressBar)view. findViewById(R.id.progressBar_main);
            url = url + this.getArguments().getString("segid") + "&userid=" + CommonsUtils.getPrefString(getActivity(), "userid");

            if (events == null || getArguments().getBoolean("clear")) {
                PostDataToServer post = new PostDataToServer(this);
                post.execute(url);
                Log.d("segurl",url);
                listView.removeAllViews();
                progress_main.setVisibility(View.VISIBLE);
                getArguments().putBoolean("clear", false);
            }
        }catch (InflateException ex){

            }

        return view;
    }


    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof ImageView) {
            if (bitmap != null){
                BitmapDrawable ob = new BitmapDrawable(bitmap);
                ((ImageView) view).setBackground(ob);
            }

        }
    }

    @Override
    public void onPostComplete(String json) {
        progress_main.setVisibility(View.GONE);
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
                        events = gson.fromJson(json, SegmentFollowersPojo.class);
                        if (events != null
                                && events.getData() != null
                                && events.getData().size() > 0) {
                            listView.removeAllViews();
                            Vector<EventRow> rowData = new Vector<EventRow>();
                            for (int i = 0; i < events.getData().size(); i++) {
                                final SegmentFollowersPojo.Data data = events.getData().get(i);
                                if (data != null) {

                                    View convertView = View.inflate(getActivity(), R.layout.item_segment_followers, null);

                                    TextView name = (TextView) convertView.findViewById(R.id.tv_name);

                                    TextView location = (TextView) convertView.findViewById(R.id.tv_location);

                                    TextView one = (TextView) convertView.findViewById(R.id.tv_one);
                                    TextView two = (TextView) convertView.findViewById(R.id.tv_two);

                                    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

                                    LinearLayout llInterests = (LinearLayout) convertView.findViewById(R.id.llInterests);

                                    name.setText(data.getUser_fullname());
                                   // location.setText(data.getUser_city());
                                   if(data.getUser_city()!=null)
                                          location.setText(data.getUser_city());
                                    else
                                        location.setText("");
                                    one.setText("Followers "+data.getFollowers());
                                    two.setText("Following "+data.getFollows());

                                    LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, SegmentFollowers.this);
                                    load.execute(data.getUser_img());
                                    List<String> list = new ArrayList<String>();
                                   // LoadInterestIcons post = new LoadInterestIcons(llInterests, SegmentFollowers.this, true, list);
                                   // post.execute("http://www.365hops.com/webservice/controller.php?Servicename=getUserInterests&userid="+data.getUser_id());


                                    name.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getActivity(), ProfileActivity.class);
                                            i.putExtra("my", false);
                                            i.putExtra("userid", data.getUser_id());
                                            i.putExtra("recieverid", CommonsUtils.getPrefString(getActivity(), "userid"));
                                            startActivity(i);
                                        }
                                    });

                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getActivity(), ProfileActivity.class);
                                            i.putExtra("my", false);
                                            i.putExtra("userid", data.getUser_id());
                                            i.putExtra("recieverid", CommonsUtils.getPrefString(getActivity(), "userid"));
                                            startActivity(i);
                                        }
                                    });




                                    listView.addView(convertView);
                                }
                            }
                        }
                    }
                } catch (Exception e) {

                }

        }else{
            noFollowers.setText("No Followers");
        }
    }




    @Override
    public void onInterestLoadComplete(LinearLayout view, ArrayList<Bitmap> list) {
        for(int j=0;j<list.size();j++) {
            Log.d("size",list.size()+"");
            ImageView iv = new ImageView(getActivity());
            iv.setImageBitmap(list.get(j));
            iv.setPadding(10,10,10,10);
            view.addView(iv);
        }
    }

    public class CustomAdapter<T> extends ArrayAdapter<T> {

        private LayoutInflater mInflater;


        public CustomAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_segment_followers, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.location = (TextView) convertView.findViewById(R.id.tv_location);
            holder.one = (TextView) convertView.findViewById(R.id.tv_one);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            EventRow row = (EventRow) getItem(position);

            holder.name.setText(row.getName());
            holder.location.setText(row.getLocation());
            holder.one.setText(row.getOne());
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(),ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", ids.get(position));
                    i.putExtra("recieverid",CommonsUtils.getPrefString(getActivity(), "userid"));
                    startActivity(i);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(),ProfileActivity.class);
                    i.putExtra("my", false);
                    i.putExtra("userid", ids.get(position));
                    i.putExtra("recieverid",CommonsUtils.getPrefString(getActivity(), "userid"));
                    startActivity(i);
                }
            });


            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, SegmentFollowers.this);
            load.execute(row.getImage());

            return convertView;

        }

        class ViewHolder {
            TextView name;
            TextView location;
            TextView one;
            ImageView imageView;
        }
    }

}

class  EventRow {
    private String name;
    private String location;
   private String one;
     private String image;

    EventRow(String name, String location, String one,  String image) {
        this.name = name;
        this.location = location;
        this.one = one;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
