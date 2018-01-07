package btu.treasurehunt.Sensors;


import com.google.gson.annotations.SerializedName;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class Magnetometer {

    @SerializedName("id")
    int id;

    @SerializedName("value")
    String value;

    @SerializedName("type")
    String type;

    @SerializedName("sensorbatch")
sensorBatch sensorbatch;


    public Magnetometer(String value, sensorBatch batch, String type) {
        this.value= value;
        this.type=type;
this.sensorbatch= batch;
    }


}

