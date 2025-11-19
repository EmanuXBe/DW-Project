package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/model")
@Tag(name = "Model", description = "Model management endpoints")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    @Operation(summary = "List all models", description = "Retrieve a list of all models")
    public List<ModelDTO> list() {
        log.info("Listing models");
        return modelService.listModels();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @Operation(summary = "View model details", description = "Retrieve details of a specific model by ID")
    public ModelDTO view(@Parameter(description = "ID of the model to retrieve", example = "1") @PathVariable Long id) {
        return modelService.searchModel(id).orElseThrow();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/create")
    @Operation(summary = "Create a new model", description = "Initialize a new model object")
    public ModelDTO createForm() {
        return new ModelDTO();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Save a new model", description = "Save a new model to the database")
    public ModelDTO create(@Parameter(description = "Model data to save") @RequestBody ModelDTO dto) {
        dto.setId(0L);
        modelService.save(dto);
        return dto;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing model", description = "Update the details of an existing model by ID")
    public ModelDTO update(@Parameter(description = "ID of the model to update", example = "1") @PathVariable Long id,
            @Parameter(description = "Updated model data") @RequestBody ModelDTO dto) {
        dto.setId(id);
        modelService.save(dto);
        return dto;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a model", description = "Delete a specific model by ID")
    public void delete(@Parameter(description = "ID of the model to delete", example = "1") @PathVariable Long id) {
        modelService.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search")
    @Operation(summary = "Search models by name", description = "Search for models by their name")
    public List<ModelDTO> search(
            @Parameter(description = "Text to search in model names", example = "example") @RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? modelService.listModels()
                : modelService.searchModelsByName(searchText);
    }
}
