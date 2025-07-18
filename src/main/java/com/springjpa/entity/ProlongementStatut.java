package com.springjpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prolongement_statut")
// @IdClass(ProlongementStatutId.class)
public class ProlongementStatut {
    
    @EmbeddedId
    private ProlongementStatutId id;

    @ManyToOne
    @MapsId("idProlongement")
    @JoinColumn(name = "id_prolongement")
    private Prolongement prolongement;

    @ManyToOne
    @MapsId("idStatutProlongement")
    @JoinColumn(name = "id_statut_prolongement")
    private StatutProlongement statutProlongement;

    
    public Prolongement getProlongement() {
        return prolongement;
    }

    public void setProlongement(Prolongement prolongement) {
        this.prolongement = prolongement;

        if (this.id == null) {
            this.id = new ProlongementStatutId();
        }
        this.id.setIdProlongement(prolongement.getId()); // Assure-toi que getId() retourne l'ID
    }

    public void setStatutProlongement(StatutProlongement statutProlongement) {
        this.statutProlongement = statutProlongement;

        if (this.id == null) {
            this.id = new ProlongementStatutId();
        }
        this.id.setIdStatutProlongement(statutProlongement.getIdStatutProlongement());
    }


    public StatutProlongement getStatutProlongement() {
        return statutProlongement;
    }

    // public void setStatutProlongement(StatutProlongement statutProlongement) {
    //     this.statutProlongement = statutProlongement;
    // }

    public ProlongementStatutId getId() {
        return id;
    }

    public void setId(ProlongementStatutId id) {
        this.id = id;
    }

    // Getters and setters
}
