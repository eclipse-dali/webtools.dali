/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * Straightforward implementation of {@link StatefulCommandContext}
 * that executes commands immediately by default. This context can
 * also be used to adapt simple {@link CommandContext}s to the
 * {@link StatefulCommandContext} interface, providing support for
 * lifecycle state.
 */
public abstract class AbstractStatefulCommandContext<E extends CommandContext>
	implements StatefulCommandContext
{
	protected final SynchronizedBoolean active = new SynchronizedBoolean(false);
	protected final E commandContext;


	protected AbstractStatefulCommandContext(E commandContext) {
		super();
		if (commandContext == null) {
			throw new NullPointerException();
		}
		this.commandContext = commandContext;
	}

	public synchronized void start() {
		if (this.active.isTrue()) {
			throw new IllegalStateException("Not stopped."); //$NON-NLS-1$
		}
		this.active.setTrue();
	}

	/**
	 * If the command context is inactive the command is simply ignored.
	 */
	public void execute(Command command) {
		if (this.active.isTrue()) {
			this.commandContext.execute(command);
		}
	}

	public synchronized void stop() {
		if (this.active.isFalse()) {
			throw new IllegalStateException("Not started."); //$NON-NLS-1$
		}
		this.active.setFalse();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commandContext);
	}
}
