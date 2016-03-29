package com.appzollo.classes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.appzollo.R;

import java.util.List;

/**
 * Created by saikrishna on 20/11/14.
 */
public class SearchAdapter extends CursorAdapter {

    private List items;

    private TextView text;

    public SearchAdapter(Context context, Cursor cursor, List items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText("" + items.get(cursor.getPosition()));


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);
        text = (TextView) view.findViewById(R.id.text);
        return view;
    }

}