package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.Proyecto_Web.model.Ship;

import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;

@Service
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

    public List<Ship> listShips() {
        return shipRepository.findAll();
    }

    public Ship searchShip(Long id) {
        return shipRepository.findById(id).orElse(null);
    }
}
