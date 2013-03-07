/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.command;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This interface extends the normal command context; it allows the client
 * to specify when a command <em>must</em> be executed synchronously.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ExtendedCommandContext
	extends CommandContext
{
	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command executor (at least when passed
	 * from clients executing on the same thread).
	 * @see #execute(Command)
	 */
	void waitToExecute(Command command) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed in the allotted time;
	 * return <code>false</code> if a time-out occurred and the command was
	 * <em>not</em> executed.
	 * If the time-out is zero, wait indefinitely.
	 * <p>
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command executor (at least when passed
	 * from clients executing on the same thread).
	 * @see #execute(Command)
	 */
	boolean waitToExecute(Command command, long timeout) throws InterruptedException;


	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements ExtendedCommandContext, Serializable
	{
		public static final ExtendedCommandContext INSTANCE = new Default();
		public static ExtendedCommandContext instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(Command command) {
			command.execute();
		}
		public void waitToExecute(Command command) {
			command.execute();
		}
		public boolean waitToExecute(Command command, long timeout) {
			command.execute();
			return true;
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
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
		implements ExtendedCommandContext, Serializable
	{
		public static final ExtendedCommandContext INSTANCE = new Inactive();
		public static ExtendedCommandContext instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Inactive() {
			super();
		}
		public void execute(Command command) {
			// do nothing
		}
		public void waitToExecute(Command command) {
			// do nothing
		}
		public boolean waitToExecute(Command command, long timeout) {
			// do nothing
			return true;
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
