package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import org.springframework.stereotype.Component;

@Component
public class ShipMapper {

    public ShipDTO toDTO(Ship ship) {
        ShipDTO shipDTO = new ShipDTO();
        shipDTO.setId(ship.getId());
        shipDTO.setName(ship.getName());

        // Velocidad y posición
        shipDTO.setVx(ship.getVx());
        shipDTO.setVy(ship.getVy());
        shipDTO.setPosX(ship.getPosX());
        shipDTO.setPosY(ship.getPosY());

        // Estado de carrera
        shipDTO.setTurnCount(ship.getTurnCount());
        shipDTO.setRacing(ship.isRacing());
        shipDTO.setFinished(ship.isFinished());

        if (ship.getModel() != null) {
            shipDTO.setModel(ship.getModel().getName());
            shipDTO.setColor(ship.getModel().getColor());
        }
        if (ship.getOwner() != null) {
            shipDTO.setOwner(ship.getOwner().getName());
        }

        return shipDTO;
    }

    public static ShipDTO toDto(Ship ship) {
        ShipDTO shipDTO = new ShipDTO();
        shipDTO.setId(ship.getId());
        shipDTO.setName(ship.getName());

        // Velocidad y posición
        shipDTO.setVx(ship.getVx());
        shipDTO.setVy(ship.getVy());
        shipDTO.setPosX(ship.getPosX());
        shipDTO.setPosY(ship.getPosY());

        // Estado de carrera
        shipDTO.setTurnCount(ship.getTurnCount());
        shipDTO.setRacing(ship.isRacing());
        shipDTO.setFinished(ship.isFinished());

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

        // Velocidad y posición
        ship.setVx(shipDTO.getVx());
        ship.setVy(shipDTO.getVy());
        ship.setPosX(shipDTO.getPosX());
        ship.setPosY(shipDTO.getPosY());

        // Estado de carrera
        ship.setTurnCount(shipDTO.getTurnCount());
        ship.setRacing(shipDTO.isRacing());
        ship.setFinished(shipDTO.isFinished());

        return ship;
    }
}
