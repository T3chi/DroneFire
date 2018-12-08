package droneapp.drone1.dronefire;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    private Polygon drawForestFire(GoogleMap googleMap, Iterable<LatLng> points) {
        Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                .addAll(points)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));
        return polygon;
    }

    private void addDroneMarker(GoogleMap googleMap, Drone... drones) {
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        for (Drone d : drones)
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(d.getLatitude(), d.getLongitude()))
                    .title(d.getName())
                    .draggable(true));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });
        Drone[] drones = {new Drone(37.1, -119.1), new Drone(36.8, -118.9)};
        addDroneMarker(mMap, drones);
        ArrayList<LatLng> firePoints = new ArrayList<LatLng>();
        firePoints.add(new LatLng(-30, 143));
        firePoints.add(new LatLng(-35, 140));
        firePoints.add(new LatLng(-25, 144));
        //drawForestFire(mMap,firePoints);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(drones[0].getLatitude(), drones[0].getLongitude())));
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(37, -119))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));
        /*List<LatLng> forestFireXY = new ArrayList<LatLng>();
        forestFireXY.add(new LatLng(-30,145));
        forestFireXY.add(new LatLng(-25,150));
        forestFireXY.add(new LatLng(-35,140));
        drawForestFire(mMap,forestFireXY);*/
    }
}