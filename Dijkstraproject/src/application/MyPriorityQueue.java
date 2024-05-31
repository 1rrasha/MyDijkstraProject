package application;

import java.util.Comparator;

public class MyPriorityQueue<E> {
	private Object[] elements;
	private int size;
	private Comparator<? super E> comparator;

	public MyPriorityQueue(Comparator<? super E> comparator) {
		this.comparator = comparator;
		elements = new Object[10]; // Initial capacity
	}

	public void add(E element) {
		if (size == elements.length) {
			increaseCapacity();
		}
		elements[size++] = element;
		// Ensure the heap property is maintained
		siftUp(size - 1);
	}

	public E poll() {
		if (isEmpty()) {
			throw new IllegalStateException("Priority queue is empty");
		}
		E result = (E) elements[0];
		elements[0] = elements[--size];
		elements[size] = null;
		siftDown(0);
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void siftUp(int index) {
		while (index > 0) {
			int parentIndex = (index - 1) >>> 1;
			E element = (E) elements[index];
			E parent = (E) elements[parentIndex];
			if (comparator.compare(element, parent) >= 0) {
				break;
			}
			swap(index, parentIndex);
			index = parentIndex;
		}
	}

	private void siftDown(int index) {
		int half = size >>> 1;
		while (index < half) {
			int leftChild = (index << 1) + 1;
			int rightChild = leftChild + 1;
			int minChild = (rightChild < size
					&& comparator.compare((E) elements[rightChild], (E) elements[leftChild]) < 0) ? rightChild
							: leftChild;
			E element = (E) elements[index];
			E child = (E) elements[minChild];
			if (comparator.compare(element, child) <= 0) {
				break;
			}
			swap(index, minChild);
			index = minChild;
		}
	}

	private void swap(int i, int j) {
		Object temp = elements[i];
		elements[i] = elements[j];
		elements[j] = temp;
	}

	private void increaseCapacity() {
		int newCapacity = elements.length << 1;
		Object[] newElements = new Object[newCapacity];
		System.arraycopy(elements, 0, newElements, 0, size);
		elements = newElements;
	}
}