package com.springjpa.dto;

import java.util.List;
import java.util.Set;

public class LivreDetailDTO {
    private Integer idLivre;
    private String titre;
    private String isbn;
    private String langue;
    private Integer anneePublication;
    private String synopsis;
    private Integer nbPage;
    private Integer ageRequis;
    private String nomEditeur;
    private String nomAuteur;
    private Set<String> categories;
    private List<ExemplaireDTO> exemplaires;
    
    public Integer getIdLivre() {
        return idLivre;
    }
    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getLangue() {
        return langue;
    }
    public void setLangue(String langue) {
        this.langue = langue;
    }
    public Integer getAnneePublication() {
        return anneePublication;
    }
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public Integer getNbPage() {
        return nbPage;
    }
    public void setNbPage(Integer nbPage) {
        this.nbPage = nbPage;
    }
    public Integer getAgeRequis() {
        return ageRequis;
    }
    public void setAgeRequis(Integer ageRequis) {
        this.ageRequis = ageRequis;
    }
    public String getNomEditeur() {
        return nomEditeur;
    }
    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }
    public String getNomAuteur() {
        return nomAuteur;
    }
    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }
    public Set<String> getCategories() {
        return categories;
    }
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
    public List<ExemplaireDTO> getExemplaires() {
        return exemplaires;
    }
    public void setExemplaires(List<ExemplaireDTO> exemplaires) {
        this.exemplaires = exemplaires;
    }

    
}
