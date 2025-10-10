package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.model.Model;

public class ModelMapper {

    public static ModelDTO toDto(Model model) {
        if (model == null){  
            return null;
        }
        ModelDTO dto = new ModelDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setColor(model.getColor());      
        return dto;
    }

    public static Model toEntity(ModelDTO dto) {
        if (dto == null){  
            return null;
        }
        Model m = new Model();
        m.setId(dto.getId());
        m.setName(dto.getName());
        m.setColor(dto.getColor());          
        return m;
    }
}

