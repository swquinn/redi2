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
package org.seanquinn.redi2.billing;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.seanquinn.redi2.domain.BillingReport;
import org.seanquinn.redi2.domain.Engagement;

/**
 * Contract for performing billing operations against {@link Engagement
 * engagements}.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public interface BillingService {

	/**
	 * Quotes the cost of an {@link Engagement}.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return the cost.
	 */
	BigDecimal quote(Engagement engagement);

	/**
	 * Processes an {@link Engagement} for billing, and produces a
	 * {@link BillingReport}.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return the {@code EngagementReport}.
	 */
	BillingReport process(Engagement engagement);

	/**
	 * Processes a collection of {@link Engagement engagements} for billing, and
	 * produces a list of billing {@link BillingReport reports}.
	 *
	 * @param engagement the {@code Engagement}.
	 * @return the list of billing reports.
	 */
	List<BillingReport> process(Collection<Engagement> engagement);
}
