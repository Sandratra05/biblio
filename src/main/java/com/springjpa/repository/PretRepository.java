package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {

    @Query("""
        SELECT COUNT(p) FROM Pret p
        WHERE p.adherant.idAdherant = :idAdherant
          AND p.typePret.idTypePret = :idTypePret
          AND p.idPret NOT IN (SELECT f.pret.idPret FROM FinPret f)
        """)
    int countPretsEnCours(@Param("idAdherant") Integer idAdherant, @Param("idTypePret") Integer idTypePret);

    
}
