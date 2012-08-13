package org.javatroopers.concurrency.util;

public interface Generator<E> {
	public E next();
}