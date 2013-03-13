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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the command interface that will throw an
 * {@link UnsupportedOperationException exception} when executed.
 */
public final class DisabledCommand
	implements Command, Serializable
{
	public static final Command INSTANCE = new DisabledCommand();

	public static Command instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledCommand() {
		super();
	}

	// throw an exception
	public void execute() {
		throw new UnsupportedOperationException();
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
