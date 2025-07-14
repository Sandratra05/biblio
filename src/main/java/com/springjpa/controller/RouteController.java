package com.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class RouteController {
    @GetMapping("/")
    public String index() {
        return "redirect:/login/"; // Redirection vers la page d'accueil
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping("/deco")
    public String deco(HttpSession session) {
        session.invalidate(); // Supprime toutes les données de session
        return "redirect:/login/"; // Redirige vers la page de login
    }
}
