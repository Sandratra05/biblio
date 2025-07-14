package com.springjpa.controller;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Livre;
import com.springjpa.entity.Reservation;
import com.springjpa.entity.ReservationStatut;
import com.springjpa.service.AdherantService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.LivreService;
import com.springjpa.service.ReservationService;
import com.springjpa.service.ReservationStatutService;
import com.springjpa.service.UtilService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationStatutService reservationStatutService;

    private UtilService utilService;

    @GetMapping("/")
    public String formResa(Model model) {
        List<Livre> livres = livreService.findAll();
        List<Adherant> adherants = adherantService.findAll();

        model.addAttribute("books", livres);
        model.addAttribute("adherants", adherants);

        return "form-reservation"; // Redirection vers la page d'accueil
    }

    @PostMapping("/reserveBook")
    public String reserverLivre(@RequestParam(value = "livre", required = true) Integer id_livre,
                                @RequestParam(value = "adherant", required = false) Integer idAdherant,
                                @RequestParam(value = "date", required = false) LocalDate date,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        if (id_livre == null || date == null) {
            redirectAttributes.addFlashAttribute("error", "Le livre et la date sont obligatoires.");
            return "redirect:/reservation";
        }

        Integer idAdhe = null;
        Adherant adherant = (Adherant) session.getAttribute("adherant");
        
        if (adherant != null && adherant.getIdAdherant() > 0) {
            idAdhe = adherant.getIdAdherant();
        } else if (idAdherant != null) {
            idAdhe = idAdherant;
        } else {
            redirectAttributes.addFlashAttribute("error", "Aucun adhérant spécifié.");
            return "redirect:/reservation";
        }

        LocalDateTime dateTime = UtilService.toDateTimeWithCurrentTime(date);
        reservationService.reserverUnLivre(idAdhe, id_livre, dateTime);

        redirectAttributes.addFlashAttribute("success", "Réservation réussie, passez à la bibliothèque le " + date);
        return "redirect:/reservation";
    }


    @GetMapping("/resa-attente")
    public String afficherReservationsAValider(Model model) {
        List<ReservationStatut> enAttente = reservationStatutService.findReservationsEnAttente();
        model.addAttribute("reservationsEnAttente", enAttente);
        return "validation-reservation";
    }

    @PostMapping("/valider")
    public String validerReservation(@RequestParam("idReservation") int idReservation,
                                    RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.findById(idReservation);
        reservationStatutService.validerReservation(reservation);
        redirectAttributes.addFlashAttribute("success", "Réservation validée avec succès !");
        return "redirect:/reservation/resa-attente";
    }

    @PostMapping("/refuser")
    public String refuserReservation(@RequestParam("idReservation") int idReservation,
                                    RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.findById(idReservation);
        reservationStatutService.validerReservation(reservation);
        redirectAttributes.addFlashAttribute("error", "Réservation refusée");
        return "redirect:/reservation/resa-attente";
    }
}