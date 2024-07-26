package com.example.expo.exception;

public class ExceptionData {

public String getUri;
	
	private String errorMsg;

	public String getGetUri() {
		return getUri;
	}

	public void setGetUri(String getUri) {
		this.getUri = getUri;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "ExceptionData [getUri=" + getUri + ", errorMsg=" + errorMsg + "]";
	}
	
}
