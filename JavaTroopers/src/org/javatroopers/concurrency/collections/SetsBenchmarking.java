package org.javatroopers.concurrency.collections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import org.javatroopers.concurrency.util.Generator;
import org.javatroopers.concurrency.util.IntegersGenerator;

public class SetsBenchmarking {
	private static final int MAX_NUMBER = 100;
	private static final int MAX_CYCLES_WRITES = 1000000;
	private static final int MAX_CYCLES_REMOVALS = 1000000;

	private static abstract class SetActionThread<E> implements Runnable {
		protected final Set<E> list;
		protected final Generator<E> generator;
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		public SetActionThread(final Set<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			this.list = list;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
			this.generator = generator;
		}

		public abstract void doAction();

		@Override
		public void run() {
			try {
				startSignal.await();
				doAction();
				doneSignal.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class SetWritter<E> extends SetActionThread<E> {

		public SetWritter(final Set<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			super(list, startSignal, doneSignal, generator);
		}

		@Override
		public void doAction() {
			E value;
			for (int i = 0; i < MAX_CYCLES_WRITES; i++) {
				value = generator.next();
				if (list.contains(value)) {
					list.add(value);
				}
			}
		}
	}

	private static class SetRemover<E> extends SetActionThread<E> {
		public SetRemover(final Set<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			super(list, startSignal, doneSignal, generator);
		}

		@Override
		public void doAction() {
			E value;
			for (int i = 0; i < MAX_CYCLES_REMOVALS; i++) {
				value = generator.next();
				if (list.contains(value)) {
					list.remove(value);
				}
			}
		}
	}

	private static long testSynchronizedSet(int nThreads) throws InterruptedException {
		final Set<Integer> testSet = Collections.synchronizedSet(new HashSet<Integer>());
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new SetWritter<Integer>(testSet, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new SetRemover<Integer>(testSet, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	private static long testConcurrentSet(int nThreads) throws InterruptedException {
		final Set<Integer> testSet = new CopyOnWriteArraySet<Integer>();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new SetWritter<Integer>(testSet, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new SetRemover<Integer>(testSet, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	public static void main(String[] args) throws Exception {
		final DateFormat df = new SimpleDateFormat("ss.SSS");
		for (int n : new int[] { 1, 2, 4, 8, 16, 32, 64, 128 }) {
			System.out.println(df.format(new Date(testSynchronizedSet(n))) + "," + df.format(new Date(testConcurrentSet(n))));
		}
	}
}
