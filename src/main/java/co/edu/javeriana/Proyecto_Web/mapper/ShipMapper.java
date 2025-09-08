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