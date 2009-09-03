/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.Synchronizer;

/**
 * This synchronizer will synchronize immediately and not return until the
 * synchronization and any nested (recursive) synchronizations are complete.
 * In some situations this implementation should be used sparingly, and for as
 * short a time as possible, as it increases the probability of deadlocks. A
 * deadlock can occur when {@link Synchronizer#synchronize()} is called from multiple
 * threads and multiple resources are locked by the synchronization in varying
 * orders.
 * <p>
 * As defined in the {@link Synchronizer} interface, {@link Synchronizer#start()}
 * and {@link Synchronizer#stop()}
 * should be called in the same thread, but it is not required.
 * {@link Synchronizer#synchronize()} should
 * always be called in the same thread (i.e. only recursively, beyond the
 * initial call); although, this too is not required.
 * This thread need not be the same thread that executes
 * {@link Synchronizer#start()} and {@link Synchronizer#stop()}.
 */
public class SynchronousSynchronizer implements Synchronizer {
	/**
	 * The client-supplied command that performs the synchronization. It may
	 * trigger further calls to {@link #synchronize()} (i.e. the
	 * synchronization may recurse).
	 */
	protected final Command command;

	/**
	 * Synchronize reading and modifying of a set of flags that control what
	 * happens when {@link #synchronize()} is called when the synchronizer is in
	 * various states (idling, executing, stopped).
	 */
	protected final Flags flags;


	/**
	 * Construct a synchronous synchronizer that uses the specified command to
	 * perform the synchronization.
	 */
	public SynchronousSynchronizer(Command command) {
		super();
		this.command = command;
		this.flags = new Flags();
	}

	/**
	 * Initialize the flags and kick off the first synchronization.
	 */
	public void start() {
		this.flags.start();
		this.synchronize();
	}

	/**
	 * It's possible to come back here if the synchronization command recurses
	 * and triggers another synchronization.
	 */
	public void synchronize() {
		if (this.flags.synchronizeCanStart()) {
			do {
				this.command.execute();
			} while (this.flags.synchronizeMustRunAgain());
		}
	}

	/**
	 * Set the flags so that no further synchronizations occur.
	 */
	public void stop() {
		this.flags.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.flags);
	}


	// ********** synchronized flags **********

	/**
	 * Synchronize access to the "executing", "again" and "stop" flags.
	 * These flags are tightly coupled to {@link SynchronousSynchronizer} in that they
	 * are altered as the side-effect to various queries. For example, asking
	 * whether a synchronization can start ({@link #synchronizeCanStart()}) will alter
	 * the "again" flag, depending on the current state of the other flags.
	 */
	protected static class Flags {
		/**
		 * Use a synchronized boolean to communicate between the two threads
		 * that might separately call {@link #start()}/{@link #stop()}
		 * and {@link #synchronizeCanStart()}/{@link #synchronizeMustRunAgain()}.
		 */
		protected final SynchronizedBoolean executing = new SynchronizedBoolean(false, this);

		protected boolean again = false;
		protected boolean stop = true;

		/**
		 * Simply clear the "stop" flag.
		 */
		protected synchronized void start() {
			if ( ! this.stop) {
				throw new IllegalStateException("The Synchronizer was not stopped."); //$NON-NLS-1$
			}
			this.stop = false;
		}

		/**
		 * Restore our start-up state and wait for any current synchronization
		 * to complete.
		 */
		protected synchronized void stop() {
			if (this.stop) {
				throw new IllegalStateException("The Synchronizer was not started."); //$NON-NLS-1$
			}
			this.stop = true;
			this.again = false;  // may not necessarily be true
			try {
				this.executing.waitUntilFalse();  // #start() cannot be called during this wait...
			} catch (InterruptedException ex) {
				// the thread that called #stop() was interrupted while waiting - ignore;
				// 'stop' is still set to true, so the #synchronize() loop will still stop - we
				// just won't wait around for it...
			}
		}

		/**
		 * A client has requested a synchronization; return whether the
		 * synchronizer can start the requested synchronization.
		 * <p>
		 * Side-effects:<ul>
		 * <li>If we are supposed to stop, both the "executing" and "again" flags were cleared in {@link #stop()}.
		 * <li>If we are currently "executing", set the "again" flag.
		 * <li>If we are not currently "executing", set the "executing" flag and clear the "again" flag.
		 * </ul>
		 */
		protected synchronized boolean synchronizeCanStart() {
			if (this.stop) {
				return false;
			}
			if (this.executing.isTrue()) {
				this.again = true;
				return false;
			}
			this.executing.setTrue();
			this.again = false;
			return true;
		}

		/**
		 * The synchronization has finished; return whether the synchronizer
		 * must perform another synchronization (because {@link Synchronizer#synchronize()} was
		 * called during the synchronization).
		 * Side-effects:<ul>
		 * <li>If we are supposed to stop:<ul>
		 *       <li>the "again" flag was cleared in {@link #stop()};
		 *       <li>clear the "executing" flag so {@link #stop()} can complete
		 * </ul>
		 * <li>If we have to synchronize again, clear the "again" flag and leave the "executing" flag set.
		 * <li>If we are finished (i.e. no recursive synchronize requests occurred), clear the "executing" flag.
		 * </ul>
		 */
		protected synchronized boolean synchronizeMustRunAgain() {
			if (this.stop) {
				this.executing.setFalse();
				return false;
			}
			if (this.again) {
				// leave 'executing' set to true
				this.again = false;
				return true;
			}
			this.executing.setFalse();
			return false;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.buildExecutingString());
		}

		protected String buildExecutingString() {
			return this.executing.isTrue() ? "executing" : "not executing";  //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

}

//	State Machine
//	
//	executing=F again=F stop=F
//	quiesced
//		start() => exception - e.g. two calls to #start()
//		stop() => stop=T - called when quiesced (e.g. immediately after #start() is called)
//		synchronizeCanStart() => executing=T - first call to #synchronize(), or later calls when quiesced
//		synchronizeMustRunAgain() => not possible - this is the only method that sets 'executing' to false and it is only called when 'executing' is true
//	
//	executing=F again=F stop=T
//	stopped
//		start() => stop=F - first call to #start()
//		stop() => exception - e.g. two calls to #stop()
//		synchronizeCanStart() => no change - called before #start() called or after #stop() called
//		synchronizeMustRunAgain() => no change - clear 'executing' flag again
//	
//	executing=F again=T stop=F
//	NOT POSSIBLE - if all synchronizations occur on the same thread - 'again' will only be set true when 'executing' is true
//	
//	executing=F again=T stop=T
//	NOT POSSIBLE on same thread - whenever 'stop' is true, 'again' is false
//	
//	executing=T again=F stop=F
//	final execution before quiescing
//		start() => exception - called during single (or final) execution
//		stop() => executing=F stop=T - called during single (or final) execution
//		synchronizeCanStart() => again=T - first call to #synchronize() *during* execution
//		synchronizeMustRunAgain() => executing=F - single (or final) execution with no recursive calls, quiesce
//	
//	executing=T again=F stop=T
//	will stop at completion of current execution
//		start() => not possible on same thread
//		stop() => exception - two calls to #stop() during single execution
//		synchronizeCanStart() => no change - #stop() called during execution that is still executing
//		synchronizeMustRunAgain() => executing=F - #stop() called during execution that just completed
//	
//	executing=T again=T stop=F
//	will execute again at completion of current execution
//		start() => exception - called during intermediate execution
//		stop() => executing=F again=F stop=T - called during intermediate execution, wait for execution to complete to set 'executing' to false
//		synchronizeCanStart() => no change (set 'again' flag again)
//		synchronizeMustRunAgain() => again=F - #synchronize() called during execution that just completed
//	
//	executing=T again=T stop=T
//	NOT POSSIBLE on same thread - whenever 'stop' is true, 'again' is false
