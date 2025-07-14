package com.springjpa.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.Reservation;
import com.springjpa.entity.ReservationStatut;
import com.springjpa.entity.ReservationStatutId;
import com.springjpa.entity.StatutReservation;
import com.springjpa.repository.ReservationStatutRepository;

@Service
public class ReservationStatutService {
    @Autowired
    private ReservationStatutRepository repository;

    @Autowired
    private StatutReservationService statutReservationService;

    public List<ReservationStatut> findAll(){
        return repository.findAll();
    }

    public void save(ReservationStatut statutReservation){
        repository.save(statutReservation);
    }

    public void associerStatut(Reservation resa, StatutReservation resaStatut) {
        ReservationStatut rs = new ReservationStatut();

        ReservationStatutId rsId = new ReservationStatutId();
        rsId.setIdReservation(resa.getIdReservation());
        rsId.setIdStatutReservation(resaStatut.getIdStatut());  // ou resaStatut.getIdStatutReservation()
        rs.setId(rsId);

        rs.setReservation(resa);
        rs.setStatutReservation(resaStatut);
        rs.setDateStatut(LocalDateTime.now());
        repository.save(rs);
    }

    public List<ReservationStatut> findReservationsEnAttente() {
        return repository.findReservationsEnAttente();
    }

    public void validerReservation(Reservation reservation) {
        // ReservationStatutId id = new ReservationStatutId(reservation.getIdReservation(), 1);
        // repository.deleteById(id); // Supprime le statut 1
        associerStatut(reservation, statutReservationService.findById(2)); // Ajoute le statut 2
    }

    public void refuserReservation(Reservation reservation) {
        associerStatut(reservation, statutReservationService.findById(3));
    }
}
