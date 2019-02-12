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
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Factory} to the {@link Command} interface.
 * The factory's output is ignored.
 * This really only useful for a factory that has side-effects.
 * 
 * @see org.eclipse.jpt.common.utility.internal.factory.CommandFactory
 */
public class FactoryCommand
	implements Command
{
	private final Factory<?> factory;


	public FactoryCommand(Factory<?> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	public void execute() {
		this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
