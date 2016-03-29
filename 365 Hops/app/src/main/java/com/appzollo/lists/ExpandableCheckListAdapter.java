package com.appzollo.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.appzollo.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableCheckListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> headerData;
    private HashMap<String, List<String>> childData;
    CheckedTextView checkbox;
    private LayoutInflater inflater;

    public ExpandableCheckListAdapter(Context context, List<String> headerData,
                                      HashMap<String, List<String>> childData) {
        this.context = context;
        this.headerData = headerData;
        inflater = LayoutInflater.from(context);
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


        TextView lblListHeader = (TextView) view.findViewById(R.id.lbl_list_header);
        lblListHeader.setText(headerRow);
        return view;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        final String rowData = (String) getChild(i, i2);

        if (view == null) {
            view = inflater.inflate(R.layout.list_item, viewGroup,false);
        }
        final CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.list_item_text_child);
        textView.setText(rowData);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.toggle();
            }
        });
        return view;
       /* TextView title;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
         checkbox = (CheckedTextView) view.findViewById(R.id.list_item_text_child);
       // title = (TextView) view.findViewById(R.id.tv_child_title);
        checkbox.setText(rowData);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkbox.toggle();
            }
        });
        return view;*/
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
