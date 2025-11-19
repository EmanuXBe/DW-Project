package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User management endpoints")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/list")
    @Operation(summary = "List all users", description = "Retrieve a list of all users")
    public List<UserDTO> displayUsers() {
        log.info("Listing users");
        return userService.listUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/view/{id}")
    @Operation(summary = "View user details", description = "Retrieve details of a specific user by ID")
    public UserDTO viewUser(
            @Parameter(description = "ID of the user to retrieve", example = "1") @PathVariable("id") Long id) {
        return userService.searchUser(id).orElseThrow();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/create")
    @Operation
    public UserDTO createUserPrototype() {
        return new UserDTO();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit/{id}")
    @Operation(summary = "Edit user details", description = "Retrieve user details for editing by ID")
    public UserDTO editUserData(
            @Parameter(description = "ID of the user to edit", example = "1") @PathVariable("id") Long id) {
        return userService.searchUser(id).orElseThrow();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Create a new user", description = "Save a new user to the database")
    public UserDTO createUser(@Parameter(description = "User data to save") @RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user")
    public UserDTO updateUser(@Parameter(description = "Updated user data") @RequestBody UserDTO dto) {
        return userService.updateUser(dto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @Operation(summary = "Delete a user", description = "Delete a specific user by ID")
    public void deleteUser(
            @Parameter(description = "ID of the user to delete", example = "1") @PathVariable("id") Long id) {
        userService.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search")
    @Operation(summary = "Search users by name", description = "Search for users by their name")
    public List<UserDTO> searchUsers(
            @Parameter(description = "Text to search in user names", example = "example") @RequestParam(required = false) String searchText) {
        return (searchText == null || searchText.trim().isEmpty())
                ? userService.listUsers()
                : userService.searchUsersByName(searchText);
    }
}
