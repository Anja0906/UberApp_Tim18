package model;

import java.io.Serializable;

public class FavouriteRoute implements Serializable {
    private Integer id;
    private Location departure;
    private Location destination;

    public FavouriteRoute() {
    }

    public FavouriteRoute(Integer id, Location departure, Location destination) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getDeparture() {
        return departure;
    }

    public void setDeparture(Location departure) {
        this.departure = departure;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}
