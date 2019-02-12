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
import org.eclipse.jpt.common.utility.command.RepeatingCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the repeating command interface that will do
 * nothing when executed.
 */
public final class NullRepeatingCommand
	implements RepeatingCommand, Serializable
{
	public static final RepeatingCommand INSTANCE = new NullRepeatingCommand();

	public static RepeatingCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullRepeatingCommand() {
		super();
	}

	public void start() {
		// do nothing
	}

	public void execute() {
		// do nothing
	}

	public void stop() {
		// do nothing
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
