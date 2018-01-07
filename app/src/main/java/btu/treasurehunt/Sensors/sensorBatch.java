package btu.treasurehunt.Sensors;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

import btu.treasurehunt.Account;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class sensorBatch {
    @SerializedName("id")
    public int id;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("account")
    public Account account;
    @SerializedName("accelerometer")
    public Accelerometer accelerometer;
    @SerializedName("barometer")
    public Barometer barometer;
    @SerializedName("gravity")
    public Gravity gravity;
    @SerializedName("gyroscope")
    public Gyroscope gyroscope;
    @SerializedName("light")
    public Light light;
    @SerializedName("magnetometer")
    public Magnetometer magnetometer;
    @SerializedName("pedometer")
    public Pedometer pedometer;
    @SerializedName("proximity")
    public Proximity proximity;
    @SerializedName("rotation")
    public Rotation rotation;
    @SerializedName("thermometer")
    public Thermometer thermometer;
    @SerializedName("coins")
    public int coins;
    public sensorBatch (){}
    public sensorBatch (Account account, double latitude, double longitude) {
        this.account= account;
        this.latitude= latitude;
        this.longitude=longitude;
    }
}