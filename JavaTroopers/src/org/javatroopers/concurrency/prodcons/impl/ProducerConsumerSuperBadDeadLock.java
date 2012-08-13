package org.javatroopers.concurrency.prodcons.impl;

import org.javatroopers.concurrency.prodcons.ProducerConsumer;
import org.javatroopers.concurrency.util.PrimesGenerator;


public class ProducerConsumerSuperBadDeadLock extends ProducerConsumer<Integer> {
	private Integer buffer = null;
	private final Object lock = new Object();

	public void produce() {
		PrimesGenerator generator = new PrimesGenerator();
		while (true) {
			int prime = generator.next();
			synchronized (lock) {
				while (buffer != null)
					;
				buffer = prime;
				log(prime);
			}
		}
	}

	public void consume() {
		while (true) {
			int value;
			synchronized (lock) {
				while (buffer == null)
					;
				value = buffer;
				buffer = null;
				log(value);
			}
		}
	}

	public static void main(String[] args) {
		ProducerConsumerSuperBadDeadLock app = new ProducerConsumerSuperBadDeadLock();
		app.start(1, 1);
	}

}
