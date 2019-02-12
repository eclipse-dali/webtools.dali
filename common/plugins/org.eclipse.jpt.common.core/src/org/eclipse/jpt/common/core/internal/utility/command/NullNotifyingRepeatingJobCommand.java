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
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.command.NotifyingRepeatingJobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the notifying repeating command interface that
 * will do nothing when executed.
 */
public final class NullNotifyingRepeatingJobCommand
	implements NotifyingRepeatingJobCommand, Serializable
{
	public static final NotifyingRepeatingJobCommand INSTANCE = new NullNotifyingRepeatingJobCommand();

	public static NotifyingRepeatingJobCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullNotifyingRepeatingJobCommand() {
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

	public void addListener(NotifyingRepeatingJobCommand.Listener listener) {
		// do nothing
	}

	public void removeListener(NotifyingRepeatingJobCommand.Listener listener) {
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
