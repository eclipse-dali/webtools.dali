/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.Command;

/**
 * This implementation of the Command interface allows the client to
 * specify a different Command for each thread.
 */
public class ThreadLocalCommand implements Command {
	protected final ThreadLocal<Command> threadLocal;
	protected final Command defaultCommand;

	/**
	 * The default command does nothing.
	 */
	public ThreadLocalCommand() {
		this(Command.Null.instance());
	}

	public ThreadLocalCommand(Command defaultCommand) {
		super();
		this.defaultCommand = defaultCommand;
		this.threadLocal = this.buildThreadLocal();
	}

	protected ThreadLocal<Command> buildThreadLocal() {
		return new ThreadLocal<Command>();
	}

	public void execute() {
		this.get().execute();
	}

	protected Command get() {
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
