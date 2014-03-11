package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Images {
	@JsonProperty("low_resolution")
	private Image lowResolution;
	private Image thumbnail;
	@JsonProperty("standard_resolution")
	private Image standardResolution;
	
	public Image getThumbnail() {
		return thumbnail;
	}

	public Image getLowResolution() {
		return lowResolution;
	}

	public Image getStandardResolution() {
		return standardResolution;
	}
}
