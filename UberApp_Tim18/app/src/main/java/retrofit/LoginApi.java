package retrofit;

import DTO.JWTResponse;
import DTO.LoginDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/auth/login")
    Call<JWTResponse> save(@Body LoginDTO loginDTO);
}