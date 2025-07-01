package com.springjpa.controller;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Pret;
import com.springjpa.service.AdherantService;
import com.springjpa.service.AdminService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.PretService;
import com.springjpa.service.QuotaTypePretService;
import com.springjpa.service.TypePretService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PretController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private QuotaTypePretService quotaTypePretService;

    @Autowired 
    private TypePretService typePretService; 

    @Autowired
    private PretService pretService;

    @Autowired
    private AdminService adminService; 


    @GetMapping("/")
    public String index() {
        return "index"; // Redirection vers la page d'accueil
    }

    private void preparePretPage(Model model) {
        model.addAttribute("exemplaires", exemplaireService.findAll());
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }


    @GetMapping("/preter")
    public String preter(Model model) {

        model.addAttribute("exemplaires", exemplaireService.findAll());
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());

        preparePretPage(model);

        return "pret";
    }

    @PostMapping("/preter")
    public String preterLivre(@RequestParam("adherantId") int adherantId,
                              @RequestParam("typePret") int typePretId,  
                              @RequestParam("exemplaires") int[] exemplaireIds, Model model) {
        Adherant adherant = adherantService.findById(adherantId);
        Exemplaire exemplaireOpt = null;
        

        if (adherant.getIdAdherant() == null) {
            model.addAttribute("message", "Adhérant inexistant.");
    
            preparePretPage(model);

            return "pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherantService.isInscri(adherant.getIdAdherant());
        if (!inscrit) {
            model.addAttribute("message", "Adhérant non inscrit ou inscription inactive.");

            preparePretPage(model);


            return "pret";
        }

        for (int exemplaireId : exemplaireIds) {
            // 3. Le numéro de l'exemplaire doit exister
            exemplaireOpt = exemplaireService.findById(exemplaireId);
            if (exemplaireOpt.getIdExemplaire() == null) {
                model.addAttribute("message", "Exemplaire n°" + exemplaireId + " inexistant.");
    
                preparePretPage(model);

                return "pret";
            }

            // 4. L'exemplaire doit être disponible (pas déjà prêté)
            int disponible = exemplaireOpt.isDispo();
            if (disponible == 1) {
                model.addAttribute("message", "Exemplaire n°" + exemplaireId + " non disponible.");
                preparePretPage(model);
                return "pret";
            }

            // 5. Vérifier si l'adhérant n'est pas pénalisé
            boolean penalise = adherantService.isPenalise(adherant.getIdAdherant()); 
            if (penalise) {
                model.addAttribute("message", "Adhérant pénalisé, prêt impossible.");
                preparePretPage(model);

                return "pret";
            }

            // 6. Vérifier que l'adhérant ne dépasse pas le quota pour le type de prêt
            boolean depasseQuota = quotaTypePretService.adherantDepasseQuota(
                adherant.getIdAdherant(),
                adherant.getProfil().getIdProfil(),
                typePretId
            ); 
            if (depasseQuota) {
                model.addAttribute("message", "Quota de prêt dépassé." + depasseQuota);
                preparePretPage(model);
                
                return "pret";
            }

            // 7. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
            // boolean peutPreter= false; /* ...vérifie les éventuelles restrictions (profil, catégorie, etc.)... */;
            // if (!peutPreter) {
            //     model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre.");
            //     return "pret";
            // }
        }

        Pret pret = new Pret(
            LocalDateTime.now(), // Date de début du prêt
            adminService.findById(1), // Admin (à définir selon votre logique, peut-être l'admin connecté)
            typePretService.findById(typePretId), // Type de prêt
            exemplaireOpt, // Exemplaire (le dernier exemplaire vérifié)
            adherant // Adhérant
        );
        

        if (exemplaireOpt != null) {
            exemplaireOpt.setDispo(1);
            exemplaireService.save(exemplaireOpt);
            pretService.save(pret);
            model.addAttribute("message", "Prêt validé et inséré");
        }

        preparePretPage(model);
        
        
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "pret";
        
    }
}