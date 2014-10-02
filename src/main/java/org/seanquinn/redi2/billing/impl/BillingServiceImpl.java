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
package org.seanquinn.redi2.billing.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seanquinn.redi2.billing.BillingService;
import org.seanquinn.redi2.billing.rule.BillingRule;
import org.seanquinn.redi2.billing.rule.BillingRuleFactory;
import org.seanquinn.redi2.domain.BillingReport;
import org.seanquinn.redi2.domain.Engagement;

/**
 * Implementation of the {@link BillingService}.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class BillingServiceImpl implements BillingService {

	/**
	 * {@inheritDoc}
	 */
	public BillingReport process(final Engagement engagement) {
		if (engagement.getLengthMs() < 0) {
			throw new IllegalArgumentException("Failed attempted to process engagement: "
					+ engagement + "; An engagement must have a non-negative length.");
		}

		final BillingRuleFactory factory = new BillingRuleFactory();
		final BillingRule rule = factory.getRule(engagement);
		final BigDecimal cost = rule.calculateCost(engagement);

		return new BillingReport(engagement, cost);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<BillingReport> process(final Collection<Engagement> engagements) {
		final List<BillingReport> reports = new ArrayList<BillingReport>();
		for (final Engagement engagement : engagements) {
			try {
				final BillingReport report = process(engagement);
				reports.add(report);
			}
			catch (final Exception ex) {
				// In lieu of proper logging, e.g. Log4J
				System.err.println("Error processing engagement: " + engagement);
				ex.printStackTrace();
			}
		}
		return reports;
	}

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal quote(final Engagement engagement) {
		if (engagement.getLengthMs() < 0) {
			throw new IllegalArgumentException("An engagement must have a non-negative length.");
		}
		final BillingRuleFactory factory = new BillingRuleFactory();
		final BillingRule rule = factory.getRule(engagement);
		return rule.calculateCost(engagement);
	}

}
