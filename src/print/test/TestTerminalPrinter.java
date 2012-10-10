package print.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import print.TerminalPrinter;
import print.exception.InvalidArgumentsException;

/**
 * Tests for TerminalPrinter class.
 * @version 1.25
 * @author Diogo Nunes
 */
public class TestTerminalPrinter extends TestCase {
	
	/* redirects stdout */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/* redirects stderr */
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/* instance of printer to test */
	TerminalPrinter printer = null;

	/**
	 * Redirects standard outputs to PrintStream objects so the output can be
	 * tested/compared later.
	 * @throws Exception
	 */
	@Before
	public void setUp()
		throws Exception
	{
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown()
		throws Exception
	{
		outContent.reset();
		errContent.reset();
		System.setOut(null);
		System.setErr(null);
	}
	
	/**
	 * Test method for {@link print.TerminalPrinter#print(java.lang.String)}.
	 * Test method for {@link print.TerminalPrinter#errorPrint(java.lang.String)}.
	 * @throws InvalidArgumentsException 
	 */
	@Test
	public void testPrint()
		throws InvalidArgumentsException
	{
		//ARRANGE
		printer = new TerminalPrinter.Builder(0, false).build();
		String msg = "Normal/Error ";
				
		// ACT
		printer.print(msg);
		printer.errorPrint(msg);
		
		//ASSERT
		assertTrue(outContent.toString().equals(msg));
		assertTrue(errContent.toString().equals(msg));
	}
	
	/**
	 * Test method for {@link print.TerminalPrinter#debugPrint(java.lang.String, int)}.
	 * @throws InvalidArgumentsException 
	 */
	@Test
	public void testDebugPrintWithLevels()
		throws InvalidArgumentsException
	{
		//ARRANGE
		printer = new TerminalPrinter.Builder(2, false).build();
		String msg = "Debug ";
				
		// ACT
		printer.debugPrint(msg, 2);
		printer.debugPrint(msg, 3);	/* not printed */
		
		//ASSERT
		assertTrue(outContent.toString().equals(msg));
		assertFalse(outContent.toString().equals(msg+msg));
	}
	
	/**
	 * Test method for {@link print.TerminalPrinter#debugPrint(java.lang.String, int)}.
	 * Test method for {@link print.TerminalPrinter#setLevel(int)}.
	 * @throws InvalidArgumentsException 
	 */
	@Test
	public void testDebugPrintChangingLevel()
		throws InvalidArgumentsException
	{
		//ARRANGE
		printer = new TerminalPrinter.Builder(1, false).build();
		String msg = "Debug ";
				
		// ACT
		printer.debugPrint(msg, 2);	/* not printed */
		printer.setLevel(3);
		printer.debugPrint(msg, 2);	
		printer.setLevel(1);
		printer.debugPrint(msg, 3);	/* not printed */
		
		//ASSERT
		assertTrue(outContent.toString().equals(msg));
		assertFalse(outContent.toString().equals(msg+msg));
		assertFalse(outContent.toString().equals(msg+msg+msg));
	}
	
	/**
	 * Test method for {@link print.TerminalPrinter#setDebugging(boolean)}.
	 * Test method for {@link print.TerminalPrinter#canPrint(int)}.
	 * @throws InvalidArgumentsException 
	 */
	@Test
	public void testDebugPrintOnOff()
		throws InvalidArgumentsException
	{
		//ARRANGE
		printer = new TerminalPrinter.Builder(0, false).build();
		String msg = "Debug ";
				
		// ACT
		printer.debugPrint(msg, 0);
		printer.debugPrint(msg, 2);
		printer.setDebugging(false);
		printer.debugPrint(msg, 0);	/* not printed */
		printer.debugPrint(msg, 2);	/* not printed */
		printer.setLevel(1);
		printer.debugPrint(msg, 1);
		printer.debugPrint(msg, 2);	/* not printed */
		
		//ASSERT
		assertTrue(outContent.toString().equals(msg+msg+msg));
	}
	
	
	/**
	 * Test method for {@link print.TerminalPrinter#setTimestamping(boolean)}.
	 * Test method for {@link print.TerminalPrinter#print(java.lang.String)}.
	 * @throws InvalidArgumentsException 
	 */
	@Test
	public void testTimestamping()
		throws InvalidArgumentsException
	{
		//ARRANGE
		printer = new TerminalPrinter.Builder(1, false).build();
		String msg = "Message";
		String timestampedMsg = printer.getDateTime()+" "+msg;
				
		// ACT
		printer.errorPrint(msg);		/* without timestamp */
		printer.setTimestamping(true);
		printer.print(msg);				/* with timestamp */
		
		//ASSERT
		assertTrue(errContent.toString().equals(msg));
		assertTrue(outContent.toString().equals(timestampedMsg));
	}

}
