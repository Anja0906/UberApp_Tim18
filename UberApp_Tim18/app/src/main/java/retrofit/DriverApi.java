package retrofit;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import DTO.DriverDTO;
import DTO.ReportDTO;
import DTO.RideResponseDTO;
import DTO.RideRetDTOMap;
import DTO.VehicleDTO;
import model.WorkingTime;
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
    @GET("/api/driver/working-hour/{driverId}/login")
    Call<Integer> workingHourValidation (@Path("driverId") int id);
    @GET("/api/driver/working-hour/{driverId}/logged")
    Call<Integer> checkDriver (@Path("driverId") int id);

    @GET("/api/driver/working-hour/{driverId}/logout")
    Call<Integer> workingHourValidationLogout (@Path("driverId") int id) ;

    @GET("/api/driver/online/{id}")
    Call<Void> onlineDriver (@Path("id") int id);

    @GET("/api/driver/offline/{id}")
    Call<Void> offlineDriver (@Path("id") int id);

    @POST("/api/driver/{id}/working-hour")
    Call<WorkingTime> addWorkingHourForDriver(@Path("id") int id);
}
