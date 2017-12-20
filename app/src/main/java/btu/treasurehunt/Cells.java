package btu.treasurehunt;

/**
 * Created by abdirahmanahmed on 12/4/17.
 */


import java.util.Set;

/**
 * Created by abdirahmanahmed on 12/1/17.
 */



public class Cells {


    private long id;
    private long numlayers;

    protected Cells() {}
    public Cells(Long numlayers) {
        this.numlayers =  numlayers ;
    }
    private Set<UsermapCells> users;
    private SpecialObjects specialObjects;




}
