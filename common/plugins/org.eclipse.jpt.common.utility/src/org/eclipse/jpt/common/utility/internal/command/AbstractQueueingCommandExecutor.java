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

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandExecutor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.LinkedQueue;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * This is a command executor that queues up any commands
 * that are {@link #execute(Command) executed} while the executor is
 * {@link #stop() stopped}. These commands will be
 * executed when the executor is {@link #start() started}.
 */
public abstract class AbstractQueueingCommandExecutor<E extends StatefulCommandExecutor>
	implements StatefulCommandExecutor
{
	protected final E commandExecutor;
	protected final SynchronizedBoolean active = new SynchronizedBoolean(false);
	private LinkedQueue<Command> queue = new LinkedQueue<Command>();


	protected AbstractQueueingCommandExecutor(E commandExecutor) {
		super();
		if (commandExecutor == null) {
			throw new NullPointerException();
		}
		this.commandExecutor = commandExecutor;
	}

	/**
	 * Start the command executor, executing all the commands that were
	 * queued up while the executor was {@link #stop() stopped}.
	 */
	public synchronized void start() {
		if (this.active.isTrue()) {
			throw new IllegalStateException("Not stopped."); //$NON-NLS-1$
		}
		this.commandExecutor.start();
		while ( ! this.queue.isEmpty()) {
			this.commandExecutor.execute(this.queue.dequeue());
		}
		this.active.setTrue();
	}

	/**
	 * If the command executor is active, execute the specified command;
	 * otherwise, queue the command to be executed once the executor is
	 * {@link #start() started}.
	 */
	public void execute(Command command) {
		if (this.commandIsToBeExecuted(command)) {
			this.commandExecutor.execute(command);
		}
	}

	/**
	 * Return whether the command executor is active and, if it is <em>in</em>active,
	 * place the specified command in the queue for later execution.
	 */
	private synchronized boolean commandIsToBeExecuted(Command command) {
		if (this.active.isFalse()) {
			this.queue.enqueue(command);
			return false;
		}
		return true;
	}

	/**
	 * Stop the command executor. Any further requests to
	 * {@link #execute(Command) execute} a command will result in the command
	 * being queued up to be executed once the command executor is
	 * {@link #start() restarted}.
	 */
	public synchronized void stop() throws InterruptedException {
		if (this.active.isFalse()) {
			throw new IllegalStateException("Not started."); //$NON-NLS-1$
		}
		this.active.setFalse();
		this.commandExecutor.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.queue);
	}
}
