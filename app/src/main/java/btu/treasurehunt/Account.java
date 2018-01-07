package btu.treasurehunt;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

import btu.treasurehunt.Sensors.sensorBatch;

public class Account {

    @SerializedName("id")
    int id;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("name")
    String name;
    @SerializedName("coins")
    int coins;
    @SerializedName("language")
    String language;
    @SerializedName("cells")
    Set<UsermapCells> cells;
    @SerializedName("specialObjects")
    SpecialObjects specialObjects;
    @SerializedName("batches")
    Set<sensorBatch> batches;
    public Account(String email, String password, String name) {
        this.email= email;
        this.password = password;
        this.name= name;
    }
    public Account(int id) {
        this.id=id;
    }


}
