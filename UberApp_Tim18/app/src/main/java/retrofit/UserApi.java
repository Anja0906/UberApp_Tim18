package retrofit;

import DTO.JWTResponse;
import DTO.LoginDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/api/passenger/find")
    Call<User> findUserByEmail(@Body String email);
}
