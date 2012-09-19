package org.javatroopers.patterns.creational.abstractFactory.ford;

import org.javatroopers.patterns.creational.abstractFactory.Transmission;

public class FordTransmission implements Transmission { 
	
	@Override
	public String getType() {
		return "Ford transmission";
	}

}
