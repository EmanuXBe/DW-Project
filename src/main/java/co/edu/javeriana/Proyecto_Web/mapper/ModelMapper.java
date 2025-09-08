package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.model.Model;

public class ModelMapper {

    public static ModelDTO toDto(Model model) {
        ModelDTO dto = new ModelDTO();
        dto.setId(model == null ? null : model.getId());
        if (model != null) {
            dto.setName(model.getName());
            dto.setColor(model.getColor());
        }
        return dto;
    }

    public static Model toEntity(ModelDTO dto) {
        Model model = new Model();
        if (dto.getId() != null) {
            // id is managed by persistence; still copying for update scenarios
        }
        model.setName(dto.getName());
        model.setColor(dto.getColor());
        return model;
    }
}

