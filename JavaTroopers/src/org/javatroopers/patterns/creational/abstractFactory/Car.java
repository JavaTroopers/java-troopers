package org.javatroopers.patterns.creational.abstractFactory;

import java.awt.Color;

public abstract class Car {

	public enum Manufacturer {
		HONDA, FORD
	}

	public enum Model {
		ACCORD, IKON
	}

	public enum Type {
		SEDAN, COMPACT
	}

	protected Motor motor;
	protected Transmission transmission;
	protected Chassis chassis;
	protected Color color;

	public abstract String getManufacturer();

	public abstract String getModel();

	public abstract String getType();

	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	public Motor getMotor() {
		return motor;
	}

	public void setTransmission(Transmission transmission) {
		this.transmission = transmission;
	}

	public Transmission getTransmission() {
		return transmission;
	}

	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}

	public Chassis getChassis() {
		return chassis;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return getManufacturer() + ", " + getModel() + ", " + getType() 
				+ " (" + getMotor().getType() + ", " + getTransmission().getType() 
				+ ", " + getChassis().getType() 
				+ (getColor() != null ? ", " + getColor().toString() : "") + ")";

	}

}
