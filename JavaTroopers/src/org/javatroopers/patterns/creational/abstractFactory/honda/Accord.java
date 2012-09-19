package org.javatroopers.patterns.creational.abstractFactory.honda;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;

public class Accord extends Car {

	public Accord(){
		super();
	}
	
	public Accord(Motor motor, Transmission transmission, Chassis chassis) {
		this.motor = motor;
		this.transmission = transmission;
		this.chassis = chassis;
	}

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
