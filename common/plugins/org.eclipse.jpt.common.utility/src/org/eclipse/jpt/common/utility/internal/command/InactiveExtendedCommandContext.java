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
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the extended command context interface
 * that ignores any commands.
 */
public final class InactiveExtendedCommandContext
	implements ExtendedCommandContext, Serializable
{
	public static final ExtendedCommandContext INSTANCE = new InactiveExtendedCommandContext();

	public static ExtendedCommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InactiveExtendedCommandContext() {
		super();
	}

	public void execute(Command command) {
		// do nothing
	}

	public void waitToExecute(Command command) {
		// do nothing
	}

	public boolean waitToExecute(Command command, long timeout) {
		// do nothing
		return true;
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
