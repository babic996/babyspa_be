package com.baby.babyspa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baby.babyspa.models.PaymentType;
import com.baby.babyspa.services.PaymentTypeService;
import com.baby.babyspa.utils.ApiResponse;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController extends BaseController {

	@Autowired
	PaymentTypeService paymentTypeService;

	@GetMapping("/find-all")
	public ResponseEntity<ApiResponse<List<PaymentType>>> findAll() {

		return createSuccessResponse(paymentTypeService.findAll());
	}

}
