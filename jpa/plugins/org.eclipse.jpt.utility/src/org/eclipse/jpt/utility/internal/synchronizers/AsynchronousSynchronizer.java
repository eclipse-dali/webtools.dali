/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.synchronizers;

import java.util.concurrent.ThreadFactory;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;

/**
 * This synchronizer will perform synchronizations in a separate thread,
 * allowing calls to {@link Synchronizer#synchronize()} to return immediately.
 * <p>
 * <strong>NB:</strong> The client-supplied command should handle any exceptions
 * appropriately (e.g. log the exception and return gracefully so the thread
 * can continue the synchronization process).
 */
public class AsynchronousSynchronizer
	implements Synchronizer
{
	/**
	 * This flag is shared with the synchronization/consumer thread. Setting it to true
	 * will trigger the synchronization to begin or, if the synchronization is
	 * currently executing, to execute again, once the current execution is
	 * complete.
	 */
	final SynchronizedBoolean synchronizeFlag = new SynchronizedBoolean(false);

	/**
	 * Most of the thread-related behavior is delegated to this coordinator.
	 */
	private final ConsumerThreadCoordinator consumerThreadCoordinator;


	// ********** construction **********

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization.
	 * Use simple JDK thread(s) for the synchronization thread(s).
	 * Allow the synchronization thread(s) to be assigned
	 * JDK-generated names.
	 */
	public AsynchronousSynchronizer(Command command) {
		this(command, null, null);
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Allow the synchronization thread(s) to be assigned
	 * JDK-generated names.
	 */
	public AsynchronousSynchronizer(Command command, ThreadFactory threadFactory) {
		this(command, threadFactory, null);
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Assign the synchronization thread(s) the specified
	 * name.
	 * Use simple JDK thread(s) for the synchronization thread(s).
	 */
	public AsynchronousSynchronizer(Command command, String threadName) {
		this(command, null, threadName);
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Assign the synchronization thread(s) the specified
	 * name.
	 */
	public AsynchronousSynchronizer(Command command, ThreadFactory threadFactory, String threadName) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.consumerThreadCoordinator = new ConsumerThreadCoordinator(this.buildConsumer(command), threadFactory, threadName);
	}

	ConsumerThreadCoordinator.Consumer buildConsumer(Command command) {
		return new Consumer(command);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Build and start the synchronization thread, but postpone the first
	 * synchronization until requested, i.e. via a call to
	 * {@link #synchronize()}.
	 * <p>
	 * Note: We don't clear the "synchronize" flag here; so if the flag has
	 * been set <em>before</em> getting here, the first synchronization will
	 * start promptly (albeit, asynchronously).
	 * The "synchronize" flag will be set if:<ul>
	 * <li>{@link #synchronize()} was called after the synchronizer was
	 *     constructed but before {@link #start()} was called; or
	 * <li>{@link #synchronize()} was called after {@link #stop()} was called
	 *     but before {@link #start()} was called (to restart the synchronizer); or
	 * <li>{@link #stop()} was called when there was an outstanding request
	 *     for a synchronization (i.e. the "synchronization" flag was set at
	 *     the time {@link #stop()} was called)
	 * </ul>
	 */
	public void start() {
		this.consumerThreadCoordinator.start();
	}

	/**
	 * Set the "synchronize" flag so the synchronization thread will either<ul>
	 * <li>if the thread is quiesced, start a synchronization immediately, or
	 * <li>if the thread is currently executing a synchronization, execute another
	 *     synchronization once the current synchronization is complete
	 * </ul>
	 */
	public void synchronize() {
		this.synchronizeFlag.setTrue();
	}

	/**
	 * Interrupt the synchronization thread so that it stops executing at the
	 * end of the current synchronization. Suspend the current thread until
	 * the synchronization thread is finished executing. If any uncaught
	 * exceptions were thrown while the synchronization thread was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 */
	public void stop() {
		this.consumerThreadCoordinator.stop();
	}


	// ********** consumer **********

	/**
	 * This implementation of {@link ConsumerThreadCoordinator.Consumer}
	 * will execute the client-supplied "synchronize" command.
	 * It will wait until the shared "synchronize" flag is set to execute the
	 * command. Once the comand is executed, the thread will quiesce until
	 * the flag is set again. If the flag was set during the execution of the
	 * command (either recursively by the command itself or by another thread),
	 * the command will be re-executed immediately. Stop the thread by calling
	 * {@link Thread#interrupt()}.
	 */
	class Consumer
		implements ConsumerThreadCoordinator.Consumer
	{
		/**
		 * The client-supplied command that executes on the
		 * synchronization/consumer thread.
		 */
		private final Command command;

		Consumer(Command command) {
			super();
			this.command = command;
		}

		/**
		 * Wait until the "synchronize" flag is set,
		 * then clear it and allow the "synchronize" command to execute.
		 */
		public void waitForProducer() throws InterruptedException {
			AsynchronousSynchronizer.this.synchronizeFlag.waitToSetFalse();
		}

		/**
		 * Execute the client-supplied command.
		 */
		public void execute() {
			this.command.execute();
		}

	}

}
