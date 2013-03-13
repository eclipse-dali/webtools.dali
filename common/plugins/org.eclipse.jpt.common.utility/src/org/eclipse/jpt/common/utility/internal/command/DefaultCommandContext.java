/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the command context interface
 * that simply executes the command without any sort of enhancement.
 */
public final class DefaultCommandContext
	implements CommandContext, Serializable
{
	public static final CommandContext INSTANCE = new DefaultCommandContext();

	public static CommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DefaultCommandContext() {
		super();
	}

	public void execute(Command command) {
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
