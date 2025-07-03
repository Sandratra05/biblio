package com.springjpa.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.DureePret;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Profil;
import com.springjpa.entity.Reservation;
import com.springjpa.repository.DureePretRepository;
import com.springjpa.repository.FinPretRepository;
import com.springjpa.repository.PretRepository;
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
        } else {
            throw new Exception("Pret non trouver idPret : "+idPret);
        }
    }
}