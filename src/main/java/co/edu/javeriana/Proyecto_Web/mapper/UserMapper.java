package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.model.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user == null ? null : user.getId());
        if (user != null) {
            dto.setName(user.getName());
        }
        return dto;
    }
}

