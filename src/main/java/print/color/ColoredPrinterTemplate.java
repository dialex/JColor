package print.color;

import print.PrinterTemplate;
import print.color.Ansi.*;

/**
 * This class is a template of a Colored Printer, hence it contains what is
 * common to each Colored Printer implementation offered by the library. Each
 * Colored Printer of this package should extend this class and thus implement
 * {@link print.ColoredPrinter} interface.
 * 
 * @version 1.2 beta
 * @author Diogo Nunes
 */
public abstract class ColoredPrinterTemplate extends PrinterTemplate
	implements ColoredPrinterI
{
	/* this three components define the printing format of all messages */
	private Attribute _attribute;
	private FColor _foregroundColor;
	private BColor _backgroundColor;


	// =====================
	//  GET and SET METHODS
	// =====================

	/**
	 * @return the current attribute; every message will be printed formated
	 * with this attribute.
	 */
	protected Attribute getAttribute() {
		return _attribute;
	}

	/**
	 * @return the current foreground color; every message will be printed with
	 * this foreground color.
	 */
	protected FColor getForegroundColor() {
		return _foregroundColor;
	}

	/**
	 * @return the current background color; every message will be printed with
	 * this background color.
	 */
	protected BColor getBackgroundColor() {
		return _backgroundColor;
	}

	@Override
	public void setAttribute(Attribute a) {
		_attribute = a;
	}

	@Override
	public void setForegroundColor(FColor c) {
		_foregroundColor = c;
	}

	@Override
	public void setBackgroundColor(BColor c) {
		_backgroundColor = c;
	}


	// ===============
	//  OTHER METHODS
	// ===============

	@Override
	public void clear() {
		setAttribute(Attribute.CLEAR);
		setForegroundColor(FColor.NONE);
		setBackgroundColor(BColor.NONE);
		print("");		//refresh terminal line, so that the changes take immediate effect
	}

	@Override
	public String generateCode() {
		String code = Ansi.PREFIX
					  + getAttribute().toString() + Ansi.SEPARATOR
					  + getForegroundColor().toString() + Ansi.SEPARATOR
					  + getBackgroundColor().toString()
					  + Ansi.POSTFIX;
		return code;
	}
	
	@Override
	public String generateCode(Attribute attr, FColor fg, BColor bg) {
		String code = Ansi.PREFIX
					  + attr.toString() + Ansi.SEPARATOR
					  + fg.toString() + Ansi.SEPARATOR
					  + bg.toString()
					  + Ansi.POSTFIX;
		return code;
	}

}
