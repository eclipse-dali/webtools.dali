/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Various utility methods.
 */
@SuppressWarnings("nls")
public final class Tools {

	// ********** object comparison **********

	/**
	 * Return whether the specified values are equal, with the appropriate
	 * <code>null</code> checks.
	 */
	public static boolean valuesAreEqual(Object value1, Object value2) {
		return (value1 == null) ?
				(value2 == null) :
				((value2 != null) && value1.equals(value2));
	}

	/**
	 * Return whether the specified values are different, with the appropriate
	 * <code>null</code> checks.
	 */
	public static boolean valuesAreDifferent(Object value1, Object value2) {
		return (value1 == null) ?
				(value2 != null) :
				((value2 == null) || ! value1.equals(value2));
	}


	// ********** System properties **********

	/**
	 * Return whether the current JVM is Sun's (or Oracle's).
	 */
	public static boolean jvmIsSun() {
		return jvmIs("Sun") || jvmIs("Oracle");
	}

	/**
	 * Return whether the current JVM is IBM's.
	 */
	public static boolean jvmIsIBM() {
		return jvmIs("IBM");
	}

	private static boolean jvmIs(String jvmVendorName) {
		return System.getProperty("java.vendor").startsWith(jvmVendorName);
	}

	/**
	 * Return whether the current operating system is Microsoft Windows.
	 */
	public static boolean osIsWindows() {
		return osIs("Windows");
	}

	/**
	 * Return whether the current operating system is Linux.
	 */
	public static boolean osIsLinux() {
		return osIs("Linux");
	}

	private static boolean osIs(String osName) {
		return System.getProperty("os.name").indexOf(osName) != -1;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private Tools() {
		super();
		throw new UnsupportedOperationException();
	}
}
