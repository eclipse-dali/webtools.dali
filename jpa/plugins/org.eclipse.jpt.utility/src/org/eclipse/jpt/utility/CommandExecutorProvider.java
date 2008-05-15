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
 * Yet another level of indirection to allow clients to control
 * how a command is executed by the server
 * (e.g. dispatching the command to the UI thread).
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CommandExecutorProvider {

	/**
	 * Return the appropriate command executor.
	 */
	CommandExecutor getCommandExecutor();


	/**
	 * Straightforward implementation of the command executor provider
	 * interface the returns the default command executor.
	 */
	final class Default implements CommandExecutorProvider {
		public static final CommandExecutorProvider INSTANCE = new Default();
		public static CommandExecutorProvider instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public CommandExecutor getCommandExecutor() {
			return CommandExecutor.Default.instance();
		}
		@Override
		public String toString() {
			return "CommandExecutorProvider.Default";
		}
	}

}
