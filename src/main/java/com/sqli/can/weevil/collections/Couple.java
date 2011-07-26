package com.sqli.can.weevil.collections;

import java.util.Iterator;

import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.text.ChainBuilder;

public class Couple<E extends Comparable<E>> extends Collection<E> {

	private E first;

	private E second;

	public Couple() {}

	public Couple(E first, E second) {
		this.first = first;
		this.second = second;
	}

	public E first() {
		return first;
	}

	public E second() {
		return second;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		return new Array<E>(first, second).iterator();
	}

	@Override
	public Array<E> filter(Filter<E> filter) {
		final Array<E> filtered = new Array<E>();
		for (E element : this) {
			if (filter.include(element)) {
				filtered.add(element);
			}
		}
		return filtered;
	}

	@Override
	public Couple<E> process(Processor<E> processor) {
		processor.process(first);
		processor.process(second);
		return this;
	}

	@Override
	public <F extends Comparable<F>> Couple<F> collect(Collector<E, F> collector) {
		final Couple<F> collected = new Couple<F>();
		collected.first = collector.process(first);
		collected.second = collector.process(second);
		return collected;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean empty() {
		return (first == null && second == null);
	}

	@Override
	public boolean contains(E element) {
		return (first != null && first.equals(element) || second != null && second.equals(element));
	}

	@Override
	public String toString() {
		return new ChainBuilder().append("[").append(first).append(", ").append(second).append("]").toString();
	}

	@Override
	public void clear() {
		first = null;
		second = null;
	}

}
