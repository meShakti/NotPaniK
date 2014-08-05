package com.meshakti.notpanic;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;

public class Sender {
	private static DBHelper dbManager;
	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
    private static final String SMS_DELIVERED = "SMS_DELIVERED";
    private static final String SMS_SENT = "SMS_SENT";
    
	public static void sendMessage(String message,Activity a){
		dbManager=new DBHelper(a);
		try{
			Cursor c=dbManager.getData("Select id,telno from Contacts");
			while(c.moveToNext()){
				String phoneNo=c.getString(1);
					
					
					SmsManager smsManager = SmsManager.getDefault();
					PendingIntent piSend = PendingIntent.getBroadcast(a.getApplicationContext(), 0, new Intent(SMS_SENT), 0);
			        PendingIntent piDelivered = PendingIntent.getBroadcast(a.getApplicationContext(), 0, new Intent(SMS_DELIVERED), 0);
			       int length = message.length();
		               
	                if(length > MAX_SMS_MESSAGE_LENGTH)
	                {
	                        ArrayList<String> messagelist = smsManager.divideMessage(message);
	                       
	                        smsManager.sendMultipartTextMessage(phoneNo, null, messagelist, null, null);
	                }
	                else
	                {
	                        smsManager.sendTextMessage(phoneNo, null, message, piSend, piDelivered);
	                }
					
				
					
					}
		
			
		}catch(Exception e){
			Log.d("Exception","Exception.."+e);
			
		}
	}


}
