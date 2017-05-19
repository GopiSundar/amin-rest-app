package com.amin.realty.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amin.realty.repository.UserRepository;
import com.amin.realty.service.dto.PropertyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * Service class for managing properties.
 */
@Service
@Transactional
public class PropertyService {

	private final Logger log = LoggerFactory.getLogger(PropertyService.class);

	private final UserRepository propertyRepository;

	// TODO - get from definition table
	private static final String propertyMetadata = "[{\"key\":\"title\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"Property title\"},{\"key\":\"description\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"Property description\"},{\"key\":\"price\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"100000\"},{\"key\":\"location\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"map location\"},{\"key\":\"images\",\"required\":false,\"visibleFrom\":0.1}]";

	public PropertyService(UserRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public PropertyDTO createProperty(PropertyDTO property) {

		// propertyRepository.save(property);
		log.debug("Created Information for Property: {}", property);
		return property;

	}

	private static boolean isPropertyJsonValid(String propertyJson) throws JsonProcessingException, IOException {

		// Get the required fields and allowed fields from definition JSON
		ObjectMapper mapper = new ObjectMapper();
		List<JsonNode> metadataList = mapper.readValue(propertyMetadata,
				mapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
		List<String> requiredFields = metadataList.stream().filter(m ->  m.get("required").booleanValue())
				 .map(m -> (String) m.get("key").asText())
				 .collect(Collectors.toList());
		System.err.println(requiredFields.toString());		
		List<String> allAllowedFields = metadataList.stream()
				 .map(m -> (String) m.get("key").asText())
				 .collect(Collectors.toList());
		System.err.println("allAllowedFields : " + allAllowedFields.toString());

		// Ensure all JSON fields sent are allowed
		JsonNode receivedJSON = mapper.readTree(propertyJson);		
		List<String> receivedFields = Lists.newArrayList(receivedJSON.fieldNames());
		
		// Ensure all fields required by this tenant are sent
		boolean isPropertyJsonValid = requiredFields.stream().allMatch(f -> receivedFields.contains(f));
		
		System.err.println("isPropertyJsonValid 1 : " + isPropertyJsonValid);
		
		if (isPropertyJsonValid){
			// Ensure all fields are allowed for this tenant
			isPropertyJsonValid= receivedFields.stream().allMatch(f -> allAllowedFields.contains(f) );
		}
				
		System.err.println("isPropertyJsonValid 2 : " + isPropertyJsonValid);

		return isPropertyJsonValid;
	}
	
	public static void main(String[] args) throws IOException {
		String propertyJson = "{\r\n  \"title\":\"prop title\",\r\n  \"description\":\"prop description\",\r\n  \"price\":\"500000\",\r\n  \"location\":\"Houston\",\r\n  \"images\":[\r\n    \"e88d6f8c-2eac-4a87-9d3c-352f92224865\",\r\n    \"661f27dc-7ea6-4720-863a-bc6d95009c9b\",\r\n    \"eff560c2-8474-417c-8b75-97ffb82ec5b7\"\r\n    ]\r\n}";
		System.err.println(isPropertyJsonValid(propertyJson));
	}
}
