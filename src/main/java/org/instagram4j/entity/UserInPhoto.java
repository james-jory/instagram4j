package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInPhoto {
	private User user;
	private Position position;
	
	public User getUser() {
		return user;
	}
	
	public Position getPosition() {
		return position;
	}
}
