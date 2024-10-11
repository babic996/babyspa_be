package com.baby.babyspa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.Arrangement;

@Repository
public interface ArrangementRepository extends JpaRepository<Arrangement, Integer> {

	@Query(value = "SELECT * FROM arrangement ORDER BY arrangement_id DESC", nativeQuery = true)
	List<Arrangement> findAllNative();

	List<Arrangement> findByRemainingTermGreaterThan(int remainingTerm);
}
