package com.mrameezraja.plugins.gpsfix;

import java.util.LinkedList;
import java.util.List;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

@SuppressLint("NewApi") public class GPS extends CordovaPlugin {

	private static final long DURATION_TO_FIX_LOST_MS = 10000;

	private static final long MINIMUM_UPDATE_TIME = 0;
	private static final float MINIMUM_UPDATE_DISTANCE = 0.0f;

	// the last location time is needed to determine if a fix has been lost
	private long locationTime = 0;
	private List<Float> rollingAverageData = new LinkedList<Float>();

	private LocationManager locationManager;
	private MyGpsListener gpsListener;

	private boolean gpsEnabled;
	private boolean gpsFix;
	private double latitude;
	private double longitude;
	private int satellitesTotal;
	private int satellitesUsed;
	private float accuracy = 60;

	final CordovaWebView appView = webView;

	private String TAG = "GPSPlugin";

	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.d(TAG, "initialize...");
        startGps();
				appView = webView;
    } 

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d(TAG, "action => " + action);
		if (action.equals("getStatus")) {
        JSONObject r = new JSONObject();
        r.put("gpsEnabled", gpsEnabled);
        r.put("gpsFix", gpsFix);
        r.put("grade", getGrade());
        r.put("accuracy", accuracy);
        callbackContext.success(r);
        return true;
    }
		else if(action.equals("getCurrentLocation")){
				JSONObject r = new JSONObject();
				r.put("latitude", latitude);
				r.put("longitude", longitude);
				r.put("gpsEnabled", gpsEnabled);
				r.put("gpsFix", gpsFix);
				r.put("grade", getGrade());
				r.put("accuracy", accuracy);
				callbackContext.success(r);
				return true;
		}


    return false;
  }

	public void startGps(){
		Log.d(TAG, "startGps starting...");
		// ask Android for the GPS service
		locationManager = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
		// make a delegate to receive callbacks
		gpsListener = new MyGpsListener();
		// ask for updates on the GPS status
		locationManager.addGpsStatusListener(gpsListener);
		// ask for updates on the GPS location
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MINIMUM_UPDATE_TIME, MINIMUM_UPDATE_DISTANCE, gpsListener);
		Log.d(TAG, "startGps started");
	}

	/*@Override
	protected void onResume() {
		super.onResume();

		// ask Android for the GPS service
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// make a delegate to receive callbacks
		gpsListener = new MyGpsListener();
		// ask for updates on the GPS status
		locationManager.addGpsStatusListener(gpsListener);
		// ask for updates on the GPS location
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MINIMUM_UPDATE_TIME, MINIMUM_UPDATE_DISTANCE, gpsListener);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (locationManager != null) {
			// remove the delegates to stop the GPS
			locationManager.removeGpsStatusListener(gpsListener);
			locationManager.removeUpdates(gpsListener);
			locationManager = null;
		}
	}*/

	private String getGrade() {

		if (!gpsEnabled) {
			return "Disabled";
		}
		if (!gpsFix) {
			return "Waiting for Fix";
		}
		if (accuracy <= 10) {
			return "Good";
		}
		if (accuracy <= 50) {
			return "Fair";
		}
		if (accuracy <= 100) {
			return "Bad";
		}
		return "Unusable";
	}

	private void sendJavascript(final String javascript) {

				webView.post(new Runnable() {
				    @Override
				    public void run() {
						// See: https://github.com/GoogleChrome/chromium-webview-samples/blob/master/jsinterface-example/src/com/google/chrome/android/example/jsinterface/MainActivity.java
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
							webView.evaluateJavascript(javascript, null);
						} else {
							webView.loadUrl("javascript:" + javascript);
						}
				    }
				});
			}

	protected class MyGpsListener implements GpsStatus.Listener, LocationListener {

		@Override
		public void onGpsStatusChanged(int changeType) {
			if (locationManager != null) {
				Log.d(TAG, "calling onGpsStatusChanged...");
				// status changed so ask what the change was
				GpsStatus status = locationManager.getGpsStatus(null);
				switch(changeType) {
					case GpsStatus.GPS_EVENT_FIRST_FIX:
						gpsEnabled = true;
						gpsFix = true;
						break;
					case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
						gpsEnabled = true;
						// if it has been more then 10 seconds since the last update, consider the fix lost
						gpsFix = System.currentTimeMillis() - locationTime < DURATION_TO_FIX_LOST_MS;
						break;
					case GpsStatus.GPS_EVENT_STARTED: // GPS turned on
						gpsEnabled = true;
						gpsFix = false;
						break;
					case GpsStatus.GPS_EVENT_STOPPED: // GPS turned off
						gpsEnabled = false;
						gpsFix = false;
						break;
					default:
						//Log.w(TAG, "unknown GpsStatus event type. "+changeType);
						return;
				}

				// number of satellites, not useful, but cool
				int newSatTotal = 0;
				int newSatUsed = 0;
				for(GpsSatellite sat : status.getSatellites()) {
					newSatTotal++;
					if (sat.usedInFix()) {
						newSatUsed++;
					}
				}
				satellitesTotal = newSatTotal;
				satellitesUsed = newSatUsed;

				Log.d(TAG, "sending onGpsStatusChanged...");
				appView.sendJavascript("cordova.fireWindowEvent('onGpsStatusChanged', { 'gpsEnabled':'"+ gpsEnabled +"', 'gpsFix':'"+ gpsFix +"', 'accuracy':'"+ accuracy +"', 'grade':'"+ getGrade() +"' });");

				//sendJavascript("cordova.fireWindowEvent('onGpsStatusChanged', { 'gpsEnabled':" + Boolean.toString(gpsEnabled)+", 'gpsFix': "+ Boolean.toString(gpsFix) +", 'accuracy': "+ getGrade() +" });");
			}
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.d(TAG, "calling onLocationChanged...");
			locationTime = location.getTime();
			latitude = location.getLatitude();
			longitude = location.getLongitude();

			if (location.hasAccuracy()) {

				rollingAverageData.add(location.getAccuracy());
				if (rollingAverageData.size() > 10) {
					rollingAverageData.remove(0);
				}

				float average = 0.0f;
				for(Float number : rollingAverageData) {
					average += number;
				}
				average = average / rollingAverageData.size();

				accuracy = average;
			}

			//Log.d(TAG, "sending onLocationChanged...");
			//appView.sendJavascript("cordova.fireWindowEvent('onLocationChanged', {'accuracy':'"+ accuracy +"'});");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			/* dont need this info */
		}

		@Override
		public void onProviderEnabled(String provider) {
			/* dont need this info */
		}

		@Override
		public void onProviderDisabled(String provider) {
			/* dont need this info */
		}


	}
}
