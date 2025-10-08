package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipOwnerDTO;
import co.edu.javeriana.Proyecto_Web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@RestController
@RequestMapping("/ship")
@Tag(name = "ShipOwner", description = "Endpoints for managing ships and their owners")
public class ShipOwnerController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private UserService userService;

    @GetMapping("/{shipId}/owner")
    @Operation(summary = "Get ship owner details", description = "Retrieve the owner details of a specific ship by ship ID")
    public ShipOwnerDTO getShipOwner(@Parameter(description = "ID of the ship to retrieve the owner for", example = "1")
        @PathVariable Long shipId) {
        return shipService.getShipOwner(shipId).orElseThrow();
    }

    @PutMapping("/{shipId}/owner")
    @Operation(summary = "Update ship owner details", description = "Update the owner details of a specific ship by ship ID")
    public ShipOwnerDTO updateShipOwner(@Parameter(description = "ID of the ship to update the owner for", example = "1")
    @PathVariable Long shipId, @Parameter(description = "Updated ship owner data") @RequestBody ShipOwnerDTO shipOwnerDTO) {
        shipOwnerDTO.setShipId(shipId);
        shipService.updateShipOwner(shipOwnerDTO);
        return shipOwnerDTO;
    }

    @GetMapping("/owners")
    @Operation(summary = "List all ship owners", description = "Retrieve a list of all available ship owners")
    public List<UserDTO> getAllOwners() {
        return userService.listUsers();
    }
}
