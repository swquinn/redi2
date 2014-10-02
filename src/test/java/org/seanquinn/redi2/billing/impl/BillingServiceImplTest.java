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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.seanquinn.redi2.billing.BillingService;
import org.seanquinn.redi2.domain.BillingReport;
import org.seanquinn.redi2.domain.Engagement;
import org.seanquinn.redi2.utils.TimeUtils;

public class BillingServiceImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteInvalidEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(-400 * TimeUtils.ONE_MINUTE_MS);

		service.quote(engagement);
	}

	@Test
	public void testQuoteShortEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(400 * TimeUtils.ONE_MINUTE_MS);

		BigDecimal actual = service.quote(engagement);
		BigDecimal expected = new BigDecimal(9596.0);
		Assert.assertThat(actual, Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testQuoteMediumEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(4000 * TimeUtils.ONE_MINUTE_MS);

		BigDecimal actual = service.quote(engagement);
		BigDecimal expected = new BigDecimal(81591.84);
		Assert.assertThat(actual, Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testQuoteLongEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(400000 * TimeUtils.ONE_MINUTE_MS);

		BigDecimal actual = service.quote(engagement);
		BigDecimal expected = new BigDecimal(240000.0);
		Assert.assertThat(actual, Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testProcessShortEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(400 * TimeUtils.ONE_MINUTE_MS);

		BillingReport report = service.process(engagement);
		Assert.assertThat(report.getEngagement(), Matchers.is(engagement));

		BigDecimal expected = new BigDecimal(9596.0, MathContext.UNLIMITED);
		Assert.assertThat(report.getBilled(), Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testProcessMediumEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(4000 * TimeUtils.ONE_MINUTE_MS);

		BillingReport report = service.process(engagement);
		Assert.assertThat(report.getEngagement(), Matchers.is(engagement));

		BigDecimal expected = new BigDecimal(81591.84);
		Assert.assertThat(report.getBilled(), Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testProcessLongEngagement() {
		final BillingService service = new BillingServiceImpl();
		final Engagement engagement = new Engagement(400000 * TimeUtils.ONE_MINUTE_MS);

		BillingReport report = service.process(engagement);
		Assert.assertThat(report.getEngagement(), Matchers.is(engagement));

		BigDecimal expected = new BigDecimal(240000.0);
		Assert.assertThat(report.getBilled(), Matchers.equalTo(expected.setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	public void testProcessEngagements() {
		final List<Engagement> engagements = new ArrayList<Engagement>();

		final Engagement shortEngagement = new Engagement(400 * TimeUtils.ONE_MINUTE_MS);
		engagements.add(shortEngagement);

		final Engagement mediumEngagement = new Engagement(4000 * TimeUtils.ONE_MINUTE_MS);
		engagements.add(mediumEngagement);

		final Engagement longEngagement = new Engagement(400000 * TimeUtils.ONE_MINUTE_MS);
		engagements.add(longEngagement);

		final BillingService service = new BillingServiceImpl();
		List<BillingReport> actual = service.process(engagements);
		Assert.assertThat(actual, Matchers.notNullValue());
		Assert.assertThat(actual, Matchers.not(Matchers.empty()));
		Assert.assertThat(actual.size(), Matchers.is(3));
	}
}
