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
 * Simple interface for implementing a command that takes a single argument.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @parm <T> the type of the object passed to the command
 */
public interface ParameterizedCommand<T>
	extends InterruptibleParameterizedCommand<T>
{
	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute(T argument);


	/**
	 * Singleton implementation of the command interface that will do nothing
	 * when executed.
	 */
	final class Null<S>
		implements ParameterizedCommand<S>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final ParameterizedCommand INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <R> ParameterizedCommand<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void execute(S argument) {
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

	/**
	 * Singleton implementation of the command interface that will throw an
	 * exception when executed.
	 */
	final class Disabled<S>
		implements ParameterizedCommand<S>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final ParameterizedCommand INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R> ParameterizedCommand<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public void execute(S argument) {
			throw new UnsupportedOperationException();
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
