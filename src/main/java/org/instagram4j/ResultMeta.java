package org.instagram4j;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultMeta {
	private int code;
	@JsonProperty("error_type")
	private String errorType;
	@JsonProperty("error_message")
	private String errorMessage;
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (errorMessage != null || errorType != null) {
			s.append(errorMessage);
			if (errorType != null)
				s.append(" (").append(errorType).append(")");
			
			if (code > 0) 
				s.append(" (").append(code).append(")");
		}
		else if (code > 0)
			s.append("HTTP ").append(code);
		else
			s.append("[no meta info available]");
		return s.toString();
	}

	public int getCode() {
		return code;
	}
	
	public boolean isSuccess() {
		return code == 200;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
