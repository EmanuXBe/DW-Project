package co.edu.javeriana.Proyecto_Web.dto;

public class ShipModelDTO {
    private Long shipId;
    private Long modelId;

    public ShipModelDTO() {}

    public ShipModelDTO(Long shipId, Long modelId) {
        this.shipId = shipId;
        this.modelId = modelId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}

