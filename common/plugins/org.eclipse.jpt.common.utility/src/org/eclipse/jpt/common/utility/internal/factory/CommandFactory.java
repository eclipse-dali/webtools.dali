/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Command} to the {@link Factory} interface.
 * The factory always returns <code>null</code>.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see org.eclipse.jpt.common.utility.internal.command.FactoryCommand
 */
public class CommandFactory<T>
	implements Factory<T>
{
	private final Command command;


	public CommandFactory(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public T create() {
		this.command.execute();
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
