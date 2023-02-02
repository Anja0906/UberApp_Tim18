package retrofit;

import DTO.EmailDTO;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PassengerApi {
    @POST("/api/user/register")
    Call<PassengerResponseDTO> save(@Body PassengerPostDTO passengerPostDTO);

    @POST("/api/user/findByEmail")
    Call<User> findByEmail(@Body EmailDTO email);


}
