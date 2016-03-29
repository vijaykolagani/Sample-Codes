package com.itcuties.android.apps;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.prasad.ibox.R;
 
public class SMSNotify extends BroadcastReceiver {
    
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private NotificationManager mNotificationManager;
    private int SIMPLE_NOTFICATION_ID;

public void onReceive(Context context, Intent intent) {
     
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
 
        try {
             
            if (bundle != null) {
                 
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                 
                for (int i = 0; i < pdusObj.length; i++) {
                     
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
 
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                     
                    mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notifyDetails = new Notification(R.drawable.icon,
                    "New Message", System.currentTimeMillis());
            PendingIntent myIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);
            notifyDetails.setLatestEventInfo(context, "Message arrived",
                    "Click on me to Translate", myIntent);
            notifyDetails.flags |= Notification.FLAG_AUTO_CANCEL;
            notifyDetails.flags |= Notification.DEFAULT_SOUND;
            mNotificationManager.notify(SIMPLE_NOTFICATION_ID, notifyDetails);
                   // Show Alert
                    int duration = Toast.LENGTH_LONG;
                  
                    	
                     
                } // end for loop
              } // bundle is null
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
             
        }
    }

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}    
}