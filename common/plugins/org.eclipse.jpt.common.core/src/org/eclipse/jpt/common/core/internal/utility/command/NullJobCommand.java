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
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the job command interface that will do
 * nothing when executed.
 */
public final class NullJobCommand
	implements JobCommand, Serializable
{
	public static final JobCommand INSTANCE = new NullJobCommand();

	public static JobCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullJobCommand() {
		super();
	}

	public IStatus execute(IProgressMonitor monitor) {
		return Status.OK_STATUS;
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
