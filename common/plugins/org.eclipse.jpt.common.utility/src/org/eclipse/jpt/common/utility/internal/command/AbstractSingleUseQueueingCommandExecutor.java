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
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.LinkedQueue;

/**
 * This is a command executor that queues up any commands that are
 * {@link #execute(Command) executed} before the executor has been
 * {@link #start() started} or while the executor is
 * {@link #suspend() suspended}. These commands will be
 * executed once the executor is {@link #start() started} or
 * {@link #resume() resumed}. Once the executor is {@link #start() started},
 * all commands are executed <em>synchronously</em>.
 * Once the executor is {@link #stop() stopped}, no further commands will be
 * executed; and the executor can<em>not</em> be {@link #start() restarted}.
 */
public abstract class AbstractSingleUseQueueingCommandExecutor<E extends StatefulCommandContext>
	implements StatefulCommandContext
{
	protected final E commandExecutor;
	private State state;
	private LinkedQueue<Command> queue = new LinkedQueue<Command>();

	private enum State {
		PRE_START,
		ACTIVE,
		SUSPENDED,
		DEAD
	}


	protected AbstractSingleUseQueueingCommandExecutor(E commandExecutor) {
		super();
		if (commandExecutor == null) {
			throw new NullPointerException();
		}
		this.commandExecutor = commandExecutor;
		this.state = State.PRE_START;
	}

	/**
	 * Start the command executor, executing all the commands that were
	 * queued up since the executor was first constructed.
	 */
	public synchronized void start() {
		if (this.state != State.PRE_START) {
			throw this.buildISE();
		}
		this.commandExecutor.start();
		while ( ! this.queue.isEmpty()) {
			this.commandExecutor.execute(this.queue.dequeue());
		}
		this.state = State.ACTIVE;
	}

	public void execute(Command command) {
		if (this.commandIsToBeExecuted(command)) {
			this.commandExecutor.execute(command);
		}
	}

	/**
	 * If the command executor is active (i.e. it has been
	 * {@link #start() started}), execute the specified command.
	 * If the command executor has not been {@link #start() started} or it is
	 * {@link #suspend() suspended}, queue the
	 * command to be executed when the executor is {@link #start() started} or
	 * {@link #resume() resumed}.
	 * If the command executor is dead (i.e. it has been
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
	 * Suspend the command executor. Any further requests to
	 * {@link #execute(Command) execute} a command will result in the command
	 * being queued up to be executed once the command executor is
	 * {@link #resume() resumed}.
	 */
	public synchronized void suspend() {
		if (this.state != State.ACTIVE) {
			throw this.buildISE();
		}
		this.state = State.SUSPENDED;
	}

	/**
	 * Resume the command executor, executing all the commands that were
	 * queued up since the executor was {@link #suspend() suspended}.
	 */
	public synchronized void resume() {
		if (this.state != State.SUSPENDED) {
			throw this.buildISE();
		}
		while ( ! this.queue.isEmpty()) {
			this.commandExecutor.execute(this.queue.dequeue());
		}
		this.state = State.ACTIVE;
	}

	/**
	 * Stop the command executor. Any queued commands (i.e. the commands
	 * {@link #execute(Command) executed} while the command executor had not
	 * been {@link #start() started} or was
	 * {@link #suspend() suspended}) will be ignored.
	 * Any further requests to
	 * {@link #execute(Command) execute} a command will be ignored.
	 * The command executor <em>cannot</em> be {@link #start() restarted},
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

		this.commandExecutor.stop();
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
