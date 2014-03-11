package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String id;
	private String username;
	@JsonProperty("profile_picture")
	private String profilePicture;
	@JsonProperty("full_name")
	private String fullName;
	private String website;
	private String bio;
	private UserCounts counts;
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public String getFullName() {
		return fullName;
	}

	public String getWebsite() {
		return website;
	}

	public String getBio() {
		return bio;
	}

	public UserCounts getCounts() {
		return counts;
	}
}
