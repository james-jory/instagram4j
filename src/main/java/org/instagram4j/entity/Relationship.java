package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Relationship {
	@JsonProperty("outgoing_status")
	private String outgoingStatus;
	@JsonProperty("incoming_status")
	private String incomingStatus;
	
	@JsonProperty("target_user_is_private")
	private boolean targetUserIsPrivate;

	public String getOutgoingStatus() {
		return outgoingStatus;
	}

	public String getIncomingStatus() {
		return incomingStatus;
	}

	public boolean isTargetUserIsPrivate() {
		return targetUserIsPrivate;
	}
}
