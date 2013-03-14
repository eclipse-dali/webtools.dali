package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;


/**
 * Utility for testing purposes
 * @author i057508
 *
 */
public class Utils {

	/**
	 * User space system variable key.
	 */
	private static Semaphore writeToConsolePermission = new Semaphore(1);
	static SimpleDateFormat sdf = new SimpleDateFormat(">> [HH:mm:ss.S] ");
	
	public final static String NEW_LINE = (System.getProperty("line.separator") != null  ? System.getProperty("line.separator") : "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$




	/** A constant for java validation error markers*/
	
	/**
	 * the plug-in ID of this plug-in
	 */
	public static final String COMMON_TEST_PLUGIN_ID = "com.sap.core.tools.eclipse.server.test.common";

	/**
	 * Issues a message into the system console into a friendly format. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message no new line will be added.
	 * @param message the message to be issued
	 */
	public static void printFormatted(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print(sdf.format(new Date()) + message);
		writeToConsolePermission.release();
	}

	/**
	 * Issues a message into the system console. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message no new line will be added.
	 * @param message the message to be issued
	 */
	public static void print(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print(message);
		writeToConsolePermission.release();
	}

	/**
	 * Issues a message into the system console into a friendly format. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message a new line will be added.
	 * @param message the message to be issued
	 */
	public static void printlnFormatted(String message) {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(sdf.format(new Date()) + message);
		writeToConsolePermission.release();
	}

	/**
	 * Utility for printing into the system console
	 * @param message a message to be printed
	 * @param t an exception to be printed
	 */
	public static void printlnFormatted(String message, Throwable t) {
		StringBuilder buf = new StringBuilder();
		buf.append(message);

		if (t != null  && t.getStackTrace() != null) {
			buf.append(NEW_LINE);
			buf.append("Exception:").append(t.getMessage());
			buf.append(NEW_LINE);
			buf.append("Stacktrace:");
			buf.append(NEW_LINE);
			buf.append(traceToString(t.getStackTrace()));
		}

		printlnFormatted(buf.toString());
	}

	/**
	 * Issues a message into the system console. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message a new line will be added.
	 * @param message the message to be issued
	 */
	public static void println(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(message);
		writeToConsolePermission.release();
	}

	/**
	 * Indicates a start of a test into the system console into a use friendly format.
	 * @param testName the name of the test.
	 */
	public static void sayTestStarted(String testName) {

		Utils.println("\n----------------------------------------------------------------------------");
		Utils.printlnFormatted("Test [" +  testName + "] started.");
		Utils.println("----------------------------------------------------------------------------");
	}

	/**
	 * Indicates an end of a test into the system console into a use friendly format.
	 * @param testName the name of the test.
	 */
	public static void sayTestFinished(String testName) {

		Utils.println("\n----------------------------------------------------------------------------");
		Utils.printlnFormatted("Test [" +  testName + "] finished.");
		Utils.println("----------------------------------------------------------------------------");
	}

	/**
	 * Converts stacktrace into a string
	 * @param trace the stacktrace
	 * @return the converted stacktrace
	 */
	public static String traceToString(StackTraceElement[] trace) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < trace.length; i++) {
			builder.append(trace[i]).append("\n");
		}
		return builder.toString();
	}
}

