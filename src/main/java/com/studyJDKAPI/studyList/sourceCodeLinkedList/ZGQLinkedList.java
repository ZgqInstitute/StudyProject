package com.studyJDKAPI.studyList.sourceCodeLinkedList;



public class ZGQLinkedList<E> {

	transient int size = 0;
	protected transient int modCount = 0;
	transient Node<E> last;
	transient Node<E> first;

	public ZGQLinkedList() {
	}

	public boolean add(E e) {
		linkLast(e);
		return true;
	}

	void linkLast(E e) {
		final Node<E> l = last;
		final Node<E> newNode = new Node<>(l, e, null);
		last = newNode;
		if (l == null)
			first = newNode;
		else
			l.next = newNode;
		size++;
		modCount++;
	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}
}
