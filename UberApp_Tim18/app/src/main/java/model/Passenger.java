package model;

import java.util.List;

public class Passenger extends User{
    private List<FavouriteRoute> favouriteRoutes;

    public Passenger() {}

    public Passenger(Integer id, String name, String surname, int profilePicture, String telephoneNumber, String email, String address, boolean blocked, String password, Role role, List<FavouriteRoute> favouriteRoutes) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, blocked, password, role);
        this.favouriteRoutes = favouriteRoutes;
    }

    public List<FavouriteRoute> getFavouriteRoutes() {
        return favouriteRoutes;
    }

    public void setFavouriteRoutes(List<FavouriteRoute> favouriteRoutes) {
        this.favouriteRoutes = favouriteRoutes;
    }
}
