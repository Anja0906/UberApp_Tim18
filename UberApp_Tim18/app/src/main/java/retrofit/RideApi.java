package retrofit;

import java.security.Principal;

import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.ReasonDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RideApi {
    @POST("/api/ride")
    Call<RideResponseDTO> save(@Body RidePostDTO ridePostDTO);

    @GET("api/ride/passenger/{id}/active")
    Call<RideResponseDTO> passengerActiveRide(@Path("id") Integer id);

    @PUT("api/ride/{id}/accept")
    Call<Void> acceptRide(@Path("id") Integer id);

    @PUT("api/ride/{id}/cancel")
    Call<Void> cancelRide(@Path("id") Integer id, @Body ReasonDTO reason);


    @PUT("api/ride/{id}/panic")
    Call<Void> activatePanic(@Path("id") Integer id, @Body ReasonDTO reason);


}
