package com.sqli.can.weevil.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sqli.can.weevil.collections.iterators.IndexedIterator;
import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.common.Bounded;
import com.sqli.can.weevil.common.Indexed;
import com.sqli.can.weevil.internal.Alias;
import com.sqli.can.weevil.internal.Immutable;

public class Array<E extends Comparable<E>> extends Collection<E> implements Indexed<E>, Bounded<E>, Cloneable {

	private List<E> implementor;

	public Array() {
		implementor = new ArrayList<E>();
	}

	public Array(E... elements) {
		implementor = new ArrayList<E>(elements.length);
		for (final E element : elements) {
			add(element);
		}
	}

	public Array(int capacity) {
		implementor = new ArrayList<E>(capacity);
	}

	public Array(java.util.Collection<E> collection) {
		implementor = new ArrayList<E>(collection);
	}

	@Override
	@Immutable
	public boolean equals(Object o) {
		boolean equals = false;
		if (o != null && o.getClass() == getClass()) {
			@SuppressWarnings("unchecked")
			final Array<E> other = (Array<E>) o;
			equals = (other.size() == size());
			if (equals) {
				final Iterator<E> iterator1 = iterator();
				final Iterator<E> iterator2 = other.iterator();
				while (iterator1.hasNext() && equals) {
					equals = iterator1.next().equals(iterator2.next());
				}
			}
		}
		// This method must be symmetric, so a comparison with a raw List, although possible, is prohibited
		return equals;
	}

	@Override
	@Immutable
	public Array<E> clone() {
		return new Array<E>(implementor);
	}

	@Override
	@Immutable
	public E first() {
		E first = null;
		if (notEmpty()) {
			first = get(0);
		}
		return first;
	}

	@Override
	@Immutable
	public E last() {
		E last = null;
		if (notEmpty()) {
			last = get(size() - 1);
		}
		return last;
	}

	public Array<E> compact() {
		return filter(new Filter<E>() {
			@Override
			public boolean include(E element) {
				return (element != null);
			}
		});
	}

	@Override
	public Array<E> filter(Filter<E> filter) {
		final Array<E> filtered = new Array<E>(size());
		for (E element : this) {
			if (filter.include(element)) {
				filtered.add(element);
			}
		}
		return inplace(filtered);
	}

	@Override
	public Array<E> process(Processor<E> processor) {
		for (E element : this) {
			processor.process(element);
		}
		return this;
	}

	@Override
	@Immutable
	public <F extends Comparable<F>> Array<F> collect(Collector<E, F> collector) {
		final Array<F> collected = new Array<F>(size());
		for (final E element : this) {
			final F operated = collector.process(element);
			collected.add(operated);
		}
		return collected;
	}

	public Array<E> unique() {
		// Ouchy...
		implementor = new ArrayList<E>(new HashSet<E>(implementor));
		return this;
	}

	@Override
	public Array<E> reverse() {
		Collections.reverse(implementor);
		return this;
	}

	public Array<E> sort() {
		Collections.sort(implementor);
		return this;
	}

	@Immutable
	public int occurences(E element) {
		return Collections.frequency(implementor, element);
	}

	@Override
	@Immutable
	public E max() {
		return empty() ? null : Collections.max(implementor);
	}

	@Override
	@Immutable
	public E min() {
		return empty() ? null : Collections.min(implementor);
	}

	@Override
	public Array<E> shuffle() {
		Collections.shuffle(implementor);
		return this;
	}

	@Override
	public int size() {
		return implementor.size();
	}

	@Override
	public boolean empty() {
		return implementor.isEmpty();
	}

	@Override
	public IndexedIterator<E> iterator() {
		return new IndexedIterator<E>(implementor);
	}

	public void add(E element) {
		implementor.add(element);
	}

	public void add(E... elements) {
		implementor.addAll(Arrays.asList(elements));
	}

	@Override
	public Array<E> add(int index, E element) {
		check(index);
		implementor.add(index, element);
		return inplace(implementor);
	}

	@Alias("concat(Array<E>)")
	public Array<E> add(Array<E> array) {
		return concat(array);
	}

	public Array<E> concat(Array<E> array) {
		if (array != null) {
			implementor.addAll(array.implementor);
		}
		return inplace(implementor);
	}

	public Array<E> remove(E element) {
		implementor.remove(element);
		return inplace(implementor);
	}

	public Array<E> remove(E... elements) {
		implementor.removeAll(Arrays.asList(elements));
		return inplace(implementor);
	}

	@Override
	public E remove(int index) {
		check(index);
		return implementor.remove(index);
	}

	@Override
	@Immutable
	public boolean contains(E element) {
		return implementor.contains(element);
	}

	@Immutable
	public boolean contains(E... elements) {
		return implementor.containsAll(Arrays.asList(elements));
	}

	public Array<E> intersect(Array<E> array) {
		implementor.retainAll(array.implementor);
		return this;
	}

	@Override
	public void clear() {
		implementor.clear();
	}

	@Override
	@Immutable
	public E get(int index) {
		check(index);
		return implementor.get(index);
	}

	@Override
	public Array<E> set(int index, E element) {
		check(index);
		implementor.set(index, element);
		return this;
	}

	@Override
	public Array<E> sub(int start, int end) {
		check(start, end);
		return inplace(implementor.subList(start, end));
	}

	@Override
	@Immutable
	public String toString() {
		return "[" + StringUtils.join(implementor, ", ") + "]";
	}

	private Array<E> inplace(List<E> implementor) {
		this.implementor = implementor;
		return this;
	}

	private Array<E> inplace(Array<E> replacement) {
		return inplace(replacement.implementor);
	}

	private void check(int start, int end) {
		check(start);
		check(end);
		if (start > end) {
			throw new IllegalArgumentException("End index must be greater than start index: " + start + ", " + end);
		}
	}

	private void check(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Array index out of bounds: " + index);
		}
	}

}
