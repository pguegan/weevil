package com.sqli.can.weevil.collections.iterators;

import java.util.Iterator;

public class IndexedIterator<E> implements Iterator<E> {

	private int index;
	
	private Iterator<E> implementor;
	
	public IndexedIterator(Iterable<E> iterable) {
		index = -1;
		implementor = iterable.iterator();
	}

	@Override
	public boolean hasNext() {
		return implementor.hasNext();
	}

	@Override
	public E next() {
		index++;
		return implementor.next();
	}

	@Override
	public void remove() {
		implementor.remove();
	}
	
	public int index() {
		return index;
	}

}
