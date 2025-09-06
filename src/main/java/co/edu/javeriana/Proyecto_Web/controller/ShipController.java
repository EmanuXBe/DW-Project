package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.edu.javeriana.Proyecto_Web.model.Ship;

import co.edu.javeriana.Proyecto_Web.service.ShipService;

@Controller
@RequestMapping("/ships")
public class ShipController {

    @Autowired
    private ShipService shipService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    void displayShips() {
        log.info("Listing ships");
        List<Ship> ships = shipService.listShips();
        // ModelAndView modelAndView = new ModelAndView(" ships-list");
    }

}
