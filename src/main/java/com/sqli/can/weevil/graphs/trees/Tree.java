package com.sqli.can.weevil.graphs.trees;

import java.util.Iterator;

import com.sqli.can.weevil.collections.Array;
import com.sqli.can.weevil.collections.Collection;
import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.common.Bounded;
import com.sqli.can.weevil.common.Indexed;

public class Tree<E extends Comparable<E>> extends Collection<Node<E>> implements Indexed<Node<E>>, Bounded<E> {

	private Node<E> root;

	private Node<E> last;

	public Tree() {}

	public Tree(Node<E> root) {
		this.root = root;
		this.last = root;
	}

	public Node<E> add(E value) {
		Node<E> node = null;
		if (value != null) {
			node = add(new Node<E>(value));
		}
		return node;
	}

	public Node<E> add(Node<E> node) {
		if (root == null) {
			root = node;
		} else {
			last.add(node);
		}
		last = node;
		return node;
	}

	public Node<E> root() {
		return root;
	}

	public Array<Node<E>> leaves() {
		return root.flatten().clone().filter(new Filter<Node<E>>() {
			@Override
			public boolean include(Node<E> element) {
				return element.children().empty();
			}
		});
	}

	@Override
	public Iterator<Node<E>> iterator() {
		return root.iterator();
	}

	@Override
	public E max() {
		return root.flatten().max().value();
	}

	@Override
	public E min() {
		return root.flatten().min().value();
	}

	@Override
	public Tree<E> add(int index, Node<E> node) {
		root.flatten().add(index, node);
		return this;
	}

	@Override
	public Node<E> remove(int index) {
		return root.flatten().remove(index);
	}

	@Override
	public Node<E> get(int index) {
		return root.flatten().get(index);
	}

	@Override
	public Tree<E> set(int index, Node<E> node) {
		root.flatten().set(index, node);
		return this;
	}

	@Override
	public Tree<E> sub(int from, int to) {
		// TODO
		return null;
	}

	@Override
	public Tree<E> reverse() {
		// TODO
		return null;
	}

	@Override
	public Tree<E> shuffle() {
		final Array<Node<E>> array = root.flatten().shuffle();
		root = array.first();
		last = array.last();
		root.encode(0);
		return null;
	}

	@Override
	public Node<E> first() {
		return root;
	}

	@Override
	public Node<E> last() {
		return last;
	}

	@Override
	public Tree<E> filter(Filter<Node<E>> filter) {
		root.flatten().filter(filter);
		return this;
	}

	@Override
	public <F extends Comparable<F>> Collection<F> collect(Collector<Node<E>, F> collector) {
		return root.flatten().collect(collector);
	}

	@Override
	public Tree<E> process(Processor<Node<E>> processor) {
		root.flatten().process(processor);
		return this;
	}

	@Override
	public int size() {
		return root == null ? 0 : root.flatten().size();
	}

	@Override
	public boolean contains(Node<E> node) {
		return root == null ? false : root.flatten().contains(node);
	}

	@Override
	public boolean empty() {
		return root == null || root.flatten().empty();
	}

	@Override
	public void clear() {
		root = null;
		last = null;
	}

	public Array<E> values() {
		return root.flatten().collect(new Collector<Node<E>, E>() {
			@Override
			public E process(Node<E> e) {
				return e.value();
			}
		});
	}

}
