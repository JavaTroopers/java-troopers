package org.javatroopers.patterns.creational.singleton;

public class Singleton {
	private static Singleton instance;
	static {
		instance = new Singleton();
	}
	
	private Singleton() {

	}

	public static Singleton getInstance() {
		return instance;
	}
	
	/*
	private static class SingletonHolder {
		public static final Singleton instance = new Singleton();
	}

	public static Singleton getInstance() {
		return SingletonHolder.instance;
	}
	*/

	public String sayHello() {
		return "Hello from singleton " + toString();
	}

	public static void main(String[] args) {
		Singleton singletonInstance = Singleton.getInstance();
		System.out.println(singletonInstance.sayHello());
		Singleton anotherSingletonInstance = Singleton.getInstance();
		System.out.println(anotherSingletonInstance.sayHello());
	}

}
