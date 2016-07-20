/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see NullCheckClosureWrapper
 * @see NullClosure
 * @see InterruptibleClosureAdapter
 */
public class NullCheckInterruptibleClosureWrapper<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleClosure<? super A> closure;
	private final InterruptibleCommand nullCommand;


	public NullCheckInterruptibleClosureWrapper(InterruptibleClosure<? super A> closure, InterruptibleCommand nullCommand) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
		if (nullCommand == null) {
			throw new NullPointerException();
		}
		this.nullCommand = nullCommand;
	}

	public void execute(A argument) throws InterruptedException {
		if (argument == null) {
			this.nullCommand.execute();
		} else {
			this.closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
