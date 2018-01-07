package btu.treasurehunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //getLocation();
        // Start home activity
        startActivity(new Intent(SplashActivity.this, MainActivity.class));


    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                ArrayList<LatLng> campusOptions = new ArrayList<LatLng>();
                campusOptions.add(new LatLng(40.189982, 29.127641));
                campusOptions.add(new LatLng(40.186695, 29.128242));
                campusOptions.add(new LatLng(40.186199, 29.130753));
                campusOptions.add(new LatLng(40.189920, 29.130887));
                campusOptions.add(new LatLng(40.189982, 29.127641));
                if(isPointInPolygon(new LatLng(latti,longi),campusOptions)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    // close splash activity
                    finish();
                }
                else{
                    Toast.makeText(this , "Lattitude " + latti + "Longitude " + longi , Toast.LENGTH_LONG).show();
                }
                //  ((EditText)findViewById(R.id.etLocationLong)).setText("Longitude: " + longi);
            } else {
                Toast.makeText(this , "We can't locate your current position!" , Toast.LENGTH_LONG).show();
            }
        }

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }
        return (intersectCount % 2) == 1;
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