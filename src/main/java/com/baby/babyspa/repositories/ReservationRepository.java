package com.baby.babyspa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.Arrangement;
import com.baby.babyspa.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	boolean existsByArrangement(Arrangement arrangement);

	List<Reservation> findByArrangement(Arrangement arrangement);
}
