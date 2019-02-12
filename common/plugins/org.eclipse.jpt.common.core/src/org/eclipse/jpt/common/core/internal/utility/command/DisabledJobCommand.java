/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the job command interface that will throw an
 * {@link UnsupportedOperationException exception} when executed.
 */
public final class DisabledJobCommand
	implements JobCommand, Serializable
{
	public static final JobCommand INSTANCE = new DisabledJobCommand();

	public static JobCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledJobCommand() {
		super();
	}

	// throw an exception
	public IStatus execute(IProgressMonitor monitor) {
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
