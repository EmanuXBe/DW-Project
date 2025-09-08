package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.mapper.ModelMapper;
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

    public Optional<ModelDTO> find(Long id) {
        return modelRepository.findById(id).map(ModelMapper::toDto);
    }
}

