package com.itcuties.android.apps;

import java.util.List;

import afictive.messagetranslator.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itcuties.android.apps.data.SMSData;



public class ListAdapter extends ArrayAdapter<SMSData> {

	// List context
    private final Context context;
    // List values
    private final List<SMSData> smsList;
	
	public ListAdapter(Context context, List<SMSData> smsList) {
		super(context, R.layout.activity_sms, smsList);
		this.context = context;
		this.smsList = smsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
        View rowView = inflater.inflate(R.layout.activity_sms, parent, false);
         
        TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
        senderNumber.setText(smsList.get(position).getNumber());
         
        return rowView;
	}
	
}
