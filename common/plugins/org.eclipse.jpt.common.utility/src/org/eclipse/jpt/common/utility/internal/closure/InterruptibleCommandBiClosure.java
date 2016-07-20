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

import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt an {@link InterruptibleCommand} to the {@link InterruptibleBiClosure} interface.
 * The closure's arguments are ignored.
 * 
 * @param <A1> the type of the first object passed to the closure; ignored
 * @param <A2> the type of the second object passed to the closure; ignored
 */
public class InterruptibleCommandBiClosure<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final InterruptibleCommand command;


	public InterruptibleCommandBiClosure(InterruptibleCommand command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		this.command.execute();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
