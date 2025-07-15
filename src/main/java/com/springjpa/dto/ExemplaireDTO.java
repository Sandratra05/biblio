package com.springjpa.dto;

public class ExemplaireDTO {
    private Integer idExemplaire;
    private Boolean disponible;

    public ExemplaireDTO() {}

    public ExemplaireDTO(Integer idExemplaire, Boolean disponible) {
        this.idExemplaire = idExemplaire;
        this.disponible = disponible;
    }

    public Integer getIdExemplaire() {
        return idExemplaire;
    }

    public void setIdExemplaire(Integer idExemplaire) {
        this.idExemplaire = idExemplaire;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
