package com.appzollo.lists;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appzollo.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> headerData;
    private HashMap<String, List<String>> childData;

    public ExpandableListAdapter(Context context, List<String> headerData,
                                 HashMap<String, List<String>> childData) {
        this.context = context;
        this.headerData = headerData;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return this.headerData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.childData.get(headerData.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.headerData.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return this.childData.get(this.headerData.get(i)).get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerRow = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exp_list_group, null);
        }

        View ind = view.findViewById(R.id.ivIndicator);
        if (ind != null)
        {
            ImageView indicator = (ImageView) ind;
            if (getChildrenCount(i) == 0)
            {
                indicator.setVisibility(View.INVISIBLE);
            }
            else
            {
                indicator.setVisibility(View.VISIBLE);
                indicator.setImageResource(b?android.R.drawable.arrow_up_float:android.R.drawable.arrow_down_float);
            }
        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.lbl_list_header);
        lblListHeader.setText(headerRow);
        return view;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        final String rowData = (String) getChild(i, i2);

        TextView title;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.account_list_item, null);
        }

        title = (TextView) view.findViewById(R.id.tv_child_title);
        title.setText(rowData);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
