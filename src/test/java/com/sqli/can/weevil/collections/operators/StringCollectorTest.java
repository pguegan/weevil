package com.sqli.can.weevil.collections.operators;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;

public class StringCollectorTest extends WeevilTest {

	@Test
	public void testCollect() {
		final StringCollector<Integer> collector = new StringCollector<Integer>();
		assertEquals("123", collector.process(new Integer(123)));
	}

	@Test
	public void testCollectNull() {
		final StringCollector<Integer> collector = new StringCollector<Integer>();
		assertEquals("", collector.process(null));
	}

}
