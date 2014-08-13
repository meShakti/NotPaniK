package com.meshakti.notpanic;

import java.io.IOException;
import java.util.HashMap;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class MessageSender extends AsyncTask<Activity, Void, String> {

	Activity a;

	TextView tv;

	public MessageSender(Activity act, TextView tv) {
		a = act;
		this.tv = tv;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

	}

	@Override
	protected String doInBackground(Activity... activity) {

		String message = "I am in danger ! Follow my location at :";
		Activity act = activity[0];

		HashMap<String, Double> loc = Location.getCurrentLocation(act);

		try {

			double lat = loc.get("lat").doubleValue();
			double lon = loc.get("lon").doubleValue();
			message += Location.getAddress(lat, lon, act);

		} catch (IOException e) {

			message += "(Location can't resolved to an address .Use the Latitude and longitude to track location using internet)";
		}

		if (loc.get("lat") != Location.INVALID_LOCATION) {
			message += " Latitude:" + loc.get("lat") + " Longitude:"
					+ loc.get("lon");
		}

		return message;
	}

	@Override
	protected void onPostExecute(String message) {
		super.onPostExecute(message);
		Sender.sendMessage(message, a);

	}

}
