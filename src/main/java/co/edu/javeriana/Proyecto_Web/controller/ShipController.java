package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@RestController
@RequestMapping("/ship")
public class ShipController {

    @Autowired
    private ShipService shipService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public List<ShipDTO> displayShips() {
        log.info("Listing ships");
        return shipService.listShips();
    }

    @GetMapping("/{id}")
    public ShipDTO viewShip(@PathVariable Long id) {
        return shipService.searchShip(id).orElseThrow();
    }

    @GetMapping("/create")
    public ShipDTO createShipPrototype() {
        return new ShipDTO();
    }

    @PostMapping
    public ShipDTO createShip(@RequestBody ShipDTO dto) {
        dto.setId(0L);
        shipService.save(dto);
        return dto;
    }

    @PutMapping("/{id}")
    public ShipDTO updateShip(@PathVariable Long id, @RequestBody ShipDTO dto) {
        dto.setId(id);
        shipService.save(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public void deleteShip(@PathVariable Long id) {
        shipService.delete(id);
    }

    @GetMapping("/search")
    public List<ShipDTO> searchShips(@RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? shipService.listShips()
                : shipService.searchShipByModel_Name(searchText);
    }
}
