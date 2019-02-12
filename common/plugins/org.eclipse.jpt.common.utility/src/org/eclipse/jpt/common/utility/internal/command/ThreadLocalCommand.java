/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;

/**
 * This command allows the client to specify a different command for each
 * thread. If there is no command for the current thread, the configured default
 * command is executed.
 * @see #set(Command)
 */
public class ThreadLocalCommand
	implements Command
{
	private final ThreadLocal<Command> threadLocal;
	private final Command defaultCommand;

	public ThreadLocalCommand(Command defaultCommand) {
		super();
		if (defaultCommand == null) {
			throw new NullPointerException();
		}
		this.defaultCommand = defaultCommand;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<Command> buildThreadLocal() {
		return new ThreadLocal<Command>();
	}

	public void execute() {
		this.get().execute();
	}

	private Command get() {
		Command command = this.threadLocal.get();
		return (command != null) ? command : this.defaultCommand;
	}

	/**
	 * Set the current thread's command to the specified value.
	 */
	public void set(Command command) {
		this.threadLocal.set(command);
	}

	/**
	 * Return the string representation of the current thread's command.
	 */
	@Override
	public String toString() {
		return this.get().toString();
	}
}
