package com.springjpa.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Admin;
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

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


@Controller
public class PretController {

    // @PostConstruct
    // public void forceStdOutToConsole() {
    //     System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    //     System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    // }

    // private static final Logger logger = LoggerFactory.getLogger(PretController.class);

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
                              @RequestParam("dateDebut") LocalDate dateDebut, 
                              @RequestParam("dateFin") LocalDate dateFin, 
                              HttpSession session,
                              Model model) {

        Adherant adherant = adherantService.findById(adherantId);
        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findAllExemplaireByIdLivre(livre.getIdLivre());
        
        Exemplaire exemplaireOpt = null;

        if (adherant.getIdAdherant() == null) {
            model.addAttribute("message", "Adhérant inexistant.");
            
            preparePretPage(model);

            return "pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        System.out.println("--------- CHECK IF ADHERANT IS ACTIF -----------------");
        boolean inscrit = adherantService.isActif(adherant.getIdAdherant(), LocalDateTime.now());
        if (inscrit == false) {
            model.addAttribute("message", "Adhérant non inscrit ou inscription inactive.");

            preparePretPage(model);


            return "pret";
        }

        System.out.println("----------------- LOOP FOR EXEMPLAIRE");
        for (Exemplaire exemplaire : exemplaires) {
            // 3. Le numéro de l'exemplaire doit exister
            if (exemplaireService.isExemplaireDisponible(exemplaire.getIdExemplaire(), LocalDateTime.now(), UtilService.toDateTimeWithCurrentTime(dateFin))) {
                exemplaireOpt = exemplaire;
                break;
            }
        }


        if (exemplaireOpt == null) {
            model.addAttribute("message", "Il n'y a plus d'exemplaire disponible.");
            preparePretPage(model);
            return "pret";
        }

        // 5. Vérifier si l'adhérant n'est pas pénalisé
        System.out.println("----------------- CHECK IF ADHERANT IS PENALISED -----------------------------");
        boolean penalise = penaliteService.isPenalise(UtilService.toDateTimeWithCurrentTime(dateDebut),adherant.getIdAdherant()); 
        if (penalise) {
            model.addAttribute("message", "Adhérant pénalisé, prêt impossible.");
            preparePretPage(model);

            return "pret";
        }

        // 6. Vérifier que l'adhérant ne dépasse pas le quota pour le type de prêt
        System.out.println("----------------- CHECK IF ADHERANT HAS MORE PRET THAN QUOTA  -----------------------------");
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
        System.out.println("----------------- CHECK IF ADHERANT CAN PRETER THE BOOK -----------------------------");

        Boolean peutPreter = livreService.peutPreterLivre(adherant, livre);

        if (!peutPreter) {
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre a cause de votre age ou du type de votre profil");
            preparePretPage(model);
            return "pret";
        }

        Admin admin = (Admin) session.getAttribute("admin");


        Pret pret = new Pret();
        pret.setAdherant(adherant);
        pret.setAdmin(admin);
        pret.setDateDebut(UtilService.toDateTimeWithCurrentTime(dateDebut));
        pret.setExemplaire(exemplaireOpt);
        pret.setTypePret(typePretService.findById(typePretId));


        FinPret finPret = new FinPret(UtilService.toDateTimeWithCurrentTime(dateFin), pret);
        
        if (exemplaireOpt != null) {
            System.out.println("----------------- SAVE PRET -----------------------------");

            pretService.save(pret);
            finPretService.save(finPret);
            model.addAttribute("success", "Prêt validé et inséré");
        }

        preparePretPage(model);
        
        
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "pret";
        
    }
}