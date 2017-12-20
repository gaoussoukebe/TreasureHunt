package btu.treasurehunt.Sensors;


import com.google.gson.annotations.SerializedName;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class Pedometer {


    @SerializedName("id")
    int id;

    @SerializedName("value")
    String value;

    @SerializedName("type")
    String type;

    private sensorBatch sensorbatch;


    public Pedometer(String value, String type) {
        this.value= value;
        this.type= type;
    }

}

