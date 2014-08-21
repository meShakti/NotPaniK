package com.meshakti.notpanic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

//Triggers the NotPanic Activity
public class AlertReciever extends BroadcastReceiver {
	// whenever change TRIGER_COMMAND change action name in manifests
	final public static String TRIGER_COMMAND = "com.meShakti.notpanic.action.triggered";
	private static int WAIT_TIME = 5000;

	@Override
	public void onReceive(Context context, Intent intent) {
		final Context appContext = context;
		if (intent.getAction().equals(TRIGER_COMMAND)) {
			startAction(appContext, 2);

		} else {

			AudioManager listner = (AudioManager) appContext
					.getSystemService(Context.AUDIO_SERVICE);
			int volumeMode = listner.getRingerMode();
			Thread timer = new Thread() {
				@Override
				public void run() {
					super.run();

					try {
						sleep(WAIT_TIME);

					} catch (InterruptedException e) {
						e.printStackTrace();

					} finally {
						startAction(appContext, 1);
					}
				}

			};
			if (volumeMode == AudioManager.RINGER_MODE_VIBRATE) {
				Toast.makeText(appContext,
						"!Panic :Please Confirm Emergency Pattern",
						Toast.LENGTH_LONG).show();
				timer.start();
			}
		}
	}

	public void startAction(Context context, int step) {
		switch (step) {
		case 1:
			Intent i = new Intent(context, AlertConfirm.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
			break;
		case 2:
			Intent i2 = new Intent(context, PanicAction.class);
			i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i2);
			break;
		}
	}
}
