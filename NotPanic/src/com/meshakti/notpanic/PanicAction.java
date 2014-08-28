package com.meshakti.notpanic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PanicAction extends Activity {
	Button deactivateButton;
	DBHelper dbManager;
	MessageSender panicActor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.panicaction);
		panicActor = new MessageSender(this);
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
