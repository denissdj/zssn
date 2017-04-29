package br.com.gzsolucoes.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

public class GenericRest {

	private Map<String, Object> responseObj = new HashMap<String, Object>();
	private Response.ResponseBuilder builder;
	
	public Map<String, Object> getResponseObj() {
		return responseObj;
	}
	
	public void setResponseObj(Map<String, Object> responseObj) {
		this.responseObj = responseObj;
	}
	
	public Response.ResponseBuilder getBuilder() {
		return builder;
	}
	
	public void setBuilder(Response.ResponseBuilder builder) {
		this.builder = builder;
	}
	
}
