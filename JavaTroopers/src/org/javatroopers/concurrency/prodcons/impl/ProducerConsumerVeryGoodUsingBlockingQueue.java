package org.javatroopers.concurrency.prodcons.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.javatroopers.concurrency.prodcons.ProducerConsumer;
import org.javatroopers.concurrency.util.PrimesGenerator;


public class ProducerConsumerVeryGoodUsingBlockingQueue extends ProducerConsumer<Integer> {
	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1, true);

	public void produce() {
		PrimesGenerator generator = new PrimesGenerator();
		int prime;
		while (true) {
			try {
				prime = generator.next();
				queue.put(prime);
				log(prime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void consume() {
		while (true) {
			try {
				log(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ProducerConsumerVeryGoodUsingBlockingQueue app = new ProducerConsumerVeryGoodUsingBlockingQueue();
		app.start(5, 5);
	}

}
