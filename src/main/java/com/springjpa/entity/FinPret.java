
package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "fin_pret")
public class FinPret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fin_pret")
    private Integer idFinPret;
    
    @Column(name = "date_fin")
    private LocalDateTime dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;
    
    // Constructeurs
    public FinPret() {}
    
    public FinPret(Integer idFinPret, LocalDateTime dateFin, Pret pret) {
        this.idFinPret = idFinPret;
        this.dateFin = dateFin;
        this.pret = pret;
    }
    
    // Getters et Setters
    public Integer getIdFinPret() {
        return idFinPret;
    }

    public FinPret (LocalDateTime dateFin, Pret pret){
        this.dateFin = dateFin;
        this.pret = pret;
    }
    
    public void setIdFinPret(Integer idFinPret) {
        this.idFinPret = idFinPret;
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
    
    public Pret getPret() {
        return pret;
    }
    
    public void setPret(Pret pret) {
        this.pret = pret;
    }
}
