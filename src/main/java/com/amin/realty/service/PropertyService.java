package com.amin.realty.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amin.realty.domain.PropertyDO;
import com.amin.realty.repository.PropertyRepository;
import com.amin.realty.service.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Service class for managing properties.
 */
@Service
@Transactional
public class PropertyService {

	private final Logger log = LoggerFactory.getLogger(PropertyService.class);

	private final PropertyRepository propertyRepository;

	public PropertyService(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public Result createProperty(String tenantId, String metadata) {

		Result result = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = (ObjectNode) mapper.readTree(metadata);
			if (node.get("id") != null) {
				result = new Result("New property cannot already have an 'id'");
			} else if (!PropertyDO.isMetadataValid(tenantId, metadata)) {
				result = new Result("Invalid property JSON");
			} else {
				String propertyId = UUID.randomUUID().toString();
				node.put("id", propertyId);
				node.put("tenantId", tenantId);
				metadata = node.toString();
				result = propertyRepository.saveProperty(tenantId, propertyId, metadata);
				log.debug("Created Information for Property: {}",
						result.getResult() ? result.getData() : result.getMessage());
			}
		} catch (IOException e) {
			result = new Result("Error occured while creating property");
		}

		return result;
	}

	public Result updateProperty(String tenantId, String metadata) {

		Result result = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = (ObjectNode) mapper.readTree(metadata);
			if (node.get("id") == null) {
				result = new Result("Property cannot be updated without an 'id'");
			} else if (!PropertyDO.isMetadataValid(tenantId, metadata)) {
				result = new Result("Invalid property JSON");
			} else {
				node.put("tenantId", tenantId);
				metadata = node.toString();
				String propertyId = node.get("id").asText();
				result = propertyRepository.updateProperty(tenantId, propertyId, metadata);
				log.debug("Updated Information for Property: {}",
						result.getResult() ? result.getData() : result.getMessage());
			}
		} catch (IOException e) {
			result = new Result("Error occured while creating property");
		}

		return result;
	}

	public Result fetchProperties(String tenantId) {

		Result result = propertyRepository.fetchProperties(tenantId);
		if (result.getResult()) {
			List<String> list = (List<String>) result.getData();

			final JsonNodeFactory factory = JsonNodeFactory.instance;

			ArrayNode array = factory.arrayNode();

			ObjectMapper mapper = new ObjectMapper();
			list.forEach(property -> {
				try {
					array.add(mapper.readTree(property));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			result = new Result(true, null, array.toString());

		}

		return result;

	}

}
