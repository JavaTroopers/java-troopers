package org.javatroopers.concurrency.collections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.javatroopers.concurrency.util.Generator;
import org.javatroopers.concurrency.util.IntegersGenerator;

public class MapsBenchmarking {
	private static final int MAX_NUMBER = 100;
	private static final int MAX_CYCLES = 1000000;

	private static abstract class MapActionThread<E> implements Runnable {
		protected final Map<E, E> map;
		protected final Generator<E> generator;
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		public MapActionThread(final Map<E, E> map, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			this.map = map;
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

	private static class MapWritter<E> extends MapActionThread<E> {

		public MapWritter(final Map<E, E> map, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			super(map, startSignal, doneSignal, generator);
		}

		@Override
		public void doAction() {
			if (map instanceof ConcurrentHashMap) {
				ConcurrentHashMap<E, E> concurrentMap = (ConcurrentHashMap<E, E>) map;
				for (int i = 0; i < MAX_CYCLES; i++) {
					concurrentMap.putIfAbsent(generator.next(), generator.next());
				}
			} else {
				E key;
				for (int i = 0; i < MAX_CYCLES; i++) {
					key = generator.next();
					if (!map.containsKey(key)) {
						map.put(key, generator.next());
					}
				}
			}
		}
	}

	private static class MapRemover<E> extends MapActionThread<E> {
		public MapRemover(final Map<E, E> map, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Generator<E> generator) {
			super(map, startSignal, doneSignal, generator);
		}

		@Override
		public void doAction() {
			for (int i = 0; i < MAX_CYCLES; i++) {
				map.remove(generator.next());
			}
		}
	}

	private static long testHashtable(final int nThreads) throws InterruptedException {
		final Map<Integer, Integer> testMap = new Hashtable<Integer, Integer>();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new MapWritter<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new MapRemover<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	private static long testSynchronizedMap(final int nThreads) throws InterruptedException {
		final Map<Integer, Integer> testMap = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new MapWritter<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new MapRemover<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
		}
		long startTime = System.currentTimeMillis();
		startSignal.countDown();
		doneSignal.await();
		long finishTime = System.currentTimeMillis();
		return finishTime - startTime;
	}

	private static long testConcurrentHashMap(final int nThreads) throws InterruptedException {
		final ConcurrentHashMap<Integer, Integer> testMap = new ConcurrentHashMap<Integer, Integer>();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(nThreads * 2);
		for (int i = 0; i < nThreads; i++) {
			new Thread(new MapWritter<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
			new Thread(new MapRemover<Integer>(testMap, startSignal, doneSignal, new IntegersGenerator(MAX_NUMBER))).start();
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
			System.out.println(df.format(new Date(testHashtable(n))) + "," + df.format(new Date(testSynchronizedMap(n))) + "," + df.format(new Date(testConcurrentHashMap(n))));
		}
	}

}
