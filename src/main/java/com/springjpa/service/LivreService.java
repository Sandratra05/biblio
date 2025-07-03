package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.Categorie;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Livre;
import com.springjpa.repository.ExemplaireRepository;
import com.springjpa.repository.LivreRepository;
import com.springjpa.repository.RestrictionCategorieRepository;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    @Autowired 
    private RestrictionCategorieRepository restrictionCategorieRepository;

    @Autowired 
    private ExemplaireRepository exemplaireRepository;

    public Livre findById(Integer id){
        return livreRepository.findById(id).get();
    }

    public List<Livre> findAll(){
        return livreRepository.findAll();
    }

    public void save(Livre livre){
        livreRepository.save(livre);
    }

    public List<Exemplaire> findAllExemplaireByIdLivre(Integer idLivre) {
        return exemplaireRepository.findByLivreIdLivre(idLivre);
    }

    public boolean peutPreterLivre(Adherant adherant, Livre livre) {
        // 1. Vérifier la restriction de catégorie
        for (Categorie categorie : livre.getCategories()) {
            int restreint = restrictionCategorieRepository.existsRestriction(
                categorie.getIdCategorie(), adherant.getProfil().getIdProfil());
            if (restreint == 0) {
                return false;
            }
        }

        // 2. Vérifier l'âge requis
        LocalDate naissance = adherant.getDateNaissance();
        int age = Period.between(naissance, LocalDate.now()).getYears();
        if (livre.getAgeRequis() != null && age < livre.getAgeRequis()) {
            return false;
        }

        return true;
    }
}
