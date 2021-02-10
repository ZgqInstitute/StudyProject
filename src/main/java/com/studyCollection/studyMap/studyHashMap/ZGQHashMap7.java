package com.studyCollection.studyMap.studyHashMap;

import java.util.Map;
import java.util.Objects;

public class ZGQHashMap7<K, V> {

	//(ZGQ) 默认的加载因子
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	//(ZGQ) 阈值 与加载因子有关系，由加载因子计数得到
	int threshold;
	//(ZGQ) 通过调用空参的构造函数，
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
	static final int MAXIMUM_CAPACITY = 1 << 30;
	//(ZGQ) hash种子，会参与key的hashCode运算，作用就是让hashCode更散列一些，以便后面和数组长度做 与 运算产生的数组下标更随机些
	transient int hashSeed = 0;
	static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;
	final float loadFactor;

	transient int modCount;
	//(ZGQ) 表示当前hashMap的数组已经存放了几个元素
	transient int size;
	//(ZGQ) 表示hashMap的数组
	transient Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;
	static final Entry<?, ?>[] EMPTY_TABLE = {};

	private static class Holder {

		/**
		 * Table capacity above which to switch to use alternative hashing.
		 */
		static final int ALTERNATIVE_HASHING_THRESHOLD;

		static {
			String altThreshold = java.security.AccessController.doPrivileged(
					new sun.security.action.GetPropertyAction(
							"jdk.map.althashing.threshold"));

			int threshold;
			try {
				threshold = (null != altThreshold)
						? Integer.parseInt(altThreshold)
						: ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

				// disable alternative hashing if -1
				if (threshold == -1) {
					threshold = Integer.MAX_VALUE;
				}

				if (threshold < 0) {
					throw new IllegalArgumentException("value must be positive integer.");
				}
			} catch (IllegalArgumentException failed) {
				throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
			}

			ALTERNATIVE_HASHING_THRESHOLD = threshold;
		}
	}

	/**
	 * ---ZGQ---
	 * 调用空参的构造函数时，会默认设置threshold为16(后面put时才会创建数组，大小为threshold)
	 * 注：此时数组还没有开始创建
	 */
	public ZGQHashMap7() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR); // all other fields defaulted
	}

	/**
	 * @param initialCapacity 自定义大小
	 * @param loadFactor      加载因子
	 */
	public ZGQHashMap7(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		}

		this.loadFactor = loadFactor;
		threshold = initialCapacity;
		init();
	}

	public V put(K key, V value) {
		if (table == EMPTY_TABLE) {
			inflateTable(threshold);
		}

		//(ZGQ) 可以存key=null
		if (key == null) {
			return putForNullKey(value);
		}

		//(ZGQ) 计数key的hash值
		int hash = hash(key);

		//(ZGQ) 根据hash值和数组的长度，计数put的对象应该放在hashMap数组的哪一个位置
		int i = indexFor(hash, table.length);

		/**---ZGQ---
		 * 若put的元素要存入数组的下标为2，这个循环就是遍历hashMap数组下标为2上的链表
		 * 看这个链表上是否有与put的元素key相等的对象，若有则进行替换
		 *
		 * 问题：put元素时采用头插法的好处？
		 *    在put元素时，假如计算出来要往数组下标为2的位置进行添加。
		 *    1）当数组下标为2位置上的链表的所有元素key与put元素的key都不相等时，这种情况下采用头插法与尾插法都一样，因为都要遍历整个链表；
		 *    2）扩容时有好处，注意听。
		 */
		for (Entry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
				//(ZGQ) 当put的key已经存在时，先获取旧key对应的value
				V oldValue = e.value;
				//(ZGQ) 新put的value覆盖旧value
				e.value = value;
				//(ZGQ) 该方法与hashMap无关，用在LinkedHashMap
				e.recordAccess(this);
				//(ZGQ) 再将旧value返回
				return oldValue;
			}
		}

		modCount++;

		/**---ZGQ---
		 * 这个方法就是添加元素
		 * hash：由key计数得到的hash值
		 * key
		 * value
		 * i：put的对象要存放在数组的哪一个下标
		 */
		addEntry(hash, key, value, i);
		return null;
	}

	void resize(int newCapacity) {
		//(ZGQ) table为旧数组
		Entry[] oldTable = table;
		//(ZGQ) oldCapacity：旧数组的长度
		int oldCapacity = oldTable.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		//(ZGQ) 扩容。重新创建数组
		Entry[] newTable = new Entry[newCapacity];

		//(ZGQ) 将旧数组的数据拷贝到新数组
		transfer(newTable, initHashSeedAsNeeded(newCapacity));

		//(ZGQ) 将新数组赋给hashMap
		table = newTable;

		//(ZGQ) 根据新数组的大小重新计数阈值
		threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
	}

	//(ZGQ) 假如要将原数组下标为2的元素拷贝到新数组
	void transfer(Entry[] newTable, boolean rehash) {
		int newCapacity = newTable.length;
		//(ZGQ) table为旧数组
		for (Entry<K, V> e : table) {
			while (null != e) {
				/**---ZGQ---
				 *  1）这里先把原数组下标为2的第一个元素的next属性先赋给next保存起来，因为后面要对这个next属性赋null
				 *  2）将原数组下标为2的第二个元素的next属性先赋给next保存起来
				 */
				Entry<K, V> next = e.next;
				if (rehash) {
					e.hash = null == e.key ? 0 : hash(e.key);
				}
				/**---ZGQ---
				 * 计算要存入新数组的哪个下标，有2种情况：假如原来数组大小为16，元素所在原数组的下标为2
				 *  1）存入新数组的下标还是为2
				 *  2）存入新数组的下标为 2 + 16 = 18
				 */
				int i = indexFor(e.hash, newCapacity);

				/**---ZGQ---
				 * 1-将原数组下标为2的第一个元素的next指向上一步计算出来的新数组下标(这里假设计数出新数组的下标也为2)，就是将原数组下标为2的第一个元素的next置为null
				 * 2-将原数组下标为2的第一个元素赋给原数组下标为2的第二个元素的next属性
				 */
				e.next = newTable[i];

				/**---ZGQ---
				 * 1-将原数组下标为2的第一个元素移动到新数组下标为2的位置
				 * 2-将链表向下移动
				 */
				newTable[i] = e;

				/**---ZGQ---
				 * 1-将原数组下标为2的第二个元素赋给e
				 * 2-将原数组下标为2的第三个元素赋给e
				 */
				e = next;
			}
		}
	}

	void addEntry(int hash, K key, V value, int bucketIndex) {
		/**---ZGQ---
		 * 扩容有2个条件：
		 * 条件1：size >= threshold
		 *    size：此时数组已经存放了几个元素
		 *    阈值：threshold = 数组的长度 * 加载因子0.75 = 16 * 0.75 = 12
		 * 条件2：null != table[bucketIndex]
		 */
		if ((size >= threshold) && (null != table[bucketIndex])) {
			//(ZGQ) 扩容方法   扩容为原来数组的2倍
			resize(2 * table.length);
			hash = (null != key) ? hash(key) : 0;
			bucketIndex = indexFor(hash, table.length);
		}

		createEntry(hash, key, value, bucketIndex);
	}

	void createEntry(int hash, K key, V value, int bucketIndex) {
		Entry<K, V> e = table[bucketIndex];
		//(ZGQ) 将新put的对象插入数组对应位置的头部，再将链表向下移动
		table[bucketIndex] = new Entry<>(hash, key, value, e);
		size++;
	}

	final int hash(Object k) {
		int h = hashSeed;
		if (0 != h && k instanceof String) {
			return sun.misc.Hashing.stringHash32((String) k);
		}

		//(ZGQ) h = h 异或 k的哈希值
		h ^= k.hashCode();

		// This function ensures that hashCodes that differ only by constant multiples at each bit position
		// have a bounded number of collisions (approximately 8 at default load factor).
		/**---ZGQ---
		 * 问题：这里的hashcode为什么还要做一系列的右移和异或操作？
		 *   因为这个hashcode后面要与数组的长度-1做 与 运算，得到插入数组的下标。这里假如数组的长度为16，减1操作后就为15，
		 *   二进制为1111。hashcode和1111做 与 运算，就得到hashcode的后4位，这里还要对hashcode做一系列的右移和异或操作
		 *   的目的就是让hashcode的后4位前面那些位（高位）也尽量参与后面与数组长度-1做 与 运算。
		 */
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) {
		// assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
		//(ZGQ) 哈希值对数组的长度做 与 操作
		return h & (length - 1);
	}

	/**
	 * ---ZGQ---
	 *
	 * @param toSize = threshold 在构造函数中确定的大小16
	 */
	private void inflateTable(int toSize) {
		// Find a power of 2 >= toSize
		/**---ZGQ---
		 * 并没有直接取threshold的大小来创建数组
		 * 而是取 >= toSize 的最小2的幂次方数：如toSize=17 那么就取大于17的最小2的幂次方数32
		 */
		int capacity = roundUpToPowerOf2(toSize);

		//(ZGQ) 阈值：threshold = capacity * loadFactor = 数组长度 * 0.75
		threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);

		//(ZGQ) 这里创建数组
		table = new Entry[capacity];
		initHashSeedAsNeeded(capacity);
	}

	final boolean initHashSeedAsNeeded(int capacity) {
		boolean currentAltHashing = hashSeed != 0;
		boolean useAltHashing = sun.misc.VM.isBooted() && (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
		boolean switching = currentAltHashing ^ useAltHashing;
		if (switching) {
			hashSeed = useAltHashing ? sun.misc.Hashing.randomHashSeed(this) : 0;
		}
		return switching;
	}

	//roundUpToPowerOf2(x)方法是找到大于等于x的最小2的幂次方数
	private static int roundUpToPowerOf2(int number) {
		// assert number >= 0 : "number must be non-negative";
		//highestOneBit(x)方法是找到小于等于x的最大2的幂次方数
		return number >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
	}

	private V putForNullKey(V value) {
		//遍历数组下标为0的位置
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null) {
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}
		modCount++;
		//(ZGQ) 当key = null 时，插入下标为0的位置
		addEntry(0, null, value, 0);
		return null;
	}

	void init() {
	}

	static class Entry<K, V> implements Map.Entry<K, V> {
		final K key;
		V value;
		Entry<K, V> next;
		//(ZGQ) 这个hash与key对应
		int hash;

		/**
		 * Creates new entry.
		 */
		Entry(int h, K k, V v, Entry<K, V> n) {
			value = v;
			next = n;
			key = k;
			hash = h;
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}

		public final int hashCode() {
			return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
		}

		public final String toString() {
			return getKey() + "=" + getValue();
		}

		/**
		 * This method is invoked whenever the value in an entry is
		 * overwritten by an invocation of put(k,v) for a key k that's already
		 * in the HashMap.
		 */
		void recordAccess(ZGQHashMap7<K, V> m) {
		}

		/**
		 * This method is invoked whenever the entry is
		 * removed from the table.
		 */
		void recordRemoval(ZGQHashMap7<K, V> m) {
		}
	}

	public V get(Object key) {
		if (key == null)
			return getForNullKey();
		Entry<K, V> entry = getEntry(key);

		return null == entry ? null : entry.getValue();
	}

	private V getForNullKey() {
		if (size == 0) {
			return null;
		}
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null)
				return e.value;
		}
		return null;
	}

	final Entry<K, V> getEntry(Object key) {
		if (size == 0) {
			return null;
		}

		//(ZGQ) 计算key的hashMap值
		int hash = (key == null) ? 0 : hash(key);
		for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
				return e;
		}
		return null;
	}
}
