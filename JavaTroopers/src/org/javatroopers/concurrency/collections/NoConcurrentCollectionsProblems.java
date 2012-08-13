package org.javatroopers.concurrency.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class NoConcurrentCollectionsProblems<E> {

	private Map<E, E> map = Collections.synchronizedMap(new HashMap<E, E>());
	private List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public void test(E key, E value) {

		// 1. Put-if-absent: race conditions
		if (!map.containsKey(key)) {
			// this gap is an eternity for the JVM
			map.put(key, value); // Could overwrite key if other thread puts
									// before
		}

		// 2. Ad-hoc iteration: race conditions
		for (int i = 0; i < list.size(); i++) {
			doSomething(list.get(i)); // NullPointerException if other thread
										// deletes item i
		}

		// 3. Using Iterator: can throw ConcurrentModificationException
		// (unchecked XD)
		for (Iterator<E> iterator = list.iterator(); iterator.hasNext();) { // Fail-fast
																			// iterator
			doSomething(iterator.next());
		}
	}

	protected abstract void doSomething(E item);

}
