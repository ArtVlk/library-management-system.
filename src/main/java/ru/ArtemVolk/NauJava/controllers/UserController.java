package ru.ArtemVolk.NauJava.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;
import ru.ArtemVolk.NauJava.details.UserDetailsServiceImpl;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.User;

import java.util.List;

@Controller
@RequestMapping("/custom/users")
public class UserController {
    UserRepository userRepository;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserController(UserRepository userRepository, UserDetailsServiceImpl userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getUsersList(Model model)
    {
        var users = userRepository.findAll();
        model.addAttribute("users", users);
        return "usersTable";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    public User addRegistration(User user) {
        userService.addUser(user);
        return user;
    }
}

//http://localhost:8080/users