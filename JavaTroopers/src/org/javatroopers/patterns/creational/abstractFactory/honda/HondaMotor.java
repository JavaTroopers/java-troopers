package org.javatroopers.patterns.creational.abstractFactory.honda;

import org.javatroopers.patterns.creational.abstractFactory.Motor;

public class HondaMotor implements Motor { 
	
	@Override
	public String getType() {
		return "Honda motor";
	}

}
