package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.model.Ship;

public class ShipMapper {

    public static ShipDTO toDto(Ship ship) {

        ShipDTO shipDTO = new ShipDTO();
        shipDTO.setId(ship.getId());
        shipDTO.setName(ship.getName());
        shipDTO.setXspeed(ship.getXspeed());
        shipDTO.setYspeed(ship.getYspeed());
        if (ship.getModel() != null) {
            shipDTO.setModel(ship.getModel().getName());
            shipDTO.setColor(ship.getModel().getColor());
        }
        if (ship.getOwner() != null) {
            shipDTO.setOwner(ship.getOwner().getName());
        }

        return shipDTO;
    }

    public static Ship toEntity(ShipDTO shipDTO) {
        
        Ship ship = new Ship();
        ship.setId(shipDTO.getId());
        ship.setName(shipDTO.getName());
        ship.setXspeed(shipDTO.getXspeed());
        ship.setYspeed(shipDTO.getYspeed());

        return ship;
    }
}
