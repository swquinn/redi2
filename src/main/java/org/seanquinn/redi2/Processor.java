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
package org.seanquinn.redi2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.seanquinn.redi2.billing.BillingService;
import org.seanquinn.redi2.billing.impl.BillingServiceImpl;
import org.seanquinn.redi2.domain.BillingReport;
import org.seanquinn.redi2.domain.Engagement;
import org.seanquinn.redi2.utils.CollectionUtils;
import org.seanquinn.redi2.utils.CurrencyUtils;
import org.seanquinn.redi2.utils.TimeUtils;

/**
 * Processor for handling invoicing billing reports.
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class Processor {

	private final BillingService billingService;

	/**
	 * Constructor.
	 *
	 * This processor statically creates the billing service, but could using
	 * a dependency injection container assign the service from a container.
	 */
	public Processor() {
		this.billingService = new BillingServiceImpl();
	}

	/**
	 * Prints the invoice for each of the billing reports to the screen and,
	 * after all reports are printed, the total amount billed as well as the
	 * average charge per customer.
	 *
	 * @param reports the collection of billing reports.
	 */
	private void display(final List<BillingReport> reports) {
		BigDecimal total = new BigDecimal(0.0);
		for (final BillingReport report : reports) {
			total = total.add(report.getBilled());

			print("INVOICE");
			print("==========================");
			print("Report ID:     {0}", report.getUuidAsString());
			print("Billed For:    {0} hrs.", report.getEngagement().getHours());
			print("Amount Billed: {0}", report.getBilledAsCurrency());
			print("\n");
		}

		// ** Display the total billed amount
		print("\nTOTAL BILLED: {0}", CurrencyUtils.asCurrency(total.doubleValue()));

		// ** Display the average charge per customer
		final BigDecimal divisor = new BigDecimal(reports.size());
		final BigDecimal average = total.divide(divisor, 2, RoundingMode.HALF_UP);
		print("AVG. CHARGE PER CUSTOMER: {0}", CurrencyUtils.asCurrency(average.doubleValue()));
	}

	/**
	 * Convenience method for printing a formatted message with a variable
	 * number of arguments to the {@code System.out} stream.
	 *
	 * @param sz the string.
	 * @param args the arguments.
	 */
	private void print(final String sz, final Object ... args) {
		System.out.println(MessageFormat.format(sz, args));
	}

	/**
	 * Processes billing charges for a variable array of minutes and displays
	 * the output for each report entry.
	 *
	 * @param minutes the minutes.
	 */
	public void process(final double ... minutes) {
		List<Engagement> engagements = new ArrayList<Engagement>();
		for (double value : minutes) {
			long ms = (long) value * TimeUtils.ONE_MINUTE_MS;
			engagements.add(new Engagement(ms));
		}

		final List<BillingReport> reports = billingService.process(engagements);
		if (reports != null && reports.size() > 0) {
			display(reports);
		}
	}

	/**
	 *
	 * @param path
	 * @throws IOException
	 */
	public void process(final String path) throws IOException {
		final List<Double> values = readFile(path);
		final double[] minutes = CollectionUtils.toPrimitiveArray(values);
		process(minutes);
	}

	/**
	 * Reads a file, line-by-line as doubles.
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private List<Double> readFile(final String path) throws FileNotFoundException, IOException {
		final List<Double> lines = new ArrayList<Double>();

		FileInputStream fs = null;
		BufferedReader reader = null;
		try {
			fs = new FileInputStream(path);
			reader = new BufferedReader(new InputStreamReader(fs));

			String line;
			while ((line = reader.readLine()) != null) {
				try {
					lines.add(new Double(line));
				}
				catch (final Exception ex) {
					System.err.println("Unable to parse the following to a number of minutes: " + line);
				}
			}
		}
		catch (final FileNotFoundException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			// ** If the reader is non-null, close it.
			if (reader != null) {
				reader.close();
				reader = null;
			}

			// ** If the file input stream is non-null, close it.
			if (fs != null) {
				fs.close();
				fs = null;
			}
		}
		return lines;
	}
}
