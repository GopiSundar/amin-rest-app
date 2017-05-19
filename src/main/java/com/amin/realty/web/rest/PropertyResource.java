package com.amin.realty.web.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amin.realty.security.AuthoritiesConstants;
import com.amin.realty.service.MailService;
import com.amin.realty.service.PropertyService;
import com.amin.realty.service.dto.PropertyDTO;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing properties.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

	private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

	private final MailService mailService;

	private final PropertyService propertyService;

	public PropertyResource(MailService mailService, PropertyService propertyService) {

		this.mailService = mailService;
		this.propertyService = propertyService;
	}

	@PostMapping("/properties")
	@Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.BROKER })
	public ResponseEntity createProperty(@Valid @RequestBody String metadata) throws URISyntaxException {
		log.debug("REST request to create Property : {}", metadata);
		// TODO - tenant id
		PropertyDTO property = new PropertyDTO(1, metadata);

		PropertyDTO newProperty = propertyService.createProperty(property);
		return null;

	}

}
