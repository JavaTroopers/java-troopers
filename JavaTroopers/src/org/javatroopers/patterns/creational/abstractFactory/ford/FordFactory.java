package org.javatroopers.patterns.creational.abstractFactory.ford;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;
import org.javatroopers.patterns.creational.abstractFactory.CarAbstractFactory;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;

public class FordFactory implements CarAbstractFactory {

	@Override
	public Car createCar(Model model) {
		switch (model) {
		case IKON:
			return new Ikon(createMotor(), createTransmission(), createChassis());
		default:
			return null;
		}
	}

	@Override
	public Motor createMotor() {
		return new FordMotor();
	}

	@Override
	public Transmission createTransmission() {
		return new FordTransmission();
	}

	@Override
	public Chassis createChassis() {
		return new FordChassis();
	}

}
