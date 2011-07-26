package com.sqli.can.weevil.collections.operators;

import org.apache.commons.lang.StringUtils;

public class StringCollector<T> implements Collector<T, String> {

	@Override
	public String process(T e) {
		return ((e == null) ? StringUtils.EMPTY : e.toString());
	}

}
