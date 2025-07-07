package com.springjpa.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProlongementStatutId implements Serializable {

    @Column(name = "id_prolongement")
    private Integer idProlongement;

    @Column(name = "id_statut_prolongement")
    private Integer idStatutProlongement;

    public ProlongementStatutId() {}

    public ProlongementStatutId(Integer idProlongement, Integer idStatutProlongement) {
        this.idProlongement = idProlongement;
        this.idStatutProlongement = idStatutProlongement;
    }

    // Getters, Setters, equals(), hashCode()

    public Integer getIdProlongement() {
        return idProlongement;
    }

    public void setIdProlongement(Integer idProlongement) {
        this.idProlongement = idProlongement;
    }

    public Integer getIdStatutProlongement() {
        return idStatutProlongement;
    }

    public void setIdStatutProlongement(Integer idStatutProlongement) {
        this.idStatutProlongement = idStatutProlongement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProlongementStatutId that)) return false;
        return Objects.equals(idProlongement, that.idProlongement) &&
               Objects.equals(idStatutProlongement, that.idStatutProlongement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProlongement, idStatutProlongement);
    }
}
