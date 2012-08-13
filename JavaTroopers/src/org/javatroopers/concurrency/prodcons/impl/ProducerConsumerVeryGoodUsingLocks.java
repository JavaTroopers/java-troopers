package org.javatroopers.concurrency.prodcons.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.javatroopers.concurrency.prodcons.ProducerConsumer;
import org.javatroopers.concurrency.util.PrimesGenerator;


public class ProducerConsumerVeryGoodUsingLocks extends ProducerConsumer<Integer> {
	private Integer buffer = null;
	private Lock lock = new ReentrantLock();
	private Condition placed = lock.newCondition();
	private Condition taken = lock.newCondition();

	public void produce() {
		PrimesGenerator generator = new PrimesGenerator();

		while (true) {
			int prime = generator.next();
			lock.lock();
			try {
				while (buffer != null) {
					taken.awaitUninterruptibly();
				}
				buffer = prime;
				placed.signal();
				log(prime);
			} finally {
				lock.unlock();
			}

		}
	}

	public void consume() {
		while (true) {
			int value;

			lock.lock();
			try {
				while (buffer == null) {
					placed.awaitUninterruptibly();
				}
				value = buffer;
				buffer = null;
				taken.signal();
				log(value);
			} finally {
				lock.unlock();
			}

		}
	}

	public static void main(String[] args) {
		ProducerConsumerVeryGoodUsingLocks app = new ProducerConsumerVeryGoodUsingLocks();
		app.start(1, 5);
	}

}
