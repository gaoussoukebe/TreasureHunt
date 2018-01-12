package btu.treasurehunt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Accountservice {
    @GET("accounts") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Account>> all();

    @GET("accounts/{id}")
    Call<Account> get(@Path("id") long id);

    @POST("accounts/new")
    Call<Account> create(@Body Account account);
}
