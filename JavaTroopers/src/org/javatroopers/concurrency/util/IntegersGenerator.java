package org.javatroopers.concurrency.util;

import java.util.Random;

public class IntegersGenerator implements Generator<Integer> {
	private final int maxNumber;
	private final Random random;

	public IntegersGenerator(int maxNumber) {
		random = new Random();
		this.maxNumber = maxNumber;
	}

	@Override
	public Integer next() {
		return random.nextInt(maxNumber);
	}
}
