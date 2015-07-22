package print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class is a Terminal implementation of the Printer interface, hence all
 * output is sent to standard output. It implements all abstract methods
 * inherited from the {@link print.PrinterTemplate} class.
 * 
 * @version 1.25 beta
 * @author Diogo Nunes
 */
public class TerminalPrinter extends PrinterTemplate {

	/**
	 * Constructor (using defaults): creates a Printer with zero level of debug,
	 * timestamping active using format according to ISO 8601.
	 */
	public TerminalPrinter() {
		this(new Builder(0, true));
	}

	/**
	 * Constructor using builder.
	 */
	public TerminalPrinter(Builder builder) {
		setLevel(builder._level);
		setTimestamping(builder._timestampFlag);
		setDateFormat(builder._dateFormat);
	}

	// =========
	//  BUILDER 
	// =========

	/**
	 * Builder pattern: allows the caller to specify the attributes that it
	 * wants to change and keep the default values ​​in the others.
	 */
	public static class Builder {
		//required parameters
		private int _level;
		private boolean _timestampFlag;
		//optional parameters, initialized to default values
		private DateFormat _dateFormat = new SimpleDateFormat(
											"dd/MM/yyyy HH:mm:ss");

		/**
		 * The Printer created uses, by default, timestamping format according to ISO 8601.
		 * @param level specifies the maximum level of debug this printer can print.
		 * @param tsFlag true, if you want a timestamp before each message.
		 * 
		 * @see <a href="http://www.iso.org/iso/catalogue_detail.htm?csnumber=26780">ISO 8601</a>
		 */
		public Builder(int level, boolean tsFlag) {
			_level = level;
			_timestampFlag = tsFlag;
		}

		/**
		 * @param level specifies the maximum level of debug this printer can print.
		 * @return the builder.
		 */
		public Builder level(int level) {
			this._level = level;
			return this;
		}

		/**
		 * @param flag true, if you want a timestamp before each message.
		 * @return the builder.
		 */
		public Builder timestamping(boolean flag) {
			this._timestampFlag = flag;
			return this;
		}

		/**
		 * @param df the printing format of the timestamp.
		 * @return the builder.
		 */
		public Builder withFormat(DateFormat df) {
			this._dateFormat = df;
			return this;
		}

		/**
		 * @return a new instance of TerminalPrinter.
		 */
		public TerminalPrinter build() {
			return new TerminalPrinter(this);
		}

	}


	// =================================
	//  OTHER METHODS (implementations)
	// =================================

	/**
	 * Prints current Time and Date to terminal.
	 */
	@Override
	public void printTimestamp() {
		System.out.print(getDateTime() + " ");		/* time + whitespace */
	}

	/**
	 * Prints a message to terminal.
	 * @param msg The message to print.
	 */
	@Override
	public void print(Object msg) {
		if(isTimestamping())
			printTimestamp();
		System.out.print(msg);
	}

	/**
	 * Prints a message to terminal with a new line at the end.
	 * @param msg The message to print.
	 */
	@Override
	public void println(Object msg) {
		if(isTimestamping())
			printTimestamp();
		System.out.println(msg);
	}

	/**
	 * Prints an error message to terminal.
	 * @param msg The error message to print.
	 */
	@Override
	public void errorPrint(Object msg) {
		if(isTimestamping())
			printTimestamp();
		System.err.print(msg);
	}

	/**
	 * Prints an error message to terminal with a new line at the end.
	 * @param msg The error message to print.
	 */
	@Override
	public void errorPrintln(Object msg) {
		if(isTimestamping())
			printTimestamp();
		System.err.println(msg);
	}

	/**
	 * Prints a debug message to terminal.
	 * @param msg Debug message to print
	 */
	@Override
	public void debugPrint(Object msg) {
		print(msg);
	}

	/**
	 * Prints a debug message to terminal if the printer has enough level of
	 * debug to print that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 */
	@Override
	public void debugPrint(Object msg, int level) {
		if(canPrint(level))
			print(msg);
	}

	/**
	 * Prints a debug message (with a newline at the end) to terminal.
	 * @param msg Debug message to print
	 */
	@Override
	public void debugPrintln(Object msg) {
		println(msg);
	}

	/**
	 * Prints a debug message (with a newline at the end) to terminal if
	 * the printer has enough level of debug to print that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 */
	@Override
	public void debugPrintln(Object msg, int level) {
		if(canPrint(level))
			println(msg);
	}

	/**
	 * @return The text representation of the Printer.
	 */
	@Override
	public String toString() {
		String desc = "TerminalPrinter" + " | level: " + getLevel()
					  + " | timestamping: " + (isTimestamping()
					  	? "active"
					  	: "inactive");
		return desc;
	}

}
