package com.example.superjohn.geonews;

import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.superjohn.geonews.datastructure.DataUnit;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap Map;
    private SupportMapFragment mapFragment;
    DataPack mDataPack;

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

        // now deal with the slide pane
        mSlidingPanel = (CustomSlidingPaneLayout) findViewById(R.id.root_view);
        mSetting = (Button) findViewById(R.id.settings);
        mSetting.setBackgroundColor(Color.TRANSPARENT);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MapsActivity.this.mSlidingPanel.isOpen()) {
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
                if(((TextView)view).getText().equals(MapsActivity.this.MenuTitles[0])){
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.AccountOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }

                // if select filter
                else if(((TextView)view).getText().equals(MapsActivity.this.MenuTitles[1])){
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.FilterOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }

                // if select Others
                else if(((TextView)view).getText().equals(MapsActivity.this.MenuTitles[2])){
                    MapsActivity.this.SlidingPaneContent = new ArrayAdapter<String>(MapsActivity.this,
                            android.R.layout.simple_list_item_1, MapsActivity.this.OthersOption);
                    MapsActivity.this.mMenuList.setAdapter(MapsActivity.this.SlidingPaneContent);
                }

                else if(((TextView)view).getText().equals(MapsActivity.this.MenuTitles[3])){
                    MapsActivity.this.finish();

                }

                // back node on others
                else if((((TextView)view).getText().equals(MapsActivity.this.OthersOption[2]) && position == 2)
                        || (((TextView)view).getText().equals(MapsActivity.this.AccountOption[0]) && position == 0)
                        || (((TextView)view).getText().equals(MapsActivity.this.FilterOption[4]) && position == 4)){
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
        /*
        Map.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                //System.out.println(66666);
                return false;
            }
        });
        */
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
        this.goToCurrentLocation();
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
        return false;
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
        Map.setOnMarkerClickListener(this);

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

    private void goToCurrentLocation(){
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        try {
            Location myLocation = locationManager.getLastKnownLocation(provider);
            for(int i=0; i<100; i++) {
                myLocation = locationManager.getLastKnownLocation(provider);
            }
            // Get latitude of the current location
            double latitude = myLocation.getLatitude();

            // Get longitude of the current location
            double longitude = myLocation.getLongitude();

            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Show the current location in Google Map
            Map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }catch (SecurityException e){
            System.out.println("That's gg");
            e.printStackTrace();
        }

        // Zoom in the Google Map
        Map.animateCamera(CameraUpdateFactory.zoomTo(20));
    }

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