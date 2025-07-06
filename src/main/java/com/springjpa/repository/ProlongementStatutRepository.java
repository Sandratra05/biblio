package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.ProlongementStatut;
import com.springjpa.entity.ProlongementStatutId;

@Repository
public interface ProlongementStatutRepository extends JpaRepository<ProlongementStatut, ProlongementStatutId> {}
