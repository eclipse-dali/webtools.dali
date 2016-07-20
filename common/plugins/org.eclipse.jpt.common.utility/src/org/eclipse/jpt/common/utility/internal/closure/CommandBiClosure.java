/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Command} to the {@link BiClosure} interface.
 * The closure's arguments are ignored.
 * 
 * @param <A1> the type of the first object passed to the closure; ignored
 * @param <A2> the type of the second object passed to the closure; ignored
 */
public class CommandBiClosure<A1, A2>
	implements BiClosure<A1, A2>
{
	private final Command command;


	public CommandBiClosure(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute(A1 argument1, A2 argument2) {
		this.command.execute();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
