package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Retour;
import com.springjpa.repository.PretRepository;
import com.springjpa.repository.RetourRepository;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private RetourRepository retourRepository;

    public Pret findById(Integer id){
        return pretRepository.findById(id).get();
    }

    public List<Pret> findAll(){
        return pretRepository.findAll();
    }

    public void save(Pret pret){
        pretRepository.save(pret);
    }

    public List<Pret> findByIdExemplaire(Integer id_exemplaire){
        return pretRepository.findByIdExemplaire(id_exemplaire);
    }

    public FinPret findFinPret(Pret pret){
        return pretRepository.findFirstByPretIdOrderByDateFinDesc(pret.getIdPret());
    }

    public Retour findRetourPret(Pret pret){
        return retourRepository.findRetourByPret(pret.getIdPret());
    }

    public static boolean datesSeChevauchent(LocalDateTime debut1, LocalDateTime fin1,
                                             LocalDateTime debut2, LocalDateTime fin2) {
        return !fin1.isBefore(debut2) && !fin2.isBefore(debut1);
    }

}
