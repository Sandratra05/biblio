package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "profil")
public class Profil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profil")
    private Integer idProfil;
    
    @Column(name = "nom_profil", nullable = false, length = 50)
    private String nomProfil;
    
    @Column(name = "quota_pret")
    private Integer quotaPret;
    
    @Column(name = "quota_reservation")
    private Integer quotaReservation;

    @Column(name = "nb_jour_penalite")
    private Integer nbJourPenalite;
    
    // Constructeurs
    public Profil() {}
    
    public Profil(Integer idProfil, String nomProfil, Integer quotaPret, Integer quotaReservation, Integer nbJourPenalite) {
        this.idProfil = idProfil;
        this.nomProfil = nomProfil;
        this.quotaPret = quotaPret;
        this.quotaReservation = quotaReservation;
        this.nbJourPenalite = nbJourPenalite;
    }
    
    // Getters et Setters
    
    public Integer getIdProfil() {
        return idProfil;
    }
    
    public void setIdProfil(Integer idProfil) {
        this.idProfil = idProfil;
    }
    
    public String getNomProfil() {
        return nomProfil;
    }
    
    public void setNomProfil(String nomProfil) {
        this.nomProfil = nomProfil;
    }
    
    public Integer getQuotaPret() {
        return quotaPret;
    }
    
    public void setQuotaPret(Integer quotaPret) {
        this.quotaPret = quotaPret;
    }
    
    public Integer getQuotaReservation() {
        return quotaReservation;
    }
    
    public void setQuotaReservation(Integer quotaReservation) {
        this.quotaReservation = quotaReservation;
    }

    public Integer getNbJourPenalite() {
        return nbJourPenalite;
    }

    public void setNbJourPenalite(Integer nbJourPenalite) {
        this.nbJourPenalite = nbJourPenalite;
    }

}