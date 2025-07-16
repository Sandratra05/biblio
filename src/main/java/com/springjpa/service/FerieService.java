package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Ferie;
import com.springjpa.repository.FerieRepository;

@Service
public class FerieService {
    @Autowired
    private FerieRepository repository;

    public Ferie findById(Integer id){
        return repository.findById(id).get();
    }

    public List<Ferie> findAll(){
        return repository.findAll();
    }

    public void save(Ferie admin){
        repository.save(admin);
    }


}
