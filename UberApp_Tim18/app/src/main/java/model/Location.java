package model;

import java.io.Serializable;

public class Location  implements Serializable {
    private Integer id;
    private String address;
    private Double latitude;
    private Double longitude;

    public Location() {
    }

    public Location(Integer id, String address, Double latitude, Double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
