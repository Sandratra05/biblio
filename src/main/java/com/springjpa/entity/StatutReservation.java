package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "statut_reservation")
public class StatutReservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_reservation")
    private Integer idStatut;
    
    @Column(name = "nom_statut", length = 50)
    private String nomStatut;
    
    // Constructeurs
    public StatutReservation() {}
    
    public StatutReservation(Integer idStatut, String nomStatut) {
        this.idStatut = idStatut;
        this.nomStatut = nomStatut;
    }
    
    // Getters et Setters
    public Integer getIdStatut() {
        return idStatut;
    }
    
    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }
    
    public String getNomStatut() {
        return nomStatut;
    }
    
    public void setNomStatut(String nomStatut) {
        this.nomStatut = nomStatut;
    }
}