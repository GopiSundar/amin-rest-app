package com.amin.realty.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * A Domain object representing a property
 */
public class PropertyDO {

	private String id;

	private String tenantId;

	private String metadata;

	private static final Map<String, String> mtdtDefinitionMap = new HashMap<String, String>();
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		// TODO - get from definition table
		mtdtDefinitionMap.put("65ad46c8-0c56-466b-b94d-1a5427edb1e8",
				"[{\"key\":\"id\",\"required\":false,\"visibleFrom\":0.1,\"default\":\"\"},{\"key\":\"tenantId\",\"required\":false,\"visibleFrom\":0.1,\"default\":\"\"},{\"key\":\"title\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"Property title\"},{\"key\":\"description\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"Property description\"},{\"key\":\"price\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"100000\"},{\"key\":\"location\",\"required\":true,\"visibleFrom\":0.1,\"default\":\"map location\"},{\"key\":\"images\",\"required\":false,\"visibleFrom\":0.1}]");
	}

	public PropertyDO() {
		// Empty constructor needed for MapStruct.
	}

	public PropertyDO(String tenantId, String metadataJson) {
		this.tenantId = tenantId;
		this.metadata = metadataJson;
	}

	@Override
	public String toString() {
		return "PropertyDTO{" + "id='" + id + '\'' + ", tenantId=" + tenantId + ", metadata=" + metadata + "}";
	}

	public static boolean isMetadataValid(String tenantId, String metadataJson)
			throws JsonProcessingException, IOException {

		// Get the required fields and allowed fields from definition JSON
		List<JsonNode> metadataList = mapper.readValue(mtdtDefinitionMap.get(tenantId),
				mapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
		List<String> requiredFields = metadataList.stream().filter(m -> m.get("required").booleanValue())
				.map(m -> (String) m.get("key").asText()).collect(Collectors.toList());
		List<String> allAllowedFields = metadataList.stream().map(m -> (String) m.get("key").asText())
				.collect(Collectors.toList());

		// Ensure all JSON fields sent are allowed
		JsonNode receivedJSON = mapper.readTree(metadataJson);
		List<String> receivedFields = Lists.newArrayList(receivedJSON.fieldNames());

		// Ensure all fields required by this tenant are sent
		boolean isPropertyJsonValid = requiredFields.stream().allMatch(f -> receivedFields.contains(f));

		if (isPropertyJsonValid) {
			// Ensure all fields are allowed for this tenant
			isPropertyJsonValid = receivedFields.stream().allMatch(f -> allAllowedFields.contains(f));
		}

		return isPropertyJsonValid;
	}

	public static void main(String[] args) throws IOException {
		String metadataJson = "{\r\n  \"title\":\"prop title\",\r\n  \"description\":\"prop description\",\r\n  \"price\":\"500000\",\r\n  \"location\":\"Houston\",\r\n  \"images\":[\r\n    \"e88d6f8c-2eac-4a87-9d3c-352f92224865\",\r\n    \"661f27dc-7ea6-4720-863a-bc6d95009c9b\",\r\n    \"eff560c2-8474-417c-8b75-97ffb82ec5b7\"\r\n    ]\r\n}";
		System.err.println(isMetadataValid("65ad46c8-0c56-466b-b94d-1a5427edb1e8", metadataJson));
	}

}
