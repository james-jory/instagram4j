package org.instagram4j.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Caption {
	private String id;
	@JsonProperty("created_time")
	private String createdTime;
	private String text;
	private User from;
	
	public String getId() {
		return id;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	@JsonIgnore
	public Date getCreatedTimeAsDate() {
		if (createdTime == null)
			return null;
		return new Date(Long.parseLong(createdTime) * 1000);
	}

	public String getText() {
		return text;
	}

	public User getFrom() {
		return from;
	}
}
