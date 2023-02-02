package model;

import java.util.Date;
import java.util.List;

public class Passenger extends User{
    private List<FavouriteRoute> favouriteRoutes;

    public Passenger() {}

    public Passenger(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, String verificationCode, String resetPasswordToken, Date timeOfResetPasswordToken, List<Role> roles, List<FavouriteRoute> favouriteRoutes) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active, verificationCode, resetPasswordToken, timeOfResetPasswordToken, roles);
        this.favouriteRoutes = favouriteRoutes;
    }

    public List<FavouriteRoute> getFavouriteRoutes() {
        return favouriteRoutes;
    }

    public void setFavouriteRoutes(List<FavouriteRoute> favouriteRoutes) {
        this.favouriteRoutes = favouriteRoutes;
    }
}
