package org.javatroopers.patterns.creational.factory;

public class Ikon extends Car {

	@Override
	public String getManufacturer() {
		return Manufacturer.FORD.toString();
	}

	@Override
	public String getModel() {
		return Model.IKON.toString();
	}

	@Override
	public String getType() {
		return Type.COMPACT.toString();
	}
}
