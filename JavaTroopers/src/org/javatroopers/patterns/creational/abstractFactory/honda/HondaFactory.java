package org.javatroopers.patterns.creational.abstractFactory.honda;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;
import org.javatroopers.patterns.creational.abstractFactory.CarAbstractFactory;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;

public class HondaFactory implements CarAbstractFactory {

	@Override
	public Car createCar(Model model) {
		switch (model) {
		case ACCORD:
			return new Accord(createMotor(), createTransmission(), createChassis());
		default:
			return null;
		}
	}

	@Override
	public Motor createMotor() {
		return new HondaMotor();
	}

	@Override
	public Transmission createTransmission() {
		return new HondaTransmission();
	}

	@Override
	public Chassis createChassis() {
		return new HondaChassis();
	}

}
