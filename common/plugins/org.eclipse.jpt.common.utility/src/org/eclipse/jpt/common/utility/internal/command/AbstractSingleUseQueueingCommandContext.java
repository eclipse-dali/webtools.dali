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

/**
 * This is a command context that queues up any commands that are
 * {@link #execute(Command) executed} before the context has been
 * {@link #start() started} or while the context is
 * {@link #suspend() suspended}. These commands will be
 * executed once the context is {@link #start() started} or
 * {@link #resume() resumed}. Once the context is {@link #start() started},
 * all commands are executed <em>synchronously</em>.
 * Once the context is {@link #stop() stopped}, no further commands will be
 * executed; and the context can<em>not</em> be {@link #start() restarted}.
 */
public abstract class AbstractSingleUseQueueingCommandContext<E extends StatefulCommandContext>
	implements StatefulCommandContext
{
	protected final E commandContext;
	private State state;
	private LinkedQueue<Command> queue = new LinkedQueue<>();

	private enum State {
		PRE_START,
		ACTIVE,
		SUSPENDED,
		DEAD
	}


	protected AbstractSingleUseQueueingCommandContext(E commandContext) {
		super();
		if (commandContext == null) {
			throw new NullPointerException();
		}
		this.commandContext = commandContext;
		this.state = State.PRE_START;
	}

	/**
	 * Start the command context, executing all the commands that were
	 * queued up since the context was first constructed.
	 */
	public synchronized void start() {
		if (this.state != State.PRE_START) {
			throw this.buildISE();
		}
		this.commandContext.start();
		while ( ! this.queue.isEmpty()) {
			this.commandContext.execute(this.queue.dequeue());
		}
		this.state = State.ACTIVE;
	}

	public void execute(Command command) {
		if (this.commandIsToBeExecuted(command)) {
			this.commandContext.execute(command);
		}
	}

	/**
	 * If the command context is active (i.e. it has been
	 * {@link #start() started}), execute the specified command.
	 * If the command context has not been {@link #start() started} or it is
	 * {@link #suspend() suspended}, queue the
	 * command to be executed when the context is {@link #start() started} or
	 * {@link #resume() resumed}.
	 * If the command context is dead (i.e. it has been
	 * {@link #stop() stopped}), ignore the command.
	 */
	private synchronized boolean commandIsToBeExecuted(Command command) {
		switch (this.state) {
			case ACTIVE:
				// execute the command outside the lock
				return true;
			case PRE_START:
			case SUSPENDED:
				this.queue.enqueue(command);
				return false;
			case DEAD:
				// ignore
				return false;
			default:
				throw this.buildISE();
		}
	}

	/**
	 * Suspend the command context. Any further requests to
	 * {@link #execute(Command) execute} a command will result in the command
	 * being queued up to be executed once the command context is
	 * {@link #resume() resumed}.
	 */
	public synchronized void suspend() {
		if (this.state != State.ACTIVE) {
			throw this.buildISE();
		}
		this.state = State.SUSPENDED;
	}

	/**
	 * Resume the command context, executing all the commands that were
	 * queued up since the context was {@link #suspend() suspended}.
	 */
	public synchronized void resume() {
		if (this.state != State.SUSPENDED) {
			throw this.buildISE();
		}
		while ( ! this.queue.isEmpty()) {
			this.commandContext.execute(this.queue.dequeue());
		}
		this.state = State.ACTIVE;
	}

	/**
	 * Stop the command context. Any queued commands (i.e. the commands
	 * {@link #execute(Command) executed} while the command context had not
	 * been {@link #start() started} or was
	 * {@link #suspend() suspended}) will be ignored.
	 * Any further requests to
	 * {@link #execute(Command) execute} a command will be ignored.
	 * The command context <em>cannot</em> be {@link #start() restarted},
	 * {@link #suspend() suspended} or {@link #resume() resumed} once it has
	 * been stopped.
	 */
	public synchronized void stop() throws InterruptedException {
		switch (this.state) {
			case ACTIVE:
				// nothing to do
				break;
			case SUSPENDED:
				// drain the queue
				while ( ! this.queue.isEmpty()) {
					this.queue.dequeue();
				}
				break;
			case PRE_START:
			case DEAD:
			default:
				throw this.buildISE();
		}

		this.commandContext.stop();
		this.state = State.DEAD;
	}

	private IllegalStateException buildISE() {
		return new IllegalStateException(String.valueOf(this.state));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.state);
	}
}
