package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {
	private String id; 
    private String type; 
    private String object;
    @JsonProperty("object_id")
    private String objectId;
    private String aspect; 
    @JsonProperty("callback_url")
    private String callbackUrl;
    
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getObject() {
		return object;
	}

	public String getAspect() {
		return aspect;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}
}
