package algorithms;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class model a stack using an LinkedList.
 * 
 * @author Mario Randazzo
 */
class LinkedListStack<T> implements Stack<T> {

	private LinkedList<T> stack;

	/**
	 * Build a void stack.
	 */
	public LinkedListStack() {
		this.stack = new LinkedList<T>();
	}

	@Override
	public void push(T item) {
		this.stack.add(0, item);
	}

	@Override
	public T pop() throws NoSuchElementException {
		if (this.stack.size() == 0)
			throw new NoSuchElementException();

		T tmp = this.stack.get(0);

		this.stack.remove(0);

		return tmp;
	}

	@Override
	public T top() throws NoSuchElementException {
		if (this.stack.size() == 0)
			throw new NoSuchElementException();

		return this.stack.get(0);
	}

	@Override
	public void clear() {
		this.stack.clear();
	}

	@Override
	public int size() {
		return this.stack.size();
	}

	@Override
	public boolean isEmpty() {
		if (this.stack.size() == 0)
			return true;

		return false;
	}

}