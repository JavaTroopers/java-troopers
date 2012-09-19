package org.javatroopers.patterns.creational.factory;

import org.javatroopers.patterns.creational.factory.Car.Model;

public class HondaFactory implements CarFactory {

	@Override
	public Car createCar(Model model) {
		switch (model) {
		case ACCORD:
			return new Accord();
		default:
			return null;
		}
	}

}
