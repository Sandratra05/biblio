package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Inscription;
import com.springjpa.entity.Profil;
import com.springjpa.repository.AdherantRepository;
import com.springjpa.repository.InscriptionRepository;

@Service
public class AdherantService {
    @Autowired
    private AdherantRepository adherantRepository;

    @Autowired 
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private ProfilService profilService;

    public Adherant findById(Integer id) {
        return adherantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Adhérant non trouvé avec l’ID " + id));
    }

    public List<Adherant> findAll(){
        return adherantRepository.findAll();
    }

    public void save(Adherant adherant){
        adherantRepository.save(adherant);
    }

    public boolean isActif(Integer adherantId, LocalDateTime datePret) {
        Inscription inscription = inscriptionRepository.findLastByAdherantId(adherantId);
        if (datePret.isAfter(inscription.getDateDebut()) && datePret.isBefore(inscription.getDateFin())) {
            return true;
        }
        
        return false;
    }

    public Adherant authenticate(int idAdherant, String motDePasse) {
        Adherant adherant = adherantRepository.findById(idAdherant).get();
        if (adherant != null && adherant.getPassword().equals(motDePasse)) {
            return adherant;
        }
        return null;
    }

}
