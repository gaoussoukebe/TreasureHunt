package btu.treasurehunt;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("id")
    int id;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("name")
    String name;

    @SerializedName("passwordconfirm")
    String passwordconfirm;



    public Account( int id ,  String email,
             String password,
             String name,String passwordconfirm) {
        this.id = id;
        this.email= email;
        this.password = password;
        this.name= name;
        this.passwordconfirm= passwordconfirm;

    }


    public Account(String email, String password, String name, String passwordconfirm) {
        this.email= email;
        this.password = password;
        this.name= name;
        this.passwordconfirm= passwordconfirm;


    }

    public Account(String email, String password) {
        this.email= email;
        this.password = password;
    }
}
