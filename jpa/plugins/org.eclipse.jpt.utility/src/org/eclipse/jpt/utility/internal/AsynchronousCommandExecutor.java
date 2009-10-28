/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;

/**
 * This command executor will dispatch commands to be executed in a separate
 * thread, allowing calls to {@link CommandExecutor#execute(Command)} to return
 * immediately.
 * <p>
 * <strong>NB:</strong> The client-supplied commands should handle any
 * exception appropriately (e.g. log the exception and return gracefully) so
 * the command execution thread can continue executing.
 */
public class AsynchronousCommandExecutor
	implements CallbackStatefulCommandExecutor
{
	/**
	 * This command queue is shared with the command execution/consumer thread.
	 * Adding a command to it will trigger the command to be executed by the
	 * command execution thread or, if another command is currently executing,
	 * to execute the new command once the currently executing command has
	 * finished executing.
	 */
	final SynchronizedQueue<Command> commands = new SynchronizedQueue<Command>();

	/**
	 * Most of the thread-related behavior is delegated to this coordinator.
	 */
	private final ConsumerThreadCoordinator consumerThreadCoordinator;

	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct an asynchronous command executor.
	 * Allow the command execution thread(s) to be assigned JDK-generated names.
	 */
	public AsynchronousCommandExecutor() {
		this(null);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Assign the command execution thread(s) the specified name.
	 */
	public AsynchronousCommandExecutor(String threadName) {
		super();
		this.consumerThreadCoordinator = new ConsumerThreadCoordinator(this.buildConsumer(), threadName);
	}

	private ConsumerThreadCoordinator.Consumer buildConsumer() {
		return new Consumer();
	}


	// ********** CallbackStatefulCommandExecutor implementation **********

	/**
	 * Build and start the command execution/consumer thread.
	 * <p>
	 * Note: We don't clear the command queue here; so if a command has been
	 * added to the queue <em>before</em> getting here, the first command will
	 * be executed promptly (albeit, asynchronously).
	 * The command queue will be non-empty if:<ul>
	 * <li>{@link #execute(Command)} was called after the command executor was
	 *     constructed but before {@link #start()} was called; or
	 * <li>{@link #execute(Command)} was called after {@link #stop()} was called
	 *     but before {@link #start()} was called (to restart the command executor); or
	 * <li>{@link #stop()} was called when there were still outstanding commands
	 *     remaining in the command queue
	 * </ul>
	 */
	public void start() {
		this.consumerThreadCoordinator.start();
	}

	/**
	 * Put the specified command on the command queue, to be consumed by the
	 * command execution thread.
	 */
	public void execute(Command command) {
		this.commands.enqueue(command);
	}

	/**
	 * Interrupt the command execution thread so that it stops executing at the
	 * end of the current command. Suspend the current thread until
	 * the command execution thread is finished executing. If any uncaught
	 * exceptions were thrown while the execution thread was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 */
	public void stop() {
		this.consumerThreadCoordinator.stop();
	}

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Notify our listeners.
	 */
	/* private */ void commandExecuted(Command command) {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.commandExecuted(command);
		}
	}


	// ********** consumer **********

	/**
	 * This implementation of {@link ConsumerThreadCoordinator.Consumer}
	 * will execute the commands enqueued by the asynchronous command executor.
	 * It will wait until the shared command queue is non-empty to begin executing the
	 * commands in the queue. Once a comand is executed, the thread will quiesce until
	 * another command is placed in the command queue. If a new command is
	 * enqueued during the execution of another command (either recursively by
	 * the command itself or by another thread),
	 * the new command will be executed immediately after the currently
	 * executing command is finished.
	 * Stop the thread by calling {@link Thread#interrupt()}.
	 */
	class Consumer
		implements ConsumerThreadCoordinator.Consumer
	{
		Consumer() {
			super();
		}

		/**
		 * Wait until a command has been placed in the queue.
		 */
		public void waitForProducer() throws InterruptedException {
			AsynchronousCommandExecutor.this.commands.waitUntilNotEmpty();
		}

		/**
		 * Execute the first command in the queue and notify our listeners.
		 */
		public void execute() {
			Command command = AsynchronousCommandExecutor.this.commands.dequeue();
			command.execute();
			AsynchronousCommandExecutor.this.commandExecuted(command);
		}

	}

}
