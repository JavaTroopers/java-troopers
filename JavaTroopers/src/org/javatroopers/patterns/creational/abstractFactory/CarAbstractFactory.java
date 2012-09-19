package org.javatroopers.patterns.creational.abstractFactory;

import org.javatroopers.patterns.creational.abstractFactory.Car.Model;

public interface CarAbstractFactory {

	Car createCar(Model model);
	
	Motor createMotor();
	
	Transmission createTransmission();
	
	Chassis createChassis();
	
}
