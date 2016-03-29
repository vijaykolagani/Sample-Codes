package com.appzollo.fragments;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.appzollo.R;
import com.appzollo.classes.RowItemPlace;


public class FeedsFragment extends Fragment implements OnItemClickListener {

    String[] menutitles = {"Asuthosh Misra, Banglore commented on your plan sticker Looking for a trek mate",
            "Sneha Misra, Banglore commented on your plan sticker Looking for a trek mate",
            "Asuthosh Misra, Banglore commented on your plan sticker Looking for a trek mate",
            "Sneha Misra, Banglore commented on your plan sticker Looking for a trek mate"};

    CustomAdapter adapter;
    private List<RowItemPlace> rowItems;
    ListView list;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feeds, null, false);
        list = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        rowItems = new ArrayList<RowItemPlace>();

        for (int i = 0; i < menutitles.length; i++) {
            RowItemPlace items = new RowItemPlace(menutitles[i]);

            rowItems.add(items);
        }

        adapter = new CustomAdapter(getActivity(), rowItems);
       // list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
                .show();

    }

    public class CustomAdapter extends BaseAdapter {

        Context context;
        List<RowItemPlace> rowItem;

        CustomAdapter(Context context, List<RowItemPlace> rowItem) {
            this.context = context;
            this.rowItem = rowItem;

        }

        @Override
        public int getCount() {

            return rowItem.size();
        }

        @Override
        public Object getItem(int position) {

            return rowItem.get(position);
        }

        @Override
        public long getItemId(int position) {

            return rowItem.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.item_list_feed, null);
            }

            // ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.tv_feed);

            RowItemPlace row_pos = rowItem.get(position);
            // setting the image resource and title
            //imgIcon.setImageResource(row_pos.getIcon());
            String[] feed = row_pos.getTitle().split(",");
            txtTitle.setText(Html.fromHtml("<b>"+feed[0]+"</b>"+", "+feed[1]));

            return convertView;

        }

    }
}
