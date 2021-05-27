package com.studyCollection.studyList.studyArrayList;

import java.util.Arrays;
import java.util.List;

public class ZGQArrayList<E> {

	transient Object[] elementData;

	//(ZGQ)
	private int size;
	/**---ZGQ---
	 *
	 */
	protected transient int modCount = 0;
	//(ZGQ) 第一次add默认设置的大小
	private static final int DEFAULT_CAPACITY = 10;
	//(ZGQ) 通过空参创建ArrayList时，默认大小为0
	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	public ZGQArrayList() {
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}

	public boolean add(E e) {
		ensureCapacityInternal(size + 1);  // Increments modCount!!
		//ZGQ 直接在尾部添加
		elementData[size++] = e;
		return true;
	}

	/**---ZGQ---
	 * 根据下标删除
	 *   注：根据元素进行删除时间复杂度为1
	 * @param index 要删除元素在数组中的下标
	 * @return
	 */
	public E remove(int index) {
		rangeCheck(index);

		modCount++;
		//(ZGQ) 查询出要删除的元素
		E oldValue = elementData(index);

		/**---ZGQ---
		 * 假设ArrayList集合有16个元素，现在要删除下标为10的元素
		 *  numMoved = size - index - 1 = 16 -10 -1 = 5   numMoved表示计算被删除的元素后面还有几个元素
		 */
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}
		//(ZGQ) 将最后一个位置置空
		elementData[--size] = null; // clear to let GC do its work

		return oldValue;
	}

	/**---ZGQ---
	 * 直接根据元素进行删除
	 *  注：根据元素进行删除时间复杂度为n
	 * @param o 要删除的元素
	 * @return
	 */
	public boolean remove(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (elementData[index] == null) {
					fastRemove(index);
					return true;
				}
		} else {
			//(ZGQ) 从下标为0开始循环遍历
			for (int index = 0; index < size; index++)
				if (o.equals(elementData[index])) {
					//(ZGQ) 匹配到要删除的元素再调用fastRemove()方法
					fastRemove(index);
					return true;
				}
		}
		return false;
	}

	/**---ZGQ---
	 * 删除元素
	 * @param index
	 */
	private void fastRemove(int index) {
		modCount++;
		/**---ZGQ---
		 * 假设ArrayList集合有16个元素，现在要删除下标为10的元素
		 *  numMoved = size - index - 1 = 16 -10 -1 = 5   numMoved表示计算被删除的元素后面还有几个元素
		 */
		int numMoved = size - index - 1;
		//(ZGQ) 若被删除元素后面还有元素就进入if
		if (numMoved > 0) {
			/**---ZGQ---
			 * arraycopy()方法的参数说明：
			 *   参数1：Object src 表示原数组
			 *   参数2：int srcPos 表示被删除下标的后一个下标，若被删除的小标为10，那么srcPos=11
			 *   参数3：Object dest 目标数组，对原数组做备份
			 *   参数4：int destPos 表示将原数组从被删除下标的下一个下标开始(下标11开始)，拷贝到目标数组的被删除下标处
			 *   参数5：int length 就是numMoved，表示被删除的元素后面还有几个元素
			 */
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}
		//(ZGQ) 将最后一个位置置空
		elementData[--size] = null; // clear to let GC do its work
	}

	private void ensureCapacityInternal(int minCapacity) {
		if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}

		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity) {

		modCount++;

		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private void grow(int minCapacity) {
		// overflow-conscious code
		//(ZGQ) 先获取容器原来的容量大小
		int oldCapacity = elementData.length;
		//(ZGQ) 初始化扩容： 0 + (0 >> 1)
		//(ZGQ) 当大小超过10扩容：10 + (10 >> 1) = 15
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		// minCapacity is usually close to size, so this is a win:
		//(ZGQ) 对ArrayList进行初始化。copyOf()方法会将原来数组拷贝到新数组
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	public E get(int index) {
		rangeCheck(index);
		return elementData(index);
	}

	private void rangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: "+index+", Size: "+size;
	}

	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}


}
