package com.example.user.my25_locationmap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText editText;

    Button button;
    Button button1;
    MarkerOptions myLocationMarker;
//이 자리 에서 커 밋 후 푸 시 까 지 합 니 다 !!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et1);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Log.d(TAG, "구글맵 레디!!");
                map = googleMap;

                map.setMyLocationEnabled(true);


            }

        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        button = findViewById(R.id.bt1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestMyLocation();

            }
        });

    }

    public void requestMyLocation() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 20000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }

            );
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }

            );

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            showCurrentLocation(lastLocation);
        }

    }

    private void showCurrentLocation(Location location) {
        LatLng curpoint = new LatLng(location.getLatitude(), location.getLongitude());
        String msg = "\nLat : [" + location.getLatitude() + "]\n Long : [" + location.getLongitude() + "]";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curpoint, 17));

        //showMyLoc(location);

    }

    private void showMyLoc(Location location) {

        if (myLocationMarker == null) {

            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title("ㆄ\n");
            myLocationMarker.snippet("ㅺㅺgps 확인 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.img));

            map.addMarker(myLocationMarker);

        } else {
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
