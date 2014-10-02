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
package org.seanquinn.redi2.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

import org.seanquinn.redi2.utils.CurrencyUtils;

/**
 * The billing report that reflects the billed amount for an {@link Engagement}.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class BillingReport implements Serializable {

	/** The serial version UID. */
	private static final long serialVersionUID = 6158096798209420953L;

	/**
	 * The {@link Engagement} associated with the report.
	 */
	private final Engagement engagement;

	/**
	 * The amount of money billed to the client for the associated
	 * {@link Engagement}.
	 */
	private final BigDecimal billed;

	/**
	 * A unique ID for the billing report.
	 */
	private final UUID uuid;

	public BillingReport(final Engagement engagement, final BigDecimal billed) {
		this.uuid = UUID.randomUUID();
		this.engagement = engagement;
		this.billed = billed;
	}

	/**
	 * Returns the billing report's {@link #uuid}.
	 *
	 * @return the billing report's uuid.
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * Returns the billing report's {@link #uuid} as a string.
	 *
	 * @return the billing report's uuid as a string.
	 */
	public String getUuidAsString() {
		return uuid.toString();
	}

	/**
	 * Returns the amount billed to the associated {@link Engagement}.
	 *
	 * @return the amount billed to the associated engagement.
	 */
	public BigDecimal getBilled() {
		return billed;
	}

	/**
	 * Returns the billing amount as a currency for the default {@link Locale}
	 * (US dollars).
	 *
	 * @return the billing amount as a currency for the default {@link Locale}
	 * 		(US dollars).
	 */
	public String getBilledAsCurrency() {
		return getBilledAsCurrency(Locale.US);
	}

	/**
	 * Returns the billing amount as a currency in the passed {@link Locale}.
	 *
	 * @param locale the {@code Locale}.
	 * @return the billing amount as a currency.
	 */
	public String getBilledAsCurrency(final Locale locale) {
		return CurrencyUtils.asCurrency(billed.doubleValue(), locale);
	}

	/**
	 * Returns the {@link Engagement} that was billed.
	 *
	 * @return the {@code Engagement}.
	 */
	public Engagement getEngagement() {
		return engagement;
	}

}
