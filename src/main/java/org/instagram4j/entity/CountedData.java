package org.instagram4j.entity;

public abstract class CountedData<T> {
	private int count;
	private T[] data;
	
	public int getCount() {
		return count;
	}

	public T[] getData() {
		return data;
	}
}
