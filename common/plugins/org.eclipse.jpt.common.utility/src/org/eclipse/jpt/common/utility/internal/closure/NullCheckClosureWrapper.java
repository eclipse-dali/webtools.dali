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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure wrapper that checks for a <code>null</code> argument before forwarding
 * the argument to the wrapped closure. If the argument is <code>null</code>,
 * the closure will execute the configured command.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class NullCheckClosureWrapper<A>
	implements Closure<A>
{
	private final Closure<? super A> closure;
	private final Command nullCommand;


	public NullCheckClosureWrapper(Closure<? super A> closure, Command nullCommand) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
		this.nullCommand = nullCommand;
	}

	public void execute(A argument) {
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
