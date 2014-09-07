package com.meshakti.notpanic;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

//confirms that alert meant for NotPanic only
public class AlertConfirm extends IntentService {

	public AlertConfirm() {
		super("AlertConfirm");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		AudioManager listener = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (listener.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
			Intent i = new Intent(AlertReciever.TRIGER_COMMAND);
			sendBroadcast(i);
		}
		stopSelf();
	}

}
