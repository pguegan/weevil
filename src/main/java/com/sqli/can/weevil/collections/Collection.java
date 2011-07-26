package com.sqli.can.weevil.collections;

import com.sqli.can.weevil.Weevil;
import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;

public abstract class Collection<E> extends Weevil implements Iterable<E> {

	public abstract Collection<E> filter(Filter<E> filter);

	public abstract <F extends Comparable<F>> Collection<F> collect(Collector<E, F> collector);

	public abstract Collection<E> process(Processor<E> processor);

	public abstract int size();

	public abstract boolean contains(E element);

	public abstract boolean empty();

	public boolean notEmpty() {
		return !empty();
	}

	public abstract void clear();

}
