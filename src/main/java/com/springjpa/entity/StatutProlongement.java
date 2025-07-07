package com.springjpa.entity;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "statut_prolongement")
public class StatutProlongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_prolongement")
    private int idStatutProlongement;

    @Column(name = "nom_statut")
    private String nomStatut;

    public int getIdStatutProlongement() {
        return idStatutProlongement;
    }

    public void setIdStatutProlongement(int idStatutProlongement) {
        this.idStatutProlongement = idStatutProlongement;
    }

    public String getNomStatut() {
        return nomStatut;
    }

    public void setNomStatut(String nomStatut) {
        this.nomStatut = nomStatut;
    }

    // @OneToMany(mappedBy = "statutProlongement")
    // private List<ProlongementStatut> prolongementStatuts;

    // Getters and setters
}
