package com.amin.realty.service.dto;

/**
 * A DTO representing a property
 */
public class PropertyDTO {

	private Long id;

	private int tenantId;

	private String metadata;

	public PropertyDTO() {
		// Empty constructor needed for MapStruct.
	}

	public PropertyDTO(int tenantId, String metadata) {
		this.tenantId = tenantId;
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "PropertyDTO{" + "id='" + id + '\'' + ", tenantId=" + tenantId + ", metadata=" + metadata + "}";
	}

}
