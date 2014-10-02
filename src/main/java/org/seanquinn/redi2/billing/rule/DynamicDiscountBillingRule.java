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
package org.seanquinn.redi2.billing.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.seanquinn.redi2.billing.discount.DiscountCalculator;
import org.seanquinn.redi2.domain.Engagement;

/**
 * A dynamic billing rule, with discounts.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class DynamicDiscountBillingRule extends DynamicBillingRule {

	/**
	 * The discount calculator.
	 */
	private final DiscountCalculator discountCalculator;

	public DynamicDiscountBillingRule(final DiscountCalculator discountCalculator,
			final double rate, final double timeAdjustmentFactor) {
		super(rate, timeAdjustmentFactor);
		this.discountCalculator = discountCalculator;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If a {@link DiscountCalculator} is registered with the billing rule, it
	 * the cost returned will be the calculated cost (based on rate and time
	 * adjustment factors) less the calculated discount.
	 * </p>
	 */
	@Override
	public BigDecimal calculateCost(final Engagement engagement) {
		final BigDecimal cost = super.calculateCost(engagement);
		if (discountCalculator != null) {
			BigDecimal discountedCost = cost.subtract(discountCalculator.calculateDiscount(engagement));
			return discountedCost.setScale(2, RoundingMode.HALF_UP);
		}
		return cost;
	}

	/**
	 * Returns the {@link DiscountCalculator}.
	 *
	 * @return the {@code DiscountCalculator}.
	 */
	protected DiscountCalculator getDiscountCalculator() {
		return discountCalculator;
	}

}
