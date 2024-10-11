package com.baby.babyspa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baby.babyspa.dtos.CreateBabyDto;
import com.baby.babyspa.dtos.ShortDetailsDto;
import com.baby.babyspa.dtos.UpdateBabyDto;
import com.baby.babyspa.exception.NotFoundException;
import com.baby.babyspa.models.Baby;
import com.baby.babyspa.services.BabyService;
import com.baby.babyspa.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/baby")
public class BabyController extends BaseController {

	@Autowired
	BabyService babyService;

	@GetMapping("/find-by-id")
	public Baby findById(@RequestParam Integer babyId) throws NotFoundException {

		return babyService.findById(babyId);
	}

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<Baby>> save(@RequestBody @Valid CreateBabyDto createBabyDto,
			BindingResult bindingResult) {

		if (hasErrors(bindingResult)) {
			return createErrorResponse(bindingResult);
		}

		try {
			Baby savedBaby = babyService.save(createBabyDto);
			return createSuccessResponse(savedBaby);
		} catch (Exception e) {
			return createExceptionResponse(e);
		}

	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Baby>> update(@RequestBody @Valid UpdateBabyDto updateBabyDto,
			BindingResult bindingResult) {

		if (hasErrors(bindingResult)) {
			return createErrorResponse(bindingResult);
		}
		try {
			Baby updatedBaby = babyService.update(updateBabyDto);
			return createSuccessResponse(updatedBaby);
		} catch (Exception e) {
			return createExceptionResponse(e);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Integer>> delete(@RequestParam int babyId) {

		try {
			int deletedBabyId = babyService.delete(babyId);
			return createSuccessResponse(deletedBabyId);
		} catch (Exception e) {
			return createExceptionResponse(e);
		}
	}

	@GetMapping("/find-all")
	public ResponseEntity<ApiResponse<Page<Baby>>> findAllByParametars(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		return createSuccessResponse(babyService.findAllByQueryParametars(name, page, size));
	}

	@GetMapping("/find-all-list")
	public ResponseEntity<ApiResponse<List<ShortDetailsDto>>> findAllList() {

		return createSuccessResponse(babyService.findAllList());
	}

}
