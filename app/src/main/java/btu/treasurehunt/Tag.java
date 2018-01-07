package btu.treasurehunt;

/**
 * Created by abdirahmanahmed on 12/4/17.
 */


import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class Tag {
    int id;
    int numlayers;
    int currentlayer;
    public Tag(int numlayers, int id,int currentlayer) {
        this.id = id;
        this.numlayers=numlayers;
        this.currentlayer=currentlayer;
    }
}
