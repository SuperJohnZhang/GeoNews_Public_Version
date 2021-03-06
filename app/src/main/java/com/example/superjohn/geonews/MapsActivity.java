package com.example.superjohn.geonews;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.superjohn.geonews.datastructure.DataUnit;
import com.example.superjohn.geonews.elements.CustomedInfoWindowAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.superjohn.geonews.datastructure.DataPack;
import com.example.superjohn.geonews.markerCluster.MyItem;
import com.google.maps.android.clustering.ClusterManager;
import com.example.superjohn.geonews.elements.CustomSlidingPaneLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap Map;
    private SupportMapFragment mapFragment;
    DataPack mDataPack;

    // the database
    private static final String FIREBASE_URL = "https://geonews.firebaseio.com/";
    private Firebase mFirebaseRef;
    private ArrayList<DataUnit> GeoNews_Data;
    private Class<DataUnit> DataUnitModel;

    // the map use marker as key and use mydataobj as pivot
    private HashMap<LatLng, DataUnit> allMarkersData = new HashMap<LatLng, DataUnit>();


    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    // for the right panal
    RelativeLayout mRightPanel;

    // for the slide menu
    Button mSetting;
    CustomSlidingPaneLayout mSlidingPanel;
    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener(){
        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub
            getActionBar().setTitle(getString(R.string.app_name));
        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub
            getActionBar().setTitle("Menu Titles");
        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }
    };
    String [] MenuTitles = new String[]{"Account","Filter","Others", "Quit"};
    String [] OthersOption = new String[]{"About", "Rating", "Back"};
    String [] AccountOption = new String[]{"Back"};
    String [] FilterOption = new String[]{"Field", "Language", "Region", "Time", "Back"};
    ListView mMenuList;
    ArrayAdapter<String> SlidingPaneContent;
    TextView TitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // assume the database is added at this part

        //List<DataUnit> data;
        //Set up the REST API
        Firebase.setAndroidContext(this);
        mFirebaseRef=new Firebase(FIREBASE_URL).child("BBCnews");
        GeoNews_Data=new ArrayList<>();
        DataUnitModel=DataUnit.class;
        Query mRef= mFirebaseRef.orderByChild("popularity").limitToFirst(200);
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap my_outer_map= (HashMap)snapshot.getValue();
                Iterator map_iterator = my_outer_map.keySet().iterator();
                HashMap my_inner_map;
                while(map_iterator.hasNext()) {
                    my_inner_map = (HashMap) my_outer_map.get(map_iterator.next().toString());
                    String title=my_inner_map.get("title").toString();
                    String location= my_inner_map.get("location").toString();
                    String date=my_inner_map.get("location").toString();
                    String content=my_inner_map.get("location").toString();
                    String link=my_inner_map.get("link").toString();
                    String popularity=my_inner_map.get("popularity").toString();
                    String[] strings={"begin",title,location,date,content,link,popularity};
                    DataUnit piece_of_new=new DataUnit(strings);
                    GeoNews_Data.add(piece_of_new);
                    //Log.e("The title is ", my_inner_map.get("title").toString());
                    //Log.e("The location is ",my_inner_map.get("location").toString());
                    //Log.e("The content is ", my_inner_map.get("content").toString());
                }

                int size= GeoNews_Data.size();
                Log.e("GeoNews_Data have ", String.valueOf(size));

                System.out.println(size+"apple");

                for (int i=0; i<size; ++i){
                    DataUnit temp_data=GeoNews_Data.get(i);
                    Log.e("The title is ", temp_data.getTitle());
                    Log.e("The location is ",temp_data.getLocation());
                    Log.e("The content is ", temp_data.getContent());
                }

                // set up the cluster when the data is changed
                MapsActivity.this.setUpClusterer();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        // then deal with the data ---- do that after talk




        // now deal with the slide pane
        mSlidingPanel = (CustomSlidingPaneLayout) findViewById(R.id.root_view);
        mSetting = (Button) findViewById(R.id.settings);
        mSetting.setBackgroundColor(Color.TRANSPARENT);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MapsActivity.this.mSlidingPanel.isOpen()) {
                    MapsActivity.this.mSlidingPanel.openPane();
                    v.setAlpha(0);
                }
            }
        });

        mMenuList = (ListView) findViewById(R.id.MenuList);
        //appImage = (ImageView)findViewById(android.R.id.home);
        //TitleText = (TextView)findViewById(android.R.id.title);
        SlidingPaneContent = new ArrayAdapter(this, android.R.layout.simple_list_item_1,MenuTitles);
        mMenuList.setAdapter(SlidingPaneContent);
        mMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // if select account
                if (((TextView) view).getText().equals(MapsActivity.this.MenuTitles[0])) {
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.AccountOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }

                // if select filter
                else if (((TextView) view).getText().equals(MapsActivity.this.MenuTitles[1])) {
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.FilterOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }

                // if select Others
                else if (((TextView) view).getText().equals(MapsActivity.this.MenuTitles[2])) {
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.OthersOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                } else if (((TextView) view).getText().equals(MapsActivity.this.MenuTitles[3])) {
                    MapsActivity.this.finish();

                }

                // back node on others
                else if ((((TextView) view).getText().equals(MapsActivity.this.OthersOption[2]) && position == 2)
                        || (((TextView) view).getText().equals(MapsActivity.this.AccountOption[0]) && position == 0)
                        || (((TextView) view).getText().equals(MapsActivity.this.FilterOption[4]) && position == 4)) {
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.MenuTitles);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }
            }
        });

        //mMenuList.click
        //mSlidingPanel.setPanelSlideListener(panelListener);
        //mSlidingPanel.setParallaxDistance(200);
        //getActionBar().setDisplayShowHomeEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Map = mapFragment.getMap();
        Map.setOnMapClickListener(this);
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
        //this.setUpClusterer();

        // set up the layout of infoWindow
        this.setUpInfoWindow();

        // get to the current location
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            private boolean state = true;

            @Override
            public void onMyLocationChange(Location location) {
                if (state == true) {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    if (MapsActivity.this.Map != null) {
                        MapsActivity.this.Map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                        state = false;
                    }
                }
            }
        });


    }

    @Override
    public void onMapClick(LatLng point) {
        //Map.moveCamera(CameraUpdateFactory.newLatLng(point));
        if (MapsActivity.this.mSlidingPanel.isOpen()){
            MapsActivity.this.mSlidingPanel.closePane();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //marker.setTitle("test");
        marker.setSnippet("this is a test");
        marker.showInfoWindow();
        //System.out.println("666666");
        return true;                                // if returen true, the default event will not occur
    }

    public void openFilter(View view){
        Intent intent = new Intent(this, FilterActivity.class);
        this.startActivity(intent);
    }

    public void initTestData(int num){
        mDataPack = new DataPack(num);
    }

    private void setUpInfoWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        CustomedInfoWindowAdapter cuswin = new CustomedInfoWindowAdapter(inflater, allMarkersData);
        Map.setInfoWindowAdapter(cuswin);
    }


    private void setUpClusterer() {


        // Position the map.
        //Map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, Map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        Map.setOnCameraChangeListener(mClusterManager);
        Map.setOnMarkerClickListener(MapsActivity.this);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }



    private void addItems() {
        int dataIndex = 1;

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        while(GeoNews_Data.isEmpty()){

        }
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
            try {
                System.out.println(GeoNews_Data.get(i).getTitle());
                allMarkersData.put(offsetItem.getPosition(), GeoNews_Data.get(i));
            }catch (IndexOutOfBoundsException e){
                System.out.println("太快了");
                allMarkersData.put(offsetItem.getPosition(),new DataUnit());
            }

        }

    }

    /*
    private void setMarkerData(){
        Collection<Marker> allMarkers = mClusterManager.getClusterMarkerCollection().getMarkers();
        int i=0;
        for(Marker m: allMarkers){
            try {
                allMarkersData.put(m, GeoNews_Data.get(i));
            }catch (IndexOutOfBoundsException e){
                allMarkersData.put(m,new DataUnit());
            }
            i++;
        }

    }
    */

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}