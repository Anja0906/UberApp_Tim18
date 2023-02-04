package retrofit;

import java.io.UnsupportedEncodingException;

import DTO.JWTResponse;
import DTO.LoginDTO;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/passenger/find")
    Call<User> findUserByEmail(@Body String email);

    @GET("api/user/{id}")
    Call<User> findUserById(@Path("id")Integer id);
//    @GET
}
//    @GetMapping("{id}/resetPassword")
//    public ResponseEntity<?> changePasswordRequest(@PathVariable("id") int id) {
//        try{
//            User user = userService.findUserById(id);
//            String email = user.getEmail();
//            String token = String.valueOf(userService.generateRandomInt());
//            userService.updateResetPasswordToken(token, email);
//            userService.sendEmail(email, token);
//            return new ResponseEntity<>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
//        }catch (UserNotFoundException e){
//            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);
//        } catch (UnsupportedEncodingException | MessagingException e) {
//            return new ResponseEntity<>("Error while sending email", HttpStatus.NOT_FOUND);
//        }
//    }
    @GET("/api/user/whoami")
    Call<User> whoami();
}
