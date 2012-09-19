package org.javatroopers.patterns.creational.factory;

import org.javatroopers.patterns.creational.factory.Car.Manufacturer;
import org.javatroopers.patterns.creational.factory.Car.Model;

public class CarAgency {

	private CarFactory hondaFactory;
	private CarFactory fordFactory;

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
