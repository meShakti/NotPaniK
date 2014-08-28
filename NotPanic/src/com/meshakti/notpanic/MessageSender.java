package com.meshakti.notpanic;

import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

//Does three task
//1.Creates the Location Message
//2.Gets the latitude and longitude
//3.Sends this message to contacts
public class MessageSender extends AsyncTask<Activity, Void, String> {

	Activity a;
	DBHelper dbManager;

	private String locUri = "https://www.google.com/maps/place/";

	public MessageSender(Activity act) {
		a = act;
		dbManager = new DBHelper(a);

	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

	}

	@Override
	protected String doInBackground(Activity... activity) {

		String message = dbManager.getMessage();
		Activity act = activity[0];

		HashMap<String, Double> loc = Location.getCurrentLocation(act);

		double lat = loc.get("lat").doubleValue();
		double lon = loc.get("lon").doubleValue();
		message += "\nLocation Details:";
		message += "\nNearby Known Landmark:";
		try {

			message += Location.getAddress(lat, lon, act);

		} catch (IOException e) {

			message += "Not Found";
		}
		message += "\nFind Current Location at : ";

		if (loc.get("lat") != Location.INVALID_LOCATION) {
			message += locUri + "" + lat + "+" + lon;
		} else {
			message += "Not Found";
		}

		return message;
	}

	@Override
	protected void onPostExecute(String message) {
		super.onPostExecute(message);

		try {
			Cursor c = dbManager.getData("Select id,telno from Contacts");
			while (c.moveToNext()) {
				String phoneNo = c.getString(1);
				Sender.sendMessage(message, phoneNo, a);
			}
		} catch (Exception e) {
			Log.d("Exception", "Exception.." + e);

		}

		Intent i = new Intent(a, SettingsShow.class);
		a.startActivity(i);
		a.finish();
	}

}
