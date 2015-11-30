package com.example.superjohn.geonews.elements;

import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;

import com.example.superjohn.geonews.MapsActivity;
import com.example.superjohn.geonews.R;
import com.example.superjohn.geonews.datastructure.DataUnit;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by superjohn on 15/11/30.
 */
public class CustomedInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    LayoutInflater inflater;
    View v;

    public CustomedInfoWindowAdapter(LayoutInflater i){
        inflater = i;
    }

    // the data is stored in marker
    @Override
    public View getInfoWindow(Marker marker) {
        if (inflater == null){
            return null;
        }
        // setting up the custom window
        v = inflater.inflate(R.layout.element_infowindow ,null);
        

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
