package com.baby.babyspa.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baby.babyspa.dtos.CreateServicePackageDto;
import com.baby.babyspa.dtos.ShortDetailsDto;
import com.baby.babyspa.dtos.UpdateServicePackageDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.ServicePackage;
import com.baby.babyspa.repositories.ServicePackageRepository;

import jakarta.transaction.Transactional;

@Service
public class ServicePackageService {

	@Autowired
	ServicePackageRepository servicePackageRepository;

	public ServicePackage findById(Integer servicePackageId) throws NotFoundException {

		ServicePackage servicePackage = servicePackageRepository.findById(servicePackageId).orElseThrow(
				() -> new NotFoundException("Nije pronadjen paket usluge sa ID: " + servicePackageId + "!"));

		return servicePackage;
	}

	public ServicePackage save(CreateServicePackageDto createServicePackageDto) throws Exception {
		ServicePackage servicePackage = new ServicePackage();

		if (servicePackageRepository.existsByServicePackageName(createServicePackageDto.getServicePackageName())) {
			throw new Exception(
					"Postoji paket usluge sa imenom: " + createServicePackageDto.getServicePackageName() + "!");
		}

		servicePackage.setServicePackageName(createServicePackageDto.getServicePackageName());
		servicePackage.setPrice(createServicePackageDto.getPrice());
		servicePackage.setServicePackageDurationDays(createServicePackageDto.getServicePackageDurationDays());
		servicePackage.setTermNumber(createServicePackageDto.getTermNumber());
		servicePackage.setNote(createServicePackageDto.getNote());

		return servicePackageRepository.save(servicePackage);
	}

	public ServicePackage update(UpdateServicePackageDto updateServicePackageDto) throws Exception {
		ServicePackage servicePackage = findById(updateServicePackageDto.getServicePackageId());

		if (servicePackageRepository.existsByServicePackageNameAndServicePackageIdNot(
				updateServicePackageDto.getServicePackageName(), updateServicePackageDto.getServicePackageId())) {
			throw new Exception(
					"Postoji paket usluge sa imenom: " + updateServicePackageDto.getServicePackageName() + "!");
		}

		servicePackage.setServicePackageName(updateServicePackageDto.getServicePackageName());
		servicePackage.setPrice(updateServicePackageDto.getPrice());
		servicePackage.setServicePackageDurationDays(updateServicePackageDto.getServicePackageDurationDays());
		servicePackage.setTermNumber(updateServicePackageDto.getTermNumber());
		servicePackage.setNote(updateServicePackageDto.getNote());

		return servicePackageRepository.save(servicePackage);
	}
	
	public Double findMaxPriceServicePackage() {
		
		return servicePackageRepository.findMaxPrice();
	}

	@Transactional
	public int delete(int servicePackageId) throws NotFoundException {

		ServicePackage servicePackage = findById(servicePackageId);
		servicePackageRepository.delete(servicePackage);

		return servicePackage.getServicePackageId();
	}

	public List<ShortDetailsDto> findAllList() {

		return servicePackageRepository.findAll().stream().map(x -> buildShortDetailsDtoFromServicePackage(x))
				.collect(Collectors.toList());
	}

	private ShortDetailsDto buildShortDetailsDtoFromServicePackage(ServicePackage servicePackage) {

		ShortDetailsDto shortDetailsDto = new ShortDetailsDto();
		shortDetailsDto.setId(servicePackage.getServicePackageId());
		shortDetailsDto.setValue(servicePackage.getServicePackageName());

		return shortDetailsDto;
	}

	public Page<ServicePackage> findAll(int page, int size, String searchText, BigDecimal startPrice,
			BigDecimal endPrice) {

		Pageable pageable = PageRequest.of(page, size);

		return servicePackageRepository.findAllServicePackageNative(searchText, startPrice, endPrice, pageable);
	}
	
}
