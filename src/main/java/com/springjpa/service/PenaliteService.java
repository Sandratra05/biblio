package com.springjpa.service;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Penalite;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Retour;
import com.springjpa.repository.AdherantRepository;
import com.springjpa.repository.FinPretRepository;
import com.springjpa.repository.PenaliteRepository;
import com.springjpa.repository.PretRepository;
import com.springjpa.repository.RetourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PenaliteService {
    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private FinPretRepository finPretRepository;

    @Autowired
    private RetourRepository retourRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private AdherantRepository adherantRepository;

    public List<Penalite> findAll() {
        return penaliteRepository.findAll();
    }

    public Optional<Penalite> findById(Integer id) {
        return penaliteRepository.findById(id);
    }

    public List<Penalite> findByAdherantId(Integer idAdherant) {
        return penaliteRepository.findByAdherantIdAdherant(idAdherant);
    }

    public Penalite save(Penalite penalite) {
        return penaliteRepository.save(penalite);
    }

    public void deleteById(Integer id) {
        penaliteRepository.deleteById(id);
    }

    // public void appliquerPenaliteSiEnRetard(Pret pret, LocalDateTime dateRetour) {
    //     // Retour dateRetour = retourRepository.findRetourByPret(pret.getIdPret());
    //     FinPret finPret = finPretRepository.findByPretId(pret.getIdPret()).getFirst();
        
    //     if (dateRetour == null || finPret == null) return;

    //     LocalDate retour = dateRetour.toLocalDate();
    //     LocalDate dateFin = finPret.getDateFin().toLocalDate();

    //     long joursDeRetard = ChronoUnit.DAYS.between(dateFin, retour);
    //     if (joursDeRetard <= 0) return;

    //     Adherant adherant = pret.getAdherant();
    //     List<Penalite> penalites = penaliteRepository.findByAdherant(adherant);

    //     // Cherche la première date possible sans conflit
    //     LocalDate nouvelleDateDebut = retour;

    //     for (Penalite p : penalites) {
    //         LocalDate debut = p.getDatePenalite().toLocalDate();
    //         LocalDate fin = debut.plusDays(p.getDuree());

    //         // Si la date de retour tombe pendant une pénalité existante
    //         if (!nouvelleDateDebut.isBefore(debut) && nouvelleDateDebut.isBefore(fin)) {
    //             nouvelleDateDebut = fin;
    //         }
    //     }

    //     Penalite nouvelle = new Penalite();
    //     nouvelle.setAdherant(adherant);
    //     nouvelle.setDatePenalite(nouvelleDateDebut.atStartOfDay());
    //     nouvelle.setDuree((int) joursDeRetard);

    //     penaliteRepository.save(nouvelle);
    // }

    public void appliquerPenaliteSiEnRetard(Pret pret, LocalDateTime dateRetour) {
        // Récupérer la date de fin prévue du prêt
        FinPret finPret = finPretRepository.findByPretId(pret.getIdPret()).getFirst(); // assure-toi que cette méthode existe
        if (finPret == null) return; // Pas de date de fin prévue => on ne pénalise pas

        LocalDateTime dateFinPrevue = finPret.getDateFin();

        // Si rendu à l'heure => pas de pénalité
        if (!dateRetour.isAfter(dateFinPrevue)) return;

        // Calcul de la durée du retard
        long joursDeRetard = Duration.between(dateFinPrevue, dateRetour).toDays();
        if (joursDeRetard <= 0) return;

        Adherant adherant = pret.getAdherant();

        // Récupérer toutes les pénalités de l'adhérant
        List<Penalite> penalites = penaliteRepository.findByAdherant(adherant);

        // Chercher une pénalité qui couvre le jour du retour
        Optional<Penalite> penaliteEnCoursLeJourDuRetour = penalites.stream()
            .filter(p -> {
                LocalDateTime debut = p.getDatePenalite();
                LocalDateTime fin = debut.plusDays(p.getDuree());
                return !dateRetour.isBefore(debut) && dateRetour.isBefore(fin);
            })
            .findFirst();

        LocalDateTime nouvelleDatePenalite;

        if (penaliteEnCoursLeJourDuRetour.isPresent()) {
            // Commencer après la fin de la pénalité en cours
            Penalite p = penaliteEnCoursLeJourDuRetour.get();
            nouvelleDatePenalite = p.getDatePenalite().plusDays(p.getDuree());
        } else {
            // Sinon, on commence le jour du retour
            nouvelleDatePenalite = dateRetour;
        }

        // Créer et enregistrer la nouvelle pénalité
        Penalite nouvellePenalite = new Penalite();
        nouvellePenalite.setAdherant(adherant);
        nouvellePenalite.setDatePenalite(nouvelleDatePenalite);
        nouvellePenalite.setDuree((int) joursDeRetard);

        penaliteRepository.save(nouvellePenalite);
    }

    public boolean isPenalise(LocalDateTime date, Integer idAdherant) {
        // Récupérer l’adhérant
        Adherant adherant = adherantRepository.findById(idAdherant).orElse(null);
        if (adherant == null) return false;

        // Récupérer toutes les pénalités de l’adhérant
        List<Penalite> penalites = penaliteRepository.findByAdherant(adherant);

        // Vérifier si la date est dans l’un des intervalles de pénalité
        for (Penalite p : penalites) {
            LocalDateTime debut = p.getDatePenalite();
            LocalDateTime fin = debut.plusDays(p.getDuree());

            // Si la date est >= début et < fin => pénalisé
            if (!date.isBefore(debut) && date.isBefore(fin)) {
                return true;
            }
        }

        // 2. Vérification des prêts en retard non retournés
        // List<Pret> prets = pretRepository.findByAdherant(adherant);

        // for (Pret pret : prets) {
        //     LocalDateTime dateFinTheorique = null;

        //     // Cherche une fin de prêt officielle (fin_pret), sinon on ne peut pas déterminer le retard
        //     FinPret finPret = finPretRepository.findByPret(pret);
        //     if (finPret != null) {
        //         dateFinTheorique = finPret.getDateFin();
        //     }

        //     // Ignore si on n’a pas de date de fin
        //     if (dateFinTheorique == null) continue;

        //     // Si le prêt n’a pas encore été retourné
        //     Retour retour = retourRepository.findByPret(pret);
        //     if (retour == null) {
        //         // Si la date de fin est dépassée, alors l’adhérant est pénalisé à partir de cette date
        //         if (!date.isBefore(dateFinTheorique)) {
        //             return true;
        //         }
        //     }
        // }

        return false;
    }



    // public void calculPenalite(Integer idPret) throws Exception {
        
    //     Pret pret = pretRepository.findById(idPret).orElse(null);
        
    //     Penalite penalite = null;

    //     if (pret != null) {
    //         FinPret finPret = finPretRepository.findByPretId(pret.getIdPret()).getFirst();
    //         Retour retour = retourRepository.findByPret_IdPret(pret.getIdPret());
        
    //         if (retour !=null && finPret != null) {
        
    //             if (retour.getDateRetour().isAfter(finPret.getDateFin())) {
    //                 long retard = ChronoUnit.DAYS.between(finPret.getDateFin(), retour.getDateRetour());
        
    //                 if (retard > 0) {
        
    //                     Penalite lastPenalites = penaliteRepository.findByAdherant(pret.getAdherant())
    //                         .stream()
    //                         .sorted(Comparator.comparing(penalitee -> penalitee.getDatePenalite().plusDays(penalitee.getDuree())))
    //                         .collect(Collectors.toList()).getFirst();
                        
    //                     if (lastPenalites.getDatePenalite().plusDays(lastPenalites.getDuree()).isAfter(retour.getDateRetour())) {
    //                         penalite = new Penalite(pret.getAdherant(), (int) retard, retour.getDateRetour());
    //                     }
        
    //                     penalite = new Penalite(pret.getAdherant(),((int)retard), lastPenalites.getDatePenalite().plusDays(lastPenalites.getDuree()).plusDays(retard));
    //                     penaliteRepository.save(penalite);
    //                 }
        
    //             } else {
    //                 throw new Exception("n'a pas emprunter ou retourner de livre");
    //             }
        
    //         } else {
    //             throw new Exception("pret introuvable");
    //         }
    //     }
    // }

    // public boolean isPenalise(LocalDateTime date, Integer idAdherant) {

    //     List<Penalite> penalites = penaliteRepository.findByAdherant(adherantRepository.findById(idAdherant).orElse(null));
        
    //     if (penalites.isEmpty()) {
    //         return false;
    //     }
        
    //     Penalite lastpenalite = penalites.stream()
    //         .sorted(Comparator.comparing(penalite -> penalite.getDatePenalite().plusDays(penalite.getDuree())))
    //         .collect(Collectors.toList())
    //         .getFirst();

    //     return lastpenalite.getDatePenalite().plusDays(lastpenalite.getDuree()).isAfter(date) && lastpenalite.getDatePenalite().isBefore(date);
    // }
}