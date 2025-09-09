package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.mapper.ModelMapper;
import co.edu.javeriana.Proyecto_Web.mapper.ShipMapper;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public List<ModelDTO> listModels() {
        return modelRepository.findAll().stream()
                .map(ModelMapper::toDto)
                .toList();
    }

    public Optional<ModelDTO> searchModel(Long id) {
        return modelRepository.findById(id).map(ModelMapper::toDto);
    }

    

     public void save(ModelDTO dto) {
        Model m;
        if (dto.getId() != null && dto.getId() != 0) {
            m = modelRepository.findById(dto.getId()).orElseGet(Model::new);
            m.setId(dto.getId());
        } else {
            m = new Model();
        }
        m.setName(dto.getName());
        m.setColor(dto.getColor());
        modelRepository.save(m);
    }

    public void delete(Long id) {
        modelRepository.deleteById(id);
    }

    public List<ModelDTO> searchModelsByName(String searchText) {
        String q = (searchText == null) ? "" : searchText.trim();
        return modelRepository.findAllByNameStartingWithIgnoreCase(q).stream()
                .map(ModelMapper::toDto)
                .toList();
    }
}

