package com.sqli.can.weevil.graphs.trees;

import java.util.Iterator;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.collections.Array;

@SuppressWarnings("unchecked")
public class TreeTest extends WeevilTest {

	@Test
	public void tesConstructor() {
		final Tree<String> tree = new Tree<String>();
		assertNull(tree.root());
		assertNull(tree.last());
		assertZero(tree.size());
		assertEmpty(tree);
	}

	@Test
	public void tesConstructorNode() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		assertEquals(root, tree.root());
		assertEquals(root, tree.last());
		assertEquals(1, tree.size());
		assertNotEmpty(tree);
	}

	@Test
	public void testAddNode() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		assertContains(tree, root);
		final Node<String> node = new Node<String>("B");
		tree.add(node);
		assertExactMatch(tree, root, node);
	}

	@Test
	public void testAddNullNode() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		tree.add((Node<String>) null);
		assertExactMatch(tree, root);
	}

	@Test
	public void testAddValue() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		final Node<String> node = tree.add("B");
		assertExactMatch(tree, root, node);
	}

	@Test
	public void testAddNullValue() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		tree.add((String) null);
		assertExactMatch(tree, root);
	}

	@Test
	public void testRoot() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		assertEquals(root, tree.root());
		tree.add(new Node<String>("B"));
		assertEquals(root, tree.root());
	}

	@Test
	public void testLeaves() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		final Node<String> child0 = root.add(new Node<String>("B"));
		final Node<String> child1 = root.add(new Node<String>("C"));
		final Node<String> child2 = root.add(new Node<String>("D"));
		final Node<String> child3 = root.add(new Node<String>("E"));
		final Node<String> child00 = child0.add(new Node<String>("F"));
		final Node<String> child000 = child00.add(new Node<String>("G"));
		final Node<String> child10 = child1.add(new Node<String>("H"));
		final Node<String> child11 = child1.add(new Node<String>("I"));
		final Node<String> child20 = child2.add(new Node<String>("J"));

		final Array<Node<String>> leaves = tree.leaves();

		assertExactMatch(leaves, child000, child10, child11, child20, child3);
	}

	@Test
	public void testIterator() {
		final Node<String> root = new Node<String>("A");
		final Tree<String> tree = new Tree<String>(root);
		final Node<String> child0 = new Node<String>("B");
		tree.add(child0);
		final Node<String> child1 = new Node<String>("C");
		tree.add(child1);
		final Node<String> child2 = new Node<String>("D");
		tree.add(child2);
		final Node<String> child3 = new Node<String>("E");
		tree.add(child3);
		final Iterator<Node<String>> iterator = root.iterator();
		assertNotNull(iterator);
		assertEquals(root, iterator.next());
		assertEquals(child0, iterator.next());
		assertEquals(child1, iterator.next());
		assertEquals(child2, iterator.next());
		assertEquals(child3, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testMax() {
		final Tree<Integer> tree = new Tree<Integer>();
		tree.add(new Node<Integer>(1));
		tree.add(new Node<Integer>(10));
		tree.add(new Node<Integer>(50));
		tree.add(new Node<Integer>(29));
		tree.add(new Node<Integer>(-14));
		assertEquals(50, tree.max());
	}

	@Test
	public void testMin() {
		final Tree<Integer> tree = new Tree<Integer>();
		tree.add(new Node<Integer>(18));
		tree.add(new Node<Integer>(10));
		tree.add(new Node<Integer>(45));
		tree.add(new Node<Integer>(2));
		tree.add(new Node<Integer>(20));
		assertEquals(2, tree.min());
	}

	@Test
	public void testRemove() {
		final Tree<String> tree = new Tree<String>();
		final Node<String> root = tree.add(new Node<String>(""));
		final Node<String> node0 = root.add(new Node<String>("0"));
		final Node<String> node00 = node0.add(new Node<String>("00"));
		final Node<String> node01 = node0.add(new Node<String>("01"));
		final Node<String> node1 = root.add(new Node<String>("1"));
		final Node<String> node2 = root.add(new Node<String>("2"));
		final Node<String> node20 = node2.add(new Node<String>("20"));
		final Node<String> node200 = node20.add(new Node<String>("200"));
		final Node<String> node3 = root.add(new Node<String>("3"));
		assertExactMatch(tree, root, node0, node00, node01, node1, node2, node20, node200, node3);
		assertEquals(node00, tree.remove(2));
		assertExactMatch(tree, root, node0, node01, node1, node2, node20, node200, node3);
		assertEquals(node0, tree.remove(1));
		assertExactMatch(tree, root, node1, node2, node20, node200, node3);
		assertEquals(node3, tree.remove(5));
		assertExactMatch(tree, root, node1, node2, node20, node200);
		assertEquals(root, tree.remove(0));
		assertEmpty(tree);
	}

	@Test
	public void testGet() {}

	@Test
	public void testSet() {}

	@Test
	public void testSub() {}

	@Test
	public void testReverse() {}

	@Test
	public void testShuffle() {}

	@Test
	public void testFirst() {}

	@Test
	public void testLast() {}

	@Test
	public void testFilter() {}

	@Test
	public void testCollect() {}

	@Test
	public void testProcess() {}

	@Test
	public void testSize() {
		final Tree<String> tree = new Tree<String>();
		assertZero(tree.size());
		tree.add("A");
		assertEquals(1, tree.size());
		tree.add("B");
		tree.add("C");
		tree.add("D");
		assertEquals(4, tree.size());
		tree.remove(1);
		assertEquals(3, tree.size());
		tree.clear();
		assertZero(tree.size());
	}

	@Test
	public void testContains() {}

	@Test
	public void testIsEmpty() {}

	@Test
	public void testValues() {
		final Tree<String> tree = new Tree<String>();
		final Node<String> root = tree.add(new Node<String>(""));
		final Node<String> node0 = root.add(new Node<String>("0"));
		node0.add(new Node<String>("00"));
		node0.add(new Node<String>("01"));
		root.add(new Node<String>("1"));
		Node<String> node2 = root.add(new Node<String>("2"));
		final Node<String> node20 = node2.add(new Node<String>("20"));
		node20.add(new Node<String>("200"));
		root.add(new Node<String>("3"));

		final Array<String> values = tree.values();

		assertExactMatch(values, "", "0", "00", "01", "1", "2", "20", "200", "3");
	}
}
