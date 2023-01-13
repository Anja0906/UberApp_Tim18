package model;

import java.io.Serializable;

public class VehicleType  implements Serializable {
    private Integer id;
    private VehicleName vehicleName;
    private double priceForKm;

    public VehicleType() {
    }

    public VehicleType(Integer id, VehicleName vehicleName, double priceForKm) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.priceForKm = priceForKm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleName getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(VehicleName vehicleName) {
        this.vehicleName = vehicleName;
    }

    public double getPriceForKm() {
        return priceForKm;
    }

    public void setPriceForKm(double priceForKm) {
        this.priceForKm = priceForKm;
    }
}
