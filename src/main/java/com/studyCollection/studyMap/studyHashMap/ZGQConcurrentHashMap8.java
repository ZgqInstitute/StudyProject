package com.studyCollection.studyMap.studyHashMap;

import javax.swing.text.Segment;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class ZGQConcurrentHashMap8<K,V> implements Serializable {
	private static final long serialVersionUID = 7249069246763182397L;

	private static final int MAXIMUM_CAPACITY = 1 << 30;
	private static final int DEFAULT_CAPACITY = 16;
	static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
	private static final float LOAD_FACTOR = 0.75f;
	static final int TREEIFY_THRESHOLD = 8;
	static final int UNTREEIFY_THRESHOLD = 6;
	static final int MIN_TREEIFY_CAPACITY = 64;
	private static final int MIN_TRANSFER_STRIDE = 16;
	private static int RESIZE_STAMP_BITS = 16;
	private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
	private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
	static final int MOVED     = -1; // hash for forwarding nodes
	static final int TREEBIN   = -2; // hash for roots of trees
	static final int RESERVED  = -3; // hash for transient reservations
	static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
	transient volatile Node<K,V>[] table;
	private transient volatile Node<K,V>[] nextTable;
	private transient volatile long baseCount;
	private transient volatile int sizeCtl;
	private transient volatile int transferIndex;
	private transient volatile int cellsBusy;
	private transient volatile CounterCell[] counterCells;

	// views
	private transient KeySetView<K,V> keySet;
	private transient ValuesView<K,V> values;
	private transient EntrySetView<K,V> entrySet;


	public ZGQConcurrentHashMap8() {
	}

	public ZGQConcurrentHashMap8(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException();
		int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
				MAXIMUM_CAPACITY :
				tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
		this.sizeCtl = cap;
	}

	public ZGQConcurrentHashMap8(Map<? extends K, ? extends V> m) {
		this.sizeCtl = DEFAULT_CAPACITY;
		putAll(m);
	}

	public ZGQConcurrentHashMap8(int initialCapacity, float loadFactor) {
		this(initialCapacity, loadFactor, 1);
	}

	public ZGQConcurrentHashMap8(int initialCapacity,
							 float loadFactor, int concurrencyLevel) {
		if (!(loadFactor > 0.0f) || initialCapacity < 0 || concurrencyLevel <= 0)
			throw new IllegalArgumentException();
		if (initialCapacity < concurrencyLevel)   // Use at least as many bins
			initialCapacity = concurrencyLevel;   // as estimated threads
		long size = (long)(1.0 + (long)initialCapacity / loadFactor);
		int cap = (size >= (long)MAXIMUM_CAPACITY) ?
				MAXIMUM_CAPACITY : tableSizeFor((int)size);
		this.sizeCtl = cap;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		tryPresize(m.size());
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			putVal(e.getKey(), e.getValue(), false);
	}

	static final class EntrySetView<K,V> extends CollectionView<K,V,Map.Entry<K,V>>
			implements Set<Map.Entry<K,V>>, java.io.Serializable {
		private static final long serialVersionUID = 2249069246763182397L;
		EntrySetView(ZGQConcurrentHashMap8<K,V> map) { super(map); }

		public boolean contains(Object o) {
			Object k, v, r; Map.Entry<?,?> e;
			return ((o instanceof Map.Entry) &&
					(k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
					(r = map.get(k)) != null &&
					(v = e.getValue()) != null &&
					(v == r || v.equals(r)));
		}

		public boolean remove(Object o) {
			Object k, v; Map.Entry<?,?> e;
			return ((o instanceof Map.Entry) &&
					(k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
					(v = e.getValue()) != null &&
					map.remove(k, v));
		}

		public Iterator<Map.Entry<K,V>> iterator() {
			ZGQConcurrentHashMap8<K,V> m = map;
			Node<K,V>[] t;
			int f = (t = m.table) == null ? 0 : t.length;
			return new EntryIterator<K,V>(t, f, 0, f, m);
		}

		public boolean add(Map.Entry<K,V> e) {
			return map.putVal(e.getKey(), e.getValue(), false) == null;
		}

		public boolean addAll(Collection<? extends Map.Entry<K,V>> c) {
			boolean added = false;
			for (Map.Entry<K,V> e : c) {
				if (add(e))
					added = true;
			}
			return added;
		}

		public final int hashCode() {
			int h = 0;
			Node<K,V>[] t;
			if ((t = map.table) != null) {
				Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
				for (Node<K,V> p; (p = it.advance()) != null; ) {
					h += p.hashCode();
				}
			}
			return h;
		}

		public final boolean equals(Object o) {
			Set<?> c;
			return ((o instanceof Set) &&
					((c = (Set<?>)o) == this ||
							(containsAll(c) && c.containsAll(this))));
		}

		public Spliterator<Map.Entry<K,V>> spliterator() {
			Node<K,V>[] t;
			ZGQConcurrentHashMap8<K,V> m = map;
			long n = m.sumCount();
			int f = (t = m.table) == null ? 0 : t.length;
			return new EntrySpliterator<K,V>(t, f, 0, f, n < 0L ? 0L : n, m);
		}

		public void forEach(Consumer<? super Map.Entry<K,V>> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V>[] t;
			if ((t = map.table) != null) {
				Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
				for (Node<K,V> p; (p = it.advance()) != null; )
					action.accept(new MapEntry<K,V>(p.key, p.val, map));
			}
		}

	}

	static final class EntrySpliterator<K,V> extends Traverser<K,V>
			implements Spliterator<Map.Entry<K,V>> {
		final ZGQConcurrentHashMap8<K,V> map; // To export MapEntry
		long est;               // size estimate
		EntrySpliterator(Node<K,V>[] tab, int size, int index, int limit,
						 long est, ZGQConcurrentHashMap8<K,V> map) {
			super(tab, size, index, limit);
			this.map = map;
			this.est = est;
		}

		public Spliterator<Map.Entry<K,V>> trySplit() {
			int i, f, h;
			return (h = ((i = baseIndex) + (f = baseLimit)) >>> 1) <= i ? null :
					new EntrySpliterator<K,V>(tab, baseSize, baseLimit = h,
							f, est >>>= 1, map);
		}

		public void forEachRemaining(Consumer<? super Map.Entry<K,V>> action) {
			if (action == null) throw new NullPointerException();
			for (Node<K,V> p; (p = advance()) != null; )
				action.accept(new MapEntry<K,V>(p.key, p.val, map));
		}

		public boolean tryAdvance(Consumer<? super Map.Entry<K,V>> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V> p;
			if ((p = advance()) == null)
				return false;
			action.accept(new MapEntry<K,V>(p.key, p.val, map));
			return true;
		}

		public long estimateSize() { return est; }

		public int characteristics() {
			return Spliterator.DISTINCT | Spliterator.CONCURRENT |
					Spliterator.NONNULL;
		}
	}

	public V put(K key, V value) {
		return putVal(key, value, false);
	}

	/** Implementation for put and putIfAbsent */
	final V putVal(K key, V value, boolean onlyIfAbsent) {
		if (key == null || value == null) throw new NullPointerException();
		int hash = spread(key.hashCode());
		int binCount = 0;
		for (Node<K,V>[] tab = table;;) {
			Node<K,V> f; int n, i, fh;
			if (tab == null || (n = tab.length) == 0)
				tab = initTable();
			else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
				if (casTabAt(tab, i, null,
						new Node<K,V>(hash, key, value, null)))
					break;                   // no lock when adding to empty bin
			}
			else if ((fh = f.hash) == MOVED)
				tab = helpTransfer(tab, f);
			else {
				V oldVal = null;
				synchronized (f) {
					if (tabAt(tab, i) == f) {
						if (fh >= 0) {
							binCount = 1;
							for (Node<K,V> e = f;; ++binCount) {
								K ek;
								if (e.hash == hash &&
										((ek = e.key) == key ||
												(ek != null && key.equals(ek)))) {
									oldVal = e.val;
									if (!onlyIfAbsent)
										e.val = value;
									break;
								}
								Node<K,V> pred = e;
								if ((e = e.next) == null) {
									pred.next = new Node<K,V>(hash, key,
											value, null);
									break;
								}
							}
						}
						else if (f instanceof TreeBin) {
							Node<K,V> p;
							binCount = 2;
							if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
									value)) != null) {
								oldVal = p.val;
								if (!onlyIfAbsent)
									p.val = value;
							}
						}
					}
				}
				if (binCount != 0) {
					if (binCount >= TREEIFY_THRESHOLD)
						treeifyBin(tab, i);
					if (oldVal != null)
						return oldVal;
					break;
				}
			}
		}
		addCount(1L, binCount);
		return null;
	}

	private final void addCount(long x, int check) {
		CounterCell[] as; long b, s;
		if ((as = counterCells) != null ||
				!U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
			CounterCell a; long v; int m;
			boolean uncontended = true;
			if (as == null || (m = as.length - 1) < 0 ||
					(a = as[ZGQThreadLocalRandom.getProbe() & m]) == null ||
					!(uncontended =
							U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
				fullAddCount(x, uncontended);
				return;
			}
			if (check <= 1)
				return;
			s = sumCount();
		}
		if (check >= 0) {
			Node<K,V>[] tab, nt; int n, sc;
			while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
					(n = tab.length) < MAXIMUM_CAPACITY) {
				int rs = resizeStamp(n);
				if (sc < 0) {
					if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
							sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
							transferIndex <= 0)
						break;
					if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
						transfer(tab, nt);
				}
				else if (U.compareAndSwapInt(this, SIZECTL, sc,
						(rs << RESIZE_STAMP_SHIFT) + 2))
					transfer(tab, null);
				s = sumCount();
			}
		}
	}

	private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
		int n = tab.length, stride;
		if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
			stride = MIN_TRANSFER_STRIDE; // subdivide range
		if (nextTab == null) {            // initiating
			try {
				@SuppressWarnings("unchecked")
				Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
				nextTab = nt;
			} catch (Throwable ex) {      // try to cope with OOME
				sizeCtl = Integer.MAX_VALUE;
				return;
			}
			nextTable = nextTab;
			transferIndex = n;
		}
		int nextn = nextTab.length;
		ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
		boolean advance = true;
		boolean finishing = false; // to ensure sweep before committing nextTab
		for (int i = 0, bound = 0;;) {
			Node<K,V> f; int fh;
			while (advance) {
				int nextIndex, nextBound;
				if (--i >= bound || finishing)
					advance = false;
				else if ((nextIndex = transferIndex) <= 0) {
					i = -1;
					advance = false;
				}
				else if (U.compareAndSwapInt
						(this, TRANSFERINDEX, nextIndex,
								nextBound = (nextIndex > stride ?
										nextIndex - stride : 0))) {
					bound = nextBound;
					i = nextIndex - 1;
					advance = false;
				}
			}
			if (i < 0 || i >= n || i + n >= nextn) {
				int sc;
				if (finishing) {
					nextTable = null;
					table = nextTab;
					sizeCtl = (n << 1) - (n >>> 1);
					return;
				}
				if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
					if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
						return;
					finishing = advance = true;
					i = n; // recheck before commit
				}
			}
			else if ((f = tabAt(tab, i)) == null)
				advance = casTabAt(tab, i, null, fwd);
			else if ((fh = f.hash) == MOVED)
				advance = true; // already processed
			else {
				synchronized (f) {
					if (tabAt(tab, i) == f) {
						Node<K,V> ln, hn;
						if (fh >= 0) {
							int runBit = fh & n;
							Node<K,V> lastRun = f;
							for (Node<K,V> p = f.next; p != null; p = p.next) {
								int b = p.hash & n;
								if (b != runBit) {
									runBit = b;
									lastRun = p;
								}
							}
							if (runBit == 0) {
								ln = lastRun;
								hn = null;
							}
							else {
								hn = lastRun;
								ln = null;
							}
							for (Node<K,V> p = f; p != lastRun; p = p.next) {
								int ph = p.hash; K pk = p.key; V pv = p.val;
								if ((ph & n) == 0)
									ln = new Node<K,V>(ph, pk, pv, ln);
								else
									hn = new Node<K,V>(ph, pk, pv, hn);
							}
							setTabAt(nextTab, i, ln);
							setTabAt(nextTab, i + n, hn);
							setTabAt(tab, i, fwd);
							advance = true;
						}
						else if (f instanceof TreeBin) {
							TreeBin<K,V> t = (TreeBin<K,V>)f;
							TreeNode<K,V> lo = null, loTail = null;
							TreeNode<K,V> hi = null, hiTail = null;
							int lc = 0, hc = 0;
							for (Node<K,V> e = t.first; e != null; e = e.next) {
								int h = e.hash;
								TreeNode<K,V> p = new TreeNode<K,V>
										(h, e.key, e.val, null, null);
								if ((h & n) == 0) {
									if ((p.prev = loTail) == null)
										lo = p;
									else
										loTail.next = p;
									loTail = p;
									++lc;
								}
								else {
									if ((p.prev = hiTail) == null)
										hi = p;
									else
										hiTail.next = p;
									hiTail = p;
									++hc;
								}
							}
							ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
									(hc != 0) ? new TreeBin<K,V>(lo) : t;
							hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
									(lc != 0) ? new TreeBin<K,V>(hi) : t;
							setTabAt(nextTab, i, ln);
							setTabAt(nextTab, i + n, hn);
							setTabAt(tab, i, fwd);
							advance = true;
						}
					}
				}
			}
		}
	}

	static <K,V> Node<K,V> untreeify(Node<K,V> b) {
		Node<K,V> hd = null, tl = null;
		for (Node<K,V> q = b; q != null; q = q.next) {
			Node<K,V> p = new Node<K,V>(q.hash, q.key, q.val, null);
			if (tl == null)
				hd = p;
			else
				tl.next = p;
			tl = p;
		}
		return hd;
	}

	static final class ForwardingNode<K,V> extends Node<K,V> {
		final Node<K,V>[] nextTable;
		ForwardingNode(Node<K,V>[] tab) {
			super(MOVED, null, null, null);
			this.nextTable = tab;
		}

		Node<K,V> find(int h, Object k) {
			// loop to avoid arbitrarily deep recursion on forwarding nodes
			outer: for (Node<K,V>[] tab = nextTable;;) {
				Node<K,V> e; int n;
				if (k == null || tab == null || (n = tab.length) == 0 ||
						(e = tabAt(tab, (n - 1) & h)) == null)
					return null;
				for (;;) {
					int eh; K ek;
					if ((eh = e.hash) == h &&
							((ek = e.key) == k || (ek != null && k.equals(ek))))
						return e;
					if (eh < 0) {
						if (e instanceof ForwardingNode) {
							tab = ((ForwardingNode<K,V>)e).nextTable;
							continue outer;
						}
						else
							return e.find(h, k);
					}
					if ((e = e.next) == null)
						return null;
				}
			}
		}
	}



	static final int resizeStamp(int n) {
		return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
	}

	private final void fullAddCount(long x, boolean wasUncontended) {
		int h;
		if ((h = ZGQThreadLocalRandom.getProbe()) == 0) {
			ZGQThreadLocalRandom.localInit();      // force initialization
			h = ZGQThreadLocalRandom.getProbe();
			wasUncontended = true;
		}
		boolean collide = false;                // True if last slot nonempty
		for (;;) {
			CounterCell[] as; CounterCell a; int n; long v;
			if ((as = counterCells) != null && (n = as.length) > 0) {
				if ((a = as[(n - 1) & h]) == null) {
					if (cellsBusy == 0) {            // Try to attach new Cell
						CounterCell r = new CounterCell(x); // Optimistic create
						if (cellsBusy == 0 &&
								U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
							boolean created = false;
							try {               // Recheck under lock
								CounterCell[] rs; int m, j;
								if ((rs = counterCells) != null &&
										(m = rs.length) > 0 &&
										rs[j = (m - 1) & h] == null) {
									rs[j] = r;
									created = true;
								}
							} finally {
								cellsBusy = 0;
							}
							if (created)
								break;
							continue;           // Slot is now non-empty
						}
					}
					collide = false;
				}
				else if (!wasUncontended)       // CAS already known to fail
					wasUncontended = true;      // Continue after rehash
				else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))
					break;
				else if (counterCells != as || n >= NCPU)
					collide = false;            // At max size or stale
				else if (!collide)
					collide = true;
				else if (cellsBusy == 0 &&
						U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
					try {
						if (counterCells == as) {// Expand table unless stale
							CounterCell[] rs = new CounterCell[n << 1];
							for (int i = 0; i < n; ++i)
								rs[i] = as[i];
							counterCells = rs;
						}
					} finally {
						cellsBusy = 0;
					}
					collide = false;
					continue;                   // Retry with expanded table
				}
				h = ZGQThreadLocalRandom.advanceProbe(h);
			}
			else if (cellsBusy == 0 && counterCells == as &&
					U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
				boolean init = false;
				try {                           // Initialize table
					if (counterCells == as) {
						CounterCell[] rs = new CounterCell[2];
						rs[h & 1] = new CounterCell(x);
						counterCells = rs;
						init = true;
					}
				} finally {
					cellsBusy = 0;
				}
				if (init)
					break;
			}
			else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))
				break;                          // Fall back on using base
		}
	}

	private final void tryPresize(int size) {
		int c = (size >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY :
				tableSizeFor(size + (size >>> 1) + 1);
		int sc;
		while ((sc = sizeCtl) >= 0) {
			Node<K,V>[] tab = table; int n;
			if (tab == null || (n = tab.length) == 0) {
				n = (sc > c) ? sc : c;
				if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
					try {
						if (table == tab) {
							@SuppressWarnings("unchecked")
							Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
							table = nt;
							sc = n - (n >>> 2);
						}
					} finally {
						sizeCtl = sc;
					}
				}
			}
			else if (c <= sc || n >= MAXIMUM_CAPACITY)
				break;
			else if (tab == table) {
				int rs = resizeStamp(n);
				if (sc < 0) {
					Node<K,V>[] nt;
					if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
							sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
							transferIndex <= 0)
						break;
					if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
						transfer(tab, nt);
				}
				else if (U.compareAndSwapInt(this, SIZECTL, sc,
						(rs << RESIZE_STAMP_SHIFT) + 2))
					transfer(tab, null);
			}
		}
	}


	private final void treeifyBin(Node<K,V>[] tab, int index) {
		Node<K,V> b; int n, sc;
		if (tab != null) {
			if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
				tryPresize(n << 1);
			else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
				synchronized (b) {
					if (tabAt(tab, index) == b) {
						TreeNode<K,V> hd = null, tl = null;
						for (Node<K,V> e = b; e != null; e = e.next) {
							TreeNode<K,V> p =
									new TreeNode<K,V>(e.hash, e.key, e.val,
											null, null);
							if ((p.prev = tl) == null)
								hd = p;
							else
								tl.next = p;
							tl = p;
						}
						setTabAt(tab, index, new TreeBin<K,V>(hd));
					}
				}
			}
		}
	}

	static final class TreeBin<K,V> extends Node<K,V> {
		TreeNode<K,V> root;
		volatile TreeNode<K,V> first;
		volatile Thread waiter;
		volatile int lockState;
		// values for lockState
		static final int WRITER = 1; // set while holding write lock
		static final int WAITER = 2; // set when waiting for write lock
		static final int READER = 4; // increment value for setting read lock

		/**
		 * Tie-breaking utility for ordering insertions when equal
		 * hashCodes and non-comparable. We don't require a total
		 * order, just a consistent insertion rule to maintain
		 * equivalence across rebalancings. Tie-breaking further than
		 * necessary simplifies testing a bit.
		 */
		static int tieBreakOrder(Object a, Object b) {
			int d;
			if (a == null || b == null ||
					(d = a.getClass().getName().
							compareTo(b.getClass().getName())) == 0)
				d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
						-1 : 1);
			return d;
		}

		/**
		 * Creates bin with initial set of nodes headed by b.
		 */
		TreeBin(TreeNode<K,V> b) {
			super(TREEBIN, null, null, null);
			this.first = b;
			TreeNode<K,V> r = null;
			for (TreeNode<K,V> x = b, next; x != null; x = next) {
				next = (TreeNode<K,V>)x.next;
				x.left = x.right = null;
				if (r == null) {
					x.parent = null;
					x.red = false;
					r = x;
				}
				else {
					K k = x.key;
					int h = x.hash;
					Class<?> kc = null;
					for (TreeNode<K,V> p = r;;) {
						int dir, ph;
						K pk = p.key;
						if ((ph = p.hash) > h)
							dir = -1;
						else if (ph < h)
							dir = 1;
						else if ((kc == null &&
								(kc = comparableClassFor(k)) == null) ||
								(dir = compareComparables(kc, k, pk)) == 0)
							dir = tieBreakOrder(k, pk);
						TreeNode<K,V> xp = p;
						if ((p = (dir <= 0) ? p.left : p.right) == null) {
							x.parent = xp;
							if (dir <= 0)
								xp.left = x;
							else
								xp.right = x;
							r = balanceInsertion(r, x);
							break;
						}
					}
				}
			}
			this.root = r;
			assert checkInvariants(root);
		}

		/**
		 * Acquires write lock for tree restructuring.
		 */
		private final void lockRoot() {
			if (!U.compareAndSwapInt(this, LOCKSTATE, 0, WRITER))
				contendedLock(); // offload to separate method
		}

		/**
		 * Releases write lock for tree restructuring.
		 */
		private final void unlockRoot() {
			lockState = 0;
		}

		/**
		 * Possibly blocks awaiting root lock.
		 */
		private final void contendedLock() {
			boolean waiting = false;
			for (int s;;) {
				if (((s = lockState) & ~WAITER) == 0) {
					if (U.compareAndSwapInt(this, LOCKSTATE, s, WRITER)) {
						if (waiting)
							waiter = null;
						return;
					}
				}
				else if ((s & WAITER) == 0) {
					if (U.compareAndSwapInt(this, LOCKSTATE, s, s | WAITER)) {
						waiting = true;
						waiter = Thread.currentThread();
					}
				}
				else if (waiting)
					LockSupport.park(this);
			}
		}

		/**
		 * Returns matching node or null if none. Tries to search
		 * using tree comparisons from root, but continues linear
		 * search when lock not available.
		 */
		final Node<K,V> find(int h, Object k) {
			if (k != null) {
				for (Node<K,V> e = first; e != null; ) {
					int s; K ek;
					if (((s = lockState) & (WAITER|WRITER)) != 0) {
						if (e.hash == h &&
								((ek = e.key) == k || (ek != null && k.equals(ek))))
							return e;
						e = e.next;
					}
					else if (U.compareAndSwapInt(this, LOCKSTATE, s,
							s + READER)) {
						TreeNode<K,V> r, p;
						try {
							p = ((r = root) == null ? null :
									r.findTreeNode(h, k, null));
						} finally {
							Thread w;
							if (U.getAndAddInt(this, LOCKSTATE, -READER) ==
									(READER|WAITER) && (w = waiter) != null)
								LockSupport.unpark(w);
						}
						return p;
					}
				}
			}
			return null;
		}

		/**
		 * Finds or adds a node.
		 * @return null if added
		 */
		final TreeNode<K,V> putTreeVal(int h, K k, V v) {
			Class<?> kc = null;
			boolean searched = false;
			for (TreeNode<K,V> p = root;;) {
				int dir, ph; K pk;
				if (p == null) {
					first = root = new TreeNode<K,V>(h, k, v, null, null);
					break;
				}
				else if ((ph = p.hash) > h)
					dir = -1;
				else if (ph < h)
					dir = 1;
				else if ((pk = p.key) == k || (pk != null && k.equals(pk)))
					return p;
				else if ((kc == null &&
						(kc = comparableClassFor(k)) == null) ||
						(dir = compareComparables(kc, k, pk)) == 0) {
					if (!searched) {
						TreeNode<K,V> q, ch;
						searched = true;
						if (((ch = p.left) != null &&
								(q = ch.findTreeNode(h, k, kc)) != null) ||
								((ch = p.right) != null &&
										(q = ch.findTreeNode(h, k, kc)) != null))
							return q;
					}
					dir = tieBreakOrder(k, pk);
				}

				TreeNode<K,V> xp = p;
				if ((p = (dir <= 0) ? p.left : p.right) == null) {
					TreeNode<K,V> x, f = first;
					first = x = new TreeNode<K,V>(h, k, v, f, xp);
					if (f != null)
						f.prev = x;
					if (dir <= 0)
						xp.left = x;
					else
						xp.right = x;
					if (!xp.red)
						x.red = true;
					else {
						lockRoot();
						try {
							root = balanceInsertion(root, x);
						} finally {
							unlockRoot();
						}
					}
					break;
				}
			}
			assert checkInvariants(root);
			return null;
		}

		/**
		 * Removes the given node, that must be present before this
		 * call.  This is messier than typical red-black deletion code
		 * because we cannot swap the contents of an interior node
		 * with a leaf successor that is pinned by "next" pointers
		 * that are accessible independently of lock. So instead we
		 * swap the tree linkages.
		 *
		 * @return true if now too small, so should be untreeified
		 */
		final boolean removeTreeNode(TreeNode<K,V> p) {
			TreeNode<K,V> next = (TreeNode<K,V>)p.next;
			TreeNode<K,V> pred = p.prev;  // unlink traversal pointers
			TreeNode<K,V> r, rl;
			if (pred == null)
				first = next;
			else
				pred.next = next;
			if (next != null)
				next.prev = pred;
			if (first == null) {
				root = null;
				return true;
			}
			if ((r = root) == null || r.right == null || // too small
					(rl = r.left) == null || rl.left == null)
				return true;
			lockRoot();
			try {
				TreeNode<K,V> replacement;
				TreeNode<K,V> pl = p.left;
				TreeNode<K,V> pr = p.right;
				if (pl != null && pr != null) {
					TreeNode<K,V> s = pr, sl;
					while ((sl = s.left) != null) // find successor
						s = sl;
					boolean c = s.red; s.red = p.red; p.red = c; // swap colors
					TreeNode<K,V> sr = s.right;
					TreeNode<K,V> pp = p.parent;
					if (s == pr) { // p was s's direct parent
						p.parent = s;
						s.right = p;
					}
					else {
						TreeNode<K,V> sp = s.parent;
						if ((p.parent = sp) != null) {
							if (s == sp.left)
								sp.left = p;
							else
								sp.right = p;
						}
						if ((s.right = pr) != null)
							pr.parent = s;
					}
					p.left = null;
					if ((p.right = sr) != null)
						sr.parent = p;
					if ((s.left = pl) != null)
						pl.parent = s;
					if ((s.parent = pp) == null)
						r = s;
					else if (p == pp.left)
						pp.left = s;
					else
						pp.right = s;
					if (sr != null)
						replacement = sr;
					else
						replacement = p;
				}
				else if (pl != null)
					replacement = pl;
				else if (pr != null)
					replacement = pr;
				else
					replacement = p;
				if (replacement != p) {
					TreeNode<K,V> pp = replacement.parent = p.parent;
					if (pp == null)
						r = replacement;
					else if (p == pp.left)
						pp.left = replacement;
					else
						pp.right = replacement;
					p.left = p.right = p.parent = null;
				}

				root = (p.red) ? r : balanceDeletion(r, replacement);

				if (p == replacement) {  // detach pointers
					TreeNode<K,V> pp;
					if ((pp = p.parent) != null) {
						if (p == pp.left)
							pp.left = null;
						else if (p == pp.right)
							pp.right = null;
						p.parent = null;
					}
				}
			} finally {
				unlockRoot();
			}
			assert checkInvariants(root);
			return false;
		}

		/* ------------------------------------------------------------ */
		// Red-black tree methods, all adapted from CLR

		static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
											  TreeNode<K,V> p) {
			TreeNode<K,V> r, pp, rl;
			if (p != null && (r = p.right) != null) {
				if ((rl = p.right = r.left) != null)
					rl.parent = p;
				if ((pp = r.parent = p.parent) == null)
					(root = r).red = false;
				else if (pp.left == p)
					pp.left = r;
				else
					pp.right = r;
				r.left = p;
				p.parent = r;
			}
			return root;
		}

		static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
											   TreeNode<K,V> p) {
			TreeNode<K,V> l, pp, lr;
			if (p != null && (l = p.left) != null) {
				if ((lr = p.left = l.right) != null)
					lr.parent = p;
				if ((pp = l.parent = p.parent) == null)
					(root = l).red = false;
				else if (pp.right == p)
					pp.right = l;
				else
					pp.left = l;
				l.right = p;
				p.parent = l;
			}
			return root;
		}

		static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
													TreeNode<K,V> x) {
			x.red = true;
			for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
				if ((xp = x.parent) == null) {
					x.red = false;
					return x;
				}
				else if (!xp.red || (xpp = xp.parent) == null)
					return root;
				if (xp == (xppl = xpp.left)) {
					if ((xppr = xpp.right) != null && xppr.red) {
						xppr.red = false;
						xp.red = false;
						xpp.red = true;
						x = xpp;
					}
					else {
						if (x == xp.right) {
							root = rotateLeft(root, x = xp);
							xpp = (xp = x.parent) == null ? null : xp.parent;
						}
						if (xp != null) {
							xp.red = false;
							if (xpp != null) {
								xpp.red = true;
								root = rotateRight(root, xpp);
							}
						}
					}
				}
				else {
					if (xppl != null && xppl.red) {
						xppl.red = false;
						xp.red = false;
						xpp.red = true;
						x = xpp;
					}
					else {
						if (x == xp.left) {
							root = rotateRight(root, x = xp);
							xpp = (xp = x.parent) == null ? null : xp.parent;
						}
						if (xp != null) {
							xp.red = false;
							if (xpp != null) {
								xpp.red = true;
								root = rotateLeft(root, xpp);
							}
						}
					}
				}
			}
		}

		static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
												   TreeNode<K,V> x) {
			for (TreeNode<K,V> xp, xpl, xpr;;)  {
				if (x == null || x == root)
					return root;
				else if ((xp = x.parent) == null) {
					x.red = false;
					return x;
				}
				else if (x.red) {
					x.red = false;
					return root;
				}
				else if ((xpl = xp.left) == x) {
					if ((xpr = xp.right) != null && xpr.red) {
						xpr.red = false;
						xp.red = true;
						root = rotateLeft(root, xp);
						xpr = (xp = x.parent) == null ? null : xp.right;
					}
					if (xpr == null)
						x = xp;
					else {
						TreeNode<K,V> sl = xpr.left, sr = xpr.right;
						if ((sr == null || !sr.red) &&
								(sl == null || !sl.red)) {
							xpr.red = true;
							x = xp;
						}
						else {
							if (sr == null || !sr.red) {
								if (sl != null)
									sl.red = false;
								xpr.red = true;
								root = rotateRight(root, xpr);
								xpr = (xp = x.parent) == null ?
										null : xp.right;
							}
							if (xpr != null) {
								xpr.red = (xp == null) ? false : xp.red;
								if ((sr = xpr.right) != null)
									sr.red = false;
							}
							if (xp != null) {
								xp.red = false;
								root = rotateLeft(root, xp);
							}
							x = root;
						}
					}
				}
				else { // symmetric
					if (xpl != null && xpl.red) {
						xpl.red = false;
						xp.red = true;
						root = rotateRight(root, xp);
						xpl = (xp = x.parent) == null ? null : xp.left;
					}
					if (xpl == null)
						x = xp;
					else {
						TreeNode<K,V> sl = xpl.left, sr = xpl.right;
						if ((sl == null || !sl.red) &&
								(sr == null || !sr.red)) {
							xpl.red = true;
							x = xp;
						}
						else {
							if (sl == null || !sl.red) {
								if (sr != null)
									sr.red = false;
								xpl.red = true;
								root = rotateLeft(root, xpl);
								xpl = (xp = x.parent) == null ?
										null : xp.left;
							}
							if (xpl != null) {
								xpl.red = (xp == null) ? false : xp.red;
								if ((sl = xpl.left) != null)
									sl.red = false;
							}
							if (xp != null) {
								xp.red = false;
								root = rotateRight(root, xp);
							}
							x = root;
						}
					}
				}
			}
		}

		/**
		 * Recursive invariant check
		 */
		static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
			TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
					tb = t.prev, tn = (TreeNode<K,V>)t.next;
			if (tb != null && tb.next != t)
				return false;
			if (tn != null && tn.prev != t)
				return false;
			if (tp != null && t != tp.left && t != tp.right)
				return false;
			if (tl != null && (tl.parent != t || tl.hash > t.hash))
				return false;
			if (tr != null && (tr.parent != t || tr.hash < t.hash))
				return false;
			if (t.red && tl != null && tl.red && tr != null && tr.red)
				return false;
			if (tl != null && !checkInvariants(tl))
				return false;
			if (tr != null && !checkInvariants(tr))
				return false;
			return true;
		}

		private static final sun.misc.Unsafe U;
		private static final long LOCKSTATE;
		static {
			try {
				U = sun.misc.Unsafe.getUnsafe();
				Class<?> k = TreeBin.class;
				LOCKSTATE = U.objectFieldOffset
						(k.getDeclaredField("lockState"));
			} catch (Exception e) {
				throw new Error(e);
			}
		}
	}


	final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
		Node<K,V>[] nextTab; int sc;
		if (tab != null && (f instanceof ForwardingNode) &&
				(nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
			int rs = resizeStamp(tab.length);
			while (nextTab == nextTable && table == tab &&
					(sc = sizeCtl) < 0) {
				if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
						sc == rs + MAX_RESIZERS || transferIndex <= 0)
					break;
				if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
					transfer(tab, nextTab);
					break;
				}
			}
			return nextTab;
		}
		return table;
	}

	private final Node<K,V>[] initTable() {
		Node<K,V>[] tab; int sc;
		while ((tab = table) == null || tab.length == 0) {
			if ((sc = sizeCtl) < 0)
				Thread.yield(); // lost initialization race; just spin
			else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
				try {
					if ((tab = table) == null || tab.length == 0) {
						int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
						@SuppressWarnings("unchecked")
						Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
						table = tab = nt;
						sc = n - (n >>> 2);
					}
				} finally {
					sizeCtl = sc;
				}
				break;
			}
		}
		return tab;
	}


	static final class EntryIterator<K,V> extends BaseIterator<K,V>
			implements Iterator<Map.Entry<K,V>> {
		EntryIterator(Node<K,V>[] tab, int index, int size, int limit,
					  ZGQConcurrentHashMap8<K,V> map) {
			super(tab, index, size, limit, map);
		}

		public final Map.Entry<K,V> next() {
			Node<K,V> p;
			if ((p = next) == null)
				throw new NoSuchElementException();
			K k = p.key;
			V v = p.val;
			lastReturned = p;
			advance();
			return new MapEntry<K,V>(k, v, map);
		}
	}

	static final class MapEntry<K,V> implements Map.Entry<K,V> {
		final K key; // non-null
		V val;       // non-null
		final ZGQConcurrentHashMap8<K,V> map;
		MapEntry(K key, V val, ZGQConcurrentHashMap8<K,V> map) {
			this.key = key;
			this.val = val;
			this.map = map;
		}
		public K getKey()        { return key; }
		public V getValue()      { return val; }
		public int hashCode()    { return key.hashCode() ^ val.hashCode(); }
		public String toString() { return key + "=" + val; }

		public boolean equals(Object o) {
			Object k, v; Map.Entry<?,?> e;
			return ((o instanceof Map.Entry) &&
					(k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
					(v = e.getValue()) != null &&
					(k == key || k.equals(key)) &&
					(v == val || v.equals(val)));
		}

		public V setValue(V value) {
			if (value == null) throw new NullPointerException();
			V v = val;
			val = value;
			map.put(key, value);
			return v;
		}
	}

	static class BaseIterator<K,V> extends Traverser<K,V> {
		final ZGQConcurrentHashMap8<K,V> map;
		Node<K,V> lastReturned;
		BaseIterator(Node<K,V>[] tab, int size, int index, int limit,
					 ZGQConcurrentHashMap8<K,V> map) {
			super(tab, size, index, limit);
			this.map = map;
			advance();
		}

		public final boolean hasNext() { return next != null; }
		public final boolean hasMoreElements() { return next != null; }

		public final void remove() {
			Node<K,V> p;
			if ((p = lastReturned) == null)
				throw new IllegalStateException();
			lastReturned = null;
			map.replaceNode(p.key, null, null);
		}
	}
	static final class TableStack<K,V> {
		int length;
		int index;
		Node<K,V>[] tab;
		TableStack<K,V> next;
	}


	static class Traverser<K,V> {
		Node<K,V>[] tab;        // current table; updated if resized
		Node<K,V> next;         // the next entry to use
		TableStack<K,V> stack, spare; // to save/restore on ForwardingNodes
		int index;              // index of bin to use next
		int baseIndex;          // current index of initial table
		int baseLimit;          // index bound for initial table
		final int baseSize;     // initial table size

		Traverser(Node<K,V>[] tab, int size, int index, int limit) {
			this.tab = tab;
			this.baseSize = size;
			this.baseIndex = this.index = index;
			this.baseLimit = limit;
			this.next = null;
		}

		final Node<K,V> advance() {
			Node<K,V> e;
			if ((e = next) != null)
				e = e.next;
			for (;;) {
				Node<K,V>[] t; int i, n;  // must use locals in checks
				if (e != null)
					return next = e;
				if (baseIndex >= baseLimit || (t = tab) == null ||
						(n = t.length) <= (i = index) || i < 0)
					return next = null;
				if ((e = tabAt(t, i)) != null && e.hash < 0) {
					if (e instanceof ForwardingNode) {
						tab = ((ForwardingNode<K,V>)e).nextTable;
						e = null;
						pushState(t, i, n);
						continue;
					}
					else if (e instanceof TreeBin)
						e = ((TreeBin<K,V>)e).first;
					else
						e = null;
				}
				if (stack != null)
					recoverState(n);
				else if ((index = i + baseSize) >= n)
					index = ++baseIndex; // visit upper slots if present
			}
		}

		private void pushState(Node<K,V>[] t, int i, int n) {
			TableStack<K,V> s = spare;  // reuse if possible
			if (s != null)
				spare = s.next;
			else
				s = new TableStack<K,V>();
			s.tab = t;
			s.length = n;
			s.index = i;
			s.next = stack;
			stack = s;
		}

		private void recoverState(int n) {
			TableStack<K,V> s; int len;
			while ((s = stack) != null && (index += (len = s.length)) >= n) {
				n = len;
				index = s.index;
				tab = s.tab;
				s.tab = null;
				TableStack<K,V> next = s.next;
				s.next = spare; // save for reuse
				stack = next;
				spare = s;
			}
			if (s == null && (index += baseSize) >= n)
				index = ++baseIndex;
		}
	}

	public V get(Object key) {
		Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
		int h = spread(key.hashCode());
		if ((tab = table) != null && (n = tab.length) > 0 &&
				(e = tabAt(tab, (n - 1) & h)) != null) {
			if ((eh = e.hash) == h) {
				if ((ek = e.key) == key || (ek != null && key.equals(ek)))
					return e.val;
			}
			else if (eh < 0)
				return (p = e.find(h, key)) != null ? p.val : null;
			while ((e = e.next) != null) {
				if (e.hash == h &&
						((ek = e.key) == key || (ek != null && key.equals(ek))))
					return e.val;
			}
		}
		return null;
	}

	public boolean remove(Object key, Object value) {
		if (key == null)
			throw new NullPointerException();
		return value != null && replaceNode(key, null, value) != null;
	}

	static final class TreeNode<K,V> extends Node<K,V> {
		TreeNode<K,V> parent;  // red-black tree links
		TreeNode<K,V> left;
		TreeNode<K,V> right;
		TreeNode<K,V> prev;    // needed to unlink next upon deletion
		boolean red;

		TreeNode(int hash, K key, V val, Node<K,V> next,
				 TreeNode<K,V> parent) {
			super(hash, key, val, next);
			this.parent = parent;
		}

		Node<K,V> find(int h, Object k) {
			return findTreeNode(h, k, null);
		}

		/**
		 * Returns the TreeNode (or null if not found) for the given key
		 * starting at given root.
		 */
		final TreeNode<K,V> findTreeNode(int h, Object k, Class<?> kc) {
			if (k != null) {
				TreeNode<K,V> p = this;
				do  {
					int ph, dir; K pk; TreeNode<K,V> q;
					TreeNode<K,V> pl = p.left, pr = p.right;
					if ((ph = p.hash) > h)
						p = pl;
					else if (ph < h)
						p = pr;
					else if ((pk = p.key) == k || (pk != null && k.equals(pk)))
						return p;
					else if (pl == null)
						p = pr;
					else if (pr == null)
						p = pl;
					else if ((kc != null ||
							(kc = comparableClassFor(k)) != null) &&
							(dir = compareComparables(kc, k, pk)) != 0)
						p = (dir < 0) ? pl : pr;
					else if ((q = pr.findTreeNode(h, k, kc)) != null)
						return q;
					else
						p = pl;
				} while (p != null);
			}
			return null;
		}
	}

	public V remove(Object key) {
		return replaceNode(key, null, null);
	}
	final V replaceNode(Object key, V value, Object cv) {
		int hash = spread(key.hashCode());
		for (Node<K,V>[] tab = table;;) {
			Node<K,V> f; int n, i, fh;
			if (tab == null || (n = tab.length) == 0 ||
					(f = tabAt(tab, i = (n - 1) & hash)) == null)
				break;
			else if ((fh = f.hash) == MOVED)
				tab = helpTransfer(tab, f);
			else {
				V oldVal = null;
				boolean validated = false;
				synchronized (f) {
					if (tabAt(tab, i) == f) {
						if (fh >= 0) {
							validated = true;
							for (Node<K,V> e = f, pred = null;;) {
								K ek;
								if (e.hash == hash &&
										((ek = e.key) == key ||
												(ek != null && key.equals(ek)))) {
									V ev = e.val;
									if (cv == null || cv == ev ||
											(ev != null && cv.equals(ev))) {
										oldVal = ev;
										if (value != null)
											e.val = value;
										else if (pred != null)
											pred.next = e.next;
										else
											setTabAt(tab, i, e.next);
									}
									break;
								}
								pred = e;
								if ((e = e.next) == null)
									break;
							}
						}
						else if (f instanceof TreeBin) {
							validated = true;
							TreeBin<K,V> t = (TreeBin<K,V>)f;
							TreeNode<K,V> r, p;
							if ((r = t.root) != null &&
									(p = r.findTreeNode(hash, key, null)) != null) {
								V pv = p.val;
								if (cv == null || cv == pv ||
										(pv != null && cv.equals(pv))) {
									oldVal = pv;
									if (value != null)
										p.val = value;
									else if (t.removeTreeNode(p))
										setTabAt(tab, i, untreeify(t.first));
								}
							}
						}
					}
				}
				if (validated) {
					if (oldVal != null) {
						if (value == null)
							addCount(-1L, -1);
						return oldVal;
					}
					break;
				}
			}
		}
		return null;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	static final class KeyIterator<K,V> extends BaseIterator<K,V>
			implements Iterator<K>, Enumeration<K> {
		KeyIterator(Node<K,V>[] tab, int index, int size, int limit,
					ZGQConcurrentHashMap8<K,V> map) {
			super(tab, index, size, limit, map);
		}

		public final K next() {
			Node<K,V> p;
			if ((p = next) == null)
				throw new NoSuchElementException();
			K k = p.key;
			lastReturned = p;
			advance();
			return k;
		}

		public final K nextElement() { return next(); }
	}

	public static class KeySetView<K,V> extends CollectionView<K,V,K>
			implements Set<K>, java.io.Serializable {
		private static final long serialVersionUID = 7249069246763182397L;
		private final V value;
		KeySetView(ZGQConcurrentHashMap8<K,V> map, V value) {  // non-public
			super(map);
			this.value = value;
		}

		public V getMappedValue() { return value; }
		public boolean contains(Object o) { return map.containsKey(o); }
		public boolean remove(Object o) { return map.remove(o) != null; }
		public Iterator<K> iterator() {
			Node<K,V>[] t;
			ZGQConcurrentHashMap8<K,V> m = map;
			int f = (t = m.table) == null ? 0 : t.length;
			return new KeyIterator<K,V>(t, f, 0, f, m);
		}

		public boolean add(K e) {
			V v;
			if ((v = value) == null)
				throw new UnsupportedOperationException();
			return map.putVal(e, v, true) == null;
		}

		public boolean addAll(Collection<? extends K> c) {
			boolean added = false;
			V v;
			if ((v = value) == null)
				throw new UnsupportedOperationException();
			for (K e : c) {
				if (map.putVal(e, v, true) == null)
					added = true;
			}
			return added;
		}

		public int hashCode() {
			int h = 0;
			for (K e : this)
				h += e.hashCode();
			return h;
		}

		public boolean equals(Object o) {
			Set<?> c;
			return ((o instanceof Set) &&
					((c = (Set<?>)o) == this ||
							(containsAll(c) && c.containsAll(this))));
		}

		public Spliterator<K> spliterator() {
			Node<K,V>[] t;
			ZGQConcurrentHashMap8<K,V> m = map;
			long n = m.sumCount();
			int f = (t = m.table) == null ? 0 : t.length;
			return new KeySpliterator<K,V>(t, f, 0, f, n < 0L ? 0L : n);
		}

		public void forEach(Consumer<? super K> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V>[] t;
			if ((t = map.table) != null) {
				Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
				for (Node<K,V> p; (p = it.advance()) != null; )
					action.accept(p.key);
			}
		}
	}

	static final class KeySpliterator<K,V> extends Traverser<K,V>
			implements Spliterator<K> {
		long est;               // size estimate
		KeySpliterator(Node<K,V>[] tab, int size, int index, int limit,
					   long est) {
			super(tab, size, index, limit);
			this.est = est;
		}

		public Spliterator<K> trySplit() {
			int i, f, h;
			return (h = ((i = baseIndex) + (f = baseLimit)) >>> 1) <= i ? null :
					new KeySpliterator<K,V>(tab, baseSize, baseLimit = h,
							f, est >>>= 1);
		}

		public void forEachRemaining(Consumer<? super K> action) {
			if (action == null) throw new NullPointerException();
			for (Node<K,V> p; (p = advance()) != null;)
				action.accept(p.key);
		}

		public boolean tryAdvance(Consumer<? super K> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V> p;
			if ((p = advance()) == null)
				return false;
			action.accept(p.key);
			return true;
		}

		public long estimateSize() { return est; }

		public int characteristics() {
			return Spliterator.DISTINCT | Spliterator.CONCURRENT |
					Spliterator.NONNULL;
		}
	}

	static final class ValueSpliterator<K,V> extends Traverser<K,V>
			implements Spliterator<V> {
		long est;               // size estimate
		ValueSpliterator(Node<K,V>[] tab, int size, int index, int limit,
						 long est) {
			super(tab, size, index, limit);
			this.est = est;
		}

		public Spliterator<V> trySplit() {
			int i, f, h;
			return (h = ((i = baseIndex) + (f = baseLimit)) >>> 1) <= i ? null :
					new ValueSpliterator<K,V>(tab, baseSize, baseLimit = h,
							f, est >>>= 1);
		}

		public void forEachRemaining(Consumer<? super V> action) {
			if (action == null) throw new NullPointerException();
			for (Node<K,V> p; (p = advance()) != null;)
				action.accept(p.val);
		}

		public boolean tryAdvance(Consumer<? super V> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V> p;
			if ((p = advance()) == null)
				return false;
			action.accept(p.val);
			return true;
		}

		public long estimateSize() { return est; }

		public int characteristics() {
			return Spliterator.CONCURRENT | Spliterator.NONNULL;
		}
	}

	public boolean containsValue(Object value) {
		if (value == null)
			throw new NullPointerException();
		Node<K,V>[] t;
		if ((t = table) != null) {
			Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
			for (Node<K,V> p; (p = it.advance()) != null; ) {
				V v;
				if ((v = p.val) == value || (v != null && value.equals(v)))
					return true;
			}
		}
		return false;
	}

	static final class ValuesView<K,V> extends CollectionView<K,V,V>
			implements Collection<V>, java.io.Serializable {
		private static final long serialVersionUID = 2249069246763182397L;
		ValuesView(ZGQConcurrentHashMap8<K,V> map) { super(map); }
		public final boolean contains(Object o) {
			return map.containsValue(o);
		}

		public final boolean remove(Object o) {
			if (o != null) {
				for (Iterator<V> it = iterator(); it.hasNext();) {
					if (o.equals(it.next())) {
						it.remove();
						return true;
					}
				}
			}
			return false;
		}

		public final Iterator<V> iterator() {
			ZGQConcurrentHashMap8<K,V> m = map;
			Node<K,V>[] t;
			int f = (t = m.table) == null ? 0 : t.length;
			return new ValueIterator<K,V>(t, f, 0, f, m);
		}

		public final boolean add(V e) {
			throw new UnsupportedOperationException();
		}
		public final boolean addAll(Collection<? extends V> c) {
			throw new UnsupportedOperationException();
		}

		public Spliterator<V> spliterator() {
			Node<K,V>[] t;
			ZGQConcurrentHashMap8<K,V> m = map;
			long n = m.sumCount();
			int f = (t = m.table) == null ? 0 : t.length;
			return new ValueSpliterator<K,V>(t, f, 0, f, n < 0L ? 0L : n);
		}

		public void forEach(Consumer<? super V> action) {
			if (action == null) throw new NullPointerException();
			Node<K,V>[] t;
			if ((t = map.table) != null) {
				Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
				for (Node<K,V> p; (p = it.advance()) != null; )
					action.accept(p.val);
			}
		}
	}

	static final class ValueIterator<K,V> extends BaseIterator<K,V>
			implements Iterator<V>, Enumeration<V> {
		ValueIterator(Node<K,V>[] tab, int index, int size, int limit,
					  ZGQConcurrentHashMap8<K,V> map) {
			super(tab, index, size, limit, map);
		}

		public final V next() {
			Node<K,V> p;
			if ((p = next) == null)
				throw new NoSuchElementException();
			V v = p.val;
			lastReturned = p;
			advance();
			return v;
		}

		public final V nextElement() { return next(); }
	}


	abstract static class CollectionView<K,V,E>
			implements Collection<E>, java.io.Serializable {
		private static final long serialVersionUID = 7249069246763182397L;
		final ZGQConcurrentHashMap8<K,V> map;
		CollectionView(ZGQConcurrentHashMap8<K,V> map)  { this.map = map; }

		public ZGQConcurrentHashMap8<K,V> getMap() { return map; }
		public final void clear()      { map.clear(); }
		public final int size()        { return map.size(); }
		public final boolean isEmpty() { return map.isEmpty(); }
		public abstract Iterator<E> iterator();
		public abstract boolean contains(Object o);
		public abstract boolean remove(Object o);

		private static final String oomeMsg = "Required array size too large";

		public final Object[] toArray() {
			long sz = map.mappingCount();
			if (sz > MAX_ARRAY_SIZE)
				throw new OutOfMemoryError(oomeMsg);
			int n = (int)sz;
			Object[] r = new Object[n];
			int i = 0;
			for (E e : this) {
				if (i == n) {
					if (n >= MAX_ARRAY_SIZE)
						throw new OutOfMemoryError(oomeMsg);
					if (n >= MAX_ARRAY_SIZE - (MAX_ARRAY_SIZE >>> 1) - 1)
						n = MAX_ARRAY_SIZE;
					else
						n += (n >>> 1) + 1;
					r = Arrays.copyOf(r, n);
				}
				r[i++] = e;
			}
			return (i == n) ? r : Arrays.copyOf(r, i);
		}

		@SuppressWarnings("unchecked")
		public final <T> T[] toArray(T[] a) {
			long sz = map.mappingCount();
			if (sz > MAX_ARRAY_SIZE)
				throw new OutOfMemoryError(oomeMsg);
			int m = (int)sz;
			T[] r = (a.length >= m) ? a :
					(T[])java.lang.reflect.Array
							.newInstance(a.getClass().getComponentType(), m);
			int n = r.length;
			int i = 0;
			for (E e : this) {
				if (i == n) {
					if (n >= MAX_ARRAY_SIZE)
						throw new OutOfMemoryError(oomeMsg);
					if (n >= MAX_ARRAY_SIZE - (MAX_ARRAY_SIZE >>> 1) - 1)
						n = MAX_ARRAY_SIZE;
					else
						n += (n >>> 1) + 1;
					r = Arrays.copyOf(r, n);
				}
				r[i++] = (T)e;
			}
			if (a == r && i < n) {
				r[i] = null; // null-terminate
				return r;
			}
			return (i == n) ? r : Arrays.copyOf(r, i);
		}

		public final String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			Iterator<E> it = iterator();
			if (it.hasNext()) {
				for (;;) {
					Object e = it.next();
					sb.append(e == this ? "(this Collection)" : e);
					if (!it.hasNext())
						break;
					sb.append(',').append(' ');
				}
			}
			return sb.append(']').toString();
		}

		public final boolean containsAll(Collection<?> c) {
			if (c != this) {
				for (Object e : c) {
					if (e == null || !contains(e))
						return false;
				}
			}
			return true;
		}

		public final boolean removeAll(Collection<?> c) {
			if (c == null) throw new NullPointerException();
			boolean modified = false;
			for (Iterator<E> it = iterator(); it.hasNext();) {
				if (c.contains(it.next())) {
					it.remove();
					modified = true;
				}
			}
			return modified;
		}

		public final boolean retainAll(Collection<?> c) {
			if (c == null) throw new NullPointerException();
			boolean modified = false;
			for (Iterator<E> it = iterator(); it.hasNext();) {
				if (!c.contains(it.next())) {
					it.remove();
					modified = true;
				}
			}
			return modified;
		}

	}

	public long mappingCount() {
		long n = sumCount();
		return (n < 0L) ? 0L : n; // ignore transient negative values
	}


	public int size() {
		long n = sumCount();
		return ((n < 0L) ? 0 :
				(n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
						(int)n);
	}

	public boolean isEmpty() {
		return sumCount() <= 0L; // ignore transient negative values
	}

	final long sumCount() {
		CounterCell[] as = counterCells; CounterCell a;
		long sum = baseCount;
		if (as != null) {
			for (int i = 0; i < as.length; ++i) {
				if ((a = as[i]) != null)
					sum += a.value;
			}
		}
		return sum;
	}

	public void clear() {
		long delta = 0L; // negative number of deletions
		int i = 0;
		Node<K,V>[] tab = table;
		while (tab != null && i < tab.length) {
			int fh;
			Node<K,V> f = tabAt(tab, i);
			if (f == null)
				++i;
			else if ((fh = f.hash) == MOVED) {
				tab = helpTransfer(tab, f);
				i = 0; // restart
			}
			else {
				synchronized (f) {
					if (tabAt(tab, i) == f) {
						Node<K,V> p = (fh >= 0 ? f :
								(f instanceof TreeBin) ?
										((TreeBin<K,V>)f).first : null);
						while (p != null) {
							--delta;
							p = p.next;
						}
						setTabAt(tab, i++, null);
					}
				}
			}
		}
		if (delta != 0L)
			addCount(delta, -1);
	}


	static final int NCPU = Runtime.getRuntime().availableProcessors();

	private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("segments", Segment[].class),
			new ObjectStreamField("segmentMask", Integer.TYPE),
			new ObjectStreamField("segmentShift", Integer.TYPE)
	};

	static class Node<K,V> implements Map.Entry<K,V> {
		final int hash;
		final K key;
		volatile V val;
		volatile Node<K,V> next;

		Node(int hash, K key, V val, Node<K,V> next) {
			this.hash = hash;
			this.key = key;
			this.val = val;
			this.next = next;
		}

		public final K getKey()       { return key; }
		public final V getValue()     { return val; }
		public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
		public final String toString(){ return key + "=" + val; }
		public final V setValue(V value) {
			throw new UnsupportedOperationException();
		}

		public final boolean equals(Object o) {
			Object k, v, u; Map.Entry<?,?> e;
			return ((o instanceof Map.Entry) &&
					(k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
					(v = e.getValue()) != null &&
					(k == key || k.equals(key)) &&
					(v == (u = val) || v.equals(u)));
		}

		/**
		 * Virtualized support for map.get(); overridden in subclasses.
		 */
		Node<K,V> find(int h, Object k) {
			Node<K,V> e = this;
			if (k != null) {
				do {
					K ek;
					if (e.hash == h &&
							((ek = e.key) == k || (ek != null && k.equals(ek))))
						return e;
				} while ((e = e.next) != null);
			}
			return null;
		}
	}

	static final int spread(int h) {
		return (h ^ (h >>> 16)) & HASH_BITS;
	}

	private static final int tableSizeFor(int c) {
		int n = c - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	static Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
			if ((c = x.getClass()) == String.class) // bypass checks
				return c;
			if ((ts = c.getGenericInterfaces()) != null) {
				for (int i = 0; i < ts.length; ++i) {
					if (((t = ts[i]) instanceof ParameterizedType) &&
							((p = (ParameterizedType)t).getRawType() ==
									Comparable.class) &&
							(as = p.getActualTypeArguments()) != null &&
							as.length == 1 && as[0] == c) // type arg is c
						return c;
				}
			}
		}
		return null;
	}

	@SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
	static int compareComparables(Class<?> kc, Object k, Object x) {
		return (x == null || x.getClass() != kc ? 0 :
				((Comparable)k).compareTo(x));
	}

	@SuppressWarnings("unchecked")
	static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
		return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
	}

	static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
										Node<K,V> c, Node<K,V> v) {
		return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
	}

	static final <K,V> void setTabAt(Node<K,V>[] tab, int i, Node<K,V> v) {
		U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
	}


	@sun.misc.Contended static final class CounterCell {
		volatile long value;
		CounterCell(long x) { value = x; }
	}

	// Unsafe mechanics
	private static final sun.misc.Unsafe U;
	private static final long SIZECTL;
	private static final long TRANSFERINDEX;
	private static final long BASECOUNT;
	private static final long CELLSBUSY;
	private static final long CELLVALUE;
	private static final long ABASE;
	private static final int ASHIFT;

	static {
		try {
			U = sun.misc.Unsafe.getUnsafe();
			Class<?> k = ZGQConcurrentHashMap8.class;
			SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
			TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
			BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
			CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
			Class<?> ck = CounterCell.class;
			CELLVALUE = U.objectFieldOffset(ck.getDeclaredField("value"));
			Class<?> ak = Node[].class;
			ABASE = U.arrayBaseOffset(ak);
			int scale = U.arrayIndexScale(ak);
			if ((scale & (scale - 1)) != 0)
				throw new Error("data type scale not a power of two");
			ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

}
