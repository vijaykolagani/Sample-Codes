package com.appzollo.classes;

/**
 * Created by vijay on 20-11-2014.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.appzollo.R;

import java.util.ArrayList;

/**
 *
 *
 * @author Lauri Nevala
 *
 * Based on example by LAURA SUCIU
 * @see http://myandroidsolutions.blogspot.com/2012/08/android-expandable-list-example.html
 *
 */

public class SettingsListAdapter extends BaseExpandableListAdapter {


    private LayoutInflater inflater;
    private ArrayList<Category> mParent;
    private ExpandableListView accordion;
    public int lastExpandedGroupPosition;
    private ArrayList<String> interest;


    public SettingsListAdapter(Context context, ArrayList<Category> parent, ExpandableListView accordion,ArrayList<String> interest) {
        mParent = parent;
        inflater = LayoutInflater.from(context);
        this.accordion = accordion;
        this.interest = interest;

    }


    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).children.size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).name;
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).children.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.list_group, viewGroup,false);
        }
        // set category name as tag so view can be found view later
        view.setTag(getGroup(i).toString());

        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);

        //"i" is the position of the parent/group in the list
        textView.setText(getGroup(i).toString());

        TextView sub = (TextView) view.findViewById(R.id.list_item_text_subscriptions);

        if(mParent.get(i).selection.size()>0) {
            sub.setText(mParent.get(i).selection.toString());
        }
        else {
            sub.setText("");
        }

        //return the entire view
        return view;
    }


    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, viewGroup,false);
        }
        CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.list_item_text_child);
        textView.setText(mParent.get(i).children.get(i1).name);
        if(interest.contains(textView.getText().toString())){
            textView.setChecked(true);
        } else {
            textView.setChecked(false);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    /**
     * automatically collapse last expanded group
     * @see http://stackoverflow.com/questions/4314777/programmatically-collapse-a-group-in-expandablelistview
     */
    public void onGroupExpanded(int groupPosition) {

        if(groupPosition != lastExpandedGroupPosition){
            accordion.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;

    }



}