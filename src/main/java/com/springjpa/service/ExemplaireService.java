package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Retour;
import com.springjpa.repository.ExemplaireRepository;
import com.springjpa.repository.PretRepository;

@Service
public class ExemplaireService {
    
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private ProlongementService prolongementService;

    @Autowired 
    private PretService pretService;

    public Exemplaire findById(Integer id){
        return exemplaireRepository.findById(id).get();
    }

    public List<Exemplaire> findAll(){
        return exemplaireRepository.findAll();
    }

    public void save(Exemplaire exemplaire){
        exemplaireRepository.save(exemplaire);
    }


    public Boolean isExemplaireDisponible(Integer id_exemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) {

        // Vérifie s'il y a un prolongement actif
        if (prolongementService.isExemplaireEnProlongementActif(id_exemplaire)) {
            return false;
        }

        List<Pret> prets = pretService.findByIdExemplaire(id_exemplaire);

        // if (prets.size() > 0) {
        //     return false;
        // }
  
        // return true;

        for (Pret pret : prets) {
 
            LocalDateTime dateDebutPret = pret.getDateDebut();
            LocalDateTime dateFinPretOuRetour = null;
    
            Retour retour = pretService.findRetourPret(pret);
            if (retour != null) {
                dateFinPretOuRetour = retour.getDateRetour();
            } else {
                FinPret finpret = pretService.findFinPret(pret);
                if (finpret != null) {
                    dateFinPretOuRetour = finpret.getDateFin();
                }
            }
    
            if (dateFinPretOuRetour == null) return false;
    
            if (PretService.datesSeChevauchent(dateDebut, dateFin, dateDebutPret, dateFinPretOuRetour)) {
                return false;
            }
        }
    
        return true;
    }
    
}
