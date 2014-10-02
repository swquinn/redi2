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
package org.seanquinn.redi2.billing.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.seanquinn.redi2.domain.Engagement;

/**
 * An implementation of the {@link DiscountCalculator} where in the digits of
 * a number will be added together, this result will be the discount for a
 * passed engagement.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class DigitAdditiveDiscountCalculator implements DiscountCalculator {

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal calculateDiscount(final Engagement engagement) {
		double discount = 0.0;

		final double minutes = engagement.getMinutes();
		final List<Integer> digits = getDigits(minutes);
		for (final Integer digit : digits) {
			discount += digit.doubleValue();
		}

		final BigDecimal result = new BigDecimal(discount);
		return result.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 *
	 * @param minutes
	 * @return
	 */
	public List<Integer> getDigits(final double minutes) {
		List<Integer> digits = new ArrayList<Integer>();

		int value = (int) minutes;
		while (value > 0) {
			digits.add(new Integer(value % 10));
			value = value / 10;
		}
		return digits;
	}
}
