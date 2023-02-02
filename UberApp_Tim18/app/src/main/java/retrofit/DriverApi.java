package retrofit;

import java.util.HashMap;
import java.util.Map;

import DTO.RideResponseDTO;
import DTO.RideRetDTOMap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DriverApi {
    @GET("/api/driver/{id}/ride")
    Call<RideRetDTOMap> getRides(@Path("id") int id);

}
