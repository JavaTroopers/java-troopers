package org.javatroopers.patterns.creational.factory;

import org.javatroopers.patterns.creational.factory.Car.Model;

public interface CarFactory {
	
	Car createCar(Model model);

}
