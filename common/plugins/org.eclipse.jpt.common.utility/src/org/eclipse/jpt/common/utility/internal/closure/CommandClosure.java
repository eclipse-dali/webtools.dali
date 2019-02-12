/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Command} to the {@link Closure} interface.
 * The closure's argument is ignored.
 * 
 * @param <A> the type of the object passed to the closure;
 *     ignored
 * 
 * @see org.eclipse.jpt.common.utility.internal.command.ClosureCommand
 */
public class CommandClosure<A>
	implements Closure<A>
{
	private final Command command;


	public CommandClosure(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute(A argument) {
		this.command.execute();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
