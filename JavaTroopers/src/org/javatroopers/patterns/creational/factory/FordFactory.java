package org.javatroopers.patterns.creational.factory;

import org.javatroopers.patterns.creational.factory.Car.Model;

public class FordFactory implements CarFactory {

	@Override
	public Car createCar(Model model) {
		switch (model) {
		case IKON:
			return new Ikon();
		default:
			return null;
		}
	}

}
