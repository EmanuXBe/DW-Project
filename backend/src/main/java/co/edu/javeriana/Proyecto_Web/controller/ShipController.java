package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.service.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ship")
@Tag(name = "Ship", description = "Ship management endpoints")  
public class ShipController {

    @Autowired
    private ShipService shipService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    @Operation(summary = "List all ships", description = "Retrieve a list of all ships")
    public List<ShipDTO> displayShips() {
        log.info("Listing ships");
        return shipService.listShips();
    }

    @GetMapping("/{id}")
    @Operation(summary = "View ship details", description = "Retrieve details of a specific ship by ID")
    public ShipDTO viewShip(@Parameter(description = "ID of the ship to retrieve", example = "1")
        @PathVariable Long id) {
        return shipService.searchShip(id).orElseThrow();
    }

    @GetMapping("/create")
    @Operation(summary = "Create a new ship", description = "Initialize a new ship object")
    public ShipDTO createShipPrototype() {
        return new ShipDTO();
    }

    @PostMapping
    @Operation(summary = "Save a new ship", description = "Save a new ship to the database")
    public ShipDTO createShip(@Parameter(description = "Ship data to save")
        @RequestBody ShipDTO dto) {
        dto.setId(0L);
        shipService.save(dto);
        return dto;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ship", description = "Update the details of an existing ship by ID")
    public ShipDTO updateShip(@Parameter(description = "ID of the ship to update", example = "1") @PathVariable Long id, 
        @Parameter(description = "Updated ship data") @RequestBody ShipDTO dto) {
        dto.setId(id);
        shipService.save(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ship", description = "Delete a specific ship by ID")
    public void deleteShip(@Parameter(description = "ID of the ship to delete", example = "1")
        @PathVariable Long id) {
        shipService.delete(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search ships by model name", description = "Search for ships by their model name")
    public List<ShipDTO> searchShips( @Parameter(description = "Text to search in model names", example = "example")
        @RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? shipService.listShips()
                : shipService.searchShipByModel_Name(searchText);
    }
}
