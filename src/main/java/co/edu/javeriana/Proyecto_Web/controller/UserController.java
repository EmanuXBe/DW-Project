package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserDTO> displayUsers() {
        log.info("Listing users");
        return userService.listUsers();
    }

    @GetMapping("/view/{id}")
    public UserDTO viewUser(@PathVariable("id") Long id) {
        return userService.searchUser(id).orElseThrow();
    }

    @GetMapping("/create")
    public UserDTO createUserPrototype() {
        return new UserDTO();
    }

    @GetMapping("/edit/{id}")
    public UserDTO editUserData(@PathVariable("id") Long id) {
        return userService.searchUser(id).orElseThrow();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO dto) {
        return userService.updateUser(dto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? userService.listUsers()
                : userService.searchUsersByName(searchText);
    }
}
