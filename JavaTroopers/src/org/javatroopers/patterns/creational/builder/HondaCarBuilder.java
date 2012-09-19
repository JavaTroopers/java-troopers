package org.javatroopers.patterns.creational.builder;

import java.awt.Color;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;
import org.javatroopers.patterns.creational.abstractFactory.CarAbstractFactory;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;
import org.javatroopers.patterns.creational.abstractFactory.honda.Accord;
import org.javatroopers.patterns.creational.abstractFactory.honda.HondaFactory;

public class HondaCarBuilder extends CarBuilder {

	private CarAbstractFactory factory = new HondaFactory();

	@Override
	protected Motor buildMotor() {
		return factory.createMotor();
	}

	@Override
	protected Transmission buildTranmission() {
		return factory.createTransmission();
	}

	@Override
	protected Chassis buildChassis() {
		return factory.createChassis();
	}

	@Override
	protected Color buildColor(Color color) {
		return color;
	}

	@Override
	public Car getCar(Model model, Color color) {
		switch (model) {
		case ACCORD:
			Car accord = new Accord();
			accord.setMotor(buildMotor());
			accord.setTransmission(buildTranmission());
			accord.setChassis(buildChassis());
			accord.setColor(color);
			return accord;
		default:
			return null;
		}
	}
}
