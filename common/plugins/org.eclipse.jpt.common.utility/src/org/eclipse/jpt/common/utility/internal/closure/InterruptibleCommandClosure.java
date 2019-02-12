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

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see CommandClosure
 */
public class InterruptibleCommandClosure<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleCommand command;


	public InterruptibleCommandClosure(InterruptibleCommand command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute(A argument) throws InterruptedException {
		this.command.execute();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
