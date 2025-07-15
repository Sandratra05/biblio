package com.springjpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.FinPret;

@Repository
public interface FinPretRepository extends JpaRepository<FinPret, Integer> {
    
    @Query("SELECT fp FROM FinPret fp WHERE fp.pret.idPret = :pretId")
    List<FinPret> findByPretId(@Param("pretId") Integer pretId);


    @Query("SELECT fp FROM FinPret fp WHERE fp.dateFin BETWEEN :dateDebut AND :dateFin")
    List<FinPret> findByDateFinBetween(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);
    
    @Query(value = "SELECT * FROM fin_pret f WHERE f.id_pret = :idPret ORDER BY date_fin ASC limit 1",  nativeQuery = true)
    FinPret findByIdPret(@Param("idPret") Integer id_pret);
}
