package com.amin.realty.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkflowStageDO {

	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private int id;
	private String name;

	public WorkflowStageDO() {

	}

	public WorkflowStageDO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJson() throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(this);
	}

}
