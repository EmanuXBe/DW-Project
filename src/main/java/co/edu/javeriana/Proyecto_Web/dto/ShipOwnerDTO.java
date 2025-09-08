package co.edu.javeriana.Proyecto_Web.dto;

public class ShipOwnerDTO {
    private Long shipId;
    private Long ownerId;

    public ShipOwnerDTO() {}

    public ShipOwnerDTO(Long shipId, Long ownerId) {
        this.shipId = shipId;
        this.ownerId = ownerId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

