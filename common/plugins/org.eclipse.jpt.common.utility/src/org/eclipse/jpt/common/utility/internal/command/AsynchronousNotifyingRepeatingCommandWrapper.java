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

import java.util.concurrent.ThreadFactory;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.NotifyingRepeatingCommand;
import org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.common.utility.internal.ListenerList;

/**
 * Extend the asynchronous repeating command to notify listeners
 * when an execution "cycle" is complete; i.e. the command has,
 * for the moment, handled every outstanding "execute" request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * execution "cycle"; since other, unrelated, executions can be
 * triggered concurrently.
 */
public class AsynchronousNotifyingRepeatingCommandWrapper
	extends AsynchronousRepeatingCommandWrapper
	implements NotifyingRepeatingCommand
{
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);

	/**
	 * This handles any exceptions thrown by the listeners.
	 */
	private final ExceptionHandler exceptionHandler;


	// ********** construction **********

	/**
	 * Construct an asynchronous notifying repeating command that executes the
	 * specified command.
	 * Allow the execution thread(s) to be assigned JDK-generated names.
	 */
	public AsynchronousNotifyingRepeatingCommandWrapper(Command command, ExceptionHandler exceptionHandler) {
		super(command, exceptionHandler);
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * Construct an asynchronous notifying repeating command that executes the
	 * specified command.
	 * Assign the synchronization thread(s) the specified name.
	 */
	public AsynchronousNotifyingRepeatingCommandWrapper(Command command, String threadName, ExceptionHandler exceptionHandler) {
		super(command, threadName, exceptionHandler);
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * Construct an asynchronous  repeating command that executes the
	 * specified command.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Assign the synchronization thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousNotifyingRepeatingCommandWrapper(Config config) {
		super(config);
		this.exceptionHandler = config.getExceptionHandler();
	}

	/**
	 * Construct an asynchronous notifying repeating command that executes the
	 * specified command.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Assign the synchronization thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousNotifyingRepeatingCommandWrapper(Command command, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super(command, threadFactory, threadName, exceptionHandler);
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * Build a consumer that will let us know when the execution thread has
	 * quiesced.
	 */
	@Override
	ConsumerThreadCoordinator.Consumer buildConsumer(Command command) {
		return new NotifyingConsumer(command);
	}


	// ********** NotifyingRepeatingCommand implementation **********

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Notify our listeners.
	 */
	/* CU private */ void executionQuiesced() {
		for (Listener listener : this.listenerList.getListeners()) {
			try {
				listener.executionQuiesced(this);
			} catch (Throwable ex) {
				// we could let the ConsumerThreadCoordinator handle these;
				// but then the loop would be stopped with the first exception...
				this.exceptionHandler.handleException(ex);
			}
		}
	}


	// ********** consumer **********

	/**
	 * Extend {@link AsynchronousRepeatingCommandWrapper.Consumer}
	 * to notify the repeating command when execution has quiesced
	 * (i.e. the command has finished executing and there are no further
	 * requests for execution).
	 * Because execution is asynchronous, no other thread will be able to
	 * initiate another execution until the command's listeners have been
	 * notified. Note also, the command's listeners can, themselves,
	 * trigger another execution (by directly or indirectly calling
	 * {@link org.eclipse.jpt.common.utility.command.Command#execute()});
	 * but this execution will not occur until <em>after</em> all the
	 * listeners have been notified.
	 */
	/* CU private */ class NotifyingConsumer
		extends Consumer
	{
		NotifyingConsumer(Command command) {
			super(command);
		}

		@Override
		public void consume() {
			super.consume();
			// hmmm - we will notify listeners even when we our thread is "interrupted";
			// that seems ok...  ~bjv
			if (AsynchronousNotifyingRepeatingCommandWrapper.this.executeFlag.isFalse()) {
				AsynchronousNotifyingRepeatingCommandWrapper.this.executionQuiesced();
			}
		}

	}
}
