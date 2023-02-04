package retrofit;

import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RideApi {
    @POST("/api/ride")
    Call<RideResponseDTO> save(@Body RidePostDTO ridePostDTO);

    @GET("api/ride/passenger/{id}/active")
    Call<RideResponseDTO> passengerActiveRide(@Path("id") Integer id);

}
