package org.javatroopers.patterns.creational.factory;

public class Accord extends Car {

	@Override
	public String getManufacturer() {
		return Manufacturer.HONDA.toString();
	}

	@Override
	public String getModel() {
		return Model.ACCORD.toString();
	}

	@Override
	public String getType() {
		return Type.SEDAN.toString();
	}

}
