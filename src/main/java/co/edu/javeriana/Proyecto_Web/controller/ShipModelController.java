package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@RestController
@RequestMapping("/ship")
public class ShipModelController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ModelService modelService;

    @GetMapping("/{shipId}/model")
    public ShipModelDTO getShipModel(@PathVariable Long shipId) {
        return shipService.getShipModel(shipId).orElseThrow();
    }

    @PutMapping("/{shipId}/model")
    public ShipModelDTO updateShipModel(@PathVariable Long shipId, @RequestBody ShipModelDTO shipModelDTO) {
        shipModelDTO.setShipId(shipId);
        shipService.updateShipModel(shipModelDTO);
        return shipModelDTO;
    }

    @GetMapping("/models")
    public List<ModelDTO> getAllModels() {
        return modelService.listModels();
    }
}
