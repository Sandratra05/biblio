package com.springjpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



@Entity
@Table(name = "exemplaire")
public class Exemplaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exemplaire")
    private Integer idExemplaire;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;


    public Exemplaire() {
    }

    public Exemplaire(Integer idExemplaire, Livre livre) {
        setIdExemplaire(idExemplaire);
        setLivre(livre);
    }

    // Getters et Setters
    public Integer getIdExemplaire() {
        return idExemplaire;
    }
    
    public void setIdExemplaire(Integer idExemplaire) {
        this.idExemplaire = idExemplaire;
    }
    
    public Livre getLivre() {
        return livre;
    }
    
    public void setLivre(Livre livre) {
        this.livre = livre;
    }


}