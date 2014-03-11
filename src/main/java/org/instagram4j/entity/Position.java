package org.instagram4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
	private float x;
	private float y;
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
