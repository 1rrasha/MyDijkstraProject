package application;

public class MyLinkedList<T> {
	private Node<T> head;
	private int size;

	// Constructor
	public MyLinkedList() {
		head = null;
		size = 0;
	}

	// Method to add an element to the end of the list
	public void add(T data) {
		Node<T> newNode = new Node<>(data);
		if (head == null) {
			head = newNode;
		} else {
			Node<T> current = head;
			while (current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(newNode);
		}
		size++;
	}

	// Method to remove the first occurrence of the specified element from the list
	public boolean remove(T data) {
		if (head == null) {
			return false;
		}
		if (head.getData().equals(data)) {
			head = head.getNext();
			size--;
			return true;
		}
		Node<T> current = head;
		while (current.getNext() != null) {
			if (current.getNext().getData().equals(data)) {
				current.setNext(current.getNext().getNext());
				size--;
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	// Method to get the size of the list
	public int size() {
		return size;
	}

	// Method to check if the list is empty
	public boolean isEmpty() {
		return head == null;
	}

	// Inner class for the nodes of the list
	private static class Node<T> {
		private T data;
		private Node<T> next;

		// Node constructor
		public Node(T data) {
			this.data = data;
			this.next = null;
		}

		// Getters and setters
		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}
	}

	// Method to get the element at the specified index
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getData();
	}

	public Node<T> getHead() {
		return head;
	}
}
