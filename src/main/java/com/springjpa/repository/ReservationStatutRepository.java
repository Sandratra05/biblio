package com.springjpa.repository;
import com.springjpa.entity.ReservationStatut;
import com.springjpa.entity.ReservationStatutId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationStatutRepository extends JpaRepository<ReservationStatut, ReservationStatutId> {
    @Query("""
        SELECT rs
        FROM ReservationStatut rs
        WHERE rs.dateStatut = (
            SELECT MAX(r2.dateStatut)
            FROM ReservationStatut r2
            WHERE r2.reservation.idReservation = rs.reservation.idReservation
        )
        AND rs.statutReservation.id = 1
    """)
    List<ReservationStatut> findReservationsEnAttente();
}