/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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

/**
 * various tools that can be used by test cases
 */
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
	 * test an object's implementation of Serializable by serializing the
	 * specified object to a byte array; then de-serializing the byte array and
	 * returning the resultant object
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
	 * redirect std out and std err to the specified stream
	 */
	public static void redirectSystemStreamsTo(OutputStream outputStream) {
		redirectSystemStreamsTo(new PrintStream(outputStream));
	}

	/**
	 * redirect std out and std err to the specified stream
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
			stream.print(" => "); //$NON-NLS-1$
			stream.print(System.getProperty(key));
			stream.println();
		}
	}

	/**
	 * execute the specified test and return a text output of its results
	 */
	public static String execute(TestCase testCase) {
		long start = System.currentTimeMillis();
		TestResult result = testCase.run();
		long end = System.currentTimeMillis();

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.print(testCase.getName());
		writer.print(": "); //$NON-NLS-1$
		if (result.wasSuccessful()) {
			writer.println("OK"); //$NON-NLS-1$
		} else {
			TestFailure failure = null;
			if (result.failures().hasMoreElements()) {
				failure = result.failures().nextElement();
			} else {
				failure = result.errors().nextElement();
			}
			failure.thrownException().printStackTrace(writer);
		}
		writer.print("elapsed time: "); //$NON-NLS-1$
		long elapsed = end - start;
		writer.print(elapsed / 1000L);
		writer.println(" sec."); //$NON-NLS-1$
		return stringWriter.toString();
	}

	/**
	 * Clear out all the instance variable of the specified test case, allowing
	 * the various test fixtures to be garbage-collected. Typically this is
	 * called in the #tearDown() method.
	 */
	public static void clear(TestCase testCase) throws IllegalAccessException {
		for (Class<?> clazz = testCase.getClass(); clazz != TestCase_class; clazz = clazz.getSuperclass()) {
			for (Field field : clazz.getDeclaredFields()) {
				// leave primitives alone - they don't get garbage-collected, and we can't set them to null...
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
	 * Workaround for a JUnit bug: JUnit does not configure the testing Thread
	 * with a context class loader. This should probably happen in
	 * TestRunner.doRunTest(Test), just before starting the thread.
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
