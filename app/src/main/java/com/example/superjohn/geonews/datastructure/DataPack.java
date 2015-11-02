package com.example.superjohn.geonews.datastructure;

import java.util.Vector;

/**
 * Created by superjohn on 15/10/19.
 */
public class DataPack {

    Vector<DataUnit> data = new Vector<DataUnit>();

    // if the source is not specificied, make a random generated datapack
    public DataPack(int number){
        int i=0;
        while (i<number) {
            DataUnit tmp = new DataUnit();
            data.add(tmp);
            i++;
        }
    }

    public DataUnit getUniByIndex(int i){
        return data.get(i);
    }

}
