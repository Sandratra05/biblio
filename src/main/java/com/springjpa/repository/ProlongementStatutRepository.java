package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.ProlongementStatut;
import com.springjpa.entity.ProlongementStatutId;

@Repository
public interface ProlongementStatutRepository extends JpaRepository<ProlongementStatut, ProlongementStatutId> {
    @Query(value = """
        SELECT * FROM prolongement_statut
        where id_prolongement = :idProlongement
        """, 
        nativeQuery = true
    )
    ProlongementStatut getProlongementStatutByIdProlongement(@Param("idProlongement") int idProlongement);
}
