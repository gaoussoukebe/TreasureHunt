package btu.treasurehunt;

/**
 * Created by abdirahmanahmed on 12/4/17.
 */


import com.google.gson.annotations.SerializedName;

import java.util.Set;

import btu.treasurehunt.Sensors.sensorBatch;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class Cells {


    @SerializedName("id")
    int id;

    @SerializedName("numlayers")
    int numlayers;


    private sensorBatch sensorbatch;


    public Cells(int numlayers) {
        this.numlayers = numlayers;

    }
}
