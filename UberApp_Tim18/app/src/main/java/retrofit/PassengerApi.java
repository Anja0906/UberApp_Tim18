package retrofit;

import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PassengerApi {
    @POST("/api/passenger")
    Call<PassengerResponseDTO> save(@Body PassengerPostDTO passengerPostDTO);


}
