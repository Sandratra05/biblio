package com.springjpa.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springjpa.dto.ExemplaireDTO;
import com.springjpa.dto.LivreDetailDTO;
import com.springjpa.entity.Categorie;
import com.springjpa.entity.Livre;
import com.springjpa.service.LivreService;
import com.springjpa.service.CategorieService;
import com.springjpa.service.ExemplaireService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/")
    public String livres(Model model) {
        List<Livre> livres = livreService.findAll();
        List<Categorie> categories = categorieService.findAll();
        
        model.addAttribute("livres", livres);
        model.addAttribute("categories", categories);

        return "list-livre"; // Redirection vers la page des livres
    }
    
    // @GetMapping("/detail")
    // public String detailLivre(Model model, Integer id) {
    //     Livre livre = livreService.findById(id);
    //     if (livre == null) {
    //         return "redirect:/livre/"; // Redirection si le livre n'existe pas
    //     }
        
    //     model.addAttribute("livre", livre);
    //     return "detail-livre"; // Redirection vers la page de détail du livre
    // }

    @GetMapping("/detail")
    @ResponseBody
    public LivreDetailDTO detailLivreJson(@RequestParam("id") Integer id) {
        Livre livre = livreService.findById(id);
        if (livre == null) return null;

        LivreDetailDTO dto = new LivreDetailDTO();
        dto.setIdLivre(livre.getIdLivre());
        dto.setTitre(livre.getTitre());
        dto.setIsbn(livre.getIsbn());
        dto.setLangue(livre.getLangue());
        dto.setAnneePublication(livre.getAnneePublication());
        dto.setSynopsis(livre.getSynopsis());
        dto.setNbPage(livre.getNbPage());
        dto.setAgeRequis(livre.getAgeRequis());
        dto.setNomAuteur(livre.getAuteur().getNomAuteur());
        dto.setNomEditeur(livre.getEditeur().getNomEditeur());

        // Catégories
        Set<String> nomsCategories = livre.getCategories().stream()
            .map(cat -> cat.getNomCategorie())
            .collect(Collectors.toSet());
        dto.setCategories(nomsCategories);

        // Exemplaires avec disponibilité
        List<ExemplaireDTO> exemplaireDTOs = livre.getExemplaires().stream()
            .map(ex -> {
                boolean dispo = exemplaireService.isExemplaireDisponible(ex.getIdExemplaire(), LocalDateTime.now(), LocalDateTime.now());
                return new ExemplaireDTO(ex.getIdExemplaire(), dispo);
            })
            .collect(Collectors.toList());
        dto.setExemplaires(exemplaireDTOs);

        return dto;
    }

}