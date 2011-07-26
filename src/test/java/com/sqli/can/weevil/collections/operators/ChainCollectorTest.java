package com.sqli.can.weevil.collections.operators;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.text.Chain;

public class ChainCollectorTest extends WeevilTest {

	@Test
	public void testCollect() {
		final ChainCollector<Integer> collector = new ChainCollector<Integer>();
		assertEquals(new Chain("246"), collector.process(new Integer(123) * 2));
	}

	@Test
	public void testCollectNull() {
		final ChainCollector<Integer> collector = new ChainCollector<Integer>();
		assertEquals(Chain.EMPTY, collector.process(null));
	}

}
