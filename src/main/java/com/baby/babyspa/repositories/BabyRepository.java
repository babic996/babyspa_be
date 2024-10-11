package com.baby.babyspa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.Baby;

@Repository
public interface BabyRepository extends JpaRepository<Baby, Integer> {

	boolean existsByPhoneNumberAndBabyName(String phoneNumber, String babyName);

	boolean existsByPhoneNumberAndBabyNameAndBabyIdNot(String phoneNumber, String babyName, int babyId);

	@Query(value = "SELECT * FROM baby WHERE baby_name LIKE %:name%", nativeQuery = true)
	Page<Baby> findByNameNative(@Param("name") String name, Pageable pageable);

	@Query(value = "SELECT * FROM baby ORDER BY baby_id DESC", nativeQuery = true)
	Page<Baby> findAllNative(Pageable pageable);

}
