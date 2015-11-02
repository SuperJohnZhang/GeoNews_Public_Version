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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener{

    private GoogleMap Map;
    DataPack mDataPack;


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
        this.initTestData(20);

        // draw marker
        for (int i=0; i<20; i++){
            DataUnit dt = mDataPack.getUniByIndex(i);
            LatLng tmp = new LatLng(dt.getGPSLocation()[0],dt.getGPSLocation()[1]);
            map.addMarker(new MarkerOptions()
                    .position(tmp)
                    .title(dt.getTitle())
                    .snippet(dt.getContent() + "\n" + dt.getLocation() + "\n"))
                    .setAlpha((float)dt.getPopularity()/10);
        }
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
}