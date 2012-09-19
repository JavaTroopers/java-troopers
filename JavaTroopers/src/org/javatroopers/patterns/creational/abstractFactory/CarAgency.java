package org.javatroopers.patterns.creational.abstractFactory;

import org.javatroopers.patterns.creational.abstractFactory.Car.Manufacturer;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;
import org.javatroopers.patterns.creational.abstractFactory.ford.FordFactory;
import org.javatroopers.patterns.creational.abstractFactory.honda.HondaFactory;

public class CarAgency {

	private CarAbstractFactory hondaFactory;
	private CarAbstractFactory fordFactory;

	public CarAgency() {
		hondaFactory = new HondaFactory();
		fordFactory = new FordFactory();
	}

	public Car orderCar(Manufacturer manufacturer, Model model) {
		switch (manufacturer) {
		case HONDA:
			return hondaFactory.createCar(model);
		case FORD:
			return fordFactory.createCar(model);
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {
		CarAgency agency = new CarAgency();
		System.out.println(agency.orderCar(Manufacturer.HONDA, Model.ACCORD));
		System.out.println(agency.orderCar(Manufacturer.FORD, Model.IKON));
	}

}
