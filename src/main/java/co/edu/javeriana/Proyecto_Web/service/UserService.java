package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.mapper.UserMapper;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }
}

