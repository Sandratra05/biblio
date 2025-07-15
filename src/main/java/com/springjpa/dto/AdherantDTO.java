package com.springjpa.dto;

public class AdherantDTO {
    private Integer numeroAdherant;
    private String nom;
    private String prenom;
    private Integer quotaRestant;
    private boolean estAbonne;
    private boolean estPenalise;
    public Integer getNumeroAdherant() {
        return numeroAdherant;
    }
    public void setNumeroAdherant(Integer numeroAdherant) {
        this.numeroAdherant = numeroAdherant;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Integer getQuotaRestant() {
        return quotaRestant;
    }
    public void setQuotaRestant(Integer quotaRestant) {
        this.quotaRestant = quotaRestant;
    }
    public boolean isEstAbonne() {
        return estAbonne;
    }
    public void setEstAbonne(boolean estAbonne) {
        this.estAbonne = estAbonne;
    }
    public boolean isEstPenalise() {
        return estPenalise;
    }
    public void setEstPenalise(boolean estPenalise) {
        this.estPenalise = estPenalise;
    }
    
}
