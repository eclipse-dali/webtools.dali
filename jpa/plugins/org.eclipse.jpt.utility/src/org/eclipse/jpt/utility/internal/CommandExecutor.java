/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * This interface allows clients to control how a command is executed
 * (e.g. dispatching the command to the UI thread).
 */
public interface CommandExecutor {

	/**
	 * Execute the specified command.
	 */
	void execute(Command command);


	/**
	 * Straightforward implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default implements CommandExecutor {
		public static final CommandExecutor INSTANCE = new Default();
		public static CommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(Command command) {
			command.execute();
		}
		@Override
		public String toString() {
			return "CommandExecutor.Default";
		}
	}

}
