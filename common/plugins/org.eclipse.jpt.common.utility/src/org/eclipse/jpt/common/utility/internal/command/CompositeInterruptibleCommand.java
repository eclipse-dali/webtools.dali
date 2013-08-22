/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * @see CompositeCommand
 */
public class CompositeInterruptibleCommand
	implements InterruptibleCommand
{
	private final Iterable<InterruptibleCommand> commands;

	public CompositeInterruptibleCommand(Iterable<InterruptibleCommand> commands) {
		super();
		if (IterableTools.isOrContainsNull(commands)) {
			throw new NullPointerException();
		}
		this.commands = commands;
	}

	public void execute() throws InterruptedException {
		for (InterruptibleCommand command : this.commands) {
			command.execute();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commands);
	}
}
