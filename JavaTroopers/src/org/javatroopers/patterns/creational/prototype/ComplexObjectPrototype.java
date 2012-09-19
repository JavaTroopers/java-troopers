package org.javatroopers.patterns.creational.prototype;

public abstract class ComplexObjectPrototype implements Cloneable {
	private String id;

	public ComplexObjectPrototype() {
		// TODO very complex/heavy creation
	}

	protected void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
