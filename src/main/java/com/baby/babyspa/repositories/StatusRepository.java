package com.baby.babyspa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

	boolean existsByStatusNameAndStatusCode(String statusName, String statusCode);

	boolean existsByStatusNameAndStatusCodeAndStatusIdNot(String statusName, String statusCode, int statusId);

	Optional<Status> findByStatusCode(String statusCode);

	List<Status> findByStatusType_StatusTypeCode(String statusTypeCode);

}
