package com.meshakti.notpanic;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.TextView;





public class MessageSender extends AsyncTask<Activity, Void, String> {
	private static final int NO_OF_MESSAGES=5;
	Activity a;
	DBHelper dbManager;
	TextView tv;
	public MessageSender(Activity act,TextView tv){
		a=act;
		dbManager=new DBHelper(a);
		this.tv=tv;
	} 
	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();
		
	}
	@Override
	protected String doInBackground(Activity... activity) {
		// TODO Auto-generated method stub
	
		
		final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
		
				//Location can't be updated :(
				final String finalMessage="Acknowledgement return";
				final Activity act=activity[0];
				Runnable task = new Runnable() {
				public void run() {
					String message="I am in danger ! Follow my location at :";
					HashMap<String,Double>loc=Location.getCurrentLocation(act);
					
					try {
						
						double lat=loc.get("lat").doubleValue();
						double lon=loc.get("lon").doubleValue();
						message+=Location.getAddress(lat,lon,act);
						  
						 
					} catch (IOException e) {
						
						message+="(Location can't resolved to an address .Use the Latitude and longitude to track location using internet)";
					}
					
					if(loc.get("lat")!=Location.INVALID_LOCATION){
						message+=" Latitude:"+loc.get("lat")+" Longitude:"+loc.get("lon");
					}
					sendMessage(message);
				}
				};
				for(int i=0;i<NO_OF_MESSAGES;i++){
					worker.schedule(task, i* 120, TimeUnit.SECONDS);
				}
		return finalMessage;
	}
	@Override
	protected void onPostExecute(String message) {
		super.onPostExecute(message);
		
		

		
	}
	public void sendMessage(String message){
		try{
			Cursor c=dbManager.getData("Select id,telno from Contacts");
			while(c.moveToNext()){
				String phoneNo=c.getString(1);
					
					try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, message, null, null);
					
					
					/*Intent intentt = new Intent(Intent.ACTION_VIEW);         
	                intentt.setData(Uri.parse("sms:"));
	                intentt.setType("vnd.android-dir/mms-sms");
	                intentt.putExtra("sms_body",message);
	                intentt.putExtra("address",  phoneNo );
	                a.startActivity(intentt);
	                */
					}catch(Exception e){
						tv.setText(""+e);
					}
					
					}
		
			
		}catch(Exception e){
			Log.d("Exception","Exception.."+e);
			//tv.setText(e.getMessage());
		}
	}



	}
	
    
