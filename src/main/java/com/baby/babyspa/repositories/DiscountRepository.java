package com.baby.babyspa.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baby.babyspa.models.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

	boolean existsByValueAndIsPrecentage(BigDecimal value, boolean isPrecentage);

	boolean existsByValueAndIsPrecentageAndDiscountIdNot(BigDecimal value, boolean isPrecentage, int discountId);

}
