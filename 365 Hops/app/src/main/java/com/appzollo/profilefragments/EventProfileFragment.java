package com.appzollo.profilefragments;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.EventClicked;
import com.appzollo.R;
import com.appzollo.classes.Events;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.PostDataToServer;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.interfaces.OnPostCompleteListener;
import com.appzollo.utils.CommonsUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

/**
 * Created by saikrishna on 18/11/14.
 */
public class EventProfileFragment extends Fragment implements OnLoadCompleteListener, OnPostCompleteListener {

    String url = "http://www.365hops.com/webservice/controller.php?Servicename=getUserEvents&userid=";
    LinearLayout listView;
    private ProgressDialog dialog;
    private TextView noFollowers;
    private Events events;
    String userId;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeAllViews();

        }
        try {
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_profile_events, null, false);
                listView = (LinearLayout) view.findViewById(R.id.listView);
                noFollowers = (TextView) view.findViewById(R.id.tv_no_followers);
                userId = CommonsUtils.getPrefString(getActivity(), "profileid");
                //String userId = this.getArguments().getString("userid");

                url += userId;
                Log.d("eventsurl", url);
                PostDataToServer post = new PostDataToServer(this);
                post.execute(url);
                dialog = ProgressDialog.show(getActivity(), "",
                        "Loading. Please wait...", true);
                dialog.setCancelable(true);

            }
        } catch (InflateException ex) {

        }

        return view;
    }


    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (view instanceof ImageView) {
            if (bitmap != null) {
                BitmapDrawable ob = new BitmapDrawable(bitmap);
                ((ImageView) view).setBackground(ob);
            }

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
                    events = gson.fromJson(json, Events.class);
                    Log.d("jhhjhj", events.getData().size() + "");
                    if (events != null
                            && events.getData() != null
                            && events.getData().size() > 0) {
                        listView.removeAllViews();
                        Vector<EventRow> rowData = new Vector<EventRow>();
                        for (int i = 0; i < events.getData().size(); i++) {
                            final Events.Data data = events.getData().get(i);
                            if (data != null) {
                                View convertView = View.inflate(getActivity(), R.layout.item_profile_palces, null);

                                TextView name = (TextView) convertView.findViewById(R.id.tv_name);
                                ;
                                TextView location = (TextView) convertView.findViewById(R.id.tv_location);
                                ;
                                TextView one = (TextView) convertView.findViewById(R.id.tv_one);
                                ;
                                TextView two = (TextView) convertView.findViewById(R.id.tv_two);
                                ;
                                TextView three = (TextView) convertView.findViewById(R.id.tv_three);
                                ;
                                TextView four = (TextView) convertView.findViewById(R.id.tv_four);
                                ;
                                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                                ;

                                name.setText(data.getName());
                                location.setText(data.getWhere_area());

                                if(data.getCost()!=null && !data.getCost().equals("null"))
                                one.setText("Rs "+data.getCost());

                                two.setText(data.getFulladdress_name());
                                three.setText(data.getPlc_name2());
                                four.setText(data.getPlc_name3());
                                convertView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (data.getType().equalsIgnoreCase("Event")) {
                                            Intent i = new Intent(getActivity(), EventClicked.class);
                                            i.putExtra("id", data.getId());
                                            i.putExtra("userid", userId);
                                            i.putExtra("event_edit", "1");
                                            startActivity(i);
                                            getActivity().finish();
                                        } else if (data.getType().equalsIgnoreCase("Plan")) {

                                            Intent intent = new Intent(getActivity(), PlanStickerClick.class);
                                            Toast.makeText(getActivity(), data.getId() + "", Toast.LENGTH_SHORT).show();
                                            intent.putExtra("id", data.getId());
                                            intent.putExtra("userid", userId);
                                            intent.putExtra("plan_edit", "1");
                                            startActivity(intent);
                                            getActivity().finish();
                                        }


                                    }
                                });

                                LoadProfileImageAsync load = new LoadProfileImageAsync(imageView, EventProfileFragment.this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 2899) {
                Log.d("eventsurl", url);
                listView.removeAllViews();
                PostDataToServer post = new PostDataToServer(this);
                post.execute(url);
                dialog = ProgressDialog.show(getActivity(), "",
                        "Loading. Please wait...", true);
                dialog.setCancelable(true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

            EventRow row = (EventRow) getItem(position);

            holder.name.setText(row.getName());
            holder.location.setText(row.getLocation());
            holder.one.setText(row.getOne());
            holder.two.setText(row.getTwo());
            holder.three.setText(row.getThree());
            holder.four.setText(row.getFour());

            LoadProfileImageAsync load = new LoadProfileImageAsync(holder.imageView, EventProfileFragment.this);
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

class EventRow {
    private String name;
    private String location;
    private String one;
    private String two;
    private String three;
    private String four;
    private String image;

    EventRow(String name, String location, String one, String two, String three, String four, String image) {
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
