package com.meshakti.notpanic;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

public class Sender {

	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
	private static final String SMS_DELIVERED = "SMS_DELIVERED";
	private static final String SMS_SENT = "SMS_SENT";

	public static void sendMessage(String message, String phoneNo, Activity a) {

		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent piSend = PendingIntent.getBroadcast(
				a.getApplicationContext(), 0, new Intent(SMS_SENT), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(
				a.getApplicationContext(), 0, new Intent(SMS_DELIVERED), 0);
		int length = message.length();

		if (length > MAX_SMS_MESSAGE_LENGTH) {
			ArrayList<String> messagelist = smsManager.divideMessage(message);

			smsManager.sendMultipartTextMessage(phoneNo, null, messagelist,
					null, null);
		} else {
			smsManager.sendTextMessage(phoneNo, null, message, piSend,
					piDelivered);
		}

	}

}
