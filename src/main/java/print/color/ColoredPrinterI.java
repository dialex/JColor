package print.color;

import print.PrinterI;
import print.color.Ansi.*;

/**
 * Every class that implements this interface is called a Colored Printer. A
 * Colored Printer is an extension of a Printer hence, plus everything a
 * Printer can do, this Colored Printer must offer a set of methods to print in
 * a terminal with format. This format is specified by three components
 * (check {@link print.color.Ansi}):Attribute, Foreground Color, Background Color.
 * Every Colored Printer must store internally a current format. Every print
 * must reflect this format. It must also offer ways to print a message with a
 * format that momentously overrides the current format.
 * 
 * @version 1.2 beta
 * @author Diogo Nunes
 */
public interface ColoredPrinterI extends PrinterI {

	/**
	 * @param attr specifies the "look" of the message
	 */
	void setAttribute(Attribute attr);

	/**
	 * @param foreground specifies the message's foreground color,
	 * 			i.e. the font's color
	 */
	void setForegroundColor(FColor foreground);

	/**
	 * @param background specifies the message's background color
	 */
	void setBackgroundColor(BColor background);

	/**
	 * Clears the current format and sets it to terminal's default format.
	 */
	void clear();

	/**
	 * @return the ansi code which contains all three components' codes.
	 * This is the code used to tell the terminal the format of the message.
	 */
	String generateCode();

	/**
	 * @param attr specifies the overriding attribute
	 * @param foreground specifies the foreground color
	 * @param background specifies the background color
	 * @return the ansi code which contains all three components' codes
	 */
	String generateCode(Attribute attr, FColor foreground, BColor background);

	/**
	 * Usual System.out.print overriding current format.
	 * @param msg Message to display
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void print(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Usual System.out.println overriding current format.
	 * @param msg Message to display.
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void println(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Usual System.err.print overriding current format. 
	 * @param msg Error message to display
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Usual System.err.println overriding current format.
	 * @param msg Error message to display
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Prints a debug message to terminal overriding current format.
	 * @param msg
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Prints a debug message to terminal, overriding current format, if the
	 * printer has enough level of debug to print that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void debugPrint(Object msg, int level,
					Attribute attr, FColor fg, BColor bg);

	/**
	 * Prints a debug message (with a newline at the end and overriding current
	 * format) to terminal.
	 * @param msg Debug message to print
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg);

	/**
	 * Prints a debug message (with a newline at the end and overriding current
	 * format) to terminal if the printer has enough level of debug to print
	 * that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 * @param attr specifies the overriding attribute
	 * @param fg specifies the foreground color
	 * @param bg specifies the background color
	 */
	void debugPrintln(Object msg, int level,
					  Attribute attr, FColor fg, BColor bg);

}
