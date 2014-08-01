package com.meshakti.notpanic;

import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
import android.os.Bundle;
//import android.telephony.SmsManager;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PanicAction extends Activity {
	Button deactivateButton;
	DBHelper dbManager;
	MessageSender panicActor;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panicaction);
		tv=(TextView)findViewById(R.id.app_panic_info);
		panicActor=new MessageSender(this,tv);
		dbManager=new DBHelper(this);
		deactivateButton=(Button)findViewById(R.id.app_settings_deactivate);
		panicActor.execute(this);
		deactivateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				panicActor.cancel(true);
				finish();
				
			}
		});
		
	}
	/*
	public void sendMessage(String message){
		try{
			Cursor c=dbManager.getData("Select id,telno from Contacts");
			while(c.moveToNext()){
				String phoneNo=c.getString(1);
					
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, message, null, null);
			/*		Intent intentt = new Intent(Intent.ACTION_VIEW);         
	                intentt.setData(Uri.parse("sms:"));
	                intentt.setType("vnd.android-dir/mms-sms");
	                intentt.putExtra("sms_body",message);
	                intentt.putExtra("address",  phoneNo );
	                startActivity(intentt);
	                
				
			}
		}catch(Exception e){
			Log.d("Exception","Exception.."+e);
		}
	}
*/

	}


