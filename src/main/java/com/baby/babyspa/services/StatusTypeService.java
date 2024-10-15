package com.baby.babyspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.StatusType;
import com.baby.babyspa.repositories.StatusTypeRepository;

@Service
public class StatusTypeService {
	
	@Autowired
	StatusTypeRepository statusTypeRepository;
	
	public StatusType findById(int statusTypeId) throws NotFoundException {
		
		return statusTypeRepository.findById(statusTypeId).orElseThrow(()->new NotFoundException("Nije pronadjen tip status sa ID: "+statusTypeId+"!"));
	}

}
