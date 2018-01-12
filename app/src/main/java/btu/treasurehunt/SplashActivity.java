package btu.treasurehunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL =  10000;  /* 5 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private int i =0;
    private int j =0;
    SessionManager session;
    Handler myHandler;
    private long myid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandler = new Handler();
        ((MyApplication) this.getApplication()).setMainActivity(this);
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn())
        {
            myid=Long.parseLong(session.getUserDetails().get("id"));
        }
        startLocationUpdates();

        // Start home activity



    }


    private void checkConnection(Location location) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected , location);

    }

    private void showSnack(boolean isConnected , final Location location) {

        if (isConnected) {

            if (location != null){

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://databaserest.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final SpecialObjectsservice service = retrofit.create(SpecialObjectsservice.class);
                Call<SpecialObjects> createCall = service.checkall();
                createCall.enqueue(new Callback<SpecialObjects>() {
                    @Override
                    public void onResponse(Call<SpecialObjects> _, Response<SpecialObjects> response) {
                        if (response.body()!=null) {
                                displayAlert2(response.body().account.id);

                        }
                        else {
                            double latti = location.getLatitude();
                            double longi = location.getLongitude();

                            ArrayList<LatLng> campusOptions = new ArrayList<LatLng>();
                            campusOptions.add(new LatLng(40.187982, 29.127641));
                            campusOptions.add(new LatLng(40.186695, 29.128242));
                            campusOptions.add(new LatLng(40.186199, 29.130753));
                            campusOptions.add(new LatLng(40.187920, 29.130887));
                            campusOptions.add(new LatLng(40.187982, 29.127641));
                            if (i == 0) {
                                if (isPointInPolygon(new LatLng(latti, longi), campusOptions)) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    // close splash activity
                                    finish();
                                    i++;
                                }
                            }
                                if (!isPointInPolygon(new LatLng(latti, longi), campusOptions)) {
                                    new AlertDialog.Builder(((MyApplication) getApplication()).getMainActivity()).setMessage("You can not play the game outside of the campus!")
                                            .setTitle("Location Error")
                                            .setCancelable(false)
                                            .setNeutralButton(android.R.string.ok,
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            android.os.Process.killProcess(android.os.Process.myPid());
                                                        }
                                                    })
                                            .show();
                                }
                        }
                    }
                    @Override
                    public void onFailure(Call<SpecialObjects> _, Throwable t) {
                        t.printStackTrace();
                    }
                });
                //  ((EditText)findViewById(R.id.etLocationLong)).setText("Longitude: " + longi);
            } else {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }, 10000);
            }


        } else {
                displayAlert();

        }
    }

    public void displayAlert()
    {

            new AlertDialog.Builder(((MyApplication) this.getApplication()).getMainActivity()).setMessage("Please check your internet connection and try again!")
                    .setTitle("Network Error")
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                    .show();
    }
    public void displayAlert2( long id) {
        if(myid==id){
            new AlertDialog.Builder(((MyApplication) this.getApplication()).getMainActivity()).setMessage("You found the treasure!")
                    .setTitle("Congratulations!")
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                    .show();
        }
        else {
            new AlertDialog.Builder(((MyApplication) this.getApplication()).getMainActivity()).setMessage("Someone found the treasure!")
                    .setTitle("Game Over!")
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                    .show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected , ((MyApplication) this.getApplication()).getCurrentLocation());
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;

        }

        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location location=locationResult.getLastLocation();
                        checkConnection(location);

                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {

                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("SplashActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }
    public void onLocationChanged(Location location) {
        // New location has now been determined
        ((MyApplication) this.getApplication()).setCurrentLocation(location);
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }


    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }
        //return (intersectCount % 2) == 1;
        return true;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }

}