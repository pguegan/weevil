package com.sqli.can.weevil.collections;

import java.util.Iterator;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.text.Chain;

public class CoupleTest extends WeevilTest {

	@Test
	public void testConstructor() {
		final Couple<String> couple = new Couple<String>();
		assertNull(couple.first());
		assertNull(couple.second());
	}

	@Test
	public void testConstructorString() {
		final Couple<String> couple = new Couple<String>("foo", "bar");
		assertEquals("foo", couple.first());
		assertEquals("bar", couple.second());
	}

	@Test
	public void testFirst() {
		final Couple<Integer> couple = new Couple<Integer>(1, 2);
		assertEquals(1, couple.first());
	}

	@Test
	public void testSecond() {
		final Couple<Integer> couple = new Couple<Integer>(42, 58);
		assertEquals(58, couple.second());
	}

	@Test
	public void testIterator() {
		final Couple<String> couple = new Couple<String>("lorem", "ipsum");
		final Iterator<String> iterator = couple.iterator();
		assertEquals("lorem", iterator.next());
		assertEquals("ipsum", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testFilter() {
		final Couple<String> couple = new Couple<String>("Lorem ipsum dolor", "Foo");
		final Array<String> array = couple.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return element.length() > 3;
			}
		});
		assertUnorderedMatch(array, "Lorem ipsum dolor");
	}

	@Test
	public void testFilterNone() {
		final Couple<String> couple = new Couple<String>("foo", "bar");
		final Array<String> array = couple.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return true;
			}
		});
		assertUnorderedMatch(array, "foo", "bar");
	}

	@Test
	public void testFilterAll() {
		final Couple<String> couple = new Couple<String>("foo", "bar");
		final Array<String> array = couple.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return false;
			}
		});
		assertEmpty(array);
	}

	@Test
	public void testSize() {
		final Couple<String> couple = new Couple<String>("foo", "bar");
		assertEquals(2, couple.size());
	}

	@Test
	public void testEmpty() {
		assertTrue(new Couple<String>().empty());
		assertFalse(new Couple<String>("foo", null).empty());
		assertFalse(new Couple<String>(null, "bar").empty());
		assertFalse(new Couple<String>("foo", "bar").empty());
		assertTrue(new Couple<String>(null, null).empty());
	}

	@Test
	public void testNotEmpty() {
		assertFalse(new Couple<String>().notEmpty());
		assertTrue(new Couple<String>("foo", null).notEmpty());
		assertTrue(new Couple<String>(null, "bar").notEmpty());
		assertTrue(new Couple<String>("foo", "bar").notEmpty());
		assertFalse(new Couple<String>(null, null).notEmpty());
	}

	@Test
	public void testContains() {
		final Couple<String> couple = new Couple<String>("foo", "bar");
		assertTrue(couple.contains("foo"));
		assertTrue(couple.contains("bar"));
		assertFalse(couple.contains("xyz"));
	}

	@Test
	public void testCollect() {
		final Couple<String> couple = new Couple<String>("lorem", "foo");
		final Couple<Integer> collected = couple.collect(new Collector<String, Integer>() {
			@Override
			public Integer process(String e) {
				return e.length() * 2;
			}
		});
		assertUnorderedMatch(collected, 10, 6);
	}

	@Test
	public void testProcess() {
		final Couple<Chain> couple = new Couple<Chain>(new Chain(1), new Chain(2));
		couple.process(new Processor<Chain>() {
			@Override
			public void process(Chain chain) {
				chain.repeat();
			}
		});
		assertExactMatch(couple, new Chain(11), new Chain(22));
	}

}
