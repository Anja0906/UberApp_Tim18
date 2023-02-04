package retrofit;

import DTO.DriverDTO;
import DTO.EmailDTO;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import DTO.ReportDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import DTO.RideRetDTOMap;
import DTO.UserDTO;
import DTO.VehicleDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PassengerApi {
    @POST("/api/user/register")
    Call<PassengerResponseDTO> save(@Body PassengerPostDTO passengerPostDTO);

    @POST("/api/user/findByEmail")
    Call<User> findByEmail(@Body EmailDTO email);

    @GET("/api/user/{id}/ride")
    Call<RideRetDTOMap> getRides(@Path("id") int id);

    @GET("/api/user/{id}")
    Call<UserDTO> getUser(@Path("id") int id);

    @GET("/api/reports/{id}/user")
    Call<ReportDTO> getReports(@Path("id") int id, @Query("page") String page, @Query("size") String size);

    @GET("/api/reports/{id}/user")
    Call<ReportDTO> getReportsForDates(@Path("id") int id, @Query("from") String from, @Query("to") String to, @Query("page") String page, @Query("size") String size);

    @PUT("/api/user/{id}")
    Call<Void> sendChange(@Path("id") int id, @Body UserDTO userDTO);


}
