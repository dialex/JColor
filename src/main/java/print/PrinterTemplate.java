package print;

import java.text.DateFormat;
import java.util.Date;


/**
 * This class is a template of a Printer, hence it contains what is common to
 * each Printer implementation offered by the library. Each Printer of this
 * package should extend this class and thus implement Printer interface.
 *  
 * @version 1.15 beta
 * @author Diogo Nunes
 */
public abstract class PrinterTemplate implements PrinterI {

	private int _level;					/* the current level of debug		*/
	private DateFormat _dateFormat;		/* the format of date and time		*/
	private boolean _timestamp;			/* true if timestamping is active 	*/


	// =====================
	//  GET and SET METHODS
	// =====================

	@Override
	public int getLevel() {
		return _level;
	}

	@Override
	public void setLevel(int level) {
		_level = level;
	}

	@Override
	public String getDateTime() {
		Date timedate = new Date();
		return _dateFormat.format(timedate);
	}

	/**
	 * Changes the date format used by Printer when timestamping.
	 * @param dateFormat the Printer should use.
	 */
	public void setDateFormat(DateFormat dateFormat) {
		_dateFormat = dateFormat;
	}

	/**
	 * @return True if timestamp printing is active, false otherwise.
	 */
	public boolean isTimestamping() {
		return _timestamp;
	}

	/**
	 * Enables/disables timestamping on all messages.
	 * @param timestampFlag true if you want timestamp before each message.
	 */
	public void setTimestamping(boolean timestampFlag) {
		_timestamp = timestampFlag;
	}

	/**
	 * Enables/disables printing of all debug messages.
	 * @param debugFlag
	 * 		true if you want all debug messages to be always printed;
	 * 		false if you want the printer to stop any debug messages.
	 * 		
	 */
	public void setDebugging(boolean debugFlag) {
		if(debugFlag)
			setLevel(0);	/* prints all debug messages */
		else
			setLevel(-1);	/* prints no debug messages  */
	}


	// ===============
	//  OTHER METHODS
	// ===============

	/**
	 * @param level of debug needed to print a message.
	 * @return true if Printer can print a message with that level of debug.
	 */
	protected boolean canPrint(int level) {
		return (getLevel() == 0) || (getLevel() >= level);
	}

}
