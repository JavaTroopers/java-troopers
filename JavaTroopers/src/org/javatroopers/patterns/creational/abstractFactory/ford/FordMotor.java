package org.javatroopers.patterns.creational.abstractFactory.ford;

import org.javatroopers.patterns.creational.abstractFactory.Motor;

public class FordMotor implements Motor { 
	
	@Override
	public String getType() {
		return "Ford motor";
	}

}
