package algorithms;

import java.util.NoSuchElementException;

/**
 *	This interface defines the operations that can be done with a stack.
 */
interface Stack<T> {
	
	/**
	 *	Inserts an item T onto stack.
	 *	@param item Any item.
	 */
	void push(T item);
	
	/**
	 *	Remove an item onto stack.
	 *	@return the item removed.
	 *	@throws NoSuchElementException if the stack is empty.
	 */
	T pop() throws NoSuchElementException;
	
	/**
	 *	Gets the item onto stack without that is is removed.
	 *	@return the item.
	 *	@throws NoSuchElementException if the stack is empty.
	 */
	T top() throws NoSuchElementException;
	
	/**  Remove all items of the stack. */
	void clear();
	
	/**
	 *	Gets the number of item in the stack.
	 *	@return the number of item.
	 */
	int size();
	
	/**
	 *	Check if the stack is empty.
	 *	@return true if stack is empty, false otherwise.
	 */
	boolean isEmpty();
	
}