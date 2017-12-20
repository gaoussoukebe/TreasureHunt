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

    public Account(String email, String password, String name) {
        this.email= email;
        this.password = password;
        this.name= name;
    }

    public Account(String email, String password) {
        this.email= email;
        this.password = password;
    }
}
