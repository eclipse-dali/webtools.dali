/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.core.utility.command.JobCommandContext;
import org.eclipse.jpt.common.core.utility.command.NotifyingRepeatingJobCommand;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;

/**
 * @see org.eclipse.jpt.common.utility.internal.command.NotifyingRepeatingCommandWrapper
 */
public class NotifyingRepeatingJobCommandWrapper
	extends RepeatingJobCommandWrapper
	implements NotifyingRepeatingJobCommand
{
	private final ListenerList<Listener> listenerList = ModelTools.listenerList();


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
	 * specified command and uses the specified command context to execute the
	 * wrapped command whenever it is not already executing.
	 * Any exceptions thrown by the command or listener will be handled by the
	 * specified exception handler.
	 */
	public NotifyingRepeatingJobCommandWrapper(JobCommand command, JobCommandContext startCommandContext, ExceptionHandler exceptionHandler) {
		super(command, startCommandContext, exceptionHandler);
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
		if (status.getSeverity() == IStatus.CANCEL) {
			this.executionCanceled();
		}
		else if (this.state.isQuiesced()) {
			this.executionQuiesced();
		}
		return status;
	}

	/**
	 * Notify our listeners. All listeners are notified. There is no way to
	 * cancel the notifications (e.g. via a monitor or exception).
	 */
	private void executionQuiesced() {
		for (Listener listener : this.listenerList) {
			this.notifyListenerQuiesced(listener);
		}
	}

	private void notifyListenerQuiesced(Listener listener) {
		try {
			listener.executionQuiesced(this);
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	/**
	 * Notify our listeners. All listeners are notified. There is no way to
	 * cancel the notifications (e.g. via a monitor or exception).
	 */
	private void executionCanceled() {
		for (Listener listener : this.listenerList) {
			this.notifyListenerCanceled(listener);
		}
	}

	private void notifyListenerCanceled(Listener listener) {
		try {
			listener.executionCanceled(this);
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}
}
