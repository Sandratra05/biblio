package com.springjpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.springjpa.service.AdherantService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.PretService;
import com.springjpa.service.QuotaTypePretService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import com.springjpa.dto.AdherantDTO;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Pret;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
// @RequestMapping("/adherants")
public class AdherantController {
    @Autowired
    private AdherantService adherantService;

    @Autowired
    private PretService pretService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private QuotaTypePretService quotaService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("numeroAdherant") int numeroAdherant, 
                            @RequestParam("motDePasse") String motDePasse,
                            HttpSession session,
                            Model model) {
        Adherant adherant = adherantService.authenticate(numeroAdherant, motDePasse);
        
        if (adherant != null) {
            session.setAttribute("adherant", adherant);
            return "redirect:/livre/"; 
        } else {
            model.addAttribute("error", "Numéro d'adhérent ou mot de passe incorrect");
            return "login";
        }
    }

    @GetMapping("/prets-adherant")
    public String afficherPretsAdherant(HttpSession session, Model model) {

        Adherant adherant = (Adherant) session.getAttribute("adherant");
        List<Pret> prets = pretService.findPretEnCours(adherant.getIdAdherant());

        model.addAttribute("prets", prets);
        model.addAttribute("adherant", adherant);
        return "prets-adherant"; // Fichier JSP ou Thymeleaf
    }

    @GetMapping("/adherant")
    public String adherantHome() {
        return "adherant";
    }

    @GetMapping("/dashboard")
    public String dashboardAdmin() {
        return "dashboard";
    }

    @GetMapping("/adherant/detail")
    @ResponseBody
    public AdherantDTO detailAdherant(@RequestParam("id") Integer idAdherant) {
        AdherantDTO dto = new AdherantDTO();

        Adherant adherant = adherantService.findById(idAdherant);

        boolean isActif = adherantService.isActif(idAdherant, LocalDateTime.now());

        boolean isPenalised = penaliteService.isPenalise(LocalDateTime.now(), idAdherant);
        
        Integer quota = quotaService.quotaRestant(idAdherant, adherant.getProfil().getIdProfil(), 1);

        dto.setNumeroAdherant(adherant.getIdAdherant());
        dto.setNom(adherant.getNomAdherant());
        dto.setPrenom(adherant.getPrenomAdherant());
        dto.setEstAbonne(isActif);
        dto.setEstPenalise(isPenalised);
        dto.setQuotaRestant(quota);

        return dto;
    }
}