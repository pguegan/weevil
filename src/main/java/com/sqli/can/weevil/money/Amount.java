package com.sqli.can.weevil.money;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import com.sqli.can.weevil.Weevil;
import com.sqli.can.weevil.text.Chain;

public class Amount extends Weevil {

	public static Locale DEFAULT_LOCALE = Locale.getDefault();

	private static final int SCALE = 2;

	private NumberFormat formater;

	private BigDecimal value;

	private Currency currency;

	public Amount(String value) {
		this(value, DEFAULT_LOCALE);
	}

	public Amount(Chain value) {
		this(value, DEFAULT_LOCALE);
	}

	public Amount(String value, Locale locale) {
		this(new Chain(value), locale);
	}

	public Amount(Chain value, Locale locale) {
		// Accepts comma instead of dot
		this.value = new BigDecimal(value.compact().replaceOnce(",", ".").toString());
		localize(locale);
	}

	public Amount add(Amount augend) {
		final Amount converted = augend.convert(currency);
		final BigDecimal result = value.add(converted.value);
		return inplace(result);
	}

	public Amount subtract(Amount subtrahend) {
		final Amount converted = subtrahend.convert(currency);
		final BigDecimal result = value.subtract(converted.value);
		return inplace(result);
	}

	public Amount multiply(Amount multiplicand) {
		final Amount converted = multiplicand.convert(currency);
		final BigDecimal result = value.multiply(converted.value);
		return inplace(result);
	}

	public Amount multiply(BigDecimal multiplicand) {
		final BigDecimal result = value.multiply(multiplicand);
		return inplace(result);
	}

	public Amount divide(Integer divisor) {
		final BigDecimal factor = BigDecimal.valueOf(divisor);
		final BigDecimal result = value.divide(factor);
		return inplace(result);
	}

	public Amount rate(BigDecimal rate) {
		return inplace(multiply(rate).divide(100));
	}

	public Amount convert(Currency target) {
		final Amount converted = this;
		if (!currency.equals(target)) {
			// TODO Compute the value
		}
		return inplace(converted);
	}

	@Override
	public String toString() {
		return formater.format(value.setScale(SCALE, BigDecimal.ROUND_HALF_UP));
	}

	public final void localize(Locale locale) {
		currency = Currency.getInstance(locale);
		formater = NumberFormat.getCurrencyInstance(locale);
	}

	private Amount inplace(BigDecimal value) {
		this.value = value;
		return this;
	}

	private Amount inplace(Amount replacement) {
		value = replacement.value;
		currency = replacement.currency;
		formater = replacement.formater;
		return this;
	}

}
