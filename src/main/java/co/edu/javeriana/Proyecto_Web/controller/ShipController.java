package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;

import co.edu.javeriana.Proyecto_Web.service.ShipService;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/create")
    public ModelAndView createShipForm() {
        ModelAndView modelAndView = new ModelAndView("ship-edit");
        modelAndView.addObject("ship", new ShipDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editShipForm(@PathVariable("id") Long id) {
        ShipDTO ship = shipService.searchShip(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ship-edit");
        modelAndView.addObject("ship", ship);
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView saveShip(@ModelAttribute("ship") ShipDTO shipDTO){
        shipService.save(shipDTO);
        return new RedirectView("/ship/list");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteShip(@PathVariable("id") Long id) {
        shipService.delete(id);
        return new RedirectView("/ship/list");
    }

    @GetMapping("/search")
    public ModelAndView searchShips(@RequestParam(required = false) String searchText) {
        log.info("Listing ships");
        List<ShipDTO> ships;
        if(searchText== null || searchText.trim().equals("")){
        ships = shipService.listShips();
        } else {
            ships = shipService.searchShipByModel_Name(searchText);
        }
        ModelAndView modelAndView = new ModelAndView("ship-search");
        modelAndView.addObject("shipList", ships);
        return modelAndView;
    }
}
