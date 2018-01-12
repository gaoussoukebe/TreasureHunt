package btu.treasurehunt;


import com.google.gson.annotations.SerializedName;


/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class SpecialObjects {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("text")
    String text;

    @SerializedName("layer")
    int layer;
    @SerializedName("cells")
    Cells cells;
    @SerializedName("account")
    Account account;


    public SpecialObjects(int layer, String name , String text ) {
        this.name = name;
        this.text = text;
        this.layer = layer;


    }



}



