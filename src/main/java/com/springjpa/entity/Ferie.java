package com.springjpa.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ferie")
public class Ferie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ferie")
    private Integer idFerie;
    
    @Column(name = "jour_ferie")
    private LocalDate jourFerie;

    public Integer getIdFerie() {
        return idFerie;
    }

    public void setIdFerie(Integer idFerie) {
        this.idFerie = idFerie;
    }

    public LocalDate getJourFerie() {
        return jourFerie;
    }

    public void setJourFerie(LocalDate jourFerie) {
        this.jourFerie = jourFerie;
    }

    
}
