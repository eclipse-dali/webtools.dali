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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * Command that provides support for treating a collection of
 * commands as a single command.
 */
public class CompositeCommand
	implements Command
{
	private final Iterable<Command> commands;

	public CompositeCommand(Iterable<Command> commands) {
		super();
		if (IterableTools.isOrContainsNull(commands)) {
			throw new NullPointerException();
		}
		this.commands = commands;
	}

	public void execute() {
		for (Command command : this.commands) {
			command.execute();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commands);
	}
}
