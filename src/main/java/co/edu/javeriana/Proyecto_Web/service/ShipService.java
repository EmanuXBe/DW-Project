package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipOwnerDTO;
import co.edu.javeriana.Proyecto_Web.mapper.ShipMapper;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.model.Ship;

import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;

@Service
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ShipDTO> listShips() {
        return shipRepository.findAll().stream()
                .map(ShipMapper::toDto)
                .toList();
    }

    public Optional<ShipDTO> searchShip(Long id) {
        return shipRepository.findById(id)
                .map(ShipMapper::toDto);
    }

    public void save(ShipDTO shipDTO){
        Ship ship;
        if (shipDTO.getId() != 0) {
            ship = shipRepository.findById(shipDTO.getId()).orElseGet(Ship::new);
            ship.setId(shipDTO.getId());
        } else {
            ship = new Ship();
        }

        ship.setName(shipDTO.getName());
        ship.setXspeed(shipDTO.getXspeed());
        ship.setYspeed(shipDTO.getYspeed());

        // Handle model name and color according to spec (reuse if exists)
        String modelName = shipDTO.getModel();
        String modelColor = shipDTO.getColor();
        Model model = null;
        if (modelName != null && modelColor != null) {
            model = modelRepository.findByNameAndColor(modelName, modelColor)
                    .orElseGet(() -> {
                        Model m = new Model(modelName, modelColor);
                        return modelRepository.save(m);
                    });
        }
        ship.setModel(model);

        shipRepository.save(ship);
    }

    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    // Association helpers following professor's pattern
    public Optional<ShipModelDTO> getShipModel(Long shipId) {
        return shipRepository.findById(shipId)
                .map(ship -> new ShipModelDTO(ship.getId(), ship.getModel() != null ? ship.getModel().getId() : null));
    }

    public void updateShipModel(ShipModelDTO dto) {
        Ship ship = shipRepository.findById(dto.getShipId()).orElseThrow();
        Model model = (dto.getModelId() == null) ? null : modelRepository.findById(dto.getModelId()).orElseThrow();
        ship.setModel(model);
        shipRepository.save(ship);
    }

    public Optional<ShipOwnerDTO> getShipOwner(Long shipId) {
        return shipRepository.findById(shipId)
                .map(ship -> new ShipOwnerDTO(ship.getId(), ship.getOwner() != null ? ship.getOwner().getId() : null));
    }

    public void updateShipOwner(ShipOwnerDTO dto) {
        Ship ship = shipRepository.findById(dto.getShipId()).orElseThrow();
        var owner = (dto.getOwnerId() == null) ? null : userRepository.findById(dto.getOwnerId()).orElseThrow();
        ship.setOwner(owner);
        shipRepository.save(ship);
    }
}
