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

import org.seanquinn.redi2.domain.Engagement;

/**
 * An implementation of the {@link BillingRule} interface which is dynamic (e.g.
 * it can take different values for rate and time adjustment factors).
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class DynamicBillingRule implements BillingRule {

	/** An unadjusted modifier. */
	private static final double UNADJUSTED = 1.0;

	/**
	 * The rate at which engagements will be billed according to this rule.
	 */
	private final double rate;

	/**
	 * The facotr by which time will be adjusted, according to this rule.
	 */
	private final double timeAdjustmentFactor;

	/**
	 * Instantiates a new {@link DynamicBillingRule} with an unadjusted constant
	 * billing rate and a constant time adjustment factor. I.e. values are not
	 * modified.
	 */
	public DynamicBillingRule() {
		this(UNADJUSTED, UNADJUSTED);
	}

	/**
	 * Instantiates a new {@link DynamicBillingRule} with the specified billing
	 * rate and an unadjusted time factor.
	 *
	 * @param rate the rate at which engagements processed by this rule will be
	 * 		billed.
	 */
	public DynamicBillingRule(final double rate) {
		this(rate, UNADJUSTED);
	}

	/**
	 * Instantiates a new {@link DynamicBillingRule} with the specified billing
	 * rate and time factor.
	 *
	 * @param rate the rate at which engagements processed by this rule will be
	 * 		billed.
	 * @param timeAdjustmentFactor the factor by which time will be adjusted.
	 */
	public DynamicBillingRule(final double rate, final double timeAdjustmentFactor) {
		this.rate = rate;
		this.timeAdjustmentFactor = timeAdjustmentFactor;
	}

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal calculateCost(final Engagement engagement) {
		final double minutes = engagement.getMinutes();
		final double adjusted = minutes * getTimeAdjustmentFactor();

		final BigDecimal cost = new BigDecimal(adjusted * getRate());
		return cost.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * {@inheritDoc}
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * {@inheritDoc}
	 */
	public double getTimeAdjustmentFactor() {
		return timeAdjustmentFactor;
	}

}
