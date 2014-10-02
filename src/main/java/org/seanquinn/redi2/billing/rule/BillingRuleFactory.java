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

import org.seanquinn.redi2.billing.discount.DigitAdditiveDiscountCalculator;
import org.seanquinn.redi2.billing.discount.SquareRootDiscountCalculator;
import org.seanquinn.redi2.domain.Engagement;

/**
 * Factory which will produce {@link BillingRule BillingRules} according to the
 * length of an engagement.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class BillingRuleFactory {

	private final double shortEngagementThreshold;
	private final double mediumEngagementThreshold;

	private final BillingRule shortEngagementBillingRule;
	private final BillingRule mediumEngagementBillingRule;
	private final BillingRule longEngagementBillingRule;

	/**
	 * Instantiates a new billing rule factory.
	 *
	 * The billing rules are statically generated based on the criteria
	 * presented in the problem; they are created as properties in the factory
	 * to accomodate for dependency injection were it actually to be used in a
	 * more robust implementation of this code.
	 */
	public BillingRuleFactory() {
		this.shortEngagementThreshold = 1000.0;
		this.mediumEngagementThreshold = 100000.0;

		this.shortEngagementBillingRule = new DynamicDiscountBillingRule(
				new DigitAdditiveDiscountCalculator(), 2.0, 12.0);
		this.mediumEngagementBillingRule = new DynamicDiscountBillingRule(
				new SquareRootDiscountCalculator(), 3.4, 6.0);
		this.longEngagementBillingRule = new DynamicBillingRule(0.6);
	}

	/**
	 * Returns the {@link BillingRule} for the passed {@link Engagement}.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return the {@code BillingRule} for the {@code Engagement}.
	 */
	public BillingRule getRule(final Engagement engagement) {
		if (isShortEngagement(engagement)) {
			return shortEngagementBillingRule;
		}
		else if (isMediumEngagement(engagement)) {
			return mediumEngagementBillingRule;
		}
		return longEngagementBillingRule;
	}

	/**
	 * Return <tt>true</tt> if the {@link Engagement} is a short engagement.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return <tt>true</tt> if the {@code Engagement} is a short engagement;
	 * 		otherwise <tt>false</tt>.
	 */
	private boolean isShortEngagement(final Engagement engagement) {
		final double minutes = engagement.getMinutes();
		return minutes < getShortEngagementThreshold();
	}

	/**
	 * Return <tt>true</tt> if the {@link Engagement} is a medium engagement.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return <tt>true</tt> if the {@code Engagement} is a medium engagement;
	 * 		otherwise <tt>false</tt>.
	 */
	private boolean isMediumEngagement(final Engagement engagement) {
		final double minutes = engagement.getMinutes();
		return minutes >= getShortEngagementThreshold() && minutes < getMediumEngagementThreshold();
	}

	/**
	 * Return <tt>true</tt> if the {@link Engagement} is a long engagement.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return <tt>true</tt> if the {@code Engagement} is a long engagement;
	 * 		otherwise <tt>false</tt>.
	 */
	private boolean isLongEngagement(final Engagement engagement) {
		// Unused method, but here for completeness.
		final double minutes = engagement.getMinutes();
		return minutes >= getMediumEngagementThreshold();
	}

	/**
	 * Returns the medium engagement threshold, in minutes.
	 *
	 * @return the medium engagement threshold, in minutes.
	 */
	public double getMediumEngagementThreshold() {
		return mediumEngagementThreshold;
	}

	/**
	 * Returns the short engagement threshold, in minutes.
	 *
	 * @return the short engagement threshold, in minutes.
	 */
	public double getShortEngagementThreshold() {
		return shortEngagementThreshold;
	}
}
