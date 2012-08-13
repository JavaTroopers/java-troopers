package org.javatroopers.concurrency.collections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.javatroopers.concurrency.util.Generator;
import org.javatroopers.concurrency.util.IntegersGenerator;

public class ListsBechmarking {
	private static final int MAX_NUMBER = 100;
	private static final int MAX_CYCLES_WRITES = 100;
	private static final int MAX_CYCLES_READS = 1000000;

	private static abstract class ListActionThread<E> implements Runnable {
		protected final List<E> list;
		protected final Generator<E> generator;
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		public ListActionThread(final List<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
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

	private static class ListWritter<E> extends ListActionThread<E> {

		public ListWritter(final List<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
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

	private static class ListReader<E> extends ListActionThread<E> {
		public ListReader(final List<E> list, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			super(list, startSignal, doneSignal, generator);
		}

		@Override
		public void doAction() {
			E value;
			for (int i = 0; i < MAX_CYCLES_READS; i++) {
				value = generator.next();
				if (list.contains(value)) {
					list.get(list.indexOf(value));
				}
			}
		}
	}

	private static long testVector(final int nThreads) throws InterruptedException {
		final List<Integer> testList = new Vector<Integer>();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new ListWritter<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new ListReader<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	private static long testSynchronizedList(int nThreads) throws InterruptedException {
		final List<Integer> testList = Collections.synchronizedList(new ArrayList<Integer>());
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new ListWritter<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new ListReader<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	private static long testConcurrentList(int nThreads) throws InterruptedException {
		final CopyOnWriteArrayList<Integer> testList = new CopyOnWriteArrayList<Integer>();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new ListWritter<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new ListReader<Integer>(testList, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
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
			System.out.println(df.format(new Date(testVector(n))) + "," + df.format(new Date(testSynchronizedList(n))) + "," + df.format(new Date(testConcurrentList(n))));
		}
	}
}
