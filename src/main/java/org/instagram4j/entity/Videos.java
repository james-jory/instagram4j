package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Videos {
	@JsonProperty("low_resolution")
	private Video lowResolution;
	@JsonProperty("standard_resolution")
	private Video standardResolution;
	
	public Video getLowResolution() {
		return lowResolution;
	}

	public Video getStandardResolution() {
		return standardResolution;
	}
}
