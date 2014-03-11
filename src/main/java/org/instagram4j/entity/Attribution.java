package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribution {
	private String website;
	@JsonProperty("itunes_url")
	private String itunesUrl;
	private String name;
	
	public String getWebsite() {
		return website;
	}

	public String getItunesUrl() {
		return itunesUrl;
	}

	public String getName() {
		return name;
	}
}
