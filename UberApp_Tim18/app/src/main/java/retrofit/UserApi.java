package retrofit;

import java.util.List;

import java.io.UnsupportedEncodingException;


import DTO.JWTResponse;
import DTO.LoginDTO;
import DTO.MessageDTO;
import DTO.MessageResponseDTO;
import DTO.MessageRetDTOMap;
import DTO.UserDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/passenger/find")
    Call<User> findUserByEmail(@Body String email);

    @GET("api/user/almostAll")
    Call<List<UserDTO>> findAlmostAllUsers();

    @GET("api/user/{id}")
    Call<UserDTO> findById(@Path("id") int id);

    @GET("api/user/{id}/message")
    Call<MessageRetDTOMap> findMessages(@Path("id") int id);

    @GET("api/user/{id}/otherUser/{otherId}/message/{rideId}")
    Call<MessageRetDTOMap> findMessages(@Path("id") int id, @Path("otherId") int otherId, @Path("rideId") int rideId);

    @POST("api/user/{id}/message")
    Call<MessageResponseDTO> sendMessage(@Path("id") int id, @Body MessageDTO messageDTO);

    @GET("api/user/{id}")
    Call<User> findUserById(@Path("id")Integer id);

    @GET("/api/user/whoami")
    Call<User> whoami();
}
