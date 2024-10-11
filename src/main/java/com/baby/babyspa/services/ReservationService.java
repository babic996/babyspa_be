package com.baby.babyspa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baby.babyspa.dtos.CreateReservationDto;
import com.baby.babyspa.dtos.ReservationFindAllDto;
import com.baby.babyspa.dtos.UpdateReservationDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.Arrangement;
import com.baby.babyspa.models.Reservation;
import com.baby.babyspa.models.Status;
import com.baby.babyspa.repositories.ReservationRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ArrangementService arrangementService;

	@Autowired
	StatusService statusService;

	private final String reservationReserved = "term_reserved";
	private final String reservationCanceled = "term_canceled";

	public Reservation findById(int reservationId) throws NotFoundException {

		return reservationRepository.findById(reservationId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjena rezervacija sa id: " + reservationId + "!"));
	}

	@Transactional
	public ReservationFindAllDto save(CreateReservationDto createReservationDto) throws NotFoundException, Exception {

		Arrangement arrangement = arrangementService.findById(createReservationDto.getArrangementId());
		Status status = statusService.findByStatusCode(reservationReserved);
		Reservation reservation = new Reservation();

		if (arrangement.getRemainingTerm() == 0) {
			throw new Exception("Nije moguće napraviti rezervaciju jer je iskorišten maksimalan broj termina!");
		}

		if (reservationRepository.existsByArrangement(arrangement) && (createReservationDto.getStartDate()
				.plusDays(arrangement.getServicePackage().getServicePackageDurationDays())
				.isBefore(createReservationDto.getStartDate()))) {
			throw new Exception("Nije moguće napraviti rezervaciju jer je broj dana koliko traje paket istekao!");
		}

		reservation.setArrangement(arrangement);
		reservation.setStartDate(createReservationDto.getStartDate());
		reservation.setEndDate(
				createReservationDto.getStartDate().plusMinutes(createReservationDto.getDurationReservation()));
		reservation.setStatus(status);
		reservation.setNote(createReservationDto.getNote());
		arrangementService.decreaseRemainingTerm(arrangement);

		reservationRepository.save(reservation);

		return buildReservationFindAllDtoFromReservation(reservation);
	}

	public ReservationFindAllDto update(UpdateReservationDto updateReservationDto) throws NotFoundException, Exception {

		Status status = statusService.findById(updateReservationDto.getStatusId());
		Reservation reservation = findById(updateReservationDto.getReservationId());

		if (status.getStatusCode().equals(reservationCanceled)) {
			arrangementService.increaseRemainingTerm(reservation.getArrangement());
		} else {
			arrangementService.decreaseRemainingTerm(reservation.getArrangement());
		}

		reservation.setStatus(status);
		reservation.setNote(updateReservationDto.getNote());

		reservationRepository.save(reservation);

		return buildReservationFindAllDtoFromReservation(reservation);
	}

	@Transactional
	public int delete(int reservationId) throws NotFoundException {

		Reservation reservation = findById(reservationId);

		arrangementService.increaseRemainingTerm(reservation.getArrangement());
		reservationRepository.delete(reservation);

		return reservationId;
	}

	public int reservationCanceled(int reservationId) throws NotFoundException {

		Reservation reservation = findById(reservationId);
		Status status = statusService.findByStatusCode(reservationCanceled);

		arrangementService.increaseRemainingTerm(reservation.getArrangement());
		reservation.setStatus(status);
		reservationRepository.save(reservation);

		return reservationId;
	}

	public List<ReservationFindAllDto> findAllList() {

		return reservationRepository.findAll().stream().map(x -> buildReservationFindAllDtoFromReservation(x))
				.collect(Collectors.toList());
	}

	public List<Reservation> findAllByArrangementId(int arrangementId) {

		Arrangement arrangement = arrangementService.findById(arrangementId);

		return reservationRepository.findByArrangement(arrangement);
	}

	private ReservationFindAllDto buildReservationFindAllDtoFromReservation(Reservation reservation) {

		ReservationFindAllDto reservationFindAllDto = new ReservationFindAllDto();

		reservationFindAllDto.setReservationId(reservation.getReservationId());
		reservationFindAllDto.setStatus(reservation.getStatus());
		reservationFindAllDto.setEndDate(reservation.getEndDate());
		reservationFindAllDto.setStartDate(reservation.getStartDate());
		reservationFindAllDto.setCreatedAt(reservation.getCreatedAt());
		reservationFindAllDto.setNote(reservation.getNote());
		reservationFindAllDto.setArrangement(
				arrangementService.buildFindAllArrangementDtoFromArrangement(reservation.getArrangement()));

		return reservationFindAllDto;
	}

}
