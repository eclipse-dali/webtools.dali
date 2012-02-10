/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.NotifyingRepeatingCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandExecutor;
import org.eclipse.jpt.common.utility.internal.ListenerList;

/**
 * <strong>NB:</strong> If another execution cycle is initiated while we are
 * notifying the command's listeners (i.e. the {@link #state} is set
 * to "repeat"), the new
 * execution will not start until all the listeners are notified.
 * <p>
 * <strong>NB2:</strong> The command's listeners can, themselves,
 * trigger another execution (by directly or indirectly calling
 * {@link #execute()});
 * but, again, this execution will not occur until <em>after</em> all the
 * listeners have been notified.
 * <p>
 * <strong>NB3:</strong> Along with any exceptions thrown by the wrapped
 * command, any exceptions thrown by the command's listeners will
 * be passed to the {@link #exceptionHandler exception handler}.
 */
public class NotifyingRepeatingCommandWrapper
	extends RepeatingCommandWrapper
	implements NotifyingRepeatingCommand
{
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct a notifying repeating command wrapper that executes the
	 * specified command. Any exceptions thrown by the command or listener
	 * will be handled by the specified exception handler.
	 */
	public NotifyingRepeatingCommandWrapper(Command command, ExceptionHandler exceptionHandler) {
		super(command, exceptionHandler);
	}

	/**
	 * Construct a notifying repeating command wrapper that executes the specified
	 * command and uses the specified command executor to execute the wrapped
	 * command whenever it is not already executing.
	 * Any exceptions thrown by the command or listener will be handled by the
	 * specified exception handler.
	 */
	public NotifyingRepeatingCommandWrapper(Command command, CommandExecutor startCommandExecutor, ExceptionHandler exceptionHandler) {
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
	/* private protected */ void executeCommand() {
		super.executeCommand();
		if (this.state.isQuiesced()) {
			// hmmm - we will notify listeners even when we are "stopped"; that seems OK...
			this.executionQuiesced();
		}
	}

	/**
	 * Notify our listeners.
	 */
	private void executionQuiesced() {
		for (Listener listener : this.listenerList.getListeners()) {
			try {
				listener.executionQuiesced(this);
			} catch (Throwable ex) {
				this.exceptionHandler.handleException(ex);
			}
		}
	}
}
