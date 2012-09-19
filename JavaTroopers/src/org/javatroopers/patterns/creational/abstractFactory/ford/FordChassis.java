package org.javatroopers.patterns.creational.abstractFactory.ford;

import org.javatroopers.patterns.creational.abstractFactory.Chassis;

public class FordChassis implements Chassis {
	
	@Override
	public String getType() {
		return "Ford chassis";
	}

}
