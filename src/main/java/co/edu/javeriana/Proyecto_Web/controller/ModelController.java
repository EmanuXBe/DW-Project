package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;

@Controller
@RequestMapping("/model")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelService;

    @GetMapping("/list")
    public ModelAndView list() {
        log.info("Listing models");
        List<ModelDTO> models = modelService.listModels();
        ModelAndView mv = new ModelAndView("model-list");
        mv.addObject("modelList", models);
        return mv;
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("model-details");
        mv.addObject("model", modelService.searchModel(id).orElseThrow());
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView mv = new ModelAndView("model-edit");
        mv.addObject("model", new ModelDTO());
        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("model-edit");
        mv.addObject("model", modelService.searchModel(id).orElseThrow());
        return mv;
    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute("model") ModelDTO dto) {
        modelService.save(dto);
        return new RedirectView("/model/list");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id) {
        modelService.delete(id);
        return new RedirectView("/model/list");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String searchText) {
        List<ModelDTO> models = (searchText == null || searchText.trim().isEmpty())
                ? modelService.listModels()
                : modelService.searchModelsByName(searchText);
        ModelAndView mv = new ModelAndView("model-search");
        mv.addObject("modelList", models);
        return mv;
    }
}
