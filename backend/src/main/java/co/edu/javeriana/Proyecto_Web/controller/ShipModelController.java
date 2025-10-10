package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;
import co.edu.javeriana.Proyecto_Web.service.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ship")
@Tag(name = "ShipModel", description = "Endpoints for managing ships and their models")
public class ShipModelController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ModelService modelService;

    @GetMapping("/{shipId}/model")
    @Operation(summary = "Get ship model details", description = "Retrieve the model details of a specific ship by ship ID")
    public ShipModelDTO getShipModel(@Parameter(description = "ID of the ship to retrieve the model for", example = "1")
        @PathVariable Long shipId) {
        return shipService.getShipModel(shipId).orElseThrow();
    }

    @PutMapping("/{shipId}/model")
    @Operation(summary = "Update ship model details", description = "Update the model details of a specific ship by ship ID")
    public ShipModelDTO updateShipModel(@Parameter(description = "ID of the ship to update the model for", example = "1")
        @PathVariable Long shipId, @RequestBody ShipModelDTO shipModelDTO) {
        shipModelDTO.setShipId(shipId);
        shipService.updateShipModel(shipModelDTO);
        return shipModelDTO;
    }

    @GetMapping("/models")
    @Operation(summary = "List all models", description = "Retrieve a list of all available ship models")
    public List<ModelDTO> getAllModels() {
        return modelService.listModels();
    }
}
