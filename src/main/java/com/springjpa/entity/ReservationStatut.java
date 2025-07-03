package com.springjpa.entity;

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

    // Getters & Setters
}