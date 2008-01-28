/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * This interface defines a simple API for allowing "pluggable"
 * string matchers that can be configured with a pattern string
 * then used to determine what strings match the pattern.
 */
public interface StringMatcher {

	/**
	 * Set the pattern string used to determine future
	 * matches. The format and semantics of the pattern
	 * string are determined by the contract between the
	 * client and the server.
	 */
	void setPatternString(String patternString);

	/**
	 * Return whether the specified string matches the
	 * established pattern string. The semantics of a match
	 * is determined by the contract between the
	 * client and the server.
	 */
	boolean matches(String string);


	final class Null implements StringMatcher {
		public static final StringMatcher INSTANCE = new Null();
		public static StringMatcher instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void setPatternString(String patternString) {
			// ignore the pattern string
		}
		public boolean matches(String string) {
			// everything is a match
			return true;
		}
		@Override
		public String toString() {
			return "StringMatcher.Null";
		}
	}

}
