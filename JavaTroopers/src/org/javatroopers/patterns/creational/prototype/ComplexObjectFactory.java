package org.javatroopers.patterns.creational.prototype;

public class ComplexObjectFactory {
	private ComplexObjectPrototype prototypeOne;
	private ComplexObjectPrototype prototypeTwo;
	
	
	public ComplexObjectFactory(ComplexObjectPrototype prototypeOne, ComplexObjectPrototype prototypeTwo) {
		this.prototypeOne = prototypeOne;
		this.prototypeTwo = prototypeTwo;
	}

	public ComplexObjectPrototype createPrototypeOne() { 
		return (ComplexObjectPrototype) prototypeOne.clone();
	}
	
	public ComplexObjectPrototype createPrototypeTwo() { 
		return (ComplexObjectPrototype) prototypeTwo.clone();
	}
	
	public static void main(String[] args) {
		ComplexObjectFactory prototypeFactory = new ComplexObjectFactory(new ComplexObjectOne(), new ComplexObjectTwo());
		System.out.println(prototypeFactory.createPrototypeOne());
		System.out.println(prototypeFactory.createPrototypeOne());
		System.out.println(prototypeFactory.createPrototypeOne());
		System.out.println(prototypeFactory.createPrototypeOne());
		System.out.println(prototypeFactory.createPrototypeTwo());
		System.out.println(prototypeFactory.createPrototypeTwo());
		System.out.println(prototypeFactory.createPrototypeTwo());
		System.out.println(prototypeFactory.createPrototypeTwo());
	}
	
}
