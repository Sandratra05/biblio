package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Adherant;
import com.springjpa.repository.AdherantRepository;

@Service
public class AdherantService {
    @Autowired
    private AdherantRepository adherantRepository;

    public Adherant findById(Integer id){
        return adherantRepository.findById(id).get();
    }

    public List<Adherant> findAll(){
        return adherantRepository.findAll();
    }

    public void save(Adherant adherant){
        adherantRepository.save(adherant);
    }
}
