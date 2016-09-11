package com.diogonunes.jcdp.bw.api;

import java.text.DateFormat;
import java.util.Date;

/**
 * This class is a template of a Printer, hence it contains what is common to
 * each Printer implementation offered by the library. Each Printer of this
 * package should extend this class and thus implement Printer interface.
 *
 * @author Diogo Nunes
 * @version 1.15 beta
 */
public abstract class AbstractPrinter implements IPrinter {

    private int _level; /* the current level of debug */
    private DateFormat _dateFormat; /* the format of date and time */
    private boolean _timestamping;

    // =====================
    // GET and SET METHODS
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
    public String getDateFormatted() {
        return _dateFormat.format(getDate());
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    /**
     * Changes the date format used by Printer when timestamping.
     *
     * @param dateFormat the Printer should use.
     */
    public void setDateFormat(DateFormat dateFormat) {
        _dateFormat = dateFormat;
    }

    /**
     * @return True if timestamp printing is active, false otherwise.
     */
    public boolean isLoggingTimestamps() {
        return _timestamping;
    }

    /**
     * Enables/disables timestamping on all messages.
     *
     * @param timestampFlag true if you want timestamp before each message.
     */
    public void setTimestamping(boolean timestampFlag) {
        _timestamping = timestampFlag;
    }

    /**
     * @return True if timestamp printing is active, false otherwise.
     */
    public boolean isLoggingDebug() {
        return getLevel() >= 0;
    }

    /**
     * Enables/disables printing of all debug messages.
     *
     * @param debugFlag true if you want all debug messages to be always printed;
     *                  false if you want the printer to stop any debug messages.
     */
    public void setDebugging(boolean debugFlag) {
        if (debugFlag)
            setLevel(0); /* prints all debug messages */
        else
            setLevel(-1); /* prints no debug messages */
    }

    // ===============
    // OTHER METHODS
    // ===============

    /**
     * @param level of debug needed to print a message.
     * @return true if Printer can print a message with that level of debug.
     */
    protected boolean canPrint(int level) {
        return (getLevel() == 0) || (getLevel() >= level);
    }
}