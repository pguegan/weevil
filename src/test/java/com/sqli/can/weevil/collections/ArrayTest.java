package com.sqli.can.weevil.collections;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.collections.iterators.IndexedIterator;
import com.sqli.can.weevil.collections.operators.Collector;
import com.sqli.can.weevil.collections.operators.Filter;
import com.sqli.can.weevil.collections.operators.Processor;
import com.sqli.can.weevil.collections.operators.StableCollector;
import com.sqli.can.weevil.text.Chain;

public class ArrayTest extends WeevilTest {

	@Test
	public void testConstructor() {
		final Array<String> array = new Array<String>("a", "b", "a", "b");
		assertExactMatch(array, "a", "b", "a", "b");
	}

	@Test
	public void testAdd() {
		final Array<String> array = new Array<String>("a", "b", "c");
		array.add("d");
		assertExactMatch(array, "a", "b", "c", "d");
	}

	@Test
	public void testAddMultiple() {
		final Array<String> array = new Array<String>("a", "b", "c");
		array.add("d", "e", "f");
		assertExactMatch(array, "a", "b", "c", "d", "e", "f");
	}

	@Test
	public void testAddEmpty() {
		final Array<String> array = new Array<String>("a", "b", "c");
		array.add("");
		assertExactMatch(array, "a", "b", "c", "");
	}

	@Test
	public void testAddNull() {
		final Array<String> array = new Array<String>("a", "b", "c");
		array.add((String) null);
		assertExactMatch(array, "a", "b", "c", null);
	}

	@Test
	public void testAddMultipleNull() {
		final Array<String> array = new Array<String>("a", "b", "c");
		array.add(null, null, null);
		assertExactMatch(array, "a", "b", "c", null, null, null);
	}

	@Test
	public void testAddIndex() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.add(2, "x");
		assertExactMatch(array, "a", "b", "x", "c", "d", "e");
	}

	@Test
	public void testAddIndexTooLarge() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		try {
			array.add(100, "x");
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: 100", e);
		}
	}

	@Test
	public void testAddNegativeIndex() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		try {
			array.add(-1, "x");
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: -1", e);
		}
	}

	@Test
	public void testAddArray() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.add(new Array<String>("f", "g", "h", "i"));
		assertUnorderedMatch(array, "a", "b", "c", "d", "e", "f", "g", "h", "i");
	}

	@Test
	public void testAddNullArray() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.concat(new Array<String>());
		assertUnorderedMatch(array, "a", "b", "c", "d", "e");
	}

	@Test
	public void testAddEmptyArray() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.add((Array<String>) null);
		assertUnorderedMatch(array, "a", "b", "c", "d", "e");
	}

	@Test
	public void testConcat() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.concat(new Array<String>("f", "g", "h", "i"));
		assertUnorderedMatch(array, "a", "b", "c", "d", "e", "f", "g", "h", "i");
	}

	@Test
	public void testConcatNull() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.concat((Array<String>) null);
		assertUnorderedMatch(array, "a", "b", "c", "d", "e");
	}

	@Test
	public void testConcatEmpty() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e");
		array.concat(new Array<String>());
		assertUnorderedMatch(array, "a", "b", "c", "d", "e");
	}

	@Test
	public void testRemove() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.remove("c");
		assertExactMatch(array, "a", "b", "d", "e", "f");
		array.remove("z");
		assertExactMatch(array, "a", "b", "d", "e", "f");
	}

	@Test
	public void testRemoveMultiple() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.remove("c", "d", "z", "e");
		assertExactMatch(array, "a", "b", "f");
	}

	@Test
	public void testRemoveIndex() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.remove(2);
		assertExactMatch(array, "a", "b", "d", "e", "f");
	}

	@Test
	public void testRemoveIndexOutOfBounds() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		try {
			array.remove(100);
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: 100", e);
		}
	}

	@Test
	public void testEmpty() {
		assertTrue(new Array<String>().empty());
		assertFalse(new Array<String>("a", "b", "c").empty());
	}

	@Test
	public void testNotEmpty() {
		assertFalse(new Array<String>().notEmpty());
		assertTrue(new Array<String>("a", "b", "c").notEmpty());
	}

	@Test
	public void testUnique() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		array.unique();
		assertUnorderedMatch(array, "a", "b", "c");
	}

	@Test
	public void testUniqueEmpty() {
		final Array<String> array = new Array<String>();
		final Array<String> set = array.unique();
		assertEmpty(set);
	}

	@Test
	public void testFilter() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b", "d", "d");
		array.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return ("a".equals(element) || "c".equals(element));
			}
		});
		assertUnorderedMatch(array, "a", "a", "a", "c");
	}

	@Test
	public void testFilterNone() {
		final Array<String> array = new Array<String>("a", "a", "a", "c");
		// Keep all elements
		array.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return true;
			}
		});
		assertUnorderedMatch(array, "a", "a", "a", "c");
	}

	@Test
	public void testFilterAll() {
		// Filter all elements
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		array.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return false;
			}
		});
		assertEmpty(array);
	}

	@Test
	public void testFilterEmpty() {
		// Filter all elements
		final Array<String> array = new Array<String>();
		array.filter(new Filter<String>() {
			@Override
			public boolean include(String element) {
				return true;
			}
		});
		assertEmpty(array);
	}

	@Test
	public void testSub() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		final Array<String> subarray = array.sub(1, 4);
		assertUnorderedMatch(subarray, "a", "b", "a");
	}

	@Test
	public void testSubEmpty() {
		final Array<String> array = new Array<String>();
		try {
			array.sub(0, 0);
			fail("IndexOutOfBoundsException must be raised");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Array index out of bounds: 0", e);
		}
	}

	@Test
	public void testSubStartOutOfBounds() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.sub(-1, 3);
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: -1", e);
		}
	}

	@Test
	public void testSubEndOutOfBounds() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.sub(0, 100);
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: 100", e);
		}
	}

	@Test
	public void testSubStartAfterEnd() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.sub(5, 2);
			fail("IllegalArgumentException must be raised");
		} catch (final IllegalArgumentException e) {
			assertNotNull(e);
			assertMessage("End index must be greater than start index: 5, 2", e);
		}
	}

	@Test
	public void testFirst() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		assertEquals("a", array.first());
	}

	@Test
	public void testFirstEmpty() {
		assertNull(new Array<String>().first());
	}

	@Test
	public void testLast() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		assertEquals("f", array.last());
	}

	@Test
	public void testLastEmpty() {
		assertNull(new Array<String>().last());
	}

	@Test
	public void testCompact() {
		final Array<String> array = new Array<String>("a", null, "c", null, "e", null);
		array.compact();
		assertUnorderedMatch(array, "a", "c", "e");
	}

	@Test
	public void testCompactEmpty() {
		final Array<String> array = new Array<String>();
		array.compact();
		assertEmpty(array);
	}

	@Test
	public void testProcess() {
		final Array<Chain> array = new Array<Chain>(new Chain(1), new Chain(2), new Chain(3), new Chain(4));
		array.process(new Processor<Chain>() {
			@Override
			public void process(Chain chain) {
				chain.repeat();
			}
		});
		assertExactMatch(array, new Chain(11), new Chain(22), new Chain(33), new Chain(44));
	}

	@Test
	public void testCollect() {
		final Array<Integer> array = new Array<Integer>(1, 2, 3, 4, 5);
		final Array<String> collected = array.collect(new Collector<Integer, String>() {
			@Override
			public String process(Integer e) {
				return String.valueOf(e * e);
			}
		});
		assertUnorderedMatch(array, 1, 2, 3, 4, 5);
		assertUnorderedMatch(collected, "1", "4", "9", "16", "25");
	}

	@Test
	public void testCollectEmpty() {
		final Array<Integer> array = new Array<Integer>();
		final Array<Integer> collected = array.collect(new StableCollector<Integer>() {
			@Override
			public Integer process(Integer e) {
				return e;
			}
		});
		assertEmpty(array);
		assertEmpty(collected);
	}

	@Test
	public void testReverse() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.reverse();
		assertExactMatch(array, "f", "e", "d", "c", "b", "a");
	}

	@Test
	public void testReverseEmpty() {
		final Array<String> array = new Array<String>();
		array.reverse();
		assertEmpty(array);
	}

	@Test
	public void testShuffle() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.shuffle();
		assertUnorderedMatch(array, "a", "b", "c", "d", "e", "f");
	}

	@Test
	public void testShuffleEmpty() {
		final Array<String> array = new Array<String>();
		array.shuffle();
		assertEmpty(array);
	}

	@Test
	public void testSort() {
		final Array<String> array = new Array<String>("b", "a", "c", "e", "f", "d");
		array.sort();
		assertExactMatch(array, "a", "b", "c", "d", "e", "f");
	}

	@Test
	public void testSortEmpty() {
		final Array<String> array = new Array<String>();
		array.sort();
		assertEmpty(array);
	}

	@Test
	public void testMin() {
		final Array<Integer> array = new Array<Integer>(8, 10, 2, -5, 14, 99);
		assertEquals(-5, array.min());
	}

	@Test
	public void testMinEmpty() {
		final Array<Integer> array = new Array<Integer>();
		assertNull(array.min());
	}

	@Test
	public void testMax() {
		final Array<Integer> array = new Array<Integer>(8, 10, 99, -5, 14, 2);
		assertEquals(99, array.max());
	}

	@Test
	public void testMaxEmpty() {
		final Array<Integer> array = new Array<Integer>();
		assertNull(array.max());
	}

	@Test
	public void testOccurences() {
		final Array<String> array = new Array<String>("a", "a", "b", "c", "a", "d");
		assertEquals(3, array.occurences("a"));
		assertEquals(0, array.occurences("z"));
	}

	@Test
	public void testClear() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.clear();
		assertEmpty(array);
	}

	@Test
	public void testClone() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		final Array<String> clone = array.clone();
		assertExactMatch(array, "a", "b", "c", "d", "e", "f");
		assertExactMatch(clone, "a", "b", "c", "d", "e", "f");
	}

	@Test
	public void testCloneEmpty() {
		final Array<String> array = new Array<String>();
		final Array<String> clone = array.clone();
		assertEmpty(array);
		assertEmpty(clone);
	}

	@Test
	public void testEquals() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		final Array<String> other = new Array<String>("a", "b", "c", "d", "e", "f");
		assertEquals(array, other);
	}

	@Test
	public void testEqualsDifferentSizes() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		final Array<String> other = new Array<String>("a", "b", "c");
		assertNotEquals(array, other);
	}

	@Test
	public void testEqualsDifferentContents() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		final Array<String> other = new Array<String>("a", "b", "c", "z", "y", "x");
		assertNotEquals(array, other);
	}

	@Test
	public void testEqualsDifferentTypes() {
		final Array<String> array = new Array<String>();
		final Array<Integer> other = new Array<Integer>();
		// Both objects belongs to the same class, since generic type is erased at runtime
		assertEquals(array, other);
	}

	@Test
	public void testEqualsDifferentClasses() {
		final Array<String> array = new Array<String>();
		assertNotEquals(array, new Integer(0));
	}

	@Test
	public void testEqualsNull() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		assertNotEquals(array, null);
	}

	@Test
	public void testSet() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.set(2, "x");
		assertExactMatch(array, "a", "b", "x", "d", "e", "f");
	}

	@Test
	public void testSetNull() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.set(2, null);
		assertExactMatch(array, "a", "b", null, "d", "e", "f");
	}

	@Test
	public void testSetIndexTooLarge() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.set(100, "x");
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: 100", e);
		}
	}

	@Test
	public void testSetNegativeIndex() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.set(-1, "x");
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: -1", e);
		}
	}

	@Test
	public void testGet() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		assertEquals("d", array.get(3));
	}

	@Test
	public void testGetIndexTooLarge() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.get(100);
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: 100", e);
		}
	}

	@Test
	public void testGetNegativeIndex() {
		final Array<String> array = new Array<String>("a", "a", "b", "a", "c", "b");
		try {
			array.get(-1);
			fail("IndexOutOfBoundsException must be raised");
		} catch (final IndexOutOfBoundsException e) {
			assertNotNull(e);
			assertMessage("Array index out of bounds: -1", e);
		}
	}

	@Test
	public void testIterator() {
		final Array<String> array = new Array<String>("a", "b", "c");
		final IndexedIterator<String> iterator = array.iterator();
		assertEquals(-1, iterator.index());
		assertEquals("a", iterator.next());
		assertEquals(0, iterator.index());
		assertEquals("b", iterator.next());
		assertEquals(1, iterator.index());
		assertEquals("c", iterator.next());
		assertEquals(2, iterator.index());
		assertFalse(iterator.hasNext());
		assertEquals(2, iterator.index());
	}

	@Test
	public void testContains() {
		final Array<String> array = new Array<String>("a", "b", "c");
		assertTrue(array.contains("a"));
		assertFalse(array.contains("z"));
	}

	@Test
	public void testContainsMultiple() {
		final Array<String> array = new Array<String>("a", "b", "c");
		assertTrue(array.contains("a", "b"));
		assertFalse(array.contains("a", "z"));
	}

	@Test
	public void testIntersect() {
		final Array<String> array = new Array<String>("a", "b", "c", "d", "e", "f");
		array.intersect(new Array<String>("c", "a", "f", "z", "u", "a"));
		assertUnorderedMatch(array, "a", "c", "f");
	}

	@Test
	public void testToChain() {
		final Array<String> array = new Array<String>("foo", "bar", "", "baz");
		assertToString("[foo, bar, , baz]", array.toChain());
	}

	@Test
	public void testToChainEmpty() {
		final Array<String> array = new Array<String>();
		assertToString("[]", array.toChain());
	}

	@Test
	public void testToString() {
		final Array<String> array = new Array<String>("foo", "bar", "", "baz");
		assertToString("[foo, bar, , baz]", array);
	}

	@Test
	public void testToStringEmpty() {
		final Array<String> array = new Array<String>();
		assertToString("[]", array);
	}

}
