/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

/**
 * <code>CompositeCommand</code> provides support for treating a collection of
 * {@link Command}s as a single command.
 */
public class CompositeCommand
	implements Command
{
	private final Iterable<Command> commands;

	public CompositeCommand(Command... commands) {
		this(new ArrayIterable<Command>(commands));
	}

	public CompositeCommand(Iterable<Command> commands) {
		super();
		this.commands = commands;
	}

	public void execute() {
		for (Command command : this.commands) {
			command.execute();
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.commands);
	}

}
