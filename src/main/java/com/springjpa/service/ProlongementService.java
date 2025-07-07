package com.springjpa.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.DureePret;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Profil;
import com.springjpa.entity.Prolongement;
import com.springjpa.entity.Reservation;
import com.springjpa.repository.DureePretRepository;
import com.springjpa.repository.FinPretRepository;
import com.springjpa.repository.PretRepository;
import com.springjpa.repository.ProlongementRepository;
import com.springjpa.repository.ReservationRepository;

@Service
public class ProlongementService {

    @Autowired
    PretRepository pretRepository;

    @Autowired
    DureePretRepository dureePretRepository;
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @Autowired
    FinPretRepository finPretRepository;
    
    @Autowired
    private ProlongementRepository prolongementRepository;


    public List<Prolongement> findById(int idPret) {
        return prolongementRepository.findByPretId(idPret);
    }

    public boolean isExemplaireEnProlongementActif(Integer idExemplaire) {
        List<Prolongement> prolongements = prolongementRepository.findProlongementsEnCoursByExemplaire(idExemplaire);
        return !prolongements.isEmpty();
    }

    public List<Prolongement> findByPretId(int idPret) {
        return prolongementRepository.findByPretId(idPret);
    }

    public int countProlongementsActifsParAdherant(int idAdherant) {
        return prolongementRepository.countActifsByAdherant(idAdherant, LocalDateTime.now());
    }

    public Prolongement creerProlongement(int idPret, int duree) {
        Pret pret = pretRepository.findById(idPret)
                                  .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));
        Prolongement p = new Prolongement();

        FinPret finPret = finPretRepository.findByIdPret(idPret);

        LocalDateTime dateFin = UtilService.ajouterJours(finPret.getDateFin(), duree);

        p.setDateFin(dateFin);
        p.setPret(pret);
        return prolongementRepository.save(p);
    }

    public void prolongerPret(Integer idPret, LocalDateTime dateTime) throws Exception{
        Pret currentPret = pretRepository.findById(idPret).orElse(null);

        Profil profil = currentPret.getAdherant().getProfil();
        
        DureePret dureePret = dureePretRepository.findByIdProfil(profil.getIdProfil());

        if (dureePret == null) {
            throw new Exception("Aucune durée de prêt définie");
        }
        if (currentPret != null) {
            List<FinPret> finPrets = finPretRepository.findByPretId(currentPret.getIdPret());
            if (finPrets.size() >= 2) {
                throw new Exception("Pret deja prolonger");
            }
            if (currentPret.getDateDebut().plusDays(dureePret.getDuree()).isAfter(dateTime)) {
                throw new Exception("La date est avant la date fin du pret");
            }
            List<Reservation> reservations = reservationRepository.findByDateDeReservationBetween(dateTime, dateTime.plusDays(dureePret.getDuree()));
            if (!reservations.isEmpty()) {
                throw new Exception("Prolongement impossible. Date deja reserver");
            }
            FinPret newFin = new FinPret(dateTime, currentPret);
            finPretRepository.save(newFin);
        } 
        // else {
        //     throw new Exception("Pret non trouver idPret : "+idPret);
        // }
    }
}