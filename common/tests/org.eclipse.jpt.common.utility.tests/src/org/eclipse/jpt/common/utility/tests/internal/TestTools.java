/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.SortedSet;
import java.util.TreeSet;
import junit.framework.TestCase;
import junit.framework.TestFailure;
import junit.framework.TestResult;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.junit.Assert;

/**
 * Various tools that can be used by test cases.
 */
@SuppressWarnings("nls")
public final class TestTools {

	/**
	 * Convenience method that handles {@link InterruptedException}.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Execute the specified command. If it throws an exception, re-execute it
	 * repeatedly until it executes without an exception.
	 * There will be a one-second delay between each execution.
	 * This is useful when calling third-party code that intermittently throws
	 * exceptions but will <em>eventually</em> execute successfully (e.g. when
	 * there are problems deleting files).
	 */
	public static void execute(TestCommand command) {
		execute(command, -1);
	}

	/**
	 * Execute the specified command. If it throws an exception, re-execute it
	 * repeatedly until it executes without an exception. Execute the command
	 * up to the specified number of attempts.
	 * There will be a one-second delay between each execution.
	 * This is useful when calling third-party code that intermittently throws
	 * exceptions but will <em>eventually</em> execute successfully (e.g. when
	 * there are problems deleting files).
	 */
	public static void execute(TestCommand command, int attempts) {
		execute(command, attempts, 1000);
	}

	/**
	 * Execute the specified command. If it throws an exception, re-execute it
	 * repeatedly until it executes without an exception. Execute the command
	 * up to the specified number of attemptsl with specified delay between
	 * each execution.
	 * This is useful when calling third-party code that intermittently throws
	 * exceptions but will <em>eventually</em> execute successfully (e.g. when
	 * there are problems deleting files).
	 */
	public static void execute(TestCommand command, int attempts, long delay) {
		for (int i = 1; i <= attempts; i++) {  // NB: start with 1
			try {
				command.execute();
				return;
			} catch (Exception ex) {
				if ((attempts != -1) && (i == attempts)) {
					throw new RuntimeException("attempts: " + i, ex);
				}
				sleep(delay);
			}
		}
	}

	/**
	 * Check whether the specified char array contains the same characters as
	 * the expected string. Throw an exception if they do not.
	 */
	public static void assertEquals(String expected, char[] actual) {
		Assert.assertEquals(expected, (actual == null) ? null : new String(actual));
	}

	/**
	 * Check whether the specified byte array contains the same characters as
	 * the expected string. Throw an exception if they do not.
	 */
	public static void assertEquals(String expected, byte[] actual) {
		Assert.assertEquals(expected, (actual == null) ? null : new String(actual));
	}

	/**
	 * Test an object's implementation of {@link java.io.Serializable} by serializing the
	 * specified object to a byte array; then de-serializing the byte array and
	 * returning the resultant object.
	 */
	public static <T> T serialize(T o) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baOutStream = new ByteArrayOutputStream(2000);
		ObjectOutputStream outStream = new ObjectOutputStream(baOutStream);
		outStream.writeObject(o);
		outStream.close();

		ByteArrayInputStream baInStream = new ByteArrayInputStream(baOutStream.toByteArray());
		ObjectInputStream inStream = new ObjectInputStream(baInStream);
		T o2 = readObject(inStream);
		inStream.close();

		return o2;
	}

	@SuppressWarnings("unchecked")
	private static <T> T readObject(ObjectInput objectInput) throws IOException, ClassNotFoundException {
		return (T) objectInput.readObject();
	}

	/**
	 * Redirect std out and std err to the specified stream.
	 */
	public static void redirectSystemStreamsTo(OutputStream outputStream) {
		redirectSystemStreamsTo(new PrintStream(outputStream));
	}

	/**
	 * Redirect std out and std err to the specified stream.
	 */
	public static void redirectSystemStreamsTo(PrintStream printStream) {
		System.setOut(printStream);
		System.setErr(printStream);
	}

	/**
	 * Sort and print out all the current Java System properties on the
	 * console.
	 */
	public static void printSystemProperties() {
		synchronized (System.out) {
			printSystemPropertiesOn(System.out);
		}
	}

	/**
	 * Sort and print out all the current Java System properties on the
	 * specified print stream.
	 */
	public static void printSystemPropertiesOn(PrintStream stream) {
		SortedSet<String> sortedKeys = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		for (Object key : System.getProperties().keySet()) {
			sortedKeys.add((String) key);
		}
		for (String key : sortedKeys) {
			stream.print(key);
			stream.print(" => ");
			stream.print(System.getProperty(key));
			stream.println();
		}
	}

	/**
	 * Execute the specified test and return a text output of its results.
	 */
	public static String execute(TestCase testCase) {
		long start = System.currentTimeMillis();
		TestResult result = testCase.run();
		long end = System.currentTimeMillis();

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.print(testCase.getName());
		writer.print(": ");
		if (result.wasSuccessful()) {
			writer.println("OK");
		} else {
			TestFailure failure = null;
			if (result.failures().hasMoreElements()) {
				failure = result.failures().nextElement();
			} else {
				failure = result.errors().nextElement();
			}
			failure.thrownException().printStackTrace(writer);
		}
		writer.print("elapsed time: ");
		long elapsed = end - start;
		writer.print(elapsed / 1000L);
		writer.println(" sec.");
		return stringWriter.toString();
	}

	/**
	 * Clear out all the instance variable of the specified test case, allowing
	 * the various test fixtures to be garbage-collected. Typically this is
	 * called in the test case's implementation of {@link TestCase#tearDown()}.
	 */
	public static void clear(TestCase testCase) throws IllegalAccessException {
		for (Class<?> clazz = testCase.getClass(); clazz != TestCase_class; clazz = clazz.getSuperclass()) {
			for (Field field : clazz.getDeclaredFields()) {
				// leave primitives alone - they don't get garbage-collected,
				// and we can't set them to null...
				if (field.getType().isPrimitive()) {
					continue;
				}
				// leave static fields alone (?)
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				field.set(testCase, null);
			}
		}
	}

	private static final Class<TestCase> TestCase_class = TestCase.class;

	/**
	 * Return the value of the specified class's <code>DEBUG</code> constant.
	 */
	public static boolean debug(Class<?> clazz) {
		Boolean debug = (Boolean) ClassTools.get(clazz, "DEBUG");
		return debug.booleanValue();
	}

	/**
	 * Verify the specified class's <code>DEBUG</code> constant is set to
	 * <code>false</code>.
	 */
	public static void assertFalseDEBUG(Class<?> clazz) {
		Assert.assertFalse("Recompile with \"DEBUG = false\": " + clazz.getName(), debug(clazz));
	}

	/**
	 * Workaround for a JUnit bug: JUnit does not configure the testing {@link Thread}
	 * with a context class loader. This should probably happen in
	 * {@link junit.textui.TestRunner#doRun(junit.framework.Test)}, just before starting the thread.
	 */
	public static void setUpJUnitThreadContextClassLoader() {
		Thread.currentThread().setContextClassLoader(TestTools.class.getClassLoader());
	}

	/**
	 * suppressed constructor
	 */
	private TestTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
