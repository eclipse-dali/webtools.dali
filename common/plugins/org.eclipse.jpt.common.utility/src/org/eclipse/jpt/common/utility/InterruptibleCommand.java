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
 * Simple interface for implementing the GOF Command design pattern
 * and allows for the command to throw an {@link InterruptedException}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jpt.common.utility.Command
 */
public interface InterruptibleCommand {

	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute() throws InterruptedException;


	/**
	 * Singleton implementation of the interruptible command interface that
	 * will throw an interrupted exception when executed.
	 */
	final class Interrupted
		implements InterruptibleCommand, Serializable
	{
		public static final InterruptibleCommand INSTANCE = new Interrupted();
		public static InterruptibleCommand instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Interrupted() {
			super();
		}
		// throw an exception
		public void execute() throws InterruptedException {
			throw new InterruptedException();
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
