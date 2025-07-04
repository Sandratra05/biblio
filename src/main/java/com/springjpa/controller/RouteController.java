package com.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
    @GetMapping("/")
    public String index() {
        return "index"; // Redirection vers la page d'accueil
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }
}
