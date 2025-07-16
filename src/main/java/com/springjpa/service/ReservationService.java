package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Reservation;
import com.springjpa.entity.ReservationStatut;
import com.springjpa.repository.ReservationRepository;
import com.springjpa.repository.ReservationStatutRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private StatutReservationService statutReservationService;

    @Autowired
    private ReservationStatutService resaStatutService;

    @Autowired
    private ReservationStatutRepository reservationStatutRepository;

    @Autowired
    private LivreService livreService;

    public Reservation findById(Integer id){
        return reservationRepository.findById(id).get();
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public void reserverUnLivre(Integer id_adherant,Integer id_livre,LocalDateTime dateTime){
        Reservation resa = new Reservation(id_livre, dateTime, null, livreService.findById(id_livre), adherantService.findById(id_adherant));
        save(resa);
        resaStatutService.associerStatut(resa, statutReservationService.findById(1));
    }

    public int countReservationEnAttente(Integer idAdherant) {
        return reservationStatutRepository.countReservationsEnAttenteByAdherant(idAdherant);
    }
}
