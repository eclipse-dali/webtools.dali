/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
 * This command will execute repeatedly the minimum
 * number of times. The assumption is the command's effects are
 * cumulative(?); i.e. the cumulative result of multiple executions of the
 * command is no different than the result of a single execution of the command.
 * Once the command is executing, any further requests to execute simply trigger
 * a re-execution of the command once it has finished its current execution.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface RepeatingCommand
	extends Command
{
	/**
	 * Start the command, allowing it to begin executing with the next call to
	 * {@link #execute()}.
	 * @exception IllegalStateException when the command is not stopped
	 */
	void start();

	/**
	 * Stop the command; ignore further calls to {@link #execute()}.
	 * @exception IllegalStateException when the command executor is not started
	 */
	void stop() throws InterruptedException;


	/**
	 * Singleton implementation of the repeating command interface that will do
	 * nothing when executed.
	 */
	final class Null
		implements RepeatingCommand, Serializable
	{
		public static final RepeatingCommand INSTANCE = new Null();
		public static RepeatingCommand instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void start() {
			// do nothing
		}
		public void execute() {
			// do nothing
		}
		public void stop() {
			// do nothing
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
