package com.example.superjohn.geonews.datastructure;

import java.util.Random;

/**
 * Created by superjohn on 15/10/19.
 */
public class DataUnit {

    private static final String[] ConcreteLocations= {"Sydney", "Hong Kong", "Beijing", "Chicago",
        "Paris", "San Diego", "New York City", "London"};
    private static final double[][] GPSReference = {{-33.8650, 151.2094}, {22.2783, 114.1747},
            {39.9167, 116.3833},{41.8369, -87.6847},{48.8567, 2.3508},{32.7150, -117.1625},
            {40.7127, -74.0059},{51.5072, -0.1275}};

    private String title;
    private String location;
    private String date;
    private String content;
    private String link;
    private int popularity;             // popularity varies from 1-10
    private double[] GPSLocation = new double[2];

    public DataUnit() {
        // if the parameter is not specified, first create a random unit
        Random rand = new Random();
        int randomNumber = rand.nextInt(200);
        title = "random news"+randomNumber;
        content = "random content" + randomNumber;
        popularity = randomNumber%10;
        location = ConcreteLocations[randomNumber%8];
        GPSLocation = GPSReference[randomNumber%8];
    }

    public DataUnit(String[] strings){
        title = strings[1];
        location = strings[2];
        date = strings[3];
        content = strings[4];
        link = strings[5];
        popularity = Integer.parseInt(strings[6]);
    }

    // accessor
    public String getTitle(){
        return title;
    };

    public String getLocation(){
        return location;
    };
    public String getDate(){
        return date;
    };
    public String getContent(){
        return content;
    };
    public String getLink(){
        return link;
    };

    public int getPopularity(){
        return popularity;
    };

    public double[] getGPSLocation(){
        return GPSLocation;
    };

}
