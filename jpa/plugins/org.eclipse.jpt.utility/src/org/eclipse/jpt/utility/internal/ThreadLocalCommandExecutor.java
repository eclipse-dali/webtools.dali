/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * This implementation of the CommandExecutor interface allows the client to
 * specify a different Command Executor for each thread.
 */
public class ThreadLocalCommandExecutor implements CommandExecutor {
	protected final ThreadLocal<CommandExecutor> threadLocalCommandExecutor;
	protected final CommandExecutor defaultCommandExecutor;

	public ThreadLocalCommandExecutor() {
		this(CommandExecutor.Default.instance());
	}

	public ThreadLocalCommandExecutor(CommandExecutor defaultCommandExecutor) {
		super();
		this.defaultCommandExecutor = defaultCommandExecutor;
		this.threadLocalCommandExecutor = this.buildThreadLocalCommandExecutor();
	}

	protected ThreadLocal<CommandExecutor> buildThreadLocalCommandExecutor() {
		return new ThreadLocal<CommandExecutor>();
	}

	public void execute(Command command) {
		this.getThreadLocalCommandExecutor().execute(command);
	}

	protected CommandExecutor getThreadLocalCommandExecutor() {
		CommandExecutor ce = this.threadLocalCommandExecutor.get();
		return (ce != null) ? ce : this.defaultCommandExecutor;
	}

	/**
	 * Set the current thread's command executor to the specified value.
	 */
	public void setThreadLocalCommandExecutor(CommandExecutor commandExecutor) {
		this.threadLocalCommandExecutor.set(commandExecutor);
	}

	@Override
	public String toString() {
		return this.getThreadLocalCommandExecutor().toString();
	}

}
