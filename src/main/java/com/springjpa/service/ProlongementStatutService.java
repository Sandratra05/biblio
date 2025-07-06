package com.springjpa.service;

import com.springjpa.entity.*;
import com.springjpa.repository.ProlongementStatutRepository;
import java.util.List;

public class ProlongementStatutService {

    private final ProlongementStatutRepository repository;

    public ProlongementStatutService(ProlongementStatutRepository repository) {
        this.repository = repository;
    }

    public List<ProlongementStatut> findAll() {
        return repository.findAll();
    }

    public ProlongementStatut save(ProlongementStatut ps) {
        return repository.save(ps);
    }

    public void delete(ProlongementStatutId id) {
        repository.deleteById(id);
    }
}
