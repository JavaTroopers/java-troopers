package org.javatroopers.patterns.creational.builder;

import java.awt.Color;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Chassis;
import org.javatroopers.patterns.creational.abstractFactory.Motor;
import org.javatroopers.patterns.creational.abstractFactory.Transmission;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;

public abstract class CarBuilder {

	protected abstract Motor buildMotor();

	protected abstract Transmission buildTranmission();

	protected abstract Chassis buildChassis();

	protected abstract Color buildColor(Color color);

	public abstract Car getCar(Model model, Color color);

}
