/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * This command context allows the client to
 * specify a different command context for each thread.
 */
public abstract class AbstractThreadLocalCommandContext<E extends CommandContext>
	implements CommandContext
{
	protected final ThreadLocal<E> threadLocal;
	protected final E defaultCommandContext;


	protected AbstractThreadLocalCommandContext(E defaultCommandContext) {
		super();
		if (defaultCommandContext == null) {
			throw new NullPointerException();
		}
		this.defaultCommandContext = defaultCommandContext;
		this.threadLocal = this.buildThreadLocal();
	}

	protected ThreadLocal<E> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public void execute(Command command) {
		this.getThreadLocalCommandContext().execute(command);
	}

	protected E getThreadLocalCommandContext() {
		E context = this.threadLocal.get();
		return (context != null) ? context : this.defaultCommandContext;
	}

	/**
	 * Set the current thread's command context to the specified value.
	 */
	public void set(E commandContext) {
		this.threadLocal.set(commandContext);
	}

	/**
	 * Return the string representation of the current thread's command
	 * context.
	 */
	@Override
	public String toString() {
		return '[' + this.getThreadLocalCommandContext().toString() + ']';
	}
}
