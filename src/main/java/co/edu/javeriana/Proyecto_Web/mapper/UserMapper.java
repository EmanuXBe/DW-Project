package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.model.User;

public class UserMapper {


    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setType(user.getType());
        if (user != null) {
            userDTO.setName(user.getName());
        }
        return userDTO;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User u = new User();
        if (dto.getId() != null) {
            u.setId(dto.getId());
        }
        u.setName(dto.getName());
        u.setType(dto.getType());
        u.setPassword(dto.getPassword());
        return u;
    }
}

