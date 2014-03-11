package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCounts {
	private int media;
	private int follows;
	@JsonProperty("followed_by")
	private int followedBy;
	
	public int getMedia() {
		return media;
	}

	public int getFollows() {
		return follows;
	}

	public int getFollowedBy() {
		return followedBy;
	}
}
