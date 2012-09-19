package org.javatroopers.patterns.creational.abstractFactory.honda;

import org.javatroopers.patterns.creational.abstractFactory.Chassis;

public class HondaChassis implements Chassis {
	
	@Override
	public String getType() {
		return "Honda chassis";
	}

}
