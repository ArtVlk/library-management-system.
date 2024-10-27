package ru.ArtemVolk.NauJava.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.User;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/find-by-name-and-gender")
    public List<User> findByNameAndGender(@RequestParam String name, @RequestParam String gender) {
        return userRepository.findByNameAndGender(name, gender);
    }

    @GetMapping("/find-by-phone-number")
    public List<User> findByPhoneNumber(@RequestParam String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/find-by-address")
    public User findByAddress(@RequestParam Address address) {
        return userRepository.findByAddress(address);
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}

//http://localhost:8080/users