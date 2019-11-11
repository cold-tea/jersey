package com.sandesh.jersey.models;

public class ErrorMessage {

	private String errorText;
	private int status;
	private String uriLocation;
	
	public ErrorMessage() {}
	
	public ErrorMessage(String errorText, int status, String uriLocation) {
		super();
		this.errorText = errorText;
		this.status = status;
		this.uriLocation = uriLocation;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUriLocation() {
		return uriLocation;
	}

	public void setUriLocation(String uriLocation) {
		this.uriLocation = uriLocation;
	}
	
	
}
