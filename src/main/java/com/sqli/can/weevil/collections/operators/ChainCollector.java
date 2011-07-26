package com.sqli.can.weevil.collections.operators;

import com.sqli.can.weevil.text.Chain;

public class ChainCollector<T> implements Collector<T, Chain> {

	@Override
	public Chain process(T e) {
		return new Chain(e);
	}

}
