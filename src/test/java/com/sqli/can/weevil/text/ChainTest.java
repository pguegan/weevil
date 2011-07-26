package com.sqli.can.weevil.text;

import java.util.Iterator;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.collections.Array;
import com.sqli.can.weevil.collections.Couple;
import com.sqli.can.weevil.money.Amount;

public class ChainTest extends WeevilTest {

	private Chain chain;

	@Before
	public void setUp() {
		chain = new Chain("Lorem ipsum");
	}

	@Test
	public void testConstructorObject() {
		assertToString("Lorem ipsum", chain);
		assertToString("123456", new Chain(123456));
		assertToString("123456", new Chain(new Chain(123456)));
		assertToString("98\u00a0765,43 €", new Chain(new Amount("98765.43", Locale.FRANCE)));
	}

	@Test
	public void testConstructorObjectNull() {
		assertToString("", new Chain((Object) null));
	}

	@Test
	public void testConstructorArray() {
		assertToString("Lorem ipsum", new Chain(new Array<String>("Lor", "", "em ip", "sum", "")));
	}

	@Test
	public void testConstructorArrayNull() {
		assertToString("", new Chain((Array<String>) null));
	}

	@Test
	public void testEquals() {
		final Chain chain1 = new Chain("Lorem ipsum");
		final Chain chain2 = new Chain("Lorem ipsum");
		assertTrue(chain1.equals(chain2));
	}

	@Test
	public void testEqualsDifferent() {
		final Chain chain1 = new Chain("Lorem ipsum");
		final Chain chain2 = new Chain("Dolor sit amet");
		assertFalse(chain1.equals(chain2));
	}

	@Test
	public void testHashCode() {
		// This test sucks, because it's checking implementation...
		assertEquals("Lorem ipsum".hashCode(), chain.hashCode());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(new Chain().isEmpty());
		assertTrue(new Chain("").isEmpty());
		assertTrue(new Chain(null).isEmpty());
		assertFalse(new Chain(" ").isEmpty());
		assertFalse(new Chain("Lorem ipsum").isEmpty());
	}

	@Test
	public void testIsNotEmpty() {
		assertFalse(new Chain().isNotEmpty());
		assertFalse(new Chain("").isNotEmpty());
		assertFalse(new Chain(null).isNotEmpty());
		assertTrue(new Chain(" ").isNotEmpty());
		assertTrue(new Chain("Lorem ipsum").isNotEmpty());
	}

	@Test
	public void testIsBlank() {
		assertTrue(new Chain().isBlank());
		assertTrue(new Chain("").isBlank());
		assertTrue(new Chain(null).isBlank());
		assertTrue(new Chain(" ").isBlank());
		assertFalse(new Chain("Lorem ipsum").isBlank());
	}

	@Test
	public void testIsNotBlank() {
		assertFalse(new Chain().isNotBlank());
		assertFalse(new Chain("").isNotBlank());
		assertFalse(new Chain(null).isNotBlank());
		assertFalse(new Chain(" ").isNotBlank());
		assertTrue(new Chain("Lorem ipsum").isNotBlank());
	}

	@Test
	public void testCapitalize() {
		chain = new Chain("lorEm Ipsum");
		chain.capitalize();
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testDowncase() {
		chain = new Chain("LorEM IPSuM");
		chain.downcase();
		assertToString("lorem ipsum", chain);
	}

	@Test
	public void testUpcase() {
		chain = new Chain("LorEM IPSuM");
		chain.upcase();
		assertToString("LOREM IPSUM", chain);
	}

	@Test
	public void testClean() {
		chain = new Chain("  Lorem    ipsum    ");
		chain.clean();
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testCompact() {
		chain = new Chain("  Lo   re m    i p  sum    ");
		chain.compact();
		assertToString("Loremipsum", chain);
	}

	@Test
	public void testAbbreviate() {
		chain.abbreviate(8);
		assertToString("Lorem...", chain);
	}

	@Test
	public void testAbbreviateTooShort() {
		try {
			chain.abbreviate(2);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Minimum abbreviation width is 4", e);
		}
	}

	@Test
	public void testAbbreviateNull() {
		chain.abbreviate(null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testReplace() {
		chain.replace("m", "x");
		assertToString("Lorex ipsux", chain);
	}

	@Test
	public void testReplaceEmpty() {
		chain.replace("m", "");
		assertToString("Lore ipsu", chain);
	}

	@Test
	public void testReplaceMissing() {
		chain.replace("foo", "bar");
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testReplaceThatNull() {
		try {
			chain.replace(null, "x");
			fail("IllegalArgumentException expected");
		} catch (final Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot replace a <null> string", e);
		}
	}

	@Test
	public void testReplaceWithNull() {
		chain.replace("m", null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testReplaceOnce() {
		chain.replaceOnce("m", "x");
		assertToString("Lorex ipsum", chain);
	}

	@Test
	public void testReplaceOnceEmpty() {
		chain.replaceOnce("m", "");
		assertToString("Lore ipsum", chain);
	}

	@Test
	public void testReplaceOnceMissing() {
		chain.replaceOnce("foo", "bar");
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testReplaceOnceThatNull() {
		try {
			chain.replaceOnce(null, "x");
			fail("IllegalArgumentException expected");
		} catch (final Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot replace a <null> string", e);
		}
	}

	@Test
	public void testReplaceOnceWithNull() {
		chain.replaceOnce("m", null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testAdd() {
		chain.add(2, "xyz");
		assertToString("Loxyzrem ipsum", chain);
	}

	@Test
	public void testAddNull() {
		chain.add(2, null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testAddIndexTooLarge() {
		try {
			chain.add(100, "xxx");
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testAddNegativeIndex() {
		try {
			chain.add(-1, "xxx");
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testRemove() {
		final String removed = chain.remove(2);
		assertToString("r", removed);
		assertToString("Loem ipsum", chain);
	}

	@Test
	public void testRemoveIndexTooLarge() {
		try {
			chain.remove(100);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testRemoveNegativeIndex() {
		try {
			chain.remove(-1);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testGet() {
		assertEquals("r", chain.get(2));
	}

	@Test
	public void testGetIndexTooLarge() {
		try {
			chain.get(100);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testGetNegativeIndex() {
		try {
			chain.get(-1);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testSet() {
		chain.set(3, "xyz");
		assertToString("Lorxyzipsum", chain);
		chain.set(10, "abcdefgh");
		assertToString("Lorxyzipsuabcdefgh", chain);
		chain = new Chain().set(0, "foo bar baz");
	}

	@Test
	public void testSetEmpty() {
		chain.set(3, "");
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testSetNull() {
		chain.set(3, null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testSetIndexTooLarge() {
		try {
			chain.set(100, "xyz");
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testSetNegativeIndex() {
		try {
			chain.set(-1, "xyz");
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testTruncate() {
		chain.truncate(100);
		assertToString("Lorem ipsum", chain);
		chain.truncate(4);
		assertToString("Lore", chain);
	}

	@Test
	public void testTruncateNegativeLength() {
		try {
			chain.truncate(-1);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testSub() {
		chain.sub(2, 7);
		assertToString("rem i", chain);
	}

	@Test
	public void testSubStartIndexTooLarge() {
		try {
			chain.sub(100, 7);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testSubNegativeStartIndex() {
		try {
			chain.sub(-1, 7);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testSubStartIndexGreaterThanEndIndex() {
		try {
			chain.sub(7, 2);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("End index must be greater than start index: 7, 2", e);
		}
	}

	@Test
	public void testSubSequence() {
		chain.subSequence(2, 7);
		assertToString("rem i", chain);
	}

	@Test
	public void testSubSequenceStartIndexTooLarge() {
		try {
			chain.subSequence(100, 7);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testSubSequenceNegativeStartIndex() {
		try {
			chain.subSequence(-1, 7);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testSubSequenceStartIndexGreaterThanEndIndex() {
		try {
			chain.subSequence(7, 2);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("End index must be greater than start index: 7, 2", e);
		}
	}

	@Test
	public void testReverse() {
		chain.reverse();
		assertToString("muspi meroL", chain);
	}

	@Test
	public void testShuffle() {
		chain.shuffle();
		assertUnorderedMatch(chain.toArray(), "L", "o", "r", "e", "m", " ", "i", "p", "s", "u", "m");
	}

	@Test
	public void testFirst() {
		assertEquals("L", chain.first());
	}

	@Test
	public void testLast() {
		assertEquals("m", chain.last());
	}

	@Test
	public void testToString() {
		assertEquals("Lorem ipsum", chain.toString());
	}

	@Test
	public void testToChain() {
		assertEquals(chain, chain.toChain());
	}

	@Test
	public void testToArray() {
		assertExactMatch(chain.toArray(), "L", "o", "r", "e", "m", " ", "i", "p", "s", "u", "m");
	}

	@Test
	public void testToArrayEmpty() {
		chain = Chain.EMPTY;
		assertEmpty(chain.toArray());
	}

	@Test
	public void testCompareToSame() {
		final Chain chain1 = new Chain("Lorem ipsum");
		final Chain chain2 = new Chain("Lorem ipsum");
		assertZero(chain1.compareTo(chain2));
	}

	@Test
	public void testCompareToAscending() {
		final Chain chain1 = new Chain("Lorem ipsum");
		final Chain chain2 = new Chain("Ut enim ad minim");
		assertStrictNegative(chain1.compareTo(chain2));
	}

	@Test
	public void testCompareToDescending() {
		final Chain chain1 = new Chain("Lorem ipsum");
		final Chain chain2 = new Chain("Dolor sit amet");
		assertStrictPositive(chain1.compareTo(chain2));
	}

	@Test
	public void testSplit() {
		final Couple<Chain> couple = chain.split(3);
		assertToString("Lor", couple.first());
		assertToString("em ipsum", couple.second());
	}

	@Test
	public void testSplitIndexTooLarge() {
		try {
			chain.split(100);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: 100", e);
		}
	}

	@Test
	public void testSplitNegativeIndex() {
		try {
			chain.split(-1);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("Chain index out of bounds: -1", e);
		}
	}

	@Test
	public void testIterator() {
		final Iterator<String> iterator = chain.iterator();
		assertEquals("L", iterator.next());
		assertEquals("o", iterator.next());
		assertEquals("r", iterator.next());
		assertEquals("e", iterator.next());
		assertEquals("m", iterator.next());
		assertEquals(" ", iterator.next());
		assertEquals("i", iterator.next());
		assertEquals("p", iterator.next());
		assertEquals("s", iterator.next());
		assertEquals("u", iterator.next());
		assertEquals("m", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testAppend() {
		chain.append(" sit amet ");
		assertToString("Lorem ipsum sit amet ", chain);
		chain.append(3.14f);
		assertToString("Lorem ipsum sit amet 3.14", chain);
	}

	@Test
	public void testAppendEmpty() {
		chain.append("");
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testAppendNull() {
		chain.append(null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testAppendChar() {
		chain.append('x');
		assertToString("Lorem ipsumx", chain);
		chain.append('y');
		assertToString("Lorem ipsumxy", chain);
	}

	@Test
	public void testAppendCharSequenceEndStart() {
		chain.append("xyz", 0, 3);
		assertToString("Lorem ipsumxyz", chain);
		chain.append("abcdef", 0, 3);
		assertToString("Lorem ipsumxyzabc", chain);
	}

	@Test
	public void testAppendCharSequenceNegativeStart() {
		try {
			chain.append("xyz", -1, 3);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start -1, end 3, s.length() 3", e);
		}
	}

	@Test
	public void testAppendCharSequenceTooLargeEnd() {
		try {
			chain.append("xyz", 0, 10);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start 0, end 10, s.length() 3", e);
		}
	}

	@Test
	public void testAppendCharSequenceStartAfterEnd() {
		try {
			chain.append("xyz", 10, 0);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start 10, end 0, s.length() 3", e);
		}
	}

	@Test
	public void testPrepend() {
		chain.prepend(" sit amet ");
		assertToString(" sit amet Lorem ipsum", chain);
		chain.prepend(3.14f);
		assertToString("3.14 sit amet Lorem ipsum", chain);
	}

	@Test
	public void testPrependEmpty() {
		chain.prepend("");
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testPrependNull() {
		chain.prepend(null);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testPrependChar() {
		chain.prepend('x');
		assertToString("xLorem ipsum", chain);
		chain.prepend('y');
		assertToString("yxLorem ipsum", chain);
	}

	@Test
	public void testPad() {
		chain.pad("x", 25);
		assertToString("Lorem ipsumxxxxxxxxxxxxxx", chain);
		chain.pad("abc", 30);
		assertToString("Lorem ipsumxxxxxxxxxxxxxxabcab", chain);
	}

	@Test
	public void testPadEmpty() {
		chain.pad("", 20);
		assertToString("Lorem ipsum         ", chain);
	}

	@Test
	public void testPadNull() {
		chain.pad(null, 20);
		assertToString("Lorem ipsum         ", chain);
	}

	@Test
	public void testPadShorterLength() {
		chain.pad("x", 4);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testPadNegativeLength() {
		try {
			chain.pad("x", -1);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot pad chain with a negative length: -1", e);
		}
	}

	@Test
	public void testPrepad() {
		chain.prepad("x", 25);
		assertToString("xxxxxxxxxxxxxxLorem ipsum", chain);
		chain.prepad("abc", 30);
		assertToString("abcabxxxxxxxxxxxxxxLorem ipsum", chain);
	}

	@Test
	public void testPrepadEmpty() {
		chain.prepad("", 20);
		assertToString("         Lorem ipsum", chain);
	}

	@Test
	public void testPrepadNull() {
		chain.prepad(null, 20);
		assertToString("         Lorem ipsum", chain);
	}

	@Test
	public void testPrepadShorterLength() {
		chain.prepad("x", 4);
		assertToString("Lorem ipsum", chain);
	}

	@Test
	public void testPrepadNegativeLength() {
		try {
			chain.prepad("x", -1);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot prepad chain with a negative length: -1", e);
		}
	}

	@Test
	public void testRepeat() {
		chain.repeat();
		assertToString("Lorem ipsumLorem ipsum", chain);
		chain.repeat(3);
		assertToString("Lorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsum", chain);
	}

	@Test
	public void testRepeatZero() {
		chain.repeat(0);
		assertToString("", chain);
	}

	@Test
	public void testRepeatNegative() {
		chain.repeat(-1);
		assertToString("", chain);
	}

	@Test
	public void testRepeatChainSeparator() {
		chain.repeat(4, new Chain(" - "));
		assertToString("Lorem ipsum - Lorem ipsum - Lorem ipsum - Lorem ipsum", chain);
	}

	@Test
	public void testRepeatStringSeparator() {
		chain.repeat(4, " - ");
		assertToString("Lorem ipsum - Lorem ipsum - Lorem ipsum - Lorem ipsum", chain);
	}

	@Test
	public void testRepeatNullChainSeparator() {
		chain.repeat(4, (Chain) null);
		assertToString("Lorem ipsumLorem ipsumLorem ipsumLorem ipsum", chain);
	}

	@Test
	public void testRepeatNullStringSeparator() {
		chain.repeat(4, (String) null);
		assertToString("Lorem ipsumLorem ipsumLorem ipsumLorem ipsum", chain);
	}

}
