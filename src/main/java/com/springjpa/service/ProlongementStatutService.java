package com.springjpa.service;

import com.springjpa.entity.*;
import com.springjpa.repository.ProlongementStatutRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProlongementStatutService {

    @Autowired
    private ProlongementStatutRepository repository;

    public List<ProlongementStatut> findAll() {
        return repository.findAll();
    }

    public ProlongementStatut save(ProlongementStatut ps) {
        return repository.save(ps);
    }

    public void delete(ProlongementStatutId id) {
        repository.deleteById(id);
    }

    public void associerStatut(Prolongement prolongement, StatutProlongement statut) {
        ProlongementStatut ps = new ProlongementStatut();
        ps.setProlongement(prolongement);
        ps.setStatutProlongement(statut);
        repository.save(ps);
    }
}
