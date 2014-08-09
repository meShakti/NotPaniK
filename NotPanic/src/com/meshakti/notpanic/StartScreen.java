package com.meshakti.notpanic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//shows the application start screen for 5 minutes

public class StartScreen extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notpanicsymbol);
		
		Thread timer=new Thread(){
			@Override
			public void run() {
				super.run();
				try{
					sleep(5000);
				}catch(InterruptedException e){
					e.printStackTrace();
					
				}finally{
					Intent settingIntent= new Intent("com.meshakti.notpanic.SettingsShow");
					startActivity(settingIntent);
					
					
				}
			}
		};
		timer.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
	}
}