package com.sqli.can.weevil;

import org.junit.Assert;

import com.sqli.can.weevil.collections.Collection;

public abstract class WeevilTest extends Assert {

	protected static final void assertToString(String expected, Object actual) {
		Assert.assertEquals(expected, actual.toString());
	}

	protected static final <E> void assertUnorderedMatch(Collection<E> collection, E... elements) {
		Assert.assertEquals("Collections must have the same size:", collection.size(), elements.length);
		for (final E element : elements) {
			Assert.assertTrue(collection.contains(element));
		}
	}

	protected static final <E> void assertExactMatch(Collection<E> collection, E... elements) {
		Assert.assertEquals(elements.length, collection.size());
		int i = 0;
		for (final E element : collection) {
			Assert.assertEquals(element, elements[i++]);
		}
	}

	protected static final <E> void assertExactMatch(E[] expected, E[] actual) {
		Assert.assertEquals(expected.length, actual.length);
		int i = 0;
		for (final E element : expected) {
			Assert.assertEquals(element, actual[i++]);
		}
	}

	protected static final <E> void assertContains(Collection<E> collection, E element) {
		assertTrue(collection.contains(element));
	}

	protected static final void assertEquals(int expected, Integer actual) {
		Assert.assertEquals((Integer) expected, actual);
	}

	protected static final <E> void assertEmpty(Collection<E> collection) {
		Assert.assertTrue(collection.empty());
	}

	protected static final <E> void assertEmpty(E[] array) {
		Assert.assertEquals(0, array.length);
	}

	protected static final <E> void assertNotEmpty(Collection<E> collection) {
		Assert.assertFalse(collection.empty());
	}

	protected static final <E> void assertNotEmpty(E[] array) {
		Assert.assertFalse(array.length == 0);
	}

	protected static final void assertNotEquals(Object expected, Object actual) {
		// Hmmm... Not sure if it's totally ok...
		Assert.assertFalse(expected.equals(actual));
	}

	protected static final void assertMessage(String expected, Exception exception) {
		assertEquals(expected, exception.getMessage());
	}

	protected static final void assertZero(Number number) {
		assertEquals(0, number.intValue());
	}

	protected static final void assertPositive(Number number) {
		assertTrue("Number must be positive (received " + number + ")", number.intValue() >= 0);
	}

	protected static final void assertStrictPositive(Number number) {
		assertTrue("Number must be strictly positive (received " + number + ")", number.intValue() > 0);
	}

	protected static final void assertNegative(Number number) {
		assertTrue("Number must be strictly negative (received " + number + ")", number.intValue() <= 0);
	}

	protected static final void assertStrictNegative(Number number) {
		assertTrue("Number must be negative (received " + number + ")", number.intValue() < 0);
	}

}
