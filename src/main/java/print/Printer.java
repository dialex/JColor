package print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import print.exception.InvalidArgumentsException;

/**
 * If you want to create a Printer this is the only class you should use. This
 * is your Printers Factory and it abstracts the creation of a Printer and its
 * real implementation. It offers two types of constructors, one for static and
 * other for dynamic printers. If you use the static constructor you must choose
 * an implementation {@link print.Printer.Types} offered by the library. If you
 * use the dynamic constructor you must pass as argument an instance of any
 * class that implements {@link print.PrinterI} interface.
 * 
 * @version 1.25 beta
 * @author Diogo Nunes
 */
public class Printer implements PrinterI {

	/**
	 * Types of Printer's implementations offered:
	 * <ul>
	 * <li>TERM for a Terminal Printer;</li>
	 * <li>FILE for a File Printer;</li>
	 * </ul>
	 */
	public enum Types { TERM, FILE }

	/* object with printer's implementation */
	private PrinterTemplate _impl;


	// ===========================
	//  CONSTRUCTORS and BUILDERS 
	// ===========================

	/**
	 * Constructor of dynamic printers.
	 * @param implementation of {@link print.PrinterI}
	 */
	public Printer(PrinterTemplate implementation) {
		setImpl(implementation);
	}

	/**
	 * 
	 * @throws InvalidArgumentsException if at least one argument is incorrect.
	 */
	public Printer(Builder b)
		throws InvalidArgumentsException
	{
		switch(b._type) {
			case TERM:
				setImpl(new TerminalPrinter.Builder(b._level, b._tsFlag)
						.withFormat(b._dateFormat)
						.build());
				break;
			case FILE:
				System.err.println("This type of printer isn't supported... yet!");
				throw new InvalidArgumentsException();
			default:
				throw new InvalidArgumentsException();
		}
	}

	/**
	 * Builder pattern: allows the caller to specify the attributes that it
	 * wants to change and keep the default values in the others.
	 */
	public static class Builder {
		//required parameters
		private Types _type;
		//optional parameters, initialized to default values
		private int _level = 0;
		private boolean _tsFlag = true;
		private DateFormat _dateFormat = new SimpleDateFormat(
											"dd/MM/yyyy HH:mm:ss");

		/**
		 * Constructor of static printers. The printer returned is one of the
		 * implementations offered by the library. By default the Printer
		 * created has zero level of debug, timestamping is active with format
		 * according to ISO 8601.
		 * @param type of implementation wanted, use one of {@link Types}
		 */
		public Builder(Types type) {
			_type = type;
		}

		public Builder type(Types t) {
			this._type = t;
			return this;
		}

		public Builder level(int level) {
			this._level = level;
			return this;
		}

		public Builder timestamping(boolean flag) {
			this._tsFlag = flag;
			return this;
		}

		public Builder withFormat(DateFormat df) {
			this._dateFormat = df;
			return this;
		}

		/**
		 * @return a new instance of a Printer.
		 * @throws InvalidArgumentsException if at least one argument is incorrect. 
		 */
		public Printer build()
			throws InvalidArgumentsException
		{
			return new Printer(this);
		}

	}


	// =====================
	//  GET and SET METHODS
	// =====================

	private PrinterTemplate getImpl() {
		return _impl;
	}

	private void setImpl(PrinterTemplate impl) {
		_impl = impl;
	}


	// =======================================
	//  INTERFACE METHODS call implementation
	// =======================================

	@Override
	public int getLevel() {
		return getImpl().getLevel();
	}

	@Override
	public void setLevel(int level) {
		getImpl().setLevel(level);
	}

	@Override
	public String getDateTime() {
		return getImpl().getDateTime();
	}

	@Override
	public void printTimestamp() {
		getImpl().printTimestamp();
	}

	@Override
	public void print(Object msg) {
		getImpl().print(msg);
	}

	@Override
	public void println(Object msg) {
		getImpl().println(msg);
	}

	@Override
	public void errorPrint(Object msg) {
		getImpl().errorPrint(msg);
	}

	@Override
	public void errorPrintln(Object msg) {
		getImpl().errorPrintln(msg);
	}

	@Override
	public void debugPrint(Object msg) {
		getImpl().debugPrint(msg);
	}

	@Override
	public void debugPrint(Object msg, int level) {
		getImpl().debugPrint(msg, level);
	}

	@Override
	public void debugPrintln(Object msg) {
		getImpl().debugPrintln(msg);
	}

	@Override
	public void debugPrintln(Object msg, int level) {
		getImpl().debugPrintln(msg, level);
	}

	@Override
	public String toString() {
		return getImpl().toString();
	}

}
