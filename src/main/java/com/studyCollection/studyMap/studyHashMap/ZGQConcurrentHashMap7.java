//package com.studyCollection.studyMap.studyHashMap;
//
//import com.studyThread.studyReentrantLock.ZGQReentrantLock;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ZGQConcurrentHashMap7<K, V> {
//
//	/**
//	 * ---ZGQ---
//	 * 这个16指的是ConcurrentHashMap的所有Segment的所有HashEntry数组大小和，
//	 * 注：由DEFAULT_INITIAL_CAPACITY / EFAULT_CONCURRENCY_LEVEL可以得到每一个Segment中的HashEntry数组大小
//	 */
//	static final int DEFAULT_INITIAL_CAPACITY = 16;
//	static final float DEFAULT_LOAD_FACTOR = 0.75f;
//	//(ZGQ) 并发级别 指的是Segment数组大小默认是16
//	static final int DEFAULT_CONCURRENCY_LEVEL = 16;
//	static final int MAXIMUM_CAPACITY = 1 << 30;
//	//(ZGQ) segments的HashEntry[]数组大小最小要为2
//	static final int MIN_SEGMENT_TABLE_CAPACITY = 2;
//	//(ZGQ) 限制ConcurrentHashMap的数组segments大小最大为2的16次方
//	static final int MAX_SEGMENTS = 1 << 16; // slightly conservative
//	static final int RETRIES_BEFORE_LOCK = 2;
//	final int segmentMask;
//	final int segmentShift;
//	//(ZGQ) segments是ConcurrentHashMap的数组
//	final Segment<K, V>[] segments;
//	private transient final int hashSeed = randomHashSeed(this);
//
//	private static int randomHashSeed(ZGQConcurrentHashMap7 instance) {
//		if (sun.misc.VM.isBooted() && Holder.ALTERNATIVE_HASHING) {
//			return sun.misc.Hashing.randomHashSeed(instance);
//		}
//
//		return 0;
//	}
//
//	private static class Holder {
//		static final boolean ALTERNATIVE_HASHING;
//
//		static {
//			String altThreshold = java.security.AccessController.doPrivileged(
//					new sun.security.action.GetPropertyAction(
//							"jdk.map.althashing.threshold"));
//
//			int threshold;
//			try {
//				threshold = (null != altThreshold)
//						? Integer.parseInt(altThreshold)
//						: Integer.MAX_VALUE;
//
//				// disable alternative hashing if -1
//				if (threshold == -1) {
//					threshold = Integer.MAX_VALUE;
//				}
//
//				if (threshold < 0) {
//					throw new IllegalArgumentException("value must be positive integer.");
//				}
//			} catch (IllegalArgumentException failed) {
//				throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
//			}
//			ALTERNATIVE_HASHING = threshold <= MAXIMUM_CAPACITY;
//		}
//	}
//
//	transient Set<K> keySet;
//	transient Set<Map.Entry<K, V>> entrySet;
//	transient Collection<V> values;
//
//	public ZGQConcurrentHashMap7() {
//		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
//	}
//
//	static final <K, V> void setEntryAt(HashEntry<K, V>[] tab, int i, HashEntry<K, V> e) {
//		UNSAFE.putOrderedObject(tab, ((long) i << TSHIFT) + TBASE, e);
//	}
//
//	@SuppressWarnings("unchecked")
//	public ZGQConcurrentHashMap7(int initialCapacity, float loadFactor, int concurrencyLevel) {
//		if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
//			throw new IllegalArgumentException();
//		if (concurrencyLevel > MAX_SEGMENTS) {
//			concurrencyLevel = MAX_SEGMENTS;
//		}
//		// Find power-of-two sizes best matching arguments
//		int sshift = 0;
//		//(ZGQ) ssize表示的是segments数组大小
//		int ssize = 1;
//
//		//(ZGQ) 找 大于等于 我们指定的并发级别(DEFAULT_CONCURRENCY_LEVEL)的最小2的幂次方数
//		while (ssize < concurrencyLevel) {
//			++sshift;
//			//(ZGQ) ConcurrentHashMap的segments数组大小始终是2的倍数
//			ssize <<= 1;
//		}
//		this.segmentShift = 32 - sshift;
//		this.segmentMask = ssize - 1;
//		if (initialCapacity > MAXIMUM_CAPACITY) {
//			initialCapacity = MAXIMUM_CAPACITY;
//		}
//
//		//(ZGQ) 计算Segments的HashEntry[]数组大小
//		int c = initialCapacity / ssize;
//		if (c * ssize < initialCapacity) {
//			++c;
//		}
//
//		//(ZGQ) Segments的HashEntry[]数组大小最小值
//		int cap = MIN_SEGMENT_TABLE_CAPACITY;
//		while (cap < c) {
//			cap <<= 1;
//		}
//		// create segments and segments[0]
//		/**---ZGQ---
//		 * 需要知道的点：
//		 * 1）第二个参数(int) (cap * loadFactor)是扩容条件，指的是Segment内的HashEntry[]数组超过这个就会扩容；
//		 *    是Segment内的HashEntry[]数组扩容，而不是ConcurrentHashMap的数组Segment[]扩容
//		 * 2）s0会存放到Segment数组Segment<K, V>[] ss的第0个位置
//		 * 3）这个s0还有一个作用：当新put的元素如果要添加到ConcurrentHashMap数组Segment[]新的位置，此时新创建的Segment会使用到s0的一些属性
//		 */
//		Segment<K, V> s0 = new Segment<K, V>(loadFactor, (int) (cap * loadFactor), (HashEntry<K, V>[]) new HashEntry[cap]);
//		//(ZGQ) 创建一个空的Segment数组
//		Segment<K, V>[] ss = (Segment<K, V>[]) new Segment[ssize];
//
//		UNSAFE.putOrderedObject(ss, SBASE, s0); // ordered write of segments[0]
//		this.segments = ss;
//	}
//
//	/**---ZGQ---
//	 * ConcurrentHashMap的put方法是向Segment[]数组添加Segment对象；
//	 * Segment对象的put方法是向Segment对象中的HashEntry[]数组添加对象，我们添加的key value是通过这个put方法添加到Segment对象中的HashEntry[]数组中的
//	 */
//	@SuppressWarnings("unchecked")
//	public V put(K key, V value) {
//		Segment<K, V> s;
//		//(ZGQ) value不能为null
//		if (value == null)
//			throw new NullPointerException();
//		//(ZGQ) 根据key算出哈希值
//		int hash = hash(key);
//		//(ZGQ) j就是需要put到Segment[]数组的下标位置(segmentMask = Segment[]数组长度 - 1)
//		int j = (hash >>> segmentShift) & segmentMask;
//		/**---ZGQ---
//		 * 1）if条件是判断Segment[]数组下标为j的位置是否为空，若空则进入if创建Segment
//		 * 2）UNSAFE.getObject(segments, (j << SSHIFT) + SBASE))  去主内存获取segments数组下标为 j 位置的元素
//		 */
//		if ((s = (Segment<K, V>) UNSAFE.getObject(segments, (j << SSHIFT) + SBASE)) == null) {
//			//(ZGQ) 创建Segment对象放入Segment[]数组下标为j的位置
//			s = ensureSegment(j);
//		}
//		//(ZGQ) 调用Segment的put方法
//		return s.put(key, hash, value, false);
//	}
//
//	/**---ZGQ---
//	 * 在多线程环境下，假如有2个线程t1、t2
//	 * t1线程先创建了Segment对象并放入Segment数组的下标为k的位置；
//	 * t2线程就不用创建Segment对象了，直接获取就行
//	 * @param k 表示put的元素要添加到Segment数组的哪一个下标下
//	 * @return Segment对象 （可以将Segment对象理解为一个HashMap）
//	 */
//	@SuppressWarnings("unchecked")
//	private Segment<K, V> ensureSegment(int k) {
//		//(ZGQ) 获取Segment数组
//		final Segment<K, V>[] ss = this.segments;
//		//(ZGQ) u = k = j 表示put的元素要添加到Segment数组的哪一个下标下
//		long u = (k << SSHIFT) + SBASE; // raw offset
//		Segment<K, V> seg;
//		//(ZGQ) 先去主内存获取Segment[]数组下标为u位置的Segment，若取到了就直接返回，若没有取到，就进入if进行创建
//		if ((seg = (Segment<K, V>) UNSAFE.getObjectVolatile(ss, u)) == null) {
//			//(ZGQ) 获取Segment数组第一个Segment对象s0，这个s0在构造函数中已经创建好并放入Segment数组下标为0的位置
//			Segment<K, V> proto = ss[0]; // use segment 0 as prototype
//			//(ZGQ) 获取s0的HashEntry数组的长度，后面创建Segment对象时，Segment对象的HashEntry数组长度也等于s0的HashEntry数组的长度
//			int cap = proto.table.length;
//			float lf = proto.loadFactor;
//			int threshold = (int) (cap * lf);
//			//(ZGQ) 创建HashEntry数组
//			HashEntry<K, V>[] tab = (HashEntry<K, V>[]) new HashEntry[cap];
//			//(ZGQ) 重新判断主内存中Segment[]数组下标为u位置有没有Segment，若还是空，就进入if创建Segment对象
//			if ((seg = (Segment<K, V>) UNSAFE.getObjectVolatile(ss, u)) == null) { // recheck
//				//(ZGQ) 创建Segment对象
//				Segment<K, V> s = new Segment<K, V>(lf, threshold, tab);
//				//(ZGQ) 再判断主内存中Segment[]数组下标为u位置有没有Segment，若还是为空，就进入if将创建的Segment对象放入Segment[]数组下标为u的位置
//				while ((seg = (Segment<K, V>) UNSAFE.getObjectVolatile(ss, u)) == null) {
//					/**---ZGQ---
//					 *  《自旋，CAS的思想》
//					 * 获取主内存中Segment[]数组下标为u的位置的元素，若为参数3(null)，则将参数4(s)存入Segment[]数组下标为u的位置
//					 */
//					if (UNSAFE.compareAndSwapObject(ss, u, null, seg = s))
//						break;
//				}
//			}
//		}
//		return seg;
//	}
//
//	private int hash(Object k) {
//		int h = hashSeed;
//
//		if ((0 != h) && (k instanceof String)) {
//			return sun.misc.Hashing.stringHash32((String) k);
//		}
//
//		h ^= k.hashCode();
//
//		// Spread bits to regularize both segment and index locations,
//		// using variant of single-word Wang/Jenkins hash.
//		h += (h << 15) ^ 0xffffcd7d;
//		h ^= (h >>> 10);
//		h += (h << 3);
//		h ^= (h >>> 6);
//		h += (h << 2) + (h << 14);
//		return h ^ (h >>> 16);
//	}
//
//	/**
//	 * ---ZGQ---
//	 * 相当于HashMap的Entry
//	 */
//	static final class HashEntry<K, V> {
//		final int hash;
//		final K key;
//		volatile V value;
//		volatile HashEntry<K, V> next;
//
//		HashEntry(int hash, K key, V value, HashEntry<K, V> next) {
//			this.hash = hash;
//			this.key = key;
//			this.value = value;
//			this.next = next;
//		}
//
//		final void setNext(HashEntry<K, V> n) {
//			UNSAFE.putOrderedObject(this, nextOffset, n);
//		}
//
//		// Unsafe mechanics
//		static final sun.misc.Unsafe UNSAFE;
//		static final long nextOffset;
//
//		static {
//			try {
//				UNSAFE = sun.misc.Unsafe.getUnsafe();
//				Class k = HashEntry.class;
//				nextOffset = UNSAFE.objectFieldOffset
//						(k.getDeclaredField("next"));
//			} catch (Exception e) {
//				throw new Error(e);
//			}
//		}
//	}
//
//	static final class Segment<K, V> extends ZGQReentrantLock implements Serializable {
//		private static final long serialVersionUID = 2249069246763182397L;
//		static final int MAX_SCAN_RETRIES = Runtime.getRuntime().availableProcessors() > 1 ? 64 : 1;
//		/**---ZGQ---
//		 * 相当于HashMap的Entry
//		 */
//		transient volatile HashEntry<K, V>[] table;
//		transient int count;
//		transient int modCount;
//		transient int threshold;
//		final float loadFactor;
//
//		Segment(float lf, int threshold, HashEntry<K, V>[] tab) {
//			this.loadFactor = lf;
//			this.threshold = threshold;
//			this.table = tab;
//		}
//
//		/**---ZGQ---
//		 * 在Segment的put方法插入，插入到Segment的HashEntry[]数组
//		 */
//		final V put(K key, int hash, V value, boolean onlyIfAbsent) {
//
//			/**---ZGQ---
//			 * 获取锁：
//			 *   1）先使用ZGQReentrantLock的tryLock()方法尝试获取锁，若获取到了就返回空的node
//			 *   2）若没有获取到锁，则调用scanAndLockForPut()方法：
//			 *        scanAndLockForPut()方法会先使用tryLock()再lock()的方式获取锁，当tryLock()没有获取到锁但不会阻塞，还可以执行其他任务，如创建HashEntry对象
//			 */
//			HashEntry<K, V> node = tryLock() ? null : scanAndLockForPut(key, hash, value);
//
//			V oldValue;
//			try {
//				//(ZGQ) 获取segment的HashEntry[]数组
//				HashEntry<K, V>[] tab = table;
//				//(ZGQ) 计算要存入HashEntry[]数组的下标
//				int index = (tab.length - 1) & hash;
//     			//(ZGQ) 获取HashEntry[]数组下标为index的值
//				HashEntry<K, V> first = entryAt(tab, index);
//				for (HashEntry<K, V> e = first; ; ) {
//					if (e != null) {//(ZGQ) 若first不为null，进入if。 遍历链表上的节点看有没有与新增元素的key相等，有相同的就替换
//						K k;
//						if ((k = e.key) == key || (e.hash == hash && key.equals(k))) {
//							//(ZGQ) 先将旧值保存起来
//							oldValue = e.value;
//							if (!onlyIfAbsent) {
//								//(ZGQ) 替换旧值
//								e.value = value;
//								++modCount;
//							}
//							break;
//						}
//						//(ZGQ) 当遍历到链表最后一个节点时还没有与新增元素的key相同的话，e 就等于 null，就会进入下面的else
//						e = e.next;
//					} else {
//						/**---ZGQ---
//						 * 进入else有2种情况：
//						 *   1）链表的头节点就为null
//						 *   2）链表有元素，会先进入上面的if。上面的if遍历完链表所有节点的key与新增元素的key都不相等再进入这里的else
//						 */
//						if (node != null)
//							node.setNext(first);
//						else
//							//(ZGQ) 创建HashEntry，采用头插法进行插入
//							node = new HashEntry<K, V>(hash, key, value, first);
//						//(ZGQ) count表示HashEntry[]数组已经存放了几个元素
//						int c = count + 1;
//						//(ZGQ) 若HashEntry[]数组存放的元素已经达到阈值就需要进行扩容
//						if (c > threshold && tab.length < MAXIMUM_CAPACITY)
//							//(ZGQ) 扩容
//							rehash(node);
//						else
//						    /**---ZGQ---
//						     * 向HashEntry[]数组(tab)下标为index添加HashEntry(node)，采用头插法插入，会将链表向下移动。
//							 * 注：这里采用UnSafe类来操作是确保操作的是主内存的值，
//							 *    若采用HashEntry[index] = node方式进行添加是在线程自己的工作内存中操作
//						     */
//							setEntryAt(tab, index, node);
//						++modCount;
//						count = c;
//						oldValue = null;
//						break;
//					}
//				}
//			} finally {
//				unlock();
//			}
//			return oldValue;
//
//		}
//
//		@SuppressWarnings("unchecked")
//		private void rehash(HashEntry<K, V> node) {//(ZGQ) 入参node是链表的头节点
//			HashEntry<K, V>[] oldTable = table;
//			int oldCapacity = oldTable.length;
//			//(ZGQ) 新HashEntry[]数组的长度取旧HashEntry[]数组的长度的2倍
//			int newCapacity = oldCapacity << 1;
//			//(ZGQ) 根据HashEntry[]数组新大小重新计数阈值
//			threshold = (int) (newCapacity * loadFactor);
//			//(ZGQ) 创建新HashEntry[]数组
//			HashEntry<K, V>[] newTable = (HashEntry<K, V>[]) new HashEntry[newCapacity];
//			//(ZGQ) sizeMask的作用就是与hash值做 与 运算，计数put的元素添加到HashEntry[]数组的哪一个下标
//			int sizeMask = newCapacity - 1;
//			//(ZGQ) 遍历旧HashEntry[]数组
//			for (int i = 0; i < oldCapacity; i++) {
//				HashEntry<K, V> e = oldTable[i];
//				if (e != null) {
//					//(ZGQ) 先将头节点的下一个节点保存起来
//					HashEntry<K, V> next = e.next;
//					//(ZGQ) 计数第一个节点要保存到新数组的哪个下标
//					int idx = e.hash & sizeMask;
//					if (next == null)   //  Single node on list
//						newTable[idx] = e;
//					else { // Reuse consecutive sequence at same slot
//						//(ZGQ)
//						HashEntry<K, V> lastRun = e;
//						int lastIdx = idx;
//						for (HashEntry<K, V> last = next; last != null; last = last.next) {
//							int k = last.hash & sizeMask;
//							if (k != lastIdx) {//(ZGQ) 判断旧数组中链表的第一个节点与第二个节点是否放到新数组的同一个下标
//								lastIdx = k;
//								lastRun = last;
//							}
//						}
//						newTable[lastIdx] = lastRun;
//						// Clone remaining nodes
//						for (HashEntry<K, V> p = e; p != lastRun; p = p.next) {
//							V v = p.value;
//							int h = p.hash;
//							int k = h & sizeMask;
//							HashEntry<K, V> n = newTable[k];
//							newTable[k] = new HashEntry<K, V>(h, p.key, v, n);
//						}
//					}
//				}
//			}
//			int nodeIndex = node.hash & sizeMask; // add the new node
//			node.setNext(newTable[nodeIndex]);
//			newTable[nodeIndex] = node;
//			table = newTable;
//		}
//
//
//		private HashEntry<K, V> scanAndLockForPut(K key, int hash, V value) {
//			/**---ZGQ---
//			 * this：表示当前的Segment对象：
//			 * entryForHash()方法就是去HashEntry[]数组对应下标获取HashEntry对象
//			 */
//			HashEntry<K, V> first = entryForHash(this, hash);
//			HashEntry<K, V> e = first;
//			HashEntry<K, V> node = null;
//			//(ZGQ) 重试次数
//			int retries = -1; // negative while locating node
//
//			/**---ZGQ---
//			 * 这里采用先tryLock()再lock()的方式获取锁的好处是：当tryLock()没有获取到锁但不会阻塞，还可以执行其他任务，如创建HashEntry对象
//			 */
//			while (!tryLock()) {
//				HashEntry<K, V> f; // to recheck first below
//				if (retries < 0) {
//					/**---ZGQ---
//					 * 能进入if有2种情况：
//					 *   1）若去HashEntry[]数组对应下标获取到的HashEntry对象为null，则进入if
//					 *   2）若去HashEntry[]数组对应下标获取到的HashEntry对象不为null，则一直执行e = e.next; 直到链表的尾节点为null再进入if
//					 */
//					if (e == null) {
//						if (node == null) // speculatively create node
//							//(ZGQ) 创建HashEntry对象
//							node = new HashEntry<K, V>(hash, key, value, null);
//						retries = 0;
//					} else if (key.equals(e.key))//(ZGQ) 若发现链表中有节点的key与新增的key相同，那么就不需要创建HashEntry对象
//						retries = 0;
//					else
//						e = e.next;
//
//				} else if (++retries > MAX_SCAN_RETRIES) {//(ZGQ) 重试次数与CPU的核数有关
//					lock();
//					break;
//				} else if ((retries & 1) == 0 && (f = entryForHash(this, hash)) != first) {
//					e = first = f; // re-traverse if entry changed
//					retries = -1;
//				}
//			}
//			return node;
//		}
//	}
//
//	public V get(Object key) {
//		Segment<K,V> s; // manually integrate access methods to reduce overhead
//		HashEntry<K,V>[] tab;
//		int h = hash(key);
//		//(ZGQ) 计数下标
//		long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
//		//(ZGQ) 通过UnSafe类去获取下标为u位置的元素
//		if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null && (tab = s.table) != null) {
//			//(ZGQ) 遍历链表
//			for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile(tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE); e != null; e = e.next) {
//				K k;
//				if ((k = e.key) == key || (e.hash == h && key.equals(k)))
//					return e.value;
//			}
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	static final <K,V> Segment<K,V> segmentAt(Segment<K,V>[] ss, int j) {
//		long u = (j << SSHIFT) + SBASE;
//		return ss == null ? null :
//				(Segment<K,V>) UNSAFE.getObjectVolatile(ss, u);
//	}
//
//	public int size() {
//		// Try a few times to get accurate count. On failure due to
//		// continuous async changes in table, resort to locking.
//		final Segment<K,V>[] segments = this.segments;
//		int size;
//		boolean overflow; // true if size overflows 32 bits
//		long sum;         // sum of modCounts
//		long last = 0L;   // previous sum
//		int retries = -1; // first iteration isn't retry
//		try {
//			for (;;) {
//				if (retries++ == RETRIES_BEFORE_LOCK) {
//					for (int j = 0; j < segments.length; ++j)
//						ensureSegment(j).lock(); // force creation
//				}
//				sum = 0L;
//				size = 0;
//				overflow = false;
//				for (int j = 0; j < segments.length; ++j) {
//					Segment<K,V> seg = segmentAt(segments, j);
//					if (seg != null) {
//						sum += seg.modCount;
//						int c = seg.count;
//						if (c < 0 || (size += c) < 0)
//							overflow = true;
//					}
//				}
//				if (sum == last)
//					break;
//				last = sum;
//			}
//		} finally {
//			if (retries > RETRIES_BEFORE_LOCK) {
//				for (int j = 0; j < segments.length; ++j)
//					segmentAt(segments, j).unlock();
//			}
//		}
//		return overflow ? Integer.MAX_VALUE : size;
//	}
//
//
//
//	@SuppressWarnings("unchecked")
//	static final <K, V> HashEntry<K, V> entryForHash(Segment<K, V> seg, int h) {
//		HashEntry<K, V>[] tab;
//		return (seg == null || (tab = seg.table) == null) ? null :
//				(HashEntry<K, V>) UNSAFE.getObjectVolatile(tab, ((long) (((tab.length - 1) & h)) << TSHIFT) + TBASE);
//	}
//
//	@SuppressWarnings("unchecked")
//	static final <K, V> HashEntry<K, V> entryAt(HashEntry<K, V>[] tab, int i) {
//		//(ZGQ) 获取tab(HashEntry数组)数组下标为i的值
//		return (tab == null) ? null : (HashEntry<K, V>) UNSAFE.getObjectVolatile(tab, ((long) i << TSHIFT) + TBASE);
//	}
//
//
//	// Unsafe mechanics
//	private static final sun.misc.Unsafe UNSAFE;
//	private static final long SBASE;
//	private static final int SSHIFT;
//	private static final long TBASE;
//	private static final int TSHIFT;
//	private static final long HASHSEED_OFFSET;
//	private static final long SEGSHIFT_OFFSET;
//	private static final long SEGMASK_OFFSET;
//	private static final long SEGMENTS_OFFSET;
//
//	static {
//		int ss, ts;
//		try {
//			UNSAFE = sun.misc.Unsafe.getUnsafe();
//			Class tc = HashEntry[].class;
//			Class sc = Segment[].class;
//			/**---ZGQ---
//			 * UNSAFE.arrayBaseOffset(数组) 是得到数组中第一个位置的起始位置
//			 */
//			TBASE = UNSAFE.arrayBaseOffset(tc);
//			SBASE = UNSAFE.arrayBaseOffset(sc);
//			/**---ZGQ---
//			 * UNSAFE.arrayIndexScale(数组) 是得到对象的对象头大小（一个对象由 对象头 + 对象成员 组成）
//			 * UNSAFE.arrayIndexScale(数组) 是得到数组的大小
//			 */
//			ts = UNSAFE.arrayIndexScale(tc);
//			ss = UNSAFE.arrayIndexScale(sc);
//			/**---ZGQ---
//			 * UNSAFE.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("属性"))
//			 * 获取到ConcurrentHashMap在主内存对应的属性值，而不是得到线程工作内存中的值
//			 */
//			HASHSEED_OFFSET = UNSAFE.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("hashSeed"));
//			SEGSHIFT_OFFSET = UNSAFE.objectFieldOffset(	ConcurrentHashMap.class.getDeclaredField("segmentShift"));
//			SEGMASK_OFFSET = UNSAFE.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("segmentMask"));
//			SEGMENTS_OFFSET = UNSAFE.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("segments"));
//		} catch (Exception e) {
//			throw new Error(e);
//		}
//		if ((ss & (ss - 1)) != 0 || (ts & (ts - 1)) != 0) {
//			throw new Error("data type scale not a power of two");
//		}
//
//		/**---ZGQ---
//		 * Integer.numberOfLeadingZeros(int i) 得到i高位总共有多少个0
//		 * 若 i = 1 转为二进制为 00000000 00000000 00000000 00000001 ==>  1前面有31个0
//		 */
//		SSHIFT = 31 - Integer.numberOfLeadingZeros(ss);
//		TSHIFT = 31 - Integer.numberOfLeadingZeros(ts);
//	}
//
//}
