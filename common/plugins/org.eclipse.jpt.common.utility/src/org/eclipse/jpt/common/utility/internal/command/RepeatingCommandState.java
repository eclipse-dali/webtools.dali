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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedObject;

/**
 * Provide the state machine to support minimal repeat command executions.
 */
public class RepeatingCommandState {
	/**
	 * The current state.
	 */
	private final SynchronizedObject<State> state;

	/**
	 * The initial {@link #state} is {@link #STOPPED}.
	 * Clients must call {@link #start()} before the command can be
	 * executed.
	 */
	private enum State {
		STOPPED,
		READY,
		PRE_EXECUTION,
		EXECUTING,
		REPEAT,
		STOPPING
	}


	// ********** construction **********

	/**
	 * Construct a repeating command state.
	 */
	public RepeatingCommandState() {
		super();
		// use the command wrapper as the mutex so it is freed up by the wait in #stop()
		this.state = new SynchronizedObject<State>(State.STOPPED, this);
	}


	/**
	 * Set the {@link #state} to {@link State#READY READY}.
	 * @exception IllegalStateException if the command wrapper is not
	 * {@link State#STOPPED STOPPED}.
	 */
	public synchronized void start() {
		switch (this.state.getValue()) {
			case STOPPED:
				this.state.setValue(State.READY);
				break;
			case READY:
			case PRE_EXECUTION:
			case EXECUTING:
			case REPEAT:
			case STOPPING:
			default:
				throw this.buildISE();
		}
	}

	/**
	 * A client has requested an execution.
	 * Return whether we are ready to begin a new execution "cycle".
	 * If an execution is already under way, return <code>false</code>;
	 * but set the {@link #state} to {@link State#REPEAT REPEAT}
	 * so another execution will occur once the current
	 * execution is complete.
	 * <p>
	 * <strong>NB:</strong> This method has possible side-effects:
	 * The value of {@link #state} may be changed.
	 */
	public synchronized boolean isReadyToStartExecutionCycle() {
		switch (this.state.getValue()) {
			case STOPPED:
				// execution is not allowed
				return false;
			case READY:
				// start a new execution, possibly asynchronously
				this.state.setValue(State.PRE_EXECUTION);
				return true;
			case PRE_EXECUTION:
				// no need to set 'state' to PRE_EXECUTION again,
				// the command has not yet begun executing
				return false;
			case EXECUTING:
				// set 'state' to REPEAT so a new execution will occur once the current one is finished
				this.state.setValue(State.REPEAT);
				return false;
			case REPEAT:
				// no need to set 'state' to REPEAT again
				return false;
			case STOPPING:
				// no further executions are allowed
				return false;
			default:
				throw this.buildISE();
		}
	}

	/**
	 * An execution "cycle" is ready to begin.
	 * Make sure a call to {@link #stop()} did not slip in between the
	 * dispatching of the initial wrapped command execution (when
	 * {@link #isReadyToStartExecutionCycle()} returns <code>true</code>) and the
	 * actual execution of the wrapped command.
	 * This can happen if the wrapped command was dispatched asynchronously.
	 * <p>
	 * This method should be called from the actual execution method, before it
	 * starts looping.
	 */
	public synchronized boolean wasStoppedBeforeFirstExecutionCouldStart() {
		switch (this.state.getValue()) {
			case STOPPED:
				// a call to stop() slipped in before the command could start
				// executing, probably because it was dispatched asynchronously
				// by 'startCommandExecutor' (e.g. in a job)
				return true;
			case PRE_EXECUTION:
				this.state.setValue(State.EXECUTING);
				return false;
			case READY:
			case EXECUTING:
			case REPEAT:
			case STOPPING:
			default:
				throw this.buildISE();
		}
	}

	/**
	 * The current execution has finished.
	 * Return whether we should begin another execution because a call to
	 * execute occurred <em>during</em> the just-completed execution.
	 */
	public synchronized boolean isRepeat() {
		switch (this.state.getValue()) {
			case STOPPED:
			case READY:
			case PRE_EXECUTION:
				throw this.buildISE();
			case EXECUTING:
				// execution has finished and there are no outstanding requests for another; return to READY
				this.state.setValue(State.READY);
				return false;
			case REPEAT:
				// set 'state' back to EXECUTING and begin another execution
				this.state.setValue(State.EXECUTING);
				return true;
			case STOPPING:
				// a client initiated a "stop" during the previous execution;
				// mark the "stop" complete and perform no more executions
				this.state.setValue(State.STOPPED);
				return false;
			default:
				throw this.buildISE();
		}
	}

	/**
	 * Return whether the execution "cycle" is "quiesced" (i.e. there are no
	 * outstanding execution requests).
	 */
	public boolean isQuiesced() {
		return this.state.getValue() != State.REPEAT;
	}

	/**
	 * Set {@link #state} so no further executions occur.
	 * @exception IllegalStateException if the command wrapper is already
	 * {@link State#STOPPED STOPPED} or {@link State#STOPPING STOPPING}.
	 */
	public synchronized void stop() throws InterruptedException {
		switch (this.state.getValue()) {
			case READY:
			case PRE_EXECUTION:
				// simply set 'state' to STOPPED and return
				this.state.setValue(State.STOPPED);
				break;
			case EXECUTING:
			case REPEAT:
				// set 'state' to STOPPING and wait until the current execution has finished
				this.state.setValue(State.STOPPING);
				this.waitUntilStopped();
				break;
			case STOPPED:
			case STOPPING:
			default:
				throw this.buildISE();
		}
	}

	/**
	 * This wait will free up the command wrapper's synchronized methods
	 * (since the command wrapper is the mutex for {@link #state}).
	 * <p>
	 * If the thread that called {@link #stop()} is interrupted while waiting
	 * for the current command to finish executing on another thread,
	 * {@link #state} will still be {@link State#STOPPING STOPPING}, so the loop
	 * begun by the command wrapper will still stop and set {@link #state} to
	 * {@link State#STOPPED STOPPED}, we just won't wait around for it....
	 */
	private void waitUntilStopped() throws InterruptedException {
		this.state.waitUntilValueIs(State.STOPPED);
	}

	private IllegalStateException buildISE() {
		return new IllegalStateException("state: " + this.state); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.state);
	}
}
