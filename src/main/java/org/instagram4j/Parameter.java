package org.instagram4j;

public class Parameter {
	private final String name;
	private final String value;
	
	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static Parameter as(String name, String value) {
		return new Parameter(name, value);
	}
	
	public static Parameter as(String name, int value) {
		return new Parameter(name, Integer.toString(value));
	}
	
	public static Parameter as(String name, long value) {
		return new Parameter(name, Long.toString(value));
	}
}
