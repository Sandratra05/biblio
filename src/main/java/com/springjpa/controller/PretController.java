package com.springjpa.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Livre;
import com.springjpa.entity.Pret;
import com.springjpa.service.AdherantService;
import com.springjpa.service.AdminService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.FinPretService;
import com.springjpa.service.LivreService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.PretService;
import com.springjpa.service.QuotaTypePretService;
import com.springjpa.service.TypePretService;
import com.springjpa.service.UtilService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PretController {

    @Autowired
    private LivreService livreService;

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

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private FinPretService finPretService;

    private void preparePretPage(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }

    @GetMapping("/preter")
    public String preter(Model model) {

        preparePretPage(model);

        return "pret";
    }

    @PostMapping("/preter")
    public String preterLivre(@RequestParam("adherantId") int adherantId,
                              @RequestParam("typePret") int typePretId,  
                              @RequestParam("livre") int livreId,
                              @RequestParam("dateFin") LocalDate dateFin, Model model) {
        Adherant adherant = adherantService.findById(adherantId);
        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = livreService.findAllExemplaireByIdLivre(livre.getIdLivre());
        Exemplaire exemplaireOpt = null;

        if (adherant.getIdAdherant() == null) {
            model.addAttribute("message", "Adhérant inexistant.");
            preparePretPage(model);

            return "pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherantService.isActif(adherant.getIdAdherant(), LocalDateTime.now());
        if (inscrit == false) {
            model.addAttribute("message", "Adhérant non inscrit ou inscription inactive.");

            preparePretPage(model);


            return "pret";
        }

        for (Exemplaire exemplaire : exemplaires) {
            // 3. Le numéro de l'exemplaire doit exister
            exemplaireOpt = exemplaireService.findById(exemplaire.getIdExemplaire());
            if (exemplaireOpt.getIdExemplaire() == null) {
                model.addAttribute("message", "Exemplaire n°" + exemplaire.getIdExemplaire() + " inexistant.");
    
                preparePretPage(model);

                return "pret";
            }

            // 4. L'exemplaire doit être disponible (pas déjà prêté)
            Boolean disponible = exemplaireService.isExemplaireDisponible(exemplaire.getIdExemplaire(), LocalDateTime.now(), UtilService.toDateTimeWithCurrentTime(dateFin));
            if (disponible) {
                break;
            } else {
                continue;
            }
        }
        if (exemplaireOpt == null) {
            model.addAttribute("message", "Il n'y a plus d'exemplaire disponible.");
            preparePretPage(model);
            return "pret";
        }

        // 5. Vérifier si l'adhérant n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherant.getIdAdherant()); 
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
            model.addAttribute("message", "Quota de prêt dépassé.");
            preparePretPage(model);
            
            return "pret";
        }


        // 7. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
        Boolean peutPreter = livreService.peutPreterLivre(adherant, livre);

        if (!peutPreter) {
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre a cause de votre age ou du type de votre profil");
            preparePretPage(model);
            return "pret";
        }

        Pret pret = new Pret(
            LocalDateTime.now(), // Date de début du prêt
            adminService.findById(1), // Admin (à définir selon votre logique, peut-être l'admin connecté)
            typePretService.findById(typePretId), // Type de prêt
            exemplaireOpt, // Exemplaire (le dernier exemplaire vérifié)
            adherant // Adhérant
        );

        FinPret finPret = new FinPret(UtilService.toDateTimeWithCurrentTime(dateFin), pret);
        
        if (exemplaireOpt != null) {
            pretService.save(pret);
            finPretService.save(finPret);
            model.addAttribute("success", "Prêt validé et inséré");
        }

        preparePretPage(model);
        
        
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "pret";
        
    }
}