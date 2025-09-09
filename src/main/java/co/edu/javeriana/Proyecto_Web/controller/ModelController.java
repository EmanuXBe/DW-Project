package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;

@RestController
@RequestMapping("/model")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelService;

    @GetMapping
    public List<ModelDTO> list() {
        log.info("Listing models");
        return modelService.listModels();
    }

    @GetMapping("/{id}")
    public ModelDTO view(@PathVariable Long id) {
        return modelService.searchModel(id).orElseThrow();
    }

    @GetMapping("/create")
    public ModelDTO createForm() {
        return new ModelDTO();
    }

    @PostMapping
    public ModelDTO create(@RequestBody ModelDTO dto) {
        dto.setId(0L);
        modelService.save(dto);
        return dto;
    }

    @PutMapping("/{id}")
    public ModelDTO update(@PathVariable Long id, @RequestBody ModelDTO dto) {
        dto.setId(id);
        modelService.save(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        modelService.delete(id);
    }

    @GetMapping("/search")
    public List<ModelDTO> search(@RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? modelService.listModels()
                : modelService.searchModelsByName(searchText);
    }
}
