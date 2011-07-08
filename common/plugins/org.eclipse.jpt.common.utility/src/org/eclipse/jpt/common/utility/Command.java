/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
 * Simple interface for implementing the GOF Command design pattern,
 * and it doesn't carry the baggage of {@link java.lang.Runnable}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Command
	extends InterruptibleCommand
{
	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute();


	/**
	 * Singleton implementation of the command interface that will do nothing
	 * when executed.
	 */
	final class Null
		implements Command, Serializable
	{
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
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	/**
	 * Singleton implementation of the command interface that will throw an
	 * exception when executed.
	 */
	final class Disabled
		implements Command, Serializable
	{
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
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
