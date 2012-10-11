/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Simple interface for implementing the GOF Command design pattern in an
 * Eclipse job.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JobCommand
	extends InterruptibleJobCommand
{
	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 * The command should check, as appropriate, whether the specified progress
	 * monitor is {@link IProgressMonitor#isCanceled() "canceled"}; if it is,
	 * the command should return a result
	 * status of severity {@link IStatus#CANCEL}. The singleton
	 * cancel status {@link Status#CANCEL_STATUS} can be used for
	 * this purpose.
	 * <p>
	 * Nested methods can also check the progress monitor
	 * and, if it is "canceled", throw an
	 * {@link org.eclipse.core.runtime.OperationCanceledException OperationCanceledException}.
	 * 
	 * @see IProgressMonitor#isCanceled()
	 * @see org.eclipse.core.runtime.jobs.Job#run(IProgressMonitor monitor)
	 */
	IStatus execute(IProgressMonitor monitor);


	/**
	 * Singleton implementation of the job command interface that will do
	 * nothing when executed.
	 */
	final class Null
		implements JobCommand, Serializable
	{
		public static final JobCommand INSTANCE = new Null();
		public static JobCommand instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public IStatus execute(IProgressMonitor monitor) {
			return Status.OK_STATUS;
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
	 * Singleton implementation of the job command interface that will throw an
	 * exception when executed.
	 */
	final class Disabled
		implements JobCommand, Serializable
	{
		public static final JobCommand INSTANCE = new Disabled();
		public static JobCommand instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public IStatus execute(IProgressMonitor monitor) {
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
