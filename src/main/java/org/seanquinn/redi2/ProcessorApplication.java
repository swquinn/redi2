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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.seanquinn.redi2.utils.CollectionUtils;

/**
 * Processor application
 *
 * @author Sean.Quinn
 * @since 1.0
 */
public class ProcessorApplication
{
    public static void main( final String[] args )
    {
    	final Options options = getOptions();
    	final CommandLineParser parser = new BasicParser();
    	try {
    		CommandLine line = parser.parse(options, args);

    		if (line.hasOption("file")) {
    			final Processor processor = new Processor();
    			processor.process(line.getOptionValue("file"));
    		}
    		else if (line.hasOption("help")){
    			printHelp(options);
    		}
    		else {
    			final Processor processor = new Processor();
    			final double[] minutes = argsToMinutes(line.getArgs());
   				processor.process(minutes);
    		}
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();

    		// ** If we encountered an exception displa
    		printHelp(options);
    	}
    }

    /**
     * Prints the help message for this tool.
     * @param options the options.
     */
    private static void printHelp(final Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("process [OPTIONS] [<minutes> <minutes> <minutes>]", options);
    }

    private static double[] argsToMinutes(final String[] args) {
    	List<Double> minutes = new ArrayList<Double>();
    	if (args != null && args.length > 0) {
			for (String arg : args) {
				try {
					minutes.add(new Double(arg));
				}
				catch (final Exception ex) {
					System.err.println("Unable to parse minutes from: "+arg);
				}
			}
    	}
    	return CollectionUtils.toPrimitiveArray(minutes);
    }

    /**
     * Returns the options.
     *
     * @return the options
     */
    private static Options getOptions() {
    	Options options = new Options();

    	options.addOption(getFileOption());
    	options.addOption(getHelpOption());
    	return options;
    }

    /**
     * Returns the {@code file} option.
     *
     * @return the file option.
     */
    private static Option getFileOption() {
    	return OptionBuilder
    			.withArgName("file")
    			.hasArg()
    			.withLongOpt("file")
    			.withDescription("processes billing reports based on the passed file")
    			.create("f");
    }

    /**
     * Returns the {@code help} option.
     *
     * @return the help option.
     */
    private static Option getHelpOption() {
    	return OptionBuilder
    			.withLongOpt("help")
    			.withDescription("prints this message")
    			.create("h");
    }
}
