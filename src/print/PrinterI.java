package print;

/**
 * Every class that implements this interface is called a Printer. A Printer
 * must print normal, error and debug messages. It must offer a choice to print
 * or not a timestamp before each of those messages. To print a message means
 * that the message is written somewhere: a terminal, a file or something else.
 * A Printer must have a level of debug and it acts like a visibility scope. A
 * debug message may also have a debug level. If so, it means that the debug
 * message should only be displayed if the Printer has enough level of debug,
 * i.e. the Printer must have a level of debug equal of greater than the level
 * of the message. By convention, if the Printer has a level of debug of zero
 * than all debug messages should be printed.
 * 
 * @version 1.25 beta
 * @author Diogo Nunes
 */
public interface PrinterI {

	/**
	 * @return current level of debug.
	 */
	int getLevel();

	/**
	 * Changes the level of debug. This level represents the maximum level of
	 * the debug messages displayed by the printer.
	 * @param level The new level of debug (should be >= 0).
	 */
	void setLevel(int level);

	/**
	 * @return the current date and time using some format specified by the
	 * class which implements this interface.
	 */
	String getDateTime();

	/**
	 * Prints current Time and Date.
	 */
	void printTimestamp();

	/**
	 * Usual System.out.print
	 * @param msg Message to display
	 */
	void print(Object msg);

	/**
	 * Usual System.out.println
	 * @param msg Message to display
	 */
	void println(Object msg);

	/**
	 * Usual System.err.print
	 * @param msg Error message to display
	 */
	void errorPrint(Object msg);

	/**
	 * Usual System.err.println
	 * @param msg Error message to display
	 */
	void errorPrintln(Object msg);

	/**
	 * Prints a debug message to terminal.
	 * @param msg Debug message to print
	 */
	void debugPrint(Object msg);

	/**
	 * Prints a debug message to terminal if the printer has enough level of
	 * debug to print that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 */
	void debugPrint(Object msg, int level);

	/**
	 * Prints a debug message (with a newline at the end) to terminal.
	 * @param msg Debug message to print
	 */
	void debugPrintln(Object msg);

	/**
	 * Prints a debug message (with a newline at the end) to terminal if
	 * the printer has enough level of debug to print that message.
	 * @param msg Debug message to print
	 * @param level Level of debug needed to print msg
	 */
	void debugPrintln(Object msg, int level);

}
