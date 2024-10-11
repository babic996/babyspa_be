package com.baby.babyspa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baby.babyspa.dtos.CreateDiscountDto;
import com.baby.babyspa.dtos.UpdateDiscountDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.Discount;
import com.baby.babyspa.repositories.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
public class DiscountService {

	@Autowired
	DiscountRepository discountRepository;

	public Discount findById(Integer discountId) throws NotFoundException {

		Discount discount = discountRepository.findById(discountId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen popust sa ID: " + discountId + "!"));

		return discount;
	}

	public Discount save(CreateDiscountDto createDiscountDto) throws Exception {

		Discount discount = new Discount();

		if (discountRepository.existsByValueAndIsPrecentage(createDiscountDto.getValue(),
				createDiscountDto.getIsPrecentage())) {
			throw new Exception("Postoji popust sa unijetim parametrima!");
		}

		if (createDiscountDto.getIsPrecentage()) {
			discount.setDiscountName(createDiscountDto.getValue().toString() + "%");
		} else {
			discount.setDiscountName(createDiscountDto.getValue().toString() + "KM");
		}

		discount.setPrecentage(createDiscountDto.getIsPrecentage());
		discount.setValue(createDiscountDto.getValue());

		return discountRepository.save(discount);
	}

	public Discount update(UpdateDiscountDto updateDiscountDto) throws Exception {

		Discount discount = findById(updateDiscountDto.getDisountId());

		if (discountRepository.existsByValueAndIsPrecentageAndDiscountIdNot(updateDiscountDto.getValue(),
				updateDiscountDto.getIsPrecentage(), updateDiscountDto.getDisountId())) {
			throw new Exception("Postoji popust sa unijetim parametrima!");
		}

		if (updateDiscountDto.getIsPrecentage()) {
			discount.setDiscountName(updateDiscountDto.getValue().toString() + "%");
		} else {
			discount.setDiscountName(updateDiscountDto.getValue().toString() + "KM");
		}

		discount.setPrecentage(updateDiscountDto.getIsPrecentage());
		discount.setValue(updateDiscountDto.getValue());

		return discountRepository.save(discount);
	}

	@Transactional
	public int delete(int discountId) throws NotFoundException {
		Discount discount = findById(discountId);

		discountRepository.delete(discount);
		return discountId;
	}

	public List<Discount> findAll() {

		return discountRepository.findAll();
	}
}
