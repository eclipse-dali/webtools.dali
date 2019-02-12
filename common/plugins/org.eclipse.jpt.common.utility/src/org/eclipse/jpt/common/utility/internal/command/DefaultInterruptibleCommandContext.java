/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.command.InterruptibleCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the interruptible command context interface
 * that simply executes the command without any sort of enhancement.
 */
public final class DefaultInterruptibleCommandContext
	implements InterruptibleCommandContext, Serializable
{
	public static final InterruptibleCommandContext INSTANCE = new DefaultInterruptibleCommandContext();

	public static InterruptibleCommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DefaultInterruptibleCommandContext() {
		super();
	}

	public void execute(InterruptibleCommand command) throws InterruptedException {
		command.execute();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
