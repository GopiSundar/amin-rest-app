package com.amin.realty.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.amin.realty.domain.WorkflowStageDO;
import com.amin.realty.service.util.Result;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkflowService {
	private static final Map<String, Map<Integer, String>> WORKFLOW_MAP = new HashMap<String, Map<Integer, String>>();
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final int INCREMENT = 10;

	static {
		// TODO - get from workflow table
		try {
			WORKFLOW_MAP.put("localhost", buildWorkflowMap(
					"[{\"id\":\"10\",\"name\":\"Created\"},{\"id\":\"20\",\"name\":\"Submitted for details\"},{\"id\":\"30\",\"name\":\"NDA Requested\"},{\"id\":\"40\",\"name\":\"NDA signed\"},{\"id\":\"50\",\"name\":\"NDA accepted\"},{\"id\":\"60\",\"name\":\"Waiting for Financial qualification\"},{\"id\":\"70\",\"name\":\"Deal booked\"},{\"id\":\"80\",\"name\":\"Deal closed\"},{\"id\":\"0\",\"name\":\"Deal Cancelled\"}]"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WorkflowService() {
	}

	public static Map<Integer, String> buildWorkflowMap(String workflowJson)
			throws JsonParseException, JsonMappingException, IOException {
		List<WorkflowStageDO> myObjects = mapper.readValue(workflowJson, new TypeReference<List<WorkflowStageDO>>() {
		});

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();

		myObjects.stream().forEach(obj -> map.put(obj.getId(), obj.getName()));

		return map;
	}

	public static Result getNextWorkflowStage(String servername, int currentWorkflowId) {

		Result result = null;

		int next = currentWorkflowId + INCREMENT;
		if (WORKFLOW_MAP.get(servername).containsKey(next)) {
			result = new Result(true, null, new WorkflowStageDO(next, WORKFLOW_MAP.get(servername).get(next)));

		} else {
			result = new Result("Deal is already in it's last workflow stage");

		}

		return result;
	}
	
	public static Result getCancelledWorkflowStage(String servername) {

		Result result = null;

		if (WORKFLOW_MAP.get(servername).containsKey(0)) {
			result = new Result(true, null, new WorkflowStageDO(0, WORKFLOW_MAP.get(servername).get(0)));

		} else {
			result = new Result("Cancelled workflow stage not found");

		}

		return result;
	}

}
