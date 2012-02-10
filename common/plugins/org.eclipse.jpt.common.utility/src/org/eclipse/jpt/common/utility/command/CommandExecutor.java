/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.command;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * This interface allows clients to control how a command is executed.
 * This is useful when the server provides the command but the client provides
 * the context (e.g. the client would like to dispatch the command to the UI
 * thread).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CommandExecutor {

	/**
	 * Execute the specified command, synchronously or asynchronously.
	 * The commands themselves must be executed in the order in which
	 * they are passed to the command executor (at least when passed
	 * from clients executing on the same thread).
	 */
	void execute(Command command);


	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements CommandExecutor, Serializable
	{
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
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}


	/**
	 * Singleton implementation of the command executor interface
	 * that ignores any commands.
	 */
	final class Inactive
		implements CommandExecutor, Serializable
	{
		public static final CommandExecutor INSTANCE = new Inactive();
		public static CommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Inactive() {
			super();
		}
		public void execute(Command command) {
			// do nothing
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
