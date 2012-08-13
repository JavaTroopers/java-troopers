package org.javatroopers.concurrency.prodcons.impl;

import org.javatroopers.concurrency.prodcons.ProducerConsumer;
import org.javatroopers.concurrency.util.PrimesGenerator;


public class ProducerConsumerBadDeadLock extends ProducerConsumer<Integer> {
	private Integer buffer = null;

	public void produce() {
		PrimesGenerator generator = new PrimesGenerator();
		while (true) {
			int prime = generator.next();
			while (buffer != null)
				;
			buffer = prime;
			log(prime);
		}
	}

	public void consume() {
		while (true) {
			int value;
			while (buffer == null)
				;
			value = buffer;
			buffer = null;
			log(value);
		}
	}

	public static void main(String[] args) {
		final ProducerConsumerBadDeadLock app = new ProducerConsumerBadDeadLock();
		app.start(1, 1);
	}

}
