package algorithms;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class model a stack using an ArrayList.
 * 
 * @author Mario Randazzo
 */
class ArrayListStack<T> implements Stack<T> {

	private ArrayList<T> stack;

	/**
	 * Build a void stack.
	 */
	public ArrayListStack() {
		this.stack = new ArrayList<T>();
	}

	@Override
	public void push(T item) {
		this.stack.add(item);
	}

	@Override
	public T pop() throws NoSuchElementException {
		if (this.stack.size() == 0)
			throw new NoSuchElementException();

		T tmp = this.stack.get(this.stack.size() - 1);

		this.stack.remove(this.stack.size() - 1);

		return tmp;
	}

	@Override
	public T top() throws NoSuchElementException {
		if (this.stack.size() == 0)
			throw new NoSuchElementException();

		return this.stack.get(this.stack.size() - 1);
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