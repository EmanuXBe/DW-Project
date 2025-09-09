package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipOwnerDTO;
import co.edu.javeriana.Proyecto_Web.service.UserService;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@RestController
@RequestMapping("/ship")
public class ShipOwnerController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private UserService userService;

    @GetMapping("/{shipId}/owner")
    public ShipOwnerDTO getShipOwner(@PathVariable Long shipId) {
        return shipService.getShipOwner(shipId).orElseThrow();
    }

    @PutMapping("/{shipId}/owner")
    public ShipOwnerDTO updateShipOwner(@PathVariable Long shipId, @RequestBody ShipOwnerDTO shipOwnerDTO) {
        shipOwnerDTO.setShipId(shipId);
        shipService.updateShipOwner(shipOwnerDTO);
        return shipOwnerDTO;
    }

    @GetMapping("/owners")
    public List<UserDTO> getAllOwners() {
        return userService.listUsers();
    }
}
