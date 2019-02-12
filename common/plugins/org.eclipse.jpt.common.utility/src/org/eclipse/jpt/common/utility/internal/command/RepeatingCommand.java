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

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Command that executes another command a specified number of times.
 */
public class RepeatingCommand
	implements Command
{
	private final int count;
	private final Command command;

	public RepeatingCommand(Command command, int count) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		if (count <= 0) {
			throw new IndexOutOfBoundsException("invalid count: " + count); //$NON-NLS-1$
		}
		this.command = command;
		this.count = count;
	}

	public void execute() {
		for (int i = this.count; i-- > 0;) {
			this.command.execute();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
