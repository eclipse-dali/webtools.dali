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

import org.eclipse.jpt.common.utility.command.InterruptibleCommand;

/**
 * @see ThreadLocalCommand
 * @see #set(InterruptibleCommand)
 */
public class ThreadLocalInterruptibleCommand
	implements InterruptibleCommand
{
	private final ThreadLocal<InterruptibleCommand> threadLocal;
	private final InterruptibleCommand defaultCommand;

	public ThreadLocalInterruptibleCommand(InterruptibleCommand defaultCommand) {
		super();
		if (defaultCommand == null) {
			throw new NullPointerException();
		}
		this.defaultCommand = defaultCommand;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleCommand> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public void execute() throws InterruptedException {
		this.get().execute();
	}

	private InterruptibleCommand get() {
		InterruptibleCommand command = this.threadLocal.get();
		return (command != null) ? command : this.defaultCommand;
	}

	/**
	 * Set the current thread's command to the specified value.
	 */
	public void set(InterruptibleCommand command) {
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
