package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Profil;
import com.springjpa.entity.QuotaProlongement;

import java.util.List;

@Repository
public interface QuotaProlongementRepository extends JpaRepository<QuotaProlongement, Integer> {
    
    // Optionnel : récupérer tous les quotas d’un profil donné
    List<QuotaProlongement> findByProfil(Profil profil);
}
