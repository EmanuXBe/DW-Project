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



@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView displayUsers() {
        log.info("Listing users");
        List<UserDTO> users = userService.listUsers();
        ModelAndView mv = new ModelAndView("user-list");
        mv.addObject("userList", users);
        return mv;
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewUser(@PathVariable("id") Long id) {
        UserDTO user = userService.searchUser(id).orElseThrow();
        ModelAndView mv = new ModelAndView("user-details");
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createUserForm() {
        ModelAndView mv = new ModelAndView("user-edit");
        mv.addObject("user", new UserDTO());
        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUserForm(@PathVariable("id") Long id) {
        UserDTO user = userService.searchUser(id).orElseThrow();
        ModelAndView mv = new ModelAndView("user-edit");
        mv.addObject("user", user);
        return mv;
    }

    @PostMapping("/save")
    public RedirectView saveUser(@ModelAttribute("user") UserDTO dto) {
        userService.save(dto);
        return new RedirectView("/user/list");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new RedirectView("/user/list");
    }

    @GetMapping("/search")
    public ModelAndView searchUsers(@RequestParam(required = false) String searchText) {
        List<UserDTO> users = (searchText == null || searchText.trim().isEmpty())
                ? userService.listUsers()
                : userService.searchUsersByName(searchText);
        ModelAndView mv = new ModelAndView("user-search");
        mv.addObject("userList", users);
        return mv;
    }
}
