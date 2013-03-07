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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * A <em>synchronizing</em> command can be used to coordinate the execution of
 * the {@link Thread thread} that <em>creates</em> the <em>synchronizing</em>
 * command (Thread A, the "primary" thread) with another {@link Thread thread}
 * that <em>executes</em> the <em>synchronizing</em> command (Thread B, the
 * "secondary" thread).
 * <p>
 * Typically, Thread A dispatches {@Command commands} to a
 * {@link org.eclipse.jpt.common.utility.command.CommandContext command
 * executor}) that executes the {@Command commands} asynchronously on Thread B.
 * A <em>synchronizing</em> command allows Thread A to
 * dispatch a <em>synchronizing</em> command to Thread B and suspend until
 * the {@link #command} held by the <em>synchronizing</em> command
 * has executed (on <em>Thread B</em>). (This can be useful when Thread B is the
 * UI {@link Thread thread} and the {@link #command} must modify a UI widget.)
 * Once the {@link #command} held by the <em>synchronizing</em> command has
 * executed, Thread B suspends and notifies Thread A to proceed. Thread A can
 * optionally execute yet another {@Command command} (on <em>Thread A</em>).
 * Once Thread A has executed its command, it must notify Thread B that it may
 * proceed (now independently of Thread A).
 * <p>
 * <strong>Timeouts:</strong> If Thread A specifies a time-limit
 * ({@link #waitForExecution(long)}) for its suspension, and a timeout occurs
 * (i.e. it takes too long for the <em>synchronizing</em> command to begin
 * executing or its {@link #command} takes too long to execute),
 * the {@link #command} held by the <em>synchronizing</em> command will
 * <em>still be executed</em>; but Thread A will not be notified when the
 * execution is complete. [It seems reasonable that the {@link #command} always
 * execute, since, if it is too long-running, it will execute anyway....]
 * <p>
 * <strong>Synchronous Execution:</strong> If the <em>synchronizing</em> command
 * is <em>created</em> and <em>executed</em> on the same {@link Thread thread},
 * the assumption is Thread A and Thread B are the same {@link Thread thread}
 * and all {@Command commands} are executing synchronously (e.g. during testing
 * or "batch" executing).
 * In that situation, the <em>synchronizing</em> command will <em>not</em>
 * suspend its {@link Thread thread}.
 */
public class SynchronizingCommand
	implements Command
{
	private final Thread thread = Thread.currentThread();  // Thread A
	private final SynchronizedBoolean flag = new SynchronizedBoolean(false);
	private volatile boolean expired = false;  // guarded by #flag
	private final Command command;


	/**
	 * Construct a <em>synchronizing</em> command that does <em>not</em> execute
	 * a {@link #command} on the "secondary" {@link Thread thread} (Thread B),
	 * but simply suspends that thread until the <em>synchronizing</em> command
	 * is {@link #release() released} by the "primary" thread (Thread A).
	 */
	public SynchronizingCommand() {
		this(Command.Null.instance());
	}

	/**
	 * Construct a <em>synchronizing</em> command that executes the specified
	 * a command on the "secondary" {@link Thread thread} (Thread B),
	 * and then suspends that thread until the <em>synchronizing</em> command
	 * is {@link #release() released} by the "primary" thread (Thread A).
	 */
	public SynchronizingCommand(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	/**
	 * Typically called by a
	 * {@link org.eclipse.jpt.common.utility.command.CommandContext command
	 * executor} executing Thread B.
	 * <p>
	 * The "synchronizing" command has reached the front of the command queue
	 * and is executing on Thread B.
	 * Execute the wrapped command and return control to the Thread A.
	 * Suspend Thread B until Thread A resets the flag.
	 * <p>
	 * If the "synchronizing" command is executed (synchronously) on the same
	 * thread where the command was created (i.e. Thread A),
	 * it will simply set the flag appropriately and return.
	 * Otherwise, the current thread would stall indefinitely.
	 * <p>
	 * If Thread A has timed out, skip any further synchronization.
	 */
	public void execute() {
		this.command.execute();

		synchronized (this.flag) {
			if (this.expired) {
				// reset the 'expired' flag and exit (don't wait for Thread A)
				this.expired = false;
				return;
			}
			// Thread A is still waiting - notify it
			this.flag.setTrue();
		}

		if (Thread.currentThread() != this.thread) {
			try {
				this.flag.waitUntilFalse();
			} catch (InterruptedException ex) {
				// something has interrupted Thread B (the "secondary" thread);
				// it must not want us to wait for Thread A to finish executing...
				Thread.currentThread().interrupt();  // set the Thread's interrupt status
			}
		}
	}

	/**
	 * Called by Thread A to wait on Thread B to execute its command.
	 * @see #waitForExecution(long)
	 */
	public void waitForExecution() throws InterruptedException {
		this.flag.waitUntilTrue();
	}

	/**
	 * Called by Thread A to wait on Thread B to execute its command.
	 * @see #waitForExecution()
	 */
	public boolean waitForExecution(long timeout) throws InterruptedException {
		synchronized (this.flag) {
			if (this.flag.waitUntilTrue(timeout) ) {
				return true;
			}
			this.expired = true;
			return false;
		}
	}

	/**
	 * Called by Thread A to indicate it has executed its command.
	 * At this point the threads are independent and no longer synchronized
	 * with each other.
	 */
	public void release() {
		this.flag.setFalse();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.flag);
	}
}
