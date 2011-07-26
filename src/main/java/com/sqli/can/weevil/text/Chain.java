package com.sqli.can.weevil.text;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.sqli.can.weevil.Weevil;
import com.sqli.can.weevil.collections.Array;
import com.sqli.can.weevil.collections.Couple;
import com.sqli.can.weevil.common.Appendable;
import com.sqli.can.weevil.common.Indexed;
import com.sqli.can.weevil.internal.Alias;
import com.sqli.can.weevil.internal.Immutable;

public class Chain extends Weevil implements CharSequence, Indexed<String>, Iterable<String>, Comparable<Chain>, Appendable {

	private static final String EMPTY_STRING = StringUtils.EMPTY;

	public static final Chain EMPTY = new Chain(EMPTY_STRING);

	private String implementor;

	public Chain() {
		implementor = StringUtils.EMPTY;
	}

	public Chain(Object o) {
		implementor = (o == null) ? StringUtils.EMPTY : o.toString().intern();
	}

	public Chain(Array<String> array) {
		if (array == null) {
			implementor = StringUtils.EMPTY;
		} else {
			final ChainBuilder builder = new ChainBuilder();
			for (String string : array) {
				builder.append(string);
			}
			implementor = builder.build().toString().intern();
		}
	}

	@Override
	@Immutable
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof Chain) {
			equals = implementor.equals(((Chain) o).implementor);
		}
		// This method must be symetric, so a comparison with a raw String, although possible, is prohibited
		return equals;
	}

	@Override
	@Immutable
	public int hashCode() {
		return implementor.hashCode();
	}

	@Override
	@Immutable
	public String toString() {
		return implementor;
	}

	@Override
	@Immutable
	public Chain toChain() {
		return this;
	}

	@Immutable
	public Integer toInteger() {
		Integer result = null;
		if (StringUtils.isNumeric(implementor)) {
			result = Integer.valueOf(implementor);
		}
		return result;
	}

	@Immutable
	public boolean isEmpty() {
		return StringUtils.isEmpty(implementor);
	}

	@Immutable
	public boolean isNotEmpty() {
		return StringUtils.isNotEmpty(implementor);
	}

	@Immutable
	public boolean isBlank() {
		return StringUtils.isBlank(implementor);
	}

	@Immutable
	public boolean isNotBlank() {
		return StringUtils.isNotBlank(implementor);
	}

	@Immutable
	public Array<String> toArray() {
		final int length = length();
		final Array<String> array = new Array<String>(length);
		for (int i = 0; i < length; i++) {
			array.add(get(i));
		}
		return array;
	}

	public Chain capitalize() {
		final String capitalized = StringUtils.capitalize(downcase().implementor);
		return inplace(capitalized);
	}

	public Chain downcase() {
		final String downcased = StringUtils.lowerCase(implementor);
		return inplace(downcased);
	}

	public Chain upcase() {
		final String upcased = StringUtils.upperCase(implementor);
		return inplace(upcased);
	}

	public Chain clean() {
		final String cleaned = StringUtils.trimToEmpty(implementor.replaceAll("\\s+", " "));
		return inplace(cleaned);
	}

	public Chain compact() {
		final String compacted = StringUtils.trimToEmpty(implementor.replaceAll("\\s+", ""));
		return inplace(compacted);
	}

	public Chain abbreviate(Integer width) {
		final Chain abbreviated;
		if (width == null) {
			abbreviated = this;
		} else {
			abbreviated = inplace(StringUtils.abbreviate(implementor, width));
		}
		return abbreviated;
	}

	public Chain replace(String that, String with) {
		if (that == null) {
			throw new IllegalArgumentException("Cannot replace a <null> string");
		}
		return inplace(StringUtils.replace(implementor, that, with));
	}

	public Chain replaceOnce(String that, String with) {
		if (that == null) {
			throw new IllegalArgumentException("Cannot replace a <null> string");
		}
		return inplace(StringUtils.replaceOnce(implementor, that, with));
	}

	@Override
	@Immutable
	public char charAt(int index) {
		return implementor.charAt(index);
	}

	@Override
	@Immutable
	public int length() {
		return implementor.length();
	}

	@Alias("sub(int, int)")
	@Override
	public CharSequence subSequence(int start, int end) {
		return sub(start, end);
	}

	@Override
	public Chain add(int index, String element) {
		check(index);
		final Chain added;
		if (element == null) {
			added = this;
		} else {
			final Couple<Chain> split = split(index);
			final ChainBuilder builder = new ChainBuilder(length() + element.length());
			builder.append(split.first());
			builder.append(element);
			builder.append(split.second());
			added = inplace(builder.build());
		}
		return added;
	}

	@Override
	public Chain append(Object object) {
		final ChainBuilder builder = new ChainBuilder(this);
		builder.append(object);
		return inplace(builder.build());
	}

	@Override
	public Chain append(char c) {
		final ChainBuilder builder = new ChainBuilder(this);
		builder.append(c);
		return inplace(builder.build());
	}

	@Override
	public Chain append(CharSequence sequence) {
		final ChainBuilder builder = new ChainBuilder(this);
		builder.append(sequence);
		return inplace(builder.build());
	}

	@Override
	public Chain append(CharSequence sequence, int start, int end) {
		final ChainBuilder builder = new ChainBuilder(this);
		builder.append(sequence, start, end);
		return inplace(builder.build());
	}

	@Override
	public Chain pad(Object o, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot pad chain with a negative length: " + length);
		}
		return inplace(StringUtils.rightPad(implementor, length, (o == null ? EMPTY_STRING : o.toString())));
	}

	@Override
	public Chain prepad(Object o, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot prepad chain with a negative length: " + length);
		}
		return inplace(StringUtils.leftPad(implementor, length, (o == null ? EMPTY_STRING : o.toString())));
	}

	@Override
	public Chain prepend(Object o) {
		final ChainBuilder builder = new ChainBuilder(o);
		builder.append(this);
		return inplace(builder.build());
	}

	@Override
	public String remove(int index) {
		check(index);
		final String removed = get(index);
		final ChainBuilder builder = new ChainBuilder(length() - 1);
		builder.append(implementor.substring(0, index));
		builder.append(implementor.substring(index + 1, length()));
		inplace(builder.build());
		return removed;
	}

	@Override
	@Immutable
	public String get(int index) {
		check(index);
		return String.valueOf(implementor.charAt(index));
	}

	@Override
	public Chain set(int index, String element) {
		check(index);
		final Chain chain;
		if (element == null) {
			chain = this;
		} else {
			final int length = length();
			final int bound = index + element.length();
			final ChainBuilder builder = new ChainBuilder(Math.max(length, bound));
			builder.append(implementor.substring(0, index));
			builder.append(element);
			if (bound < length) {
				builder.append(implementor.substring(bound));
			}
			chain = inplace(builder.build());
		}
		return chain;
	}

	public Chain truncate(int length) {
		return sub(0, Math.min(length, length()));
	}

	@Override
	public Chain sub(int start, int end) {
		check(start, end);
		return inplace(StringUtils.substring(implementor, start, end));
	}

	@Override
	public Chain reverse() {
		return inplace(StringUtils.reverse(implementor));
	}

	@Override
	public Chain shuffle() {
		final Array<String> shuffled = toArray().shuffle();
		return inplace(new Chain(shuffled));
	}

	@Override
	@Immutable
	public String first() {
		return get(0);
	}

	@Override
	@Immutable
	public String last() {
		return get(length() - 1);
	}

	@Override
	@Immutable
	public Iterator<String> iterator() {
		return toArray().iterator();
	}

	@Immutable
	public Couple<Chain> split(int index) {
		check(index);
		final Chain first = new Chain(implementor.substring(0, index));
		final Chain second = new Chain(implementor.substring(index));
		return new Couple<Chain>(first, second);
	}

	@Override
	@Immutable
	public int compareTo(Chain chain) {
		return implementor.compareTo(chain.implementor);
	}

	public Chain repeat() {
		return repeat(2);
	}

	/**
	 * Repeats this chain as many times as specified.
	 * 
	 * <pre>
	 * "a".repeat(3)   =>  "aaa"
     * "ab".repeat(2)  =>  "abab"
     * "ab".repeat(0)  =>  ""
     * "ab".repeat(-1) =>  ""
     * "".repeat(2)    =>  ""</pre>
	 * <p>
	 * This method can be chained with itself, although it is more convenient to multiply the parameters and make only one call. More formally:
	 * 
	 * <pre>
	 * &forall; i, j
	 * chain.repeat(i).repeat(j) = chain.repeat(j).repeat(i) 
	 *                           = chain.repeat(i * j)</pre>
	 * </p>
	 * 
	 * @param times The number of repetitions of this chain. If zero or negative, this chain is not repeated at all and is the empty chain.
	 * @return this chain.
	 */
	public Chain repeat(int times) {
		return inplace(StringUtils.repeat(implementor, times));
	}

	public Chain repeat(int times, Chain separator) {
		return repeat(times, (separator == null) ? EMPTY_STRING : separator.implementor);
	}

	public Chain repeat(int times, String separator) {
		return inplace(StringUtils.repeat(implementor, separator, times));
	}

	private void check(int start, int end) {
		check(start);
		check(end);
		if (start > end) {
			throw new IllegalArgumentException("End index must be greater than start index: " + start + ", " + end);
		}
	}

	private void check(int index) {
		if (index < 0 || index > length()) {
			throw new IndexOutOfBoundsException("Chain index out of bounds: " + index);
		}
	}

	private Chain inplace(Chain chain) {
		return inplace(chain.implementor);
	}

	private Chain inplace(String string) {
		implementor = string.intern();
		return this;
	}

}
