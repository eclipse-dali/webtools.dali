/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import java.util.ArrayList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.core.utility.command.JobCommandContext;
import org.eclipse.jpt.common.core.utility.command.RepeatingJobCommand;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.StackTrace;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.RepeatingCommandState;

/**
 * Wrap a repeating {@link JobCommand}.
 * <p>
 * <strong>NB:</strong> The {@link IProgressMonitor progress monitor} passed to
 * the job command wrapper is <em>ignored</em>. The {@link IProgressMonitor
 * progress monitor} passed to the <em>wrapped</em> job command is determined by
 * the {@link #startCommandContext start command executor}. That same
 * {@link IProgressMonitor progress monitor} is passed to the <em>wrapped</em>
 * job command for every execution during an execution "cycle". It is
 * <em>not</em> reset with each execution; so several
 * executions may take place before a new progress monitor is passed to the
 * command.
 * @see org.eclipse.jpt.common.utility.internal.command.RepeatingCommandWrapper
 */
public class RepeatingJobCommandWrapper
	implements RepeatingJobCommand
{
	/**
	 * The client-supplied command that performs the execution. It may
	 * trigger further calls to {@link #execute(IProgressMonitor)}
	 * (i.e. the <em>wrapped</em>
	 * command's execution may recurse back to the client code that executes the
	 * <em>wrapper</em> command).
	 */
	/* CU private */ final JobCommand command;

	/**
	 * The command executed whenever the wrapped {@link #command} must be
	 * executed and it is not already executing. If the wrapped {@link #command}
	 * is already executing, it will simply be re-executed directly (once it has
	 * completed its current execution), as opposed to calling the start
	 * command.
	 */
	private final JobCommand startCommand;

	/**
	 * The client-supplied command context that provides the context for the
	 * {@link #startCommand start command}. By default, the start command is
	 * executed directly; but this executor provides a hook for executing the
	 * {@link #startCommand start command} asynchronously; after which,
	 * subsequent overlapping executions are executed synchronously.
	 */
	private final JobCommandContext startCommandContext;

	/**
	 * This handles the exceptions thrown by the <em>wrapped</em> command.
	 */
	final ExceptionHandler exceptionHandler;

	/**
	 * The command wrapper's state.
	 */
	final RepeatingCommandState state;

	/**
	 * List of stack traces for each (repeating) invocation of the command,
	 * starting with the initial invocation. The list is cleared with each
	 * initial invocation of the command.
	 */
	private final ArrayList<StackTrace> stackTraces = debug() ? new ArrayList<StackTrace>() : null;

	private static boolean debug() {
		return JptCommonCorePlugin.instance().isDebugEnabled();
	}


	// ********** construction **********

	/**
	 * Construct a repeating command wrapper that executes the specified
	 * command. Any exceptions thrown by the command will be handled by the
	 * specified exception handler.
	 */
	public RepeatingJobCommandWrapper(JobCommand command, ExceptionHandler exceptionHandler) {
		this(command, DefaultJobCommandContext.instance(), exceptionHandler);
	}

	/**
	 * Construct a repeating command wrapper that executes the specified
	 * command and uses the specified command context to execute the wrapped
	 * command whenever it is not already executing.
	 * Any exceptions thrown by the command will be handled by the
	 * specified exception handler.
	 */
	public RepeatingJobCommandWrapper(JobCommand command, JobCommandContext startCommandContext, ExceptionHandler exceptionHandler) {
		super();
		if ((command == null) || (startCommandContext == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.command = command;
		this.startCommandContext = startCommandContext;
		this.startCommand = this.buildStartJobCommand();
		this.exceptionHandler = exceptionHandler;
		this.state = this.buildState();
	}

	private JobCommand buildStartJobCommand() {
		return new StartJobCommand();
	}

	private RepeatingCommandState buildState() {
		return new RepeatingCommandState();
	}


	// ********** RepeatingJobCommand implementation **********

	public void start() {
		this.state.start();
	}

	/**
	 * It is possible to come back here if the wrapped command recurses
	 * to the client and triggers another execution.
	 */
	public synchronized IStatus execute(IProgressMonitor monitor) {
		if (this.state.isReadyToStartExecutionCycle()) {
			if (debug()) {
				this.stackTraces.clear();
				this.stackTraces.add(new StackTrace());
			}
			this.executeStartCommand();
		} else {
			if (debug()) {
				this.stackTraces.add(new StackTrace());
			}
		}
		return Status.OK_STATUS;
	}

	/* private protected */ void executeStartCommand() {
		this.startCommandContext.execute(this.startCommand);
	}

	public void stop() throws InterruptedException {
		this.state.stop();
	}

	/**
	 * The start command.
	 * @see #startCommandContext
	 */
	/* CU private */ class StartJobCommand
		implements JobCommand
	{
		public IStatus execute(IProgressMonitor monitor) {
			return RepeatingJobCommandWrapper.this.execute_(monitor);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, RepeatingJobCommandWrapper.this.command);
		}
	}

	/**
	 * This method will be called only once per execution cycle.
	 * Any further calls to {@link #execute(IProgressMonitor)} will
	 * simply set the {@link #state} to "repeat",
	 * causing the command to execute again.
	 */
	/* CU private */ IStatus execute_(IProgressMonitor monitor) {
		if (this.state.wasStoppedBeforeFirstExecutionCouldStart()) {
			return Status.OK_STATUS;
		}

		do {
			IStatus status = this.executeCommand(monitor);
			if (status.getSeverity() == IStatus.CANCEL) {
				return status;  // seems reasonable...
			}
		} while (this.state.isRepeat());
		return Status.OK_STATUS;
	}

	/**
	 * Execute the client-supplied command. Do not allow any unhandled
	 * exceptions to kill the wrapper. Pass to the exception handler.
	 * @see NotifyingRepeatingJobCommandWrapper
	 */
	/* private protected */ IStatus executeCommand(IProgressMonitor monitor) {
		try {
			return this.command.execute(monitor);
		} catch (OperationCanceledException ex) {
			return Status.CANCEL_STATUS;  // seems reasonable...
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
			return Status.OK_STATUS;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
