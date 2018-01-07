package btu.treasurehunt;

/**
 * Created by abdirahmanahmed on 12/4/17.
 */

import com.google.gson.annotations.SerializedName;



/**
 * Created by abdirahmanahmed on 12/1/17.
 */




public class UsermapCells {



    @SerializedName("id")
    int id;

    @SerializedName("currentlayer")
    int currentlayer;
    @SerializedName("cells")
    Cells cells;
    @SerializedName("account")
    Account account;
    public UsermapCells(int currentlayer) {
        this.currentlayer = currentlayer;

    }
    public UsermapCells(int currentlayer,int id) {
        this.cells=new Cells(0,id);
    }
}
