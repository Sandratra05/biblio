package com.springjpa.entity;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "statut_prolongement")
public class StatutProlongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_prolongement")
    private int id;

    @Column(name = "nom_statut")
    private String nomStatut;

    @OneToMany(mappedBy = "statutProlongement")
    private List<ProlongementStatut> prolongementStatuts;

    // Getters and setters
}
