package org.javatroopers.patterns.creational.builder;

import java.awt.Color;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;
import org.javatroopers.patterns.creational.abstractFactory.CarAbstractFactory;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;
import org.javatroopers.patterns.creational.abstractFactory.ford.FordFactory;
import org.javatroopers.patterns.creational.abstractFactory.ford.Ikon;

public class FordCarBuilder extends CarBuilder {

	private CarAbstractFactory factory = new FordFactory();

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
		case IKON:
			Car ikon = new Ikon();
			ikon.setMotor(buildMotor());
			ikon.setTransmission(buildTranmission());
			ikon.setChassis(buildChassis());
			ikon.setColor(color);
			return ikon;
		default:
			return null;
		}
	}
}
