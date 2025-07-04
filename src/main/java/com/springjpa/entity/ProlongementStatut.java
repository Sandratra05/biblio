package com.springjpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prolongement_statut")
@IdClass(ProlongementStatutId.class)
public class ProlongementStatut {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_prolongement")
    private Prolongement prolongement;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_statut_prolongement")
    private StatutProlongement statutProlongement;

    // Getters and setters
}
