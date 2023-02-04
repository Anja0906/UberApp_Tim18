package retrofit;

import java.io.UnsupportedEncodingException;

import DTO.JWTResponse;
import DTO.LoginDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/passenger/find")
    Call<User> findUserByEmail(@Body String email);

    @GET("api/user/{id}")
    Call<User> findUserById(@Path("id")Integer id);

    @GET("/api/user/whoami")
    Call<User> whoami();
}
