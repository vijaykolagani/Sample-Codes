package com.itcuties.android.apps;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcuties.android.apps.data.ContactsData;
import com.prasad.ibox.R;



public class ListAdapterContacts extends ArrayAdapter<ContactsData> {

	// List context
    private final Context context;
    SharedPreferences prefs;
    // List values
    private final List<ContactsData> contactsList;
    String languages[] = { " ARABIC",
			"CHINESE_SIMPLIFIED",
			"CHINESE_TRADITIONAL", "DANISH",
			"DUTCH", "ENGLISH", "FRENCH",
			"GERMAN", "GREEK", "HINDI",
			"HUNGARIAN", "INDONESIAN",
			"ITALIAN", "JAPANESE", "KOREAN",
			"PORTUGUESE", "ROMANIAN",
		"RUSSIAN", "SPANISH", "SWEDISH","THAI",
			"TURKISH", "UKRANIAN",
			"VIETNAMESE" };
	
	public ListAdapterContacts(Context context, List<ContactsData> contactsList) {
		super(context, R.layout.activity_sms, contactsList);
		this.context = context;
		this.contactsList = contactsList;
		prefs = getContext().getSharedPreferences(
			      "com.ducere.wallet", Context.MODE_PRIVATE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
        View rowView = inflater.inflate(R.layout.activity_sms, parent, false);
         
        TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
        TextView messageText = (TextView) rowView.findViewById(R.id.smsText);
        ImageView contactImage = (ImageView)rowView.findViewById(R.id.imageC);
        senderNumber.setText(contactsList.get(position).getName());
        int lang = prefs.getInt(contactsList.get(position).getNumber().replace(" ", ""), 5);
        Log.d("contactlist",contactsList.get(position).getNumber());
        messageText.setText(languages[lang]);
        
        if(contactsList.get(position).getBitmap()!=null){
        	contactImage.setImageBitmap(contactsList.get(position).getBitmap());
        	//Log.d("",contactsList.get(position).getImage()+contactsList.get(position).getDisname());
      
        }else{
        	
          
        }
         
        return rowView;
	}
	
}
