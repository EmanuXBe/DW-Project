package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipOwnerDTO;
import co.edu.javeriana.Proyecto_Web.service.UserService;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@Controller
@RequestMapping("/ship/owner")
public class ShipOwnerController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private UserService userService;

    @GetMapping("/edit/{shipId}")
    public ModelAndView editShipOwnerForm(@PathVariable Long shipId) {
        ShipOwnerDTO shipOwnerDTO = shipService.getShipOwner(shipId).orElseThrow();
        List<UserDTO> allUsers = userService.listUsers();
        ModelAndView mv = new ModelAndView("ship-owner-edit");
        mv.addObject("shipOwner", shipOwnerDTO);
        mv.addObject("allUsers", allUsers);
        return mv;
    }

    @PostMapping("/save")
    public RedirectView saveShipOwner(@ModelAttribute ShipOwnerDTO shipOwnerDTO) {
        shipService.updateShipOwner(shipOwnerDTO);
        return new RedirectView("/ship/list");
    }
}

