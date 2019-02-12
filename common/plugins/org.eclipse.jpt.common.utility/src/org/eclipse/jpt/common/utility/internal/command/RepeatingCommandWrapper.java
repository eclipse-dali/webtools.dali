/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.command.RepeatingCommand;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.StackTrace;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Wrap a repeating {@link Command}.
 */
public class RepeatingCommandWrapper
	implements RepeatingCommand
{
	/**
	 * The client-supplied command that performs the execution. It may
	 * trigger further calls to {@link #execute()} (i.e. the <em>wrapped</em>
	 * command's execution may recurse back to the client code that executes the
	 * <em>wrapper</em> command).
	 */
	/* CU private */ final Command command;

	/**
	 * The command executed whenever the wrapped {@link #command} must be
	 * executed and it is not already executing. If the wrapped {@link #command}
	 * is already executing, it will simply be re-executed directly (once it has
	 * completed its current execution), as opposed to calling the start
	 * command.
	 */
	private final Command startCommand;

	/**
	 * The client-supplied command context that provides the context for the
	 * {@link #startCommand start command}. By default, the start command is
	 * executed directly; but this context provides a hook for executing the
	 * {@link #startCommand start command} asynchronously; after which,
	 * subsequent overlapping executions are executed synchronously.
	 */
	private final CommandContext startCommandContext;

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
	private final ArrayList<StackTrace> stackTraces = DEBUG ? new ArrayList<>() : null;
	// see RepeatingCommandWrapperTests.testDEBUG()
	private static final boolean DEBUG = false;


	// ********** construction **********

	/**
	 * Construct a repeating command wrapper that executes the specified
	 * command. Any exceptions thrown by the command will be handled by the
	 * specified exception handler.
	 */
	public RepeatingCommandWrapper(Command command, ExceptionHandler exceptionHandler) {
		this(command, DefaultCommandContext.instance(), exceptionHandler);
	}

	/**
	 * Construct a repeating command wrapper that executes the specified
	 * command and uses the specified command context to execute the wrapped
	 * command whenever it is not already executing.
	 * Any exceptions thrown by the command will be handled by the
	 * specified exception handler.
	 */
	public RepeatingCommandWrapper(Command command, CommandContext startCommandContext, ExceptionHandler exceptionHandler) {
		super();
		if ((command == null) || (startCommandContext == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.command = command;
		this.startCommandContext = startCommandContext;
		this.startCommand = this.buildStartCommand();
		this.exceptionHandler = exceptionHandler;
		this.state = this.buildState();
	}

	private Command buildStartCommand() {
		return new StartCommand();
	}

	private RepeatingCommandState buildState() {
		return new RepeatingCommandState();
	}


	// ********** RepeatingCommand implementation **********

	public void start() {
		this.state.start();
	}

	/**
	 * It is possible to come back here if the wrapped command recurses
	 * to the client and triggers another execution.
	 */
	public synchronized void execute() {
		if (this.state.isReadyToStartExecutionCycle()) {
			if (DEBUG) {
				this.stackTraces.clear();
				this.stackTraces.add(new StackTrace());
			}
			this.executeStartCommand();
		} else {
			if (DEBUG) {
				this.stackTraces.add(new StackTrace());
			}
		}
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
	/* CU private */ class StartCommand
		implements Command
	{
		public void execute() {
			RepeatingCommandWrapper.this.execute_();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, RepeatingCommandWrapper.this.command);
		}
	}

	/**
	 * This method will be called only once per execution cycle.
	 * Any further calls to {@link #execute()} will
	 * simply set the {@link #state} to "repeat",
	 * causing the command to execute again.
	 */
	/* CU private */ void execute_() {
		if ( ! this.state.wasStoppedBeforeFirstExecutionCouldStart()) {
			do {
				this.executeCommand();
			} while (this.state.isRepeat());
		}
	}

	/**
	 * Execute the client-supplied command. Do not allow any unhandled
	 * exceptions to kill the wrapper. Pass to the exception handler.
	 * @see NotifyingRepeatingCommandWrapper
	 */
	/* private protected */ void executeCommand() {
		try {
			this.command.execute();
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
