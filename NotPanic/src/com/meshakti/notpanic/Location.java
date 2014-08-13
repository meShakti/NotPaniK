package com.meshakti.notpanic;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;

public class Location {
	public static final double INVALID_LOCATION = -0.763456756879;

	public static HashMap<String, Double> getCurrentLocation(
			Activity activityContext) {
		HashMap<String, Double> lat_long = null;
		LocationManager locationManager = (LocationManager) activityContext
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		android.location.Location location = locationManager
				.getLastKnownLocation(provider);
		if (location != null) {
			Double lat = location.getLatitude();
			Double lon = location.getLongitude();
			lat_long = new HashMap<String, Double>();
			lat_long.put("lat", lat);
			lat_long.put("lon", lon);

		}
		if (lat_long == null) {
			lat_long = new HashMap<String, Double>();
			lat_long.put("lat", INVALID_LOCATION);
			lat_long.put("lon", INVALID_LOCATION);
			return lat_long;
		} else {
			return lat_long;
		}

	}

	public static String getAddress(double lattitude, double longitude,
			Activity actContext) throws IOException {

		Geocoder gc = new Geocoder(actContext, Locale.getDefault());
		List<Address> addresses = gc.getFromLocation(lattitude, longitude, 1);
		String curAddress = "Can't Resolve Address";
		if (addresses.size() > 0 && lattitude != INVALID_LOCATION) {
			Address address = addresses.get(0);
			curAddress = address.getAddressLine(0);
		}
		return curAddress;

	}

}
