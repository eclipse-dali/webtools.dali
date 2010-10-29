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

import java.util.Vector;
import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.CompositeException;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.SynchronizedObject;
import org.eclipse.jpt.utility.synchronizers.Synchronizer;

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
public class SynchronousSynchronizer
	implements Synchronizer
{
	/**
	 * The client-supplied command that performs the synchronization. It may
	 * trigger further calls to {@link #synchronize()} (i.e. the
	 * synchronization may recurse).
	 */
	private final Command command;

	/**
	 * The synchronizer's current state.
	 */
	final SynchronizedObject<State> state;

	/**
	 * The synchronizer's initial state is {@link #STOPPED}.
	 */
	enum State {
		STOPPED,
		READY,
		EXECUTING,
		REPEAT,
		STOPPING
	}

	/**
	 * A list of the uncaught exceptions thrown by the command.
	 */
	final Vector<Throwable> exceptions = new Vector<Throwable>();


	// ********** construction **********

	/**
	 * Construct a synchronous synchronizer that uses the specified command to
	 * perform the synchronization.
	 */
	public SynchronousSynchronizer(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
		// use the synchronizer as the mutex so it is freed up by the wait in #stop()
		this.state = new SynchronizedObject<State>(State.STOPPED, this);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Set the synchronizer's {@link #state} to {@link State#READY READY}
	 * and execute the first synchronization. Throw an exception if the
	 * synchronizer is not {@link State#STOPPED STOPPED}.
	 */
	public synchronized void start() {
		switch (this.state.getValue()) {
			case STOPPED:
				this.state.setValue(State.READY);
				this.synchronize();
				break;
			case READY:
			case EXECUTING:
			case REPEAT:
			case STOPPING:
			default:
				throw this.buildIllegalStateException();
		}
	}

	/**
	 * It's possible to come back here if the synchronization command recurses
	 * and triggers another synchronization.
	 */
	public void synchronize() {
		if (this.beginSynchronization()) {
			this.synchronize_();
		}
	}

	/**
	 * A client has requested a synchronization.
	 * Return whether we can begin a new synchronization.
	 * If a synchronization is already under way, return <code>false</code>;
	 * but set the {@link #state} to {@link State#REPEAT REPEAT}
	 * so another synchronization will occur once the current
	 * synchronization is complete.
	 */
	private synchronized boolean beginSynchronization() {
		switch (this.state.getValue()) {
			case STOPPED:
				// synchronization is not allowed
				return false;
			case READY:
				// begin a new synchronization
				this.state.setValue(State.EXECUTING);
				return true;
			case EXECUTING:
				// set flag so a new synchronization will occur once the current one is finished
				this.state.setValue(State.REPEAT);
				return false;
			case REPEAT:
				// the "repeat" flag is already set
				return false;
			case STOPPING:
				// no further synchronizations are allowed
				return false;
			default:
				throw this.buildIllegalStateException();
		}
	}

	/**
	 * This method should be called only once per set of "recursing"
	 * synchronizations. Any recursive call to {@link #synchronize()} will
	 * simply set the {@link #state} to {@link State#REPEAT REPEAT},
	 * causing the command to execute again.
	 */
	private void synchronize_() {
		do {
			this.execute();
		} while (this.endSynchronization());
	}

	/**
	 * Execute the client-supplied command. Do not allow any unhandled
	 * exceptions to kill the thread. Store them up for later pain.
	 */
	private void execute() {
		try {
			this.execute_();
		} catch (Throwable ex) {
			this.exceptions.add(ex);
		}
	}

	/**
	 * By default, just execute the command.
	 */
	void execute_() {
		this.command.execute();
	}

	/**
	 * The current synchronization has finished.
	 * Return whether we should begin another synchronization.
	 */
	private synchronized boolean endSynchronization() {
		switch (this.state.getValue()) {
			case STOPPED:
			case READY:
				throw this.buildIllegalStateException();
			case EXECUTING:
				// synchronization has finished and there are no outstanding requests for another; return to "ready"
				this.state.setValue(State.READY);
				return false;
			case REPEAT:
				// the "repeat" flag was set; clear it and start another synchronization
				this.state.setValue(State.EXECUTING);
				return true;
			case STOPPING:
				// a client has initiated a "stop"; mark the "stop" complete and perform no more synchronizations
				this.state.setValue(State.STOPPED);
				return false;
			default:
				throw this.buildIllegalStateException();
		}
	}

	/**
	 * Set the flags so that no further synchronizations occur. If any uncaught
	 * exceptions were thrown while the synchronization was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 */
	public synchronized void stop() {
		switch (this.state.getValue()) {
			case STOPPED:
				throw this.buildIllegalStateException();
			case READY:
				// simply return to "stopped" state
				this.state.setValue(State.STOPPED);
				break;
			case EXECUTING:
			case REPEAT:
				// set the "stopping" flag and wait until the synchronization has finished
				this.state.setValue(State.STOPPING);
				this.waitUntilStopped();
				break;
			case STOPPING:
				throw this.buildIllegalStateException();
			default:
				throw this.buildIllegalStateException();
		}

		if (this.exceptions.size() > 0) {
			Throwable[] temp = this.exceptions.toArray(new Throwable[this.exceptions.size()]);
			this.exceptions.clear();
			throw new CompositeException(temp);
		}
	}

	/**
	 * This wait will free up the synchronizer's synchronized methods
	 * (since the synchronizer is the state's mutex).
	 */
	private void waitUntilStopped() {
		try {
			this.state.waitUntilValueIs(State.STOPPED);
		} catch (InterruptedException ex) {
			// the thread that called #stop() was interrupted while waiting
			// for the synchronization to finish - ignore;
			// 'state' is still set to 'STOPPING', so the #synchronize_() loop
			// will still stop - we just won't wait around for it...
		}
	}

	private IllegalStateException buildIllegalStateException() {
		return new IllegalStateException("state: " + this.state); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.state);
	}

}
