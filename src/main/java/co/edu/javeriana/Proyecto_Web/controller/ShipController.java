package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;

import co.edu.javeriana.Proyecto_Web.service.ShipService;

@Controller
@RequestMapping("/ship") //se modifico a singular para que quede mejor
public class ShipController {

    @Autowired
    private ShipService shipService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public ModelAndView displayShips() {
        log.info("Listing ships");
        List<ShipDTO> ships = shipService.listShips();
        ModelAndView modelAndView = new ModelAndView("ship-list");
        modelAndView.addObject("shipList", ships);
        return modelAndView;
    }

    @GetMapping("/view/{idShip}")
    public ModelAndView searchShip(@PathVariable("idShip") Long id) {
        ShipDTO ship = shipService.searchShip(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ship-details");
        modelAndView.addObject("ship", ship);
        return modelAndView;
    }
}
