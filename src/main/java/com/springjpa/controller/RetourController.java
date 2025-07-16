// RetourController.java
package com.springjpa.controller;

import com.springjpa.entity.Ferie;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Penalite;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Retour;
import com.springjpa.service.FerieService;
import com.springjpa.service.FinPretService;
import com.springjpa.service.PretService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.RetourService;
import com.springjpa.service.UtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @Autowired
    private PretService pretService;

    @Autowired
    private FinPretService finPretService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private FerieService ferieService;

    @GetMapping("/retour")
    public String afficherFormulaireRetour(Model model) {
        model.addAttribute("prets", pretService.findAllEnCoursWithDetails());
        return "retour";
    }

    @PostMapping("/retour")
    public String validerRetour(@RequestParam("idPret") Integer pretId,
                                @RequestParam("dateRetour") LocalDate dateRetour,
                                Model model,
                                RedirectAttributes red) {
        Pret pret = pretService.findById(pretId);

        LocalDateTime dateDeRetour = UtilService.toDateTimeWithCurrentTime(dateRetour);

        if (pret == null) {
            model.addAttribute("error", "Prêt introuvable.");
            model.addAttribute("prets", pretService.findAll());
            return "retour";
        }

        List<Ferie> jourFeries = ferieService.findAll();
        for (Ferie ferie : jourFeries) {
            LocalDate jourFerie = ferie.getJourFerie();
            if (dateRetour.equals(jourFerie)) {
                model.addAttribute("error", "La date de retour tombe un jour férié !");
                model.addAttribute("prets", pretService.findAllEnCoursWithDetails());
                return "retour";
            }
        }

        FinPret finPret = finPretService.findById(pret.getIdPret());
        
        if (dateDeRetour.isAfter(finPret.getDateFin())) {
            penaliteService.appliquerPenaliteSiEnRetard(pret, dateDeRetour);
        }

        //     long joursDeRetard = ChronoUnit.DAYS.between(finPret.getDateFin(), dateDeRetour);
        //     Penalite penalite = new Penalite();
        //     penalite.setAdherant(pret.getAdherant());
        //     penalite.setDuree((int) joursDeRetard);
        //     penalite.setDatePenalite(dateDeRetour);
            
        //     penaliteService.save(penalite);


        Retour retour = new Retour();
        retour.setPret(pret);
        retour.setDateRetour(dateDeRetour);

        retourService.save(retour);

        model.addAttribute("success", "Retour enregistré avec succès.");
        model.addAttribute("prets", pretService.findAllEnCoursWithDetails());

        return "retour";
    }
}
