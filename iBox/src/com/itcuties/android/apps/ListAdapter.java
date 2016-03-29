package com.itcuties.android.apps;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcuties.android.apps.data.SMSData;
import com.prasad.ibox.R;



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
        TextView messageText = (TextView) rowView.findViewById(R.id.smsText);
        ImageView contactImage = (ImageView)rowView.findViewById(R.id.imageC);
        senderNumber.setText(smsList.get(position).getDisname());
        messageText.setText(smsList.get(position).getBody());
        if(smsList.get(position).getImage()!=null){
        	contactImage.setImageBitmap(smsList.get(position).getImage());
        	Log.d("",smsList.get(position).getImage()+smsList.get(position).getDisname());
      
        }else{
        	
          
        }
         
        return rowView;
	}
	
}
