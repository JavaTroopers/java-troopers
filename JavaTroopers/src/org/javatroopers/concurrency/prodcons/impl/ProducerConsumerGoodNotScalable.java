package org.javatroopers.concurrency.prodcons.impl;

import org.javatroopers.concurrency.prodcons.ProducerConsumer;
import org.javatroopers.concurrency.util.PrimesGenerator;

public class ProducerConsumerGoodNotScalable extends ProducerConsumer<Integer> {
	private Integer buffer = null;
	private final Object lock = new Object();

	public void produce() {
		PrimesGenerator generator = new PrimesGenerator();
		while (true) {
			int prime = generator.next();
			synchronized (lock) {
				while (buffer != null) {
					try {
						lock.wait();
					} catch (InterruptedException ex) {
					}
				}
				buffer = prime;
				lock.notify();
				log(prime);
			}
		}
	}

	public void consume() {
		while (true) {
			int value;
			synchronized (lock) {
				while (buffer == null) {
					try {
						lock.wait();
					} catch (InterruptedException ex) {
					}
				}
				value = buffer;
				buffer = null;
				lock.notify();
				log(value);
			}
		}
	}

	public static void main(String[] args) {
		ProducerConsumerGoodNotScalable app = new ProducerConsumerGoodNotScalable();
//		app.start(1, 1);	// This works fine, just 1 producer and 1 consumer
		app.start(1, 2);	// Try this to see the problem
	}
}
