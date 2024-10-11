package com.baby.babyspa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.PaymentType;
import com.baby.babyspa.repositories.PaymentTypeRepository;

@Service
public class PaymentTypeService {

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	public PaymentType findById(Integer paymentTypeId) throws NotFoundException {

		return paymentTypeRepository.findById(paymentTypeId)
				.orElseThrow(() -> new NotFoundException("Nije pronađen tip plaćanja sa ID: " + paymentTypeId + "!"));
	}

	public List<PaymentType> findAll() {

		return paymentTypeRepository.findAll();
	}

}
