/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jpt.common.core.utility.command.NotifyingRepeatingJobCommand;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.core.utility.command.JobCommandExecutor;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ListenerList;

/**
 * @see org.eclipse.jpt.common.utility.internal.command.NotifyingRepeatingCommandWrapper
 */
public class NotifyingRepeatingJobCommandWrapper
	extends RepeatingJobCommandWrapper
	implements NotifyingRepeatingJobCommand
{
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct a notifying repeating command wrapper that executes the
	 * specified command. Any exceptions thrown by the command or listener
	 * will be handled by the specified exception handler.
	 */
	public NotifyingRepeatingJobCommandWrapper(JobCommand command, ExceptionHandler exceptionHandler) {
		super(command, exceptionHandler);
	}

	/**
	 * Construct a notifying repeating command wrapper that executes the
	 * specified command and uses the specified command executor to execute the
	 * wrapped command whenever it is not already executing.
	 * Any exceptions thrown by the command or listener will be handled by the
	 * specified exception handler.
	 */
	public NotifyingRepeatingJobCommandWrapper(JobCommand command, JobCommandExecutor startCommandExecutor, ExceptionHandler exceptionHandler) {
		super(command, startCommandExecutor, exceptionHandler);
	}


	// ********** listeners **********

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}


	// ********** override **********

	@Override
	/* private protected */ IStatus executeCommand(IProgressMonitor monitor) {
		IStatus status = super.executeCommand(monitor);
		if (this.state.isQuiesced()) {
			// hmmm - we will notify listeners even when we are "stopped"; that seems OK...
			this.executionQuiesced(monitor);
		}
		return status;
	}

	/**
	 * Notify our listeners.
	 */
	private void executionQuiesced(IProgressMonitor monitor) {
		for (Listener listener : this.listenerList.getListeners()) {
			this.notifyListener(listener, monitor);
		}
	}

	private void notifyListener(Listener listener, IProgressMonitor monitor) {
		try {
			listener.executionQuiesced(this, monitor);
		} catch (OperationCanceledException ex) {
			throw ex;  // seems reasonable...
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}
}
