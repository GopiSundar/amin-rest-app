package com.amin.realty.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("status")
	private String status;
	@JsonProperty("data")
	private Object data;
	@JsonProperty("statusMsg")
	private String statusMsg;
	
	public ResponseDTO(){}
	
	public ResponseDTO(boolean success, Object data, String message) {
		super();
		this.data = data;
		this.statusMsg = message;
	}
	public ResponseDTO(String code, String message , Object data) {
		super();
		this.status = code;
		this.data = data;
		this.statusMsg = message;
	}
	public ResponseDTO(boolean success, String code, String message) {
		super();
		this.status = code;
		this.statusMsg = message;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String message) {
		this.statusMsg = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String code) {
		this.status = code;
	}
	
}
