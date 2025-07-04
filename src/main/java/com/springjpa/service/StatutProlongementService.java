package com.springjpa.service;

import com.springjpa.entity.StatutProlongement;
import com.springjpa.repository.StatutProlongementRepository;
import java.util.List;

public class StatutProlongementService {

    private final StatutProlongementRepository repository;

    public StatutProlongementService(StatutProlongementRepository repository) {
        this.repository = repository;
    }

    public List<StatutProlongement> findAll() {
        return repository.findAll();
    }

    public StatutProlongement save(StatutProlongement statut) {
        return repository.save(statut);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
