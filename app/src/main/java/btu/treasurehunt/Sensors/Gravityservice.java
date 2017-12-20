package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Gravityservice {
    @GET("accounts") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Account>> all();

    @GET("accounts/{isbn}")
    Call<Account> get(@Path("isbn") String id);

    @POST("accounts/new")
    Call<Account> create(@Body Account account);





}
