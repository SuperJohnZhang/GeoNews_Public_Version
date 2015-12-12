package com.example.superjohn.geonews.elements;

import android.graphics.Typeface;
import android.sax.TextElementListener;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.superjohn.geonews.MapsActivity;
import com.example.superjohn.geonews.R;
import com.example.superjohn.geonews.datastructure.DataUnit;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by superjohn on 15/11/30.
 */
public class CustomedInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    LayoutInflater inflater;
    HashMap<LatLng, DataUnit> AllData;
    View v;

    public CustomedInfoWindowAdapter(LayoutInflater i, HashMap<LatLng, DataUnit> d){
        inflater = i;
        AllData = d;
    }

    // the data is stored in marker
    @Override
    public View getInfoWindow(Marker marker) {
        if (inflater == null){
            return null;
        }

        LatLng position = marker.getPosition();

        // setting up the custom window
        v = inflater.inflate(R.layout.element_infowindow, null);
        TextView title = (TextView) v.findViewById(R.id.infoWindow_title);
        title.setText(AllData.get(marker.getPosition()).getTitle());
        title.setTypeface(null, Typeface.BOLD_ITALIC);

        //

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
