package org.javatroopers.concurrency.util;

import java.util.SortedSet;
import java.util.TreeSet;

public class PrimesGenerator implements Generator<Integer> {
	private SortedSet<Integer> generatedPrimes = new TreeSet<Integer>();

	@Override
	public Integer next() {
		// Comparison to make production infinite
		if (!generatedPrimes.isEmpty() && generatedPrimes.last() + 2 >= Integer.MAX_VALUE) {
			generatedPrimes.clear();
		}

		if (generatedPrimes.isEmpty()) {
			generatedPrimes.add(2);
		} else if (generatedPrimes.last() == 2) {
			generatedPrimes.add(3);
		} else {
			boolean primeFound = false;
			Integer nextPrime = generatedPrimes.last();
			while (!primeFound) {
				nextPrime += 2;
				for (Integer prime : generatedPrimes) {
					if (!(primeFound = (nextPrime % prime != 0))) {
						break;
					}
				}
			}
			generatedPrimes.add(nextPrime);
		}
		return generatedPrimes.last();
	}

	public static void main(String[] args) {
		PrimesGenerator pg = new PrimesGenerator();
		while (true) {
			System.out.println(pg.next());
		}
	}

}
