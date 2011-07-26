package com.sqli.can.weevil.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.sqli.can.weevil.WeevilTest;
import com.sqli.can.weevil.text.Chain;

public class AmountTest extends WeevilTest {

	public void setUp() {
		Amount.DEFAULT_LOCALE = Locale.FRANCE;
	}

	@Test
	public void testConstructorString() {
		assertToString("123,45 €", new Amount("123,45"));
		// Should also work with a comma
		assertToString("123,45 €", new Amount("123.45"));
		// And with trailing spaces
		assertToString("123,45 €", new Amount("   1 2   3. 45   "));
	}

	@Test
	public void testConstructorChain() {
		Amount amount = new Amount(new Chain("123,45"));
		assertToString("123,45 €", amount);
		// Should also work with a comma
		amount = new Amount("123.45");
		assertToString("123,45 €", amount);
	}

	@Test
	public void testAdd() {
		final Amount addend = new Amount("123,45");
		final Amount augend = new Amount("456,78");
		final Amount amout = addend.add(augend);
		assertToString("580,23 €", addend);
		assertToString("580,23 €", amout);
	}

	@Test
	public void testSubtract() {
		final Amount minuend = new Amount("123,45");
		final Amount subtrahend = new Amount("456,78");
		final Amount amout = minuend.subtract(subtrahend);
		assertToString("-333,33 €", minuend);
		assertToString("-333,33 €", amout);
	}

	@Test
	public void testMultiplyAmount() {
		final Amount multiplier = new Amount("123,45");
		final Amount multiplicand = new Amount("10");
		final Amount amout = multiplier.multiply(multiplicand);
		// Beware of the non-breaking space provided by NumberFormat
		assertToString("1\u00a0234,50 €", multiplier);
		assertToString("1\u00a0234,50 €", amout);
	}

	@Test
	public void testMultiplyBigDecimal() {
		final Amount multiplier = new Amount("123,45");
		final BigDecimal multiplicand = BigDecimal.valueOf(2);
		final Amount amout = multiplier.multiply(multiplicand);
		assertToString("246,90 €", multiplier);
		assertToString("246,90 €", amout);
	}

	@Test
	public void testDivide() {
		final Amount dividend = new Amount("123,45");
		final Amount amout = dividend.divide(2);
		assertToString("61,73 €", dividend);
		assertToString("61,73 €", amout);
	}

	@Test
	public void testRate() {
		final Amount amount = new Amount("123,45");
		final BigDecimal rate = BigDecimal.valueOf(10.25d);
		final Amount result = amount.rate(rate);
		assertToString("12,65 €", amount);
		assertToString("12,65 €", result);
	}

	@Test
	public void testToString() {
		assertToString("123\u00a0456\u00a0789,00 €", new Amount("123456789", Locale.FRANCE));
		assertToString("1\u00a0234,00 €", new Amount("1234,00", Locale.FRANCE));
		assertToString("1\u00a0234,56 €", new Amount("1234,56", Locale.FRANCE));
		assertToString("1\u00a0234,19 €", new Amount("1234,19", Locale.FRANCE));
		assertToString("1\u00a0234,20 €", new Amount("1234,199", Locale.FRANCE));
		assertToString("1\u00a0234,50 €", new Amount("1234,499", Locale.FRANCE));
		assertToString("1\u00a0234,99 €", new Amount("1234,99", Locale.FRANCE));
		assertToString("1\u00a0234,99 €", new Amount("1234,990", Locale.FRANCE));
		assertToString("1\u00a0235,00 €", new Amount("1234,999", Locale.FRANCE));
		assertToString("$1,234.00", new Amount("1234", Locale.US));
		assertToString("$1,234.00", new Amount("1234,00", Locale.US));
		assertToString("$1,234.56", new Amount("1234,56", Locale.US));
		assertToString("$1,234.19", new Amount("1234,19", Locale.US));
		assertToString("$1,234.20", new Amount("1234,199", Locale.US));
		assertToString("$1,234.50", new Amount("1234,499", Locale.US));
		assertToString("$1,234.99", new Amount("1234,99", Locale.US));
		assertToString("$1,234.99", new Amount("1234,990", Locale.US));
		assertToString("$1,235.00", new Amount("1234,999", Locale.US));
		assertToString("£1,234.00", new Amount("1234", Locale.UK));
		assertToString("£1,234.00", new Amount("1234,00", Locale.UK));
		assertToString("£1,234.56", new Amount("1234,56", Locale.UK));
		assertToString("£1,234.19", new Amount("1234,19", Locale.UK));
		assertToString("£1,234.20", new Amount("1234,199", Locale.UK));
		assertToString("£1,234.50", new Amount("1234,499", Locale.UK));
		assertToString("£1,234.99", new Amount("1234,99", Locale.UK));
		assertToString("£1,234.99", new Amount("1234,990", Locale.UK));
		assertToString("£1,235.00", new Amount("1234,999", Locale.UK));
	}

	@Test
	public void testConvert() {
		final Amount amount = new Amount("123,45", Locale.FRANCE);
		amount.convert(Currency.getInstance(Locale.US));
		// Well... As the currency may vary, it is quite impossible to test the returned value
		assertNotNull(amount.toString());
	}

	@Test
	public void testLocalize() {
		Amount amount = new Amount("1234,56");
		amount.localize(Locale.US);
		assertToString("$1,234.56", amount);

		amount = new Amount("1234,56");
		amount.localize(Locale.CANADA);
		assertToString("$1,234.56", amount);

		amount = new Amount("1234,56");
		amount.localize(Locale.UK);
		assertToString("£1,234.56", amount);

		amount = new Amount("1234,56");
		amount.localize(Locale.FRANCE);
		assertToString("1\u00a0234,56 €", amount);
	}

}
