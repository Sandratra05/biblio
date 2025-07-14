package com.springjpa.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class ReservationStatutId implements Serializable {
    @Column(name = "id_reservation")
    private Integer idReservation;

    @Column(name = "id_statut_reservation")
    private Integer idStatutReservation;

    // ⚠️ Obligatoire pour JPA
    public ReservationStatutId() {}

    public ReservationStatutId(Integer idReservation, Integer idStatutReservation) {
        this.idReservation = idReservation;
        this.idStatutReservation = idStatutReservation;
    }

    // Getters & setters
    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public Integer getIdStatutReservation() {
        return idStatutReservation;
    }

    public void setIdStatutReservation(Integer idStatutReservation) {
        this.idStatutReservation = idStatutReservation;
    }

    // equals() et hashCode() sont obligatoires aussi
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationStatutId)) return false;
        ReservationStatutId that = (ReservationStatutId) o;
        return Objects.equals(idReservation, that.idReservation)
            && Objects.equals(idStatutReservation, that.idStatutReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReservation, idStatutReservation);
    }
}
