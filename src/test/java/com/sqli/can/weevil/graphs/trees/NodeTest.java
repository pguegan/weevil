package com.sqli.can.weevil.graphs.trees;

import java.util.Iterator;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;

public class NodeTest extends WeevilTest {

	@Test
	public void testConstructor() {
		final Node<String> node = new Node<String>("A");
		assertEquals("A", node.value());
		assertEmpty(node.children());
		assertNull(node.parent());
	}

	@Test
	public void testCode() {
		// This is pure implementation (and not contract) testing
		final Node<String> root = new Node<String>("A");
		assertToString("00", root.code());
		final Node<String> child0 = root.add(new Node<String>("B"));
		assertToString("0000", child0.code());
		final Node<String> child1 = root.add(new Node<String>("C"));
		assertToString("0001", child1.code());
		final Node<String> child2 = root.add(new Node<String>("D"));
		assertToString("0002", child2.code());
		final Node<String> child3 = root.add(new Node<String>("E"));
		assertToString("0003", child3.code());
		final Node<String> child00 = child0.add(new Node<String>("F"));
		assertToString("000000", child00.code());
		final Node<String> child000 = child00.add(new Node<String>("G"));
		assertToString("00000000", child000.code());
		final Node<String> child10 = child1.add(new Node<String>("H"));
		assertToString("000100", child10.code());
		final Node<String> child11 = child1.add(new Node<String>("I"));
		assertToString("000101", child11.code());
		final Node<String> child20 = child2.add(new Node<String>("J"));
		assertToString("000200", child20.code());
	}

	@Test
	public void testDetach() {
		final Node<String> root = new Node<String>("A");
		final Node<String> child0 = root.add(new Node<String>("B"));
		final Node<String> child1 = root.add(new Node<String>("C"));
		final Node<String> child2 = root.add(new Node<String>("D"));
		final Node<String> child3 = root.add(new Node<String>("E"));
		final Node<String> child00 = child0.add(new Node<String>("F"));
		final Node<String> child000 = child00.add(new Node<String>("G"));
		final Node<String> child10 = child1.add(new Node<String>("H"));
		final Node<String> child11 = child1.add(new Node<String>("I"));
		final Node<String> child20 = child2.add(new Node<String>("J"));

		child1.detach();

		assertNull(child1.parent());
		assertToString("00", child1.code());
		assertToString("0000", child10.code());
		assertToString("0001", child11.code());
		assertToString("00", root.code());
		assertToString("0000", child0.code());
		assertToString("000000", child00.code());
		assertToString("00000000", child000.code());
		assertToString("0001", child2.code());
		assertToString("000100", child20.code());
		assertToString("0002", child3.code());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddNode() {
		final Node<String> node = new Node<String>("A");
		final Node<String> child0 = node.add(new Node<String>("B"));
		assertUnorderedMatch(node.children(), child0);
		final Node<String> child1 = node.add(new Node<String>("C"));
		assertUnorderedMatch(node.children(), child0, child1);
		// Should not change root's children
		child1.add(new Node<String>("D"));
		assertUnorderedMatch(node.children(), child0, child1);
	}

	@Test
	public void testAddNodeTooMuchChildren() {
		final Node<String> node = new Node<String>("A");
		final int max = 100;
		for (int i = 0; i < max; i++) {
			node.add(new Node<String>(String.valueOf(i)));
		}
		try {
			node.add(new Node<String>(String.valueOf(max)));
			fail("UnsupportedOperationException expected");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
			assertMessage("Number of children exceeded (" + max + ")", e);
		}
	}

	@Test
	public void testAddNullNode() {
		final Node<String> node = new Node<String>("A");
		assertNull(node.add((Node<String>) null));
		assertEmpty(node.children());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddValue() {
		final Node<String> node = new Node<String>("A");
		final Node<String> child0 = node.add("B");
		assertUnorderedMatch(node.children(), child0);
		final Node<String> child1 = node.add("C");
		assertUnorderedMatch(node.children(), child0, child1);
		// Should not change root's children
		child1.add("D");
		assertUnorderedMatch(node.children(), child0, child1);
	}

	@Test
	public void testAddValueTooMuchChildren() {
		final Node<String> node = new Node<String>("A");
		final int max = 100;
		for (int i = 0; i < max; i++) {
			node.add(String.valueOf(i));
		}
		try {
			node.add(String.valueOf(max));
			fail("UnsupportedOperationException expected");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
			assertMessage("Number of children exceeded (" + max + ")", e);
		}
	}

	@Test
	public void testAddNullValue() {
		final Node<String> node = new Node<String>("A");
		assertNull(node.add((String) null));
		assertEmpty(node.children());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFlatten() {
		final Node<String> root = new Node<String>("A");
		assertExactMatch(root.flatten(), root);
		final Node<String> child0 = root.add(new Node<String>("B"));
		assertExactMatch(root.flatten(), root, child0);
		final Node<String> child1 = root.add(new Node<String>("C"));
		assertExactMatch(root.flatten(), root, child0, child1);
		final Node<String> child2 = root.add(new Node<String>("D"));
		assertExactMatch(root.flatten(), root, child0, child1, child2);
		final Node<String> child3 = root.add(new Node<String>("E"));
		assertExactMatch(root.flatten(), root, child0, child1, child2, child3);
		final Node<String> child00 = child0.add(new Node<String>("F"));
		assertExactMatch(root.flatten(), root, child0, child00, child1, child2, child3);
		final Node<String> child000 = child00.add(new Node<String>("G"));
		assertExactMatch(root.flatten(), root, child0, child00, child000, child1, child2, child3);
		final Node<String> child10 = child1.add(new Node<String>("H"));
		assertExactMatch(root.flatten(), root, child0, child00, child000, child1, child10, child2, child3);
		final Node<String> child11 = child1.add(new Node<String>("I"));
		assertExactMatch(root.flatten(), root, child0, child00, child000, child1, child10, child11, child2, child3);
		final Node<String> child20 = child2.add(new Node<String>("J"));
		assertExactMatch(root.flatten(), root, child0, child00, child000, child1, child10, child11, child2, child20, child3);
	}

	@Test
	public void testIterator() {
		final Node<String> root = new Node<String>("A");
		final Node<String> child0 = root.add(new Node<String>("B"));
		final Node<String> child1 = root.add(new Node<String>("C"));
		final Node<String> child2 = root.add(new Node<String>("D"));
		final Node<String> child3 = root.add(new Node<String>("E"));
		final Node<String> child00 = child0.add(new Node<String>("F"));
		final Node<String> child000 = child00.add(new Node<String>("G"));
		final Node<String> child10 = child1.add(new Node<String>("H"));
		final Node<String> child11 = child1.add(new Node<String>("I"));
		final Node<String> child20 = child2.add(new Node<String>("J"));
		final Iterator<Node<String>> iterator = root.iterator();
		assertNotNull(iterator);
		assertEquals(root, iterator.next());
		assertEquals(child0, iterator.next());
		assertEquals(child00, iterator.next());
		assertEquals(child000, iterator.next());
		assertEquals(child1, iterator.next());
		assertEquals(child10, iterator.next());
		assertEquals(child11, iterator.next());
		assertEquals(child2, iterator.next());
		assertEquals(child20, iterator.next());
		assertEquals(child3, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testCompareTo() {
		final Node<String> root1 = new Node<String>("Root");
		final Node<String> root2 = new Node<String>("Root");
		assertEquals(0, root1.compareTo(root2));
		final Node<String> node = root1.add(new Node<String>("Node"));
		assertEquals(4, root1.compareTo(node));
		assertEquals(-4, node.compareTo(root1));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAfter() {
		final Node<String> root = new Node<String>("A");
		final Node<String> child0 = root.add(new Node<String>("B"));
		final Node<String> child1 = root.add(new Node<String>("C"));
		final Node<String> child2 = root.add(new Node<String>("D"));
		final Node<String> child3 = root.add(new Node<String>("E"));
		final Node<String> child00 = child0.add(new Node<String>("F"));
		child00.add(new Node<String>("G"));
		child1.add(new Node<String>("H"));
		child1.add(new Node<String>("I"));
		child2.add(new Node<String>("J"));

		assertExactMatch(child0.after(), child1, child2, child3);
		assertExactMatch(child1.after(), child2, child3);
		assertExactMatch(child2.after(), child3);
		assertEmpty(child3.after());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testBefore() {
		final Node<String> root = new Node<String>("A");
		final Node<String> child0 = root.add(new Node<String>("B"));
		final Node<String> child1 = root.add(new Node<String>("C"));
		final Node<String> child2 = root.add(new Node<String>("D"));
		final Node<String> child3 = root.add(new Node<String>("E"));
		final Node<String> child00 = child0.add(new Node<String>("F"));
		child00.add(new Node<String>("G"));
		child1.add(new Node<String>("H"));
		child1.add(new Node<String>("I"));
		child2.add(new Node<String>("J"));

		assertEmpty(child0.before());
		assertExactMatch(child1.before(), child0);
		assertExactMatch(child2.before(), child0, child1);
		assertExactMatch(child3.before(), child0, child1, child2);
	}

	@Test
	public void testToString() {
		assertToString("12345 [00]", new Node<Integer>(12345));
	}

}
