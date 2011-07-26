package com.sqli.can.weevil.graphs.trees;

import java.util.Iterator;

import com.sqli.can.weevil.Weevil;
import com.sqli.can.weevil.collections.Array;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.text.Chain;

public class Node<E extends Comparable<E>> extends Weevil implements Comparable<Node<E>>, Iterable<Node<E>> {

	private static final int CODE_LENGTH = 2;

	private static final int MAX_CHILDREN = (int) Math.pow(10, CODE_LENGTH);

	private E value;

	private Node<E> parent;

	private Array<Node<E>> children;

	private Chain code;

	public Node(E value) {
		this.value = value;
		children = new Array<Node<E>>();
		encode(0);
	}

	public E value() {
		return value;
	}

	public Node<E> parent() {
		return parent;
	}

	public Array<Node<E>> children() {
		return children;
	}

	@Override
	public int compareTo(Node<E> node) {
		return value.compareTo(node.value);
	}

	public Node<E> add(E value) {
		Node<E> child = null;
		if (value != null) {
			child = add(new Node<E>(value));
		}
		return child;
	}

	public Node<E> add(Node<E> child) {
		if (child != null) {
			if (children.size() == MAX_CHILDREN) {
				throw new UnsupportedOperationException("Number of children exceeded (" + MAX_CHILDREN + ")");
			}
			child.parent = this;
			child.encode(children.size());
			children.add(child);
		}
		return child;
	}

	public void detach() {
		after().process(new Processor<Node<E>>() {
			@Override
			public void process(Node<E> node) {
				node.encode(node.code.toInteger() - 1);
			}
		});
		parent = null;
		encode(0);
	}

	public Array<Node<E>> flatten() {
		final Array<Node<E>> array = new Array<Node<E>>();
		array.add(this);
		for (Node<E> child : children) {
			array.concat(child.flatten());
		}
		return array;
	}

	public Array<Node<E>> before() {
		return parent.children.clone().filter(new Filter<Node<E>>() {
			@Override
			public boolean include(Node<E> node) {
				return node.code.compareTo(code) < 0;
			}
		});
	}

	public Array<Node<E>> after() {
		return parent.children.clone().filter(new Filter<Node<E>>() {
			@Override
			public boolean include(Node<E> node) {
				return node.code.compareTo(code) > 0;
			}
		});
	}

	@Override
	public Iterator<Node<E>> iterator() {
		return flatten().iterator();
	}

	@Override
	public String toString() {
		final Chain chain = new Chain(value);
		chain.append(" [");
		chain.append(code);
		chain.append("]");
		return chain.toString();
	}

	protected Chain code() {
		return code;
	}

	protected void encode(int seed) {
		code = new Chain(seed);
		code.prepad(0, CODE_LENGTH);
		code.prepend(parent == null ? Chain.EMPTY : parent.code);
		int i = 0;
		for (Node<E> child : children) {
			child.encode(i++);
		}
	}

}
