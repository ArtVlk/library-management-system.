package ru.ArtemVolk.NauJava.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-work")
public class TestController {
    @GetMapping(value = "/html", produces = {"text/html"})
    public String get() {
        return "userList.html";
    }

}
