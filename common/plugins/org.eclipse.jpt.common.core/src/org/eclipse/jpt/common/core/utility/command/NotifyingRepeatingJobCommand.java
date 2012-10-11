/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import java.io.Serializable;
import java.util.EventListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Extend the repeating job command to support listeners that are notified
 * when an execution "cycle" is complete; i.e. the job command has,
 * for the moment, handled every execution request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * execution "cycle"; since other, unrelated, executions can be triggered
 * concurrently, causing the "cycle" to continue.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface NotifyingRepeatingJobCommand
	extends RepeatingJobCommand
{
	/**
	 * Add the specified listener.
	 */
	void addListener(Listener listener);

	/**
	 * Remove the specified listener.
	 */
	void removeListener(Listener listener);


	// ********** listener **********

	/**
	 * Interface implemented by listeners to be notified whenever the
	 * job command has quiesced.
	 */
	public interface Listener
		extends EventListener
	{
		/**
		 * The specified job command has quiesced.
		 */
		IStatus executionQuiesced(JobCommand command, IProgressMonitor monitor);
	}


	// ********** null singleton **********

	/**
	 * Singleton implementation of the notifying repeating command interface that
	 * will do nothing when executed.
	 */
	final class Null
		implements NotifyingRepeatingJobCommand, Serializable
	{
		public static final NotifyingRepeatingJobCommand INSTANCE = new Null();
		public static NotifyingRepeatingJobCommand instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void start() {
			// do nothing
		}
		public IStatus execute(IProgressMonitor monitor) {
			return Status.OK_STATUS;
		}
		public void stop() {
			// do nothing
		}
		public void addListener(Listener listener) {
			// do nothing
		}
		public void removeListener(Listener listener) {
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
