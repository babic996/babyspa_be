package com.baby.babyspa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.StatusType;

@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, Integer> {

}
