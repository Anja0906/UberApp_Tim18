package retrofit;

import DTO.EmailDTO;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.ResetPasswordDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PassengerApi {
    @POST("/api/user/register")
    Call<PassengerResponseDTO> save(@Body PassengerPostDTO passengerPostDTO);

    @POST("/api/user/{email}")
    Call<User> findByEmail(@Path("email") String email);

    @GET("api/user/{id}/resetPassword")
    Call<String> resetPassRequest(@Path("id") Integer id);

    @PUT("api/user/{id}/resetPassword")
    Call<String> resetPass(@Path("id") Integer id,@Body ResetPasswordDTO resetPass);

}
