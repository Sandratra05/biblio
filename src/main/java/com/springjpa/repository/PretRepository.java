package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {

    @Query(value = "SELECT COUNT(*) FROM pret p WHERE p.id_adherant = :idAdherant AND p.id_type_pret = :idTypePret AND p.id_pret NOT IN (SELECT r.id_pret FROM retour r) ", nativeQuery = true)
    int countPretsEnCours(@Param("idAdherant") Integer idAdherant, @Param("idTypePret") Integer idTypePret);

    @Query(value = "SELECT * FROM fin_pret f WHERE f.id_pret = :idPret ORDER BY date_fin ASC limit 1",  nativeQuery = true)
    FinPret findByIdPret(@Param("idPret") Integer id_pret);

    @Query("SELECT f FROM FinPret f WHERE f.pret.idPret = :idPret ORDER BY f.dateFin DESC")
    FinPret findFirstByPretIdOrderByDateFinDesc(@Param("idPret") Integer idPret);

    @Query(value = "SELECT * FROM pret p WHERE p.id_exemplaire = :idExemplaire", nativeQuery = true)
    List<Pret> findByIdExemplaire(@Param("idExemplaire") Integer id_exemplaire);
    
    @Query(value = "SELECT * FROM pret p WHERE p.id_adherant = :idAdherant", nativeQuery = true)
    List<Pret> findPretByIdAdherant(@Param("idAdherant") Integer idAdherant);

    @Query(value = """
        SELECT p.*
        FROM pret p
        WHERE p.id_adherant = :idAdherant
        AND p.id_pret NOT IN (SELECT r.id_pret FROM retour r)
        AND (
                NOT EXISTS (
                    SELECT 1 FROM fin_pret f
                    WHERE f.id_pret = p.id_pret AND f.date_fin <= NOW()
                )
            )
        AND EXISTS (
                SELECT 1 FROM prolongement pr
                WHERE pr.id_pret = p.id_pret
            )
    """, nativeQuery = true)
    List<Pret> findPretsEnCoursAvecProlongement(@Param("idAdherant") Integer idAdherant);

    @Query("""
        SELECT p
        FROM Pret p
        JOIN FETCH p.exemplaire e
        JOIN FETCH e.livre
        JOIN FETCH p.adherant
        WHERE p.idPret NOT IN (
            SELECT r.pret.idPret FROM Retour r
        )
    """)
    List<Pret> findAllEnCoursWithDetails();

}
