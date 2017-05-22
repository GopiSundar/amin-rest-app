package com.amin.realty.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amin.realty.security.AuthoritiesConstants;
import com.amin.realty.service.DealService;
import com.amin.realty.service.MailService;
import com.amin.realty.service.TenantService;
import com.amin.realty.service.util.Result;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing deals.
 */
@RestController
@RequestMapping("/api")
public class DealResource {

	private final Logger log = LoggerFactory.getLogger(DealResource.class);

	private static final String ENTITY_NAME = "dealManagement";

	private final MailService mailService;

	@Inject
	private DealService dealService;

	public DealResource(MailService mailService) {

		this.mailService = mailService;
	}

	@PostMapping("/deals")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })
	public ResponseEntity<?> createDeal(@Valid String propertyId, HttpServletRequest request)
			throws URISyntaxException {

		long buyerId = 6;
		log.debug("REST request to create deal : {} {}", propertyId, buyerId);
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = dealService.createDeal(tenantId, propertyId, buyerId);

		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@PutMapping("/deals/push")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })
	public ResponseEntity<?> pushToNextStage(@Valid String dealId, HttpServletRequest request)
			throws URISyntaxException {
		log.debug("REST request to update deal : {}", dealId);
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());


		Result result = dealService.pushToNextStage(tenantId, request.getServerName(), dealId);

		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@GetMapping("/deals/{dealId}")
	@Timed
	public ResponseEntity<?> getDealById(@PathVariable String dealId, HttpServletRequest request) {
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = dealService.getDeals(tenantId, dealId,0,0);
		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@GetMapping("/deals/buyer/{buyerId}")
	@Timed
	public ResponseEntity<?> getDealsByBuyer(@PathVariable long buyerId, HttpServletRequest request) {
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = dealService.getDeals(tenantId, null,buyerId,0);
		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@GetMapping("/deals/broker/{brokerId}")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })	
	public ResponseEntity<?> getDealsByBroker(@PathVariable long brokerId, HttpServletRequest request) {
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = dealService.getDeals(tenantId, null,0,brokerId);
		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}
}
