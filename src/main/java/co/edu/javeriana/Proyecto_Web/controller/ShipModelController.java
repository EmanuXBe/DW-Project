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

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;
import co.edu.javeriana.Proyecto_Web.service.ShipService;

@Controller
@RequestMapping("/ship/model")
public class ShipModelController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ModelService modelService;

    @GetMapping("/edit/{shipId}")
    public ModelAndView editShipModelForm(@PathVariable Long shipId) {
        ShipModelDTO shipModelDTO = shipService.getShipModel(shipId).orElseThrow();
        List<ModelDTO> allModels = modelService.listModels();
        ModelAndView mv = new ModelAndView("ship-model-edit");
        mv.addObject("shipModel", shipModelDTO);
        mv.addObject("allModels", allModels);
        return mv;
    }

    @PostMapping("/save")
    public RedirectView saveShipModel(@ModelAttribute ShipModelDTO shipModelDTO) {
        shipService.updateShipModel(shipModelDTO);
        return new RedirectView("/ship/list");
    }
}

