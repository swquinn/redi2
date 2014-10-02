/* Copyright (c) 2014 Sean Quinn
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.seanquinn.redi2.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for formatting numbers as currency.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class CurrencyUtils {

	/**
	 * Formats the passed number in the default currency (US).
	 *
	 * @param value the value to be returned in a currency format.
	 * @return the formatted result.
	 */
	public static String asCurrency(final double value) {
		return asCurrency(value, Locale.US);
	}

	/**
	 * Formats the passed number in the context of the passed {@link Locale}.
	 *
	 * @param value the value to be returned in a currency format.
	 * @param locale the {@code Locale}.
	 * @return the formatted result.
	 */
	public static String asCurrency(final double value, final Locale locale) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
		return formatter.format(value);
	}
}
