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

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.seanquinn.redi2.domain.Engagement;
import org.seanquinn.redi2.utils.TimeUtils;

public class DigitAdditiveDiscountCalculatorTest {

	@Test
	public void testCalculateDiscount() {
		final Engagement engagement = new Engagement();
		engagement.setLengthMs(400 * TimeUtils.ONE_MINUTE_MS);

		final DiscountCalculator calculator = new DigitAdditiveDiscountCalculator();
		final BigDecimal actual = calculator.calculateDiscount(engagement);

		Assert.assertThat(actual, Matchers.is(new BigDecimal(4.0)));
	}
}
