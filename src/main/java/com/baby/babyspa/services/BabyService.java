package com.baby.babyspa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baby.babyspa.dtos.CreateBabyDto;
import com.baby.babyspa.dtos.ShortDetailsDto;
import com.baby.babyspa.dtos.UpdateBabyDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.Baby;
import com.baby.babyspa.repositories.BabyRepository;

import jakarta.transaction.Transactional;

@Service
public class BabyService {

	@Autowired
	BabyRepository babyRepository;

	public Baby findById(Integer babyId) throws NotFoundException {

		Baby baby = babyRepository.findById(babyId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjena beba sa ID: " + babyId + "!"));

		return baby;
	}

	public Baby save(CreateBabyDto createBabyDto) throws Exception {
		Baby baby = new Baby();

		if (babyRepository.existsByPhoneNumberAndBabyName(createBabyDto.getPhoneNumber(),
				createBabyDto.getBabyName())) {
			throw new Exception("Ova beba je već unesena u sistem!");
		}

		baby.setBabyName(createBabyDto.getBabyName());
		baby.setBabySurname(createBabyDto.getBabySurname());
		baby.setBirthDate(createBabyDto.getBirthDate());
		baby.setMotherName(createBabyDto.getMotherName());
		baby.setNote(createBabyDto.getNote());
		baby.setNumberOfMonths(createBabyDto.getNumberOfMonths());
		baby.setPhoneNumber(createBabyDto.getPhoneNumber());

		return babyRepository.save(baby);
	}

	public Baby update(UpdateBabyDto updateBabyDto) throws Exception {

		Baby baby = findById(updateBabyDto.getBabyId());

		if (babyRepository.existsByPhoneNumberAndBabyNameAndBabyIdNot(updateBabyDto.getPhoneNumber(),
				updateBabyDto.getBabyName(), updateBabyDto.getBabyId())) {
			throw new Exception("Ova beba je već unesena u sistem!");
		}

		baby.setBabyName(updateBabyDto.getBabyName());
		baby.setBabySurname(updateBabyDto.getBabySurname());
		baby.setBirthDate(updateBabyDto.getBirthDate());
		baby.setMotherName(updateBabyDto.getMotherName());
		baby.setNote(updateBabyDto.getNote());
		baby.setNumberOfMonths(updateBabyDto.getNumberOfMonths());
		baby.setPhoneNumber(updateBabyDto.getPhoneNumber());

		return babyRepository.save(baby);

	}

	@Transactional
	public int delete(int babyId) throws NotFoundException {

		Baby baby = findById(babyId);
		babyRepository.delete(baby);

		return baby.getBabyId();
	}

	public Page<Baby> findAllByQueryParametars(String name, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return babyRepository.findAllNative(pageable);
	}

	public List<ShortDetailsDto> findAllList() {

		return babyRepository.findAll().stream().map(x -> buildShortDetailsDtoFromBaby(x)).collect(Collectors.toList());
	}

	private ShortDetailsDto buildShortDetailsDtoFromBaby(Baby baby) {

		ShortDetailsDto shortDetailsDto = new ShortDetailsDto();
		shortDetailsDto.setId(baby.getBabyId());
		shortDetailsDto.setValue(baby.getBabyName() + " (" + baby.getPhoneNumber() + " )");

		return shortDetailsDto;
	}
}
