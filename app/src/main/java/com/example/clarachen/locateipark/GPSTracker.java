package com.example.clarachen.locateipark;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by ClaraChen on 4/17/16.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    //Declaring a Location Manager
    protected LocationManager locationManager;

    //Flag for GPS status
    boolean isGPSEnabled = false;

    //Flag for Network status
    boolean isNetworkEnabled = false;

    //Flag for Location Service status
    boolean canGetLocation = false;

    //Minimun distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 meters minimum

    //Minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000*60*1; // minimum 1 minute


    Location location;
    double latitude;
    double longitude;

    public GPSTracker (Context context){
        mContext = context;
        getLocation();
    }

    public Location getLocation(){

        try{
            locationManager = (LocationManager)mContext
                    .getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting Network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                //no network provider is enabled
                Log.i("No Network Provider is","Enabled!");

            }
            else{
                this.canGetLocation = true;
                //First get location from Network Provider
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                }

                //if GS Enabled get  lat/long using GPS Services
                if(isGPSEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d("GPS Enabled","GPS Enabled");
                        if(locationManager !=null){
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(locationManager !=null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }

                    }
                }
            }

            }catch (Exception e) {
            e.printStackTrace();
        }
        return location;
        }

    /**
     * Stop using GPS Listiner
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS(){
        if(locationManager !=null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * Function to check GPS/wifi enable
     **/
    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    /**
     * Function show settings alert dialog
     * On pressing settings button will launch setting options
     */
    public void showSettingAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        //Setting dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to setting menu?");


        //on pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //on pressing Cancel Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });

        //Show Alert Message
        alertDialog.show();
    }
    @Override
    public void onLocationChanged(Location location){

    }

    @Override
    public void onProviderDisabled(String provider){

    }
    @Override
    public void onProviderEnabled(String provider){

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }
    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
}
