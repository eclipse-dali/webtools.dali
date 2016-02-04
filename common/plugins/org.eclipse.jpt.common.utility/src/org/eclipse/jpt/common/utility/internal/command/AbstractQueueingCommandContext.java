/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.queue.LinkedQueue;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * This is a command context that queues up any commands
 * that are {@link #execute(Command) executed} while the context is
 * {@link #stop() stopped}. These commands will be
 * executed when the context is {@link #start() started}.
 */
public abstract class AbstractQueueingCommandContext<E extends StatefulCommandContext>
	implements StatefulCommandContext
{
	protected final E commandContext;
	protected final SynchronizedBoolean active = new SynchronizedBoolean(false);
	private LinkedQueue<Command> queue = new LinkedQueue<>();


	protected AbstractQueueingCommandContext(E commandContext) {
		super();
		if (commandContext == null) {
			throw new NullPointerException();
		}
		this.commandContext = commandContext;
	}

	/**
	 * Start the command context, executing all the commands that were
	 * queued up while the context was {@link #stop() stopped}.
	 */
	public synchronized void start() {
		if (this.active.isTrue()) {
			throw new IllegalStateException("Not stopped."); //$NON-NLS-1$
		}
		this.commandContext.start();
		while ( ! this.queue.isEmpty()) {
			this.commandContext.execute(this.queue.dequeue());
		}
		this.active.setTrue();
	}

	/**
	 * If the command context is active, execute the specified command;
	 * otherwise, queue the command to be executed once the context is
	 * {@link #start() started}.
	 */
	public void execute(Command command) {
		if (this.commandIsToBeExecuted(command)) {
			this.commandContext.execute(command);
		}
	}

	/**
	 * Return whether the command context is active and, if it is <em>in</em>active,
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
	 * Stop the command context. Any further requests to
	 * {@link #execute(Command) execute} a command will result in the command
	 * being queued up to be executed once the command context is
	 * {@link #start() restarted}.
	 */
	public synchronized void stop() throws InterruptedException {
		if (this.active.isFalse()) {
			throw new IllegalStateException("Not started."); //$NON-NLS-1$
		}
		this.active.setFalse();
		this.commandContext.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.queue);
	}
}
