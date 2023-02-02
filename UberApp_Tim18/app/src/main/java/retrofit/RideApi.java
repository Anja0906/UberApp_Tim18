package retrofit;

import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RideApi {
    @POST("/api/ride/android")
    Call<RideResponseDTO> save(@Body RidePostDTO ridePostDTO);

}
