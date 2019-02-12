/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Closure} to the {@link Command} interface.
 * The closure is passed <code>null</code> for an argument.
 * This really only useful for a closure that accepts a
 * <code>null</code> argument.
 * 
 * @see org.eclipse.jpt.common.utility.internal.closure.CommandClosure
 */
public class ClosureCommand
	implements Command
{
	private final Closure<?> closure;


	public ClosureCommand(Closure<?> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public void execute() {
		this.closure.execute(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
