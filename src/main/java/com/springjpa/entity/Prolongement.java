package com.springjpa.entity;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "prolongement")
public class Prolongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prolongement")
    private int id;

    @Column(name = "date_fin")
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;

    @OneToMany(mappedBy = "prolongement")
    private List<ProlongementStatut> prolongementStatuts;

    // Getters and setters
}
