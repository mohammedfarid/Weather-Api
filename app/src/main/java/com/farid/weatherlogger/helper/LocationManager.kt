package com.farid.weatherlogger.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class LocationManager(activity: Activity, locationType: Int): LocationListener {
    private val TAG = LocationManager::class.java.simpleName

    companion object {
        val SINGLE = 0
        val UPDATES = 1
        val REQUEST_PERMISSIONS_REQUEST_CODE = 0x2
        val REQUEST_CHECK_SETTINGS = 0x1
        private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    private var onLocationManagerListener: OnLocationManagerListener? = null

    private var activity: Activity? = null

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            stopLocationUpdates()
            val location = locationResult.lastLocation
            if (location != null) {
                onLocationManagerListener?.onLocationDetected(
                    location
                )
            } else {
                onLocationManagerListener?.onSomethingWentWrong()
            }
        }
    }

    init {
        this.activity = activity
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
        createLocationRequest(locationType) // creating location updates request
    }

    fun location(onLocationManagerListener: OnLocationManagerListener?) {
        this.onLocationManagerListener = onLocationManagerListener
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            checkLocationSettings()
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationSettingsEnabled(): Boolean {
        var gps_enabled = false
        var network_enabled = false
        val lm =
            activity!!.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        try {
            gps_enabled = lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
            Log.e(TAG, ex.localizedMessage)
        }
        try {
            network_enabled =
                lm.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
            Log.e(TAG, ex.localizedMessage)
        }
        return gps_enabled || network_enabled
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat
            .shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
//        if (shouldProvideRationale) {
//            Log.i(TAG, "Displaying permission rationale to provide additional context.")
//            if (onLocationManagerListener != null)
//                onLocationManagerListener!!.onShowPermissionRationale()
//            else
//                somethingWentWrong()
//
//        } else {
//            Log.i(TAG, "Requesting permission");
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            startLocationPermissionRequest()
//        }
        startLocationPermissionRequest()
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
        //        if (fragment == null) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
//        } else {
//            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
//        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isEmpty()) { // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permission granted.
                checkLocationSettings()
            } else { // Permission denied.
                if (onLocationManagerListener != null) onLocationManagerListener!!.onPermissionsDenied() else somethingWentWrong()
            }
        }
    }

    private fun checkLocationSettings() {
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest!!)
        //builder.setAlwaysShow(true);
        val client = LocationServices.getSettingsClient(activity!!)
        val task =
            client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(activity!!) {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            getLocation()
        }
        task.addOnFailureListener(activity!!) { e ->
            if (e is ResolvableApiException) { // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try { // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        activity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: SendIntentException) { // Ignore the error.
                }
            }
        }
    }

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) { // Check for the integer request code originally supplied to startResolutionForResult().
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            when (resultCode) {
                Activity.RESULT_OK ->  //Toast.makeText(this, "User agreed to make required location settings changes.", Toast.LENGTH_SHORT).show();
                    // Nothing to do. startLocationupdates() gets called in onResume again.
                    getLocation()
                Activity.RESULT_CANCELED ->  //Toast.makeText(this, "User chose not to make required location settings changes.", Toast.LENGTH_SHORT).show();
                    //mRequestingLocationUpdates = false;
                    //updateUI();
                    if (onLocationManagerListener != null) onLocationManagerListener!!.onLocationSettingsDenied() else {
                        somethingWentWrong()
                    }
            }
        }
    }

    private fun getLocation() {
        startLocationUpdates()
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     *
     *
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     *
     *
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private fun createLocationRequest(locationType: Int) {
        mLocationRequest = LocationRequest()
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (locationType == SINGLE)
            mLocationRequest!!.numUpdates = 1
    }

    private fun startLocationUpdates() {
        mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(locationCallback)
    }

    private fun somethingWentWrong() {
        Toast.makeText(
            activity,
            "Something went wrong. Please try again later!",
            Toast.LENGTH_SHORT
        ).show()
    }

    interface OnLocationManagerListener {
        fun onShowPermissionRationale()
        fun onPermissionsDenied()
        fun onLocationSettingsDenied()
        fun onLocationDetected(location: Location?)
        fun onSomethingWentWrong()
    }

    override fun onLocationChanged(p0: Location?) {
    }
}