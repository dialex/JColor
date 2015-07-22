package print.color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.fusesource.jansi.AnsiConsole;

import print.color.Ansi.*;

/**
 * This class is an implementation of the Colored Printer interface. It works
 * correctly on both UNIX terminal and WINDOWS command console. It implements
 * all abstract methods inherited from the {@link print.PrinterTemplate} class.
 * All output is sent to terminal using the PrintStream provided by
 * {@link AnsiConsole}.out.
 * 
 * @version 1.25 beta
 * @author Diogo Nunes
 */
public class ColoredPrinterWIN extends ColoredPrinterTemplate {

	/**
	 * Constructor (using defaults): creates a Colored Printer with no format,
	 * zero level of debug and timestamping active according to ISO 8601.
	 */
	public ColoredPrinterWIN() {
		this(new Builder(0, true));
	}

	/**
	 * Constructor using builder.
	 */
	public ColoredPrinterWIN(Builder builder) {
		setLevel(builder._level);
		setTimestamping(builder._timestampFlag);
		setDateFormat(builder._dateFormat);
		setAttribute(builder._attribute);
		setForegroundColor(builder._foregroundColor);
		setBackgroundColor(builder._backgroundColor);
	}


	// =========
	//  BUILDER
	// =========
	
	/**
	 * Builder pattern: allows the caller to specify the attributes that it
	 * wants to change and keep the default values in the others.
	 */
	public static class Builder {
		//required parameters
		private int _level;
		private boolean _timestampFlag;
		//optional parameters - initialized to default values
		private Attribute _attribute = Attribute.NONE;
		private FColor _foregroundColor = FColor.NONE;
		private BColor _backgroundColor = BColor.NONE;
		private DateFormat _dateFormat = new SimpleDateFormat(
											"dd/MM/yyyy HH:mm:ss");

		/**
		 * The Colored Printer created, by default, has no format. So it's just
		 * like a usual Printer {@link print.PrinterI}.
		 * @param level specifies the maximum level of debug this printer can print.
		 * @param tsFlag true, if you want a timestamp before each message.
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
		 * @param attr specifies the attribute component of the printing format.
		 * @return the builder.
		 */
		public Builder attribute(Attribute attr) {
			this._attribute = attr;
			return this;
		}

		/**
		 * @param fg specifies the foreground color of the printing format.
		 * @return the builder.
		 */
		public Builder foreground(FColor fg) {
			this._foregroundColor = fg;
			return this;
		}

		/**
		 * @param bg specifies the background color of the printing format.
		 * @return the builder.
		 */
		public Builder background(BColor bg) {
			this._backgroundColor = bg;
			return this;
		}

		/**
		 * @return a new instance of ColoredPrinterNIX.
		 */
		public ColoredPrinterWIN build() {
			return new ColoredPrinterWIN(this);
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
		AnsiConsole.out.print(generateCode() + getDateTime() + " ");
	}

	/**
	 * Prints a message to terminal.
	 * @param msg The message to print.
	 */
	@Override
	public void print(Object msg) {		
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode());
		}
		AnsiConsole.out.print(msg);
	}

	@Override
	public void print(Object msg, Attribute attr, FColor fg, BColor bg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode(attr, fg, bg));
		}
		AnsiConsole.out.print(msg);
	}

	/**
	 * Prints a message to terminal with a new line at the end.
	 * @param msg The message to print.
	 */
	@Override
	public void println(Object msg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode());
		}
		AnsiConsole.out.println(msg);
	}

	@Override
	public void println(Object msg, Attribute attr, FColor fg, BColor bg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode(attr, fg, bg));
		}
		AnsiConsole.out.println(msg);
	}

	/**
	 * Prints an error message to terminal.
	 * @param msg The error message to print.
	 */
	@Override
	public void errorPrint(Object msg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode());
		}
		AnsiConsole.err.print(msg);
	}

	@Override
	public void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode(attr, fg, bg));
		}
		AnsiConsole.err.print(msg);
	}

	/**
	 * Prints an error message to terminal with a new line at the end.
	 * @param msg The error message to print.
	 */
	@Override
	public void errorPrintln(Object msg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode());
		}
		AnsiConsole.err.println(msg);
	}

	@Override
	public void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
		if(isTimestamping()) {
			printTimestamp();
		} else {
			AnsiConsole.out.print(generateCode(attr, fg, bg));
		}
		AnsiConsole.err.println(msg);
	}

	/**
	 * Prints a debug message to terminal.
	 * @param msg Debug message to print
	 */
	@Override
	public void debugPrint(Object msg) {
		print(msg);
	}

	@Override
	public void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
		print(msg, attr, fg, bg);
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

	@Override
	public void debugPrint(Object msg, int level,
						   Attribute attr, FColor fg, BColor bg) {
		if(canPrint(level))
			print(msg, attr, fg, bg);
	}

	/**
	 * Prints a debug message (with a newline at the end) to terminal.
	 * @param msg Debug message to print
	 */
	@Override
	public void debugPrintln(Object msg) {
		println(msg);
	}

	@Override
	public void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
		println(msg, attr, fg, bg);
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

	@Override
	public void debugPrintln(Object msg, int level,
							 Attribute attr, FColor fg, BColor bg) {
		if(canPrint(level))
			println(msg, attr, fg, bg);
	}

	/**
	 * @return The text representation of the Printer.
	 */
	@Override
	public String toString() {
		String desc = "ColoredPrinterWIN" + " | level: " + getLevel()
						+ " | timestamping: " + (isTimestamping()
						  ? "active"
						  : "inactive")
						+ " | Attribute: " + getAttribute().name()
						+ " | Foreground color: " + getForegroundColor().name()
						+ " | Background color: " + getBackgroundColor().name();
		return desc;
	}

}
