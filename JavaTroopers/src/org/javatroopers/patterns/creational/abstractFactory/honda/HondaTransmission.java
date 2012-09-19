package org.javatroopers.patterns.creational.abstractFactory.honda;

import org.javatroopers.patterns.creational.abstractFactory.Transmission;

public class HondaTransmission implements Transmission { 
	
	@Override
	public String getType() {
		return "Honda transmission";
	}

}
