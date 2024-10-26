package com.backend.babyspa.v1.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "arrangement")
public class Arrangement {

	@Id
	@Column(name = "arrangement_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int arrangementId;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "remaining_term", nullable = false)
	private int remainingTerm;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "note", nullable = true)
	private String note;

	@ManyToOne
	@JoinColumn(name = "discount_id", nullable = true)
	private Discount discount;

	@ManyToOne
	@JoinColumn(name = "baby_id", nullable = false)
	private Baby baby;

	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "service_package_id", nullable = false)
	private ServicePackage servicePackage;

	@ManyToOne
	@JoinColumn(name = "payment_type_id", nullable = true)
	private PaymentType paymentType;

}