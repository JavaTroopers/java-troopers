package org.javatroopers.patterns.creational.factory;

public abstract class Car {
	
	public enum Manufacturer {
		HONDA, FORD
	}
	
	public enum Model {
		ACCORD, IKON
	}
	
	public enum Type {
		SEDAN, COMPACT
	}
	
	abstract String getManufacturer();

	abstract String getModel();

	abstract String getType();
	
	@Override
	public String toString() {
		return getManufacturer() + ", " + getModel() + ", " + getType();
	}
	
}
