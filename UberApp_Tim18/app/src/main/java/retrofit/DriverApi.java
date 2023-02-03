package retrofit;

import java.util.HashMap;
import java.util.Map;

import DTO.DriverDTO;
import DTO.ReportDTO;
import DTO.RideResponseDTO;
import DTO.RideRetDTOMap;
import DTO.VehicleDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DriverApi {
    @GET("/api/driver/{id}/ride")
    Call<RideRetDTOMap> getRides(@Path("id") int id);

    @GET("/api/driver/{id}")
    Call<DriverDTO> getDriver(@Path("id") int id);

    @GET("/api/driver/{id}/vehicle")
    Call<VehicleDTO> getVehicle(@Path("id") int id);

    @GET("/api/reports/{id}/driver")
    Call<ReportDTO> getReports(@Path("id") int id);
    @GET("/api/reports/{id}/driver")
    Call<ReportDTO> getReportsForDates(@Path("id") int id, @Query("from") String from, @Query("to") String to);
    @POST("/api/request/{id}")
    Call<Void> sendChange(@Path("id") int id, @Body DriverDTO driverDTO);
}
