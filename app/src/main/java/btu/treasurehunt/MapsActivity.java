package btu.treasurehunt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import btu.treasurehunt.Sensors.Magnetometer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Polygon previouspolygon;
    public SessionManager session;
    public List<UsermapCells> mycells;

    public MapsActivity() {
    }

    // Create a LatLngBounds that includes the city of Adelaide in Australia.
    // Instantiates a new Polygon object and adds points to define a rectangle
    private static Iterable<LatLng> createHole(LatLng center, int radius) {
        int points = 50; // number of corners of inscribed polygon

        double radiusLatitude = Math.toDegrees(radius / (float) 1000000);
        double radiusLongitude = radiusLatitude / Math.cos(Math.toRadians(center.latitude));

        List<LatLng> result = new ArrayList<>(points);

        double anglePerCircleRegion = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double theta = i * anglePerCircleRegion;
            double latitude = center.latitude + (radiusLatitude * Math.sin(theta));
            double longitude = center.longitude + (radiusLongitude * Math.cos(theta));

            result.add(new LatLng(latitude, longitude));
        }

        return result;
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        session = new SessionManager(this.getActivity().getApplicationContext());
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://databaserest.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsermapCellsservice service = retrofit.create(UsermapCellsservice.class);
        String id="0";
        if(session.isLoggedIn())
        {
            id=session.getUserDetails().get("id");
        }
        Call<List<UsermapCells>> call = service.user(Long.parseLong(id));
        call.enqueue(new Callback<List<UsermapCells>>() {
            @Override
            public void onResponse(Call<List<UsermapCells>> call, Response<List<UsermapCells>> cells) {
                mycells=cells.body();
                mMap = googleMap;
                mMap.setMaxZoomPreference(19);
                mMap.setMinZoomPreference(17);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.getUiSettings().setTiltGesturesEnabled(false);
                MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.my_map_style);
                mMap.setMapStyle(style);
                LatLngBounds ADELAIDE = new LatLngBounds(
                        new LatLng(40.186626, 29.128054),new LatLng(40.187934, 29.130599));
// Constrain the camera target to the Adelaide bounds.
                mMap.setLatLngBoundsForCameraTarget(ADELAIDE);
                // Add a marker in Sydney and move the camera
                LatLng BTU = new LatLng(40.187215, 29.129367);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(BTU));
                ArrayList<LatLng> campusOptions = new ArrayList<LatLng>();
                campusOptions.add(new LatLng(40.187982, 29.127641));
                campusOptions.add(new LatLng(40.186695, 29.128242));
                campusOptions.add(new LatLng(40.186199, 29.130753));
                campusOptions.add(new LatLng(40.187920, 29.130887));
                campusOptions.add(new LatLng(40.187982, 29.127641));
                PolygonOptions worldOptions = new PolygonOptions()
                        .add(new LatLng(40.202699, 29.117967),
                                new LatLng(40.202896, 29.158624),
                                new LatLng(40.172140, 29.156945),
                                new LatLng(40.172574, 29.101299),
                                new LatLng(40.202699, 29.117967));
                worldOptions.strokeWidth(10);
                worldOptions.strokeColor(0xffffffff);
                worldOptions.fillColor(0x7f000000);
                worldOptions.addHole(createHole(BTU,28));
                Polygon Worldyygon = mMap.addPolygon(worldOptions);
                double x=40.186142;
                double y=29.127519;
                int z=176;
                while(x<40.188043){
                    y=29.127519;
                    while (y<29.130913){
                        PolygonOptions rectOptions = new PolygonOptions()
                                .add(new LatLng(x+0.000150, y),
                                        new LatLng(x+0.000150, y+0.000200),
                                        new LatLng(x, y+0.000200),
                                        new LatLng(x, y),
                                        new LatLng(x+0.000150, y));
                        rectOptions.strokeWidth(10);
                        rectOptions.clickable(true);
                        rectOptions.strokeColor(0xffffffff);
                        rectOptions.fillColor(0x7fF4A460);
                        // Get back the mutable Polygon
                        ArrayList<LatLng> taps = new ArrayList<LatLng>();
                        taps.add(new LatLng(x+0.000150, y));
                        taps.add(new LatLng(x+0.000150, y+0.000200));
                        taps.add(new LatLng(x, y+0.000200));
                        taps.add(new LatLng(x+0.000150, y));
                        int count=0;
                        for(int i=0;i<=taps.size()-1;i++) {
                            if(isPointInPolygon(taps.get(i),campusOptions)){
                                count++;}
                        }
                        if(count==4){
                            Polygon polygon = mMap.addPolygon(rectOptions);
                            polygon.setClickable(true);
                            if(!mycells.isEmpty()){
                                for (Iterator<UsermapCells> it = mycells.iterator(); it.hasNext(); ) {
                                    UsermapCells f = it.next();
                                    if (f.cells.id==z)
                                    {
                                        polygon.setTag(new Tag(f.cells.numlayers,f.cells.id,f.currentlayer));
                                    }
                                }
                                if(polygon.getTag()==null)
                                    polygon.setTag(new Tag(0,z,1));
                            }
                            else{
                                polygon.setTag(new Tag(0,z,1));
                            }

                            rectOptions.fillColor(((Tag)polygon.getTag()).currentlayer==((Tag)polygon.getTag()).numlayers?0x7f7C542E:((Tag)polygon.getTag()).currentlayer<5?0x7fF4A460:((Tag)polygon.getTag()).currentlayer<10?0x7fDC9456:((Tag)polygon.getTag()).currentlayer<15?0x7fC4844C:0x7fAC7442);
                        }
                        y=y+0.000220;
                        z++;
                    }
                    x=x+0.000160;
                }

                mMap.setOnPolygonClickListener(new OnPolygonClickListener(){
                    public void onPolygonClick(Polygon polygon){
                        final BottomSheetDialogFragment myBottomSheet = MyBottomSheetDialogFragment.newInstance(polygon);
                        if(previouspolygon!=null) {
                            previouspolygon.setStrokeColor(0Xffffffff);
                            previouspolygon.setStrokeWidth(10);
                        }
                        polygon.setStrokeColor(0XffFFD700);
                        polygon.setStrokeWidth(10);
                        previouspolygon=polygon;
                        myBottomSheet.show(getFragmentManager(), myBottomSheet.getTag());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UsermapCells>> call, Throwable t) {
            }
        });
}
}
