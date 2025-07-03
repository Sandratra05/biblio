package com.springjpa.repository;
import com.springjpa.entity.ReservationStatut;
import com.springjpa.entity.ReservationStatutId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationStatutRepository extends JpaRepository<ReservationStatut, ReservationStatutId> {}