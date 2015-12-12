package com.example.superjohn.geonews.markerCluster;

import com.example.superjohn.geonews.datastructure.DataUnit;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by superjohn on 15/11/9.
 */
public class MyItem implements ClusterItem{

    private final LatLng mPosition;
    //private DataUnit dataUnit;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        //dataUnit = d;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    //public DataUnit getDataUnit(){return  dataUnit;}
}
