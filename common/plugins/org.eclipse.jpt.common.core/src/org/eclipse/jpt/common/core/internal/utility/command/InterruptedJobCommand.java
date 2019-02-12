/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.command.InterruptibleJobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the interruptible job command interface that
 * will throw an {@link InterruptedException exception} when executed.
 */
public final class InterruptedJobCommand
	implements InterruptibleJobCommand, Serializable
{
	public static final InterruptibleJobCommand INSTANCE = new InterruptedJobCommand();

	public static InterruptibleJobCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InterruptedJobCommand() {
		super();
	}

	// throw an exception
	public IStatus execute(IProgressMonitor monitor) throws InterruptedException {
		throw new InterruptedException();
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
