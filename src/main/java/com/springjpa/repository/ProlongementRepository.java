package com.springjpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Prolongement;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    @Query(value = """
        SELECT p.*
        FROM prolongement p
        JOIN prolongement_statut ps ON p.id_prolongement = ps.id_prolongement
        WHERE ps.id_statut_prolongement = 2
        AND p.id_pret IN (
            SELECT pr.id_pret
            FROM pret pr
            WHERE pr.id_exemplaire = :idExemplaire
        )
    """, nativeQuery = true)
    List<Prolongement> findProlongementsEnCoursByExemplaire(@Param("idExemplaire") Integer idExemplaire);

    @Query(value = """
        SELECT COUNT(*) FROM prolongement p 
        JOIN prolongement_statut ps ON ps.id_prolongement = p.id_prolongement 
        JOIN statut_prolongement s ON s.id_statut_prolongement = ps.id_statut_prolongement
        JOIN pret pr ON pr.id_pret = p.id_pret
        WHERE pr.id_adherant = :idAdherant 
          AND p.date_fin > :now
          AND s.id_statut_prolongement = 2
    """, nativeQuery = true)
    int countActifsByAdherant(@Param("idAdherant") Integer idAdherant, @Param("now") LocalDateTime now);
}
