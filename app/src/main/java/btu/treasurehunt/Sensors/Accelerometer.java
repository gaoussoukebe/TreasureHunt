package btu.treasurehunt.Sensors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */





public class Accelerometer {

    @SerializedName("id")
    int id;

    @SerializedName("value")
    String value;

    @SerializedName("type")
    String type;

    @SerializedName("sensorbatch")
    private sensorBatch sensorbatch;


    public Accelerometer(String value, String type) {
        this.value= value;
        this.type= type;
    }


    public Accelerometer(String value) {
        this.value=value;
    }
}

