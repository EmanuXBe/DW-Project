package co.edu.javeriana.Proyecto_Web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.model.User;
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

    public Optional<UserDTO> searchUser(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    public UserDTO createUser(UserDTO dto) {
        User user;
        if (dto.getId() != null && dto.getId() != 0) {
            user = userRepository.findById(dto.getId()).orElseGet(User::new);
            user.setId(dto.getId());
        } else {
            user = new User();
        }
        user.setName(dto.getName());
        user.setType(dto.getType());
        user.setPassword(dto.getPassword());
        return UserMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> searchUsersByName(String searchText) {
        return userRepository.findAllByNameStartingWithIgnoreCase(searchText).stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDTO updateUser(UserDTO dto) {
        if (dto.getId() == null || dto.getId() == 0) {
            throw new IllegalArgumentException("User ID must be provided for update.");
        }
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getId()));
        user.setName(dto.getName());
        user.setType(dto.getType());
        user.setPassword(dto.getPassword());
        return UserMapper.toDto(userRepository.save(user));
    }
}
