package com.studyCollection.studyList.studyArrayList;

import java.util.Arrays;

public class ZGQVector<E> {

	protected transient int modCount = 0;
	protected Object[] elementData;
	protected int capacityIncrement;
	protected int elementCount;
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


	public ZGQVector(int initialCapacity, int capacityIncrement) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " +
					initialCapacity);
		this.elementData = new Object[initialCapacity];
		this.capacityIncrement = capacityIncrement;
	}

	public ZGQVector(int initialCapacity) {
		this(initialCapacity, 0);
	}

	public ZGQVector() {
		this(10);
	}

	public synchronized boolean add(E e) {
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = e;
		return true;
	}

	private void ensureCapacityHelper(int minCapacity) {
		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = elementData.length;
		/**---ZGQ---
		 * newCapacity为oldCapacity的2倍。Vector扩容大小为原来的2倍。
		 * 通过Vector(int initialCapacity, int capacityIncrement)构造器进行创建集合，使用capacityIncrement来自定义扩容的大小。
		 */
		int newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity);
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

}
