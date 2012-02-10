/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandExecutor;

/**
 * This command executor allows the client to
 * specify a different command executor for each thread.
 */
public abstract class AbstractThreadLocalCommandExecutor<E extends CommandExecutor>
	implements CommandExecutor
{
	protected final ThreadLocal<E> threadLocal;
	protected final E defaultCommandExecutor;


	protected AbstractThreadLocalCommandExecutor(E defaultCommandExecutor) {
		super();
		if (defaultCommandExecutor == null) {
			throw new NullPointerException();
		}
		this.defaultCommandExecutor = defaultCommandExecutor;
		this.threadLocal = this.buildThreadLocal();
	}

	protected ThreadLocal<E> buildThreadLocal() {
		return new ThreadLocal<E>();
	}

	public void execute(Command command) {
		this.getThreadLocalCommandExecutor().execute(command);
	}

	protected E getThreadLocalCommandExecutor() {
		E ce = this.threadLocal.get();
		return (ce != null) ? ce : this.defaultCommandExecutor;
	}

	/**
	 * Set the current thread's command executor to the specified value.
	 */
	public void set(E commandExecutor) {
		this.threadLocal.set(commandExecutor);
	}

	/**
	 * Return the string representation of the current thread's command
	 * executor.
	 */
	@Override
	public String toString() {
		return '[' + this.getThreadLocalCommandExecutor().toString() + ']';
	}
}
