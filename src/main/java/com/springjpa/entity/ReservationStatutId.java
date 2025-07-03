package com.springjpa.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Embeddable
public class ReservationStatutId implements Serializable {
    private Integer idReservation;
    private Integer idStatutReservation;

    // equals & hashCode
    // Getters & Setters
}