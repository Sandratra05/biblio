package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Retour;
import com.springjpa.repository.ExemplaireRepository;
import com.springjpa.repository.PretRepository;

import ch.qos.logback.classic.pattern.Util;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private PretService pretService;

    public Exemplaire findById(Integer id) {
        return exemplaireRepository.findById(id).get();
    }

    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    public void save(Exemplaire exemplaire) {
        exemplaireRepository.save(exemplaire);
    }

    public List<Exemplaire> findAllExemplaireByIdLivre(Integer idLivre) {
        return exemplaireRepository.findByLivreIdLivre(idLivre);
    }

    public Boolean isExemplaireDisponible(Integer id_exemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) {

        // Vérifie s'il y a un prolongement actif
        if (prolongementService.isExemplaireEnProlongementActif(id_exemplaire)) {
            return false;
        }

        List<Pret> prets = pretService.findByIdExemplaire(id_exemplaire);

        for (Pret pret : prets) {

            LocalDateTime dateDebutPret = pret.getDateDebut();
            LocalDateTime dateFinPretOuRetour = null;

            Retour retour = pretService.findRetourPret(pret);
            if (retour != null) {
                System.out.println("----------- RETOUR NOT NULL ----------------");
                dateFinPretOuRetour = retour.getDateRetour();
            } else {
                System.out.println("----------- RETOUR NOT NULL ----------------");

                return false;
                // FinPret finpret = pretService.findFinPret(pret);
                // if (finpret != null) {
                // dateFinPretOuRetour = finpret.getDateFin();
                // }
            }

            if (dateFinPretOuRetour == null) return false;

            if (UtilService.periodesSeChevauchent(dateDebutPret, dateFinPretOuRetour, dateDebut, dateFin)) {
                return false;
            }

        }

        return true;
    }

    public Boolean isExemplaireDisponible(Integer idExemplaire, LocalDateTime dateDebut) {

        if (prolongementService.isExemplaireEnProlongementActif(idExemplaire)) {
            return false;
        }

        List<Pret> prets = pretService.findByIdExemplaire(idExemplaire);

        for (Pret pret : prets) {
            Retour retour = pretService.findRetourPret(pret);

            // Si aucun retour => prêt toujours en cours => exemplaire indisponible
            if (retour == null) {
                return false;
            }

            // FinPret finPret = pretService.findFinPret(pret);

            // Si on a le retour, on vérifie qu'il est bien revenu avant la fin prévue
            if (retour != null && retour.getDateRetour().isBefore(dateDebut)) {
                // continue; // exemplaire peut être dispo pour ce prêt-là
                return true;
            } else {
                return false; // retour trop tard ou pas de fin prévue => indispo
            }
        }

        return true;
    }

}
