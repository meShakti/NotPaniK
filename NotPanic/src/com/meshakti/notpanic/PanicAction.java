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
		tv = (TextView) findViewById(R.id.app_panic_info);
		panicActor = new MessageSender(this, tv);
		dbManager = new DBHelper(this);
		deactivateButton = (Button) findViewById(R.id.app_settings_deactivate);
		panicActor.execute(this);
		deactivateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				panicActor.cancel(true);
				finish();

			}
		});

	}

}
