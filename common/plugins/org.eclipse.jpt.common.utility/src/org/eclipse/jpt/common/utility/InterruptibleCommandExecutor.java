/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * This interface allows clients to control how an interruptible command is executed.
 * This is useful when the server provides the command but the client provides
 * the context (e.g. the client would like to dispatch the command to the UI
 * thread).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jpt.common.utility.CommandExecutor
 */
public interface InterruptibleCommandExecutor {

	/**
	 * Execute the specified command.
	 */
	void execute(InterruptibleCommand command) throws InterruptedException;


	/**
	 * Singleton implementation of the interruptible command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements InterruptibleCommandExecutor, Serializable
	{
		public static final InterruptibleCommandExecutor INSTANCE = new Default();
		public static InterruptibleCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(InterruptibleCommand command) throws InterruptedException {
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
}
