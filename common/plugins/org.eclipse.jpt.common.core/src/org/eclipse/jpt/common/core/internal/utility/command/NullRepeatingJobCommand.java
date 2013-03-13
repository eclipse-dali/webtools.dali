/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.command.RepeatingJobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the repeating job command interface that
 * will do nothing when executed.
 */
public final class NullRepeatingJobCommand
	implements RepeatingJobCommand, Serializable
{
	public static final RepeatingJobCommand INSTANCE = new NullRepeatingJobCommand();

	public static RepeatingJobCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullRepeatingJobCommand() {
		super();
	}

	public void start() {
		// do nothing
	}

	public IStatus execute(IProgressMonitor monitor) {
		return Status.OK_STATUS;
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
