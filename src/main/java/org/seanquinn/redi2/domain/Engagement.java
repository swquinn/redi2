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

import org.seanquinn.redi2.utils.TimeUtils;

/**
 * The representation of an engagement with a client.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class Engagement implements Serializable {

	/** The serial version UID. */
	private static final long serialVersionUID = -2926529678562460901L;

	private long lengthMs;


	public Engagement() {
		// Empty
	}

	/**
	 *
	 * @param lengthMs
	 */
	public Engagement(final long lengthMs) {
		this.lengthMs = lengthMs;
	}

	/**
	 * Returns the length of this engagement in milliseconds.
	 *
	 * @return the length of this engagement in milliseconds.
	 */
	public long getLengthMs() {
		return lengthMs;
	}

	/**
	 * Assigns a value to the length of this engagement, in milliseconds.
	 *
	 * @param lengthMs the length of the engagement in milliseconds.
	 */
	public void setLengthMs(final long lengthMs) {
		this.lengthMs = lengthMs;
	}

	/**
	 * Returns the length of this engagement, in a fractional representation of
	 * minutes.
	 *
	 * @return
	 */
	public double getMinutes() {
		return lengthMs / TimeUtils.ONE_MINUTE_MS;
	}

	/**
	 * Returns the length of this engagement in a fractional representation of
	 * the number of hours.
	 *
	 * @return the number of hours.
	 */
	public double getHours() {
		return (double) lengthMs / (double) TimeUtils.ONE_HOUR_MS;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder()
			.append("ENGAGEMENT[lengthMs = ")
			.append(lengthMs)
			.append("]");
		return sb.toString();
	}
}
