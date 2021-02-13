package com.studyCollection.studyMap.studyHashMap;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ZGQHashMap8<K,V> implements Serializable {

	private static final long serialVersionUID = 362498820763181265L;
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	static final int TREEIFY_THRESHOLD = 8;//(ZGQ) 链表长度的阈值
	static final int UNTREEIFY_THRESHOLD = 6;
	static final int MIN_TREEIFY_CAPACITY = 64;//(ZGQ)

	static class Node<K,V> implements Map.Entry<K,V> {
		final int hash;//(ZGQ) 根据key计数出来的哈希值
		final K key;
		V value;
		Node<K,V> next;

		Node(int hash, K key, V value, Node<K,V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public final K getKey()        { return key; }
		public final V getValue()      { return value; }
		public final String toString() { return key + "=" + value; }

		public final int hashCode() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (o == this)
				return true;
			if (o instanceof Map.Entry) {
				Map.Entry<?,?> e = (Map.Entry<?,?>)o;
				if (Objects.equals(key, e.getKey()) &&
						Objects.equals(value, e.getValue()))
					return true;
			}
			return false;
		}
	}

	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	/**---ZGQ---
	 * 若新增节点key的Class对象实现了comparable接口才返回key的Class对象
	 * @param x 新增节点的key
	 * @return 新增节点的key的Class对象
	 */
	static Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
			if ((c = x.getClass()) == String.class) // bypass checks
				return c;
			if ((ts = c.getGenericInterfaces()) != null) {
				for (int i = 0; i < ts.length; ++i) {
					if (((t = ts[i]) instanceof ParameterizedType) &&
							((p = (ParameterizedType)t).getRawType() == Comparable.class) &&
							(as = p.getActualTypeArguments()) != null &&
							as.length == 1 &&
							as[0] == c
					) // type arg is c
						return c;
				}
			}
		}
		return null;
	}

	@SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
	static int compareComparables(Class<?> kc, Object k, Object x) {
		return (x == null || x.getClass() != kc ? 0 : ((Comparable)k).compareTo(x));
	}

	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	//(ZGQ) HashMap的数组类型为Node
	transient Node<K,V>[] table;
	transient Set<Map.Entry<K,V>> entrySet;
	transient int size;
	transient int modCount;
	int threshold;
	final float loadFactor;

	public ZGQHashMap8(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " +
					initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " +
					loadFactor);
		this.loadFactor = loadFactor;
		this.threshold = tableSizeFor(initialCapacity);
	}

	public ZGQHashMap8(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	public ZGQHashMap8() {
		this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
	}

	public ZGQHashMap8(Map<? extends K, ? extends V> m) {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		putMapEntries(m, false);
	}

	final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
		int s = m.size();
		if (s > 0) {
			if (table == null) { // pre-size
				float ft = ((float)s / loadFactor) + 1.0F;
				int t = ((ft < (float)MAXIMUM_CAPACITY) ?
						(int)ft : MAXIMUM_CAPACITY);
				if (t > threshold)
					threshold = tableSizeFor(t);
			}
			else if (s > threshold)
				resize();
			for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
				K key = e.getKey();
				V value = e.getValue();
				putVal(hash(key), key, value, false, evict);
			}
		}
	}

	public V put(K key, V value) {
		return putVal(hash(key), key, value, false, true);
	}

	/**---ZGQ---
	 * 新增元素时，假设计数出来要插入Node[]数组下标为2
	 * @param hash 根据key算出来的哈希值
	 * @param key key
	 * @param value value
	 * @param onlyIfAbsent false
	 * @param evict true 在LinkedHashMap才有使用到
	 * @return
	 */
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
		Node<K,V>[] tab;
		Node<K,V> p;//(ZGQ) p刚开始指向数组下标为2位置链表的头节点
		int n, i;
		//(ZGQ) 判断Node[]数组是否为空
		if ((tab = table) == null || (n = tab.length) == 0)
			//(ZGQ) 若Node[]数组为空，则进行初始化
			n = (tab = resize()).length;
		/**---ZGQ---
		 * i = (n - 1) & hash 计数插入数组的哪一个下标
		 * (p = tab[i]) == null 判断数组下标为i处是否为空
		 */
		if ((p = tab[i = (n - 1) & hash]) == null)
			//(ZGQ) 数组下标为i处若为空，则创建Node，放在数组下标为i处
			tab[i] = newNode(hash, key, value, null);
		else {//(ZGQ) 数组下标为i处若不为空，进入else
			Node<K,V> e;
			K k;
			//(ZGQ) 1-先判断新增节点的key是否与下标为2处的链表的头节点的key相同
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				e = p;//(ZGQ) 将头节点赋给e
			else if (p instanceof TreeNode)//(ZGQ) 2-再判断下标为2处是链表还是红黑树
				e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
			else {//(ZGQ) 执行到这说明下标为2处是链表，不是红黑树
				for (int binCount = 0; ; ++binCount) {//(ZGQ) 遍历链表，看链表中有没有与新增节点相同的key
					if ((e = p.next) == null) {//(ZGQ) 若当前节点的下一个节点为空，说明整个链表的节点key都没有与新增节点key相同
						/**---ZGQ---
						 * 创建Node，放在链表当前节点的next（插入链表的尾部）  《尾插法》
						 */
						p.next = newNode(hash, key, value, null);
						/**---ZGQ---
						 * 将新节点插入到链表的尾部后会判断链表的长度是否达到阈值，若达到了就要将链表改为红黑树
						 * 注：
						 *   1）上一行将新节点插入到链表的尾部后binCount并没有立刻 +1
						 *   2）当binCount = 7 时，就会将链表转为红黑树。当binCount = 7 时，链表共有8个节点。
						 *
						 * 总结：链表什么时候转为红黑树：当链表已经有8个节点，当在链表尾部插入第9个节点后(此时链表的长度为9)，链表就会转为红黑树（树化）
						 */
						if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
							//(ZGQ) treeifyBin()方法：将链表转为红黑树
							treeifyBin(tab, hash);
						break;
					}
					//(ZGQ) 判断新增节点的key 与 下标为2处的链表中的节点的key是否有相同
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
						break;//(ZGQ) 链表中的节点有key 和 新增的节点key相同，则直接跳出for循环

					p = e;//(ZGQ) 链表的当前节点key与新增节点key不等，则向下移动，继续判断链表下一个节点key是否与新增节点key相等
				}
			}

			//(ZGQ) 若能进入if的话，说明新增节点的key与头节点的key相同
			if (e != null) { // existing mapping for key
				//(ZGQ) 先将头节点的value保存到oldValue
				V oldValue = e.value;
				if (!onlyIfAbsent || oldValue == null)
					//(ZGQ) 替换
					e.value = value;
				afterNodeAccess(e);
				//(ZGQ) 返回头节点的value
				return oldValue;
			}
		}
		++modCount;
		//(ZGQ) 无论新增节点是插入链表还是红黑树 size都会 +1
		if (++size > threshold)
			//(ZGQ) 扩容
			resize();
		afterNodeInsertion(evict);
		return null;
	}

	void afterNodeAccess(Node<K,V> p) { }
	void afterNodeInsertion(boolean evict) { }
	void afterNodeRemoval(Node<K,V> p) { }

	/**---ZGQ---
	 * 将链表转为红黑树
	 * @param tab Node[]数组
	 * @param hash 由新增节点key计数出来的哈希值
	 */
	final void treeifyBin(Node<K,V>[] tab, int hash) {
		int n, index;//(ZGQ) n：Node[]数组长度
		Node<K,V> e;
		/**---ZGQ---
		 * 将链表转为红黑树的条件：
		 *   1）链表的长度要大于8，当往链表尾部插入第9个节点时就会进入treeifyBin()方法
		 *   2）treeifyBin()方法内还有判断：若Node[]数组为空 或 Node[]数组的长度小于64 还是不会将链表转为红黑树
		 */
		if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
			resize();//(ZGQ) 若Node[]数组为空 或 Node[]数组的长度小于64 先对Node[]数组进行扩容
		else if ((e = tab[index = (n - 1) & hash]) != null) {//(ZGQ) e为链表的头节点
			TreeNode<K,V> hd = null, tl = null;//t1=头节点
			/**---ZGQ---
			 * do()循环做的事：
			 *   1）将节点类型由Node转为TreeNode
			 *   2）生成双向链表
			 */
			do {
				//(ZGQ) replacementTreeNode()方法是将节点类型由Node转为TreeNode
				TreeNode<K,V> p = replacementTreeNode(e, null);
				if (tl == null)
					hd = p;
				else {
					//(ZGQ) 形成双向链表
					p.prev = tl;
					tl.next = p;
				}
				tl = p;
			} while ((e = e.next) != null);

			//(ZGQ) hd为双向链表的头节点
			if ((tab[index] = hd) != null)
				//(ZGQ) treeify()方法是将双向链表转为红黑树的方法
				hd.treeify(tab);
		}
	}

	TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
		return new TreeNode<>(p.hash, p.key, p.value, next);
	}

	Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
		return new Node<>(p.hash, p.key, p.value, next);
	}

	TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
		return new TreeNode<>(hash, key, value, next);
	}

	Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
		return new Node<>(hash, key, value, next);
	}

	/**---ZGQ---
	 * 扩容
	 */
	final Node<K,V>[] resize() {
		Node<K,V>[] oldTab = table;
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		int oldThr = threshold;
		int newCap, newThr = 0;
		if (oldCap > 0) {
			if (oldCap >= MAXIMUM_CAPACITY) {
				threshold = Integer.MAX_VALUE;
				return oldTab;
			}
			//(ZGQ) 扩容为原数组的2倍
			else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
				newThr = oldThr << 1; // double threshold
		}
		else if (oldThr > 0) // initial capacity was placed in threshold
			newCap = oldThr;
		else {               // zero initial threshold signifies using defaults
			newCap = DEFAULT_INITIAL_CAPACITY;
			newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
		}
		if (newThr == 0) {
			float ft = (float)newCap * loadFactor;
			newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE);
		}
		threshold = newThr;
		@SuppressWarnings({"rawtypes","unchecked"})
		Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
		table = newTab;

		//(ZGQ) 扩容
		if (oldTab != null) {
			for (int j = 0; j < oldCap; ++j) {
				Node<K,V> e;
				//(ZGQ) 从旧Node[]数组下标为0开始遍历
				if ((e = oldTab[j]) != null) {
					oldTab[j] = null;
					if (e.next == null)
						//(ZGQ) 若链表只有一个节点，则直接计数该节点要存放到新数组的哪一个下标，并放入新数组
						newTab[e.hash & (newCap - 1)] = e;
					else if (e instanceof TreeNode)//(ZGQ) 若头节点的类型是TreeNode，则进行红黑树扩容
					/**---ZGQ---
					 * this：HashMap
					 * newTab：新数组
					 * j：下标
					 * oldCap：旧数组长度
					 */
						((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
					else { // preserve order  (ZGQ) 若节点是链表
						/**---ZGQ---
						 * 将链表从旧数组移动到新数组时，若旧数组的长度为16，链表在旧数组下标为2的位置，那么链表移动到新数组的下标有如下2种可能：
						 *   1）在新数组的下标还是为2；
						 *   2）在新数组的下标为 2 + 旧数组的长度 = 18
						 *
						 *  loHead、loTail分别存放放入新数组下标为2位置链表的头节点与尾节点；
						 *  hiHead、hiTail分别存放放入新数组下标为18位置链表的头节点与尾节点；
						 */
						Node<K,V> loHead = null, loTail = null;
						Node<K,V> hiHead = null, hiTail = null;
						Node<K,V> next;
						do {
							//(ZGQ) 刚开始e表示链表的头节点。 next = e.next先将头节点的下一个节点保存起来
							next = e.next;
							//(ZGQ) 节点的哈希值与旧数组长度做 与 运算，若结果等于0，则该节点按顺序的插入以loHead为头节点的链表
							if ((e.hash & oldCap) == 0) {//(ZGQ) oldCap为旧数组的大小
								if (loTail == null)
									loHead = e;
								else
									loTail.next = e;
								loTail = e;
							}
							//(ZGQ) 节点的哈希值与旧数组长度做 与 运算，若结果不等于0，则该节点按顺序的插入以hiHead为头节点的链表
							else {
								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);

						if (loTail != null) {
							loTail.next = null;
							newTab[j] = loHead;
						}
						if (hiTail != null) {
							hiTail.next = null;
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab;
	}

	/**---ZGQ---
	 * TreeNode表示红黑树的节点，是Node的子类
	 * 由于有prev 和 next，也可把红黑树看做双向链表
	 */
	static final class TreeNode<K,V> extends ZGQLinkedHashMap8.Entry<K,V> {
		/*父类Node的属性
		final int hash;
		final K key;
		V value;
		Node<K,V> next;
		*/
		TreeNode<K,V> parent;  // red-black tree links 父节点
		TreeNode<K,V> left; //(ZGQ) 左节点
		TreeNode<K,V> right; //(ZGQ) 右节点
		TreeNode<K,V> prev;    // needed to unlink next upon deletion
		boolean red; //(ZGQ) 是否为红色？ true : false
		TreeNode(int hash, K key, V val, Node<K,V> next) {
			super(hash, key, val, next);
		}

		final TreeNode<K,V> root() {
			for (TreeNode<K,V> r = this, p;;) {
				if ((p = r.parent) == null)
					return r;
				r = p;
			}
		}

		/**---ZGQ---
		 *
		 * @param tab Node[]数组
		 * @param root 红黑树的根节点
		 */
		static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
			int n;
			if (root != null && tab != null && (n = tab.length) > 0) {
				int index = (n - 1) & root.hash;
				TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
				if (root != first) {
					Node<K,V> rn;
					//(ZGQ) 将红黑树的根节点赋值给数组对应的下标
					tab[index] = root;

					//(ZGQ) 下面代码实现的功能：因为红黑树的根节点也在双向链表中，将红黑树的根节点移到双向链表的头节点
					TreeNode<K,V> rp = root.prev;
					if ((rn = root.next) != null)
						((TreeNode<K,V>)rn).prev = rp;
					if (rp != null)
						rp.next = rn;
					if (first != null)
						first.prev = root;
					root.next = first;
					root.prev = null;
				}
				assert checkInvariants(root);
			}
		}

		final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
			TreeNode<K,V> p = this;
			do {
				int ph, dir; K pk;
				TreeNode<K,V> pl = p.left, pr = p.right, q;
				if ((ph = p.hash) > h)
					p = pl;
				else if (ph < h)
					p = pr;
				else if ((pk = p.key) == k || (k != null && k.equals(pk)))
					return p;
				else if (pl == null)
					p = pr;
				else if (pr == null)
					p = pl;
				else if ((kc != null ||
						(kc = comparableClassFor(k)) != null) && (dir = compareComparables(kc, k, pk)) != 0)
					p = (dir < 0) ? pl : pr;
				else if ((q = pr.find(h, k, kc)) != null)
					return q;
				else
					p = pl;
			} while (p != null);
			return null;
		}


		final TreeNode<K,V> getTreeNode(int h, Object k) {
			return ((parent != null) ? root() : this).find(h, k, null);
		}

		static int tieBreakOrder(Object a, Object b) {
			int d;
			if (a == null || b == null || (d = a.getClass().getName().compareTo(b.getClass().getName())) == 0)
				d = (System.identityHashCode(a) <= System.identityHashCode(b) ? -1 : 1);
			return d;
		}

		/**---ZGQ---
		 * 该方法的作用：将双向链表转为红黑树
		 * @param tab Node[]数组
		 */
		final void treeify(Node<K,V>[] tab) {
			TreeNode<K,V> root = null;
			//(ZGQ) this为双向链表的头节点，next为每次要插入红黑树的节点
			for (TreeNode<K,V> x = this, next; x != null; x = next) {
				next = (TreeNode<K,V>)x.next;
				x.left = x.right = null;
				if (root == null) {
					x.parent = null;
					x.red = false;//(ZGQ) 将根节点设置成黑色
					root = x;//(ZGQ) 双向链表的头节点设置为根节点
				}
				else {
					//(ZGQ) x为新插入的节点
					K k = x.key;
					int h = x.hash;
					Class<?> kc = null;//(ZGQ) key的Class类型
					for (TreeNode<K,V> p = root;;) {
						/**---ZGQ---
						 * 先看新增节点插入父节点的左边还是右边：
						 *   （1）先比较新增节点key的哈希值与父节点key的哈希值；
						 *   （2）若条件（1）相等，再看key是否实现comparable接口，若实现了再比较key的大小
						 *   （3）若条件（1）和条件（2）d都相等，再调用tieBreakOrder()方法
						 *         3.1 比较key的Class名字，若还相等再比较3.2
						 *         3.2 System.identityHashCode(key)
						 *
						 * 补充：identityHashCode(key)方法：当一个类重写的hashCode()方法，但是现在又不想使用重写的hashCode()方法，就可以使用identityHashCode()方法来获得哈希值
						 */
						int dir, ph;
						K pk = p.key;//(ZGQ) 根节点的key
						if ((ph = p.hash) > h)//(ZGQ) 若父节点的哈希值大于新插入节点的哈希值，则将dir设为 -1，新节点插入父节点的左边
							dir = -1;
						else if (ph < h)//(ZGQ) 若父节点的哈希值小于新插入节点的哈希值，则将dir设为 1，新节点插入父节点的右边
							dir = 1;
						else if ((kc == null && (kc = comparableClassFor(k)) == null) || (dir = compareComparables(kc, k, pk)) == 0)
							dir = tieBreakOrder(k, pk);

						TreeNode<K,V> xp = p;
						if ((p = (dir <= 0) ? p.left : p.right) == null) {
							//(ZGQ) 将xp节点赋值给x节点的父节点
							x.parent = xp;
							/**---ZGQ---
							 * 将新节点插入红黑树
							 */
							if (dir <= 0)
								xp.left = x;
							else
								xp.right = x;

							//(ZGQ) 平衡红黑树
							root = balanceInsertion(root, x);
							break;
						}
					}
				}
			}

			/**---ZGQ---
			 * 该方法做了2件事：
			 *   1）将红黑树的根节点赋值给数组对应的下标；
			 *   2）因为红黑树的根节点也在双向链表中，将红黑树的根节点移到双向链表的头节点
			 */
			moveRootToFront(tab, root);
		}

		final Node<K,V> untreeify(ZGQHashMap8<K,V> map) {
			Node<K,V> hd = null, tl = null;
			for (Node<K,V> q = this; q != null; q = q.next) {
				Node<K,V> p = map.replacementNode(q, null);
				if (tl == null)
					hd = p;
				else
					tl.next = p;
				tl = p;
			}
			return hd;
		}

		final TreeNode<K,V> putTreeVal(ZGQHashMap8<K,V> map, Node<K,V>[] tab, int h, K k, V v) {
			Class<?> kc = null;
			boolean searched = false;
			TreeNode<K,V> root = (parent != null) ? root() : this;
			for (TreeNode<K,V> p = root;;) {
				int dir, ph; K pk;
				if ((ph = p.hash) > h)
					dir = -1;
				else if (ph < h)
					dir = 1;
				else if ((pk = p.key) == k || (k != null && k.equals(pk)))
					return p;
				else if ((kc == null && (kc = comparableClassFor(k)) == null) || (dir = compareComparables(kc, k, pk)) == 0) {
					if (!searched) {
						TreeNode<K,V> q, ch;
						searched = true;
						if (((ch = p.left) != null && (q = ch.find(h, k, kc)) != null) || ((ch = p.right) != null && (q = ch.find(h, k, kc)) != null))
							return q;
					}
					dir = tieBreakOrder(k, pk);
				}

				TreeNode<K,V> xp = p;
				if ((p = (dir <= 0) ? p.left : p.right) == null) {
					Node<K,V> xpn = xp.next;
					TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
					if (dir <= 0)
						xp.left = x;
					else
						xp.right = x;
					xp.next = x;
					x.parent = x.prev = xp;
					if (xpn != null)
						((TreeNode<K,V>)xpn).prev = x;
					/**---ZGQ---
					 * balanceInsertion()方法作用：平衡红黑树
					 */
					moveRootToFront(tab, balanceInsertion(root, x));
					return null;
				}
			}
		}

		final void removeTreeNode(ZGQHashMap8<K,V> map, Node<K,V>[] tab, boolean movable) {
			int n;
			if (tab == null || (n = tab.length) == 0)
				return;
			int index = (n - 1) & hash;
			TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
			TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
			if (pred == null)
				tab[index] = first = succ;
			else
				pred.next = succ;
			if (succ != null)
				succ.prev = pred;
			if (first == null)
				return;
			if (root.parent != null)
				root = root.root();
			if (root == null || root.right == null || (rl = root.left) == null || rl.left == null) {
				tab[index] = first.untreeify(map);  // too small
				return;
			}
			TreeNode<K,V> p = this, pl = left, pr = right, replacement;
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
					root = s;
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
					root = replacement;
				else if (p == pp.left)
					pp.left = replacement;
				else
					pp.right = replacement;
				p.left = p.right = p.parent = null;
			}

			TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);

			if (replacement == p) {  // detach
				TreeNode<K,V> pp = p.parent;
				p.parent = null;
				if (pp != null) {
					if (p == pp.left)
						pp.left = null;
					else if (p == pp.right)
						pp.right = null;
				}
			}
			if (movable)
				moveRootToFront(tab, r);
		}

		/**---ZGQ---
		 * 红黑树扩容
		 * @param map HashMap
		 * @param tab 新数组
		 * @param index 下标
		 * @param bit 旧数组长度
		 */
		final void split(ZGQHashMap8<K,V> map, Node<K,V>[] tab, int index, int bit) {
			TreeNode<K,V> b = this;
			// Relink into lo and hi lists, preserving order
			TreeNode<K,V> loHead = null, loTail = null;
			TreeNode<K,V> hiHead = null, hiTail = null;
			int lc = 0, hc = 0;
			for (TreeNode<K,V> e = b, next; e != null; e = next) {
				next = (TreeNode<K,V>)e.next;
				e.next = null;
				/**---ZGQ---
				 *  步骤1：
				 *  因为红黑树节点TreeNode继承了Node，TreeNode有prev和next属性，所以红黑树也可以看成一个双向 链表。
				 *  首先跟移动链表一样，将红黑树链表也插分成两个分别以loHead和hiHead为头节点的链表。
				 *  在插的过程中用lc记录loHead链表的长度；hc记录hiHead链表的长度。
				 */
				//(ZGQ) 节点的哈希值与旧数组长度做 与 运算，若结果等于0，则该节点按顺序的插入以loHead为头节点的链表
				if ((e.hash & bit) == 0) {
					if ((e.prev = loTail) == null)
						loHead = e;
					else
						loTail.next = e;
					loTail = e;
					++lc;
				}
				//(ZGQ) 节点的哈希值与旧数组长度做 与 运算，若结果不等于0，则该节点按顺序的插入以hiHead为头节点的链表
				else {
					if ((e.prev = hiTail) == null)
						hiHead = e;
					else
						hiTail.next = e;
					hiTail = e;
					++hc;
				}
			}

			//(ZGQ) 步骤2：判断低位链表loHead是否为空
			if (loHead != null) {
				//(ZGQ) 再判断低位链表loHead的长度，若长度小于6，则会将红黑树改成链表
				if (lc <= UNTREEIFY_THRESHOLD)
					//(ZGQ) 使用untreeify()方法将红黑树改为链表(单向链表)，节点类型由TreeNode改为Node
					tab[index] = loHead.untreeify(map);
				else {//(ZGQ) 若长度大于6，则直接将链表loHead放入新数组，放入新数组的下标与旧数组相同
					tab[index] = loHead;
					//(ZGQ) 若高位链表hiHead不为空，需要对低位链表loHead重新树化
					if (hiHead != null) // (else is already treeified)
						loHead.treeify(tab);
				}
			}

			//(ZGQ) 步骤3：判断高位链表hiHead是否为空
			if (hiHead != null) {
				if (hc <= UNTREEIFY_THRESHOLD)
					tab[index + bit] = hiHead.untreeify(map);
				else {
					tab[index + bit] = hiHead;
					if (loHead != null)
						hiHead.treeify(tab);
				}
			}
		}

		/**---ZGQ---
		 * 左旋
		 * p：为新增节点的父节点，表示需要旋转的节点
		 */
		static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
											  TreeNode<K,V> p) {
			TreeNode<K,V> r, pp, rl;
			if (p != null && (r = p.right) != null) {//(ZGQ) 新增节点的父节点不为空 且 新增节点的父节点的右子节点不为空
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

		/**---ZGQ---
		 * 右旋
		 */
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

		/**---ZGQ---
		 * 该方法做的事：进入该方法之前新增节点已经插入红黑树，这个方法是做红黑树的平衡操作！
		 * @param root 表示红黑树的根节点
		 * @param x 表示新插入的节点
		 * @return
		 */
		static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
													TreeNode<K,V> x) {
			//(ZGQ) 新插入的节点默认是红色
			x.red = true;
			/**---ZGQ---
			 * xp：表示新增节点的父节点；
			 * xpp：表示新增节点的祖父节点；
			 * xppl：表示xpp的左子节点；
			 * xppr：表示xpp的右子节点；
			 */
			for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
				//(ZGQ) 判断新增节点的父节点是否为空
				if ((xp = x.parent) == null) {
					//(ZGQ) 若新增节点的父节点为空，则表示该红黑树还没有节点，那么新增节点就是根节点，设为黑色
					x.red = false;
					return x;
				}
				else if (!xp.red || (xpp = xp.parent) == null)//(ZGQ) 新增节点的父节点是黑色的 或 新增节点的祖父节点为空 则直接返回父节点
					return root;
				//(ZGQ) 能执行到这表示新增节点的父节点是红色的
				if (xp == (xppl = xpp.left)) {//(ZGQ) 新增节点的父节点 == 祖父节点的左子节点
					if ((xppr = xpp.right) != null && xppr.red) { //(ZGQ) 叔叔节点不为空 且 叔叔节点是红色的 ==> 操作：变色  《情况2.2》
						xppr.red = false;//(ZGQ) 将新增节点的叔叔节点置 黑
						xp.red = false;//(ZGQ) 将新增节点的父节点置 黑
						xpp.red = true;//(ZGQ) 将新增节点的祖父节点置 红

						//(ZGQ) 执行到这子树的平衡关系以及调整好，下面还要调整整颗红黑树的平衡

						x = xpp;//(ZGQ) 由于新增节点的祖父节点颜色由黑变为红，现在将祖父节点作为新节点赋给x，通过循环重新调整红黑树
					} else {//(ZGQ) 叔叔节点为空 或 叔叔节点是黑色的  ==> 操作：旋转 + 变色  《情况2.1》
						if (x == xp.right) {//(ZGQ) 新增节点(红色)是父节点(红色)的右节点，父节点是祖父节点(黑色)的左节点。 操作：左旋转 + 变色  《类似情况2.1*》
							/**---ZGQ---
							 * 左旋
							 */
							root = rotateLeft(root, x = xp);
							xpp = (xp = x.parent) == null ? null : xp.parent;
						}
						if (xp != null) {
							xp.red = false;
							if (xpp != null) {
								xpp.red = true;
								/**---ZGQ---
								 * 右旋
								 */
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
	}






}
