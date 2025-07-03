package com.springjpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "penalite")
public class Penalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_penalite")
    private Integer idPenalite;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "date_penalite")
    private LocalDateTime datePenalite;

    @ManyToOne
    @JoinColumn(name = "id_adherant", nullable = false)
    private Adherant adherant;

    // Constructeurs
    public Penalite() {}

    public Penalite(Adherant adherant, Integer duree, LocalDateTime datePenalite) {
        this.adherant = adherant;
        this.duree = duree;
        this.datePenalite = datePenalite;
    }
    
    public Penalite(Integer idPenalite, Adherant adherant, Integer duree, LocalDateTime datePenalite) {
        this.idPenalite = idPenalite;
        this.adherant = adherant;
        this.duree = duree;
        this.datePenalite = datePenalite;
    }

    // Getters et setters

    public Integer getIdPenalite() {
        return idPenalite;
    }

    public void setIdPenalite(Integer idPenalite) {
        this.idPenalite = idPenalite;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public LocalDateTime getDatePenalite() {
        return datePenalite;
    }

    public void setDatePenalite(LocalDateTime datePenalite) {
        this.datePenalite = datePenalite;
    }

    public Adherant getAdherant() {
        return adherant;
    }

    public void setAdherant(Adherant adherant) {
        this.adherant = adherant;
    }
}