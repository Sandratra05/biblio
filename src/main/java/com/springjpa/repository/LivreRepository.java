package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Livre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    @Query(value = "SELECT * from exemplaire where id_livre = :idLivre", nativeQuery = true)
    List<Exemplaire> findAllExemplaireByIdLivre(@Param("idLivre") Integer idLivre);

}
