package com.example.background_geolocator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LocationRetriever {

	GeolocationService locator;
	Context context;

	public LocationRetriever(Context context) {
		locator = new GeolocationService(context);
		this.context = context;
		
	}
	
	public String getLocationName() {
		if (locator.canGetLocation()) {

			double latitude = locator.getLatitude();
			double longitude = locator.getLongitude();

			// Toast.makeText(this.context, "Your Location is - \nLat: " +
			// latitude + "\nLong: " + longitude,
			// Toast.LENGTH_LONG).show();

			Geocoder geocoder = new Geocoder(context, Locale.getDefault());
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
			} catch (IOException e1) {
				Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
				e1.printStackTrace();
				return ("IO Exception trying to get address");
			} catch (IllegalArgumentException e2) {

				String errorString = "Illegal arguments " + Double.toString(latitude) + " , "
						+ Double.toString(longitude) + " passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}

			if (addresses != null && addresses.size() > 0) {

				String address = addresses.get(0).getLocality();

//				String addressText = String.format("%s, %s, %s",

//				address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getLocality(),
//						address.getCountryName());

				Toast.makeText(this.context, address, Toast.LENGTH_LONG).show();
				return address;
			} else {
				return "No address found";
			}

		} else {
			locator.showSettingsAlert();
		}
		return null;
	}

}
