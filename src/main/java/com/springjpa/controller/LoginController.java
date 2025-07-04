package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.Admin;
import com.springjpa.service.AdherantService;
import com.springjpa.service.AdminService;

import jakarta.persistence.PostLoad;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdherantService adherantService;    
    
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/auth")
    public String authentifier(@RequestParam("typeLogin") String typeLogin,
                               @RequestParam("numeroAdherant") int numero,
                               @RequestParam("motDePasse") String motDePasse,
                               Model model,
                               HttpSession session) {
        if (typeLogin == null || typeLogin.isEmpty()) {
            model.addAttribute("error", "Veuillez choisir un profil.");
            return "login";
        }

        if (typeLogin.equals("1")) {
            Admin admin = adminService.findById(numero);
            if (admin == null || !admin.getPassword().equals(motDePasse)) {
                model.addAttribute("error", "Identifiants Admin incorrects.");
                return "login";
            }
            session.setAttribute("admin", admin);
            return "dashboard"; // page d'accueil admin
        } else if (typeLogin.equals("2")) {
            Adherant adherant = adherantService.findById(numero);
            if (adherant == null || !adherant.getPassword().equals(motDePasse)) {
                model.addAttribute("error", "Identifiants Adhérant incorrects.");
                return "login";
            }
            session.setAttribute("adherant", adherant);
            return "adherant"; // page d'accueil adherant
        }

        model.addAttribute("error", "Profil inconnu.");
        return "login";
    }

    // @GetMapping("/deco")
    // public String deco(HttpSession session) {
    //     session.invalidate(); // Supprime toutes les données de session
    //     return "redirect:/login/"; // Redirige vers la page de login
    // }

    
}


