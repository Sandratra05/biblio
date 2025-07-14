package com.springjpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_statut")
public class ReservationStatut {
    @EmbeddedId
    private ReservationStatutId id;

    @ManyToOne
    @MapsId("idReservation")
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne
    @MapsId("idStatutReservation")
    @JoinColumn(name = "id_statut_reservation")
    private StatutReservation statutReservation;

    @Column(name = "date_statut")
    private LocalDateTime dateStatut;

    

    // Getters & Setters
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public StatutReservation getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(StatutReservation statutReservation) {
        this.statutReservation = statutReservation;
    }

    public LocalDateTime getDateStatut() {
        return dateStatut;
    }

    public void setDateStatut(LocalDateTime dateStatut) {
        this.dateStatut = dateStatut;
    }

    public ReservationStatutId getId() {
        return id;
    }

    public void setId(ReservationStatutId id) {
        this.id = id;
    }

    
}