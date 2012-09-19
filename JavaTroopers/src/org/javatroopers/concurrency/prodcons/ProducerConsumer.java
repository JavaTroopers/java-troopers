package org.javatroopers.concurrency.prodcons;

public abstract class ProducerConsumer<T> {

	public abstract void produce();

	public abstract void consume();

	public void start(final int nProducers, final int nConsumers) {
		Thread thread;
		for (int i = 0; i < nProducers; i++) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					produce();
				}
			});
			thread.setName("Producer_" + i);
			thread.start();
		}
		for (int i = 0; i < nConsumers; i++) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					consume();
				}
			});
			thread.setName("Consumer_" + i);
			thread.start();
		}
		/*
		//Using thread pools
		Executor producersPool = Executors.newFixedThreadPool(10);
		Executor consumersPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < nProducers; i++) {
			producersPool.execute(new Runnable() {
				@Override
				public void run() {
					produce();
				}
			});
		}
		for (int i = 0; i < nConsumers; i++) {
			consumersPool.execute(new Runnable() {
				@Override
				public void run() {
					consume();
				}
			});
		}
		*/
	}

	protected void log(final Object msg) {
		System.out.println(Thread.currentThread().getName() + " > " + msg);
	}

}
