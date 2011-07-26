package com.sqli.can.weevil.text;

import com.sqli.can.weevil.Weevil;
import com.sqli.can.weevil.common.Appendable;
import com.sqli.can.weevil.common.Builder;

public class ChainBuilder extends Weevil implements Builder<Chain>, Appendable {

	private final StringBuilder implementor;

	public ChainBuilder() {
		implementor = new StringBuilder();
	}

	public ChainBuilder(String string) {
		this(new Chain(string));
	}

	public ChainBuilder(Chain chain) {
		if (chain == null) {
			implementor = new StringBuilder();
		} else {
			implementor = new StringBuilder(chain.length());
			implementor.append(chain);
		}
	}

	public ChainBuilder(Object o) {
		if (o == null) {
			implementor = new StringBuilder();
		} else {
			implementor = new StringBuilder();
			implementor.append(o.toString());
		}
	}

	public ChainBuilder(int length) {
		implementor = new StringBuilder(length);
	}

	@Override
	public ChainBuilder append(Object object) {
		if (object != null) {
			implementor.append(object);
		}
		return this;
	}

	@Override
	public Chain build() {
		return new Chain(toString());
	}

	@Override
	public String toString() {
		return implementor.toString();
	}

	@Override
	public ChainBuilder append(CharSequence sequence) {
		implementor.append(sequence == null ? Chain.EMPTY : sequence);
		return this;
	}

	@Override
	public ChainBuilder append(char c) {
		implementor.append(c);
		return this;
	}

	@Override
	public ChainBuilder append(CharSequence sequence, int start, int end) {
		implementor.append(sequence, start, end);
		return this;
	}

	@Override
	public ChainBuilder prepend(Object o) {
		implementor.insert(0, o == null ? Chain.EMPTY : o);
		return this;
	}

	@Override
	public ChainBuilder pad(Object o, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot pad chain with a negative length: " + length);
		}
		if (length > implementor.length()) {
			final int offset = length - implementor.length();
			final Chain c = new Chain(o);
			if (c.isEmpty()) {
				c.append(' ');
			}
			c.repeat(offset / c.length() + 1);
			c.sub(0, offset);
			implementor.append(c);
		}
		return this;
	}

	@Override
	public ChainBuilder prepad(Object o, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot prepad chain with a negative length: " + length);
		}
		if (length > implementor.length()) {
			final int offset = length - implementor.length();
			final Chain c = new Chain(o);
			if (c.isEmpty()) {
				c.append(' ');
			}
			c.repeat(offset / c.length() + 1);
			c.sub(0, offset);
			implementor.insert(0, c);
		}
		return this;
	}

}
