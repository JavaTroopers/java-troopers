package org.javatroopers.patterns.creational.builder;

import java.awt.Color;

import org.javatroopers.patterns.creational.abstractFactory.Car;
import org.javatroopers.patterns.creational.abstractFactory.Car.Manufacturer;
import org.javatroopers.patterns.creational.abstractFactory.Car.Model;

public class CarAgency {

	private CarBuilder hondaBuilder;
	private CarBuilder fordBuilder;

	public CarAgency() {
		hondaBuilder = new HondaCarBuilder();
		fordBuilder = new FordCarBuilder();
	}

	public Car orderCar(Manufacturer manufacturer, Model model, Color color) {
		CarBuilder carBuilder = null;
		switch (manufacturer) {
		case HONDA:
			carBuilder = hondaBuilder;
			break;
		case FORD:
			carBuilder = fordBuilder;
			break;
		}
		if (carBuilder != null) {
			return carBuilder.getCar(model, color);
		}
		return null;
	}

	public static void main(String[] args) {
		CarAgency agency = new CarAgency();
		System.out.println(agency.orderCar(Manufacturer.HONDA, Model.ACCORD, Color.GRAY));
		System.out.println(agency.orderCar(Manufacturer.FORD, Model.IKON, Color.RED));
	}

}
