/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility;

/**
 * Simple interface for implementing the GOF Command design pattern.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Command {

	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute();

	final class Null implements Command {
		public static final Command INSTANCE = new Null();
		public static Command instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void execute() {
			// do nothing
		}
		@Override
		public String toString() {
			return "Command.Null";
		}
	}

	final class Disabled implements Command {
		public static final Command INSTANCE = new Disabled();
		public static Command instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public void execute() {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "Command.Disabled";
		}
	}

}
