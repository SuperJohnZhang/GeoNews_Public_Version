package com.example.superjohn.geonews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.superjohn.geonews.datastructure.DataUnit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.superjohn.geonews.datastructure.DataPack;
import com.example.superjohn.geonews.markerCluster.MyItem;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener{

    private GoogleMap Map;
    DataPack mDataPack;
    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Map = mapFragment.getMap();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-33.796923, 150.922433);
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.setMyLocationEnabled(true);
        // configure Ui settings
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);

        // listeners
        //map.setOnMapClickListener(this);

        // init data
        //this.initTestData(20);

        // draw marker
        /*for (int i=0; i<20; i++){
            DataUnit dt = mDataPack.getUniByIndex(i);
            LatLng tmp = new LatLng(dt.getGPSLocation()[0],dt.getGPSLocation()[1]);
            map.addMarker(new MarkerOptions()
                    .position(tmp)
                    .title(dt.getTitle())
                    .snippet(dt.getContent() + "\n" + dt.getLocation() + "\n"))
                    .setAlpha((float)dt.getPopularity()/10);
        }*/
        this.setUpClusterer();
    }

    @Override
    public void onMapClick(LatLng point) {
        Map.moveCamera(CameraUpdateFactory.newLatLng(point));

    }

    public void openFilter(View view){
        Intent intent = new Intent(this, FilterActivity.class);
        this.startActivity(intent);
    }

    public void initTestData(int num){
        mDataPack = new DataPack(num);
    }

    private void setUpClusterer() {


        // Position the map.
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, Map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        Map.setOnCameraChangeListener(mClusterManager);
        Map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

}