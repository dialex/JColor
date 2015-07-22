package print.color;

/**
 * This class stores enums used to generate a Ansi escape code. There is one
 * enum for each component of the print format: Attribute, Foreground Color,
 * Background Color. </br>
 * 
 * Ansi escape codes have the following syntax:
 * 		<ESC>[{attr1};...;{attrn}m </br>
 * Ansi escape codes are modeled by this class using the following syntax:
 * 		PREFIX{attr1}SEPARATOR...SEPARATOR{attrn}POSTFIX </br>
 * 
 * @see <a href="http://ascii-table.com/ansi-escape-sequences.php">Ansi escape codes</a>
 * 
 * @version 1.2
 * @author Diogo Nunes
 */
public class Ansi {

	/** Every Ansi escape code begins with this PREFIX. */
	public static final String PREFIX = "\033[";
	/** Every attribute is separated by this SEPARATOR. */
	public static final String SEPARATOR = ";";
	/** Every Ansi escape code end with this POSTFIX. */
	public static final String POSTFIX = "m";

	/**
	 * Enumeration of each Ansi code for Foreground Color.
	 */
	public enum FColor {

		BLACK	("30"),
		RED		("31"),
		GREEN	("32"),
		YELLOW	("33"),
		BLUE	("34"),
		MAGENTA	("35"),
		CYAN	("36"),
		WHITE	("37"),
		NONE	("");

		private final String _code;		//Ansi escape code

		/**
		 * Enum's constructor. Associates a code to a Foreground Color.
		 * @param code to associate 
		 */
		FColor(String code) {
			_code = code;
		}

		/**
		 * @return Ansi escape code for that Foreground Color.
		 */
		public String getCode() {
			return _code;
		}

		/**
		 * @return The text representation of the enum (its code).
		 */
		@Override
		public String toString() {
			return getCode();
		}

	}

	/**
	 * Enumeration of each Ansi code for Background Color.
	 */
	public enum BColor {

		BLACK	("40"),
		RED		("41"),
		GREEN	("42"),
		YELLOW	("43"),
		BLUE	("44"),
		MAGENTA	("45"),
		CYAN	("46"),
		WHITE	("47"),
		NONE	("");

		private final String _code;		//Ansi escape code

		/**
		 * Enum's constructor. Associates a code to a Background Color.
		 * @param code to associate 
		 */
		BColor(String code) {
			_code = code;
		}

		/**
		 * @return Ansi escape code for that Foreground Color.
		 */
		public String getCode() {
			return _code;
		}

		/**
		 * @return The text representation of the enum (its code).
		 */
		@Override
		public String toString() {
			return getCode();
		}

	}

	/**
	 * Enumeration of each Ansi code for Attribute.
	 */
	public enum Attribute {

		CLEAR		("0"),
		BOLD		("1"),
		LIGHT		("1"),
		DARK		("2"),
		UNDERLINE	("4"),
		REVERSE		("7"),
		HIDDEN		("8"),
		NONE		("");

		private final String _code;		//Ansi escape code

		/**
		 * Enum's constructor. Associates a code to a Attribute.
		 * @param code to associate 
		 */
		Attribute(String code) {
			_code = code;
		}

		/**
		 * @return Ansi escape code for that attribute.
		 */
		public String getCode() {
			return _code;
		}

		/**
		 * @return The text representation of the enum (its code).
		 */
		@Override
		public String toString() {
			return getCode();
		}

	}

}
