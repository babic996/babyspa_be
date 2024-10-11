package com.baby.babyspa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.ServicePackage;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer> {

	boolean existsByServicePackageName(String servicePackageName);

	boolean existsByServicePackageNameAndServicePackageIdNot(String servicePackageName, int servicePackageId);

	@Query(value = "SELECT * FROM service_package ORDER BY service_package_id DESC", nativeQuery = true)
	Page<ServicePackage> findAllNative(Pageable pageable);
}
