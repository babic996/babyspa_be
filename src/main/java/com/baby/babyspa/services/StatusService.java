package com.baby.babyspa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baby.babyspa.dtos.CreateStatusDto;
import com.baby.babyspa.dtos.UpdateStatusDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.Status;
import com.baby.babyspa.repositories.StatusRepository;

import jakarta.transaction.Transactional;

@Service
public class StatusService {

	@Autowired
	StatusRepository statusRepository;

	public Status findById(int statusId) throws NotFoundException {

		return statusRepository.findById(statusId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen status sa id: " + statusId + "!"));
	}

	public Status findByStatusCode(String statusCode) throws NotFoundException {

		return statusRepository.findByStatusCode(statusCode)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen status sa kodom: " + statusCode + "!"));
	}

	public Status save(CreateStatusDto createStatusDto) throws Exception {

		Status status = new Status();

		if (statusRepository.existsByStatusNameAndStatusCode(createStatusDto.getStatusName(),
				createStatusDto.getStatusCode())) {
			throw new Exception("Postoji status sa ovom kombinacijom imena i koda!");
		}

		status.setStatusCode(createStatusDto.getStatusCode());
		status.setStatusName(createStatusDto.getStatusName());

		return statusRepository.save(status);
	}

	public Status update(UpdateStatusDto updateStatusDto) throws Exception {

		Status status = findById(updateStatusDto.getStatusId());

		if (statusRepository.existsByStatusNameAndStatusCodeAndStatusIdNot(updateStatusDto.getStatusName(),
				updateStatusDto.getStatusCode(), status.getStatusId())) {
			throw new Exception("Postoji status sa ovom kombinacijom imena i koda!");
		}

		status.setStatusCode(updateStatusDto.getStatusCode());
		status.setStatusName(updateStatusDto.getStatusName());

		return statusRepository.save(status);
	}

	@Transactional
	public int delete(int statusId) throws NotFoundException {

		Status status = findById(statusId);

		statusRepository.delete(status);
		return statusId;
	}

	public List<Status> findAll() {

		return statusRepository.findAll();
	}

	public List<Status> findAllByStatusTypeCode(String statusTypeCode) {

		return statusRepository.findByStatusType_StatusTypeCode(statusTypeCode);
	}

}
