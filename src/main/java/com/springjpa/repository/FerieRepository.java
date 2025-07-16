package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Ferie;

@Repository
public interface FerieRepository extends JpaRepository<Ferie, Integer> {
}
