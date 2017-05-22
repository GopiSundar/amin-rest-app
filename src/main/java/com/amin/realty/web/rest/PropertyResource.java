package com.amin.realty.web.rest;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amin.realty.security.AuthoritiesConstants;
import com.amin.realty.service.MailService;
import com.amin.realty.service.PropertyService;
import com.amin.realty.service.TenantService;
import com.amin.realty.service.util.Result;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing properties.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

	private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

	private static final String ENTITY_NAME = "propertyManagement";

	private final MailService mailService;

	private final PropertyService propertyService;

	public PropertyResource(MailService mailService, PropertyService propertyService) {

		this.mailService = mailService;
		this.propertyService = propertyService;
	}

	@PostMapping("/properties")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })
	public ResponseEntity<?> createProperty(@Valid @RequestBody String metadata, HttpServletRequest request)
			throws URISyntaxException {
		log.debug("REST request to create Property : {}", metadata);
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = propertyService.createProperty(tenantId, metadata);

		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@PutMapping("/properties")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })
	public ResponseEntity<?> updateProperty(@Valid @RequestBody String metadata, HttpServletRequest request)
			throws URISyntaxException {
		log.debug("REST request to update Property : {}", metadata);
		ResponseEntity<?> response;
		String tenantId = TenantService.getTenantId(request.getServerName());

		Result result = propertyService.updateProperty(tenantId, metadata);

		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@GetMapping("/properties")
	@Timed
	public ResponseEntity<?> getProperties(@ApiParam HttpServletRequest request) {
		ResponseEntity<?> response;

		String tenantId = TenantService.getTenantId(request.getServerName());
		
		System.err.println(tenantId);
		Result result = propertyService.fetchProperties(tenantId);
		if (result.getResult()) {
			response = new ResponseEntity<>(result.getData(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

}
