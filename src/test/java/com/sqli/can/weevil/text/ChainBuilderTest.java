package com.sqli.can.weevil.text;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.collections.Couple;

public class ChainBuilderTest extends WeevilTest {

	@Test
	public void testConstructor() {
		final ChainBuilder builder = new ChainBuilder();
		assertToString("", builder);
	}

	@Test
	public void testConstructorChain() {
		final ChainBuilder builder = new ChainBuilder(new Chain("Lorem ipsum"));
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testConstructorChainNull() {
		final ChainBuilder builder = new ChainBuilder((Chain) null);
		assertToString("", builder);
	}

	@Test
	public void testConstructorString() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testConstructorStringNull() {
		final ChainBuilder builder = new ChainBuilder((String) null);
		assertToString("", builder);
	}

	@Test
	public void testConstructorObject() {
		final ChainBuilder builder = new ChainBuilder(new Integer(123456));
		assertToString("123456", builder);
	}

	@Test
	public void testConstructorObjectNull() {
		final ChainBuilder builder = new ChainBuilder((Object) null);
		assertToString("", builder);
	}

	@Test
	public void testAppend() {
		final ChainBuilder builder = new ChainBuilder();
		builder.append(123456);
		assertToString("123456", builder);
		builder.append(" lorem ipsum ");
		assertToString("123456 lorem ipsum ", builder);
		builder.append(new Chain("dolor "));
		assertToString("123456 lorem ipsum dolor ", builder);
		builder.append(null);
		assertToString("123456 lorem ipsum dolor ", builder);
		builder.append(new Couple<String>("foo", "bar"));
		assertToString("123456 lorem ipsum dolor [foo, bar]", builder);
	}

	@Test
	public void testAppendNull() {
		final ChainBuilder builder = new ChainBuilder();
		builder.append(null);
		assertToString("", builder);
		builder.append("lorem ipsum");
		assertToString("lorem ipsum", builder);
		builder.append(null);
		assertToString("lorem ipsum", builder);
		builder.append(" dolor");
		assertToString("lorem ipsum dolor", builder);
	}

	@Test
	public void testAppendChar() {
		final ChainBuilder builder = new ChainBuilder();
		builder.append('l');
		assertToString("l", builder);
		builder.append('o');
		assertToString("lo", builder);
		builder.append('r');
		assertToString("lor", builder);
		builder.append('e');
		assertToString("lore", builder);
		builder.append('m');
		assertToString("lorem", builder);
	}

	@Test
	public void testAppendCharSequenceEndStart() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum ");
		builder.append("xyz", 0, 3);
		assertToString("Lorem ipsum xyz", builder);
		builder.append("abcdef", 0, 3);
		assertToString("Lorem ipsum xyzabc", builder);
	}

	@Test
	public void testAppendCharSequenceNegativeStart() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum ");
		try {
			builder.append("xyz", -1, 3);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start -1, end 3, s.length() 3", e);
		}
	}

	@Test
	public void testAppendCharSequenceTooLargeEnd() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum ");
		try {
			builder.append("xyz", 0, 10);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start 0, end 10, s.length() 3", e);
		}
	}

	@Test
	public void testAppendCharSequenceStartAfterEnd() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum ");
		try {
			builder.append("xyz", 10, 0);
			fail("IndexOutOfBoundsException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
			assertMessage("start 10, end 0, s.length() 3", e);
		}
	}

	@Test
	public void testPrepend() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepend(" sit amet ");
		assertToString(" sit amet Lorem ipsum", builder);
		builder.prepend(3.14f);
		assertToString("3.14 sit amet Lorem ipsum", builder);
	}

	@Test
	public void testPrependEmpty() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepend("");
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testPrependNull() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepend(null);
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testPrependChar() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepend('x');
		assertToString("xLorem ipsum", builder);
		builder.prepend('y');
		assertToString("yxLorem ipsum", builder);
	}

	@Test
	public void testPad() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.pad("x", 25);
		assertToString("Lorem ipsumxxxxxxxxxxxxxx", builder);
		builder.pad("abc", 30);
		assertToString("Lorem ipsumxxxxxxxxxxxxxxabcab", builder);
	}

	@Test
	public void testPadEmpty() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.pad("", 20);
		assertToString("Lorem ipsum         ", builder);
	}

	@Test
	public void testPadNull() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.pad(null, 20);
		assertToString("Lorem ipsum         ", builder);
	}

	@Test
	public void testPadShorterLength() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.pad("x", 4);
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testPadNegativeLength() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		try {
			builder.pad("x", -1);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot pad chain with a negative length: -1", e);
		}
	}

	@Test
	public void testPrepad() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepad("x", 25);
		assertToString("xxxxxxxxxxxxxxLorem ipsum", builder);
		builder.prepad("abc", 30);
		assertToString("abcabxxxxxxxxxxxxxxLorem ipsum", builder);
	}

	@Test
	public void testPrepadEmpty() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepad("", 20);
		assertToString("         Lorem ipsum", builder);
	}

	@Test
	public void testPrepadNull() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepad(null, 20);
		assertToString("         Lorem ipsum", builder);
	}

	@Test
	public void testPrepadShorterLength() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		builder.prepad("x", 4);
		assertToString("Lorem ipsum", builder);
	}

	@Test
	public void testPrepadNegativeLength() {
		final ChainBuilder builder = new ChainBuilder("Lorem ipsum");
		try {
			builder.prepad("x", -1);
			fail("IllegalArgumentException expected");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertMessage("Cannot prepad chain with a negative length: -1", e);
		}
	}

}
