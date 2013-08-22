/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see ClosureCommand
 */
public class InterruptibleClosureCommand
	implements InterruptibleCommand
{
	private final InterruptibleClosure<?> closure;


	public InterruptibleClosureCommand(InterruptibleClosure<?> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public void execute() throws InterruptedException {
		this.closure.execute(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
